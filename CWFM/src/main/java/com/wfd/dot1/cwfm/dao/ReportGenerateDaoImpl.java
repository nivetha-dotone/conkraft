package com.wfd.dot1.cwfm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wfd.dot1.cwfm.dto.ReportGenerateDto;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class ReportGenerateDaoImpl implements ReportGenerateDao {

	private static final Logger log = LoggerFactory.getLogger(ReportGenerateDaoImpl.class.getName());

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public String getListOfReportTypes() {
		return QueryFileWatcher.getQuery("GET_ALL_REPORTTYPES_FROM_REPORT_PREFERENCENS");
	}
	
	@Override
	public List<ReportGenerateDto> getListOfReports() {
		String sql =getListOfReportTypes();
		
//		String sql = "SELECT RPTREFID, RPTNAME " + "FROM REPORTPREFERENCES " + "WHERE ISACTIVE = 1 "
//				+ "ORDER BY RPTNAME";
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			ReportGenerateDto dto = new ReportGenerateDto();
			dto.setReportId(rs.getString("RPTREFID"));
			dto.setReportType(rs.getString("RPTNAME"));
			return dto;
		});
	}
	public String insertReportParameters() {
		return QueryFileWatcher.getQuery("INSERT_REPORTPARAMS_INTO_REPORTPARAMETER");
	}
	public String insertReportExecutionStatus() {
		return QueryFileWatcher.getQuery("INSERT_REPORTDATA_INTO_REPORTEXECUTIONSTATUS");
	}
	public String getActiveWorkmenListsForReports() {
		return QueryFileWatcher.getQuery("GET_ACTIVE_WORKMENLIST_FOR_REPORTS");
	}
	public String getMaxRequestId() {
		return QueryFileWatcher.getQuery("GET_MAX_REFERENCEID_WTKSESSION");
	}
	public String insertReportWtkSession() {
		return QueryFileWatcher.getQuery("INSERT_REPORTDATA_REPORTWTKSESSION");
	}
	@Override
	@Transactional
	public ReportGenerateDto getGatePassIds(String reportType, String unitId, String contractorId, String departmentId,
			String fromDate, String toDate, int requestedBy) {

		try {

			// 1. Insert REPORTPARAMETER
			String parameterSql =insertReportParameters();
//			String parameterSql = "INSERT INTO REPORTPARAMETER " + "(UNITID, CONTRACTORID, DEPARTMENTID, CREATEDDTM) "
//					+ "VALUES (?, ?, ?, GETDATE())";

			KeyHolder parameterKeyHolder = new GeneratedKeyHolder();

			jdbcTemplate.update(connection -> {

				PreparedStatement ps = connection.prepareStatement(parameterSql, Statement.RETURN_GENERATED_KEYS);

				ps.setString(1, unitId);
				ps.setString(2, contractorId);
				ps.setString(3, departmentId);

				return ps;

			}, parameterKeyHolder);

			Number generatedParameterKey = parameterKeyHolder.getKey();

			if (generatedParameterKey == null) {
				throw new SQLException("Unable to retrieve generated REPORTPARAMETERID");
			}

			Long reportParameterId = generatedParameterKey.longValue();

			log.info("REPORTPARAMETER saved successfully. REPORTPARAMETERID: {}", reportParameterId);

			// 2. Insert REPORTEXECUTIONSTATUS
			String reportExecutionSql =insertReportExecutionStatus();
//			String reportExecutionSql = "INSERT INTO REPORTEXECUTIONSTATUS "
//					+ "(RPTREFID, USERID, RPTFROMDATE, RPTTODATE, RPTPARAM, " + " RPTSTATUS, RPTREQUESTEDON) "
//					+ "VALUES (?, ?, ?, ?, ?, 'Y', GETDATE())";

			KeyHolder executionKeyHolder = new GeneratedKeyHolder();

			jdbcTemplate.update(connection -> {

				PreparedStatement ps = connection.prepareStatement(reportExecutionSql, Statement.RETURN_GENERATED_KEYS);

				ps.setString(1, reportType);
				ps.setInt(2, requestedBy);
				ps.setString(3, fromDate);
				ps.setString(4, toDate);
				ps.setLong(5, reportParameterId);

				return ps;

			}, executionKeyHolder);

			/*
			 * IMPORTANT: Get the key from executionKeyHolder, not parameterKeyHolder.
			 */
			Number executionGeneratedKey = executionKeyHolder.getKey();

			if (executionGeneratedKey == null) {
				throw new SQLException("Unable to retrieve generated REPORTEXECUTIONSTATUS ID");
			}

			Long reportExecutionStatusId = executionGeneratedKey.longValue();

			log.info("REPORTEXECUTIONSTATUS saved successfully. Request ID: {}", reportExecutionStatusId);

			// 3. Call stored procedure to get GatePass IDs
			String sql =getActiveWorkmenListsForReports();
//			String sql = """
//					EXEC usp_GetActiveGatePassDetailsToGenerateReport
//					     @UnitId = ?,
//					     @ContractorId = ?,
//					     @DepartmentId = ?,
//					     @FromDate = ?,
//					     @ToDate = ? """;

			List<String> gatePassIds = jdbcTemplate.query(sql, ps -> {
				ps.setString(1, unitId);
				ps.setString(2, contractorId);
				ps.setString(3, departmentId);
				ps.setString(4, fromDate);
				ps.setString(5, toDate);
			}, (rs, rowNum) -> rs.getString("GatePassId")).stream().filter(Objects::nonNull).map(String::trim)
					.filter(id -> !id.isEmpty()).distinct().toList();

			log.info("GatePass IDs fetched for Request ID {}: {}", reportExecutionStatusId, gatePassIds);

			//4. Insert GatePass IDs into REPORTWTKSESSIONID
			if (!gatePassIds.isEmpty()) {

				/*
				 * Get the next REFID.
				 *
				 * ISNULL is required when the table is empty.
				 */
				String refIdSql = getMaxRequestId();
//				String refIdSql = "SELECT ISNULL(MAX(REFID), 0) + 1 " + "FROM REPORTWTKSESSIONID";

				Long refId = jdbcTemplate.queryForObject(refIdSql, Long.class);

				if (refId == null) {
					throw new SQLException("Unable to generate REFID for REPORTWTKSESSIONID");
				}

				log.info("Generated REPORTWTKSESSIONID REFID: {} for Request ID: {}", refId, reportExecutionStatusId);

				/*
				 * Insert one row for every GatePass ID.
				 */
				String reportWtkSessionSql =insertReportWtkSession();
//				String reportWtkSessionSql = "INSERT INTO REPORTWTKSESSIONID "
//						+ "(REFID, PERSONREFID, FROMDATE, TODATE) " + "VALUES (?, ?, ?, ?)";

				int[][] rowsUpdated = jdbcTemplate.batchUpdate(reportWtkSessionSql, gatePassIds, gatePassIds.size(),
						(PreparedStatement ps, String gatePassId) -> {

							ps.setLong(1, refId);
							ps.setString(2, gatePassId);
							ps.setString(3, fromDate);
							ps.setString(4, toDate);
						});

				int totalInserted = 0;

				for (int[] batchResult : rowsUpdated) {
					for (int count : batchResult) {
						totalInserted += count;
					}
				}

				log.info("Inserted {} GatePass IDs into REPORTWTKSESSIONID. REFID: {}, Request ID: {}", totalInserted,
						refId, reportExecutionStatusId);

			} else {

				log.warn("No GatePass IDs found for Request ID: {}", reportExecutionStatusId);
			}

			// 5. Return Request ID + GatePass IDs
			ReportGenerateDto result = new ReportGenerateDto(reportExecutionStatusId, reportParameterId, gatePassIds);

			return result;

		} catch (Exception e) {

			log.error("Error while saving report parameters, execution status " + "and GatePass IDs", e);

			throw new RuntimeException("Error while saving report parameters and fetching GatePass IDs", e);
		}
	}
		@Override
	public void updateReportStatus(Long requestId, String status) {

		try {

			String sql = "UPDATE CMSREPORTSEARCHHISTORY " + "SET REPORT_STATUS = ?, " + "UPDATED_DTM = GETDATE() "
					+ "WHERE REPORT_TRANS_ID = ?";

			int rowsUpdated = jdbcTemplate.update(sql, status, requestId);

			if (rowsUpdated == 0) {
				log.warn("No report history record found for Request ID: {}", requestId);
			} else {
				log.info("Report status updated successfully. Request ID: {}, Status: {}", requestId, status);
			}

		} catch (Exception e) {

			log.error("Error while updating report status. Request ID: {}", requestId, e);

			throw new RuntimeException("Error while updating report status", e);
		}
	}
		
		public String getReportSearchHistory() {
			return QueryFileWatcher.getQuery("GET_REPORT_SEARCH_HISTORY_LIST");
		}
	@Override
	public List<ReportGenerateDto> getReportSearchHistory(int requestedBy) {

		try {

			String sql = getReportSearchHistory();
//					+ "select exe.RPEEXNSTSID AS REPORT_TRANS_ID,pre.RPTNAME AS REPORT_NAME,exe.RPTFROMDATE AS FROM_DATE,exe.RPTTODATE AS TO_DATE,\r\n"
//					+ "CASE WHEN pro.RPTSTATUS='1' THEN 'SUCESS' ELSE 'FAILED' END as REPORT_STATUS \r\n"
//					+ "from  RPTPROCESS pro\r\n"
//					+ "join REPORTEXECUTIONSTATUS exe on exe.RPEEXNSTSID=pro.RPEEXNSTSID\r\n"
//					+ "join REPORTPREFERENCES pre on pre.RPTREFID=exe.RPTREFID\r\n"
//					+ "where exe.USERID=? ORDER BY REPORT_TRANS_ID DESC";

			return jdbcTemplate.query(sql, ps -> ps.setInt(1, requestedBy), (rs, rowNum) -> {

				ReportGenerateDto dto = new ReportGenerateDto();

				dto.setRequestId(rs.getLong("REPORT_TRANS_ID"));

				dto.setReportType(rs.getString("REPORT_NAME"));

				dto.setFromDate(rs.getString("FROM_DATE"));

				dto.setToDate(rs.getString("TO_DATE"));

				dto.setReportStatus(rs.getString("REPORT_STATUS"));

				return dto;
			});

		} catch (Exception e) {

			log.error("Error while fetching report search history for user: {}", requestedBy, e);

			throw new RuntimeException("Error while fetching report search history", e);
		}
	}
	public String getReportName() {
		return QueryFileWatcher.getQuery("GET_REPORTNAME_BY_ID");
	}
	@Override
	public String getReportName(String reportType) {
		String sql =getReportName();
		//String sql = "SELECT RPTNAME FROM REPORTPREFERENCES where RPTREFID=?";
		try {
			return jdbcTemplate.queryForObject(sql, String.class, reportType);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	public String insertReportProcess() {
		return QueryFileWatcher.getQuery("INSERT_REPORTDATA_INTO_REPORTPROCESS");
	}

	@Override
	public void insertReportProcess(Long requestId, Long paramId, String fromDate, String toDate, int status) {
		String sql =insertReportProcess();
//		String sql = "INSERT INTO RPTPROCESS " + "(RPEEXNSTSID, RPTSTARTDATE, RPTENDDATE, "
//				+ "RPTSTATUS, RPTSEARCHPARAMATER, " + "RPTOUTPUTFILENM, ISEMAIL, EMAILID, UPDATEDTM) "
//				+ "VALUES (?, ?, ?, ?, ?, NULL, NULL, NULL, GETDATE())";

		jdbcTemplate.update(sql, requestId, fromDate, toDate, status, String.valueOf(paramId));

		log.info("RPTPROCESS inserted. Request ID: {}, Param ID: {}, Status: {}", requestId, paramId, status);
	}
	public String getReportForDownload() {
		return QueryFileWatcher.getQuery("GET_REPORTDATA_TO_CHECK_REPORT_STATUS");
	}
	@Override
	public ReportGenerateDto getReportForDownload(Long requestId, int requestedBy) {
		String sql =getReportForDownload();
//		String sql = "SELECT pro.RPEEXNSTSID,  cast(RPTSTARTDATE as date) as RPTSTARTDATE ,  cast(RPTENDDATE as date) as RPTENDDATE ,  pro.RPTSTATUS, \r\n"
//				+ "RPTOUTPUTFILENM,pre.RPTNAME FROM RPTPROCESS pro\r\n"
//				+ "join REPORTEXECUTIONSTATUS exe on exe.RPEEXNSTSID=pro.RPEEXNSTSID\r\n"
//				+ "join REPORTPREFERENCES pre on pre.RPTREFID=exe.RPTREFID\r\n"
//				+ "WHERE pro.RPEEXNSTSID =? ";
		List<ReportGenerateDto> result = jdbcTemplate.query(sql, new Object[] { requestId }, (rs, rowNum) -> {
			ReportGenerateDto dto = new ReportGenerateDto();
			dto.setRequestId(rs.getLong("RPEEXNSTSID"));
			dto.setFromDate(rs.getString("RPTSTARTDATE"));
			dto.setToDate(rs.getString("RPTENDDATE"));
			int status = rs.getInt("RPTSTATUS");
			String reportStatus;
			if (status == 1) {
				reportStatus = "SUCCESS";
			} else if (status == 0) {
				reportStatus = "FAILED";
			} else {
				reportStatus = String.valueOf(status);
			}
			dto.setReportStatus(reportStatus);
			dto.setReportStatus(reportStatus);
			dto.setOutPutFileName(rs.getString("RPTOUTPUTFILENM"));
			dto.setReportType(rs.getString("RPTNAME"));
			return dto;
		});
		return result.isEmpty() ? null : result.get(0);
	}
	public String getReportOutputFileName() {
		return QueryFileWatcher.getQuery("GET_REPORT_OUTPUT_FILENAME");
	}
	public String getReportOutputFileName(Long requestId) {
		String sql = getReportOutputFileName();
		//String sql = "SELECT RPTOUTPUTFILENM " + "FROM RPTPROCESS " + "WHERE RPEEXNSTSID = ?";
		List<String> result = jdbcTemplate.query(sql, ps -> ps.setLong(1, requestId),
				(rs, rowNum) -> rs.getString("RPTOUTPUTFILENM"));
		return result.isEmpty() ? null : result.get(0);
	}
	public String updateReportOutputFileName() {
		return QueryFileWatcher.getQuery("UPDATE_REPORT_OUTPUT_FILENAME");
	}
	@Override
	public void updateReportOutputFileName(Long requestId, String fileName) {
		String sql = updateReportOutputFileName();
//		String sql = "UPDATE RPTPROCESS " + "SET RPTOUTPUTFILENM = ?, " + "UPDATEDTM = GETDATE() "
//				+ "WHERE RPEEXNSTSID = ?";
		int updated = jdbcTemplate.update(sql, fileName, requestId);
		if (updated == 0) {
			log.warn("No RPTPROCESS record updated. Request ID: {}", requestId);
		}
	}
	public String getReportData() {
		return QueryFileWatcher.getQuery("GET_REPORT_DATA_TO_CSV_FILE");
	}
	@Override
	public List<Map<String, Object>> getReportData(Long requestId, int requestedBy) {
		String sql =getReportData();
//		String sql = "SELECT TOP 30 " + "TransactionId, " + "FirstName, " + "LastName, " + "DOB, " + "GatePassId "
//				+ "FROM GATEPASSMAIN " + "WHERE GatePassTypeId IN (1, 2, 12) " + "AND GatePassStatus = 4";

		return jdbcTemplate.queryForList(sql);
	}

	

}