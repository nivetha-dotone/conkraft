package com.wfd.dot1.cwfm.service;


import com.wfd.dot1.cwfm.dto.GatePassToOnBoard;
import com.wfd.dot1.cwfm.dto.PostSkillWfd;
import com.wfd.dot1.cwfm.dto.SkillProLevelDateDTO;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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


    public GatePassToOnBoard getIndividualOnBoardDetailsByTrnId(String trnsId ){
        try{
            log.info("Fetching Onboarding Details to create Employee UKG by Passing GatePassToOnBoard dao ");
            GatePassToOnBoard dto = null;
            String queryGetOnBdByTranId  = getGTByTrnsId();
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



    public SkillProLevelDateDTO getOnlySkillProByTrnId(String trnsId ){
        try{
            log.info("Fetching Onboarding Details to create Employee UKG by Passing GatePassToOnBoard dao ");
            String queryGetOnBdByTranId  = getGTByTrnsId();
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
