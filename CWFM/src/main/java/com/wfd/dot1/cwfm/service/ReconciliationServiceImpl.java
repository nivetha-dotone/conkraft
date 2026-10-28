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

		List<ChallanEmployeeDTO> documentList = parseChallanDocument(dest, reconType);

		System.out.println("Reconciliation Type = " + reconType);
		System.out.println("DB rows count = " + dbList.size());
		System.out.println("Document parsed rows count = " + documentList.size());

		for (ChallanEmployeeDTO dto : documentList) {
			System.out.println("Parsed Row -> UAN=" + dto.getUanNumber() + ", ESIC=" + dto.getEsicNumber() + ", Name="
					+ dto.getWorkmenName() + ", Amount=" + dto.getAmount());
		}

		List<ReconciliationMismatchDTO> mismatchList = compareData(dbList, documentList, reconType);

		int totalCount = dbList.size();
		int unverifiedCount = mismatchList.size();
		int verifiedCount = Math.max(0, totalCount - unverifiedCount);
		String overallStatus = unverifiedCount == 0 ? "VERIFIED" : "UNVERIFIED";

		Long uploadId = reconciliationDao.saveUploadMaster(contractorId, reconType, originalFileName, savedFilePath,
				overallStatus, totalCount, verifiedCount, unverifiedCount, uploadedBy);

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
		return safe(db.getEsicNumber());
	}

	private String buildDocumentKey(ChallanEmployeeDTO dto, String reconType) {
		if ("PF".equalsIgnoreCase(reconType)) {
			return safe(dto.getUanNumber());
		}
		return safe(dto.getEsicNumber());
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
			throw new IllegalArgumentException("Invalid PF ECR PDF");
		}

		List<ChallanEmployeeDTO> result = new ArrayList<>();
		Set<String> seenUans = new HashSet<>();

		int expectedMembers;
		BigDecimal expectedEeTotal;

		try (PDDocument document = PDDocument.load(file)) {

			if (document.isEncrypted()) {
				throw new IllegalArgumentException("Password-protected ECR is not supported");
			}

			PDFTextStripper headerStripper = new PDFTextStripper();
			headerStripper.setStartPage(1);
			headerStripper.setEndPage(1);

			String firstPage = headerStripper.getText(document);

			expectedMembers = extractPfHeaderInt(firstPage, "Total\\s+Members\\s+(\\d+)");

			expectedEeTotal = new BigDecimal(extractPfHeaderValue(firstPage, "Total\\s+EPF\\s+Contribution\\s+(\\d+)"));

			int expectedSerial = 1;

			for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {

				// PDFBox document page indexes start from zero.
				PDPage page = document.getPage(pageIndex);

				int pdfPageNumber = pageIndex + 1;

				float pageWidth = page.getMediaBox().getWidth();

				// This parser is calibrated for the uploaded
				// 842-point-wide EPFO ECR table.
				if (Math.abs(pageWidth - 842f) > 3f) {
					throw new IllegalArgumentException("Unsupported ECR layout on page " + (pdfPageNumber));
				}

				PfPositionStripper stripper = new PfPositionStripper();

				stripper.setSortByPosition(true);
				// PDFTextStripper page numbers start from one.
				stripper.setStartPage(pdfPageNumber);
				stripper.setEndPage(pdfPageNumber);
				stripper.getText(document);

				List<PfWord> words = stripper.getWords();

				List<PfPdfRow> rows = new ArrayList<>();

				// Find employee rows using the UAN column.
				for (PfWord word : words) {

					if (word.x >= 40 && word.x < 120 && word.value.matches("\\d{12}")) {

						PfPdfRow row = new PfPdfRow();
						row.uan = word.value;
						row.y = word.y;

						rows.add(row);
					}
				}

				rows.sort(Comparator.comparingDouble(r -> r.y));

// Skip pages containing only notes, headers, or footers.
// Continue validating employee rows on all other pages.
				if (rows.isEmpty()) {
					System.out.println("Skipping page " + (pdfPageNumber) + " because it contains no employee rows.");
					continue;
				}

				// Assign each word to its nearest employee row.
				// This handles name text above and below UAN.
				for (PfWord word : words) {

					if (word.x < 20 || word.x > 805) {
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

					if (nearest == null || minDistance > 18f) {
						continue;
					}

					if (word.x >= 125 && word.x < 225) {
						nearest.returnNameWords.add(word);

					} else if (word.x >= 225 && word.x < 345) {
						nearest.repositoryNameWords.add(word);

					} else if (word.x >= 575 && word.x < 630 && Math.abs(word.y - nearest.y) < 4f) {

						nearest.eeWords.add(word);
					}
				}

				for (PfPdfRow row : rows) {

					if (!seenUans.add(row.uan)) {
						throw new IllegalArgumentException("Duplicate UAN: " + row.uan);
					}

					String returnName = joinPfWords(row.returnNameWords);

					if (returnName.isEmpty()) {
						throw new IllegalArgumentException("Return Name missing for UAN " + row.uan);
					}

					if (row.eeWords.size() != 1) {
						throw new IllegalArgumentException("Invalid EE contribution for UAN " + row.uan);
					}

					String amountText = row.eeWords.get(0).value;

					if (!amountText.matches("\\d+(?:\\.\\d{1,2})?")) {
						throw new IllegalArgumentException("Invalid PF amount for UAN " + row.uan + ": " + amountText);
					}

					// Find serial number on the same baseline.
					List<PfWord> serialWords = new ArrayList<>();

					for (PfWord word : words) {
						if (word.x >= 20 && word.x < 45 && Math.abs(word.y - row.y) < 4f
								&& word.value.matches("\\d{1,4}")) {
							serialWords.add(word);
						}
					}

					if (serialWords.size() != 1) {
						throw new IllegalArgumentException("Missing serial number for UAN " + row.uan);
					}

					int serial = Integer.parseInt(serialWords.get(0).value);

					if (serial != expectedSerial) {
						throw new IllegalArgumentException(
								"Unexpected serial number. Expected " + expectedSerial + ", found " + serial);
					}

					expectedSerial++;

					ChallanEmployeeDTO dto = new ChallanEmployeeDTO();
					dto.setUanNumber(row.uan);
					dto.setWorkmenName(normalizeName(returnName));
					dto.setAmount(new BigDecimal(amountText));

					result.add(dto);
				}
			}
		}

		if (result.size() != expectedMembers) {
			throw new IllegalArgumentException(
					"PF ECR member count mismatch. Expected " + expectedMembers + ", extracted " + result.size());
		}

		BigDecimal actualEeTotal = result.stream().map(ChallanEmployeeDTO::getAmount).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		if (actualEeTotal.compareTo(expectedEeTotal) != 0) {
			throw new IllegalArgumentException(
					"PF EE total mismatch. ECR header = " + expectedEeTotal + ", extracted = " + actualEeTotal);
		}

		System.out.println("PF ECR parsed successfully");
		System.out.println("Members: " + result.size());
		System.out.println("EE Total: " + actualEeTotal);

		return result;
	}

	private int extractPfHeaderInt(String text, String regex) {
		return Integer.parseInt(extractPfHeaderValue(text, regex));
	}

	private String extractPfHeaderValue(String text, String regex) {

		Matcher matcher = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(text);

		if (!matcher.find()) {
			throw new IllegalArgumentException("Required PF ECR header field missing: " + regex);
		}

		return matcher.group(1);
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
			PDFTextStripper stripper = new PDFTextStripper();
			String text = stripper.getText(document);
			text = text.replace("\uFFFE", " ");
			String[] lines = text.split("\\r?\\n");

			for (String raw : lines) {
				String line = raw == null ? "" : raw.replaceAll("\\s+", " ").trim();
				if (line.isEmpty()) {
					continue;
				}

				if (!line.matches("^\\d+\\s+\\d{8,15}.*")) {
					continue;
				}

				String[] parts = line.split("\\s+");
				if (parts.length < 7) {
					continue;
				}

				String esicNumber = parts[1];

				String maybeIpContribution = parts[parts.length - 1];
				String maybeDash2 = parts[parts.length - 2];
				String maybeDash1 = parts[parts.length - 3];
				String maybeMonthlyWages = parts[parts.length - 4];
				String maybeDaysWorked = parts[parts.length - 5];

				if (!maybeIpContribution.matches("^\\d+(?:\\.\\d{1,2})?$")) {
					continue;
				}
				if (!"-".equals(maybeDash1) || !"-".equals(maybeDash2)) {
					continue;
				}
				if (!maybeMonthlyWages.matches("^\\d+(?:\\.\\d{1,2})?$")) {
					continue;
				}
				if (!maybeDaysWorked.matches("^\\d+$")) {
					continue;
				}

				int nameStart = 2;
				int nameEndExclusive = parts.length - 5;

				StringBuilder nameBuilder = new StringBuilder();
				for (int i = nameStart; i < nameEndExclusive; i++) {
					if (i > nameStart) {
						nameBuilder.append(" ");
					}
					nameBuilder.append(parts[i]);
				}

				ChallanEmployeeDTO dto = new ChallanEmployeeDTO();
				dto.setEsicNumber(cleanString(esicNumber));
				dto.setWorkmenName(normalizeName(nameBuilder.toString().trim()));
				dto.setAmount(parseBigDecimal(maybeIpContribution));

				if (dto.getEsicNumber() != null && !dto.getEsicNumber().isEmpty()) {
					list.add(dto);
				}
			}
		}

		return list;
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

				boolean newWord = whitespace || (previousEndX >= 0 && x - previousEndX > 2.5f)
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
}