package com.wfd.dot1.cwfm.dao;


import java.util.Collections;
import java.util.List;
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
import com.wfd.dot1.cwfm.dto.ContractorDTO;
import com.wfd.dot1.cwfm.dto.PrincipalEmployerDTO;
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
    public List<PrincipalEmployerDTO> getPrincipalEmployers() {

        try {

            String sql = "";

            return jdbcTemplate.query(
                    sql,
                    new BeanPropertyRowMapper<>(PrincipalEmployerDTO.class));

        } catch (Exception e) {

            LOGGER.error("Error getting Principal Employers", e);

            return Collections.emptyList();

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

}
