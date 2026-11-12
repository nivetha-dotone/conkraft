package com.wfd.dot1.cwfm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import com.wfd.dot1.cwfm.dao.ReconciliationDao;
import com.wfd.dot1.cwfm.dto.BankWageDetailDTO;
import com.wfd.dot1.cwfm.dto.BankWageExpectedDTO;
import com.wfd.dot1.cwfm.dto.BankWagePaymentDTO;
import com.wfd.dot1.cwfm.dto.BankWageUploadDTO;
import com.wfd.dot1.cwfm.dto.ChallanEmployeeDTO;
import com.wfd.dot1.cwfm.dto.ReconciliationMismatchDTO;
import com.wfd.dot1.cwfm.dto.ReconciliationResultDTO;
import com.wfd.dot1.cwfm.dto.WorkmenReconciliationDTO;
import com.wfd.dot1.cwfm.util.ApplicationConfig;
import com.wfd.dot1.cwfm.util.BankWageRatingCalculator;

import jakarta.servlet.ServletContext;

@Service
public class ReconciliationServiceImpl implements ReconciliationService {

	private static final Logger logger =
	        LoggerFactory.getLogger(ReconciliationServiceImpl.class);
	@Autowired
	private ReconciliationDao reconciliationDao;

	@Autowired
	private ServletContext servletContext;

	@Override
	public List<WorkmenReconciliationDTO> getPfReconciliationList(Long contractorId, String fromDate, String toDate) {
		return reconciliationDao.getPfReconciliationList(contractorId, fromDate, toDate);
	}

	@Override
	public List<WorkmenReconciliationDTO> getEsicReconciliationList(Long contractorId, String fromDate, String toDate) {
		return reconciliationDao.getEsicReconciliationList(contractorId, fromDate, toDate);
	}

	@Override
	public ReconciliationResultDTO processReconciliation(Long contractorId, String reconType, String fromDate,
			String toDate, MultipartFile file, String uploadedBy) throws Exception {

		ReconciliationResultDTO result = new ReconciliationResultDTO();

		if (file == null || file.isEmpty()) {
			result.setStatus("UNVERIFIED");
			result.setMessage("Please upload a valid challan file.");
			return result;
		}

		String uploadDir = servletContext.getRealPath("/") + File.separator + "recon_uploads";
		File dir = new File(uploadDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		String originalFileName = file.getOriginalFilename();
		String savedFilePath = uploadDir + File.separator + System.currentTimeMillis() + "_" + originalFileName;
		File dest = new File(savedFilePath);
		file.transferTo(dest);

		List<WorkmenReconciliationDTO> dbList = "PF".equalsIgnoreCase(reconType)
				? reconciliationDao.getPfReconciliationList(contractorId, fromDate, toDate)
				: reconciliationDao.getEsicReconciliationList(contractorId, fromDate, toDate);

		List<ChallanEmployeeDTO> documentList;
		try {
			documentList = parseChallanDocument(dest, reconType);
			if (documentList == null || documentList.isEmpty()) {
				result.setStatus("UNVERIFIED");
				result.setMessage("No employee records could be extracted from the uploaded challan.");
				return result;
			}
		} catch (IllegalArgumentException ex) {
			result.setStatus("UNVERIFIED");
			result.setMessage(ex.getMessage() == null ? "Invalid challan file." : ex.getMessage());
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setStatus("UNVERIFIED");
			result.setMessage("Unable to process the uploaded challan. Please verify the file format and contents.");
			return result;
		}

		System.out.println("Reconciliation Type = " + reconType);
		System.out.println("DB rows count = " + dbList.size());
		System.out.println("Document parsed rows count = " + documentList.size());


		List<ReconciliationMismatchDTO> mismatchList = compareData(dbList, documentList, reconType);

		int totalCount = dbList.size();
		int unverifiedCount = mismatchList.size();
		int verifiedCount = Math.max(0, totalCount - unverifiedCount);
		String overallStatus = unverifiedCount == 0 ? "VERIFIED" : "UNVERIFIED";

		Long uploadId = reconciliationDao.saveUploadMaster(contractorId, reconType, originalFileName, savedFilePath,
				overallStatus, totalCount, verifiedCount, unverifiedCount,fromDate,toDate, uploadedBy);

		if (!mismatchList.isEmpty()) {
			reconciliationDao.saveMismatchList(uploadId, mismatchList);
		}

		result.setUploadId(uploadId);
		result.setStatus(overallStatus);
		result.setTotalCount(totalCount);
		result.setVerifiedCount(verifiedCount);
		result.setUnverifiedCount(unverifiedCount);
		result.setMismatchList(mismatchList);
		result.setMessage("Reconciliation completed successfully.");

		return result;
	}

	private List<ReconciliationMismatchDTO> compareData(List<WorkmenReconciliationDTO> dbList,
			List<ChallanEmployeeDTO> docList, String reconType) {

		List<ReconciliationMismatchDTO> mismatchList = new ArrayList<>();
		Map<String, ChallanEmployeeDTO> documentMap = new HashMap<>();

		for (ChallanEmployeeDTO dto : docList) {
			String key = buildDocumentKey(dto, reconType);
			if (!key.isEmpty()) {
				documentMap.put(key, dto);
			}
		}

		for (WorkmenReconciliationDTO db : dbList) {

			String dbKey = buildDbKey(db, reconType);
			BigDecimal dbAmount = "PF".equalsIgnoreCase(reconType) ? safeAmount(db.getPfAmount())
					: safeAmount(db.getEsicAmount());

			System.out.println("Comparing DB Key = " + dbKey + " against document map");

			if ("PF".equalsIgnoreCase(reconType) && safe(dbKey).isEmpty()) {
				ReconciliationMismatchDTO mm = new ReconciliationMismatchDTO();
				mm.setGatePassId(db.getGatePassId());
				mm.setWorkmenName(db.getWorkmenName());
				mm.setDbNumber(dbKey);
				mm.setDocNumber(null);
				mm.setDbAmount(dbAmount);
				mm.setDocAmount(null);
				mm.setMismatchReason("UAN number missing in system");
				mm.setReconType(reconType);
				mismatchList.add(mm);
				continue;
			}

			if ("ESIC".equalsIgnoreCase(reconType) && safe(dbKey).isEmpty()) {
				ReconciliationMismatchDTO mm = new ReconciliationMismatchDTO();
				mm.setGatePassId(db.getGatePassId());
				mm.setWorkmenName(db.getWorkmenName());
				mm.setDbNumber(dbKey);
				mm.setDocNumber(null);
				mm.setDbAmount(dbAmount);
				mm.setDocAmount(null);
				mm.setMismatchReason("ESIC number missing in system");
				mm.setReconType(reconType);
				mismatchList.add(mm);
				continue;
			}

			ChallanEmployeeDTO doc = documentMap.get(dbKey);

			if (doc == null) {
				ReconciliationMismatchDTO mm = new ReconciliationMismatchDTO();
				mm.setGatePassId(db.getGatePassId());
				mm.setWorkmenName(db.getWorkmenName());
				mm.setDbNumber(dbKey);
				mm.setDocNumber(null);
				mm.setDbAmount(dbAmount);
				mm.setDocAmount(null);
				mm.setMismatchReason("PF".equalsIgnoreCase(reconType) ? "UAN number not found in uploaded challan"
						: "ESIC number not found in uploaded challan");
				mm.setReconType(reconType);
				mismatchList.add(mm);
				continue;
			}

			boolean amountMismatch = dbAmount.compareTo(safeAmount(doc.getAmount())) != 0;
			boolean nameMismatch = !normalizeName(db.getWorkmenName()).replaceAll(" ", "")
					.equalsIgnoreCase(normalizeName(doc.getWorkmenName()).replaceAll(" ", ""));

			if (amountMismatch || nameMismatch) {
				ReconciliationMismatchDTO mm = new ReconciliationMismatchDTO();
				mm.setGatePassId(db.getGatePassId());
				mm.setWorkmenName(db.getWorkmenName());
				mm.setDbNumber(dbKey);
				mm.setDocNumber(dbKey);
				mm.setDbAmount(dbAmount);
				mm.setDocAmount(safeAmount(doc.getAmount()));

				StringBuilder reason = new StringBuilder();
				if (nameMismatch) {
					reason.append("Name mismatch");
				}
				if (amountMismatch) {
					if (reason.length() > 0) {
						reason.append(", ");
					}
					reason.append("Amount mismatch");
				}

				mm.setMismatchReason(reason.toString());
				mm.setReconType(reconType);
				mismatchList.add(mm);
			}
		}

		return mismatchList;
	}

	private String buildDbKey(WorkmenReconciliationDTO db, String reconType) {
		if ("PF".equalsIgnoreCase(reconType)) {
			return safe(db.getUanNumber());
		}
		return normalizeEsicNumber(db.getEsicNumber());
	}

	private String buildDocumentKey(ChallanEmployeeDTO dto, String reconType) {
		if ("PF".equalsIgnoreCase(reconType)) {
			return safe(dto.getUanNumber());
		}
		return normalizeEsicNumber(dto.getEsicNumber());
	}

	private List<ChallanEmployeeDTO> parseChallanDocument(File file, String reconType) throws Exception {
		String fileName = file.getName().toLowerCase();

		if (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
			return parseExcelChallan(file, reconType);
		} else if (fileName.endsWith(".csv")) {
			return parseCsvChallan(file, reconType);
		} else if (fileName.endsWith(".pdf")) {
			return parsePdfChallan(file, reconType);
		} else {
			throw new IllegalArgumentException("Unsupported file format. Please upload PDF, XLS, XLSX or CSV.");
		}
	}

	private List<ChallanEmployeeDTO> parseExcelChallan(File file, String reconType) throws Exception {
		List<ChallanEmployeeDTO> list = new ArrayList<>();

		try (FileInputStream fis = new FileInputStream(file); Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = workbook.getSheetAt(0);
			if (sheet == null) {
				return list;
			}

			Row headerRow = sheet.getRow(0);
			if (headerRow == null) {
				return list;
			}

			Map<String, Integer> columnMap = buildColumnIndexMap(headerRow);

			Integer nameCol = findColumn(columnMap, "name", "employee name", "member name", "workmen name");
			Integer uanCol = findColumn(columnMap, "uan", "uan number");
			Integer esicCol = findColumn(columnMap, "esic number", "esicno", "esic", "ip number", "employee ip number");
			Integer amountCol = findColumn(columnMap, "amount", "pf amount", "esic amount", "ee", "ip contribution",
					"price", "total contribution");

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null || isRowEmpty(row)) {
					continue;
				}

				ChallanEmployeeDTO dto = new ChallanEmployeeDTO();

				if (nameCol != null) {
					dto.setWorkmenName(getCellStringValue(row.getCell(nameCol)));
				}
				if (uanCol != null) {
					dto.setUanNumber(cleanString(getCellStringValue(row.getCell(uanCol))));
				}
				if (esicCol != null) {
					dto.setEsicNumber(cleanString(getCellStringValue(row.getCell(esicCol))));
				}
				if (amountCol != null) {
					dto.setAmount(parseBigDecimal(getCellStringValue(row.getCell(amountCol))));
				}

				if (isValidChallanRow(dto, reconType)) {
					list.add(dto);
				}
			}
		}

