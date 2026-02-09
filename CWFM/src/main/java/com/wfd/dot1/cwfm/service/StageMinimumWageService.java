package com.wfd.dot1.cwfm.service;

import com.wfd.dot1.cwfm.dto.RequestorListDTO;
import com.wfd.dot1.cwfm.dto.StageMinimumWageDTO;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StageMinimumWageService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private static final Logger log = LoggerFactory.getLogger(GatePassToOnBoardService.class.getName());

    public String getInsertStageMiniumWage() {
        return QueryFileWatcher.getQuery("INSERT_STAGEMINIMUMWAGE");
    }

    public String getGET_LISTOFSTAGEWAGEMASTER() {
        return QueryFileWatcher.getQuery("GET_LISTOFSTAGEWAGEMASTER");
    }

    public String getUpdateOFSTAGEWAGEMASTER() {
        return QueryFileWatcher.getQuery("GET_LISTOFSTAGEWAGEMASTER");
    }

    public String insertStageMinimumWage(StageMinimumWageDTO dto) {
        try {
            String sql = getInsertStageMiniumWage();

            int rows = jdbcTemplate.update(sql,
                    // INSERT params
                    dto.getStateIdS(),
                    dto.getZoneIdS(),
                    dto.getTradeIdS(),
                    dto.getSkillIdS(),
                    dto.getBasicS(),
                    dto.getDaS(),
                    dto.getHraS(),
                    dto.getOtherAllowanceS(),
                    dto.getFromDates(),

                    // WHERE NOT EXISTS + OR ? > (SELECT MAX(...)) params
                    dto.getStateIdS(),
                    dto.getZoneIdS(),
                    dto.getTradeIdS(),
                    dto.getSkillIdS(),
                    dto.getFromDates(),
                    dto.getStateIdS(),
                    dto.getZoneIdS(),
                    dto.getTradeIdS(),
                    dto.getSkillIdS()
            );

            return String.valueOf(rows);

        } catch (Exception e) {
            throw new RuntimeException("Error inserting StageMinimumWage record", e);
        }
    }

    public List<StageMinimumWageDTO> getListOfSTATEWAGE( HttpServletRequest request) {
        try {
            log.info("Fetching Stage Minimum Wage list");

            String fetchListRequestor = getGET_LISTOFSTAGEWAGEMASTER();

            SqlRowSet rs = jdbcTemplate.queryForRowSet(fetchListRequestor);

            List<StageMinimumWageDTO> wageList = new ArrayList<>();

            while (rs.next()) {
                StageMinimumWageDTO dto = new StageMinimumWageDTO();

                dto.setRefIdS(rs.getLong("RefIdS"));

                dto.setStateIdS(rs.getInt("StateS"));
                dto.setStateName(rs.getString("STATENAME"));

                dto.setZoneIdS(rs.getLong("ZoneS"));
                dto.setZoneName(rs.getString("ZONENAME"));

                dto.setTradeIdS(rs.getLong("TradeS"));
                dto.setTradeName(rs.getString("TRADENAME"));

                dto.setSkillIdS(rs.getLong("SkillS"));
                dto.setSkillName(rs.getString("SKILLNAME"));

                dto.setBasicS(rs.getBigDecimal("BasicS"));
                dto.setDaS(rs.getBigDecimal("DAS"));
                dto.setHraS(rs.getBigDecimal("HRAS"));
                dto.setOtherAllowanceS(rs.getBigDecimal("OtherAllowanceS"));
                dto.setFromDates(rs.getString("FromDates"));
                dto.setUpdateDTM(rs.getString("UpdateDTMS"));
                wageList.add(dto);
            }

            log.info("Fetched {} Stage Minimum Wage records", wageList.size());
            return wageList;

        } catch (Exception e) {
            throw new RuntimeException("Error fetching Stage Minimum Wage list", e);
        }
    }

}