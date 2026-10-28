package com.wfd.dot1.cwfm.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.RegistrationLink;
import com.wfd.dot1.cwfm.pojo.RegistrationLinkDetails;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
@Repository
public class RegistrationLinkDaoImpl implements RegistrationLinkDao{

	   @Autowired
	    private JdbcTemplate jdbcTemplate;
	   @Autowired
		 private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	   public String saveRegistrationLink() {
		    return QueryFileWatcher.getQuery("SAVE_REGISTRATION_LINK");
	    }
	@Override
	public Long saveRegistrationLink(RegistrationLink link, String tokenHash) {
		// TODO Auto-generated method stub
		Timestamp expiryTimestamp =
		        Timestamp.valueOf(LocalDateTime.parse(link.getExpiryDate()));

		String sql = """
			    INSERT INTO dbo.WORKMAN_REGISTRATION_LINK
			    (
			        TokenHash,
			        UnitId,
			        ContractorId,
			        WorkorderId,
			        DepartmentId,
			        AreaId,
			        EicId,
			        WcEsicNo,
			        LlNo,
			        CreatedBy,
			        ExpiryDate
			    )
			    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
			    """;

	        KeyHolder keyHolder = new GeneratedKeyHolder();

	        int rows = jdbcTemplate.update(connection -> {

	            PreparedStatement ps = connection.prepareStatement(
	                    sql,
	                    Statement.RETURN_GENERATED_KEYS
	            );

	            ps.setString(1, tokenHash);
	            ps.setString(2, link.getUnitId());
	            ps.setString(3, link.getContractorId());
	            ps.setString(4, link.getWorkorderId());
	            ps.setString(5, link.getDepartmentId());
	            ps.setString(6, link.getAreaId());
	            ps.setString(7, link.getEicId());
	            ps.setString(8, link.getWcEsicNo());
	            ps.setString(9, link.getLlNo());
	            ps.setString(10, link.getCreatedBy());
	            ps.setTimestamp(11,
	                    Timestamp.valueOf(LocalDateTime.parse(link.getExpiryDate())));

	            return ps;

	        }, keyHolder);

	        if (rows != 1 || keyHolder.getKey() == null) {
	            throw new IllegalStateException(
	                    "Failed to create registration link");
	        }

	        return keyHolder.getKey().longValue();
	    }
	@Override
	public RegistrationLink findValidLinkByTokenHash(String tokenHash) {

	    String sql = """
	        SELECT LinkId, UnitId, ContractorId, WorkorderId,
	               DepartmentId, AreaId, WcEsicNo, LlNo,EicId,
	               CreatedBy, CreatedDate, ExpiryDate, IsActive
	        FROM dbo.WORKMAN_REGISTRATION_LINK
	        WHERE TokenHash = ?
	          AND IsActive = 1
	          AND ExpiryDate > SYSDATETIME()
	        """;

	    List<RegistrationLink> links = jdbcTemplate.query(sql, (rs, rowNum) -> {

	        RegistrationLink link = new RegistrationLink();

	        link.setLinkId(rs.getLong("LinkId"));
	        link.setUnitId(rs.getString("UnitId"));
	        link.setContractorId(rs.getString("ContractorId"));
	        link.setWorkorderId(rs.getString("WorkorderId"));
	        link.setDepartmentId(rs.getString("DepartmentId"));
	        link.setAreaId(rs.getString("AreaId"));
	        link.setWcEsicNo(rs.getString("WcEsicNo"));
	        link.setLlNo(rs.getString("LlNo"));
	        link.setCreatedBy(rs.getString("CreatedBy"));
	        link.setActive(rs.getBoolean("IsActive"));
	        link.setEicId(rs.getString("EicId"));
	        // Your POJO currently uses String for dates.
	        Timestamp created = rs.getTimestamp("CreatedDate");
	        Timestamp expiry = rs.getTimestamp("ExpiryDate");

	        link.setCreatedDate(created != null
	                ? created.toLocalDateTime().toString() : null);

	        link.setExpiryDate(expiry != null
	                ? expiry.toLocalDateTime().toString() : null);

	        return link;
	    }, tokenHash);

	    return links.isEmpty() ? null : links.get(0);
	}
	
