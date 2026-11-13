package com.wfd.dot1.cwfm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.BankWageDetailDTO;
import com.wfd.dot1.cwfm.dto.BankWageExpectedDTO;
import com.wfd.dot1.cwfm.dto.BankWageUploadDTO;
import com.wfd.dot1.cwfm.dto.ReconciliationComplianceDetailDTO;
import com.wfd.dot1.cwfm.dto.ReconciliationHistoryDTO;
import com.wfd.dot1.cwfm.dto.ReconciliationMismatchDTO;
import com.wfd.dot1.cwfm.dto.WorkmenReconciliationDTO;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Repository
public class ReconciliationDaoImpl implements ReconciliationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    
    private String getPfQuery() {
    	// TODO Auto-generated method stub
    	return QueryFileWatcher.getQuery("PF_CHALLAN");
    }
    
    private String getEsicQuery() {
    	// TODO Auto-generated method stub
    	return QueryFileWatcher.getQuery("ESIC_CHALLAN");
    }
    
    @Override
    public List<WorkmenReconciliationDTO> getPfReconciliationList(Long contractorId,String fromDate, String toDate) {

    	 String sql =getPfQuery();
    	            

        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(WorkmenReconciliationDTO.class),
                contractorId,fromDate,toDate);
    }

    @Override
    public List<WorkmenReconciliationDTO> getEsicReconciliationList(Long contractorId,String fromDate, String toDate) {

    	 String sql =getEsicQuery();
    	        

        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(WorkmenReconciliationDTO.class),
                contractorId,fromDate,toDate);
    }
    public String saveUploadMaster() {
		return QueryFileWatcher.getQuery("SAVE_RECONCILEIATION_MASTER");
	}
    @Override
    public Long saveUploadMaster(Long contractorId, String reconType, String fileName, String filePath,
                                 String overallStatus, int totalCount, int verifiedCount, int unverifiedCount,
                                 String fromDate,String toDate,
                                 String uploadedBy) {
    	String sql =saveUploadMaster();
//        String sql = "INSERT INTO RECONCILIATION_UPLOAD " +
//                     "(CONTRACTOR_ID, RECON_TYPE, FILE_NAME, FILE_PATH, OVERALL_STATUS, TOTAL_COUNT, VERIFIED_COUNT, UNVERIFIED_COUNT, UPLOADED_BY, UPLOADED_DATE) " +
//                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, contractorId);
            ps.setString(2, reconType);
            ps.setString(3, fileName);
            ps.setString(4, filePath);
            ps.setString(5, overallStatus);
            ps.setInt(6, totalCount);
            ps.setInt(7, verifiedCount);
            ps.setInt(8, unverifiedCount);
            ps.setString(9, fromDate);
            ps.setString(10, toDate);
            ps.setString(11, uploadedBy);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
    public String saveMismatchList() {
		return QueryFileWatcher.getQuery("SAVE_MISMATCH_RECONCILEIATION_MASTER");
	}
    @Override
    public void saveMismatchList(Long uploadId, List<ReconciliationMismatchDTO> mismatchList) {
    	 String sql =saveMismatchList();
//        String sql = "INSERT INTO RECONCILIATION_MISMATCH " +
//                     "(UPLOAD_ID, GATEPASS_ID, WORKMEN_NAME, DB_NUMBER, DOC_NUMBER, DB_AMOUNT, DOC_AMOUNT, MISMATCH_REASON, RECON_TYPE, CREATED_DATE) " +
//                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws java.sql.SQLException {
                ReconciliationMismatchDTO dto = mismatchList.get(i);
                ps.setLong(1, uploadId);
                ps.setString(2, dto.getGatePassId());
                ps.setString(3, dto.getWorkmenName());
                ps.setString(4, dto.getDbNumber());
                ps.setString(5, dto.getDocNumber());
                ps.setBigDecimal(6, dto.getDbAmount());
                ps.setBigDecimal(7, dto.getDocAmount());
                ps.setString(8, dto.getMismatchReason());
                ps.setString(9, dto.getReconType());
            }

            @Override
            public int getBatchSize() {
                return mismatchList.size();
            }
        });
    }
    

private String getBankWageExpectedQuery() {
    return QueryFileWatcher.getQuery("BANK_WAGE_EXPECTED");
}




@Override
public List<String> findExistingBankUtrs(
        List<String> utrNumbers) {

    if (utrNumbers == null || utrNumbers.isEmpty()) {
        return Collections.emptyList();
    }

    String placeholders = String.join(",",
            Collections.nCopies(utrNumbers.size(), "?"));

    String sql = "SELECT UTRNumber "
            + "FROM BANK_WAGE_RECON_DETAIL "
            + "WHERE UTRNumber IN (" + placeholders + ")";

    return jdbcTemplate.queryForList(
            sql, String.class, utrNumbers.toArray());
}

