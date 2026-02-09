package com.wfd.dot1.cwfm.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dto.GatePassToOnBoard;
import com.wfd.dot1.cwfm.dto.PostSkillWfd;
import com.wfd.dot1.cwfm.dto.SkillProLevelDateDTO;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Service
public class GatePassToOnBoardService  {

    private static final Logger log = LoggerFactory.getLogger(GatePassToOnBoardService.class.getName());


    @Autowired
    private JdbcTemplate jdbcTemplate;



    public String getGTByTrnsId() {
        return QueryFileWatcher.getQuery("WORKMEN_EXPORT_ONBOARDING_UKG");
    }

    public String getSkillQuery() {
        return QueryFileWatcher.getQuery("getSkillQuery");
    }

    
    public PostSkillWfd createSkills(Integer id){
        try{
            log.info("Fetching Skills URL");

            String skillQuery = getSkillQuery();

            SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(skillQuery, id);
            PostSkillWfd postSkillWfd = null;

            if(sqlRowSet.next()){
              postSkillWfd=  new PostSkillWfd();

              postSkillWfd.setName(sqlRowSet.getString("GMDESCRIPTION"));

                String gmname = sqlRowSet.getString("GMNAME");

                gmname.trim().charAt(4);

            }
            log.info("Exit from create skill method");
            return postSkillWfd;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String getDetailsByTransactionIDQuery() {
        return QueryFileWatcher.getQuery("GET_DETAILS_BY_TRANSACTIONID_QUERY");
    }
    public GatePassToOnBoard getIndividualOnBoardDetailsByTrnId(String trnsId ){
        try{
            log.info("Fetching Onboarding Details to create Employee UKG by Passing GatePassToOnBoard dao ");
            GatePassToOnBoard dto = null;
            String queryGetOnBdByTranId =getDetailsByTransactionIDQuery();
            //String queryGetOnBdByTranId  = "select gpm.gatepasstypeid,gpm.GatePassId,gpm.FirstName,gpm.LastName,'' as middleName, '' as shortName,'Employee FAP' as accessProfileName, 'Employee' as preferenceProfileName, 'Empty Profile' as professionalPayCodeName,'Empty Profile' as professionalWorkRuleName,'Empty Profile' as shiftCodeName,20.15 as hourlyRate, 'CW BAR MALE PR' as payRuleName,'Bharthi' as supervisorName,'BR0001' as supervisorPersonNumber ,'Default' as logonProfileName,'' as addressEmail,'Work' as contactTypeName,'' as badgeNumber,FORMAT(CAST(GETDATE() AS DATE), 'yyyy-MM-dd') AS hireDate,FORMAT(CAST(gpm.DOB AS DATE), 'yyyy-MM-dd') as birthDate,gpm.MobileNumber as phone1,mu.ContactNumber as phone2,'' as phone3,'' as email, REPLACE(gpm.Address, ',', ' ') AS Address, '' as city,'' as State, '' as postalCode,'' as country,'Active' as employmentStatus,FORMAT(CAST(GETDATE() AS DATE), 'yyyy-MM-dd') as employmentStatusEffectiveDate,mu.userAccount AS ReportingManager,'' as workType,gpm.GatePassId as userAccountName,'Active' as userAccountStatus,'Kronos@531' as userPassword,cpe.ORGANIZATION as company,cpe.CODE as location,'Main Plant' as plantLocation,cgm.GMNAME as department,cgm1.GMNAME as section,'General' as subSection,cc.CODE as contractorCode,'' as home8,'' as home9,wo.SAP_WORKORDER_NUM as category,'' as supervisorId,'' as costCenter,'' as workorder,'' as home4,''as home5,'' as home6,FORMAT(CAST(GETDATE() AS DATE), 'yyyy-MM-dd') as categoryEffectiveDate,cgm2.GMNAME as gender,gpm.AadharNumber,CONCAT(COALESCE(gpm.FirstName, ''), ' ', COALESCE(gpm.LastName, '')) AS aadharName,gpm.RelativeName, REPLACE(gpm.Address, ',', ' ') AS Address,'na' as permanentDistrict,'na' as permanentState,'' as permanentPincode,gpm.IdMark,gpm.UanNumber,CASE     WHEN gpm.MaritalStatus = 'Married' THEN 'MARRIED'  WHEN gpm.MaritalStatus = 'Single' THEN 'UNMARRIED' ELSE gpm.MaritalStatus END AS MaritalStatus,gpm.Technical,cgm3.GMNAME as academic,'' as shoesize,cgm4.GMNAME as bloodGroup,'Labor Supply' as workmenType,gpm.NatureOfJob,ccwc.WC_CODE as WcEsicNo,'' as panNumber,'xxxxx00000000000000000' as pfNumber,gpm.AccountNumber,'' as bankName,gpm.IfscCode ,'' as future2,'' as future3,'' as future4,'' as future5,'' as future6,'' as future7,'' as future8,''as futureDate1,'' as futureDate2,'' as futureDate3, '' as futureDate4, '' as futureDate5,ct.GMNAME as skill,cs.GMNAME as proLevel,FORMAT(CAST(GETDATE() AS DATE), 'yyyy-MM-dd') as skillDate,'' as cert1,'' as cert1StartDate,'' as cert1EndDate, '' as cert2,'' as cert2StartDate,'' as cert2EndDate,'' as cert3 ,'' as cert3StartDate,'' as cert3EndDate,'' as cert4, '' as cert4StartDate,'' as cert4EndDate from GATEPASSMAIN gpm join CMSPRINCIPALEMPLOYER cpe on cpe.UNITID=gpm.UnitId join MASTERUSER mu on mu.UserId=gpm.UpdatedBy join CMSWORKORDER wo on wo.WORKORDERID=gpm.WorkorderId join CMSCONTRACTOR cc on cc.CONTRACTORID=gpm.ContractorId join CMSCONTRACTOR_WC ccwc on ccwc.WCID = gpm.WcEsicNo join CMSGENERALMASTER ct on ct.GMID = gpm.TradeId JOIN CMSGENERALMASTER cs on cs.GMID = gpm.SkillId join CMSGENERALMASTER cgm on cgm.GMID=gpm.DepartmentId join CMSGENERALMASTER cgm1 on cgm1.GMID=gpm.AreaId join CMSGENERALMASTER cgm2 on cgm2.GMID=gpm.Gender left join CMSGENERALMASTER cgm3 on cgm3.GMID=gpm.AcademicId left join CMSGENERALMASTER cgm4 on cgm4.GMID=gpm.BloodGroupId where gpm.GatePassStatus='4' and gpm.GatePassTypeId in ('1','2') and gpm.TransactionId = ?	";
            log.info("query to get onboardDetails "+ queryGetOnBdByTranId);
            SqlRowSet rs = jdbcTemplate.queryForRowSet(queryGetOnBdByTranId, trnsId);
            if(rs.next()){
                dto=new GatePassToOnBoard();
                dto.setGatePassTypeId(Integer.valueOf(rs.getString("gatepasstypeid")));
                dto.setGatePassId(rs.getString("GatePassId"));
                dto.setFirstName(rs.getString("FirstName"));
                dto.setLastName(rs.getString("LastName"));
                dto.setLastName(rs.getString("LastName"));
                dto.setAccessProfileName(rs.getString("accessProfileName"));
                dto.setPreferenceProfileName(rs.getString("preferenceProfileName"));
                dto.setProfessionalPayCodeName(rs.getString("professionalPayCodeName"));
                dto.setProfessionalWorkRuleName(rs.getString("professionalWorkRuleName"));
                dto.setShiftCodeName(rs.getString("shiftCodeName"));
                dto.setAddressEmail(rs.getString("addressEmail"));
                dto.setContactTypeName(rs.getString("contactTypeName"));
                dto.setHourlyRate(rs.getDouble("hourlyRate"));
                dto.setPayRuleName(rs.getString("payRuleName"));
                dto.setSupervisorName(rs.getString("supervisorName"));
                dto.setSupervisorPersonNumber(rs.getString("supervisorPersonNumber"));
                dto.setLogonProfileName(rs.getString("logonProfileName"));
                dto.setAddressEmail(rs.getString("addressEmail"));
                dto.setContactTypeName(rs.getString("contactTypeName"));
                dto.setEmploymentStatus(rs.getString("employmentStatus"));
                dto.setEmploymentStatusEffectiveDate(rs.getString("employmentStatusEffectiveDate"));

//                **************************************Additional Information************************************************

                dto.setGender(rs.getString("gender"));
                dto.setAadharNumber(rs.getString("AadharNumber"));
                dto.setAadharName(rs.getString("aadharName"));
                dto.setRelativeName(rs.getString("RelativeName"));
                dto.setAddress(rs.getString("Address"));
                dto.setPermanentDistrict(rs.getString("permanentDistrict"));
                dto.setPermanentState(rs.getString("permanentState"));
                dto.setPermanentPincode(rs.getString("permanentPincode"));
                dto.setIdMark(rs.getString("IdMark"));
                dto.setUanNumber(rs.getString("UanNumber"));
                dto.setMaritalStatus(rs.getString("MaritalStatus"));
                dto.setTechnical(rs.getString("Technical"));
                dto.setAcademic(rs.getString("academic"));
                dto.setShoeSize(rs.getString("shoesize"));
                dto.setBloodGroup(rs.getString("bloodGroup"));
                dto.setWorkmenType(rs.getString("workmenType"));
                dto.setNatureOfJob(rs.getString("NatureOfJob"));
//                dto.setWcEsicNo(rs.getString("WcEsicNo")); IP number
                dto.setPanNumber(rs.getString("panNumber"));
                dto.setPfNumber(rs.getString("pfNumber"));
                dto.setAccountNumber(rs.getString("AccountNumber"));
                dto.setBankName(rs.getString("bankName"));
                dto.setIfscCode(rs.getString("IfscCode"));
//                dto.(rs.getString("IfscCode")); PayRoll Id
                dto.setCompany(rs.getString("company"));
                dto.setLocation(rs.getString("location"));
                dto.setPlantLocation(rs.getString("plantLocation"));
                dto.setDepartment(rs.getString("department"));
                dto.setSection(rs.getString("section"));
                dto.setSubSection(rs.getString("subSection"));
                dto.setContractorCode(rs.getString("contractorCode"));
                dto.setCategory(rs.getString("category"));

                dto.setHireDate(rs.getString("hireDate"));
                dto.setBirthDate(rs.getString("birthDate"));
                dto.setPhone1(rs.getString("phone1"));
                dto.setPhone2(rs.getString("phone2"));
                dto.setEmail(rs.getString("email"));
                dto.setAddress(rs.getString("Address"));
                dto.setUserAccountName(rs.getString("userAccountName"));
                dto.setUserAccountStatus(rs.getString("userAccountStatus"));
                dto.setUserPassword(rs.getString("userPassword"));






            }
            log.info("Exit from getIndividualOnBoardDetailsByTrnId dao method");
            return dto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String getSkillDetailsByTransactionIDQuery() {
        return QueryFileWatcher.getQuery("GET_SKILL_DETAILS_BY_TRANSACTIONID_QUERY");
    }
    public SkillProLevelDateDTO getOnlySkillProByTrnId(String trnsId ){
        try{
            log.info("Fetching Onboarding Details to create Employee UKG by Passing GatePassToOnBoard dao ");
            String queryGetOnBdByTranId  = getSkillDetailsByTransactionIDQuery();
            		//"select gpm.gatepasstypeid,gpm.GatePassId,gpm.FirstName,gpm.LastName,'' as middleName, '' as shortName,'Employee FAP' as accessProfileName, 'Employee' as preferenceProfileName, 'Empty Profile' as professionalPayCodeName,'Empty Profile' as professionalWorkRuleName,'Empty Profile' as shiftCodeName,20.15 as hourlyRate, 'CW BAR MALE PR' as payRuleName,'Bharthi' as supervisorName,'BR0001' as supervisorPersonNumber ,'Default' as logonProfileName,'' as addressEmail,'Work' as contactTypeName,'' as badgeNumber,FORMAT(CAST(GETDATE() AS DATE), 'yyyy-MM-dd') AS hireDate,FORMAT(CAST(gpm.DOB AS DATE), 'yyyy-MM-dd') as birthDate,gpm.MobileNumber as phone1,mu.ContactNumber as phone2,'' as phone3,'' as email, REPLACE(gpm.Address, ',', ' ') AS Address, '' as city,'' as State, '' as postalCode,'' as country,'Active' as employmentStatus,FORMAT(CAST(GETDATE() AS DATE), 'yyyy-MM-dd') as employmentStatusEffectiveDate,mu.userAccount AS ReportingManager,'' as workType,gpm.GatePassId as userAccountName,'Active' as userAccountStatus,'Kronos@531' as userPassword,cpe.ORGANIZATION as company,cpe.CODE as location,'Main Plant' as plantLocation,cgm.GMNAME as department,cgm1.GMNAME as section,'General' as subSection,cc.CODE as contractorCode,'' as home8,'' as home9,wo.SAP_WORKORDER_NUM as category,'' as supervisorId,'' as costCenter,'' as workorder,'' as home4,''as home5,'' as home6,FORMAT(CAST(GETDATE() AS DATE), 'yyyy-MM-dd') as categoryEffectiveDate,cgm2.GMNAME as gender,gpm.AadharNumber,CONCAT(COALESCE(gpm.FirstName, ''), ' ', COALESCE(gpm.LastName, '')) AS aadharName,gpm.RelativeName, REPLACE(gpm.Address, ',', ' ') AS Address,'na' as permanentDistrict,'na' as permanentState,'' as permanentPincode,gpm.IdMark,gpm.UanNumber,CASE     WHEN gpm.MaritalStatus = 'Married' THEN 'MARRIED'  WHEN gpm.MaritalStatus = 'Single' THEN 'UNMARRIED' ELSE gpm.MaritalStatus END AS MaritalStatus,gpm.Technical,cgm3.GMNAME as academic,'' as shoesize,cgm4.GMNAME as bloodGroup,'Labor Supply' as workmenType,gpm.NatureOfJob,ccwc.WC_CODE as WcEsicNo,'' as panNumber,'xxxxx00000000000000000' as pfNumber,gpm.AccountNumber,'' as bankName,gpm.IfscCode ,'' as future2,'' as future3,'' as future4,'' as future5,'' as future6,'' as future7,'' as future8,''as futureDate1,'' as futureDate2,'' as futureDate3, '' as futureDate4, '' as futureDate5,ct.GMNAME as skill,cs.GMNAME as proLevel,FORMAT(CAST(GETDATE() AS DATE), 'yyyy-MM-dd') as skillDate,'' as cert1,'' as cert1StartDate,'' as cert1EndDate, '' as cert2,'' as cert2StartDate,'' as cert2EndDate,'' as cert3 ,'' as cert3StartDate,'' as cert3EndDate,'' as cert4, '' as cert4StartDate,'' as cert4EndDate from GATEPASSMAIN gpm join CMSPRINCIPALEMPLOYER cpe on cpe.UNITID=gpm.UnitId join MASTERUSER mu on mu.UserId=gpm.UpdatedBy join CMSWORKORDER wo on wo.WORKORDERID=gpm.WorkorderId join CMSCONTRACTOR cc on cc.CONTRACTORID=gpm.ContractorId join CMSCONTRACTOR_WC ccwc on ccwc.WCID = gpm.WcEsicNo join CMSGENERALMASTER ct on ct.GMID = gpm.TradeId JOIN CMSGENERALMASTER cs on cs.GMID = gpm.SkillId join CMSGENERALMASTER cgm on cgm.GMID=gpm.DepartmentId join CMSGENERALMASTER cgm1 on cgm1.GMID=gpm.AreaId join CMSGENERALMASTER cgm2 on cgm2.GMID=gpm.Gender join CMSGENERALMASTER cgm3 on cgm3.GMID=gpm.AcademicId join CMSGENERALMASTER cgm4 on cgm4.GMID=gpm.BloodGroupId where gpm.GatePassStatus='4' and gpm.GatePassTypeId in ('1','2') and gpm.TransactionId = ?	";
            log.info("query to get onboardDetails "+ queryGetOnBdByTranId);
            SqlRowSet rs = jdbcTemplate.queryForRowSet(queryGetOnBdByTranId, trnsId);
            SkillProLevelDateDTO addSkillPro=null;
            if(rs.next()){

                addSkillPro = new SkillProLevelDateDTO();

                addSkillPro.setPersonNumber(rs.getString("GatePassId"));
                addSkillPro.setSkill(rs.getString("skill"));
                addSkillPro.setProficiencyLevel(rs.getString("proLevel"));
                addSkillPro.setEffectiveDate(rs.getString("skillDate"));

            }
            log.info("Exit from getIndividualOnBoardDetailsByTrnId dao method");
            return  addSkillPro;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }





}
