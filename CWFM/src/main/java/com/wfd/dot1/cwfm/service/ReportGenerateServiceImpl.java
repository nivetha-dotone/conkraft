package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.controller.CreateEmpFetchByGatePassAPICALL;
import com.wfd.dot1.cwfm.controller.ReportGenerateController;
import com.wfd.dot1.cwfm.dao.ReportGenerateDao;
import com.wfd.dot1.cwfm.dao.WorkmenDao;
import com.wfd.dot1.cwfm.dto.FetchTotalRequestDto;
import com.wfd.dot1.cwfm.dto.FetchTotalResponseDto;
import com.wfd.dot1.cwfm.dto.ReportGenerateDto;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Service
public class ReportGenerateServiceImpl implements ReportGenerateService {
	private static final Logger log = LoggerFactory.getLogger(ReportGenerateServiceImpl.class.getName());
	@Autowired
	ReportGenerateDao reportGenerateDao;

	@Autowired
	private CreateEmpFetchByGatePassAPICALL api;

	@Override
	public ReportGenerateDto getGatePassIds(String reportType, String unitId, String contractorId, String departmentId,
			String fromDate, String toDate, int requestedBy) {
		// gets the report request ID and GatePass IDs.
		ReportGenerateDto result = reportGenerateDao.getGatePassIds(reportType, unitId, contractorId, departmentId,
				fromDate, toDate, requestedBy);
		if (result == null) {
			throw new IllegalStateException("Unable to create report request");
		}
		Long requestId = result.getRequestId();
		Long paramId = result.getReportParamId();
		List<String> gatePassIds = result.getGatePassIds();
		log.info("Report Request ID: {}", requestId);
		log.info("Report Paraam ID: {}", paramId);
		log.info("Report Type ID: {}", reportType);
		log.info("GatePass IDs: {}", gatePassIds);

		if (gatePassIds == null || gatePassIds.isEmpty()) {
			log.warn("No GatePass IDs found. Request ID: {}", requestId);
			insertReportProcess(requestId, paramId, fromDate, toDate, 0);
			return result;
		}

		try {
			// Get actual report name from General Master.
			String reportTypeName = getReportName(reportType);

			// Prepare UKG request.
			FetchTotalRequestDto ukgRequest = new FetchTotalRequestDto();
			ukgRequest.setReqId(String.valueOf(requestId));
			ukgRequest.setPersonNumbers(gatePassIds);
			ukgRequest.setStartDate(fromDate);
			ukgRequest.setEndDate(toDate);
			log.info("Preparing UKG request. Request ID: {}", requestId);

			// Call the correct existing UKG API.
			ResponseEntity<FetchTotalResponseDto> apiResponse = null;
			if ("Punch Total Report".equals(reportTypeName)) {
				log.info("Calling Fetch Total API. Request ID: {}", requestId);
				apiResponse = api.fetchTotalFromUKG(ukgRequest);
			} else if ("Punch Report".equals(reportTypeName)) {
				log.info("Calling Fetch Punch API. Request ID: {}", requestId);
				apiResponse = api.fetchPunchFromUKG(ukgRequest);
			} else if ("Schedule Report".equals(reportTypeName)) {
				log.info("Calling Fetch Schedule API. Request ID: {}", requestId);
				apiResponse = api.fetchScheduleFromUKG(ukgRequest);
			} else {
				log.warn("Invalid Report Type: {}. Request ID: {}", reportTypeName, requestId);
				insertReportProcess(requestId, paramId, fromDate, toDate, 0);
				throw new IllegalArgumentException("Invalid report type");
			}

			// Check UKG API response.
			boolean apiSuccess = false;
			String apiMessage = null;
			if (apiResponse != null) {
				log.info("UKG API HTTP Status: {}. Request ID: {}", apiResponse.getStatusCode(), requestId);
				if (apiResponse.getBody() != null) {
					apiMessage = apiResponse.getBody().getMessage();
					System.out.println(apiMessage);
					log.info("UKG API Response Message: {}. Request ID: {}", apiMessage, requestId);
				}

				// HTTP 2xx + valid message
				if (apiResponse != null
				        && apiResponse.getStatusCode().is2xxSuccessful()
				        && apiResponse.getBody() != null) {

				    apiMessage = apiResponse.getBody().getMessage();

				    if (apiMessage != null && !apiMessage.trim().isEmpty()
				            && !"Records are not inserted in DB.".equalsIgnoreCase(apiMessage.trim())) {
				        apiSuccess = true;
				    }
				}
			}

			// Update report status.
			if (apiSuccess) {
				insertReportProcess(requestId, paramId, fromDate, toDate, 1);
				log.info("Report completed successfully. Request ID: {}", requestId);
			} else {
				insertReportProcess(requestId, paramId, fromDate, toDate, 0);
				log.error("Report failed. Request ID: {}, API Message: {}", requestId, apiMessage);
			}
			// Return existing DTO.
			return result;
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			insertReportProcess(requestId, paramId, fromDate, toDate, 0);
			log.error("Exception while generating report. Request ID: {}", requestId, e);
			throw new RuntimeException("Unable to Generate Report", e);
		}
	}

	@Override
	public void updateReportStatus(Long requestId, String status) {
		reportGenerateDao.updateReportStatus(requestId, status);
	}

	@Override
	public List<ReportGenerateDto> getReportSearchHistory(int requestedBy) {
		return reportGenerateDao.getReportSearchHistory(requestedBy);
	}

