package com.wfd.dot1.cwfm.dao;


import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.ActiveContractorDTO;
import com.wfd.dot1.cwfm.dto.ChatBotVideoDTO;
import com.wfd.dot1.cwfm.dto.ContractorDTO;
import com.wfd.dot1.cwfm.dto.LicenseExpiryDTO;
import com.wfd.dot1.cwfm.dto.PrincipalEmployerDTO;
import com.wfd.dot1.cwfm.dto.WorkOrderDTO;
import com.wfd.dot1.cwfm.enums.GatePassStatus;
import com.wfd.dot1.cwfm.enums.GatePassType;
import com.wfd.dot1.cwfm.pojo.GatePassMain;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;


@Repository
public class ChatBotDAOImpl implements ChatBotDAO {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ChatBotDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
	 private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer getPendingApprovalCount(
            List<PersonOrgLevel> peList,
            List<PersonOrgLevel> contList) {

        try {

            if (peList == null || peList.isEmpty()
                    || contList == null || contList.isEmpty()) {
                return 0;
            }

            
            
            String peIds = peList == null ? "" :
                peList.stream()
                        .map(PersonOrgLevel::getId)
                        .filter(id -> id != null && !id.trim().isEmpty())
                        .collect(Collectors.joining(","));

        String contIds = contList == null ? "" :
                contList.stream()
                        .map(PersonOrgLevel::getId)
                        .filter(id -> id != null && !id.trim().isEmpty())
                        .collect(Collectors.joining(","));

            if (peIds.isEmpty() || contIds.isEmpty()) {
                return 0;
            }

            String sql =
                    "SELECT COUNT(*) " +
                    "FROM GATEPASSMAIN " +
                    "WHERE UNITID IN (:peIds) " +
                    "AND CONTRACTORID IN (:contIds) " +
                    "AND GATEPASSSTATUS = :status";

            MapSqlParameterSource parameters =
                    new MapSqlParameterSource();

            parameters.addValue("peIds", peIds);
            parameters.addValue("contIds", contIds);
            parameters.addValue("status", 3);

            Integer count = namedParameterJdbcTemplate.queryForObject(
                    sql,
                    parameters,
                    Integer.class
            );

            return count != null ? count : 0;

        } catch (Exception e) {

            LOGGER.error(
                    "Error getting Pending Approval Count",
                    e
            );

            return 0;
        }
    }

