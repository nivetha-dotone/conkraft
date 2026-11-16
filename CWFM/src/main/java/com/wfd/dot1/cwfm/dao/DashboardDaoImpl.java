package com.wfd.dot1.cwfm.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.AckExpiryDTO;
import com.wfd.dot1.cwfm.dto.BillStatusDTO;
import com.wfd.dot1.cwfm.dto.BlackliestedGPDTO;
import com.wfd.dot1.cwfm.dto.BusinessTypePEDTO;
import com.wfd.dot1.cwfm.dto.ContractorComplianceScoreDTO;
import com.wfd.dot1.cwfm.dto.ContractorWorkmenDTO;
import com.wfd.dot1.cwfm.dto.DashboardDTO;
import com.wfd.dot1.cwfm.dto.DepartmentWorkmenDTO;
import com.wfd.dot1.cwfm.dto.ESICDTO;
import com.wfd.dot1.cwfm.dto.ExpiryDTO;
import com.wfd.dot1.cwfm.dto.GatepassExpiryDTO;
import com.wfd.dot1.cwfm.dto.LLLicensesDTO;
import com.wfd.dot1.cwfm.dto.LicensesDTO;
import com.wfd.dot1.cwfm.dto.PEContractorDTO;
import com.wfd.dot1.cwfm.dto.PlantWorkmenDTO;
import com.wfd.dot1.cwfm.dto.PvcTypeDTO;
import com.wfd.dot1.cwfm.dto.WCDTO;
import com.wfd.dot1.cwfm.dto.WorkOrderDTO;
import com.wfd.dot1.cwfm.dto.WorkmensReachedRetiredAgeDTO;
import com.wfd.dot1.cwfm.dto.WorkorderAlertsDTO;
import com.wfd.dot1.cwfm.dto.WorkordersDTO;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

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
        loadWorkordersExpiry( dashboard,  peIds,  contIds) ;
       // loadLicensesExpiry( dashboard,  peIds,  contIds) ;
        loadLicensesExpiry( dashboard,  peIds,  contIds) ;
        gatepassesExpiry( dashboard,  peIds,  contIds) ;
        getWorkmensReachedRetiredAge( dashboard,  peIds,  contIds) ;
        blackliestedGatepasses(dashboard,  peIds,  contIds) ;
        pendingBills( dashboard,  peIds,  contIds) ;
        workOrderCompliance(dashboard,contIds) ;
        llCompliance(dashboard,contIds) ;
        wcCompliance(dashboard,contIds) ;
        esicCompliance(dashboard,contIds) ;
        billVerificationStatus(dashboard,contIds) ;
        
        dashboard.setAckExpiryList(new ArrayList<AckExpiryDTO>());
        dashboard.setBusinessTypePEList(new ArrayList<BusinessTypePEDTO>());
        dashboard.setPeContractorList(new ArrayList<PEContractorDTO>());

        return dashboard;
    }
    public String loadKpiSummary() {
	    return QueryFileWatcher.getQuery("DASHBOARD_KPI_SUMMARY");
	}
    private void loadKpiSummary(DashboardDTO dashboard, String userId, String roleName,
                                String peIds, String contIds) {
    	String sql = loadKpiSummary();
       // String sql = "EXEC dbo.USP_DASHBOARD_KPI_SUMMARY ?, ?, ?, ?";

        jdbcTemplate.query(sql, rs -> {
           
                dashboard.setActiveWorkmen(rs.getInt("ActiveWorkmen"));
                dashboard.setActiveWO(rs.getInt("ActiveWO"));
                dashboard.setActiveLL(rs.getInt("ActiveLL"));
                dashboard.setActiveWC(rs.getInt("ActiveWC"));
                dashboard.setActiveESIC(rs.getInt("ActiveESIC"));
                dashboard.setPendingRequests(rs.getInt("PendingRequests"));
            
        }, userId, roleName, peIds, contIds);
    }
    public String loadPlantWiseWorkmen() {
	    return QueryFileWatcher.getQuery("DASHBOARD_PLANT_WISE_WORKMEN");
	}
    private void loadPlantWiseWorkmen(DashboardDTO dashboard, String peIds,String contIds) {
    	String sql = loadPlantWiseWorkmen();
        //String sql = "EXEC dbo.USP_DASHBOARD_PLANT_WISE_WORKMEN ?,?";

        List<PlantWorkmenDTO> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            PlantWorkmenDTO dto = new PlantWorkmenDTO();
            dto.setPlantName(rs.getString("PlantName"));
            dto.setActiveCount(rs.getInt("CountValue"));
            return dto;
        }, peIds,contIds);

        dashboard.setPlantWorkmenList(list);
    }
    public String loadContractorWiseWorkmen() {
	    return QueryFileWatcher.getQuery("DASHBOARD_CONTRACTOR_WISE_WORKMEN");
	}
    private void loadContractorWiseWorkmen(DashboardDTO dashboard, String contIds) {
    	String sql = loadContractorWiseWorkmen();
        //String sql = "EXEC dbo.USP_DASHBOARD_CONTRACTOR_WISE_WORKMEN ?";

        List<ContractorWorkmenDTO> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            ContractorWorkmenDTO dto = new ContractorWorkmenDTO();
            dto.setContractorId(rs.getString("ContractorId"));
            dto.setContractorName(rs.getString("ContractorName"));
            dto.setWorkmenCount(rs.getInt("WorkmenCount"));
            return dto;
        }, contIds);

        dashboard.setContractorWorkmenList(list);
    }
    public String loadActiveWOList() {
	    return QueryFileWatcher.getQuery("DASHBOARD_ACTIVE_WO_PER_PLANT");
	}
    private void loadActiveWOList(DashboardDTO dashboard, String peIds, String contIds) {
    	String sql = loadActiveWOList();
        //String sql = "EXEC dbo.USP_DASHBOARD_ACTIVE_WO_PER_PLANT ?, ?";

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
    public String loadPvcExpiryList() {
	    return QueryFileWatcher.getQuery("DASHBOARD_PVC_EXPIRY_30_DAYS");
	}
    private void loadPvcExpiryList(DashboardDTO dashboard, String peIds) {
    	String sql = loadPvcExpiryList();
        //String sql = "EXEC dbo.USP_DASHBOARD_PVC_EXPIRY_30_DAYS ?";

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
    public String loadPvcTypeWiseCount() {
	    return QueryFileWatcher.getQuery("DASHBOARD_PVC_TYPE_WISE_COUNT");
	}
    private void loadPvcTypeWiseCount(DashboardDTO dashboard, String peIds) {
    	String sql = loadPvcTypeWiseCount();
        //String sql = "EXEC dbo.USP_DASHBOARD_PVC_TYPE_WISE_COUNT ?";

        List<PvcTypeDTO> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            PvcTypeDTO dto = new PvcTypeDTO();
            dto.setPvcType(rs.getString("PVCType"));
            dto.setTotalCount(rs.getInt("TotalCount"));
            return dto;
        }, peIds);

        dashboard.setPvcTypeList(list);
    }
    public String loadContractorDeptWorkmen() {
	    return QueryFileWatcher.getQuery("DASHBOARD_CONTRACTOR_DEPT_WORKMEN");
	}
    private void loadContractorDeptWorkmen(DashboardDTO dashboard, String peIds) {
    	String sql = loadContractorDeptWorkmen();
        //String sql = "EXEC dbo.USP_DASHBOARD_CONTRACTOR_DEPT_WORKMEN ?";

        List<DepartmentWorkmenDTO> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            DepartmentWorkmenDTO dto = new DepartmentWorkmenDTO();
            dto.setContractorName(rs.getString("ContractorName"));
            dto.setDepartmentName(rs.getString("DepartmentName"));
            dto.setWorkmenCount(rs.getInt("WorkmenCount"));
            return dto;
        }, peIds);

        dashboard.setContractorDeptWorkmenList(list);
    }
    public String loadPlantContractorWorkmen() {
	    return QueryFileWatcher.getQuery("DASHBOARD_PLANT_CONTRACTOR_WORKMEN");
	}
    private void loadPlantContractorWorkmen(DashboardDTO dashboard, String peIds) {
    	String sql = loadPlantContractorWorkmen();
        //String sql = "EXEC dbo.USP_DASHBOARD_PLANT_CONTRACTOR_WORKMEN ?";

        List<PlantWorkmenDTO> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            PlantWorkmenDTO dto = new PlantWorkmenDTO();
            dto.setPlantName(rs.getString("PlantName"));
            dto.setContractorCount(rs.getInt("ContractorCount"));
            dto.setActiveCount(rs.getInt("WorkmenCount"));
            return dto;
        }, peIds);

        dashboard.setPlantContrWorkmenList(list);
    }
    public String getWorkmenByWO() {
	    return QueryFileWatcher.getQuery("DASHBOARD_GET_WORKMEN_BY_WO");
	}
    @Override
    public List<WorkOrderDTO> getWorkmenByWO(long woId, String contractorId) {

        List<WorkOrderDTO> list = new ArrayList<>();
        String query = getWorkmenByWO();
//        String query =
//                "SELECT gpm.GatePassId, gpm.AadharNumber, " +
//                "CONCAT(COALESCE(gpm.FirstName, ''), ' ', COALESCE(gpm.LastName, '')) AS FullName " +
//                "FROM GATEPASSMAIN gpm " +
//                "WHERE gpm.WorkorderId = ? " +
//                "AND gpm.ContractorId = ? " +
//                "AND gpm.GatePassStatus = 4 " +
//                "AND gpm.GatePassTypeId IN (1,2,12,15)";

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
    public String loadWorkordersExpiry() {
	    return QueryFileWatcher.getQuery("DASHBOARD_LOAD_EXPIRED_WORKORDERS");
	}
    private void loadWorkordersExpiry(DashboardDTO dashboard, String peIds, String contIds) {
    	String sql = loadWorkordersExpiry();
    	//String sql = "EXEC GetWorkordersExpiry ?, ?";
    	
    	 List<WorkorderAlertsDTO> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
    		 WorkorderAlertsDTO dto = new WorkorderAlertsDTO();
             dto.setWorkorderNumber(rs.getString("workorderNumber"));
             dto.setExpiryDate(rs.getString("ExpiryDate"));
             dto.setDaysLeft(rs.getInt("DaysLeft"));
             dto.setWorkorderExpiryCount(rs.getString("expiredworkorders"));
             return dto;
         }, peIds,contIds);

         dashboard.setWorkorderalertsList(list);
    }
  
    
    public String loadLicensesExpiry() {
	    return QueryFileWatcher.getQuery("DASHBOARD_LOAD_EXPIRED_LICENSES");
	}
    private void loadLicensesExpiry(DashboardDTO dashboard, String peIds, String contIds) {
        	String sql = loadLicensesExpiry();
        	//String sql = "EXEC GetLicensesExpiry ?, ?";

//        jdbcTemplate.query(sql, rs -> {
//           
//                dashboard.setExpiredLicensess(rs.getInt("LicenseExpired")); 
//        }, peIds, contIds);
        	
        	 List<LLLicensesDTO> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
        		 LLLicensesDTO dto = new LLLicensesDTO();
                 dto.setLicenseNumber(rs.getString("licenseNumber"));
                 dto.setExpiryDate(rs.getString("expierddate"));
                 dto.setDaysLeft(rs.getInt("DaysLeft"));
                 dto.setLicenseType(rs.getString("licenseType"));
                 return dto;
             }, peIds,contIds);

             dashboard.setLLLicenseList(list);
    }
    
    public String gatepassesExpiry() {
	    return QueryFileWatcher.getQuery("DASHBOARD_LOAD_EXPIRED_GATEPASSES");
	}
    private void gatepassesExpiry(DashboardDTO dashboard, String peIds, String contIds) {
    	String sql = gatepassesExpiry();
    	//String sql = "EXEC GetGatepassesExpiry ?, ?";
    	
    	List<GatepassExpiryDTO> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
    		GatepassExpiryDTO dto = new GatepassExpiryDTO();
            dto.setGatepassId(rs.getString("gatepassid"));
            dto.setExpiryDate(rs.getString("dot"));
            dto.setDaysLeft(rs.getInt("DaysLeft"));
            dto.setFullName(rs.getString("FullName"));
            return dto;
        }, peIds,contIds);

        dashboard.setGatepassExpiryList(list);
    }
    public String getWorkmensReachedRetiredAge() {
	    return QueryFileWatcher.getQuery("WORKMEN_APPROACHING_RETIREMENT_AGE");
	}
    private void getWorkmensReachedRetiredAge(DashboardDTO dashboard, String peIds, String contIds) {
    	String sql = getWorkmensReachedRetiredAge();
    	//String sql = "EXEC GET_WORKMEN_AGE_58 ?, ?";
    	
    	List<WorkmensReachedRetiredAgeDTO> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
    		WorkmensReachedRetiredAgeDTO dto = new WorkmensReachedRetiredAgeDTO();
            dto.setGatepassId(rs.getString("GatePassId"));
            dto.setFullname(rs.getString("FullName"));
            dto.setDateOfBirth(rs.getString("dateOfBirth"));
            dto.setDaysLeft(rs.getInt("DaysLeft"));
            return dto;
        }, peIds,contIds);

        dashboard.setWorkmensReachedRetiredAgeList(list);
    }
    public String blackliestedGatepasses() {
	    return QueryFileWatcher.getQuery("DASHBOARD_LOAD_BLACKLISTED_GATEPASSES");
	}
    private void blackliestedGatepasses(DashboardDTO dashboard,String peIds, String contIds) {
    	 String sql =blackliestedGatepasses();
//        //String sql = "select count(*) as BlackliestedGatepasses from GATEPASSMAIN where GatePassTypeId=6";
//    	 //String sql = "EXEC GetBlacklistedGatepasses";
//        jdbcTemplate.query(sql, rs -> {
//           
//                dashboard.setBlackliestedGP(rs.getInt("BlackliestedGatepasses"));
//        });
    	 List<BlackliestedGPDTO> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
    		 BlackliestedGPDTO dto = new BlackliestedGPDTO();
             dto.setGatepassId(rs.getString("GatePassId"));
             dto.setFullname(rs.getString("FullName"));
             dto.setAadharNumber(rs.getString("AadharNumber"));
             dto.setContractor(rs.getString("ContractorName"));
             return dto;
         }, peIds,contIds);

         dashboard.setBlackliestedGPList(list);
    }
    public String pendingBills() {
	    return QueryFileWatcher.getQuery("DASHBOARD_LOAD_PENDING_BILLS");
	}
    private void pendingBills(DashboardDTO dashboard, String peIds, String contIds) {
    	 String sql =pendingBills();
//        String sql = "select count(*) as PendingBills from CMSWageCostWorkFlow wc where Status=3 and wc.UnitId  IN (SELECT TRY_CAST(value AS INT)  FROM STRING_SPLIT(?, ','))"
//        		 + "AND wc.ContractorId  IN (SELECT TRY_CAST(value AS INT)  FROM STRING_SPLIT(?, ','))";
    	 //String sql = "EXEC GetPendingBills ?, ?";
        jdbcTemplate.query(sql, rs -> {
           
                dashboard.setPendingBills(rs.getInt("PendingBills"));
        }, peIds, contIds);
    }
    public String workOrderCompliance() {
	    return QueryFileWatcher.getQuery("DASHBOARD_LOAD_WORKORDER_COMPLIANCE");
	}
    private void workOrderCompliance(DashboardDTO dashboard, String contIds) {
    	String sql =workOrderCompliance();
//        String sql = """
//            SELECT 
//                COUNT(*) AS TotalWorkOrders,
//                SUM(CASE WHEN VALIDDT >= GETDATE() THEN 1 ELSE 0 END) AS ActiveWorkOrders
//            FROM CMSWORKORDER
//            WHERE ContractorId IN (
//                SELECT TRY_CAST(value AS INT) 
//                FROM STRING_SPLIT(?, ',')
//            )
//        """;
    	//String sql = "EXEC GetWorkOrderCompliance ?";
        List<WorkordersDTO> list = jdbcTemplate.query(sql, new Object[]{contIds}, (rs, rowNum) -> {
            int total = rs.getInt("TotalWorkOrders");
            int active = rs.getInt("ActiveWorkOrders");

            // ✅ Calculate percentage safely
            int percent = total > 0 ? (active * 100) / total : 0;

            WorkordersDTO dto = new WorkordersDTO();
            dto.setTotalWO(total);
            dto.setActiveWO(active);
            dto.setWoPercentage(percent);

            return dto;
        });

        dashboard.setWorkorderList(list);
    }
    public String llCompliance() {
	    return QueryFileWatcher.getQuery("DASHBOARD_LOAD_LL_COMPLIANCE");
	}
    private void llCompliance(DashboardDTO dashboard ,String contIds) {
    	String sql =llCompliance();
        //String sql = "SELECT COUNT(*) AS TotalLL,SUM(CASE WHEN WC_TO_DTM >= GETDATE() THEN 1 ELSE 0 END) AS ActiveLLs FROM CMSCONTRACTOR_WC where LICENCE_TYPE IN ('LL') and  ContractorId  IN (SELECT TRY_CAST(value AS INT)  FROM STRING_SPLIT(?, ','))";

        List<LicensesDTO> list = jdbcTemplate.query(sql, new Object[]{contIds}, (rs, rowNum) -> {
            int total = rs.getInt("TotalLL");
            int active = rs.getInt("ActiveLLs");

            // ✅ Calculate percentage safely
            int percent = total > 0 ? (active * 100) / total : 0;

            LicensesDTO dto = new LicensesDTO();
            dto.setTotalLL(total);
            dto.setActiveLL(active);
            dto.setLLPercentage(percent);

            return dto;
        });

        dashboard.setLicenseList(list);
    }
    public String wcCompliance() {
	    return QueryFileWatcher.getQuery("DASHBOARD_LOAD_WC_COMPLIANCE");
	}
    private void wcCompliance(DashboardDTO dashboard ,String contIds) {
    	String sql =wcCompliance();
       // String sql = "SELECT COUNT(*) AS TotalLL,SUM(CASE WHEN WC_TO_DTM >= GETDATE() THEN 1 ELSE 0 END) AS ActiveLLs FROM CMSCONTRACTOR_WC where LICENCE_TYPE IN ('WC') and  ContractorId  IN (SELECT TRY_CAST(value AS INT)  FROM STRING_SPLIT(?, ','))";
    	 //String sql = "EXEC GetWCCompliance ?";
        List<WCDTO> list = jdbcTemplate.query(sql, new Object[]{contIds}, (rs, rowNum) -> {
            int total = rs.getInt("TotalLL");
            int active = rs.getInt("ActiveLLs");

            // ✅ Calculate percentage safely
            int percent = total > 0 ? (active * 100) / total : 0;

            WCDTO dto = new WCDTO();
            dto.setTotalWC(total);
            dto.setActiveWC(active);
            dto.setWCPercentage(percent);

            return dto;
        });

        dashboard.setWCList(list);
    }
    public String esicCompliance() {
	    return QueryFileWatcher.getQuery("DASHBOARD_LOAD_ESIC_COMPLIANCE");
	}
    private void esicCompliance(DashboardDTO dashboard ,String contIds) {
    	String sql =esicCompliance();
        //String sql = "SELECT COUNT(*) AS TotalLL,SUM(CASE WHEN WC_TO_DTM >= GETDATE() THEN 1 ELSE 0 END) AS ActiveLLs FROM CMSCONTRACTOR_WC where LICENCE_TYPE IN ('ESIC') and  ContractorId  IN (SELECT TRY_CAST(value AS INT)  FROM STRING_SPLIT(?, ','))";
    	//String sql = "EXEC GetESICCompliance ?";
        List<ESICDTO> list = jdbcTemplate.query(sql, new Object[]{contIds}, (rs, rowNum) -> {
            int total = rs.getInt("TotalLL");
            int active = rs.getInt("ActiveLLs");

            // ✅ Calculate percentage safely
            int percent = total > 0 ? (active * 100) / total : 0;

            ESICDTO dto = new ESICDTO();
            dto.setTotalEsic(total);
            dto.setActiveEsic(active);
            dto.setEsicPercentage(percent);

            return dto;
        });

        dashboard.setESICList(list);
    }
    public String billVerificationStatus() {
	    return QueryFileWatcher.getQuery("DASHBOARD_LOAD_BILL_STATUS");
	}
    private void billVerificationStatus(DashboardDTO dashboard, String contractorIds) {
    	String sql = billVerificationStatus();
       // String sql = """
//            SELECT  
//                SUM(CASE WHEN cwc.status = 4 THEN 1 ELSE 0 END) AS ApprovedCount,
//                SUM(CASE WHEN cwc.status = 5 THEN 1 ELSE 0 END) AS RejectedCount,
//                SUM(CASE WHEN cwc.status = 3 THEN 1 ELSE 0 END) AS PendingCount,
//                COUNT(*) AS TotalCount
//            FROM CMSWageCostWorkFlow cwc
//            WHERE cwc.ContractorId IN (
//                SELECT TRY_CAST(value AS INT) FROM STRING_SPLIT(?, ',')
//            )
       // """;
    	  //String sql = "EXEC GetBillVerificationStatus ?";
        List<BillStatusDTO> list = jdbcTemplate.query(sql, new Object[]{contractorIds}, (rs, rowNum) -> {
            int approved = rs.getInt("ApprovedCount");
            int rejected = rs.getInt("RejectedCount");
            int pending = rs.getInt("PendingCount");
            int total = rs.getInt("TotalCount");

            int approvedPercent = total > 0 ? (approved * 100) / total : 0;
            int rejectedPercent = total > 0 ? (rejected * 100) / total : 0;
            int pendingPercent  = total > 0 ? (pending * 100) / total : 0;

            BillStatusDTO dto = new BillStatusDTO();
            dto.setApprovedCount(approved);
            dto.setRejectedCount(rejected);
            dto.setPendingCount(pending);
            dto.setTotalCount(total);
            dto.setApprovedPercent(approvedPercent);
            dto.setRejectedPercent(rejectedPercent);
            dto.setPendingPercent(pendingPercent);

            return dto;
        });

        dashboard.setBillList(list);
    }
    public String getAllowedPagesForRole() {
	    return QueryFileWatcher.getQuery("GET_ALLOWED_PAGES_FOR_ROLE_QUICKACTIONS");
	}
    @Override
    public List<String> getAllowedPagesForRole(String roleId) {
        //String sql = "EXEC GetAllowedPagesForRole ?"; // call stored procedure
    	String sql =getAllowedPagesForRole();
        return jdbcTemplate.query(sql, new Object[]{roleId}, (rs, rowNum) -> rs.getString("PageUrl"));
    }

    @Override
    public List<ContractorComplianceScoreDTO> getContractorComplianceScores(
            String unitIds,
            String contractorIds,
            LocalDate monthStart,
            LocalDate nextMonthStart) {

        if (unitIds == null || unitIds.isBlank()
                || contractorIds == null || contractorIds.isBlank()) {
            return Collections.emptyList();
        }

        //String sql = QueryFileWatcher.getQuery(
               // "DASHBOARD_CONTRACTOR_COMPLIANCE_SCOREBOARD");
        String sql="WITH AuthorizedUnits AS (\r\n"
        		+ "    SELECT DISTINCT\r\n"
        		+ "        TRY_CAST(value AS BIGINT) AS UnitId\r\n"
        		+ "    FROM STRING_SPLIT(?, ',')\r\n"
        		+ "    WHERE TRY_CAST(value AS BIGINT) IS NOT NULL\r\n"
        		+ "),\r\n"
        		+ "AuthorizedContractors AS (\r\n"
        		+ "    SELECT DISTINCT\r\n"
        		+ "        TRY_CAST(value AS BIGINT) AS ContractorId\r\n"
        		+ "    FROM STRING_SPLIT(?, ',')\r\n"
        		+ "    WHERE TRY_CAST(value AS BIGINT) IS NOT NULL\r\n"
        		+ "),\r\n"
        		+ "PFESICUploadsByUnit AS (\r\n"
        		+ "    SELECT DISTINCT\r\n"
        		+ "        g.UnitId,\r\n"
        		+ "        r.CONTRACTOR_ID AS ContractorId,\r\n"
        		+ "        r.RECON_TYPE AS ComplianceType,\r\n"
        		+ "        r.ID AS UploadId,\r\n"
        		+ "        r.UPLOADED_DATE AS UploadedDate\r\n"
        		+ "    FROM RECONCILIATION_UPLOAD r\r\n"
        		+ "    INNER JOIN RECONCILIATION_COMPLIANCE_DETAIL d\r\n"
        		+ "        ON d.UploadId = r.ID\r\n"
        		+ "    INNER JOIN GATEPASSMAIN g\r\n"
        		+ "        ON g.GatePassId = d.GatePassId\r\n"
        		+ "       AND g.ContractorId = r.CONTRACTOR_ID\r\n"
        		+ "    INNER JOIN AuthorizedUnits au\r\n"
        		+ "        ON au.UnitId = g.UnitId\r\n"
        		+ "    INNER JOIN AuthorizedContractors ac\r\n"
        		+ "        ON ac.ContractorId = r.CONTRACTOR_ID\r\n"
        		+ "    WHERE r.RECON_TYPE IN ('PF', 'ESIC')\r\n"
        		+ "      AND r.FromDate >= ?\r\n"
        		+ "      AND r.FromDate < ?\r\n"
        		+ "),\r\n"
        		+ "LatestPFESIC AS (\r\n"
        		+ "    SELECT\r\n"
        		+ "        UnitId,\r\n"
        		+ "        ContractorId,\r\n"
        		+ "        ComplianceType,\r\n"
        		+ "        UploadId,\r\n"
        		+ "        ROW_NUMBER() OVER (\r\n"
        		+ "            PARTITION BY\r\n"
        		+ "                UnitId,\r\n"
        		+ "                ContractorId,\r\n"
        		+ "                ComplianceType\r\n"
        		+ "            ORDER BY\r\n"
        		+ "                UploadedDate DESC,\r\n"
        		+ "                UploadId DESC\r\n"
        		+ "        ) AS RowNum\r\n"
        		+ "    FROM PFESICUploadsByUnit\r\n"
        		+ "),\r\n"
        		+ "PFESICScores AS (\r\n"
        		+ "    SELECT\r\n"
        		+ "        l.UnitId,\r\n"
        		+ "        l.ContractorId,\r\n"
        		+ "        l.ComplianceType,\r\n"
        		+ "        COUNT(DISTINCT d.DetailId) AS WorkmenCount,\r\n"
        		+ "        CAST(\r\n"
        		+ "            AVG(CAST(d.Rating AS DECIMAL(10,2)))\r\n"
        		+ "            AS DECIMAL(10,2)\r\n"
        		+ "        ) AS AverageRating\r\n"
        		+ "    FROM LatestPFESIC l\r\n"
        		+ "    INNER JOIN RECONCILIATION_COMPLIANCE_DETAIL d\r\n"
        		+ "        ON d.UploadId = l.UploadId\r\n"
        		+ "    WHERE l.RowNum = 1\r\n"
        		+ "      AND EXISTS (\r\n"
        		+ "          SELECT 1\r\n"
        		+ "          FROM GATEPASSMAIN g\r\n"
        		+ "          WHERE g.GatePassId = d.GatePassId\r\n"
        		+ "            AND g.ContractorId = l.ContractorId\r\n"
        		+ "            AND g.UnitId = l.UnitId\r\n"
        		+ "      )\r\n"
        		+ "    GROUP BY\r\n"
        		+ "        l.UnitId,\r\n"
        		+ "        l.ContractorId,\r\n"
        		+ "        l.ComplianceType\r\n"
        		+ "),\r\n"
        		+ "LatestBankWage AS (\r\n"
        		+ "    SELECT\r\n"
        		+ "        b.UploadId,\r\n"
        		+ "        b.PrincipalEmployerId AS UnitId,\r\n"
        		+ "        b.ContractorId,\r\n"
        		+ "        ROW_NUMBER() OVER (\r\n"
        		+ "            PARTITION BY\r\n"
        		+ "                b.PrincipalEmployerId,\r\n"
        		+ "                b.ContractorId\r\n"
        		+ "            ORDER BY\r\n"
        		+ "                b.UploadedDate DESC,\r\n"
        		+ "                b.UploadId DESC\r\n"
        		+ "        ) AS RowNum\r\n"
        		+ "    FROM BANK_WAGE_RECON_UPLOAD b\r\n"
        		+ "    INNER JOIN AuthorizedUnits au\r\n"
        		+ "        ON au.UnitId = b.PrincipalEmployerId\r\n"
        		+ "    INNER JOIN AuthorizedContractors ac\r\n"
        		+ "        ON ac.ContractorId = b.ContractorId\r\n"
        		+ "    WHERE b.WageMonth >= ?\r\n"
        		+ "      AND b.WageMonth < ?\r\n"
        		+ "),\r\n"
        		+ "BankWageScores AS (\r\n"
        		+ "    SELECT\r\n"
        		+ "        b.UnitId,\r\n"
        		+ "        b.ContractorId,\r\n"
        		+ "        'BANK_WAGE' AS ComplianceType,\r\n"
        		+ "        COUNT(d.DetailId) AS WorkmenCount,\r\n"
        		+ "        CAST(\r\n"
        		+ "            AVG(CAST(d.Rating AS DECIMAL(10,2)))\r\n"
        		+ "            AS DECIMAL(10,2)\r\n"
        		+ "        ) AS AverageRating\r\n"
        		+ "    FROM LatestBankWage b\r\n"
        		+ "    LEFT JOIN BANK_WAGE_RECON_DETAIL d\r\n"
        		+ "        ON d.UploadId = b.UploadId\r\n"
        		+ "    WHERE b.RowNum = 1\r\n"
        		+ "    GROUP BY\r\n"
        		+ "        b.UnitId,\r\n"
        		+ "        b.ContractorId\r\n"
        		+ "),\r\n"
        		+ " AllScores AS (\r\n"
        		+ "    SELECT\r\n"
        		+ "        UnitId,\r\n"
        		+ "        ContractorId,\r\n"
        		+ "        ComplianceType,\r\n"
        		+ "        WorkmenCount,\r\n"
        		+ "        AverageRating\r\n"
        		+ "    FROM PFESICScores\r\n"
        		+ "\r\n"
        		+ "    UNION ALL\r\n"
        		+ "\r\n"
        		+ "    SELECT\r\n"
        		+ "        UnitId,\r\n"
        		+ "        ContractorId,\r\n"
        		+ "        ComplianceType,\r\n"
        		+ "        WorkmenCount,\r\n"
        		+ "        AverageRating\r\n"
        		+ "    FROM BankWageScores\r\n"
        		+ ")\r\n"
        		+ "SELECT\r\n"
        		+ "    s.UnitId,\r\n"
        		+ "    pe.NAME AS UnitName,\r\n"
        		+ "    s.ContractorId,\r\n"
        		+ "    c.NAME AS ContractorName,\r\n"
        		+ "    s.ComplianceType,\r\n"
        		+ "    s.WorkmenCount,\r\n"
        		+ "    s.AverageRating\r\n"
        		+ "FROM AllScores s\r\n"
        		+ "LEFT JOIN CMSPRINCIPALEMPLOYER pe\r\n"
        		+ "    ON pe.UNITID = s.UnitId\r\n"
        		+ "LEFT JOIN CMSCONTRACTOR c\r\n"
        		+ "    ON c.ContractorId = s.ContractorId\r\n"
        		+ "ORDER BY\r\n"
        		+ "    s.UnitId,\r\n"
        		+ "    s.ContractorId,\r\n"
        		+ "    s.ComplianceType;\r\n"
        		+ "SELECT\r\n"
        		+ "    s.UnitId,\r\n"
        		+ "    pe.NAME AS UnitName,\r\n"
        		+ "    s.ContractorId,\r\n"
        		+ "    c.NAME AS ContractorName,\r\n"
        		+ "    s.ComplianceType,\r\n"
        		+ "    s.WorkmenCount,\r\n"
        		+ "    s.AverageRating\r\n"
        		+ "FROM AllScores s\r\n"
        		+ "LEFT JOIN CMSPRINCIPALEMPLOYER pe\r\n"
        		+ "    ON pe.UNITID = s.UnitId\r\n"
        		+ "LEFT JOIN CMSCONTRACTOR c\r\n"
        		+ "    ON c.ContractorId = s.ContractorId\r\n"
        		+ "ORDER BY\r\n"
        		+ "    s.UnitId,\r\n"
        		+ "    s.ContractorId,\r\n"
        		+ "    s.ComplianceType;\r\n"
        		+ "";

        return jdbcTemplate.query(
        	    sql,
        	    (rs, rowNum) -> {
        	        ContractorComplianceScoreDTO dto =
        	                new ContractorComplianceScoreDTO();

        	        dto.setUnitId(rs.getLong("UnitId"));
        	        dto.setContractorId(rs.getLong("ContractorId"));
        	        dto.setComplianceType(rs.getString("ComplianceType"));
        	        dto.setWorkmenCount(rs.getInt("WorkmenCount"));
        	        dto.setAverageRating(rs.getBigDecimal("AverageRating"));
        	        dto.setUnitName(rs.getString("UnitName"));
        	        dto.setContractorName(rs.getString("ContractorName"));

        	        return dto;
        	    },
        	    unitIds,
        	    contractorIds,
        	    java.sql.Date.valueOf(monthStart),
        	    java.sql.Date.valueOf(nextMonthStart),
        	    java.sql.Date.valueOf(monthStart),
        	    java.sql.Date.valueOf(nextMonthStart)
        	);
    }

}