package com.wfd.dot1.cwfm.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.ActionAuditDto;
import com.wfd.dot1.cwfm.dto.CurrentEmploymentDto;
import com.wfd.dot1.cwfm.dto.PreviousEmploymentDto;
import com.wfd.dot1.cwfm.dto.WorkmanFullHistoryDTO;
import com.wfd.dot1.cwfm.dto.WorkmenSummaryDto;
@Repository
public class AadharSearchHistoryDaoImpl implements AadharSearchHistoryDao{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	
	@Override
    public WorkmanFullHistoryDTO getFullHistory(String aadhaar) {

        WorkmanFullHistoryDTO dto = new WorkmanFullHistoryDTO();

        List<WorkmenSummaryDto> summaryList = new ArrayList<>();
        List<CurrentEmploymentDto> currentList = new ArrayList<>();
        List<PreviousEmploymentDto> prevList = new ArrayList<>();
        List<ActionAuditDto> auditList = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             CallableStatement cs = con.prepareCall("{call sp_GetWorkmanFullHistory(?)}")) {

            cs.setString(1, aadhaar);
            boolean hasResult = cs.execute();

            int rsIndex = 0;

            while (hasResult) {

                ResultSet rs = cs.getResultSet();

                if (rsIndex == 0) {
                    while (rs.next()) {
                    	WorkmenSummaryDto w = new WorkmenSummaryDto();
                        w.setAadharNumber(rs.getString("AadharNumber"));
                        w.setFirstName(rs.getString("FirstName"));
                        w.setLastName(rs.getString("LastName"));
                        w.setDob(rs.getString("DOB"));
                        w.setGender(rs.getString("Gender"));
                        w.setMobileNumber(rs.getString("MobileNumber"));
                        w.setGatePassId(rs.getString("GatePassId"));
                        w.setGatePassStatus(rs.getString("GatePassStatus"));
                        w.setUpdatedBy(rs.getString("UpdatedBy"));
                        w.setUpdatedDate(rs.getString("UpdatedDate"));
                        summaryList.add(w);
                    }
                }

                if (rsIndex == 1) {
                    while (rs.next()) {
                    	CurrentEmploymentDto c = new CurrentEmploymentDto();
                        c.setPrincipalEmployer(rs.getString("PrincipalEmployer"));
                        c.setContractor(rs.getString("Contractor"));
                        c.setWorkorder(rs.getString("Workorder"));
                        c.setTrade(rs.getString("Trade"));
                        c.setSkill(rs.getString("Skill"));
                        c.setDepartment(rs.getString("Department"));
                        c.setArea(rs.getString("Area"));
                        c.setNatureOfJob(rs.getString("NatureOfJob"));
                        c.setAccessAreaId(rs.getString("AccessAreaId"));
                        c.setDoj(rs.getString("DOJ"));
                        c.setDot(rs.getString("DOT"));
                        currentList.add(c);
                    }
                }

                if (rsIndex == 2) {
                    while (rs.next()) {
                    	PreviousEmploymentDto p = new PreviousEmploymentDto();
                        p.setGatePassId(rs.getString("GatePassId"));
                        p.setGatePassType(rs.getString("GatePassType"));
                        p.setStatus(rs.getString("GatePassStatus"));
                        p.setPrincipalEmployer(rs.getString("PrincipalEmployer"));
                        p.setContractor(rs.getString("Contractor"));
                        p.setWorkorder(rs.getString("Workorder"));
                        p.setTrade(rs.getString("Trade"));
                        p.setSkill(rs.getString("Skill"));
                        p.setDepartment(rs.getString("Department"));
                        p.setArea(rs.getString("Area"));
                        p.setNatureOfJob(rs.getString("NatureOfJob"));
                        p.setAccessAreaId(rs.getString("AccessAreaId"));
                        p.setDoj(rs.getString("DOJ"));
                        p.setDot(rs.getString("DOT"));
                        prevList.add(p);
                    }
                }

                if (rsIndex == 3) {
                    while (rs.next()) {
                    	ActionAuditDto a = new ActionAuditDto();
                        a.setAadharNumber(rs.getString("AadharNumber"));
                        a.setGatePassId(rs.getString("GatePassId"));
                        a.setActionType(rs.getString("ActionType"));
                        a.setGatePassStatus(rs.getString("GatePassStatus"));
                        a.setRemarks(rs.getString("Remarks"));
                        a.setActionDate(rs.getString("ActionDate"));
                        a.setFromDate(rs.getString("FromDate"));
                        a.setToDate(rs.getString("ToDate"));
                        a.setSource(rs.getString("Source"));
                        auditList.add(a);
                    }
                }

                rsIndex++;
                hasResult = cs.getMoreResults();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        dto.setSummary(summaryList);
        dto.setCurrentEmployment(currentList);
        dto.setPreviousEmployment(prevList);
        dto.setAuditTrail(auditList);

        return dto;
    }
}