		return list;
	}

	private List<ChallanEmployeeDTO> parseCsvChallan(File file, String reconType) throws Exception {
		List<ChallanEmployeeDTO> list = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

			String headerLine = br.readLine();
			if (headerLine == null || headerLine.trim().isEmpty()) {
				return list;
			}

			String[] headers = splitCsvLine(headerLine);
			Map<String, Integer> columnMap = new HashMap<>();
			for (int i = 0; i < headers.length; i++) {
				columnMap.put(normalizeHeader(headers[i]), i);
			}

			Integer nameCol = findColumn(columnMap, "name", "employee name", "member name", "workmen name");
			Integer uanCol = findColumn(columnMap, "uan", "uan number");
			Integer esicCol = findColumn(columnMap, "esic number", "esicno", "esic", "ip number", "employee ip number");
			Integer amountCol = findColumn(columnMap, "amount", "pf amount", "esic amount", "ee", "ip contribution",
					"price", "total contribution");

			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty()) {
					continue;
				}

				String[] values = splitCsvLine(line);
				ChallanEmployeeDTO dto = new ChallanEmployeeDTO();

				if (nameCol != null && nameCol < values.length) {
					dto.setWorkmenName(values[nameCol].trim());
				}
				if (uanCol != null && uanCol < values.length) {
					dto.setUanNumber(cleanString(values[uanCol]));
				}
				if (esicCol != null && esicCol < values.length) {
					dto.setEsicNumber(cleanString(values[esicCol]));
				}
				if (amountCol != null && amountCol < values.length) {
					dto.setAmount(parseBigDecimal(values[amountCol]));
				}

				if (isValidChallanRow(dto, reconType)) {
					list.add(dto);
				}
			}
		}

		return list;
	}

	private List<ChallanEmployeeDTO> parsePdfChallan(File file, String reconType) throws Exception {
		return "PF".equalsIgnoreCase(reconType) ? parsePfPdf(file) : parseEsicPdf(file);
	}

	private List<ChallanEmployeeDTO> parsePfPdf(File file) throws Exception {

		if (file == null || !file.isFile() || file.length() == 0) {
			throw new IllegalArgumentException("Invalid PF ECR PDF. Please upload a non-empty PDF file.");
		}

		List<ChallanEmployeeDTO> result = new ArrayList<>();
		Set<String> seenUans = new HashSet<>();
		int expectedMembers;
		BigDecimal expectedEeTotal;

		try (PDDocument document = PDDocument.load(file)) {

			if (document.isEncrypted()) {
				throw new IllegalArgumentException("Password-protected PF ECR PDFs are not supported.");
			}

			if (document.getNumberOfPages() == 0) {
				throw new IllegalArgumentException("The uploaded PF ECR PDF does not contain any pages.");
			}

			PDFTextStripper headerStripper = new PDFTextStripper();
			headerStripper.setStartPage(1);
			headerStripper.setEndPage(1);
			String firstPage = headerStripper.getText(document);
			String normalizedText = normalizePfPdfText(firstPage);

			// EPFO summary footer example:
			// 380 357347 165256 522603 GNGGN2590741000 /
			Pattern totalsPattern = Pattern.compile(
					"(?<!\\d)(\\d{1,6})\\s+([\\d,]+)\\s+([\\d,]+)\\s+([\\d,]+)\\s+[A-Z0-9]{8,}\\s*/",
					Pattern.CASE_INSENSITIVE);
			Matcher totalsMatcher = totalsPattern.matcher(normalizedText);
			String membersText = null;
			String eeTotalText = null;

			while (totalsMatcher.find()) {
				// Capture while the matcher is on a successful match.
				membersText = totalsMatcher.group(1);
				eeTotalText = totalsMatcher.group(4);
			}

			if (membersText == null || eeTotalText == null) {

			    System.out.println("PF ECR Summary could not be identified.");
			    System.out.println("Extracted first-page text: " + normalizedText);

			    throw new IllegalArgumentException(
			            "Unable to verify the PF ECR summary details in the uploaded PDF. "
			            + "Please upload the original EPFO ECR PDF with the summary details clearly visible. "
			            + "Scanned PDFs, incomplete PDFs, or PDFs with a different format cannot be processed."
			    );
			}

			try {
				expectedMembers = Integer.parseInt(membersText.replace(",", "").trim());
				expectedEeTotal = new BigDecimal(eeTotalText.replace(",", "").trim());
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Invalid member count or EE contribution in the PF ECR summary.", ex);
			}

			if (expectedMembers <= 0 || expectedEeTotal.signum() < 0) {
				throw new IllegalArgumentException("PF ECR summary contains invalid member count or contribution total.");
			}

			int expectedSerial = 1;

			for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {

				int pdfPageNumber = pageIndex + 1;
				PfPositionStripper stripper = new PfPositionStripper();
				stripper.setSortByPosition(true);
				stripper.setStartPage(pdfPageNumber);
				stripper.setEndPage(pdfPageNumber);
				stripper.getText(document);

				List<PfWord> words = stripper.getWords();
				if (words == null || words.isEmpty()) {
					continue;
				}

				List<PfPdfRow> rows = new ArrayList<>();

				// STEP 1: Identify UAN rows before assigning names and amounts.
				// Do not validate MediaBox width: valid EPFO PDFs can have
				// different page dimensions or page rotation settings.
				for (PfWord word : words) {
					if (word == null || word.value == null) {
						continue;
					}

					String value = word.value.trim();
					if (word.x >= 20f && word.x < 150f && value.matches("\\d{12}")) {
						PfPdfRow row = new PfPdfRow();
						row.uan = value;
						row.y = word.y;
						rows.add(row);
					}
				}

				rows.sort(Comparator.comparingDouble(row -> row.y));

				// Notes/header/footer pages are ignored. The final summary
				// member-count check prevents missing employee pages being accepted.
				if (rows.isEmpty()) {
					System.out.println("No PF employee UAN rows found on page " + pdfPageNumber);
					continue;
				}

				// STEP 2: Assign name and EE contribution words to detected rows.
				for (PfWord word : words) {
					if (word == null || word.value == null || word.value.trim().isEmpty()) {
						continue;
					}
					if (word.x < 20f || word.x > 805f) {
						continue;
					}

					PfPdfRow nearest = null;
					float minDistance = Float.MAX_VALUE;
					for (PfPdfRow row : rows) {
						float distance = Math.abs(word.y - row.y);
						if (distance < minDistance) {
							minDistance = distance;
							nearest = row;
						}
					}

					if (nearest == null || minDistance > 8.0f) {
						continue;
					}

					if (word.x >= 125f && word.x < 225f) {
						nearest.returnNameWords.add(word);
					} else if (word.x >= 225f && word.x < 345f) {
						nearest.repositoryNameWords.add(word);
					} else if (word.x >= 575f && word.x < 630f
							&& Math.abs(word.y - nearest.y) < 4f) {
						nearest.eeWords.add(word);
					}
				}

				// STEP 3: Validate each employee row and build the existing DTO.
				for (PfPdfRow row : rows) {

					if (!seenUans.add(row.uan)) {
						throw new IllegalArgumentException("Duplicate UAN found in PF ECR: " + row.uan);
					}

					String returnName = joinPfWords(row.returnNameWords);
					if (returnName.isEmpty()) {
						throw new IllegalArgumentException("Return Name is missing for UAN " + row.uan
								+ " on page " + pdfPageNumber + ".");
					}

					if (row.eeWords.size() != 1) {
						throw new IllegalArgumentException("EE contribution is missing or ambiguous for UAN "
								+ row.uan + " on page " + pdfPageNumber + ".");
					}

					String amountText = row.eeWords.get(0).value.trim();
					if (!amountText.matches("\\d+(?:\\.\\d{1,2})?")) {
						throw new IllegalArgumentException("Invalid EE contribution for UAN " + row.uan + ": " + amountText);
					}

					List<PfWord> serialWords = new ArrayList<>();
					for (PfWord word : words) {
						if (word != null && word.value != null
								&& word.x >= 20f && word.x < 45f
								&& Math.abs(word.y - row.y) < 4f
								&& word.value.matches("\\d{1,4}")) {
							serialWords.add(word);
						}
					}

					if (serialWords.size() != 1) {
						throw new IllegalArgumentException("Serial number is missing or ambiguous for UAN " + row.uan
								+ " on page " + pdfPageNumber + ".");
					}

					int serial;
				try {
						serial = Integer.parseInt(serialWords.get(0).value);
					} catch (NumberFormatException ex) {
						throw new IllegalArgumentException("Invalid serial number for UAN " + row.uan + ".", ex);
					}

					if (serial != expectedSerial) {
						throw new IllegalArgumentException("Unexpected serial number on page " + pdfPageNumber
								+ ". Expected " + expectedSerial + ", found " + serial + ".");
					}
					expectedSerial++;

					ChallanEmployeeDTO dto = new ChallanEmployeeDTO();
					dto.setUanNumber(row.uan);
					dto.setWorkmenName(normalizeName(returnName));
					dto.setAmount(new BigDecimal(amountText));
					result.add(dto);
				}
			}
		} catch (org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException ex) {
			throw new IllegalArgumentException("Password-protected PF ECR PDFs are not supported.", ex);
		} catch (IOException ex) {
			throw new IllegalArgumentException("Unable to read the PF ECR PDF. The file may be damaged or unreadable.", ex);
		}

		if (result.size() != expectedMembers) {
			throw new IllegalArgumentException("PF ECR member count mismatch. Expected " + expectedMembers
					+ ", extracted " + result.size() + ". Please verify that all employee pages were read.");
		}

		BigDecimal actualEeTotal = result.stream()
				.map(ChallanEmployeeDTO::getAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		if (actualEeTotal.compareTo(expectedEeTotal) != 0) {
			throw new IllegalArgumentException("PF EE total mismatch. ECR summary = " + expectedEeTotal
					+ ", extracted = " + actualEeTotal + ".");
		}

		System.out.println("PF ECR parsed successfully. Members=" + result.size()
				+ ", EE total=" + actualEeTotal);
		return result;
	}

	private int extractPfHeaderInt(String text, String regex) {
		return Integer.parseInt(extractPfHeaderValue(text, regex));
	}

	private String extractPfHeaderValue(String text, String regex) {

	    if (text == null || text.trim().isEmpty()) {
	        throw new IllegalArgumentException(
	                "PF ECR header could not be read. Please upload a readable PDF."
	        );
	    }

	    // Normalize special spaces and line breaks returned by PDFBox.
	    String normalizedText = text
	            .replace('\u00A0', ' ')
	            .replace('\u2007', ' ')
	            .replace('\u202F', ' ')
	            .replace('\uFEFF', ' ')
	            .replaceAll("[\\r\\n\\t]+", " ")
	            .replaceAll("\\s+", " ")
	            .trim();

	    Pattern pattern = Pattern.compile(
	            regex,
	            Pattern.CASE_INSENSITIVE | Pattern.DOTALL
	    );

	    Matcher matcher = pattern.matcher(normalizedText);

	    if (matcher.find()) {
	        return matcher.group(1).replace(",", "").trim();
	    }

	    // Print only the header extraction diagnostic while testing.
	    System.out.println("PF ECR header extraction failed.");
	    System.out.println("Expected pattern: " + regex);
	    System.out.println("Normalized header text: " + normalizedText);

	    throw new IllegalArgumentException(
	            "Required PF ECR header field could not be extracted. "
	            + "Please verify the Total Members and Total EPF Contribution values in the PDF."
	    );
	}
	private String joinPfWords(List<PfWord> words) {

		words.sort(Comparator.comparingDouble((PfWord w) -> w.y).thenComparingDouble(w -> w.x));

		StringBuilder name = new StringBuilder();

		for (PfWord word : words) {
			if (name.length() > 0) {
				name.append(' ');
			}
			name.append(word.value);
		}

		return name.toString().trim().replaceAll("\\s+", " ");
	}

	private List<ChallanEmployeeDTO> parseEsicPdf(File file) throws Exception {

		List<ChallanEmployeeDTO> list = new ArrayList<>();

		try (PDDocument document = PDDocument.load(file)) {

			if (document.isEncrypted()) {
				throw new IllegalArgumentException("Password-protected ESIC challan is not supported.");
			}

			PDFTextStripper stripper = new PDFTextStripper();
			stripper.setSortByPosition(true);

			String text = stripper.getText(document);
			text = text.replace("\uFFFE", " ").replace("\uFEFF", " ");

			String[] lines = text.split("\\r?\\n");

			for (String raw : lines) {

				String line = raw == null ? "" : raw.replaceAll("\\s+", " ").trim();

				if (line.isEmpty()) {
					continue;
				}

				// Expected row:
				// Serial IPNumber EmployeeName Days Wages IsDisable Reason Contribution
				if (!line.matches("^\\d+\\s+\\d{8,15}\\s+.*")) {
					continue;
				}

				String[] parts = line.split("\\s+");

				// Serial + IP number + name + 5 trailing columns
				if (parts.length < 8) {
					continue;
				}

				String esicNumber = parts[1];


				// Contribution is always the last column.
				int contributionIndex = parts.length - 1;
				String ipContribution = parts[contributionIndex];

				// Support amounts such as 108.2300, 202.00 and 0.00.
				String amountPattern = "\\d[\\d,]*(?:\\.\\d{1,4})?";

				// Find Days and Wages without depending on the Reason column.
				// Days = integer, Wages = numeric amount.
				int daysIndex = -1;

				for (int i = 2; i < contributionIndex - 2; i++) {

				    if (parts[i].matches("\\d+")
				            && parts[i + 1].matches(amountPattern)) {

				        daysIndex = i;
				        break;
				    }
				}

				if (daysIndex == -1) {
				    continue;
				}

				int wagesIndex = daysIndex + 1;

				String daysWorked = parts[daysIndex];
				String monthlyWages = parts[wagesIndex];


				if (!daysWorked.matches("\\d+")) {
					continue;
				}

				if (!monthlyWages.matches(amountPattern)) {
					continue;
				}

				if (!ipContribution.matches(amountPattern)) {
					continue;
				}

				// Preserve the existing challan layout validation.
//				if (!"-".equals(isDisable) || !"-".equals(reason)) {
//					continue;
//				}

				// Employee name can contain multiple words.
				StringBuilder nameBuilder = new StringBuilder();

				for (int i = 2; i < daysIndex; i++) {
					if (nameBuilder.length() > 0) {
						nameBuilder.append(" ");
					}
					nameBuilder.append(parts[i]);
				}

				String employeeName = nameBuilder.toString().trim();

				if (employeeName.isEmpty()) {
					continue;
				}

				ChallanEmployeeDTO dto = new ChallanEmployeeDTO();

				dto.setEsicNumber(normalizeEsicNumber(esicNumber));
				dto.setWorkmenName(normalizeName(employeeName));
				dto.setAmount(parseBigDecimal(ipContribution));

				if (!dto.getEsicNumber().isEmpty()) {
					list.add(dto);

					System.out.println("ESIC PDF Parsed -> IP Number=" + dto.getEsicNumber() + ", Name="
							+ dto.getWorkmenName() + ", IP Contribution=" + dto.getAmount());
				}
			}
		}

		if (list.isEmpty()) {
			throw new IllegalArgumentException("No employee rows could be read from the uploaded ESIC challan. "
					+ "Please verify the PDF text and challan format.");
		}

		System.out.println("ESIC challan parsed successfully. Rows: " + list.size());

		return list;
	}

	private String normalizeEsicNumber(String value) {
		if (value == null) {
			return "";
		}

		return value.replaceAll("\\D", "");
	}

	private Map<String, Integer> buildColumnIndexMap(Row headerRow) {
		Map<String, Integer> columnMap = new HashMap<>();
		for (int i = 0; i < headerRow.getLastCellNum(); i++) {
			Cell cell = headerRow.getCell(i);
			String header = normalizeHeader(getCellStringValue(cell));
			if (!header.isEmpty()) {
				columnMap.put(header, i);
			}
		}
		return columnMap;
	}

	private Integer findColumn(Map<String, Integer> columnMap, String... aliases) {
		for (String alias : aliases) {
			String key = normalizeHeader(alias);
			if (columnMap.containsKey(key)) {
				return columnMap.get(key);
			}
		}
		return null;
	}

	private String normalizeHeader(String value) {
		if (value == null) {
			return "";
		}
		return value.trim().toLowerCase().replaceAll("[^a-z0-9]", " ").replaceAll("\\s+", " ").trim();
	}

	private String getCellStringValue(Cell cell) {
		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue().toString();
			}
			double value = cell.getNumericCellValue();
			return (value == (long) value) ? String.valueOf((long) value) : String.valueOf(value);
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			try {
				return cell.getStringCellValue().trim();
			} catch (Exception e) {
				try {
					double v = cell.getNumericCellValue();
					return (v == (long) v) ? String.valueOf((long) v) : String.valueOf(v);
				} catch (Exception ex) {
					return "";
				}
			}
		default:
			return "";
		}
	}

	private boolean isRowEmpty(Row row) {
		if (row == null) {
			return true;
		}
		for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
			Cell cell = row.getCell(i);
			if (cell != null && !getCellStringValue(cell).trim().isEmpty()) {
				return false;
			}
		}
		return true;
	}

	private BigDecimal parseBigDecimal(String value) {
		if (value == null || value.trim().isEmpty()) {
			return BigDecimal.ZERO;
		}
		value = value.replaceAll(",", "").trim();
		try {
			return new BigDecimal(value);
		} catch (Exception e) {
			return BigDecimal.ZERO;
		}
	}

	private String cleanString(String value) {
		return value == null ? "" : value.trim().replaceAll("\\s+", "");
	}

	private boolean isValidChallanRow(ChallanEmployeeDTO dto, String reconType) {
		if (dto == null) {
			return false;
		}

		if ("PF".equalsIgnoreCase(reconType)) {
			return !safe(dto.getUanNumber()).isEmpty();
		} else if ("ESIC".equalsIgnoreCase(reconType)) {
			return !safe(dto.getEsicNumber()).isEmpty();
		}

		return false;
	}

	private String[] splitCsvLine(String line) {
		return line.split("\\s*,\\s*");
	}

	private String safe(String val) {
		return val == null ? "" : val.trim();
	}

	private BigDecimal safeAmount(BigDecimal val) {
		return val == null ? BigDecimal.ZERO : val;
	}

	private String normalizeName(String name) {
		if (name == null) {
			return "";
		}
		return name.trim().replaceAll("\\s+", " ").toUpperCase();
	}

	private static class PfPdfRow {
		String uan;
		float y;
		List<PfWord> returnNameWords = new ArrayList<>();
		List<PfWord> repositoryNameWords = new ArrayList<>();
		List<PfWord> eeWords = new ArrayList<>();
	}

	private static class PfPositionStripper extends PDFTextStripper {

		private final List<PfWord> words = new ArrayList<>();

		public PfPositionStripper() throws IOException {
			super();
		}

		@Override
		protected void writeString(String text, List<TextPosition> textPositions) throws IOException {

			if (textPositions == null || textPositions.isEmpty()) {
				return;
			}

			StringBuilder currentWord = new StringBuilder();
			float startX = 0;
			float startY = 0;
			float previousEndX = -1;

			for (TextPosition position : textPositions) {

				String character = position.getUnicode();
				if (character == null || character.isEmpty()) {
					continue;
				}

				float x = position.getXDirAdj();
				float y = position.getYDirAdj();
				boolean whitespace = character.trim().isEmpty();

				boolean uanBoundary = currentWord.length() == 12
						&& currentWord.toString().matches("\\d{12}")
						&& Character.isLetter(character.charAt(0));

				boolean newWord = whitespace || uanBoundary
						|| (previousEndX >= 0 && x - previousEndX > 2.5f)
						|| (currentWord.length() > 0 && Math.abs(y - startY) > 2f);

				if (newWord && currentWord.length() > 0) {
					words.add(new PfWord(currentWord.toString(), startX, startY));
					currentWord.setLength(0);
				}

				if (!whitespace) {
					if (currentWord.length() == 0) {
						startX = x;
						startY = y;
					}
					currentWord.append(character);
				}

				previousEndX = x + position.getWidthDirAdj();
			}

			if (currentWord.length() > 0) {
				words.add(new PfWord(currentWord.toString(), startX, startY));
			}
		}

		public List<PfWord> getWords() {
			return words;
		}
	}

	private static class PfWord {

		String value;
		float x;
		float y;

		public PfWord(String value, float x, float y) {
			this.value = value;
			this.x = x;
			this.y = y;
		}
	}
	private String normalizePfPdfText(String text) {

	    if (text == null) {
	        return "";
	    }

	    return text
	            .replace('\u00A0', ' ')
	            .replace('\u2007', ' ')
	            .replace('\u202F', ' ')
	            .replace('\uFEFF', ' ')
	            .replaceAll("[\\r\\n\\t]+", " ")
	            .replaceAll("\\s+", " ")
	            .trim();
	}
	