@Override
public Long saveBankUpload(BankWageUploadDTO upload) {

    String sql = QueryFileWatcher.getQuery(
            "SAVE_BANK_WAGE_UPLOAD");

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {

        PreparedStatement ps =
                connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS);

        ps.setLong(1, upload.getPrincipalEmployerId());
        ps.setLong(2, upload.getContractorId());
        ps.setDate(3, java.sql.Date.valueOf(
                upload.getWageMonth()));
        ps.setString(4, upload.getFileName());
        ps.setString(5, upload.getFilePath());
        ps.setInt(6, upload.getTotalWorkmen());
        ps.setBigDecimal(7, upload.getTotalExpectedAmount());
        ps.setBigDecimal(8, upload.getTotalPaidAmount());
        ps.setBigDecimal(9, upload.getTotalShortfall());
        ps.setBigDecimal(10, upload.getContractorRating());
        ps.setString(11, upload.getUploadedBy());
        ps.setString(12, upload.getStatus());

        return ps;

    }, keyHolder);

    if (keyHolder.getKey() == null) {
        throw new IllegalStateException(
                "Bank wage upload ID was not generated.");
    }

    return keyHolder.getKey().longValue();
}

@Override
public void saveBankDetails(
        Long uploadId,
        List<BankWageDetailDTO> details) {

    String sql = QueryFileWatcher.getQuery(
            "SAVE_BANK_WAGE_DETAILS");

    jdbcTemplate.batchUpdate(
            sql, new BatchPreparedStatementSetter() {

        @Override
        public void setValues(
                PreparedStatement ps, int i)
                throws SQLException {

            BankWageDetailDTO dto = details.get(i);

            ps.setLong(1, uploadId);
            ps.setString(2, dto.getGatePassId());
            ps.setString(3, dto.getWorkmenName());
            ps.setString(4, dto.getAccountNumber());
            ps.setString(5, dto.getIfscCode());
            ps.setBigDecimal(6, dto.getExpectedAmount());
            ps.setBigDecimal(7, dto.getPaidAmount());
            ps.setBigDecimal(8, dto.getDifferenceAmount());
            ps.setBigDecimal(9, dto.getVariancePercentage());
            ps.setBigDecimal(10, dto.getPaymentPercentage());
            ps.setInt(11, dto.getRating());
            ps.setString(12, dto.getRatingDescription());
            ps.setDate(13, java.sql.Date.valueOf(
                    dto.getTransactionDate()));
            ps.setDate(14, java.sql.Date.valueOf(
                    dto.getDueDate()));
            ps.setString(15, dto.getUtrNumber());
            ps.setString(16, dto.getAmountStatus());
            ps.setString(17, dto.getTimelinessStatus());
        }

        @Override
        public int getBatchSize() {
            return details.size();
        }
    });
}

@Override
public boolean existsBankWageReconciliation(
        Long contractorId,
        YearMonth wageMonth) {

    String sql = QueryFileWatcher.getQuery(
            "CHECK_BANK_WAGE_RECON_EXISTS");

    Integer count = jdbcTemplate.queryForObject(
            sql,
            Integer.class,
            contractorId,
            java.sql.Date.valueOf(wageMonth.atDay(1)));

    return count != null && count > 0;
}



@Override
public boolean isValidBankWagePeContractor(
        Long principalEmployerId,
        Long contractorId) {

    String sql = QueryFileWatcher.getQuery(
            "VALIDATE_BANK_WAGE_PE_CONTRACTOR");

    Integer count = jdbcTemplate.queryForObject(
            sql,
            Integer.class,
            principalEmployerId,
            contractorId);

    return count != null && count > 0;
}


@Override
public List<BankWageExpectedDTO> getExpectedBankWages(
        Long principalEmployerId,
        Long contractorId,
        String fromDate,
        String toDate) {

    String sql = QueryFileWatcher.getQuery(
            "BANK_WAGE_EXPECTED");

    return jdbcTemplate.query(
            sql,
            new BeanPropertyRowMapper<>(
                    BankWageExpectedDTO.class),
            principalEmployerId,
            contractorId,
            fromDate,
            toDate);
}

@Override
public List<Map<String, Object>> findInvalidBankWageRecords(
        Long principalEmployerId,
        Long contractorId,
        String fromDate,
        String toDate) {

    String sql = QueryFileWatcher.getQuery(
            "BANK_WAGE_EXPECTED_VALIDATION");

    return jdbcTemplate.queryForList(
            sql,
            contractorId,
            fromDate,
            toDate,
            principalEmployerId,
            contractorId,
            principalEmployerId,
            contractorId);
}


