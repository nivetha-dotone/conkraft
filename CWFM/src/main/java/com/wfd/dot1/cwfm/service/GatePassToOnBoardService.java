

package com.wfd.dot1.cwfm.service;

import com.wfd.dot1.cwfm.dto.*;
import com.wfd.dot1.cwfm.enums.EmployeeStatusType;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

@Service
public class GatePassToOnBoardService {
    private static final Logger log = LoggerFactory.getLogger(GatePassToOnBoardService.class.getName());
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public GatePassToOnBoardService() {
    }

    public String getGTByTrnsId() {
        return QueryFileWatcher.getQuery("GET_DETAILS_BY_TRANSACTIONID_QUERY");
    }

    public String getQuerytoFetchListNOTPOST() {
        return QueryFileWatcher.getQuery("getQuerytoFetchListNOTPOST");
    }

    public String getQueryInsertSuccessEnty() {
        return QueryFileWatcher.getQuery("getWFDLogOK");
    }

    public String getQueryUpdateWFDLogOK() {
        return QueryFileWatcher.getQuery("getupdateWFDLogOK");
    }

    public String getQueryInsertNotSuccess() {
        return QueryFileWatcher.getQuery("getWFDLognotOK");
    }

    public String getQueryUpdateWFDNotSuccess() {
        return QueryFileWatcher.getQuery("getupdateWFDLognotOK");
    }

    public String getSKILLSByTrnsId() {
        return QueryFileWatcher.getQuery("GET_SKILL_DETAILS_BY_TRANSACTIONID_QUERY");
    }

