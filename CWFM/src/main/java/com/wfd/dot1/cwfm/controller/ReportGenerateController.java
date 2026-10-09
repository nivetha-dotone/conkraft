package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.wfd.dot1.cwfm.dto.FetchTotalRequestDto;
import com.wfd.dot1.cwfm.dto.FetchTotalResponseDto;
import com.wfd.dot1.cwfm.dto.ReportGenerateDto;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.ReportGenerateService;
import com.wfd.dot1.cwfm.service.WorkmenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Controller
@RequestMapping("/reportGenerate")
public class ReportGenerateController {
	private static final Logger log = LoggerFactory.getLogger(ReportGenerateController.class.getName());

	@Autowired
	private WorkmenService workmenService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private ReportGenerateService reportGenerateService;

	@Autowired
	CreateEmpFetchByGatePassAPICALL api;

	@GetMapping("/reportGenerateList")
	public String list(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false);
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
		List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMaster();

		Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
				.collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
		Map<String, List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
				.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
		List<PersonOrgLevel> principalEmployerList = groupedByLevelDef.getOrDefault("Principal Employer",
				new ArrayList<>());
		request.setAttribute("PrincipalEmployer", principalEmployerList);
		List<PersonOrgLevel> contractorList = groupedByLevelDef.getOrDefault("Contractor", new ArrayList<>());
		request.setAttribute("Contractors", contractorList);

		// Grouping the CmsGeneralMaster objects by gmType

//		// Define the types and their corresponding request attribute names
//		Map<String, String> attributeMapping = Map.of("REPORTTYPE", "ReportType");
//
//		// Iterate over the attribute mappings and set the request attributes dynamically
//		attributeMapping.forEach((type, attributeName) -> {
//		    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
//		    request.setAttribute(attributeName, gmList1);
//		});
		List<ReportGenerateDto> reportTypes = reportGenerateService.getListOfReports();
		request.setAttribute("ReportTypes", reportTypes);
		return "reportGenerate/generateReports";
	}

	@PostMapping("/getGatePassIds")
	@ResponseBody
	public ResponseEntity<?> getGatePassIds(@RequestParam("reportType") String reportType,
			@RequestParam("unitId") String unitId, @RequestParam("contractorId") String contractorId,
			@RequestParam("departmentId") String departmentId, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
			if (user == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session expired");
			}

			int requestedBy = user.getUserId();
			ReportGenerateDto result = reportGenerateService.getGatePassIds(reportType, unitId, contractorId,
					departmentId, fromDate, toDate, requestedBy);
			if (result == null) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to Generate Report");
			}

			log.info("Report Request ID: {}", result.getRequestId());
			log.info("Report Type: {}", reportType);
			log.info("GatePass IDs: {}", result.getGatePassIds());

			return ResponseEntity.ok(result);
		} catch (IllegalArgumentException e) {
			log.error("Invalid report request", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			log.error("Unable to Generate Report", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to Generate Report");
		}
	}

	@GetMapping("/getReportSearchHistory")
	@ResponseBody
	public ResponseEntity<?> getReportSearchHistory(HttpServletRequest request) {

		try {

			HttpSession session = request.getSession(false);
			MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
			if (user == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session expired");
			}
			int requestedBy = user.getUserId();

			List<ReportGenerateDto> history = reportGenerateService.getReportSearchHistory(requestedBy);
			log.info("Report search history count: {}", history != null ? history.size() : 0);
			return ResponseEntity.ok(history);

		} catch (Exception e) {
			log.error("Error while fetching report search history", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Unable to fetch report search history");
		}
	}

	@PostMapping("/downloadReport")
	public ResponseEntity<Resource> downloadReport(@RequestParam("requestId") Long requestId,
			HttpServletRequest request) {

		try {
			HttpSession session = request.getSession(false);

			if (session == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}

			MasterUser user = (MasterUser) session.getAttribute("loginuser");

			if (user == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}

			int requestedBy = user.getUserId();

			Path filePath = reportGenerateService.generateReportFile(requestId, requestedBy);

			if (filePath == null || !Files.exists(filePath) || !Files.isRegularFile(filePath)) {

				return ResponseEntity.notFound().build();
			}

			Resource resource = new FileSystemResource(filePath.toFile());

			String fileName = filePath.getFileName().toString();

			return ResponseEntity.ok().contentType(MediaType.parseMediaType("text/csv"))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
					.contentLength(Files.size(filePath)).body(resource);

		} catch (IllegalArgumentException e) {

			log.warn("Invalid report download request. Request ID: {}", requestId, e);

			return ResponseEntity.badRequest().build();

		} catch (IllegalStateException e) {

			log.warn("Report is not ready. Request ID: {}", requestId, e);

			return ResponseEntity.status(HttpStatus.CONFLICT).build();

		} catch (Exception e) {

			log.error("Error downloading report. Request ID: {}", requestId, e);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("/viewReport")
	public ModelAndView viewReport(@RequestParam("requestId") Long requestId, HttpServletRequest request) {

		HttpSession session = request.getSession(false);

		if (session == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session expired");
		}

		MasterUser user = (MasterUser) session.getAttribute("loginuser");

		if (user == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
		}

		int requestedBy = user.getUserId();

		// Reuse your existing service. CSV saving remains unchanged.
		Path filePath = reportGenerateService.generateReportFile(requestId, requestedBy);

		if (filePath == null || !Files.exists(filePath) || !Files.isRegularFile(filePath)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Report file not found");
		}

		try {
			List<Map<String, Object>> reportData = readCsvFile(filePath);

			ModelAndView modelAndView = new ModelAndView("reportGenerate/reportViewer");

			modelAndView.addObject("reportData", reportData);
			modelAndView.addObject("reportTitle", filePath.getFileName().toString());

			return modelAndView;

		} catch (IOException e) {
			log.error("Error reading report CSV: {}", filePath, e);

			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to display report");
		}
	}

	private List<Map<String, Object>> readCsvFile(Path filePath) throws IOException {

		List<Map<String, Object>> rows = new ArrayList<>();

		try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {

			String headerLine = reader.readLine();

			if (headerLine == null || headerLine.trim().isEmpty()) {
				return rows;
			}

			List<String> headers = parseCsvLine(headerLine);

			String line;

			while ((line = reader.readLine()) != null) {
				List<String> values = parseCsvLine(line);

				Map<String, Object> row = new LinkedHashMap<>();

				for (int i = 0; i < headers.size(); i++) {
					row.put(headers.get(i), i < values.size() ? values.get(i) : "");
				}

				rows.add(row);
			}
		}

		return rows;
	}

	private List<String> parseCsvLine(String line) {

		List<String> values = new ArrayList<>();
		StringBuilder value = new StringBuilder();
		boolean insideQuotes = false;

		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);

			if (c == '"') {
				if (insideQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {

					value.append('"');
					i++;

				} else {
					insideQuotes = !insideQuotes;
				}

			} else if (c == ',' && !insideQuotes) {
				values.add(value.toString());
				value.setLength(0);

			} else {
				value.append(c);
			}
		}

		values.add(value.toString());

		return values;
	}

}
