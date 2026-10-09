package com.wfd.dot1.cwfm.service;

import java.nio.file.Path;
import java.util.List;

import com.wfd.dot1.cwfm.dto.ReportGenerateDto;

public interface ReportGenerateService {

	ReportGenerateDto  getGatePassIds(String reportType,String unitId, String contractorId, String departmentId, String fromDate, String toDate, int requestedBy);

	void updateReportStatus(Long requestId, String status);
	
	List<ReportGenerateDto> getReportSearchHistory(int requestedBy);
	

	String getReportName(String reportType);

	List<ReportGenerateDto> getListOfReports();

	Path generateReportFile(Long requestId, int userId);
}
