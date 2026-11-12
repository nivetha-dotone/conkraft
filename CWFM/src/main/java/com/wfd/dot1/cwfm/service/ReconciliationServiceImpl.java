package com.wfd.dot1.cwfm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wfd.dot1.cwfm.dao.ReconciliationDao;
import com.wfd.dot1.cwfm.dto.ChallanEmployeeDTO;
import com.wfd.dot1.cwfm.dto.ReconciliationMismatchDTO;
import com.wfd.dot1.cwfm.dto.ReconciliationResultDTO;
import com.wfd.dot1.cwfm.dto.WorkmenReconciliationDTO;
import com.wfd.dot1.cwfm.service.ReconciliationService;

import jakarta.servlet.ServletContext;

@Service
public class ReconciliationServiceImpl implements ReconciliationService {

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
}