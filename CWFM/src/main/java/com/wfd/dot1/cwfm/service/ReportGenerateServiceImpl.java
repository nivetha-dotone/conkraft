package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.ReportGenerateDao;
import com.wfd.dot1.cwfm.dao.WorkmenDao;
import com.wfd.dot1.cwfm.dto.ReportGenerateDto;

@Service
public class ReportGenerateServiceImpl implements ReportGenerateService{

	@Autowired 
	WorkmenDao workmenDao;
	
	@Autowired 
	ReportGenerateDao reportGenerateDao;

	@Override
	public ReportGenerateDto getGatePassIds(String reportType,String unitId, String contractorId, String departmentId, String fromDate,String toDate,int requestedBy) {
		 return reportGenerateDao.getGatePassIds(reportType,unitId,contractorId,departmentId,fromDate,toDate,requestedBy);
	}
	
}
