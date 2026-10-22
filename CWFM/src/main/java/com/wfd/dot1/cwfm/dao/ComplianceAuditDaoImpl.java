package com.wfd.dot1.cwfm.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.ComplianceAuditDto;

@Repository
public class ComplianceAuditDaoImpl implements ComplianceAuditDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<ComplianceAuditDto> searchComplianceAuditByAadhar(String aadharNumber) {

		String sql = "EXEC ComplianceAuditAadharHistory ?";

		RowMapper<ComplianceAuditDto> rowMapper = (rs, rowNum) -> {

			ComplianceAuditDto ca = new ComplianceAuditDto();

			ca.setGatePassId(rs.getString("GatePassId"));
			ca.setActionType(rs.getString("gatepassType"));
			ca.setDisability(rs.getString("Disability"));
			ca.setUanNumber(rs.getString("UANNumber"));
			ca.setPfNumber(rs.getString("PFNumber"));
			ca.setBankAccount(rs.getString("BankAccount"));
			ca.setIfsc(rs.getString("IFSC"));
			ca.setFromDate(rs.getString("FromDate"));
			ca.setToDate(rs.getString("ToDate"));

			return ca;
		};

		List<ComplianceAuditDto> result = jdbcTemplate.query(sql, rowMapper, aadharNumber);

		return result != null ? result : new ArrayList<>();
	}

}
