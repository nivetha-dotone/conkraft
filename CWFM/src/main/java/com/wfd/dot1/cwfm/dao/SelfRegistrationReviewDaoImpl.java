package com.wfd.dot1.cwfm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dao.SelfRegistrationReviewDao;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Repository
public class SelfRegistrationReviewDaoImpl implements SelfRegistrationReviewDao {

	private final JdbcTemplate jdbc;

	public SelfRegistrationReviewDaoImpl(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	public String fetchRegistrationList() {
		return QueryFileWatcher.getQuery("FETCH_REGISTRATION_LIST");
	}
	// 1. LIST REGISTRATIONS
	@Override
	public List<Map<String, Object>> list(String unitId, String contractorId) {

		String sql = fetchRegistrationList();
//				SELECT
//				    sr.RegistrationId,
//				    sr.FirstName,
//				    sr.LastName,
//				    sr.MobileNumber,
//				    RIGHT(sr.AadharNumber, 4) AS AadhaarLast4,
//				    sr.Status,
//				    rl.UnitId,
//				    rl.ContractorId,
//				    pe.NAME AS PrincipalEmployer,
//				    co.NAME AS ContractorName
//				FROM dbo.WORKMAN_SELF_REGISTRATION sr
//				INNER JOIN dbo.WORKMAN_REGISTRATION_LINK rl
//				    ON rl.LinkId = sr.LinkId
//				INNER JOIN dbo.CMSPRINCIPALEMPLOYER pe
//				    ON pe.UNITID = rl.UnitId
//				INNER JOIN dbo.CMSCONTRACTOR co
//				    ON co.CONTRACTORID = rl.ContractorId
//				WHERE rl.UnitId = ?
//				  AND rl.ContractorId = ?
//				  AND sr.Status IN ('SUBMITTED', 'UNDER_REVIEW', 'REJECTED')
//				ORDER BY sr.RegistrationId DESC
//				""";

		return jdbc.queryForList(sql, unitId, contractorId);
	}
	public String fetchRegistrationDetail() {
		return QueryFileWatcher.getQuery("FETCH_REGISTRATION_DETAILS");
	}
	// 2. FETCH REGISTRATION DETAILS
	@Override
	public Map<String, Object> fetchDetail(long registrationId) {

		String sql = fetchRegistrationDetail();
//				SELECT
//				    sr.RegistrationId,
//				    sr.LinkId,
//				    sr.FirstName,
//				    sr.LastName,
//				    sr.MobileNumber,
//				    sr.RegistrationData,
//				    sr.Status,
//				    rl.UnitId,
//				    rl.ContractorId,
//				    rl.WorkorderId,
//				    rl.DepartmentId,
//				    rl.AreaId,
//				    rl.EicId,
//				    rl.WcEsicNo,
//				    rl.LlNo,
//				    wc.LICENSE_NUMBER AS WcEsicName,
//				    llwc.LICENSE_NUMBER AS LlName,
//				    sr.ReviewComments,
//				    pe.NAME AS PrincipalEmployer,
//				    co.NAME AS ContractorName,
//				    wo.SAP_WORKORDER_NUM AS WorkOrderNumber
//				FROM dbo.WORKMAN_SELF_REGISTRATION sr
//				INNER JOIN dbo.WORKMAN_REGISTRATION_LINK rl
//				    ON rl.LinkId = sr.LinkId
//				INNER JOIN dbo.CMSPRINCIPALEMPLOYER pe
//				    ON pe.UNITID = rl.UnitId
//				INNER JOIN dbo.CMSCONTRACTOR co
//				    ON co.CONTRACTORID = rl.ContractorId
//				INNER JOIN dbo.CMSWORKORDER wo
//				    ON wo.WORKORDERID = rl.WorkorderId
//				LEFT JOIN dbo.CMSWORKORDER_LLWC wc
//				    ON wc.WOLLID = rl.WcEsicNo
//				LEFT JOIN dbo.CMSWORKORDER_LLWC llwc
//				    ON llwc.WOLLID = rl.LlNo
//				WHERE sr.RegistrationId = ?
				//""";

		List<Map<String, Object>> rows = jdbc.queryForList(sql, registrationId);

		if (rows.isEmpty()) {
			return null;
		}

		return rows.get(0);
	}
	public String fetchDocumentsData() {
		return QueryFileWatcher.getQuery("FETCH_DOCUMENTS_DATA");
	}
	// 3. FETCH DOCUMENT METADATA
	@Override
	public List<Map<String, Object>> documents(long registrationId) {

		String sql = fetchDocumentsData();
//				SELECT
//				    DocumentId,
//				    DocumentType,
//				    StoredFileName
//				FROM dbo.WORKMAN_SELF_REG_DOCUMENT
//				WHERE RegistrationId = ?
//				ORDER BY DocumentId
//				""";

		return jdbc.queryForList(sql, registrationId);
	}
	public String fetchdocumentForReview() {
		return QueryFileWatcher.getQuery("FETCH_DOCUMENTS_FOR_REVIEW");
	}
	// 4. FETCH DOCUMENT DETAILS FOR REVIEW
	@Override
	public Map<String, Object> documentForReview(long documentId) {

		String sql = fetchdocumentForReview();
//				SELECT
//				    DocumentId,
//				    RegistrationId,
//				    DocumentType,
//				    StoredFileName,
//				    StoragePath
//				FROM dbo.WORKMAN_SELF_REG_DOCUMENT
//				WHERE DocumentId = ?
//				""";

		List<Map<String, Object>> rows = jdbc.queryForList(sql, documentId);

		if (rows.isEmpty()) {
			return null;
		}

		return rows.get(0);
	}
	public String rejectRegistration() {
		return QueryFileWatcher.getQuery("REJECT_REGISTRATION");
	}
	// 5. REJECT REGISTRATION
	@Override
	public int reject(long registrationId, String reason, String reviewedBy) {

		String sql = rejectRegistration();
//				UPDATE dbo.WORKMAN_SELF_REGISTRATION
//				SET Status = 'REJECTED',
//				    ReviewedBy = ?,
//				    ReviewComments = ?
//				WHERE RegistrationId = ?
//				  AND Status IN ('SUBMITTED', 'UNDER_REVIEW')
//				  AND TransactionId IS NULL
//				""";

		return jdbc.update(sql, reviewedBy, reason, registrationId);
	}
}