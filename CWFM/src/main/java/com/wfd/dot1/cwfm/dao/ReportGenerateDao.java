package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.dto.ReportGenerateDto;

public interface ReportGenerateDao {

	ReportGenerateDto getGatePassIds(String reportType,String unitId, String contractorId, String departmentId, String fromDate,
			String toDate, int requestedBy);

}
