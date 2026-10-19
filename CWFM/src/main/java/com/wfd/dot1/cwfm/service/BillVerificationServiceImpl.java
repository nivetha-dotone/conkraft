package com.wfd.dot1.cwfm.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wfd.dot1.cwfm.dao.BillVerificationDao;
import com.wfd.dot1.cwfm.dto.ApproveRejectBillDto;
import com.wfd.dot1.cwfm.dto.ApproverStatusDTO;
import com.wfd.dot1.cwfm.dto.CMSWageCostDTO;
import com.wfd.dot1.cwfm.dto.ChecklistItemDTO;
import com.wfd.dot1.cwfm.dto.GatePassStatusLogDto;
import com.wfd.dot1.cwfm.dto.LicenseDto;
import com.wfd.dot1.cwfm.dto.WageCostReportResponse;
import com.wfd.dot1.cwfm.enums.GatePassStatus;
import com.wfd.dot1.cwfm.enums.GatePassType;
import com.wfd.dot1.cwfm.enums.WorkFlowType;
import com.wfd.dot1.cwfm.pojo.BillReportFile;
import com.wfd.dot1.cwfm.pojo.BillStatusLogDto;
import com.wfd.dot1.cwfm.pojo.BillVerification;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BillVerificationServiceImpl implements BillVerificationService {
	private static final Logger log = LoggerFactory.getLogger(BillVerificationServiceImpl.class.getName());

	@Autowired
	BillVerificationDao billDao;

	@Override
	public List<CMSWageCostDTO> getBillVerificationList(String userId, String deptId, String unitId) {
		// TODO Auto-generated method stub
		return billDao.getBillVerificationList(userId, deptId, unitId);
	}

	@Override
	public BillVerification viewbillDetails(String transactionId) {
		// TODO Auto-generated method stub
		return billDao.viewbillDetails(transactionId);
	}

	@Override
	public BillVerification viewbillReportsDetails(String transactionId) {
		// TODO Auto-generated method stub
		return billDao.viewbillReportsDetails(transactionId);
	}

	@Override
	public BillVerification viewbillhrclearanceDetails(String transactionId) {
		// TODO Auto-generated method stub
		return billDao.viewbillhrclearanceDetails(transactionId);
	}

	@Override
	public BillVerification viewbillprecomments(String transactionId) {
		// TODO Auto-generated method stub
		return billDao.viewbillprecomments(transactionId);
	}

	@Override
	public BillVerification viewbilleicDetails(String transactionId) {
		// TODO Auto-generated method stub
		return billDao.viewbilleicDetails(transactionId);
	}

	@Override
	public String generateWCTransactionId() {
		return billDao.generateWCTransactionId();
	}

	@Override
	public List<PrincipalEmployer> getPEDetailByUser(String userAccount) {
		// TODO Auto-generated method stub
		return billDao.getPEDetailByUser(userAccount);
	}

	@Override
	public String save(CMSWageCostDTO workflowData, List<ChecklistItemDTO> listDto) {
		String result = null;
		int workFlowTypeId = billDao.getWorkflowType("Bill Verification", String.valueOf(workflowData.getUnitId()));
		if (workFlowTypeId == WorkFlowType.AUTO.getWorkFlowTypeId()) {
			workflowData.setStatus(Integer.parseInt(GatePassStatus.APPROVED.getStatus()));
			result = billDao.saveBill(workflowData);
			processWageCostReportForApproval(String.valueOf(workflowData.getWcTransId()), String.valueOf(workflowData.getCreatedBy()));
			if (result != null) {
				// save checklist
				this.saveChecklist(listDto, result, workflowData.getCreatedBy());
				BillStatusLogDto dto = new BillStatusLogDto();
				dto.setTransactionId(String.valueOf(workflowData.getWcTransId()));
				dto.setStatus(workflowData.getStatus());
				dto.setComments(workflowData.getComments());
				dto.setUpdatedBy(workflowData.getCreatedBy());
				billDao.saveBillStatusLog(dto);
			}
		} else {
			workflowData.setStatus(Integer.parseInt(GatePassStatus.APPROVALPENDING.getStatus()));
			result = billDao.saveBill(workflowData);
			if (result != null) {
				// save checklist
				this.saveChecklist(listDto, result, workflowData.getCreatedBy());
				BillStatusLogDto dto = new BillStatusLogDto();
				dto.setTransactionId(String.valueOf(workflowData.getWcTransId()));
				dto.setStatus(workflowData.getStatus());
				dto.setComments(workflowData.getComments());
				dto.setUpdatedBy(workflowData.getCreatedBy());
				billDao.saveBillStatusLog(dto);
			}
		}
		return result;
	}

	@Override
	public List<CMSWageCostDTO> getBillVerificationListForApprovers(String principalEmployerId, String deptId,
			MasterUser user) {
		// TODO Auto-generated method stub
		int workFlowType = billDao.getWorkflowType("Bill Verification", principalEmployerId);

		return billDao.getBillVerificationListForApprovers(user.getRoleId(), workFlowType, deptId, principalEmployerId);
	}

	@Override
	public CMSWageCostDTO getIndividualBillDetails(String transactionId) {
		// TODO Auto-generated method stub
		return billDao.getIndividualBVRDetails(transactionId);
	}

	public void saveFile(BillReportFile b) {
		billDao.saveFile(b);
	}

	public List<BillReportFile> findByTransactionIdAndType(String transactionId, String type) {
		return billDao.findByTransactionIdAndType(Long.parseLong(transactionId), type);
	}

	@Override
	public void saveChecklist(List<ChecklistItemDTO> checklistItems, String wcTransId, String userId) {
		billDao.saveChecklist(checklistItems, wcTransId, userId);
	}

	@Override
	public List<ChecklistItemDTO> getChecklistByTransactionId(String wcTransId) {
		return billDao.fetchChecklistByTransactionId(wcTransId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public String approveRejectBill(ApproveRejectBillDto dto) {

		CMSWageCostDTO gpm = billDao.getIndividualBVRDetails(dto.getTransactionId());
		String result = null;
		result = billDao.approveRejectBill(dto);
		BillStatusLogDto ldto = new BillStatusLogDto();
		ldto.setTransactionId(dto.getTransactionId());
		ldto.setStatus(Integer.parseInt(dto.getStatus()));
		ldto.setComments(dto.getComments());
		ldto.setUpdatedBy(dto.getApproverId());
		billDao.saveBillStatusLog(ldto);
		boolean status = false;
		if (dto.getStatus().equals(GatePassStatus.REJECTED.getStatus())) {
			status = billDao.updateBillStatusByTransactionId(dto.getTransactionId(), dto.getStatus());
		} else {
			boolean isLastApprover = false;
			int workFlowTypeId = billDao.getWorkFlowTYpeByTransactionId(dto.getTransactionId());
			if (workFlowTypeId == WorkFlowType.SEQUENTIAL.getWorkFlowTypeId()) {
				isLastApprover = billDao.isLastApprover(dto.getApproverRole(), dto.getUnitId());
			} else if (workFlowTypeId == WorkFlowType.PARALLEL.getWorkFlowTypeId()) {
				isLastApprover = billDao.isLastApproverForParallel(dto.getTransactionId(), dto.getRoleId(),
						dto.getUnitId());

			}

			// check if last approver, if last approver change the status of gate pass main
			// to approved

//			if (isLastApprover) {
//				status = billDao.updateBillStatusByTransactionId(dto.getTransactionId(), dto.getStatus());
//
//			}
//		}
//
//		return result;
//	}
			if (isLastApprover) {

				/*
				 * Insert Wage Cost Report rows before approving the bill. If no CSV exists,
				 * this helper skips the insertion. If processing fails, an exception triggers
				 * transaction rollback.
				 */
				processWageCostReportForApproval(dto.getTransactionId(), String.valueOf(dto.getApproverId()));

				status = billDao.updateBillStatusByTransactionId(dto.getTransactionId(), dto.getStatus());

				if (!status) {
					throw new IllegalStateException(
							"Unable to update bill status for transaction: " + dto.getTransactionId());
				}
			}
		}

		return result;
	}

	@Override
	public List<LicenseDto> getLicensesByWorkOrder(String workOrder) {
		return billDao.getLicensesByWorkOrder(workOrder);
	}

	@Override
	public String getValidUptoByLicense(String licenseNumber) {
		return billDao.getValidUptoByLicense(licenseNumber);
	}

	@Override
	public List<ApproverStatusDTO> getBillApprovalDetails(String transactionId, String unitId, String gatePassTypeId) {
		// TODO Auto-generated method stub
		return billDao.getBillApprovalDetails(transactionId, unitId, gatePassTypeId);

	}

	private static final Path ROOT_DIRECTORY = Paths.get("D:/wfd_cwfm/WageBill");

	private static final String INVALID_TEMPLATE_MESSAGE = "Invalid template format of Wage Cost Report.";
	/*
	 * CSV header names in the required order.
	 */
	private static final List<String> EXPECTED_HEADERS = Arrays.asList("Entry Pass Number", "Workmen Name", "DOJ",
			"Department", "Sub Area", "Contractor", "Work Order", "From Date", "To Date", "PF Number", "UAN Number",
			// "Present Days", "Weekly Offs", "Holiday", "Leave Days", "Paid Days", "Holiday
			// Worked Hrs", "Night Shift",
			"ESIC Number", "Trade", "Skill", "Workmen Category", "Calendar Days", "Present Days", "Weekly Offs",
			"Holiday", "Leave Days", "Paid Days", "Holiday Worked Hrs", "Night Shift", "Arrear Days", "Arrear OT", "OT",
			"Basic", "DA", "HRA", "Other Allowances", "Uniform Allow", "Washing Allowance", "Leave Encashment",
			"EX Serviceman Allowance", "Supervisor Allowance", "Hardship Allowance", "Gunman Allowance",
			"Technical Allowance", "Driver Allowance", "QRT Allowance", "Extra Hours Amount",
			"Arrear Correction Amount", "Arrear Amount", "Gross", "Night Shift Allowance", "Flexi Benefits",
			"PF Employer Contribution", "ESIC Employer Contribution", "LWF Contribution Employer", "Monthly Wages",
			"PF Employee Contribution", "ESIC Employee Contribution", "Professional Tax", "LWF Contribution Employee",
			"Flexi Deductions", "Contractor Gross", "Service Charge", "Taxes", "Total Amount");

	@Override
	public WageCostReportResponse validateAndSave(MultipartFile file, List<PersonOrgLevel> contList, String userId,
			String transactionId) {

		try {
			// 1. Validate file and required context.
			if (file == null || file.isEmpty()) {
				return response(false, "Please upload the Wage Cost Report CSV file.");
			}

			if (contList == null || contList.isEmpty()) {
				return response(false, "No contractor mapping is available for the logged-in user.");
			}

			if (userId == null || userId.trim().isEmpty() || transactionId == null || transactionId.trim().isEmpty()) {
				return response(false, "User ID or transaction ID is missing. Unable to save the file.");
			}

			List<Map<String, Object>> reportRows = new ArrayList<>();

			// 2. Read and validate the CSV.
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

				String headerLine = readCsvRecord(reader);

				if (headerLine == null || headerLine.trim().isEmpty()) {
					return response(false, INVALID_TEMPLATE_MESSAGE);
				}

				List<String> actualHeaders = parseCsvLine(headerLine);

				// Remove UTF-8 BOM and trim the first header.
				if (!actualHeaders.isEmpty()) {
					actualHeaders.set(0, actualHeaders.get(0).replace("\uFEFF", "").trim());
				}

				// Match the complete header list: count, names and order.
				if (!headersMatch(actualHeaders)) {
					return response(false, INVALID_TEMPLATE_MESSAGE);
				}

				// These headers occur once in EXPECTED_HEADERS.
				int entryPassIndex = EXPECTED_HEADERS.indexOf("Entry Pass Number");
				int fromDateIndex = EXPECTED_HEADERS.indexOf("From Date");
				int toDateIndex = EXPECTED_HEADERS.indexOf("To Date");

				if (entryPassIndex < 0 || fromDateIndex < 0 || toDateIndex < 0) {
					return response(false, "Required headers Entry Pass Number, From Date "
							+ "and To Date are missing from the configured template.");
				}

				String csvRecord;
				int rowNumber = 0;

				while ((csvRecord = readCsvRecord(reader)) != null) {
					rowNumber++;

					if (csvRecord.trim().isEmpty()) {
						continue;
					}

					List<String> values = parseCsvLine(csvRecord);

					// Ignore rows containing only empty values.
					boolean emptyRow = values.stream().allMatch(value -> value == null || value.trim().isEmpty());

					if (emptyRow) {
						continue;
					}

					// Each data row must have exactly the expected number of columns.
					if (values.size() != EXPECTED_HEADERS.size()) {
						return response(false,
								INVALID_TEMPLATE_MESSAGE + " Invalid column count at CSV row " + rowNumber + ".");
					}

					String entryPassNumber = values.get(entryPassIndex).trim();

					String fromDateText = values.get(fromDateIndex).trim();

					String toDateText = values.get(toDateIndex).trim();

					if (entryPassNumber.isEmpty()) {
						return response(false, "Entry Pass Number is required at CSV row " + rowNumber + ".");
					}

					if (fromDateText.isEmpty() || toDateText.isEmpty()) {
						return response(false, "From Date and To Date are required at CSV row " + rowNumber + ".");
					}

					LocalDate fromDate;
					LocalDate toDate;

					try {
						fromDate = parseReportDate(fromDateText);
						toDate = parseReportDate(toDateText);
					} catch (DateTimeParseException ex) {
						return response(false, "Invalid From Date or To Date at CSV row " + rowNumber
								+ ". Supported formats: yyyy-MM-dd, " + "dd/MM/yyyy or dd-MM-yyyy.");
					}

					if (fromDate.isAfter(toDate)) {
						return response(false, "From Date cannot be after To Date at CSV row " + rowNumber + ".");
					}

					// Store only the fields required for validation.
					// No report data is inserted into the database.
					Map<String, Object> row = new LinkedHashMap<>();

					row.put("EntryPassNumber", entryPassNumber);
					row.put("FromDate", java.sql.Date.valueOf(fromDate));
					row.put("ToDate", java.sql.Date.valueOf(toDate));
					row.put("RowNumber", rowNumber);

					reportRows.add(row);
				}
			}

			if (reportRows.isEmpty()) {
				return response(false, "The Wage Cost Report CSV does not contain any data rows.");
			}

			// 3. Get contractor IDs belonging to the logged-in user.
			List<Long> contractorIds = contList.stream().map(PersonOrgLevel::getId).filter(Objects::nonNull)
					.map(id -> Long.valueOf(id.toString())).distinct().collect(Collectors.toList());

			if (contractorIds.isEmpty()) {
				return response(false, "No valid contractor IDs were found for the logged-in user.");
			}

			// 4. Collect all uploaded gate pass numbers.
			List<String> entryPassNumbers = reportRows.stream()
					.map(row -> Objects.toString(row.get("EntryPassNumber"), "").trim())
					.filter(value -> !value.isEmpty()).distinct().collect(Collectors.toList());

			// 5. Reject the entire upload if any gate pass is not owned
			// by a contractor belonging to the logged-in user.
			List<String> invalidNumbers = billDao.findEntryPassNumbersNotBelongingToContractors(entryPassNumbers,
					contractorIds);

			if (invalidNumbers != null && !invalidNumbers.isEmpty()) {
				List<String> invalidList = invalidNumbers.stream().filter(Objects::nonNull).map(String::trim)
						.filter(value -> !value.isEmpty()).distinct().collect(Collectors.toList());

				if (!invalidList.isEmpty()) {
					return response(false, "Workmen are not belonging to the logged-in user's "
							+ "contractors. Invalid gate passes: " + String.join(", ", invalidList));
				}
			}

			// 6. Check database records using each uploaded row's own date range.
			List<String> duplicateNumbers = billDao.findExistingGatePassNumbersForRanges(reportRows);

			if (duplicateNumbers != null && !duplicateNumbers.isEmpty()) {
				List<String> duplicateList = duplicateNumbers.stream().filter(Objects::nonNull).map(String::trim)
						.filter(value -> !value.isEmpty()).distinct().collect(Collectors.toList());

				if (!duplicateList.isEmpty()) {
					return response(false,
							"Wage Cost Report data is already present in the "
									+ "system for the uploaded date range(s). " + "Duplicate gate passes: "
									+ String.join(", ", duplicateList));
				}
			}

			// 7. Check duplicates within this uploaded file as well.
			List<String> uploadedDuplicates = findOverlappingDuplicatesInUpload(reportRows);

			if (!uploadedDuplicates.isEmpty()) {
				return response(false, "Duplicate gate passes with overlapping dates exist "
						+ "within the uploaded file: " + String.join(", ", uploadedDuplicates));
			}

			// 8. All validation passed. Save the original CSV file only.
//			// No calls to saveWageCostReport() are made in this method.
//			Path normalizedRoot = ROOT_DIRECTORY.toAbsolutePath().normalize();
//
//			Path targetDirectory = normalizedRoot.resolve(userId.trim()).resolve(transactionId.trim()).normalize();
//
//			// Prevent path traversal outside D:/wfd_cwfm.
//			if (!targetDirectory.startsWith(normalizedRoot)) {
//				return response(false, "Invalid file storage path.");
//			}
//
//			Files.createDirectories(targetDirectory);
//
//			//String savedFileName = "WageCostReport_" + UUID.randomUUID() + ".csv";
//			
//
//			Path targetFile = targetDirectory.resolve(savedFileName);
//
//			try (InputStream inputStream = file.getInputStream()) {
//				Files.copy(inputStream, targetFile);
//			}
//
//			WageCostReportResponse result = new WageCostReportResponse();
//			result.setSuccess(true);
//			result.setMessage("Wage Cost Report validated successfully and file saved.");
//			return result;
			// Save the original CSV filename without overwriting existing files.

			Path normalizedRoot = ROOT_DIRECTORY.toAbsolutePath().normalize();

			Path targetDirectory = normalizedRoot
			        .resolve(userId.trim())
			        .resolve(transactionId.trim())
			        .normalize();

			// Prevent path traversal.
			if (!targetDirectory.startsWith(normalizedRoot)) {
			    return response(false, "Invalid file storage path.");
			}

			String originalFileName = file.getOriginalFilename();

			if (originalFileName == null || originalFileName.trim().isEmpty()) {
			    return response(false, "The uploaded file does not have a valid filename.");
			}

			// Keep only the filename, not any client-supplied directory path.
			String savedFileName = originalFileName.trim().replace('\\', '/');
			savedFileName = savedFileName.substring(savedFileName.lastIndexOf('/') + 1);

			if (savedFileName.isEmpty()
			        || ".".equals(savedFileName)
			        || "..".equals(savedFileName)
			        || !savedFileName.toLowerCase(Locale.ROOT).endsWith(".csv")) {
			    return response(false, "Please upload a valid CSV file.");
			}

			Path targetFile = targetDirectory.resolve(savedFileName).normalize();

			if (!targetFile.startsWith(targetDirectory)) {
			    return response(false, "Invalid file name or storage path.");
			}

			try {
			    Files.createDirectories(targetDirectory);

			    try (InputStream inputStream = file.getInputStream()) {
			        // No REPLACE_EXISTING: an existing file will not be overwritten.
			        Files.copy(inputStream, targetFile);
			    }

			} catch (FileAlreadyExistsException ex) {
			    log.warn("Wage Cost Report already exists. Transaction ID: {}, File: {}",
			            transactionId, savedFileName);

			    return response(false,
			            "A file with the name '" + savedFileName
			                    + "' already exists for this transaction. Please use a different filename.");

			} catch (IOException ex) {
			    log.error("Unable to save Wage Cost Report. Transaction ID: {}, Path: {}",
			            transactionId, targetFile, ex);

			    return response(false,
			            "Unable to save the Wage Cost Report file. Please check the server logs.");
			}

			WageCostReportResponse result = new WageCostReportResponse();
			result.setSuccess(true);
			result.setMessage("Wage Cost Report validated successfully and file saved.");

			return result;

		} catch (IOException ex) {
			// Replace with your existing logger if one is available.
			log.error("Unable to read or save Wage Cost Report.", ex);

			return response(false, "Unable to read or save the Wage Cost Report file.");
		} catch (Exception ex) {
			log.error("Wage Cost Report validation failed.", ex);

			return response(false, "Unable to validate the Wage Cost Report. Please try again.");
		}
	}

	private LocalDate parseReportDate(String value) {
		String dateText = value == null ? "" : value.trim();

		List<DateTimeFormatter> formats = Arrays.asList(DateTimeFormatter.ISO_LOCAL_DATE,
				DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT),
				DateTimeFormatter.ofPattern("d-M-uuuu").withResolverStyle(ResolverStyle.STRICT));

		for (DateTimeFormatter formatter : formats) {
			try {
				return LocalDate.parse(dateText, formatter);
			} catch (DateTimeParseException ignored) {
				// Try the next supported date format.
			}
		}

		throw new DateTimeParseException("Unsupported date format", dateText, 0);
	}

	private List<String> findOverlappingDuplicatesInUpload(List<Map<String, Object>> reportRows) {

		Set<String> duplicates = new LinkedHashSet<>();

		for (int i = 0; i < reportRows.size(); i++) {
			Map<String, Object> first = reportRows.get(i);

			String firstNumber = Objects.toString(first.get("EntryPassNumber"), "").trim();

			LocalDate firstFrom = ((java.sql.Date) first.get("FromDate")).toLocalDate();

			LocalDate firstTo = ((java.sql.Date) first.get("ToDate")).toLocalDate();

			for (int j = i + 1; j < reportRows.size(); j++) {
				Map<String, Object> second = reportRows.get(j);

				String secondNumber = Objects.toString(second.get("EntryPassNumber"), "").trim();

				if (!firstNumber.equalsIgnoreCase(secondNumber)) {
					continue;
				}

				LocalDate secondFrom = ((java.sql.Date) second.get("FromDate")).toLocalDate();

				LocalDate secondTo = ((java.sql.Date) second.get("ToDate")).toLocalDate();

				boolean overlaps = !firstFrom.isAfter(secondTo) && !secondFrom.isAfter(firstTo);

				if (overlaps) {
					duplicates.add(firstNumber);
				}
			}
		}

		return new ArrayList<>(duplicates);
	}

	private boolean headersMatch(List<String> actualHeaders) {
		if (actualHeaders.size() != EXPECTED_HEADERS.size()) {
			return false;
		}

		for (int i = 0; i < EXPECTED_HEADERS.size(); i++) {
			String actual = actualHeaders.get(i) == null ? "" : actualHeaders.get(i).trim();

			if (!EXPECTED_HEADERS.get(i).equalsIgnoreCase(actual)) {
				return false;
			}
		}

		return true;
	}

	/*
	 * Reads a complete CSV record, including quoted fields containing commas or
	 * line breaks.
	 */
	private String readCsvRecord(BufferedReader reader) throws IOException {
		StringBuilder record = new StringBuilder();
		String line;
		boolean insideQuotes = false;

		while ((line = reader.readLine()) != null) {
			if (record.length() > 0) {
				record.append('\n');
			}

			record.append(line);

			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == '"') {
					if (insideQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
						i++;
					} else {
						insideQuotes = !insideQuotes;
					}
				}
			}

			if (!insideQuotes) {
				return record.toString();
			}
		}

		if (record.length() > 0) {
			if (insideQuotes) {
				throw new IOException("Invalid CSV format: an enclosed field is not closed.");
			}
			return record.toString();
		}

		return null;
	}

	/*
	 * Splits a CSV record while preserving commas inside double quotes. Double
	 * quotes escaped as "" are converted to a single quote.
	 */
	private List<String> parseCsvLine(String line) throws IOException {
		List<String> values = new ArrayList<>();
		StringBuilder value = new StringBuilder();
		boolean insideQuotes = false;

		for (int i = 0; i < line.length(); i++) {
			char ch = line.charAt(i);

			if (ch == '"') {
				if (insideQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
					value.append('"');
					i++;
				} else {
					insideQuotes = !insideQuotes;
				}
			} else if (ch == ',' && !insideQuotes) {
				values.add(value.toString());
				value.setLength(0);
			} else {
				value.append(ch);
			}
		}

		if (insideQuotes) {
			throw new IOException("Invalid CSV format: unmatched double quote.");
		}

		values.add(value.toString());
		return values;
	}

	private WageCostReportResponse response(boolean success, String message) {

		WageCostReportResponse result = new WageCostReportResponse();
		result.setSuccess(success);
		result.setMessage(message);
		return result;
	}

	private static final Path WAGE_REPORT_ROOT = Paths.get("D:/wfd_cwfm/WageBill");

	private void processWageCostReportForApproval(String transactionId, String updatedBy) {

		try {
			Path csvFile = findWageCostReportFile(transactionId);

			// Wage Cost Report is optional. No file means skip insertion.
			if (csvFile == null) {
				log.info("No Wage Cost Report CSV found for transaction {}. " + "Skipping Wage Cost Report insertion.",
						transactionId);
				return;
			}

			List<Map<String, Object>> rows = readWageCostReportCsv(csvFile);

			// A file with a header but no data rows has nothing to insert.
			if (rows.isEmpty()) {
				log.info("Wage Cost Report CSV contains no data rows for transaction {}.", transactionId);
				return;
			}

			billDao.insertWageCostReportRows(rows, updatedBy);

			log.info("Inserted {} Wage Cost Report rows for transaction {}.", rows.size(), transactionId);

		} catch (Exception e) {
			log.error("Failed to process Wage Cost Report for transaction {}.", transactionId, e);

			throw new IllegalStateException("Unable to process Wage Cost Report for transaction " + transactionId
					+ ". Approval has been cancelled.", e);
		}
	}