@Override
public List<BankWagePaymentDTO> parseBankWageCsv(
        MultipartFile file) throws Exception {

    if (file == null || file.isEmpty()) {
        throw new IllegalArgumentException(
                "Please upload a bank wage CSV file.");
    }

    String fileName = file.getOriginalFilename();

    if (fileName == null ||
            !fileName.toLowerCase(Locale.ROOT).endsWith(".csv")) {
        throw new IllegalArgumentException(
                "Only CSV files are allowed.");
    }

    List<String> expectedHeaders = Arrays.asList(
            "GatePassId",
            "AccountNumber",
            "IFSCCode",
            "BeneficiaryName",
            "Amount",
            "TransactionDate",
            "UTRNumber"
    );

    List<BankWagePaymentDTO> payments = new ArrayList<>();

    Set<String> gatePassIds = new HashSet<>();
    Set<String> utrNumbers = new HashSet<>();

    DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern(
                    "dd/MM/uuuu", Locale.ENGLISH)
                    .withResolverStyle(ResolverStyle.STRICT);

    try (Reader reader = new InputStreamReader(
            file.getInputStream(), StandardCharsets.UTF_8);
         CSVParser parser = CSVFormat.DEFAULT.parse(reader)) {

        List<CSVRecord> records = parser.getRecords();

        if (records.size() < 2) {
            throw new IllegalArgumentException(
                    "CSV must contain headers and payment records.");
        }

        CSVRecord headerRecord = records.get(0);

        if (headerRecord.size() != expectedHeaders.size()) {
            throw new IllegalArgumentException(
                    "Invalid CSV header count.");
        }

        for (int i = 0; i < expectedHeaders.size(); i++) {

            String actualHeader = headerRecord.get(i)
                    .replace("\uFEFF", "").trim();

            if (!expectedHeaders.get(i).equals(actualHeader)) {
                throw new IllegalArgumentException(
                        "Invalid CSV header at column " + (i + 1)
                        + ". Expected: " + expectedHeaders.get(i));
            }
        }

        for (int i = 1; i < records.size(); i++) {

            CSVRecord record = records.get(i);
            long rowNumber = record.getRecordNumber();

            if (record.size() != expectedHeaders.size()) {
                throw new IllegalArgumentException(
                        "Invalid column count at row " + rowNumber);
            }

            String gatePassId = record.get(0).trim();
            String accountNumber = record.get(1).trim();
            String ifscCode = record.get(2).trim()
                    .toUpperCase(Locale.ROOT);
            String beneficiaryName = record.get(3).trim();
            String amountText = record.get(4).trim();
            String dateText = record.get(5).trim();
            String utrNumber = record.get(6).trim()
                    .toUpperCase(Locale.ROOT);

            if (gatePassId.isEmpty()
                    || accountNumber.isEmpty()
                    || ifscCode.isEmpty()
                    || beneficiaryName.isEmpty()
                    || amountText.isEmpty()
                    || dateText.isEmpty()
                    || utrNumber.isEmpty()) {

                throw new IllegalArgumentException(
                        "Mandatory field missing at row " + rowNumber);
            }

            if (!ifscCode.matches(
                    "^[A-Z]{4}0[A-Z0-9]{6}$")) {

                throw new IllegalArgumentException(
                        "Invalid IFSC at row " + rowNumber);
            }

            if (!gatePassIds.add(gatePassId)) {
                throw new IllegalArgumentException(
                        "Duplicate GatePassId at row " + rowNumber);
            }

            if (!utrNumbers.add(utrNumber)) {
                throw new IllegalArgumentException(
                        "Duplicate UTR at row " + rowNumber);
            }

            BigDecimal amount;
            LocalDate transactionDate;

            try {
                amount = new BigDecimal(amountText);

                if (amount.compareTo(BigDecimal.ZERO) <= 0
                        || amount.scale() > 2) {
                    throw new IllegalArgumentException();
                }

            } catch (Exception ex) {
                throw new IllegalArgumentException(
                        "Invalid amount at row " + rowNumber);
            }

            try {
                transactionDate = LocalDate.parse(
                        dateText, dateFormatter);

            } catch (Exception ex) {
                throw new IllegalArgumentException(
                        "Invalid transaction date at row "
                        + rowNumber + ". Use DD/MM/YYYY.");
            }

            BankWagePaymentDTO dto =
                    new BankWagePaymentDTO();

            dto.setGatePassId(gatePassId);
            dto.setAccountNumber(accountNumber);
            dto.setIfscCode(ifscCode);
            dto.setBeneficiaryName(beneficiaryName);
            dto.setAmount(amount);
            dto.setTransactionDate(transactionDate);
            dto.setUtrNumber(utrNumber);

            payments.add(dto);
        }
    }

    if (payments.isEmpty()) {
        throw new IllegalArgumentException(
                "No payment records found in CSV.");
    }

    return payments;
}

