

package com.wfd.dot1.cwfm.service;

import com.wfd.dot1.cwfm.dto.*;
import com.wfd.dot1.cwfm.enums.EmployeeStatusType;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

@Service
public class GatePassToOnBoardService {
    private static final Logger log = LoggerFactory.getLogger(GatePassToOnBoardService.class.getName());
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private WfdEmployeeService wfdEmployeeService;

    public GatePassToOnBoardService() {
    }

    public String getGTByTrnsId() {
        return QueryFileWatcher.getQuery("GET_DETAILS_BY_TRANSACTIONID_QUERY");
    }

    public String getListOfTrEmpStatus() {
        return QueryFileWatcher.getQuery("getALLWorkmenTerminated");
    }

    public String getGTByTrnsIdProject() {
        return QueryFileWatcher.getQuery("GET_DETAILS_BY_TRANSACTIONID_QUERY_PROJ");
    }

    public String getQuerytoFetchListNOTPOST() {
        return QueryFileWatcher.getQuery("getQuerytoFetchListNOTPOST");
    }

    public String getQueryInsertSuccessEnty() {
        return QueryFileWatcher.getQuery("getWFDLogOK");
    }

    public String getQueryInsertSuccessEntyTr() {
        return QueryFileWatcher.getQuery("getWFDLogOKTr");
    }

    public String getQueryUpdateWFDLogOK() {
        return QueryFileWatcher.getQuery("getupdateWFDLogOK");
    }

    public String getQueryInsertNotSuccess() {
        return QueryFileWatcher.getQuery("getWFDFLognotOK");
    }

    public String getQueryInsertNotSuccessTr() {
        return QueryFileWatcher.getQuery("getWFDFLognotOKTr");
    }

    public String getQueryInsertNotSuccesstrNot() {
        return QueryFileWatcher.getQuery("getWFDLognotOKtrNot");
    }

    public String getQueryInsertNotSuccesstrNotTr() {
        return QueryFileWatcher.getQuery("getWFDLognotOKtrNotTr");
    }

    public String getQueryUpdateWFDNotSuccess() {
        return QueryFileWatcher.getQuery("getupdateWFDLognotOK");
    }

    public String getSKILLSByTrnsId() {
        return QueryFileWatcher.getQuery("GET_SKILL_DETAILS_BY_TRANSACTIONID_QUERY");
    }

    public String getSKILLSByTrnsIdPro() {
        return QueryFileWatcher.getQuery("GET_DETAILS_BY_TRANSACTIONID_QUERY_PROJ");
    }

    public String getSKILSPROBygpId() {
        return QueryFileWatcher.getQuery("GET_SKILLS_PRO");
    }

    public String getQueryMailerDiscuss() {
        return QueryFileWatcher.getQuery("getMailerDiscuss");
    }

    public String getQueryMailerDiscussLL() {
        return QueryFileWatcher.getQuery("getMailerDiscussLL");
    }

    public String getQueryLocationPresentOrNotInDB() {
        return QueryFileWatcher.getQuery("getLocationPathCheck");
    }

     public String getQueryCreateLocation() {
        return QueryFileWatcher.getQuery("INSERTBSIN");
    }

    public String getQueryHrEmailByUnitName() {
        return QueryFileWatcher.getQuery("getHrMailByunitName");
    }

    public String getQueryOfBlacklist() {
        return QueryFileWatcher.getQuery("GET_OffBoarding_QueryOfBlacklist");
    }

    public String getQueryOfCancel() {
        return QueryFileWatcher.getQuery("GET_OffBoarding_QueryOfCancel");
    }

    public String getQueryOfBlock() {
        return QueryFileWatcher.getQuery("GET_OffBoarding_QueryOfBlock");
    }

    public String getQueryOfDeblack() {
        return QueryFileWatcher.getQuery("GET_OffBoarding_QueryOfDeblack");
    }

    public String getQueryOfDeblock() {
        return QueryFileWatcher.getQuery("GET_OffBoarding_QueryOfDeblock");
    }

    public String getSkillQuery() {
        return QueryFileWatcher.getQuery("getSkillQuery");
    }
    public String getISSAND() {
        return QueryFileWatcher.getQuery("ISSAND");
    }
    public String getCSMWorkOrderNumber() {
        return QueryFileWatcher.getQuery("getCMSWorkOrder");
    }
    public String getLAborCategory() {
        return QueryFileWatcher.getQuery("getLaborCategory");
    }