	@Override
	public RegistrationLinkDetails getRegistrationLinkDetails(Long linkId) {

	    String sql = """
	        SELECT
	            cpe.NAME AS peName,
	            cc.NAME AS contractorName,
	            wo.SAP_WORKORDER_NUM AS woNumber,
	            wc.LICENSE_NUMBER AS wcEsicNo,
	            llwc.LICENSE_NUMBER AS llNo
	        FROM dbo.WORKMAN_REGISTRATION_LINK link
	        INNER JOIN dbo.CMSPRINCIPALEMPLOYER cpe
	            ON cpe.UNITID = link.UnitId
	        INNER JOIN dbo.CMSCONTRACTOR cc
	            ON cc.CONTRACTORID = link.ContractorId
	        INNER JOIN dbo.CMSWORKORDER wo
	            ON wo.WORKORDERID = link.WorkorderId
	        LEFT JOIN dbo.CMSWORKORDER_LLWC wc
	            ON wc.WOLLID = link.WcEsicNo
	        LEFT JOIN dbo.CMSWORKORDER_LLWC llwc
	            ON llwc.WOLLID = link.LlNo
	        WHERE link.LinkId = ?
	        """;

	    List<RegistrationLinkDetails> details = jdbcTemplate.query(
	        sql,
	        (rs, rowNum) -> {

	            RegistrationLinkDetails dto = new RegistrationLinkDetails();

	            dto.setPeName(rs.getString("peName"));
	            dto.setContractorName(rs.getString("contractorName"));
	            dto.setWoNumber(rs.getString("woNumber"));
	            dto.setWcEsicNo(rs.getString("wcEsicNo"));
	            dto.setLlNo(rs.getString("llNo"));

	            return dto;
	        },
	        linkId
	    );

	    return details.isEmpty() ? null : details.get(0);
	}
	
	public List<Map<String, Object>> getSubmittedRegistrations(
	        List<String> authorizedUnitIds,
	        List<String> authorizedContractorIds) {

	    if (authorizedUnitIds == null || authorizedUnitIds.isEmpty()
	            || authorizedContractorIds == null || authorizedContractorIds.isEmpty()) {
	        return Collections.emptyList();
	    }

	    String sql = """
	        SELECT
	            sr.RegistrationId,
	            sr.LinkId,
	            sr.AadharNumber,
	            sr.FirstName,
	            sr.LastName,
	            sr.MobileNumber,
	            sr.Status,
	            sr.CreatedDate,
	            rl.UnitId,
	            rl.ContractorId,
	            cpe.NAME AS PrincipalEmployer,
	            cc.NAME AS ContractorName
	        FROM dbo.WORKMAN_SELF_REGISTRATION sr
	        INNER JOIN dbo.WORKMAN_REGISTRATION_LINK rl
	            ON rl.LinkId = sr.LinkId
	        INNER JOIN dbo.CMSPRINCIPALEMPLOYER cpe
	            ON cpe.UNITID = rl.UnitId
	        INNER JOIN dbo.CMSCONTRACTOR cc
	            ON cc.CONTRACTORID = rl.ContractorId
	        WHERE sr.Status = 'SUBMITTED'
	          AND rl.UnitId IN (:unitIds)
	          AND rl.ContractorId IN (:contractorIds)
	        ORDER BY sr.RegistrationId DESC
	        """;

	    MapSqlParameterSource params = new MapSqlParameterSource()
	            .addValue("unitIds", authorizedUnitIds)
	            .addValue("contractorIds", authorizedContractorIds);

	    return namedParameterJdbcTemplate.queryForList(sql, params);
	}

}