@Override
public List<BankWageDetailDTO> reconcileBankWages(
        List<BankWageExpectedDTO> expectedList,
        List<BankWagePaymentDTO> paymentList,
        YearMonth wageMonth) {

    if (expectedList == null || expectedList.isEmpty()) {
        throw new IllegalArgumentException(
                "No Wage Cost Report data found.");
    }

    if (paymentList == null || paymentList.isEmpty()) {
        throw new IllegalArgumentException(
                "No bank payment records found.");
    }

    Map<String, BankWageExpectedDTO> expectedMap =
            new HashMap<>();

    for (BankWageExpectedDTO expected : expectedList) {

        String gatePassId = required(
                expected.getGatePassId(), "Expected GatePassId");

        if (expectedMap.putIfAbsent(
                gatePassId, expected) != null) {
            throw new IllegalArgumentException(
                    "Duplicate GatePassId in Wage Cost Report: "
                    + gatePassId);
        }

        if (expected.getExpectedAmount() == null
                || expected.getExpectedAmount()
                    .compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Invalid EmployeeNet for GatePassId: "
                    + gatePassId);
        }

        required(expected.getAccountNumber(),
                "Account number for " + gatePassId);
        required(expected.getIfscCode(),
                "IFSC for " + gatePassId);
    }

    Map<String, BankWagePaymentDTO> paymentMap =
            new HashMap<>();

    Set<String> utrNumbers = new HashSet<>();

    for (BankWagePaymentDTO payment : paymentList) {

        String gatePassId = required(
                payment.getGatePassId(), "CSV GatePassId");

        if (paymentMap.putIfAbsent(
                gatePassId, payment) != null) {
            throw new IllegalArgumentException(
                    "Duplicate GatePassId in CSV: "
                    + gatePassId);
        }

        String utr = required(
                payment.getUtrNumber(), "UTR")
                .toUpperCase(Locale.ROOT);

        if (!utrNumbers.add(utr)) {
            throw new IllegalArgumentException(
                    "Duplicate UTR in CSV: " + utr);
        }
    }

    if (!expectedMap.keySet().equals(paymentMap.keySet())) {

        Set<String> missing =
                new HashSet<>(expectedMap.keySet());
        missing.removeAll(paymentMap.keySet());

        Set<String> unexpected =
                new HashSet<>(paymentMap.keySet());
        unexpected.removeAll(expectedMap.keySet());

        throw new IllegalArgumentException(
                "Workmen mismatch. Missing: " + missing
                + ", Unexpected: " + unexpected);
    }

    List<String> existingUtrs =
            reconciliationDao.findExistingBankUtrs(
                    new ArrayList<>(utrNumbers));

    if (!existingUtrs.isEmpty()) {
        throw new IllegalArgumentException(
                "UTR already exists in database: "
                + existingUtrs);
    }

    LocalDate dueDate = getBankPaymentDueDate(wageMonth);

    List<BankWageDetailDTO> results =
            new ArrayList<>();

    for (BankWageExpectedDTO expected : expectedList) {

        String gatePassId =
                expected.getGatePassId().trim();

        BankWagePaymentDTO payment =
                paymentMap.get(gatePassId);

        if (!normalizeAccount(expected.getAccountNumber())
                .equals(normalizeAccount(
                        payment.getAccountNumber()))) {
            throw new IllegalArgumentException(
                    "Bank account mismatch for GatePassId: "
                    + gatePassId);
        }

        if (!required(expected.getIfscCode(), "IFSC")
                .equalsIgnoreCase(
                        required(payment.getIfscCode(), "IFSC"))) {
            throw new IllegalArgumentException(
                    "IFSC mismatch for GatePassId: "
                    + gatePassId);
        }

        BigDecimal expectedAmount =
                expected.getExpectedAmount();

        BigDecimal paidAmount = payment.getAmount();

        if (paidAmount == null
                || paidAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Invalid paid amount for GatePassId: "
                    + gatePassId);
        }

        BigDecimal difference =
                expectedAmount.subtract(paidAmount);

        BigDecimal variance = difference
                .multiply(new BigDecimal("100"))
                .divide(expectedAmount, 4,
                        RoundingMode.HALF_UP);

        BigDecimal paymentPercentage = paidAmount
                .multiply(new BigDecimal("100"))
                .divide(expectedAmount, 4,
                        RoundingMode.HALF_UP);

        String amountStatus =
                difference.compareTo(BigDecimal.ZERO) > 0
                        ? "SHORT_PAYMENT"
                        : "MATCHED";

        String timelinessStatus =
                payment.getTransactionDate()
                        .isAfter(dueDate)
                        ? "LATE"
                        : "ON_TIME";

        BankWageDetailDTO detail =
                new BankWageDetailDTO();

        detail.setGatePassId(gatePassId);
        detail.setWorkmenName(expected.getWorkmenName());
        detail.setAccountNumber(payment.getAccountNumber());
        detail.setIfscCode(payment.getIfscCode());
        detail.setExpectedAmount(expectedAmount);
        detail.setPaidAmount(paidAmount);
        detail.setDifferenceAmount(difference);
        detail.setVariancePercentage(variance);
        detail.setPaymentPercentage(paymentPercentage);
        detail.setTransactionDate(
                payment.getTransactionDate());
        detail.setDueDate(dueDate);
        detail.setUtrNumber(
                payment.getUtrNumber()
                        .trim().toUpperCase(Locale.ROOT));
        detail.setAmountStatus(amountStatus);
        detail.setTimelinessStatus(timelinessStatus);

      