    public String getCertiteQuery() {
        return QueryFileWatcher.getQuery("getassCertificateQuery");
    }

    public PostSkillWfd createSkills(Integer id) {
        try {
            log.info("Fetching Certification URL");
            String skillQuery = this.getSkillQuery();
            SqlRowSet sqlRowSet = this.jdbcTemplate.queryForRowSet(skillQuery, new Object[]{id});
            PostSkillWfd postSkillWfd = null;
            if (sqlRowSet.next()) {
                postSkillWfd = new PostSkillWfd();
                postSkillWfd.setName(sqlRowSet.getString("GMNAME"));
                String gmname = sqlRowSet.getString("GMNAME");
                gmname.trim().charAt(4);
            }

            log.info("Exit from create skill method");
            return postSkillWfd;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public PostJobWfd createJob(Integer id) {
        try{

            String issandorpoc = getISSAND();

            if (issandorpoc != null) {
                issandorpoc = issandorpoc.trim();
            }

            if ("yes".equalsIgnoreCase(issandorpoc)) {

            } else if ("no".equalsIgnoreCase(issandorpoc)) {
                log.info("Fetching Skill for job URL");
                String skillQuery = this.getSkillQuery();
                SqlRowSet sqlRowSet = this.jdbcTemplate.queryForRowSet(skillQuery, new Object[]{id});
                PostJobWfd postSkillWfd = null;
                if (sqlRowSet.next()) {
                    postSkillWfd = new PostJobWfd();
                    postSkillWfd.setName(sqlRowSet.getString("GMNAME"));
                    postSkillWfd.setEffectiveDate("1900-01-01");
                    postSkillWfd.setExpirationDate("3000-01-01");
                }

                log.info("Exit from create  job method");
                return postSkillWfd;

            }else {

            }



        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }


    public PostJobWfd createJobByname(String id) {
        try{

            String issandorpoc = getISSAND();

            if (issandorpoc != null) {
                issandorpoc = issandorpoc.trim();
            }

            if ("yes".equalsIgnoreCase(issandorpoc)) {

            } else if ("no".equalsIgnoreCase(issandorpoc)) {
                log.info("Fetching Skill for job URL");
               PostJobWfd postSkillWfd = null;
                if (id!=null && !id.isEmpty()) {
                    postSkillWfd = new PostJobWfd();
                    postSkillWfd.setName(id);
                    postSkillWfd.setEffectiveDate("1900-01-01");
                    postSkillWfd.setExpirationDate("3000-01-01");
                }

                log.info("Exit from create  job method");
                return postSkillWfd;

            }else {

            }



        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public PostLaborCatDTO createLaborCategory(String id) {
        try {
            log.info("fetching SAP Work Order to Labor Category record");
            String workOrder = this.getCSMWorkOrderNumber();
            SqlRowSet sqlRowSet = this.jdbcTemplate.queryForRowSet(workOrder, new Object[]{id});

            PostLaborCatDTO postLaborCateWfd = null;
            if (sqlRowSet.next() && !getLAborCategory().isEmpty()) {
                postLaborCateWfd = new PostLaborCatDTO();
                postLaborCateWfd.setName(sqlRowSet.getString("SAP_WORKORDER_NUM"));
                PostLaborCatDTO.LaborCategory laborCategory = new PostLaborCatDTO.LaborCategory();
                laborCategory.setQualifier(getLAborCategory());
                postLaborCateWfd.setLaborCategory(laborCategory);
                postLaborCateWfd.setInactive(false);
            }

            log.info("Exit from SAP Work Order to Labor Category record");
            return postLaborCateWfd;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public PostLaborCatDTO createLaborCategoryDto(String workOrder) {
        try {
            log.info("fetching SAP Work Order to Labor Category record");

            PostLaborCatDTO postLaborCateWfd = null;
                postLaborCateWfd = new PostLaborCatDTO();
                postLaborCateWfd.setName(workOrder);
                PostLaborCatDTO.LaborCategory laborCategory = new PostLaborCatDTO.LaborCategory();
                laborCategory.setQualifier(getLAborCategory());
                postLaborCateWfd.setLaborCategory(laborCategory);
                postLaborCateWfd.setInactive(false);

            log.info("Exit from SAP Work Order to Labor Category record");
            return postLaborCateWfd;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ProficiencyDTO createProf(Integer id) {
        try {
            log.info("Fetching Prof URL");
            String skillQuery = this.getSkillQuery();
            SqlRowSet sqlRowSet = this.jdbcTemplate.queryForRowSet(skillQuery, new Object[]{id});
            ProficiencyDTO postProWfd = null;
            if (sqlRowSet.next()) {
                postProWfd = new ProficiencyDTO();
                postProWfd.setName(sqlRowSet.getString("GMDESCRIPTION"));
                postProWfd.setId(329);
                postProWfd.setActive(true);
                postProWfd.setProficiencyLevelNumeric(329);
                postProWfd.setVersion(0);
            }

            log.info("Exit from create skill method");
            return postProWfd;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveSuccessTrace(Long gpTransactionId, Long personId, Integer statusNumber) {
        String sql = this.getQueryInsertSuccessEnty();
        this.jdbcTemplate.update(sql, new Object[]{gpTransactionId, personId, statusNumber});
    }

    public void saveSuccessTraceTr(String gpTransactionId, Integer statusNumber) {
        String sql = this.getQueryInsertSuccessEntyTr();
        this.jdbcTemplate.update(sql, new Object[]{gpTransactionId, statusNumber});
    }

    public void updateSuccessTrace(Long gpTransactionId, Long personId, Integer statusNumber, Boolean flag) {
        String sql = this.getQueryUpdateWFDLogOK();
        this.jdbcTemplate.update(sql, new Object[]{personId, statusNumber, gpTransactionId});
    }

    public void updateErrorTrace(Long gpTransactionId, Integer statusNumber, String errorResponse, Integer flag) {
        String sql = this.getQueryUpdateWFDNotSuccess();
        this.jdbcTemplate.update(sql, new Object[]{statusNumber, errorResponse, flag, gpTransactionId});
    }

    public void saveErrorTrace(Long gpTransactionId, Integer statusNumber, String errorResponse) {
        String sql = this.getQueryInsertNotSuccess();
        this.jdbcTemplate.update(sql, new Object[]{gpTransactionId, statusNumber, errorResponse});
    }

    public void saveErrorTraceTr(String gpTransactionId, Integer statusNumber, String errorResponse) {
        String sql = this.getQueryInsertNotSuccessTr();
        this.jdbcTemplate.update(sql, new Object[]{gpTransactionId, statusNumber, errorResponse});
    }

    public void saveErrorTraceTrNOT(Long gpTransactionId, Integer statusNumber, String errorResponse) {
        String sql = this.getQueryInsertNotSuccesstrNot();
        this.jdbcTemplate.update(sql, new Object[]{gpTransactionId, statusNumber, errorResponse});
    }

    public List<String> getListOfTrReScheduleOnb() {
        try {
            log.info("Fetching TranscationId list for reschedule to post ");
            List<String> dtoTrList = new LinkedList();
            String queryGetOnBdByTranId = this.getQuerytoFetchListNOTPOST();
            log.info("query to get onboardDetails " + queryGetOnBdByTranId);
            SqlRowSet sqlRowSet = this.jdbcTemplate.queryForRowSet(queryGetOnBdByTranId);

            while(sqlRowSet.next()) {
                String trId = sqlRowSet.getString("GPTranscationId");
                dtoTrList.add(trId);
            }

            log.info("Exit from getListOfTrReScheduleOnb method");
            return dtoTrList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PostSkillWfd createCertifi(Integer id) {
        try {
            log.info("Fetching Prof URL");
            String skillQuery = this.getSkillQuery();
            SqlRowSet sqlRowSet = this.jdbcTemplate.queryForRowSet(skillQuery, new Object[]{id});
            PostSkillWfd postProWfd = null;
            if (sqlRowSet.next()) {
                postProWfd = new PostSkillWfd();
                postProWfd.setName(sqlRowSet.getString("GMDESCRIPTION"));
            }

            log.info("Exit from create skill method");
            return postProWfd;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CertificationAssignmentRequestDTO assignmentCertific(String id) {
        try {
            log.info("Fetching Certification Data");
            String skillQuery = this.getCertiteQuery();
            SqlRowSet sqlRowSet = this.jdbcTemplate.queryForRowSet(skillQuery, new Object[]{id});
            ArrayList<CertificationAssignmentRequestDTO.Assignment> assignmentList = new ArrayList();

            while(sqlRowSet.next()) {
                CertificationAssignmentRequestDTO.Certification certification = new CertificationAssignmentRequestDTO.Certification();
                certification.setQualifier(sqlRowSet.getString("CertificationName"));
                CertificationAssignmentRequestDTO.ProficiencyLevel proficiencyLevel = new CertificationAssignmentRequestDTO.ProficiencyLevel();
                proficiencyLevel.setQualifier(sqlRowSet.getString("Proficiency"));
                CertificationAssignmentRequestDTO.Assignment assignment = new CertificationAssignmentRequestDTO.Assignment();
                assignment.setCertification(certification);
                assignment.setProficiencyLevel(proficiencyLevel);
                LocalDate grantDate = sqlRowSet.getDate("GrantDate").toLocalDate();
                LocalDate expiryDate = sqlRowSet.getDate("ExpiryDate").toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                assignment.setEffectiveDate(grantDate.format(formatter));
                assignment.setExpirationDate(expiryDate.format(formatter));
                assignmentList.add(assignment);
            }

            if (assignmentList.isEmpty()) {
                return null;
            } else {
                CertificationAssignmentRequestDTO requestDTO = new CertificationAssignmentRequestDTO();
                requestDTO.setAssignments(assignmentList);
                log.info("Exit from assignmentCertific method");
                return requestDTO;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PersonSkillAssignmentDTO assignmentSkillsPro(String id) {
        try {
            log.info("Fetching Skills Pro Data");
            String skillQuery = this.getSKILSPROBygpId();
            SqlRowSet sqlRowSet = this.jdbcTemplate.queryForRowSet(skillQuery, new Object[]{id});
            ArrayList<PersonSkillAssignmentDTO.Assignment> assignmentList = new ArrayList();

            while(sqlRowSet.next()) {
                PersonSkillAssignmentDTO.Skill skill = new PersonSkillAssignmentDTO.Skill();
                skill.setQualifier(sqlRowSet.getString("SkillName"));
                PersonSkillAssignmentDTO.ProficiencyLevel proficiencyLevel = new PersonSkillAssignmentDTO.ProficiencyLevel();
                proficiencyLevel.setQualifier(sqlRowSet.getString("ProficiencyLevel"));
                PersonSkillAssignmentDTO.Assignment assignment = new PersonSkillAssignmentDTO.Assignment();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                assignment.setEffectiveDate(sqlRowSet.getString("skillDate"));
                assignment.setActive(true);
                assignment.setProficiencyLevel(proficiencyLevel);
                assignment.setSkill(skill);
                assignmentList.add(assignment);
            }

            if (assignmentList.isEmpty()) {
                return null;
            } else {
                PersonSkillAssignmentDTO requestDTO = new PersonSkillAssignmentDTO();
                requestDTO.setAssignments(assignmentList);
                log.info("Exit from assignmentCertific method");
                return requestDTO;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ActiveEmpStatusDto> updateEmpStatusTrSchedule() {
        try {
            String query = this.getListOfTrEmpStatus();
            if (query == null) {
                return null;
            } else {
                SqlRowSet rowSet = this.jdbcTemplate.queryForRowSet(query);
                List<ActiveEmpStatusDto> list = new ArrayList();

                while(rowSet.next()) {
                    ActiveEmpStatusDto dto = this.buildEmpStatusDtoSchedular(rowSet.getString("GatePassId"), rowSet.getString("DOT"));
                    list.add(dto);
                }

                return list.isEmpty() ? null : list;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ActiveEmpStatusDto buildEmpStatusDtoSchedular(String gpId, String effectiveDate) {
        ActiveEmpStatusDto dto = new ActiveEmpStatusDto();
        ActiveEmpStatusDto.PersonInformation personInfo = new ActiveEmpStatusDto.PersonInformation();
        String employmentName = "Terminated";
        personInfo.setEmploymentStatusList(List.of(this.buildEmploymentStatus(employmentName, effectiveDate)));
        personInfo.setPerson(this.buildPersonData(gpId));
        personInfo.setPersonLicenseTypes(this.buildLicenseTypes());
        dto.setPersonInformation(personInfo);
        return dto;
    }

    public ActiveEmpStatusDto updateEmpStatusTr(String id, EmployeeStatusType empStatus) {
        try {
            log.info("Fetching updateEmpStatus for status: {}", empStatus);
            String query = this.getQueryByStatus(empStatus);
            if (query == null) {
                return null;
            } else {
                SqlRowSet rowSet = this.jdbcTemplate.queryForRowSet(query, new Object[]{id});
                if (!rowSet.next()) {
                    return null;
                } else {
                    String effectiveDate = rowSet.getString("effectiveDate");
                    return this.buildEmpStatusDto(empStatus, effectiveDate);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ActiveEmpStatusDto buildEmpStatusDto(EmployeeStatusType status, String effectiveDate) {
        ActiveEmpStatusDto dto = new ActiveEmpStatusDto();
        ActiveEmpStatusDto.PersonInformation personInfo = new ActiveEmpStatusDto.PersonInformation();
        personInfo.setCustomDataList(List.of(this.buildCustomData(status.name())));
        String employmentName = status != EmployeeStatusType.DEBLACKLIST && status != EmployeeStatusType.UNBLOCK ? "Terminated" : "Active";
        personInfo.setEmploymentStatusList(List.of(this.buildEmploymentStatus(employmentName, effectiveDate)));
        personInfo.setPersonLicenseTypes(this.buildLicenseTypes());
        dto.setPersonInformation(personInfo);
        return dto;
    }

    private String getQueryByStatus(EmployeeStatusType status) {
        String var10000;
        switch (status) {
            case BLACKLIST -> var10000 = this.getQueryOfBlacklist();
            case BLOCK -> var10000 = this.getQueryOfBlock();
            case CANCEL -> var10000 = this.getQueryOfCancel();
            case DEBLACKLIST -> var10000 = this.getQueryOfDeblack();
            case UNBLOCK -> var10000 = this.getQueryOfDeblock();
            default -> throw new MatchException((String)null, (Throwable)null);
        }

        return var10000;
    }

    private ActiveEmpStatusDto.CustomData buildCustomData(String value) {
        ActiveEmpStatusDto.CustomData data = new ActiveEmpStatusDto.CustomData();
        data.setCustomDataTypeName("Workmen Type");
        data.setText(value);
        return data;
    }

    private ActiveEmpStatusDto.Person buildPersonData(String value) {
        ActiveEmpStatusDto.Person data = new ActiveEmpStatusDto.Person();
        data.setPersonNumber(value);
        return data;
    }

    private ActiveEmpStatusDto.EmploymentStatus buildEmploymentStatus(String name, String date) {
        ActiveEmpStatusDto.EmploymentStatus status = new ActiveEmpStatusDto.EmploymentStatus();
        status.setEmploymentStatusName(name);
        status.setEffectiveDate(date);
        return status;
    }

    private List<ActiveEmpStatusDto.PersonLicenseType> buildLicenseTypes() {
        return List.of(this.buildLicense("Employee"), this.buildLicense("Absence"), this.buildLicense("Hourly Timekeeping"), this.buildLicense("Scheduling"));
    }

    private ActiveEmpStatusDto.PersonLicenseType buildLicense(String name) {
        ActiveEmpStatusDto.PersonLicenseType license = new ActiveEmpStatusDto.PersonLicenseType();
        license.setLicenseTypeName(name);
        license.setActiveFlag(true);
        return license;
    }

    public GatePassToOnBoard getIndividualOnBoardDetailsByTrnId(String trnsId) {
        try {
            log.info("Fetching Onboarding Details to create Employee UKG by Passing GatePassToOnBoard dao ");
            GatePassToOnBoard dto = null;
            String queryGetOnBdByTranId = this.getGTByTrnsId();
            log.info("query to get onboardDetails " + queryGetOnBdByTranId);
            SqlRowSet rs = this.jdbcTemplate.queryForRowSet(queryGetOnBdByTranId, new Object[]{trnsId});
            if (rs.next()) {
                dto = new GatePassToOnBoard();
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
                dto.setPanNumber(rs.getString("panNumber"));
                dto.setPfNumber(rs.getString("pfNumber"));
                dto.setAccountNumber(rs.getString("AccountNumber"));
                dto.setBankName(rs.getString("bankName"));
                dto.setIfscCode(rs.getString("IfscCode"));
                dto.setCompany(rs.getString("company"));
                dto.setLocation(rs.getString("location"));
                dto.setSkill(rs.getString("skill"));
                dto.setProLevel(rs.getString("proLevel"));
                dto.setSkillDate(rs.getString("skillDate"));
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

    public GatePassToOnBoard getIndividualOnBoardDetailsByTrnIdPro(String trnsId) {
        try {
            log.info("Fetching Onboarding Details to create Employee UKG by Passing GatePassToOnBoard dao ");
            GatePassToOnBoard dto = null;
            String queryGetOnBdByTranId = this.getGTByTrnsIdProject();
            log.info("query to get onboardDetails " + queryGetOnBdByTranId);
            SqlRowSet rs = this.jdbcTemplate.queryForRowSet(queryGetOnBdByTranId, new Object[]{trnsId});
            if (rs.next()) {
                dto = new GatePassToOnBoard();
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
                dto.setPanNumber(rs.getString("panNumber"));
                dto.setPfNumber(rs.getString("pfNumber"));
                dto.setAccountNumber(rs.getString("AccountNumber"));
                dto.setBankName(rs.getString("bankName"));
                dto.setIfscCode(rs.getString("IfscCode"));
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

    public SkillProLevelDateDTO getOnlySkillProByTrnId(String trnsId) {
        try {
            log.info("Fetching Onboarding Details to create Employee UKG by Passing GatePassToOnBoard dao ");
            String queryGetOnBdByTranId = this.getSKILLSByTrnsId();
            log.info("query to get onboardDetails " + queryGetOnBdByTranId);
            SqlRowSet rs = this.jdbcTemplate.queryForRowSet(queryGetOnBdByTranId, new Object[]{trnsId});
            SkillProLevelDateDTO addSkillPro = null;
            if (rs.next()) {
                addSkillPro = new SkillProLevelDateDTO();
                addSkillPro.setPersonNumber(rs.getString("GatePassId"));
                addSkillPro.setSkill(rs.getString("skill"));
                addSkillPro.setProficiencyLevel(rs.getString("proLevel"));
                addSkillPro.setEffectiveDate(rs.getString("skillDate"));
            }

            log.info("Exit from getIndividualOnBoardDetailsByTrnId dao method");
            return addSkillPro;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<WorkOrderDTOMail> getExpiringWorkOrders() {
        try {
            log.info("Work Order Expriry record fetch");
            String sql = this.getQueryMailerDiscuss();
            return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
                WorkOrderDTOMail dto = new WorkOrderDTOMail();
                dto.setContractorId(rs.getLong("CONTRACTORID"));
                dto.setCode(rs.getString("CODE"));
                dto.setUnitCode(rs.getString("unitcode"));
                dto.setUnitName(rs.getString("UnitName"));
                dto.setContractor(rs.getString("Contractor"));
                dto.setConEmail(rs.getString("ContractorMail"));
                dto.setWorkOrderId(rs.getLong("WORKORDERID"));
                dto.setSapWorkOrderNum(rs.getString("SAP_WORKORDER_NUM"));
                dto.setValidDt(rs.getString("VALIDDT"));
                return dto;
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Set<String> getHrMailByunitName(String unitName) {
        try {
            log.info("Work Order Expriry record fetch");
            String sql = this.getQueryHrEmailByUnitName();
            SqlRowSet rs = this.jdbcTemplate.queryForRowSet(sql, new Object[]{unitName});
            Set<String> mailSends = new HashSet();

            while(rs.next()) {
                String string = rs.getString("EmailId");
                mailSends.add(string);
            }

            return mailSends;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<WorkOrderDTOMail> getExpiringLL() {
        try {
            log.info("Work Order Expriry record fetch");
            String sql = this.getQueryMailerDiscussLL();
            return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
                WorkOrderDTOMail dto = new WorkOrderDTOMail();
                dto.setContractorId(rs.getLong("CONTRACTORID"));
                dto.setCode(rs.getString("CODE"));
                dto.setUnitCode(rs.getString("unitcode"));
                dto.setUnitName(rs.getString("UnitName"));
                dto.setContractor(rs.getString("Contractor"));
                dto.setConEmail(rs.getString("ContractorMail"));
                dto.setWorkOrderId(rs.getLong("WONUMBER"));
                dto.setSapWorkOrderNum(rs.getString("LICENSE_NUMBER"));
                dto.setValidDt(rs.getString("WC_TO_DTM"));
                return dto;
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    public boolean checkLocationPath(String fullLocationPath) {
//        try {
//            String sql = this.getQueryLocationPresentOrNotInDB();
//            return (Integer)this.jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{fullLocationPath}) > 0;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }



    public boolean checkLocationPath(String path) {
        try {
            String sql = getQueryLocationPresentOrNotInDB();

            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, path);

            if (rs.next()) {
                int count = rs.getInt(1); // first column
                return count > 0;
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }

    public void storeHierarchyInDB(String fullPath) {

        String[] parts = fullPath.split("/");
        String queryCreateLocation = getQueryCreateLocation();
        for (int i = parts.length; i > 0; i--) {

            String path = String.join("/", Arrays.copyOfRange(parts, 0, i));

            if (!checkLocationPath(path)) {

                String locationName = parts[i - 1];
                String locationType = getLocationType(i - 1);

                jdbcTemplate.update(
                        queryCreateLocation,
                        path, locationType
                );
            }
        }
    }


    public void createBusinessStructure(String fullPath) {

        String[] parts = fullPath.split("/");

        String currentPath = "";

        for (int i = 0; i < parts.length; i++) {

            String nodeName = parts[i];

            // Build path step-by-step
            currentPath = currentPath.isEmpty() ? nodeName : currentPath + "/" + nodeName;

            boolean exists = wfdEmployeeService.checkLocationInUKG(currentPath);

            if (!exists) {

                String parentPath = (i == 0) ? "/" : currentPath.substring(0, currentPath.lastIndexOf("/"));

                String type = getLocationType(i);

                wfdEmployeeService.createNodeInUKG(parentPath, nodeName, type);
                System.out.println("Creating Node → Parent: " + parentPath + " | Name: " + nodeName + " | Type: " + type);
                log.info("Creating Node → Parent: " + parentPath + " | Name: " + nodeName + " | Type: " + type);

                // 🔁 Optional: verify after create
                boolean created = wfdEmployeeService.checkLocationInUKG(currentPath);

                if (!created) {
                    throw new RuntimeException("Failed to create node: " + currentPath);
                }
            }
        }
    }


    public void createBusinessStructurePOC(String fullPath) {

        String[] parts = fullPath.split("/");

        String currentPath = "";

        for (int i = 0; i < parts.length; i++) {

            String nodeName = parts[i];

            // Build path step-by-step
            currentPath = currentPath.isEmpty() ? nodeName : currentPath + "/" + nodeName;

            boolean exists = wfdEmployeeService.checkLocationInUKG(currentPath);

            if (!exists) {

                String parentPath = (i == 0) ? "/" : currentPath.substring(0, currentPath.lastIndexOf("/"));

                String type = getLocationTypePOC(i);

                wfdEmployeeService.createNodeInUKG(parentPath, nodeName, type);
                System.out.println("Creating Node → Parent: " + parentPath + " | Name: " + nodeName + " | Type: " + type);
                log.info("Creating Node → Parent: " + parentPath + " | Name: " + nodeName + " | Type: " + type);

                // 🔁 Optional: verify after create
                boolean created = wfdEmployeeService.checkLocationInUKG(currentPath);

                if (!created) {
                    throw new RuntimeException("Failed to create node: " + currentPath);
                }
            }
        }
    }

    public String getLocationType(int level) {

        switch (level) {
            case 0: return "Company";
            case 1: return "Location";
            case 2: return "Site";
            case 3: return "Department";
            case 4: return "Sub Department";
            case 5: return "Product";
            case 6: return "Contractor";
            case 7: return "Job";
            default: return "Job";
        }
    }

    public String getLocationTypePOC(int level) {

        switch (level) {
            case 0: return "Business Entity";
            case 1: return "Location";
            case 2: return "Department";
            case 3: return "Area";
            case 4: return "Contractor";
            case 5: return "Job";
            default: return "Job";
        }
    }

}
