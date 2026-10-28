
package com.wfd.dot1.cwfm.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SelfRegistrationReviewService {

    private final JdbcTemplate jdbc;
    private final ObjectMapper json = new ObjectMapper();

    public SelfRegistrationReviewService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // One authorized Principal Employer + Contractor combination
    public record AuthorizedPair(String unitId, String contractorId) {
    }

    // 1. LIST REGISTRATIONS
    // Controller must validate the requested PE + Contractor pair.
    public List<Map<String, Object>> list(
            String unitId, String contractorId) {

        String sql = """
            SELECT
                sr.RegistrationId,
                sr.FirstName,
                sr.LastName,
                sr.MobileNumber,
                RIGHT(sr.AadharNumber, 4) AS AadhaarLast4,
                sr.Status,
                rl.UnitId,
                rl.ContractorId,
                pe.NAME AS PrincipalEmployer,
                co.NAME AS ContractorName
            FROM dbo.WORKMAN_SELF_REGISTRATION sr
            INNER JOIN dbo.WORKMAN_REGISTRATION_LINK rl
                ON rl.LinkId = sr.LinkId
            INNER JOIN dbo.CMSPRINCIPALEMPLOYER pe
                ON pe.UNITID = rl.UnitId
            INNER JOIN dbo.CMSCONTRACTOR co
                ON co.CONTRACTORID = rl.ContractorId
            WHERE rl.UnitId = ?
              AND rl.ContractorId = ?
              AND sr.Status IN ('SUBMITTED', 'UNDER_REVIEW', 'REJECTED')
            ORDER BY sr.RegistrationId DESC
            """;

        return jdbc.queryForList(sql, unitId, contractorId);
    }

    // 2. FETCH REGISTRATION DETAILS INTERNALLY
    private Map<String, Object> fetchDetail(long registrationId) {

        String sql = """
            SELECT
                sr.RegistrationId,
                sr.LinkId,
                sr.FirstName,
                sr.LastName,
                sr.MobileNumber,
                sr.RegistrationData,
                sr.Status,
                rl.UnitId,
                rl.ContractorId,
                rl.WorkorderId,
                rl.DepartmentId,
                rl.AreaId,
                rl.EicId,
                rl.WcEsicNo,
                rl.LlNo,
                wc.LICENSE_NUMBER AS WcEsicName,
llwc.LICENSE_NUMBER AS LlName,sr.ReviewComments,
                pe.NAME AS PrincipalEmployer,
                co.NAME AS ContractorName,
                wo.SAP_WORKORDER_NUM AS WorkOrderNumber
            FROM dbo.WORKMAN_SELF_REGISTRATION sr
            INNER JOIN dbo.WORKMAN_REGISTRATION_LINK rl
                ON rl.LinkId = sr.LinkId
            INNER JOIN dbo.CMSPRINCIPALEMPLOYER pe
                ON pe.UNITID = rl.UnitId
            INNER JOIN dbo.CMSCONTRACTOR co
                ON co.CONTRACTORID = rl.ContractorId
            INNER JOIN dbo.CMSWORKORDER wo
                ON wo.WORKORDERID = rl.WorkorderId
                LEFT JOIN dbo.CMSWORKORDER_LLWC wc
    ON wc.WOLLID = rl.WcEsicNo

LEFT JOIN dbo.CMSWORKORDER_LLWC llwc
    ON llwc.WOLLID = rl.LlNo
            WHERE sr.RegistrationId = ?
            """;

        List<Map<String, Object>> rows =
                jdbc.queryForList(sql, registrationId);

        if (rows.isEmpty()) {
            throw new NoSuchElementException(
                    "Registration not found: " + registrationId);
        }

        return rows.get(0);
    }

    // 3. FETCH REGISTRATION WITH AUTHORIZATION
    public Map<String, Object> detail(
            long registrationId,
            List<AuthorizedPair> authorizedPairs) {

        Map<String, Object> registration =
                fetchDetail(registrationId);

        String unitId =
                String.valueOf(registration.get("UnitId"));

        String contractorId =
                String.valueOf(registration.get("ContractorId"));

        boolean allowed = authorizedPairs != null
                && authorizedPairs.stream().anyMatch(pair ->
                    pair != null
                    && unitId.equals(pair.unitId())
                    && contractorId.equals(pair.contractorId())
                );

        if (!allowed) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not authorized to view this registration");
        }

        return registration;
    }

    // 4. PARSE REGISTRATION DATA JSON
    public Map<String, String> fields(
            Map<String, Object> registration) {

        try {
            Object raw = registration.get("RegistrationData");

            if (raw == null) {
                return Collections.emptyMap();
            }

            return json.readValue(
                    raw.toString(),
                    new TypeReference<Map<String, String>>() {}
            );

        } catch (Exception ex) {
            throw new IllegalStateException(
                    "Invalid registration JSON", ex);
        }
    }

    // 5. FETCH DOCUMENT METADATA WITH AUTHORIZATION
    public List<Map<String, Object>> documents(
            long registrationId,
            List<AuthorizedPair> authorizedPairs) {

        // Ensure supervisor is authorized for this registration.
        detail(registrationId, authorizedPairs);

        String sql = """
            SELECT
                DocumentId,
                DocumentType,
                StoredFileName
            FROM dbo.WORKMAN_SELF_REG_DOCUMENT
            WHERE RegistrationId = ?
            ORDER BY DocumentId
            """;

        return jdbc.queryForList(sql, registrationId);
    }
    
 // Add inside your existing SelfRegistrationReviewService class:
    public Map<String, Object> documentForReview(long documentId, List<AuthorizedPair> authorizedPairs) {
        String sql = "SELECT DocumentId, RegistrationId, DocumentType, StoredFileName, StoragePath "
                + "FROM dbo.WORKMAN_SELF_REG_DOCUMENT WHERE DocumentId = ?";
        List<Map<String, Object>> rows = jdbc.queryForList(sql, documentId);
        if (rows.isEmpty()) throw new NoSuchElementException("Document not found");
        Map<String, Object> doc = rows.get(0);
        long registrationId = ((Number) doc.get("RegistrationId")).longValue();
        detail(registrationId, authorizedPairs); // checks registration's PE + contractor
        return doc;
    }
    
 // Add JdbcTemplate jdbc if not already present. Existing review service already uses it.
 // Imports: org.springframework.http.HttpStatus;
//           org.springframework.web.server.ResponseStatusException;
//           org.springframework.transaction.annotation.Transactional;

 @Transactional
 public void reject(long registrationId, String reason, String reviewedBy,
                    List<AuthorizedPair> pairs) {
     if (reason == null || reason.isBlank() || reason.trim().length() > 1000) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                 "Rejection reason is required (maximum 1000 characters).");
     }
     // detail() enforces registration-level PE/Contractor authorization.
     detail(registrationId, pairs);
     int updated = jdbc.update("""
             UPDATE dbo.WORKMAN_SELF_REGISTRATION
             SET Status = 'REJECTED', ReviewedBy = ?, ReviewComments = ?
             WHERE RegistrationId = ?
               AND Status IN ('SUBMITTED', 'UNDER_REVIEW')
               AND TransactionId IS NULL
             """, reviewedBy, reason.trim(), registrationId);
     if (updated != 1) {
         throw new ResponseStatusException(HttpStatus.CONFLICT,
                 "Registration has already been processed or cannot be rejected.");
     }
 }

}