int rating = BankWageRatingCalculator.calculateRating(
        expectedAmount, paidAmount);

detail.setRating(rating);
detail.setRatingDescription(
        BankWageRatingCalculator.getRatingDescription(rating));


        results.add(detail);
    }

    return results;
}

private String required(String value, String field) {

    if (value == null || value.trim().isEmpty()) {
        throw new IllegalArgumentException(
                field + " is missing.");
    }

    return value.trim();
}

private String normalizeAccount(String accountNumber) {

    return required(accountNumber, "Account number")
            .replaceAll("\\s+", "");
}

@Override
public void calculateBankUploadSummary(
        BankWageUploadDTO upload,
        List<BankWageDetailDTO> details) {

    if (details == null || details.isEmpty()) {
        throw new IllegalArgumentException(
                "No reconciliation details found.");
    }

    BigDecimal totalExpected = BigDecimal.ZERO;
    BigDecimal totalPaid = BigDecimal.ZERO;
    BigDecimal totalShortfall = BigDecimal.ZERO;
    BigDecimal totalRating = BigDecimal.ZERO;

    for (BankWageDetailDTO detail : details) {

        totalExpected = totalExpected.add(
                detail.getExpectedAmount());

        totalPaid = totalPaid.add(
                detail.getPaidAmount());

        BigDecimal difference =
                detail.getDifferenceAmount();

        if (difference.compareTo(BigDecimal.ZERO) > 0) {
            totalShortfall = totalShortfall.add(difference);
        }

        totalRating = totalRating.add(
                BigDecimal.valueOf(detail.getRating()));
    }

    BigDecimal averageRating = totalRating.divide(
            BigDecimal.valueOf(details.size()),
            2,
            RoundingMode.HALF_UP);

    upload.setTotalWorkmen(details.size());
    upload.setTotalExpectedAmount(totalExpected);
    upload.setTotalPaidAmount(totalPaid);
    upload.setTotalShortfall(totalShortfall);
    upload.setContractorRating(averageRating);
    upload.setStatus("ACCEPTED");
}



