package com.wfd.dot1.cwfm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.CMSContractorRegistrationLLWC;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Repository
public class ContractorRegistrationLLWCDAOImpl implements ContractorRegistrationLLWCDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getNextCONTRACTORREGLLWCID() {
	    return QueryFileWatcher.getQuery("GET_NEXT_CONTRACTORREGLLWCID");
	}
    public String getWOIDandWCCODEexists() {
	    return QueryFileWatcher.getQuery("GET_COUNT_WOID_WCCODE_CONTREGID");
	}
    public String insertLLWCRecords() {
	    return QueryFileWatcher.getQuery("INSERT_CONTRACTOR_REGISTRATION_LLWC");
	}
    
    @Override
    public int getNextId() {
    	String sql=getNextCONTRACTORREGLLWCID();
       // String sql = "SELECT ISNULL(MAX(CONTRACTORREGLLWCID), 0) + 1 FROM CMSContractorRegistrationLLWC";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public boolean exists(long regId, int woId, String wcCode) {
    	String sql=getWOIDandWCCODEexists();
        //String sql = "SELECT COUNT(*) FROM CMSContractorRegistrationLLWC " +
        //             "WHERE CONTRACTORREGID = ? AND WOID = ? AND WCCODE = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{regId, woId, wcCode}, Integer.class);
        return count != null && count > 0;
    }

    @Override
    public void insertLLWCRecords(List<CMSContractorRegistrationLLWC> records) {
    	String sql=insertLLWCRecords();
       // String sql = "INSERT INTO CMSContractorRegistrationLLWC (" +
        //        " CONTRACTORREGID, CONTRACTORID, UNITID, " +
        //        "WOID, LICENCETYPE, WONUMBER, WCCODE, STATUS, DELETESW, CREATEDDTM, CREATEDBY, UPDATEDDTM, UPDATEDBY" +
       //         ") VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                CMSContractorRegistrationLLWC r = records.get(i);
               
                ps.setLong(1, r.getContractorRegId());
                ps.setInt(2, r.getContractorId());
                ps.setInt(3, r.getUnitId());
                ps.setInt(4, r.getWorkOrderId());
                ps.setString(5, r.getLicenseType());
                ps.setString(6, r.getWorkOrderNumber());
                ps.setString(7, r.getWcCode());
                ps.setInt(8, 1); // STATUS
                ps.setInt(9, 0); // DELETESW
                ps.setTimestamp(10, r.getCreatedDtm());
                ps.setString(11, r.getCreatedBy());
                ps.setTimestamp(12, r.getUpdatedDtm());
                ps.setString(13, r.getUpdatedBy());
            }

            @Override
            public int getBatchSize() {
                return records.size();
            }
        });
    }
    
    
}

