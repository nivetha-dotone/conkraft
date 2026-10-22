package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wfd.dot1.cwfm.dao.ComplianceAuditDao;
import com.wfd.dot1.cwfm.dto.ComplianceAuditDto;

@Service
public class ComplianceAuditServiceImpl implements ComplianceAuditService {
	@Autowired
	private  ComplianceAuditDao complianceAuditDao;

	@Override
	@Transactional(readOnly = true)
	public List<ComplianceAuditDto> searchComplianceAuditByAadhar(String aadharNumber) {

		if (aadharNumber == null || !aadharNumber.matches("\\d{12}")) {
			throw new IllegalArgumentException("Please enter a valid 12-digit Aadhaar number.");
		}

		return complianceAuditDao.searchComplianceAuditByAadhar(aadharNumber);
	}
}