@Transactional(rollbackFor = Exception.class)
public Long saveBankReconciliation(
        BankWageUploadDTO upload,
        List<BankWageDetailDTO> details) {

    if (upload == null || details == null
            || details.isEmpty()) {
        throw new IllegalArgumentException(
                "Invalid bank reconciliation data.");
    }

    calculateBankUploadSummary(upload, details);

    Long uploadId =
            reconciliationDao.saveBankUpload(upload);

    reconciliationDao.saveBankDetails(uploadId, details);

    return uploadId;
}




private LocalDate getBankPaymentDueDate(YearMonth wageMonth) {

    if (wageMonth == null) {
        throw new IllegalArgumentException(
                "Wage month is mandatory.");
    }

    return wageMonth.plusMonths(1).atDay(7);
}

//@Value("${bank.wage.upload.directory}")
//private String bankWageUploadDirectory;

private String bankWageUploadDirectory = ApplicationConfig.getProperty("bank.wage.upload.directory");
private Path storeBankWageCsv(
        MultipartFile file,
        Long contractorId,
        YearMonth wageMonth) throws IOException {

    if (file == null || file.isEmpty()) {
        throw new IllegalArgumentException(
                "Bank wage CSV file is empty.");
    }

    if (contractorId == null || wageMonth == null) {
        throw new IllegalArgumentException(
                "Contractor and wage month are mandatory.");
    }

    Path baseDirectory = Paths.get(
            bankWageUploadDirectory)
            .toAbsolutePath()
            .normalize();

    Path targetDirectory = baseDirectory
            .resolve(String.valueOf(contractorId))
            .resolve(wageMonth.toString())
            .normalize();

    if (!targetDirectory.startsWith(baseDirectory)) {
        throw new SecurityException(
                "Invalid bank wage upload directory.");
    }

    Files.createDirectories(targetDirectory);

    String generatedFileName =
            UUID.randomUUID().toString() + ".csv";

    Path targetFile =
            targetDirectory.resolve(generatedFileName);

    try (java.io.InputStream input =
            file.getInputStream()) {

        Files.copy(
                input,
                targetFile,
                StandardCopyOption.REPLACE_EXISTING);
    }

    return targetFile;
}