private Path findWageCostReportFile(String transactionId) throws IOException {

    if (transactionId == null || transactionId.trim().isEmpty()) {
        throw new IllegalArgumentException("Transaction ID is required.");
    }

    String safeTransactionId = transactionId.trim();

    if (safeTransactionId.contains("/")
            || safeTransactionId.contains("\\")
            || ".".equals(safeTransactionId)
            || "..".equals(safeTransactionId)) {
        throw new IllegalArgumentException("Invalid transaction ID.");
    }

    Path root = WAGE_REPORT_ROOT.toAbsolutePath().normalize();

    log.info("Searching Wage Cost Report. Root: {}, Transaction ID: {}",
            root, safeTransactionId);

    if (!Files.isDirectory(root)) {
        log.warn("Wage Cost Report root directory does not exist: {}", root);
        return null;
    }

    List<Path> matchingFiles = new ArrayList<>();

    try (Stream<Path> userDirectories = Files.list(root)) {

        Iterator<Path> iterator = userDirectories.iterator();

        while (iterator.hasNext()) {

            Path userDirectory = iterator.next();

            if (!Files.isDirectory(userDirectory)) {
                continue;
            }

            // Directory: WageBill/{userId}/{transactionId}
            Path transactionDirectory =
                    userDirectory.resolve(safeTransactionId).normalize();

            if (!transactionDirectory.startsWith(root)
                    || !Files.isDirectory(transactionDirectory)) {
                continue;
            }

            log.info("Checking Wage Cost Report directory: {}",
                    transactionDirectory);

            try (Stream<Path> files = Files.list(transactionDirectory)) {

                files.filter(Files::isRegularFile)
                        // Original filename is allowed.
                        // Example: October_Wage_Report.csv
                        .filter(path -> path.getFileName().toString()
                                .toLowerCase(Locale.ROOT)
                                .endsWith(".csv"))
                        .forEach(matchingFiles::add);
            }
        }
    }

    if (matchingFiles.isEmpty()) {
        log.warn(
                "No CSV file found for transaction {} under root {}",
                safeTransactionId, root);

        return null;
    }

    // If multiple CSV files exist, use the latest modified file.
    Path latestFile = null;
    long latestModifiedTime = Long.MIN_VALUE;

    for (Path file : matchingFiles) {

        long modifiedTime = Files.getLastModifiedTime(file).toMillis();

        if (modifiedTime > latestModifiedTime) {
            latestModifiedTime = modifiedTime;
            latestFile = file;
        }
    }

    log.info("Wage Cost Report file found: {}", latestFile);

    return latestFile;
}


	private static final List<String> WAGE_REPORT_DB_COLUMNS = Arrays.asList("EntryPassNumber", "WorkmenName", "DOJ",
			"Department", "SubArea", "Contractor", "WorkOrder", "FromDate", "ToDate", "PFNumber", "UANNumber",
			"ESICNumber", "Trade", "Skill", "WorkmenCategory", "CalendarDays", "PresentDays", "WeeklyOffs",
			"Holiday", "LeaveDays", "PaidDays", "HolidayWorkedHrs", "NightShift", "ArrearDays", "ArrearOT", "OT",
			"Basic", "DA", "HRA", "OtherAllowances", "UniformAllow", "WashingAllowance", "LeaveEncashment",
			"ExServicemanAllowance", "SupervisorAllowance", "HardshipAllowance", "GunmanAllowance",
			"TechnicalAllowance", "DriverAllowance", "QRTAllowance", "ExtraHoursAmount", "ArrearCorrectionAmount",
			"ArrearAmount", "Gross", "NightShiftAllowance", "FlexiBenefits", "PFEmployerContribution",
			"ESICEmployerContribution", "LWFContributionEmployer", "MonthlyWages", "PFEmployeeContribution",
			"ESICEmployeeContribution", "ProfessionalTax", "LWFContributionEmployee", "FlexiDeductions",
			"ContractorGross", "ServiceCharge", "Taxes", "TotalAmount");
	private static final Set<String> WAGE_REPORT_TEXT_COLUMNS = Set.of("EntryPassNumber", "WorkmenName", "Department",
			"SubArea", "Contractor", "WorkOrder", "PFNumber", "UANNumber", "ESICNumber", "Trade", "Skill",
			"WorkmenCategory");

	private List<Map<String, Object>> readWageCostReportCsv(Path csvFile) throws IOException {

		List<Map<String, Object>> result = new ArrayList<>();

		try (BufferedReader reader = Files.newBufferedReader(csvFile, StandardCharsets.UTF_8)) {

			String headerLine = readCsvRecord(reader);

			if (headerLine == null || headerLine.trim().isEmpty()) {
				throw new IllegalArgumentException("Wage Cost Report CSV is empty.");
			}

			List<String> actualHeaders = parseCsvLine(headerLine);

			if (!actualHeaders.isEmpty()) {
				actualHeaders.set(0, actualHeaders.get(0).replace("\uFEFF", "").trim());
			}

			if (actualHeaders.size() != WAGE_REPORT_DB_COLUMNS.size() || !headersMatch(actualHeaders)) {
				throw new IllegalArgumentException("Invalid template format of Wage Cost Report.");
			}

			String line;
			int rowNumber = 0; // Header is row 1.

			while ((line = readCsvRecord(reader)) != null) {
				rowNumber++;

				if (line.trim().isEmpty() || line.trim().matches(",+")) {
					continue;
				}

				List<String> values = parseCsvLine(line);

				if (values.size() != WAGE_REPORT_DB_COLUMNS.size()) {
					throw new IllegalArgumentException("Invalid column count in CSV row " + rowNumber + ".");
				}

				String entryPassNumber = values.get(0) == null ? "" : values.get(0).trim();

				if (entryPassNumber.isEmpty()) {
					throw new IllegalArgumentException("Entry Pass Number is required in CSV row " + rowNumber + ".");
				}

				Map<String, Object> row = new LinkedHashMap<>();

				for (int i = 0; i < WAGE_REPORT_DB_COLUMNS.size(); i++) {

					String column = WAGE_REPORT_DB_COLUMNS.get(i);
					String value = values.get(i) == null ? "" : values.get(i).trim();

					Object parsedValue;

					if (value.isEmpty()) {
						parsedValue = null;

					} else if ("DOJ".equals(column) || "FromDate".equals(column) || "ToDate".equals(column)) {

						parsedValue = java.sql.Date.valueOf(parseWageReportDate(value));

					} else if (WAGE_REPORT_TEXT_COLUMNS.contains(column)) {
						parsedValue = value;

					} else {
						// Supports decimal amounts and fractional days/hours.
						try {
							parsedValue = new BigDecimal(value.replace(",", ""));
						} catch (NumberFormatException e) {
							throw new IllegalArgumentException(
									"Invalid numeric value for " + column + " in CSV row " + rowNumber + ": " + value,
									e);
						}
					}

					row.put(column, parsedValue);
				}

				java.sql.Date fromDate = (java.sql.Date) row.get("FromDate");
				java.sql.Date toDate = (java.sql.Date) row.get("ToDate");

				if (fromDate == null || toDate == null) {
					throw new IllegalArgumentException(
							"From Date and To Date are required in CSV row " + rowNumber + ".");
				}

				if (fromDate.after(toDate)) {
					throw new IllegalArgumentException(
							"From Date cannot be after To Date in CSV row " + rowNumber + ".");
				}

				result.add(row);
			}
		}

		return result;
	}

	private LocalDate parseWageReportDate(String value) {

		List<DateTimeFormatter> formats = Arrays.asList(DateTimeFormatter.ISO_LOCAL_DATE,
				DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT),
				DateTimeFormatter.ofPattern("d-M-uuuu").withResolverStyle(ResolverStyle.STRICT));

		for (DateTimeFormatter format : formats) {
			try {
				return LocalDate.parse(value.trim(), format);
			} catch (DateTimeParseException ignored) {
				// Try the next supported date format.
			}
		}

		throw new IllegalArgumentException(
				"Unsupported date format: " + value + ". Use yyyy-MM-dd, d/M/yyyy, or d-M-yyyy.");
	}
}