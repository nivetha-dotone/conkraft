package com.wfd.dot1.cwfm.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.RegistrationLink;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
@Repository
public class RegistrationLinkDaoImpl implements RegistrationLinkDao{

	   @Autowired
	    private JdbcTemplate jdbcTemplate;
	   
	   public String saveRegistrationLink() {
		    return QueryFileWatcher.getQuery("SAVE_REGISTRATION_LINK");
	    }
	@Override
	public Long saveRegistrationLink(RegistrationLink link, String tokenHash) {
		// TODO Auto-generated method stub
		Timestamp expiryTimestamp =
		        Timestamp.valueOf(LocalDateTime.parse(link.getExpiryDate()));

	        String sql = saveRegistrationLink();
//	            INSERT INTO dbo.WORKMAN_REGISTRATION_LINK
//	            (
//	                TokenHash,
//	                UnitId,
//	                ContractorId,
//	                WorkorderId,
//	                DepartmentId,
//	                AreaId,
//	                WcEsicNo,
//	                LlNo,
//	                CreatedBy,
//	                ExpiryDate,
//	                IsActive
//	            )
//	            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)
	            //""";

	        KeyHolder keyHolder = new GeneratedKeyHolder();

	        int rows = jdbcTemplate.update(connection -> {

	            PreparedStatement ps = connection.prepareStatement(
	                    sql, Statement.RETURN_GENERATED_KEYS);

	            ps.setString(1, tokenHash);
	            ps.setString(2, link.getUnitId());
	            ps.setString(3, link.getContractorId());
	            ps.setString(4, link.getWorkorderId());
	            ps.setString(5, link.getDepartmentId());
	            ps.setString(6, link.getAreaId());
	            ps.setString(7, link.getWcEsicNo());
	            ps.setString(8, link.getLlNo());
	            ps.setString(9, link.getCreatedBy());
	            ps.setTimestamp(10,
	            		expiryTimestamp);

	            return ps;

	        }, keyHolder);

	        if (rows != 1 || keyHolder.getKey() == null) {
	            throw new IllegalStateException(
	                    "Failed to create registration link");
	        }

	        return keyHolder.getKey().longValue();
	    }
	

}