	@Override
	public String getReportName(String reportType) {
		return reportGenerateDao.getReportName(reportType);
	}

	@Override
	public List<ReportGenerateDto> getListOfReports() {
		return reportGenerateDao.getListOfReports();
	}

	public void insertReportProcess(Long requestId, Long paramId, String fromDate, String toDate, int status) {
		reportGenerateDao.insertReportProcess(requestId, paramId, fromDate, toDate, status);
	}

	private static final String ROOT_DIRECTORY = "D:/wfd_cwfm/generatedReport/";

	@Override
	public Path generateReportFile(Long requestId, int requestedBy) {
		try {

			// 1. Validate report request 
			  ReportGenerateDto report = reportGenerateDao.getReportForDownload(requestId, requestedBy);
			if (report == null) {
				log.warn("Report request not found. " + "Request ID: {}, User: {}", requestId, requestedBy);
				throw new IllegalArgumentException("Report request not found");
			}
			//2. Check report status 
			  if (!"SUCCESS".equalsIgnoreCase(report.getReportStatus())) {
				log.warn("Report is not ready for download. " + "Request ID: {}, Status: {}", requestId,
						report.getReportStatus());
				throw new IllegalStateException("Report is not ready for download");
			}
			// 3. Create user-specific folder *
			 Path rootPath = Paths.get(ROOT_DIRECTORY).toAbsolutePath().normalize();
			Path userFolder = rootPath.resolve(String.valueOf(requestedBy)).normalize();
			/* * Security check. */
			if (!userFolder.startsWith(rootPath)) {
				throw new IllegalStateException("Invalid report directory");
			}
			Files.createDirectories(userFolder);
			// 4. Check existing generated file *
			  String existingFileName = reportGenerateDao.getReportOutputFileName(requestId);
			if (existingFileName != null && !existingFileName.trim().isEmpty()) {
				 Path existingFile = userFolder.resolve(existingFileName).normalize();
				if (existingFile.startsWith(userFolder) && Files.exists(existingFile)
						&& Files.isRegularFile(existingFile)) {
					log.info("Existing report file found. " + "Request ID: {}, File: {}", requestId, existingFile);
					return existingFile;
				}
			}
			// 5. Get dynamic report data *
			  List<Map<String, Object>> reportData = reportGenerateDao.getReportData(requestId, requestedBy);
			if (reportData == null || reportData.isEmpty()) {
				log.warn("No report data available. " + "Request ID: {}", requestId);
				throw new IllegalStateException("No report data available");
			}
			// 6. Generate unique CSV filename *
			String reportName;
			String reportType = report.getReportType();
			if (reportType != null && !reportType.trim().isEmpty()) {
				reportName = reportType.trim();
			} else {
				reportName = "Report";
			}
			// Remove characters that are invalid in Windows filenames.
			reportName = reportName.replaceAll("[\\\\/:*?\"<>|]", "_");
			// Format dates for the filename.
			String fromDate = report.getFromDate();
			String toDate = report.getToDate();
			String fileName = reportName + "_" + fromDate + "_" + toDate + ".csv";
			// Resolve and validate the file path.
			Path filePath = userFolder.resolve(fileName).normalize();
			if (!filePath.startsWith(userFolder)) {
				throw new IllegalStateException("Invalid report file path");
			}
			// 7. Generate CSV 
			  writeCsvFile(filePath, reportData);
			// 8. Store generated filename in RPTPROCESS *
			  reportGenerateDao.updateReportOutputFileName(requestId, fileName);
			 log.info("Report CSV generated successfully. " + "Request ID: {}, User: {}, File: {}", requestId,
					requestedBy, filePath);
			// 9. Return file path 
			  return filePath;
		} catch (IllegalArgumentException | IllegalStateException e) {
			throw e;
		} catch (Exception e) {
			log.error("Error generating report file. " + "Request ID: {}, User: {}", requestId, requestedBy, e);
			throw new RuntimeException("Unable to generate report file", e);
		}
	}

	  // Generates CSV file from report data.
	private void writeCsvFile(Path filePath, List<Map<String, Object>> reportData) throws IOException {
		if (reportData == null || reportData.isEmpty()) {
			throw new IllegalArgumentException("Report data is empty");
		}
		  // Determine columns dynamically 
		  LinkedHashSet<String> columnSet = new LinkedHashSet<>();
		for (Map<String, Object> row : reportData) {
			if (row != null) {
				columnSet.addAll(row.keySet());
			}
		}
		List<String> columns = new ArrayList<>(columnSet);
		if (columns.isEmpty()) {
			throw new IllegalArgumentException("No report columns available");
		}
		   // Create CSV 
		  try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
	       // Header 
			 List<String> headers = new ArrayList<>(columns.size());
			for (String column : columns) {
				headers.add(escapeCsv(column));
			}
			writer.write(String.join(",", headers));
			writer.newLine();
			   //Data 
			  for (Map<String, Object> row : reportData) {
				List<String> values = new ArrayList<>(columns.size());
				for (String column : columns) {
					Object value = row.get(column);
					String stringValue = value == null ? "" : String.valueOf(value);
					values.add(escapeCsv(stringValue));
				}
				writer.write(String.join(",", values));
				writer.newLine();
			}
		}
	}

	private String escapeCsv(String value) {
		if (value == null) {
			return "";
		}
		String escaped = value.replace("\"", "\"\"");
		if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n") || escaped.contains("\r")) {
			return "\"" + escaped + "\"";
		}
		return escaped;
	}

	

}
