package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.dto.ComplianceAuditDto;

public interface ComplianceAuditDao {

	List<ComplianceAuditDto> searchComplianceAuditByAadhar(String aadharNumber);
}
