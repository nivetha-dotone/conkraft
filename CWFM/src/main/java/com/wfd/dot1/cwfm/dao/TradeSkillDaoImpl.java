package com.wfd.dot1.cwfm.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.CertificationDTO;
import com.wfd.dot1.cwfm.dto.GatePassTradeSkillDTO;
import com.wfd.dot1.cwfm.dto.TradeSkillDTO;
import com.wfd.dot1.cwfm.dto.TradeSkillListingDto;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
@Repository
public class TradeSkillDaoImpl implements TradeSkillDao{

	private static final Logger log = LoggerFactory.getLogger(TradeSkillDaoImpl.class.getName());
	
	@Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	
	 
	 public String geWorkmenListBasedOnPEQuery() {
		    return QueryFileWatcher.getQuery("GET_ALL_WM_BY_PE");
		}
	 
	@Override
	public List<TradeSkillListingDto> geWorkmenListBasedOnPE(String unitId) {
		log.info("Entering into geWorkmenListBasedOnPE dao method ");
		List<TradeSkillListingDto> listDto= new ArrayList<TradeSkillListingDto>();
		String query = geWorkmenListBasedOnPEQuery();
		log.info("Query to geWorkmenListBasedOnPE "+query);

		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId);
		while(rs.next()) {
			TradeSkillListingDto dto = new TradeSkillListingDto();
			dto.setGatePassId(rs.getString("GatePassId"));
			dto.setUnitId(rs.getString("UnitId"));
			dto.setUnitName(rs.getString("peName"));
			dto.setContactorId(rs.getString("ContractorId"));
			dto.setContractorName(rs.getString("contractorName"));
			dto.setContractorCode(rs.getString("contractorCode"));
			dto.setName(rs.getString("FullName"));
			dto.setAadharNumber(rs.getString("AadharNumber"));
			listDto.add(dto);
		}
		log.info("Exiting from geWorkmenListBasedOnPE dao method "+listDto.size());
		return listDto;
		
	}

	 public String getAllGeneralMaster() {
		    return QueryFileWatcher.getQuery("GET_ALL_TRADE_CMSGENERALMASTER");
		}
	@Override
	public List<CmsGeneralMaster> getAllTradeSkillBasedOnPe(String unitId) {
		log.info("Entering into getAllGeneralMasters dao method ");
		List<CmsGeneralMaster> gmList= new ArrayList<CmsGeneralMaster>();
		String query = getAllGeneralMaster();
		log.info("Query to getAllGeneralMasters "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId);
		while(rs.next()) {
			CmsGeneralMaster gm = new CmsGeneralMaster();
			gm.setGmId(rs.getString("GMID"));
			gm.setGmName(rs.getString("GMNAME"));
			gm.setGmType(rs.getString("GMTYPE"));
			gmList.add(gm);
		}
		log.info("Exiting from getAllGeneralMasters dao method "+gmList.size());
		return gmList;
	}
	 public String getAllProLevelQuery() {
		    return QueryFileWatcher.getQuery("GET_ALL_PROFICIENCY_LEVEL");
		}
	@Override
	public List<CmsGeneralMaster> getAllProLevel() {
		log.info("Entering into getAllGeneralMasters dao method ");
		List<CmsGeneralMaster> gmList= new ArrayList<CmsGeneralMaster>();
		String query = getAllProLevelQuery();
		log.info("Query to getAllGeneralMasters "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		while(rs.next()) {
			CmsGeneralMaster gm = new CmsGeneralMaster();
			gm.setGmId(rs.getString("GMID"));
			gm.setGmName(rs.getString("GMNAME"));
			gm.setGmType(rs.getString("GMTYPE"));
			gmList.add(gm);
		}
		log.info("Exiting from getAllGeneralMasters dao method "+gmList.size());
		return gmList;
	}

	@Override
	public void deleteByGatePass(String gatePassId) {

	    String sql = "UPDATE GatePassTradeSkillMapping " +
	                 "SET IsActive = 0 WHERE GatePassId = ?";

	    jdbcTemplate.update(sql, gatePassId);
	}


	@Override
	public void batchInsert(GatePassTradeSkillDTO dto, String user) {

	    String sql = """
	        MERGE GatePassTradeSkillMapping AS target
	        USING (SELECT ? AS GatePassId,
	                      ? AS TradeId,
	                      ? AS SkillId,
	                      ? AS ProficiencyId,
	                      ? AS UpdatedBy) AS source
	        ON target.GatePassId = source.GatePassId
	           AND target.TradeId = source.TradeId
	           AND target.SkillId = source.SkillId
	           AND target.IsActive = 1

	        WHEN MATCHED THEN
	            UPDATE SET
	                target.ProficiencyId = source.ProficiencyId,
	                target.UpdatedBy = source.UpdatedBy,
	                target.UpdatedDate = GETDATE()

	        WHEN NOT MATCHED THEN
	            INSERT (GatePassId, TradeId, SkillId, ProficiencyId, UpdatedBy, UpdatedDate, IsActive)
	            VALUES (source.GatePassId, source.TradeId, source.SkillId,
	                    source.ProficiencyId, source.UpdatedBy, GETDATE(), 1);
	        """;

	    jdbcTemplate.batchUpdate(sql,
	        dto.getTradeSkills(),
	        dto.getTradeSkills().size(),
	        (ps, ts) -> {
	            ps.setString(1, dto.getGatePassId());
	            ps.setString(2, ts.getTradeId());
	            ps.setString(3, ts.getSkillId());
	            ps.setString(4, ts.getProficiencyId());
	            ps.setString(5, user);
	        }
	    );
	}

	
	 public String getAllTradeSkillProLevelViewQuery() {
		    return QueryFileWatcher.getQuery("GET_ALL_TRADE_SKILL_PRO_VIEW");
		}

	@Override
	public List<TradeSkillDTO> viewTradeSkill(String gatePassId) {

//	    String sql = "SELECT TradeId, SkillId, ProficiencyId " +
//	                 "FROM GatePassTradeSkillMapping " +
//	                 "WHERE GatePassId = ? AND IsActive = 1";
	    String sql =getAllTradeSkillProLevelViewQuery();

	    return jdbcTemplate.query(sql, new Object[]{gatePassId},
	        (rs, rowNum) -> {
	            TradeSkillDTO dto = new TradeSkillDTO();
	            dto.setTradeId(rs.getString("TradeId"));
	            dto.setSkillId(rs.getString("SkillId"));
	            dto.setProficiencyId(rs.getString("ProficiencyId"));
	            return dto;
	        });
	}

	@Override
	public void deleteCertification(String gatePassId) {

	    String sql = "UPDATE GatePassCertificationMapping " +
	                 "SET IsActive = 0 WHERE GatePassId = ?";

	    jdbcTemplate.update(sql, gatePassId);
	}


	@Override
	public void batchInsertCertification(
	        GatePassTradeSkillDTO dto,
	        String user) {

	    String sql = """
	        MERGE GatePassCertificationMapping AS target
	        USING (SELECT ? AS GatePassId,
	                      ? AS CertificationId,
	                      ? AS ProficiencyId,
	                      ? AS GrantDate,
	                      ? AS ExpiryDate,
	                      ? AS UpdatedBy) AS source
	        ON target.GatePassId = source.GatePassId
	           AND target.CertificationId = source.CertificationId
	           AND target.IsActive = 1

	        WHEN MATCHED THEN
	            UPDATE SET
	                target.ProficiencyId = source.ProficiencyId,
	                target.GrantDate = source.GrantDate,
	                target.ExpiryDate = source.ExpiryDate,
	                target.UpdatedBy = source.UpdatedBy,
	                target.UpdatedDate = GETDATE()

	        WHEN NOT MATCHED THEN
	            INSERT (GatePassId, CertificationId, ProficiencyId,
	                    GrantDate, ExpiryDate,
	                    UpdatedBy, UpdatedDate, IsActive)
	            VALUES (source.GatePassId, source.CertificationId,
	                    source.ProficiencyId,
	                    source.GrantDate, source.ExpiryDate,
	                    source.UpdatedBy, GETDATE(), 1);
	        """;

	    jdbcTemplate.batchUpdate(
	        sql,
	        dto.getCertifications(),
	        dto.getCertifications().size(),
	        (ps, c) -> {
	            ps.setString(1, dto.getGatePassId());
	            ps.setString(2, c.getCertificationId());
	            ps.setString(3, c.getProficiencyId());
	            ps.setString(4, c.getGrantDate());
	            ps.setString(5, c.getExpiryDate());
	            ps.setString(6, user);
	        });
	}

	 public String getCertificationDetailsQuery() {
		    return QueryFileWatcher.getQuery("GET_ALL_CERTIFICATION");
		}
	@Override
	public List<CertificationDTO> getCertification(String gatePassId) {

	    String sql =getCertificationDetailsQuery();

	    return jdbcTemplate.query(sql,
	        new Object[]{gatePassId},
	        (rs, rowNum) -> {

	            CertificationDTO dto =
	                new CertificationDTO();

	            dto.setCertificationId(
	                rs.getString("CertificationId"));
	            dto.setProficiencyId(
	                rs.getString("ProficiencyId"));
	            dto.setGrantDate(
	                rs.getString("GrantDate"));
	            dto.setExpiryDate(
	                rs.getString("ExpiryDate"));

	            return dto;
	        });
	}

	 public String getAllCertQuery() {
		    return QueryFileWatcher.getQuery("GET_ALL_CERT");
		}
	@Override
	public List<CmsGeneralMaster> getAllCert() {
		log.info("Entering into getAllGeneralMasters dao method ");
		List<CmsGeneralMaster> gmList= new ArrayList<CmsGeneralMaster>();
		String query = getAllCertQuery();
		log.info("Query to getAllGeneralMasters "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		while(rs.next()) {
			CmsGeneralMaster gm = new CmsGeneralMaster();
			gm.setGmId(rs.getString("GMID"));
			gm.setGmName(rs.getString("GMNAME"));
			gm.setGmType(rs.getString("GMTYPE"));
			gmList.add(gm);
		}
		log.info("Exiting from getAllGeneralMasters dao method "+gmList.size());
		return gmList;
	}



	@Override
	public List<TradeSkillDTO> viewExistingTradeSkill(String gatePassId) {

	    String sql =
	        "SELECT " +
	        " t.GMID AS TradeId, t.GMNAME AS TradeName, " +
	        " s.GMID AS SkillId, s.GMNAME AS SkillName, " +
	        " p.GMID AS ProficiencyId, p.GMNAME AS ProficiencyName " +
	        "FROM GatePassTradeSkillMapping g " +
	        "JOIN CMSGENERALMASTER t ON t.GMID = g.TradeId " +
	        "JOIN CMSGENERALMASTER s ON s.GMID = g.SkillId " +
	        "JOIN CMSGENERALMASTER p ON p.GMID = g.ProficiencyId " +
	        "WHERE g.GatePassId = ? AND g.IsActive = 1";

	    return jdbcTemplate.query(sql,
	        new Object[]{gatePassId},
	        (rs, rowNum) -> {

	            TradeSkillDTO dto = new TradeSkillDTO();

	            dto.setTradeId(rs.getString("TradeId"));
	            dto.setTradeName(rs.getString("TradeName"));

	            dto.setSkillId(rs.getString("SkillId"));
	            dto.setSkillName(rs.getString("SkillName"));

	            dto.setProficiencyId(rs.getString("ProficiencyId"));
	            dto.setProficiencyName(rs.getString("ProficiencyName"));

	            return dto;
	        });
	}
	
	@Override
	public List<CertificationDTO> viewCertification(String gatePassId) {

	    String sql =
	        "SELECT " +
	        " c.GMID AS CertificationId, c.GMNAME AS CertificationName, " +
	        " p.GMID AS ProficiencyId, p.GMNAME AS ProficiencyName, " +
	        " g.GrantDate, g.ExpiryDate " +
	        "FROM GatePassCertificationMapping g " +
	        "JOIN CMSGENERALMASTER c ON c.GMID = g.CertificationId " +
	        "JOIN CMSGENERALMASTER p ON p.GMID = g.ProficiencyId " +
	        "WHERE g.GatePassId = ? AND g.IsActive = 1";

	    return jdbcTemplate.query(sql,
	        new Object[]{gatePassId},
	        (rs, rowNum) -> {

	            CertificationDTO dto = new CertificationDTO();

	            dto.setCertificationId(rs.getString("CertificationId"));
	            dto.setCertificationName(rs.getString("CertificationName"));

	            dto.setProficiencyId(rs.getString("ProficiencyId"));
	            dto.setProficiencyName(rs.getString("ProficiencyName"));

	            dto.setGrantDate(rs.getString("GrantDate"));
	            dto.setExpiryDate(rs.getString("ExpiryDate"));

	            return dto;
	        });
	}




}