    @Override
    public List<ActiveContractorDTO> getActiveContractors(List<PersonOrgLevel> peList) {

        try {

            if (peList == null || peList.isEmpty()) {
                return Collections.emptyList();
            }

            List<Long> peIds = peList.stream()
                    .map(p -> Long.valueOf(p.getId()))
                    .collect(Collectors.toList());

            String sql =
                    "SELECT DISTINCT " +
                    "mm.CONTRACTORID, " +
                    "cc.NAME AS CONTRACTORNAME " +
                    "FROM CMSCONTRPEMM mm " +
                    "INNER JOIN CMSCONTRACTOR cc " +
                    "ON cc.CONTRACTORID = mm.CONTRACTORID " +
                    "WHERE mm.UNITID IN (:peIds) " +
                    "AND GETDATE() BETWEEN mm.VALIDFROMDT AND mm.VALIDTODT " +
                    "ORDER BY cc.NAME";

            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("peIds", peIds);

            return namedParameterJdbcTemplate.query(
                    sql,
                    parameters,
                    (rs, rowNum) -> {

                        ActiveContractorDTO dto = new ActiveContractorDTO();

                        dto.setContractorId(rs.getLong("CONTRACTORID"));
                        dto.setContractorName(rs.getString("CONTRACTORNAME"));

                        return dto;
                    });

        } catch (Exception e) {

            LOGGER.error("Error getting Active Contractors", e);
            return Collections.emptyList();
        }
    }
    @Override
    public Integer getTodayGatePassCount(String peId) {

        try {

            String sql = "";

            return jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{peId},
                    Integer.class);

        } catch (Exception e) {

            LOGGER.error("Error getting Gatepass Count", e);

            return 0;

        }

    }

    @Override
    public Integer getWorkOrderCount(String peId) {

        try {

            String sql = "";

            return jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{peId},
                    Integer.class);

        } catch (Exception e) {

            LOGGER.error("Error getting WorkOrder Count", e);

            return 0;

        }

    }

    @Override
    public Integer getLicenseExpiryCount(String peId) {

        try {

            String sql = "";

            return jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{peId},
                    Integer.class);

        } catch (Exception e) {

            LOGGER.error("Error getting License Expiry Count", e);

            return 0;

        }

    }


    @Override
    public ContractorDTO searchContractor(String contractorName) {

        try {

            String sql = "";

            return jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{contractorName},
                    new BeanPropertyRowMapper<>(ContractorDTO.class));

        } catch (EmptyResultDataAccessException e) {

            return null;

        } catch (Exception e) {

            LOGGER.error("Error searching Contractor", e);

            return null;

        }

    }
    @Override
    public List<GatePassMain> getCreatePendingApprovals(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList){

        try {
        	 if ((peList == null || peList.isEmpty()) && (contList == null || contList.isEmpty())) {
                 return Collections.emptyList();
             }

        	 List<Long> peIds = peList == null ? Collections.emptyList(): peList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                         .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
            
        	 List<Long> contIds = contList == null ? Collections.emptyList(): contList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                         .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
        	 
        	 if (peIds.isEmpty() || contIds.isEmpty()) {
                 return Collections.emptyList();
             }

            String sql ="select gpm.TransactionId,gpm.AadharNumber as aadhar,CASE WHEN gpm.GatePassId = '' THEN '-' ELSE gpm.GatePassId END AS GatePassId, gpm.GatePassStatus\r\n"
            		+ "from GATEPASSMAIN gpm where GatePassStatus=3 and OnboardingType='regular' and gpm.UnitId IN (:peIds) and gpm.ContractorId in (:contIds) ORDER BY gpm.TransactionId DESC";


            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("peIds", peIds);
            parameters.addValue("contIds", contIds);
            return namedParameterJdbcTemplate.query(
                    sql,
                    parameters,
                    (rs, rowNum) -> {

                    	GatePassMain dto = new GatePassMain();

                        dto.setTransactionId(rs.getString("TransactionId"));
                        dto.setAadhaarNumber(rs.getString("aadhar"));
                        dto.setGatePassId(rs.getString("GatePassId"));
                        String gatePassStatus = rs.getString("GatePassStatus");

            	        if (GatePassStatus.APPROVALPENDING.getStatus().equals(gatePassStatus)) {
            	            dto.setGatePassStatus("Pending");
            	        }else {
                            dto.setGatePassStatus(gatePassStatus);
                        }
                        return dto;
                    });

        } catch (Exception e) {

            LOGGER.error("Error getting Create Pending Approvals", e);
            return Collections.emptyList();
        }
    }
    @Override
    public List<GatePassMain> getQuickPendingApprovals(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList){

        try {
        	 if ((peList == null || peList.isEmpty()) && (contList == null || contList.isEmpty())) {
                 return Collections.emptyList();
             }

        	 List<Long> peIds = peList == null ? Collections.emptyList(): peList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                         .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
            
        	 List<Long> contIds = contList == null ? Collections.emptyList(): contList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                         .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
        	 
        	 if (peIds.isEmpty() || contIds.isEmpty()) {
                 return Collections.emptyList();
             }

            String sql ="select gpm.TransactionId,gpm.AadharNumber as aadhar,CASE WHEN gpm.GatePassId = '' THEN '-' ELSE gpm.GatePassId END AS GatePassId, gpm.GatePassStatus\r\n"
            		+ "from GATEPASSMAIN gpm where GatePassStatus=3 and OnboardingType='quick' and gpm.UnitId IN (:peIds) and gpm.ContractorId in (:contIds) ORDER BY gpm.TransactionId DESC";


            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("peIds", peIds);
            parameters.addValue("contIds", contIds);
            return namedParameterJdbcTemplate.query(
                    sql,
                    parameters,
                    (rs, rowNum) -> {

                    	GatePassMain dto = new GatePassMain();

                        dto.setTransactionId(rs.getString("TransactionId"));
                        dto.setAadhaarNumber(rs.getString("aadhar"));
                        dto.setGatePassId(rs.getString("GatePassId"));
                        String gatePassStatus = rs.getString("GatePassStatus");

            	        if (GatePassStatus.APPROVALPENDING.getStatus().equals(gatePassStatus)) {
            	            dto.setGatePassStatus("Pending");
            	        }else {
                            dto.setGatePassStatus(gatePassStatus);
                        }
                        return dto;
                    });

        } catch (Exception e) {

            LOGGER.error("Error getting Quick Pending Approvals", e);
            return Collections.emptyList();
        }
    }
    @Override
    public List<GatePassMain>  getProjectPendingApprovals(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList){

    	try {
       	 if ((peList == null || peList.isEmpty()) && (contList == null || contList.isEmpty())) {
                return Collections.emptyList();
            }

       	 List<Long> peIds = peList == null ? Collections.emptyList(): peList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
           
       	 List<Long> contIds = contList == null ? Collections.emptyList(): contList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
       	 
       	 if (peIds.isEmpty() || contIds.isEmpty()) {
                return Collections.emptyList();
            }

           String sql ="select gpm.TransactionId,gpm.AadharNumber as aadhar,CASE WHEN gpm.GatePassId = '' THEN '-' ELSE gpm.GatePassId END AS GatePassId, gpm.GatePassStatus\r\n"
           		+ "from GATEPASSMAIN gpm where GatePassStatus=3 and GatePassTypeId=12 and gpm.UnitId IN (:peIds) and gpm.ContractorId in (:contIds) ORDER BY gpm.TransactionId DESC";


           MapSqlParameterSource parameters = new MapSqlParameterSource();
           parameters.addValue("peIds", peIds);
           parameters.addValue("contIds", contIds);
           return namedParameterJdbcTemplate.query(
                   sql,
                   parameters,
                   (rs, rowNum) -> {

                   	GatePassMain dto = new GatePassMain();

                       dto.setTransactionId(rs.getString("TransactionId"));
                       dto.setAadhaarNumber(rs.getString("aadhar"));
                       dto.setGatePassId(rs.getString("GatePassId"));
                       String gatePassStatus = rs.getString("GatePassStatus");

           	        if (GatePassStatus.APPROVALPENDING.getStatus().equals(gatePassStatus)) {
           	            dto.setGatePassStatus("Pending");
           	        }else {
                           dto.setGatePassStatus(gatePassStatus);
                       }
                       return dto;
                   });

       } catch (Exception e) {

           LOGGER.error("Error getting project Pending Approvals", e);
           return Collections.emptyList();
       }
    }
    @Override
    public List<GatePassMain>  getBlockPendingApprovals(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList){

    	try {
       	 if ((peList == null || peList.isEmpty()) && (contList == null || contList.isEmpty())) {
                return Collections.emptyList();
            }

       	 List<Long> peIds = peList == null ? Collections.emptyList(): peList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
           
       	 List<Long> contIds = contList == null ? Collections.emptyList(): contList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
       	 
       	 if (peIds.isEmpty() || contIds.isEmpty()) {
                return Collections.emptyList();
            }

           String sql ="select gpm.TransactionId,gpm.AadharNumber as aadhar,CASE WHEN gpm.GatePassId = '' THEN '-' ELSE gpm.GatePassId END AS GatePassId, gpm.GatePassStatus\r\n"
           		+ "from GATEPASSMAIN gpm where GatePassStatus=3 and GatePassTypeId=4 and gpm.UnitId IN (:peIds) and gpm.ContractorId in (:contIds) ORDER BY gpm.TransactionId DESC";


           MapSqlParameterSource parameters = new MapSqlParameterSource();
           parameters.addValue("peIds", peIds);
           parameters.addValue("contIds", contIds);
           return namedParameterJdbcTemplate.query(
                   sql,
                   parameters,
                   (rs, rowNum) -> {

                   	GatePassMain dto = new GatePassMain();

                       dto.setTransactionId(rs.getString("TransactionId"));
                       dto.setAadhaarNumber(rs.getString("aadhar"));
                       dto.setGatePassId(rs.getString("GatePassId"));
                       String gatePassStatus = rs.getString("GatePassStatus");

           	        if (GatePassStatus.APPROVALPENDING.getStatus().equals(gatePassStatus)) {
           	            dto.setGatePassStatus("Pending");
           	        }else {
                           dto.setGatePassStatus(gatePassStatus);
                       }
                       return dto;
                   });

       } catch (Exception e) {

           LOGGER.error("Error getting block Pending Approvals", e);
           return Collections.emptyList();
       }
    }
    @Override
    public List<GatePassMain>  getUnblockPendingApprovals(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList){

    	try {
       	 if ((peList == null || peList.isEmpty()) && (contList == null || contList.isEmpty())) {
                return Collections.emptyList();
            }

       	 List<Long> peIds = peList == null ? Collections.emptyList(): peList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
           
       	 List<Long> contIds = contList == null ? Collections.emptyList(): contList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
       	 
       	 if (peIds.isEmpty() || contIds.isEmpty()) {
                return Collections.emptyList();
            }

           String sql ="select gpm.TransactionId,gpm.AadharNumber as aadhar,CASE WHEN gpm.GatePassId = '' THEN '-' ELSE gpm.GatePassId END AS GatePassId, gpm.GatePassStatus\r\n"
           		+ "from GATEPASSMAIN gpm where GatePassStatus=3 and GatePassTypeId=5 and gpm.UnitId IN (:peIds) and gpm.ContractorId in (:contIds) ORDER BY gpm.TransactionId DESC";


           MapSqlParameterSource parameters = new MapSqlParameterSource();
           parameters.addValue("peIds", peIds);
           parameters.addValue("contIds", contIds);
           return namedParameterJdbcTemplate.query(
                   sql,
                   parameters,
                   (rs, rowNum) -> {

                   	GatePassMain dto = new GatePassMain();

                       dto.setTransactionId(rs.getString("TransactionId"));
                       dto.setAadhaarNumber(rs.getString("aadhar"));
                       dto.setGatePassId(rs.getString("GatePassId"));
                       String gatePassStatus = rs.getString("GatePassStatus");

           	        if (GatePassStatus.APPROVALPENDING.getStatus().equals(gatePassStatus)) {
           	            dto.setGatePassStatus("Pending");
           	        }else {
                           dto.setGatePassStatus(gatePassStatus);
                       }
                       return dto;
                   });

       } catch (Exception e) {

           LOGGER.error("Error getting unblock Pending Approvals", e);
           return Collections.emptyList();
       }
    }
    @Override
    public List<GatePassMain>  getBlacklistPendingApprovals(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList){

    	try {
       	 if ((peList == null || peList.isEmpty()) && (contList == null || contList.isEmpty())) {
                return Collections.emptyList();
            }

       	 List<Long> peIds = peList == null ? Collections.emptyList(): peList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
           
       	 List<Long> contIds = contList == null ? Collections.emptyList(): contList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
       	 
       	 if (peIds.isEmpty() || contIds.isEmpty()) {
                return Collections.emptyList();
            }

           String sql ="select gpm.TransactionId,gpm.AadharNumber as aadhar,CASE WHEN gpm.GatePassId = '' THEN '-' ELSE gpm.GatePassId END AS GatePassId, gpm.GatePassStatus\r\n"
           		+ "from GATEPASSMAIN gpm where GatePassStatus=3 and GatePassTypeId=6 and gpm.UnitId IN (:peIds) and gpm.ContractorId in (:contIds) ORDER BY gpm.TransactionId DESC";


           MapSqlParameterSource parameters = new MapSqlParameterSource();
           parameters.addValue("peIds", peIds);
           parameters.addValue("contIds", contIds);
           return namedParameterJdbcTemplate.query(
                   sql,
                   parameters,
                   (rs, rowNum) -> {

                   	GatePassMain dto = new GatePassMain();

                       dto.setTransactionId(rs.getString("TransactionId"));
                       dto.setAadhaarNumber(rs.getString("aadhar"));
                       dto.setGatePassId(rs.getString("GatePassId"));
                       String gatePassStatus = rs.getString("GatePassStatus");

           	        if (GatePassStatus.APPROVALPENDING.getStatus().equals(gatePassStatus)) {
           	            dto.setGatePassStatus("Pending");
           	        }else {
                           dto.setGatePassStatus(gatePassStatus);
                       }
                       return dto;
                   });

       } catch (Exception e) {

           LOGGER.error("Error getting blacklist Pending Approvals", e);
           return Collections.emptyList();
       }
    }
    @Override
    public List<GatePassMain>  getDeblacklistPendingApprovals(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList){

    	try {
       	 if ((peList == null || peList.isEmpty()) && (contList == null || contList.isEmpty())) {
                return Collections.emptyList();
            }

       	 List<Long> peIds = peList == null ? Collections.emptyList(): peList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
           
       	 List<Long> contIds = contList == null ? Collections.emptyList(): contList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
       	 
       	 if (peIds.isEmpty() || contIds.isEmpty()) {
                return Collections.emptyList();
            }

           String sql ="select gpm.TransactionId,gpm.AadharNumber as aadhar,CASE WHEN gpm.GatePassId = '' THEN '-' ELSE gpm.GatePassId END AS GatePassId, gpm.GatePassStatus\r\n"
           		+ "from GATEPASSMAIN gpm where GatePassStatus=3 and GatePassTypeId=7 and gpm.UnitId IN (:peIds) and gpm.ContractorId in (:contIds) ORDER BY gpm.TransactionId DESC";


           MapSqlParameterSource parameters = new MapSqlParameterSource();
           parameters.addValue("peIds", peIds);
           parameters.addValue("contIds", contIds);
           return namedParameterJdbcTemplate.query(
                   sql,
                   parameters,
                   (rs, rowNum) -> {

                   	GatePassMain dto = new GatePassMain();

                       dto.setTransactionId(rs.getString("TransactionId"));
                       dto.setAadhaarNumber(rs.getString("aadhar"));
                       dto.setGatePassId(rs.getString("GatePassId"));
                       String gatePassStatus = rs.getString("GatePassStatus");

           	        if (GatePassStatus.APPROVALPENDING.getStatus().equals(gatePassStatus)) {
           	            dto.setGatePassStatus("Pending");
           	        }else {
                           dto.setGatePassStatus(gatePassStatus);
                       }
                       return dto;
                   });

       } catch (Exception e) {

           LOGGER.error("Error getting deblacklist Pending Approvals", e);
           return Collections.emptyList();
       }
    }
    @Override
    public List<GatePassMain>  getCancelPendingApprovals(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList){

    	try {
       	 if ((peList == null || peList.isEmpty()) && (contList == null || contList.isEmpty())) {
                return Collections.emptyList();
            }

       	 List<Long> peIds = peList == null ? Collections.emptyList(): peList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
           
       	 List<Long> contIds = contList == null ? Collections.emptyList(): contList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
       	 
       	 if (peIds.isEmpty() || contIds.isEmpty()) {
                return Collections.emptyList();
            }

           String sql ="select gpm.TransactionId,gpm.AadharNumber as aadhar,CASE WHEN gpm.GatePassId = '' THEN '-' ELSE gpm.GatePassId END AS GatePassId, gpm.GatePassStatus\r\n"
           		+ "from GATEPASSMAIN gpm where GatePassStatus=3 and GatePassTypeId=9 and gpm.UnitId IN (:peIds) and gpm.ContractorId in (:contIds) ORDER BY gpm.TransactionId DESC";


           MapSqlParameterSource parameters = new MapSqlParameterSource();
           parameters.addValue("peIds", peIds);
           parameters.addValue("contIds", contIds);
           return namedParameterJdbcTemplate.query(
                   sql,
                   parameters,
                   (rs, rowNum) -> {

                   	GatePassMain dto = new GatePassMain();

                       dto.setTransactionId(rs.getString("TransactionId"));
                       dto.setAadhaarNumber(rs.getString("aadhar"));
                       dto.setGatePassId(rs.getString("GatePassId"));
                       String gatePassStatus = rs.getString("GatePassStatus");

           	        if (GatePassStatus.APPROVALPENDING.getStatus().equals(gatePassStatus)) {
           	            dto.setGatePassStatus("Pending");
           	        }else {
                           dto.setGatePassStatus(gatePassStatus);
                       }
                       return dto;
                   });

       } catch (Exception e) {

           LOGGER.error("Error getting cancel Pending Approvals", e);
           return Collections.emptyList();
       }
    }
    @Override
    public List<GatePassMain>  getRenewPendingApprovals(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList){

    	try {
       	 if ((peList == null || peList.isEmpty()) && (contList == null || contList.isEmpty())) {
                return Collections.emptyList();
            }

       	 List<Long> peIds = peList == null ? Collections.emptyList(): peList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
           
       	 List<Long> contIds = contList == null ? Collections.emptyList(): contList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
       	 
       	 if (peIds.isEmpty() || contIds.isEmpty()) {
                return Collections.emptyList();
            }

           String sql ="select gpm.TransactionId,gpm.AadharNumber as aadhar,CASE WHEN gpm.GatePassId = '' THEN '-' ELSE gpm.GatePassId END AS GatePassId, gpm.GatePassStatus\r\n"
           		+ "from GATEPASSMAIN gpm where GatePassStatus=3 and GatePassTypeId=2 and gpm.UnitId IN (:peIds) and gpm.ContractorId in (:contIds) ORDER BY gpm.TransactionId DESC";


           MapSqlParameterSource parameters = new MapSqlParameterSource();
           parameters.addValue("peIds", peIds);
           parameters.addValue("contIds", contIds);
           return namedParameterJdbcTemplate.query(
                   sql,
                   parameters,
                   (rs, rowNum) -> {

                   	GatePassMain dto = new GatePassMain();

                       dto.setTransactionId(rs.getString("TransactionId"));
                       dto.setAadhaarNumber(rs.getString("aadhar"));
                       dto.setGatePassId(rs.getString("GatePassId"));
                       String gatePassStatus = rs.getString("GatePassStatus");

           	        if (GatePassStatus.APPROVALPENDING.getStatus().equals(gatePassStatus)) {
           	            dto.setGatePassStatus("Pending");
           	        }else {
                           dto.setGatePassStatus(gatePassStatus);
                       }
                       return dto;
                   });

       } catch (Exception e) {

           LOGGER.error("Error getting renew Pending Approvals", e);
           return Collections.emptyList();
       }
    }
    @Override
    public List<GatePassMain>  getTodayGatePass(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList){

    	try {
       	 if ((peList == null || peList.isEmpty()) && (contList == null || contList.isEmpty())) {
                return Collections.emptyList();
            }

       	 List<Long> peIds = peList == null ? Collections.emptyList(): peList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
           
       	 List<Long> contIds = contList == null ? Collections.emptyList(): contList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
       	 
       	 if (peIds.isEmpty() || contIds.isEmpty()) {
                return Collections.emptyList();
            }

           String sql ="select gpm.TransactionId,gpm.AadharNumber as aadhar,CASE WHEN gpm.GatePassId = '' THEN '-' ELSE gpm.GatePassId END AS GatePassId, \r\n"
           		+ "gpm.GatePassStatus,gpm.OnboardingType from GATEPASSMAIN gpm where GatePassTypeId in (1,12) and CAST(updateddate as date) = CAST(GETDATE() AS DATE) \r\n"
           		+ "and gpm.UnitId IN (:peIds) and gpm.ContractorId in (:contIds) ORDER BY gpm.TransactionId DESC";


           MapSqlParameterSource parameters = new MapSqlParameterSource();
           parameters.addValue("peIds", peIds);
           parameters.addValue("contIds", contIds);
           return namedParameterJdbcTemplate.query(
                   sql,
                   parameters,
                   (rs, rowNum) -> {

                   	GatePassMain dto = new GatePassMain();

                       dto.setTransactionId(rs.getString("TransactionId"));
                       dto.setAadhaarNumber(rs.getString("aadhar"));
                       dto.setGatePassId(rs.getString("GatePassId"));
                       dto.setOnboardingType(rs.getString("OnboardingType"));
                       String gatePassStatus = rs.getString("GatePassStatus");

           	        if (GatePassStatus.APPROVALPENDING.getStatus().equals(gatePassStatus)) {
           	            dto.setGatePassStatus("Pending");
           	        }else if (GatePassStatus.APPROVED.getStatus().equals(gatePassStatus)) {
           	            dto.setGatePassStatus("Approved");
           	        }else {
                           dto.setGatePassStatus(gatePassStatus);
                       }
                       return dto;
                   });

       } catch (Exception e) {

           LOGGER.error("Error getting Todays Gatepass Approvals", e);
           return Collections.emptyList();
       }
    }
    @Override
    public List<WorkOrderDTO> getWorkOrderList(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList){

    	try {
       	 if ((peList == null || peList.isEmpty()) && (contList == null || contList.isEmpty())) {
                return Collections.emptyList();
            }

       	 List<Long> peIds = peList == null ? Collections.emptyList(): peList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
           
       	 List<Long> contIds = contList == null ? Collections.emptyList(): contList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
       	 
       	 if (peIds.isEmpty() || contIds.isEmpty()) {
                return Collections.emptyList();
            }

           String sql ="SELECT wo.SAP_WORKORDER_NUM as woNumber,cpe.CODE AS pecode,cmc.CODE AS contcode,CAST(wo.VALIDFROM AS DATE) AS validFrom,CAST(wo.VALIDDT AS DATE) AS validTo,COUNT(gm.TransactionId) AS workmenCount\r\n"
           		+ "FROM CMSWORKORDER wo INNER JOIN CMSPRINCIPALEMPLOYER cpe ON cpe.UNITID = wo.UNITID INNER JOIN CMSCONTRACTOR cmc ON cmc.CONTRACTORID = wo.CONTRACTORID\r\n"
           		+ "LEFT JOIN GATEPASSMAIN gm ON gm.WorkorderId = wo.WORKORDERID AND gm.GatePassStatus = 4 and GatePassTypeId in (1,12,2) and gm.dot>GETDATE() WHERE wo.UnitId IN (:peIds) AND wo.ContractorId in (:contIds) AND wo.VALIDDT >= CAST(GETDATE() AS DATE) GROUP BY wo.SAP_WORKORDER_NUM, cpe.CODE,cmc.CODE,wo.VALIDFROM,wo.VALIDDT ORDER BY wo.SAP_WORKORDER_NUM";


           MapSqlParameterSource parameters = new MapSqlParameterSource();
           parameters.addValue("peIds", peIds);
           parameters.addValue("contIds", contIds);
           return namedParameterJdbcTemplate.query(
                   sql,
                   parameters,
                   (rs, rowNum) -> {

                	   WorkOrderDTO dto = new WorkOrderDTO();

                       dto.setWoNumber(rs.getString("woNumber"));
                       dto.setPeCode(rs.getString("pecode"));
                       dto.setContCode(rs.getString("contcode"));
                       dto.setValidFrom(rs.getString("validFrom"));
                       dto.setValidTo(rs.getString("validTo"));
                       dto.setWorkmenCount(rs.getInt("workmenCount"));
           	       
                       return dto;
                   });

       } catch (Exception e) {

           LOGGER.error("Error getting workorder list", e);
           return Collections.emptyList();
       }
    }
    
    @Override
    public List<PrincipalEmployerDTO> getPrincipalEmployers(List<PersonOrgLevel> peList){

    	try {
       	 if (peList == null || peList.isEmpty()) {
                return Collections.emptyList();
            }

       	 List<Long> peIds = peList == null ? Collections.emptyList(): peList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
       	 
       	 if (peIds.isEmpty()) {
                return Collections.emptyList();
            }

           String sql ="select NAME as principalEmployerName,CODE as pecode from CMSPRINCIPALEMPLOYER where UnitId IN (:peIds)";


           MapSqlParameterSource parameters = new MapSqlParameterSource();
           parameters.addValue("peIds", peIds);
           return namedParameterJdbcTemplate.query(
                   sql,
                   parameters,
                   (rs, rowNum) -> {

                	   PrincipalEmployerDTO dto = new PrincipalEmployerDTO();
                       dto.setPrincipalEmployerName(rs.getString("principalEmployerName"));
                       dto.setPecode(rs.getString("pecode"));
           	       
                       return dto;
                   });

       } catch (Exception e) {

           LOGGER.error("Error getting principalemployer list", e);
           return Collections.emptyList();
       }
    }
    @Override
    public List<LicenseExpiryDTO> getLicenseExpiryList(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList){

    	try {
       	 if ((peList == null || peList.isEmpty()) && (contList == null || contList.isEmpty())) {
                return Collections.emptyList();
            }

       	 List<Long> peIds = peList == null ? Collections.emptyList(): peList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
           
       	 List<Long> contIds = contList == null ? Collections.emptyList(): contList.stream().filter(Objects::nonNull).map(PersonOrgLevel::getId)
                        .filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toList());
       	 
       	 if (peIds.isEmpty() || contIds.isEmpty()) {
                return Collections.emptyList();
            }

           String sql ="SELECT cwc.WC_CODE as licenseNumber,cmc.NAME AS contractorName,CAST(cwc.WC_TO_DTM AS DATE) AS validto,DATEDIFF(DAY, CAST(GETDATE() AS DATE), CAST(cwc.WC_TO_DTM AS DATE)) AS daysToExpire FROM CMSCONTRACTOR_WC cwc JOIN CMSCONTRACTOR cmc ON cmc.CONTRACTORID = cwc.CONTRACTORID join CMSPRINCIPALEMPLOYER cpe on cpe.UNITID=cwc.UNITID\r\n"
           		+ "WHERE CAST(cwc.WC_TO_DTM AS DATE) BETWEEN CAST(GETDATE() AS DATE) AND DATEADD(DAY, 30, CAST(GETDATE() AS DATE)) and cwc.UNITID IN (:peIds) and cwc.CONTRACTORID in (:contIds) ORDER BY cwc.WC_TO_DTM";


           MapSqlParameterSource parameters = new MapSqlParameterSource();
           parameters.addValue("peIds", peIds);
           parameters.addValue("contIds", contIds);
           return namedParameterJdbcTemplate.query(
                   sql,
                   parameters,
                   (rs, rowNum) -> {

                	   LicenseExpiryDTO dto = new LicenseExpiryDTO();

                       dto.setLicenseNumber(rs.getString("licenseNumber"));
                       dto.setContractorName(rs.getString("contractorName"));
                       dto.setValidTo(rs.getString("validto"));
                       dto.setDaysLeft(rs.getInt("daysToExpire"));
           	       
                       return dto;
                   });

       } catch (Exception e) {

           LOGGER.error("Error getting license expiry list", e);
           return Collections.emptyList();
       }
    }
    
    public ChatBotVideoDTO getTrainingVideo(String moduleName) {

        String sql =
                "SELECT " +
                "VIDEOID," +
                "MODULENAME," +
                "VIDEOTITLE," +
                "DESCRIPTION," +
                "VIDEOURL " +
                "FROM CHATBOT_VIDEO_MASTER " +
                "WHERE MODULENAME=? " +
                "AND ISACTIVE=1";

        List<ChatBotVideoDTO> list =
                jdbcTemplate.query(
                        sql,
                        new Object[]{moduleName},
                        (rs,rowNum)->{

                        	ChatBotVideoDTO dto=new ChatBotVideoDTO();

                            dto.setId(rs.getLong("VIDEOID"));
                            dto.setModuleName(rs.getString("MODULENAME"));
                            dto.setVideoTitle(rs.getString("VIDEOTITLE"));
                            dto.setVideoDescription(rs.getString("DESCRIPTION"));
                            dto.setVideoUrl(rs.getString("VIDEOURL"));

                            return dto;

                        });

        return list.isEmpty() ? null : list.get(0);

    }
}