@Override
@Transactional(rollbackFor = Exception.class)
public Long processBankWageReconciliation(
        Long principalEmployerId,
        Long contractorId,
        String wageMonth,
        String fromDate,
        String toDate,
        MultipartFile file,
        String uploadedBy) throws Exception {

    // 1. Validate mandatory inputs
    if (principalEmployerId == null || contractorId == null) {
        throw new IllegalArgumentException(
                "Principal Employer and Contractor are mandatory.");
    }

    if (file == null || file.isEmpty()) {
        throw new IllegalArgumentException(
                "Please upload a bank wage CSV file.");
    }

    if (uploadedBy == null || uploadedBy.trim().isEmpty()) {
        throw new IllegalArgumentException(
                "Uploaded by is mandatory.");
    }

    if (fromDate == null || fromDate.trim().isEmpty()
            || toDate == null || toDate.trim().isEmpty()) {
        throw new IllegalArgumentException(
                "Wage Cost Report date range is mandatory.");
    }

    // 2. Parse wage month
    YearMonth selectedMonth;

    try {
        selectedMonth = YearMonth.parse(wageMonth);
    } catch (Exception ex) {
        throw new IllegalArgumentException(
                "Invalid wage month. Expected YYYY-MM.", ex);
    }

    // 3. Validate Principal Employer and Contractor mapping
    if (!reconciliationDao.isValidBankWagePeContractor(
            principalEmployerId, contractorId)) {

        throw new IllegalArgumentException(
                "The selected Contractor is not mapped "
                + "to the selected Principal Employer "
                + "through any Work Order.");
    }

    // 4. Prevent duplicate reconciliation
    if (reconciliationDao.existsBankWageReconciliation(
            contractorId, selectedMonth)) {

        throw new IllegalArgumentException(
                "Bank wage reconciliation is already completed "
                + "for contractor " + contractorId
                + " for wage month " + selectedMonth
                + ". Re-upload is not allowed.");
    }

    // 5. Parse and validate the uploaded bank CSV
    List<BankWagePaymentDTO> payments =
            parseBankWageCsv(file);

    // 6. Validate Wage Cost Report mappings
    List<Map<String, Object>> invalidRecords =
            reconciliationDao.findInvalidBankWageRecords(
                    principalEmployerId,
                    contractorId,
                    fromDate,
                    toDate);

    if (invalidRecords != null && !invalidRecords.isEmpty()) {
        throw new IllegalArgumentException(
                "Wage Cost Report contains "
                + invalidRecords.size()
                + " invalid records. Please verify "
                + "GatePass, Principal Employer, Contractor, "
                + "Work Order and EmployeeNet mappings.");
    }

    // 7. Fetch expected wages for selected PE and contractor
    List<BankWageExpectedDTO> expected =
            reconciliationDao.getExpectedBankWages(
                    principalEmployerId,
                    contractorId,
                    fromDate,
                    toDate);

    if (expected == null || expected.isEmpty()) {
        throw new IllegalArgumentException(
                "No Wage Cost Report records found "
                + "for the selected Principal Employer, "
                + "Contractor and date range.");
    }

    // 8. Validate all workmen and calculate reconciliation
    // Includes:
    // - GatePass matching
    // - Account / IFSC matching
    // - UTR uniqueness
    // - Short payment
    // - Variance and rating
    // - Timeliness (7th of following month)
    List<BankWageDetailDTO> details =
            reconcileBankWages(
                    expected, payments, selectedMonth);

    // 9. Prepare upload master
    BankWageUploadDTO upload = new BankWageUploadDTO();

    upload.setPrincipalEmployerId(principalEmployerId);
    upload.setContractorId(contractorId);
    upload.setWageMonth(selectedMonth.atDay(1));
    upload.setFileName(file.getOriginalFilename());
    upload.setUploadedBy(uploadedBy);

    // 10. Calculate summary and contractor rating
    calculateBankUploadSummary(upload, details);

    // 11. Verify transaction before writing the CSV
    if (!TransactionSynchronizationManager
            .isActualTransactionActive()
            || !TransactionSynchronizationManager
                    .isSynchronizationActive()) {

        throw new IllegalStateException(
                "An active Spring transaction is required "
                + "for bank wage reconciliation.");
    }

    // 12. Save original CSV securely
    Path storedFile = storeBankWageCsv(
            file, contractorId, selectedMonth);

    upload.setFilePath(storedFile.toString());

    boolean cleanupRegistered = false;

    try {

        // 13. Delete CSV if SQL transaction rolls back
        TransactionSynchronizationManager
                .registerSynchronization(
                    new TransactionSynchronization() {

            @Override
            public void afterCompletion(int status) {

                if (status != TransactionSynchronization
                        .STATUS_COMMITTED) {

                    try {
                        Files.deleteIfExists(storedFile);

                    } catch (IOException cleanupEx) {
                    	logger.error(
                                "Failed to clean up bank wage CSV: {}",
                                storedFile,
                                cleanupEx);
                    }
                }
            }
        });

        cleanupRegistered = true;

        // 14. Save upload master and all detail records
        // Both inserts participate in the same transaction
        return saveBankReconciliation(upload, details);

    } catch (Exception ex) {

        // If registration failed, clean up immediately.
        // Otherwise afterCompletion handles rollback.
        if (!cleanupRegistered) {

            try {
                Files.deleteIfExists(storedFile);

            } catch (IOException cleanupEx) {
                ex.addSuppressed(cleanupEx);
            }
        }

        throw ex;
    }
}

@Override
public List<BankWageExpectedDTO> getExpectedBankWages(Long principalEmployerId, Long contractorId, String fromDate,
		String toDate) {
	// TODO Auto-generated method stub
	return reconciliationDao.getExpectedBankWages(principalEmployerId, contractorId, fromDate, toDate);
}

@Override
public List<BankWageDetailDTO> getBankWageDetails(Long uploadId) {
	// TODO Auto-generated method stub
	return reconciliationDao.getBankWageDetails(uploadId);
}


}