    public String getSKILSPROBygpId() {
        return QueryFileWatcher.getQuery("GET_SKILLS_PRO");
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



    public void saveSuccessTrace(Long gpTransactionId,
                                 Long personId,
                                 Integer statusNumber,
                                 Boolean postFlag) {

        String sql = getQueryInsertSuccessEnty();

        jdbcTemplate.update(sql,
                gpTransactionId,
                personId,
                statusNumber
                );
    }


    public void updateSuccessTrace(Long gpTransactionId,
                                   Long personId,
                                   Integer statusNumber,
                                   Boolean flag) {
//UPDATE WFDOnbinTrace SET PersonId = ?, StatusNumber = ?, Postflag = 1, ErrorResponse = NULL, UpdatedDate = SYSDATETIME() WHERE GPTranscationId = ?;

        String sql = getQueryUpdateWFDLogOK();

        jdbcTemplate.update(sql,
                personId,
                statusNumber,

                gpTransactionId);
    }

    public void updateErrorTrace(Long gpTransactionId,
                                 Integer statusNumber,
                                 String errorResponse) {

        String sql = getQueryUpdateWFDNotSuccess();

        jdbcTemplate.update(sql,
                statusNumber,
                errorResponse,
                gpTransactionId);
    }




    public void saveErrorTrace(Long gpTransactionId,
                               Integer statusNumber,
                               String errorResponse) {

        String sql = getQueryInsertNotSuccess();

        jdbcTemplate.update(sql,
                gpTransactionId,
                statusNumber,
                errorResponse,
                false);
    }




    public List<String> getListOfTrReScheduleOnb( ){
        try{
            log.info("Fetching TranscationId list for reschedule to post ");
            List<String> dtoTrList = new LinkedList<>();
            String queryGetOnBdByTranId  = getQuerytoFetchListNOTPOST();
            log.info("query to get onboardDetails "+ queryGetOnBdByTranId);
            SqlRowSet sqlRowSet = this.jdbcTemplate.queryForRowSet(queryGetOnBdByTranId);
            while(sqlRowSet.next()){
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



    public ActiveEmpStatusDto updateEmpStatusTr(String id,
                                                EmployeeStatusType empStatus) {

        try {
            log.info("Fetching updateEmpStatus for status: {}", empStatus);

            String query = getQueryByStatus(empStatus);
            if (query == null) return null;

            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query, id);

            if (!rowSet.next()) return null;

            String effectiveDate = rowSet.getString("effectiveDate");

            return buildEmpStatusDto(empStatus, effectiveDate);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private ActiveEmpStatusDto buildEmpStatusDto(EmployeeStatusType status,
                                                 String effectiveDate) {

        ActiveEmpStatusDto dto = new ActiveEmpStatusDto();
        ActiveEmpStatusDto.PersonInformation personInfo =
                new ActiveEmpStatusDto.PersonInformation();

        // Custom Data
        personInfo.setCustomDataList(
                List.of(buildCustomData(status.name()))
        );

        // Employment Status
        String employmentName =
                (status == EmployeeStatusType.DEBLACKLIST ||
                        status == EmployeeStatusType.UNBLOCK)
                        ? "Active"
                        : "Terminated";

        personInfo.setEmploymentStatusList(
                List.of(buildEmploymentStatus(employmentName, effectiveDate))
        );

        // Licenses
        personInfo.setPersonLicenseTypes(buildLicenseTypes());

        dto.setPersonInformation(personInfo);

        return dto;
    }


    private String getQueryByStatus(EmployeeStatusType status) {
        return switch (status) {
            case BLACKLIST -> getQueryOfBlacklist();
            case BLOCK -> getQueryOfBlock();
            case CANCEL -> getQueryOfCancel();
            case DEBLACKLIST -> getQueryOfDeblack();
            case UNBLOCK -> getQueryOfDeblock();
        };
    }

    private ActiveEmpStatusDto.CustomData buildCustomData(String value) {
        ActiveEmpStatusDto.CustomData data =
                new ActiveEmpStatusDto.CustomData();
        data.setCustomDataTypeName("Workmen Type");
        data.setText(value);
        return data;
    }
    private ActiveEmpStatusDto.EmploymentStatus buildEmploymentStatus(
            String name, String date) {

        ActiveEmpStatusDto.EmploymentStatus status =
                new ActiveEmpStatusDto.EmploymentStatus();
        status.setEmploymentStatusName(name);
        status.setEffectiveDate(date);
        return status;
    }

    private List<ActiveEmpStatusDto.PersonLicenseType> buildLicenseTypes() {

        return List.of(
                buildLicense("Employee"),
                buildLicense("Absence"),
                buildLicense("Hourly Timekeeping"),
                buildLicense("Scheduling")
        );
    }

    private ActiveEmpStatusDto.PersonLicenseType buildLicense(String name) {
        ActiveEmpStatusDto.PersonLicenseType license =
                new ActiveEmpStatusDto.PersonLicenseType();
        license.setLicenseTypeName(name);
        license.setActiveFlag(true);
        return license;
    }



//
//    public ActiveEmpStatusDto updateEmpStatusAc(String id) {
//        try {
//            log.info("Fetching Updated ternimate or active or deblock  Data");
//            String skillQuery = this.getSKILSPROBygpId();
//            SqlRowSet sqlRowSet = this.jdbcTemplate.queryForRowSet(skillQuery, new Object[]{id});
//
//            if(sqlRowSet.next()) {
//                ActiveEmpStatusDto empStatusDto = new ActiveEmpStatusDto();
//                ActiveEmpStatusDto.PersonInformation personInformation= new ActiveEmpStatusDto.PersonInformation();
//
//                List<ActiveEmpStatusDto.EmploymentStatus> employmentStatusList = new ArrayList<>();
//                ActiveEmpStatusDto.EmploymentStatus employmentStatus= new ActiveEmpStatusDto.EmploymentStatus();
//                employmentStatus.setEmploymentStatusName(sqlRowSet.getString(""));
//
//
//                ActiveEmpStatusDto.UserAccountStatus userAccountStatus= new ActiveEmpStatusDto.UserAccountStatus();
//
//                ActiveEmpStatusDto.Person person= new ActiveEmpStatusDto.Person();
//                ActiveEmpStatusDto.PersonLicenseType personLicenseType= new ActiveEmpStatusDto.PersonLicenseType();
//
//
//
//
//                }
//
//            if (assignmentList.isEmpty()) {
//                return null;
//            } else {
//                PersonSkillAssignmentDTO requestDTO = new PersonSkillAssignmentDTO();
//                requestDTO.setAssignments(assignmentList);
//                log.info("Exit from assignmentCertific method");
//                return requestDTO;
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

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
}
