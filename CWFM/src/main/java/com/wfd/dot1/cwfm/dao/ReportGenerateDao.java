package com.wfd.dot1.cwfm.dao;

import java.util.List;
import java.util.Map;

import com.wfd.dot1.cwfm.dto.ReportGenerateDto;

public interface ReportGenerateDao {

	ReportGenerateDto getGatePassIds(String reportType, String unitId, String contractorId, String departmentId,
			String fromDate, String toDate, int requestedBy);

	void updateReportStatus(Long requestId, String status);

	List<ReportGenerateDto> getReportSearchHistory(int requestedBy);

	String getReportName(String reportType);

	List<ReportGenerateDto> getListOfReports();

	void insertReportProcess(Long requestId, Long paramId, String fromDate, String toDate, int status);

	ReportGenerateDto getReportForDownload(Long requestId, int requestedBy);


	String getReportOutputFileName(Long requestId);

	void updateReportOutputFileName(Long requestId, String fileName);

	List<Map<String, Object>> getReportData(Long requestId, int requestedBy);


}