@Override
public List<BankWageDetailDTO> getBankWageDetails(Long uploadId) {

    String sql =
        "SELECT " +
        "GatePassId AS gatePassId, " +
        "WorkmenName AS workmenName, " +
        "AccountNumber AS accountNumber, " +
        "IFSCCode AS ifscCode, " +
        "ExpectedAmount AS expectedAmount, " +
        "PaidAmount AS paidAmount, " +
        "DifferenceAmount AS differenceAmount, " +
        "VariancePercentage AS variancePercentage, " +
        "PaymentPercentage AS paymentPercentage, " +
        "Rating AS rating, " +
        "RatingDescription AS ratingDescription, " +
        "TransactionDate AS transactionDate, " +
        "DueDate AS dueDate, " +
        "UTRNumber AS utrNumber, " +
        "AmountStatus AS amountStatus, " +
        "TimelinessStatus AS timelinessStatus " +
        "FROM BANK_WAGE_RECON_DETAIL " +
        "WHERE UploadId = ? " +
        "ORDER BY GatePassId";

    return jdbcTemplate.query(
        sql,
        new BeanPropertyRowMapper<>(BankWageDetailDTO.class),
        uploadId
    );
}



@Override
public void saveComplianceDetails(
        Long uploadId,
        List<ReconciliationComplianceDetailDTO> details) {

    if (details == null || details.isEmpty()) {
        return;
    }

    String sql = """
        INSERT INTO RECONCILIATION_COMPLIANCE_DETAIL
        (
            UploadId,
            GatePassId,
            WorkmenName,
            ReconType,
            ExpectedAmount,
            ChallanAmount,
            DifferenceAmount,
            VariancePercentage,
            PaymentPercentage,
            Rating,
            RatingDescription,
            ComplianceStatus,
            MismatchReason
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    jdbcTemplate.batchUpdate(sql,
            new BatchPreparedStatementSetter() {

        @Override
        public void setValues(
                PreparedStatement ps, int i)
                throws SQLException {

            ReconciliationComplianceDetailDTO dto =
                    details.get(i);

            ps.setLong(1, uploadId);
            ps.setString(2, dto.getGatePassId());
            ps.setString(3, dto.getWorkmenName());
            ps.setString(4, dto.getReconType());

            ps.setBigDecimal(5, dto.getExpectedAmount());
            ps.setBigDecimal(6, dto.getChallanAmount());
            ps.setBigDecimal(7, dto.getDifferenceAmount());
            ps.setBigDecimal(8, dto.getVariancePercentage());
            ps.setBigDecimal(9, dto.getPaymentPercentage());

            ps.setInt(10, dto.getRating());
            ps.setString(11, dto.getRatingDescription());
            ps.setString(12, dto.getComplianceStatus());
            ps.setString(13, dto.getMismatchReason());
        }

        @Override
        public int getBatchSize() {
            return details.size();
        }
    });
}

@Override
public List<ReconciliationHistoryDTO> getReconciliationHistory(
        List<Long> contractorIds) {

    if (contractorIds == null || contractorIds.isEmpty()) {
        return Collections.emptyList();
    }

    String sql = QueryFileWatcher.getQuery(
            "RECONCILIATION_HISTORY_LIST");

    MapSqlParameterSource params =
            new MapSqlParameterSource()
                    .addValue("contractorIds", contractorIds);

    return namedParameterJdbcTemplate.query(
            sql,
            params,
            new BeanPropertyRowMapper<>(
                    ReconciliationHistoryDTO.class));
}

@Override
public List<ReconciliationComplianceDetailDTO>
        getSavedComplianceDetails(Long uploadId) {

    String sql = QueryFileWatcher.getQuery(
            "RECONCILIATION_SAVED_COMPLIANCE_DETAILS");

    return jdbcTemplate.query(
            sql,
            new BeanPropertyRowMapper<>(
                    ReconciliationComplianceDetailDTO.class),
            uploadId);
}

@Override
public List<ReconciliationMismatchDTO>
        getSavedMismatchDetails(Long uploadId) {

    String sql = QueryFileWatcher.getQuery(
            "RECONCILIATION_SAVED_MISMATCH_DETAILS");

    return jdbcTemplate.query(
            sql,
            new BeanPropertyRowMapper<>(
                    ReconciliationMismatchDTO.class),
            uploadId);
}



@Override
public boolean hasReconciliationUploadAccess(
        Long uploadId,
        String reconType,
        List<Long> contractorIds) {

    if (uploadId == null || reconType == null
            || contractorIds == null
            || contractorIds.isEmpty()) {
        return false;
    }

    String type = reconType.trim()
            .toUpperCase(Locale.ROOT);

    if (!Arrays.asList("BANK", "PF", "ESIC")
            .contains(type)) {
        return false;
    }

    String sql = QueryFileWatcher.getQuery(
            "RECONCILIATION_VALIDATE_UPLOAD_ACCESS");

    MapSqlParameterSource params =
            new MapSqlParameterSource()
                    .addValue("uploadId", uploadId)
                    .addValue("reconType", type)
                    .addValue("contractorIds", contractorIds);

    Integer count = namedParameterJdbcTemplate
            .queryForObject(sql, params, Integer.class);

    return count != null && count > 0;
}


}