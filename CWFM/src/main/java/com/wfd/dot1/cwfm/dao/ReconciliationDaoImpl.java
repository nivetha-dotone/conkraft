package com.wfd.dot1.cwfm.dao;


import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dao.ReconciliationDao;
import com.wfd.dot1.cwfm.dto.ReconciliationMismatchDTO;
import com.wfd.dot1.cwfm.dto.WorkmenReconciliationDTO;

@Repository
public class ReconciliationDaoImpl implements ReconciliationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<WorkmenReconciliationDTO> getPfReconciliationList(Long contractorId) {

        String sql =
            "SELECT " +
            "   GPM.GatePassId AS gatePassId, " +
             "             CONCAT(COALESCE(GPM.FirstName, ''), ' ', COALESCE(GPM.LastName, '')) AS workmenName, \r\n"
            +"   ISNULL(GPM.UANNumber,'') AS uanNumber, " +
            "   ISNULL(GPM.PFNumber,'') AS pfNumber, " +
            "   '1000' AS pfAmount " +
            "FROM GATEPASSMAIN GPM " +
            "WHERE GPM.updatedBy = ? " +
            "  AND GPM.GatePassStatus IN (4) " +
            "  AND GPM.GatePassTypeId IN (1,2,12,15) " +
            "ORDER BY GPM.GatePassId DESC";

        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(WorkmenReconciliationDTO.class),
                contractorId);
    }

    @Override
    public List<WorkmenReconciliationDTO> getEsicReconciliationList(Long contractorId) {


        String sql =
            "SELECT " +
            "   GPM.GatePassId AS gatePassId, " +
            "             CONCAT(COALESCE(GPM.FirstName, ''), ' ', COALESCE(GPM.LastName, '')) AS workmenName, \r\n"+
            "   ISNULL(GPM.ESICNumber,'') AS esicNumber, " +
            "   '2000' AS esicAmount " +
            "FROM GATEPASSMAIN GPM " +
            "WHERE GPM.updatedBy = ? " +
            "  AND GPM.GatePassStatus IN (4) " +
            "  AND GPM.GatePassTypeId IN (1,2,12,15) " +
            "ORDER BY GPM.GatePassId DESC";

        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(WorkmenReconciliationDTO.class),
                contractorId);
    }

    @Override
    public Long saveUploadMaster(Long contractorId, String reconType, String fileName, String filePath,
                                 String overallStatus, int totalCount, int verifiedCount, int unverifiedCount,
                                 String uploadedBy) {

        String sql = "INSERT INTO RECONCILIATION_UPLOAD " +
                     "(CONTRACTOR_ID, RECON_TYPE, FILE_NAME, FILE_PATH, OVERALL_STATUS, TOTAL_COUNT, VERIFIED_COUNT, UNVERIFIED_COUNT, UPLOADED_BY, UPLOADED_DATE) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";

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
            ps.setString(9, uploadedBy);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void saveMismatchList(Long uploadId, List<ReconciliationMismatchDTO> mismatchList) {

        String sql = "INSERT INTO RECONCILIATION_MISMATCH " +
                     "(UPLOAD_ID, GATEPASS_ID, WORKMEN_NAME, DB_NUMBER, DOC_NUMBER, DB_AMOUNT, DOC_AMOUNT, MISMATCH_REASON, RECON_TYPE, CREATED_DATE) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";

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
}