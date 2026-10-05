package com.wfd.dot1.cwfm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.ReportGenerateDto;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
@Repository
public class ReportGenerateDaoImpl implements ReportGenerateDao{

	private static final Logger log = LoggerFactory.getLogger(ReportGenerateDaoImpl.class.getName());
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	 @Override
	 public ReportGenerateDto getGatePassIds(
	         String reportType,
	         String unitId,
	         String contractorId,
	         String departmentId,
	         String fromDate,
	         String toDate,
	         int requestedBy) {

	     try {

	         // 1. Save report search history and get generated REPORT_TRANS_ID
	         String insertSql =
	                 "INSERT INTO CMSREPORTSEARCHHISTORY "
	                 + "(REPORT_NAME, PLANT, CONTRACTOR, DEPARTMENT, "
	                 + "FROM_DATE, TO_DATE, REQUESTER_ID, REQUEST_DTM, "
	                 + "REPORT_STATUS, UPDATED_DTM) "
	                 + "VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE(), '', GETDATE())";

	         KeyHolder keyHolder = new GeneratedKeyHolder();

	         jdbcTemplate.update(connection -> {

	             PreparedStatement ps = connection.prepareStatement(
	                     insertSql,
	                     Statement.RETURN_GENERATED_KEYS
	             );

	             ps.setString(1, reportType);
	             ps.setString(2, unitId);
	             ps.setString(3, contractorId);
	             ps.setString(4, departmentId);
	             ps.setString(5, fromDate);
	             ps.setString(6, toDate);
	             ps.setInt(7, requestedBy);

	             return ps;

	         }, keyHolder);

	         Number generatedKey = keyHolder.getKey();

	         if (generatedKey == null) {
	             throw new SQLException(
	                     "Unable to retrieve generated REPORT_TRANS_ID"
	             );
	         }

	         Long requestId = generatedKey.longValue();

	         log.info("Report search history saved successfully. Request ID: {}",
	                 requestId);


	         // 2. Call stored procedure to get GatePass details
	         String sql = """
	                 EXEC usp_GetActiveGatePassDetailsToGenerateReport
	                      @UnitId = ?,
	                      @ContractorId = ?,
	                      @DepartmentId = ?,
	                      @FromDate = ?,
	                      @ToDate = ?
	                 """;


	         // 3. Get unique GatePass IDs
	         List<String> gatePassIds = jdbcTemplate.query(
	                 sql,
	                 ps -> {
	                     ps.setString(1, unitId);
	                     ps.setString(2, contractorId);
	                     ps.setString(3, departmentId);
	                     ps.setString(4, fromDate);
	                     ps.setString(5, toDate);
	                 },
	                 (rs, rowNum) -> rs.getString("GatePassId")
	         )
	         .stream()
	         .filter(Objects::nonNull)
	         .distinct()
	         .toList();


	         // 4. Return Request ID + GatePass IDs
	         return new ReportGenerateDto(
	                 requestId,
	                 gatePassIds
	         );

	     } catch (Exception e) {

	         log.error(
	                 "Error while saving report search history and fetching GatePass IDs",
	                 e
	         );

	         throw new RuntimeException(
	                 "Error while saving report search history and fetching GatePass IDs",
	                 e
	         );
	     }
	 }
}
