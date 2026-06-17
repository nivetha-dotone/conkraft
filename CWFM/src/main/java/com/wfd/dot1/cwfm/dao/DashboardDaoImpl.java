package com.wfd.dot1.cwfm.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.AckExpiryDTO;
import com.wfd.dot1.cwfm.dto.BusinessTypePEDTO;
import com.wfd.dot1.cwfm.dto.ContractorWorkmenDTO;
import com.wfd.dot1.cwfm.dto.DashboardDTO;
import com.wfd.dot1.cwfm.dto.DepartmentWorkmenDTO;
import com.wfd.dot1.cwfm.dto.ExpiryDTO;
import com.wfd.dot1.cwfm.dto.PEContractorDTO;
import com.wfd.dot1.cwfm.dto.PlantWorkmenDTO;
import com.wfd.dot1.cwfm.dto.PvcTypeDTO;
import com.wfd.dot1.cwfm.dto.WorkOrderDTO;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;

@Repository
public class DashboardDaoImpl implements DashboardDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public DashboardDTO getDashboardData(String userId, String roleName,
                                         List<PersonOrgLevel> peList,
                                         List<PersonOrgLevel> contList) {

        DashboardDTO dashboard = new DashboardDTO();

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

        loadKpiSummary(dashboard, userId, roleName,peIds, contIds);
        loadPlantWiseWorkmen(dashboard, peIds,contIds);
        loadContractorWiseWorkmen(dashboard, contIds);
        loadActiveWOList(dashboard, peIds, contIds);
        loadPvcExpiryList(dashboard, peIds);
        loadPvcTypeWiseCount(dashboard, peIds);
        loadContractorDeptWorkmen(dashboard, peIds);
        loadPlantContractorWorkmen(dashboard, peIds);

        dashboard.setAckExpiryList(new ArrayList<AckExpiryDTO>());
        dashboard.setBusinessTypePEList(new ArrayList<BusinessTypePEDTO>());
        dashboard.setPeContractorList(new ArrayList<PEContractorDTO>());

        return dashboard;
    }

    private void loadKpiSummary(DashboardDTO dashboard, String userId, String roleName,
                                String peIds, String contIds) {

        String sql = "EXEC dbo.USP_DASHBOARD_KPI_SUMMARY ?, ?, ?, ?";

        jdbcTemplate.query(sql, rs -> {
           
                dashboard.setActiveWorkmen(rs.getInt("ActiveWorkmen"));
                dashboard.setActiveWO(rs.getInt("ActiveWO"));
                dashboard.setActiveLL(rs.getInt("ActiveLL"));
                dashboard.setActiveWC(rs.getInt("ActiveWC"));
                dashboard.setActiveESIC(rs.getInt("ActiveESIC"));
                dashboard.setPendingRequests(rs.getInt("PendingRequests"));
            
        }, userId, roleName, peIds, contIds);
    }

    private void loadPlantWiseWorkmen(DashboardDTO dashboard, String peIds,String contIds) {

        String sql = "EXEC dbo.USP_DASHBOARD_PLANT_WISE_WORKMEN ?,?";

        List<PlantWorkmenDTO> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            PlantWorkmenDTO dto = new PlantWorkmenDTO();
            dto.setPlantName(rs.getString("PlantName"));
            dto.setActiveCount(rs.getInt("CountValue"));
            return dto;
        }, peIds,contIds);

        dashboard.setPlantWorkmenList(list);
    }

    private void loadContractorWiseWorkmen(DashboardDTO dashboard, String contIds) {

        String sql = "EXEC dbo.USP_DASHBOARD_CONTRACTOR_WISE_WORKMEN ?";

        List<ContractorWorkmenDTO> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            ContractorWorkmenDTO dto = new ContractorWorkmenDTO();
            dto.setContractorId(rs.getString("ContractorId"));
            dto.setContractorName(rs.getString("ContractorName"));
            dto.setWorkmenCount(rs.getInt("WorkmenCount"));
            return dto;
        }, contIds);

        dashboard.setContractorWorkmenList(list);
    }

    private void loadActiveWOList(DashboardDTO dashboard, String peIds, String contIds) {

        String sql = "EXEC dbo.USP_DASHBOARD_ACTIVE_WO_PER_PLANT ?, ?";

        List<WorkOrderDTO> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            WorkOrderDTO dto = new WorkOrderDTO();
            dto.setWoId(rs.getLong("WORKORDERID"));
            dto.setWoNumber(rs.getString("SAP_WORKORDER_NUM"));
            dto.setContractorName(rs.getString("CONTRACTOR_NAME"));
            dto.setContractorId(rs.getString("CONTRACTORID"));
            dto.setPlantName(rs.getString("PlantName"));
            dto.setWorkmenCount(rs.getInt("WMCOUNT"));
            return dto;
        }, peIds, contIds);

        dashboard.setActiveWOList(list);
    }

    private void loadPvcExpiryList(DashboardDTO dashboard, String peIds) {

        String sql = "EXEC dbo.USP_DASHBOARD_PVC_EXPIRY_30_DAYS ?";

        List<ExpiryDTO> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            ExpiryDTO dto = new ExpiryDTO();
            dto.setLicenseNo(rs.getString("GatePassId"));
            dto.setContractorName(rs.getString("ContractorName"));
            dto.setExpiryDate(rs.getString("ExpiryDate"));
            dto.setDaysLeft(rs.getInt("DaysLeft"));
            dto.setType(rs.getString("Type"));
            return dto;
        }, peIds);

        dashboard.setExpiryList(list);
    }

    private void loadPvcTypeWiseCount(DashboardDTO dashboard, String peIds) {

        String sql = "EXEC dbo.USP_DASHBOARD_PVC_TYPE_WISE_COUNT ?";

        List<PvcTypeDTO> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            PvcTypeDTO dto = new PvcTypeDTO();
            dto.setPvcType(rs.getString("PVCType"));
            dto.setTotalCount(rs.getInt("TotalCount"));
            return dto;
        }, peIds);

        dashboard.setPvcTypeList(list);
    }

    private void loadContractorDeptWorkmen(DashboardDTO dashboard, String peIds) {

        String sql = "EXEC dbo.USP_DASHBOARD_CONTRACTOR_DEPT_WORKMEN ?";

        List<DepartmentWorkmenDTO> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            DepartmentWorkmenDTO dto = new DepartmentWorkmenDTO();
            dto.setContractorName(rs.getString("ContractorName"));
            dto.setDepartmentName(rs.getString("DepartmentName"));
            dto.setWorkmenCount(rs.getInt("WorkmenCount"));
            return dto;
        }, peIds);

        dashboard.setContractorDeptWorkmenList(list);
    }

    private void loadPlantContractorWorkmen(DashboardDTO dashboard, String peIds) {

        String sql = "EXEC dbo.USP_DASHBOARD_PLANT_CONTRACTOR_WORKMEN ?";

        List<PlantWorkmenDTO> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            PlantWorkmenDTO dto = new PlantWorkmenDTO();
            dto.setPlantName(rs.getString("PlantName"));
            dto.setContractorCount(rs.getInt("ContractorCount"));
            dto.setActiveCount(rs.getInt("WorkmenCount"));
            return dto;
        }, peIds);

        dashboard.setPlantContrWorkmenList(list);
    }

    @Override
    public List<WorkOrderDTO> getWorkmenByWO(long woId, String contractorId) {

        List<WorkOrderDTO> list = new ArrayList<>();

        String query =
                "SELECT gpm.GatePassId, gpm.AadharNumber, " +
                "CONCAT(COALESCE(gpm.FirstName, ''), ' ', COALESCE(gpm.LastName, '')) AS FullName " +
                "FROM GATEPASSMAIN gpm " +
                "WHERE gpm.WorkorderId = ? " +
                "AND gpm.ContractorId = ? " +
                "AND gpm.GatePassStatus = 4 " +
                "AND gpm.GatePassTypeId IN (1,2,12,15)";

        SqlRowSet rs = jdbcTemplate.queryForRowSet(query, woId, contractorId);

        while (rs.next()) {
            WorkOrderDTO dto = new WorkOrderDTO();
            dto.setGatepassId(rs.getString("GatePassId"));
            dto.setAadharNumber(rs.getString("AadharNumber"));
            dto.setFullname(rs.getString("FullName"));
            list.add(dto);
        }

        return list;
    }
}