package com.wfd.dot1.cwfm.service;

import com.wfd.dot1.cwfm.dto.PlantZoneMappingDTO;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PlantZoneMappingService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(PlantZoneMappingService.class.getName());

    public String getStateList() {
        return QueryFileWatcher.getQuery("GET_ALL_STATESFORWAGEMAPP");
    }

    public String getPlantZoneList() {
        return QueryFileWatcher.getQuery("GET_PERSON_ORG_LEVELFORSTAGE");
    }

    public String getPlant_Zone_MappingList() {
        return QueryFileWatcher.getQuery("GET_PLANT_ZONE_MAPPING_LIST");
    }

    public String getGET_LIST_OF_CMSTRADE() {
        return QueryFileWatcher.getQuery("GET_LIST_OF_CMSTRADE");
    }

    public String update_OF_PLANTZONE() {
        return QueryFileWatcher.getQuery("UPDATE_PLANT_ZONE");
    }

    public String insertOFPLANTZONE() {
        return QueryFileWatcher.getQuery("INSERT_PLANT_ZONE_MAPPING");
    }

    public String getGET_LIST_OF_CMSSKILL() {
        return QueryFileWatcher.getQuery("GET_LIST_OF_CMSSKILL");
    }

    public String insertPlantZoneMapping(PlantZoneMappingDTO dto) {
        try {
            if (dto.getUnitIdS() == null || dto.getUnitIdS().isEmpty()) {
                throw new IllegalArgumentException("Unit ID is required");
            }
            if (dto.getStateS() == null || dto.getStateS().isEmpty()) {
                throw new IllegalArgumentException("State is required");
            }
            if (dto.getZoneS() == null || dto.getZoneS().isEmpty()) {
                throw new IllegalArgumentException("Zone is required");
            }

            String query = insertOFPLANTZONE();
            log.info("[v0] Executing insert query with params - Unit: {}, State: {}, Zone: {}, Active: {}",
                    dto.getUnitIdS(), dto.getStateS(), dto.getZoneS(), dto.getIsActive());

            int activeValue = 1;
            if (dto.getIsActive() != null && !dto.getIsActive().isEmpty()) {
                try {
                    activeValue = Integer.parseInt(dto.getIsActive());
                } catch (NumberFormatException e) {
                    log.warn("[v0] Invalid isActive value: {}, defaulting to 1", dto.getIsActive());
                    activeValue = 1;
                }
            }

            int rows = jdbcTemplate.update(
                    query,
                    dto.getUnitIdS(),
                    dto.getStateS(),
                    dto.getZoneS(),
                    activeValue
            );

            if (rows > 0) {
                log.info("[v0] Successfully inserted {} rows", rows);
                return "Mapping saved successfully!";
            } else {
                log.warn("[v0] Insert returned 0 rows affected");
                return "Insert failed - no rows affected";
            }

        } catch (DataIntegrityViolationException e) {
            log.error("[v0] Duplicate entry detected: {}", e.getMessage());
            String errorMsg = "This Plant Zone Mapping already exists. Cannot insert duplicate entry.";
            throw new DuplicateEntryException(errorMsg, e);
        } catch (IllegalArgumentException e) {
            log.error("[v0] Validation error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        } catch (Exception e) {
            log.error("[v0] Error inserting Plant_Zone_Mapping: {}", e.getMessage(), e);
            throw new RuntimeException("Error inserting Plant_Zone_Mapping: " + e.getMessage(), e);
        }
    }

    public String updatePlantZoneMapping(Integer refMppId) {
        try {
            String query = update_OF_PLANTZONE();

            int rows = jdbcTemplate.update(query, refMppId);

            return rows > 0 ? "Flag changed successfully" : "Update failed";

        } catch (Exception e) {
            throw new RuntimeException("Error updating Plant_Zone_Mapping", e);
        }
    }

    private Integer getIntOrNull(SqlRowSet rs, String column) {
        String value = rs.getString(column);
        return (value != null) ? Integer.valueOf(value) : null;
    }

    public List<PlantZoneMappingDTO> getListOfPlantZoneMappingDTO(HttpServletRequest request) {
        try {
            log.info("Fetching Plant_Zone_Mapping list");

            String fetchListState = getPlant_Zone_MappingList();
            SqlRowSet rs = jdbcTemplate.queryForRowSet(fetchListState);

            List<PlantZoneMappingDTO> peList = new ArrayList<>();

            while (rs.next()) {
                PlantZoneMappingDTO dto = new PlantZoneMappingDTO();

                dto.setRefMppId(rs.getString("RefMppId"));
                dto.setUnitIdS(rs.getString("UnitIdS"));
                dto.setUnitName(rs.getString("UnitName"));
                dto.setStateS(rs.getString("StateS"));
                dto.setStateName(rs.getString("STATENM"));
                dto.setZoneS(rs.getString("ZoneS"));
                dto.setZoneName(rs.getString("GMDESCRIPTION"));
                dto.setIsActive(rs.getString("IsActive"));
                dto.setUpdateDTM(rs.getString("UpdateDTM"));

                peList.add(dto);
            }

            log.info("Fetched {} plant-zone mappings", peList.size());
            return peList;

        } catch (Exception e) {
            throw new RuntimeException("Error fetching Plant_Zone_Mapping list", e);
        }
    }

    public List<Map<String, Object>> getAllStates() {
        String fetchListState = getStateList();
        return jdbcTemplate.queryForList(fetchListState);
    }

    public List<PersonOrgLevel> getAllPRINCIPLE() {
        try {

            log.info("Fetching list of principle employer ");

            String fetchListState = getPlantZoneList();

            SqlRowSet rs = jdbcTemplate.queryForRowSet(fetchListState);

            List<PersonOrgLevel> peList = new ArrayList<>();

            while (rs.next()) {
                PersonOrgLevel dto = new PersonOrgLevel();
                dto.setLevelDef(rs.getString("LevelDef"));
                dto.setId(rs.getString("Id"));
                dto.setOleId(rs.getString("Oleid"));
                dto.setDescription(rs.getString("Description"));
                peList.add(dto);

            }
            log.info("getting of principle employer ");

            return peList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public List<Map<String, Object>> getAlltrade() {
        String fetchListState = getGET_LIST_OF_CMSTRADE();
        return jdbcTemplate.queryForList(fetchListState);
    }

    public List<Map<String, Object>> getAllskill() {
        String fetchListState = getGET_LIST_OF_CMSSKILL();
        return jdbcTemplate.queryForList(fetchListState);
    }

    public static class DuplicateEntryException extends RuntimeException {
        public DuplicateEntryException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
