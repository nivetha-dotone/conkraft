package com.wfd.dot1.cwfm.service;

import java.util.List;

import com.wfd.dot1.cwfm.dto.ComplianceAuditDto;

public interface ComplianceAuditService {

	List<ComplianceAuditDto> searchComplianceAuditByAadhar(String aadharNumber);
}
