
package com.wfd.dot1.cwfm.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.dto.*;
import com.wfd.dot1.cwfm.enums.EmployeeStatusType;
import com.wfd.dot1.cwfm.enums.GatePassType;
import com.wfd.dot1.cwfm.pojo.GatePassMain;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeMapper {
    @Autowired
    private WfdEmployeeService wfdEmployeeService;
    @Autowired
    private GatePassToOnBoardService gatePassToOnBoardService;
    @Autowired
    private EmailService emailService;
    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(GatePassToOnBoardService.class.getName());

    public EmployeeMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String getRegardsEmail() {
        return QueryFileWatcher.getQuery("getRegards");
    }

    public String gatePassEmpDtoStatic(String GatePassId) {
        try {
            this.gatePassToOnBoardService.getIndividualOnBoardDetailsByTrnId(GatePassId);
            EmployeeRequestDTO dto = new EmployeeRequestDTO();
            EmployeeRequestDTO.PersonInformation personInfo = new EmployeeRequestDTO.PersonInformation();
            EmployeeRequestDTO.AccessAssignment access = new EmployeeRequestDTO.AccessAssignment();
            access.setAccessProfileName("Employee FAP");
            access.setPreferenceProfileName("Employee");
            access.setProfessionalPayCodeName("Empty Profile");
            access.setProfessionalWorkRuleName("Empty Profile");
            access.setShiftCodeName("Empty Profile");
            personInfo.setAccessAssignment(access);
            EmployeeRequestDTO.EmailAddress email = new EmployeeRequestDTO.EmailAddress();
            email.setAddress("");
            email.setContactTypeName("Work");
            email.setHasEmailNotificationDelivery(false);
            personInfo.setEmailAddresses(Arrays.asList(email));
            EmployeeRequestDTO.EmploymentStatus empStatus = new EmployeeRequestDTO.EmploymentStatus();
            empStatus.setEffectiveDate("2025-08-15");
            empStatus.setEmploymentStatusName("Active");
            empStatus.setExpirationDate("3000-01-01");
            personInfo.setEmploymentStatusList(Arrays.asList(empStatus));
            EmployeeRequestDTO.Person person = new EmployeeRequestDTO.Person();
            person.setBirthDate("2000-08-29");
            person.setFirstName("ritesh");
            person.setLastName("malhar");
            person.setFullName("malhar, ritesh");
            person.setHireDate("2025-08-15");
            person.setPersonNumber(GatePassId);
            person.setShortName("malharR");
            personInfo.setPerson(person);
            EmployeeRequestDTO.PersonAuthenticationType auth = new EmployeeRequestDTO.PersonAuthenticationType();
            auth.setActiveFlag(true);
            auth.setAuthenticationTypeName("Basic");
            personInfo.setPersonAuthenticationTypes(Arrays.asList(auth));
            EmployeeRequestDTO.PersonLicenseType licenseEmployee = new EmployeeRequestDTO.PersonLicenseType();
            licenseEmployee.setActiveFlag(true);
            licenseEmployee.setLicenseTypeName("Employee");
            EmployeeRequestDTO.PersonLicenseType licenseAbsence = new EmployeeRequestDTO.PersonLicenseType();
            licenseAbsence.setActiveFlag(true);
            licenseAbsence.setLicenseTypeName("Absence");
            EmployeeRequestDTO.PersonLicenseType licensehourlyTimekeeping = new EmployeeRequestDTO.PersonLicenseType();
            licensehourlyTimekeeping.setActiveFlag(true);
            licensehourlyTimekeeping.setLicenseTypeName("Hourly Timekeeping");
            EmployeeRequestDTO.PersonLicenseType licenseScheduling = new EmployeeRequestDTO.PersonLicenseType();
            licenseScheduling.setActiveFlag(true);
            licenseScheduling.setLicenseTypeName("Scheduling");
            personInfo.setPersonLicenseTypes(Arrays.asList(licenseEmployee, licenseAbsence, licensehourlyTimekeeping, licenseScheduling));
            EmployeeRequestDTO.UserAccountStatus userStatus = new EmployeeRequestDTO.UserAccountStatus();
            userStatus.setEffectiveDate("2025-08-15");
            userStatus.setExpirationDate("3000-01-01");
            userStatus.setUserAccountStatusName("Active");
            personInfo.setUserAccountStatusList(Arrays.asList(userStatus));
            dto.setPersonInformation(personInfo);
            EmployeeRequestDTO.JobAssignment job = new EmployeeRequestDTO.JobAssignment();
//            EmployeeRequestDTO.BaseWageRate wage = new EmployeeRequestDTO.BaseWageRate();
//            wage.setEffectiveDate("2025-08-15");
//            wage.setExpirationDate("3000-01-01");
//            wage.setHourlyRate(20.15);
//            job.setBaseWageRates(Arrays.asList(wage));
            EmployeeRequestDTO.JobAssignmentDetails jobDetails = new EmployeeRequestDTO.JobAssignmentDetails();
            jobDetails.setPayRuleName("CW BAR MALE PR");
            jobDetails.setSupervisorName("Bharthi");
            jobDetails.setSupervisorPersonNumber("BR0001");
            jobDetails.setTimeZoneName("(GMT +05:30) Calcutta");
            job.setJobAssignmentDetails(jobDetails);
            EmployeeRequestDTO.PrimaryLaborAccount labor = new EmployeeRequestDTO.PrimaryLaborAccount();
            labor.setEffectiveDate("2025-08-15");
            labor.setExpirationDate("3000-01-01");
            labor.setOrganizationPath("DOT1 Solutions Pvt Ltd/Banglore/Main Plant/IT/IT/General/Bravispach/Team Lead");
            job.setPrimaryLaborAccounts(Arrays.asList(labor));
            dto.setJobAssignment(job);
            EmployeeRequestDTO.User user = new EmployeeRequestDTO.User();
            EmployeeRequestDTO.UserAccount userAcc = new EmployeeRequestDTO.UserAccount();
            userAcc.setLogonProfileName("Default");
            userAcc.setUserName("ritesh.malhar");
            userAcc.setUserPassword("Kronos@123");
            user.setUserAccount(userAcc);
            dto.setUser(user);
            String employee = this.wfdEmployeeService.createEmployee(dto);
            return employee != null ? employee : "issue into mapping method";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String postSkillTowfd(Integer gmId) {
        try {
            PostSkillWfd skills = this.gatePassToOnBoardService.createSkills(gmId);
            String issandorpoc = getISSANDORPOC();
            String orgPath = "";
            if (issandorpoc != null) {
                issandorpoc = issandorpoc.trim();

            }
            boolean b = this.wfdEmployeeService.verifySkillsInWFD(skills.getName());

            if ("no".equalsIgnoreCase(issandorpoc)) {
                boolean checkJob = this.wfdEmployeeService.verifyJobInWFD(skills.getName(),"1999-01-01");
                if(!checkJob){
                    postJobTowfd(gmId);
                }

            }
            if (skills == null) {
                return "Skill is not present";
            } else if (b) {
                return "already in the WFD";
            } else if (skills != null) {
                String skillsInWFD = this.wfdEmployeeService.createSkillsInWFD(skills);
                return skillsInWFD;
            } else {
                return "Skill is not present";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String postJobTowfd(Integer gmId) {
        try {
            PostJobWfd skills = this.gatePassToOnBoardService.createJob(gmId);
            boolean b = this.wfdEmployeeService.verifyJobInWFD(skills.getName(),skills.getEffectiveDate());
            if (skills == null) {
                return "Job is not present";
            } else if (b) {
                return "already in the WFD";
            } else if (skills != null) {
                String skillsInWFD = this.wfdEmployeeService.createJobInWFD(skills);
                return skillsInWFD;
            } else {
                return "Job is not present";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String postToLaborCate(String gmId) {
        try {
            PostLaborCatDTO laborCategory = this.gatePassToOnBoardService.createLaborCategory(gmId);
            boolean b = this.wfdEmployeeService.verifyLaborCatEnInWFD(laborCategory.getName());
            if (laborCategory == null ) {
                return "Workorder ID is not present table";
            } else if (b) {
                return "Labor Category already in the WFD";
            } else if (laborCategory != null) {
                String skillsInWFD = this.wfdEmployeeService.createLaborCatInWFD(laborCategory);
                return skillsInWFD;
            } else {
                return "Labor Category is not posted in wfd";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String postProfTowfd(Integer gmId) {
        try {
            ProficiencyDTO prof = this.gatePassToOnBoardService.createProf(gmId);
            boolean b = this.wfdEmployeeService.verifyProfInWFD(prof.getName());
            if (prof == null) {
                return "Prof Level is not present";
            } else if (b) {
                return "already in the WFD";
            } else if (prof != null) {
                String skillsInWFD = this.wfdEmployeeService.createProfInWFD(prof);
                return skillsInWFD;
            } else {
                return "Prof Level is not present";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled( cron = "0 */15 * * * *" )
    @Transactional
    public synchronized void gatePassEmpDtoSchedular() {
        try {
            List<String> listOfTrReScheduleOnb = this.gatePassToOnBoardService.getListOfTrReScheduleOnb();
            if (listOfTrReScheduleOnb == null || listOfTrReScheduleOnb.isEmpty()) {
                return;
            }

            for (String gpTransactionId : listOfTrReScheduleOnb) {
                System.out.println("Processing: " + gpTransactionId);

                String result = this.gatePassEmpDtoDynamic(gpTransactionId);

                if (result == null) {
                    this.gatePassToOnBoardService.updateErrorTrace(Long.valueOf(gpTransactionId), 404, "Transaction Id Not Found", 1);

                } else if (result.matches("\\d+")) {
                    Long personKey = Long.parseLong(result);
                    this.gatePassToOnBoardService.updateSuccessTrace(Long.valueOf(gpTransactionId), personKey, 200, true);

                } else if (result.startsWith("STATUS:")) {
                    String[] parts = result.split("\n", 2);
                    int statusCode = Integer.parseInt(parts[0].replace("STATUS:", "").trim());
                    String body = parts.length > 1 ? parts[1] : "";

                    Integer flag = (body.contains("WCO-101520") && body.contains("ID already exists")) ||
                            body.contains("Transaction Id Not Found") ? 1 : 0;

                    this.gatePassToOnBoardService.updateErrorTrace(Long.valueOf(gpTransactionId), statusCode, body, flag);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    @Scheduled(
            cron = "0 */15 * * * *"
    )
    @Transactional
    public synchronized void gatePassEmpDtoSchedularUpdate() {
        try {
            List<String> listOfTrReScheduleOnb = this.gatePassToOnBoardService.getListOfTrReScheduleOnbUpdate();
            if (listOfTrReScheduleOnb == null || listOfTrReScheduleOnb.isEmpty()) {
                return;
            }

            for (String gpTransactionId : listOfTrReScheduleOnb) {
                System.out.println("Processing: " + gpTransactionId);

                String result = this.updatePassEmpDtoDynamic(gpTransactionId);

                if (result == null) {
                    this.gatePassToOnBoardService.updateErrorTraceUpdateON(Long.valueOf(gpTransactionId), 404, "Transaction Id Not Found", 1);

                } else if (result.matches("\\d+")) {
                    Long personKey = Long.parseLong(result);
                    this.gatePassToOnBoardService.updateSuccessTraceUPON(Long.valueOf(gpTransactionId), personKey, 200, true);

                } else if (result.startsWith("STATUS:")) {
                    String[] parts = result.split("\n", 2);
                    int statusCode = Integer.parseInt(parts[0].replace("STATUS:", "").trim());
                    String body = parts.length > 1 ? parts[1] : "";

                    Integer flag = (body.contains("WCO-101520") && body.contains("ID already exists")) ||
                            body.contains("Transaction Id Not Found") ? 1 : 0;

                    this.gatePassToOnBoardService.updateErrorTraceUpdateON(Long.valueOf(gpTransactionId), statusCode, body, flag);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public String postCertificTowfd(Integer gmId) {
        try {
            PostSkillWfd certifi = this.gatePassToOnBoardService.createCertifi(gmId);
            boolean b = this.wfdEmployeeService.verifyCertiInWFD(certifi.getName());
            if (certifi == null) {
                return "Certifiction ID is not present CWFM";
            } else if (b) {
                return "already in the WFD";
            } else if (certifi != null) {
                String skillsInWFD = this.wfdEmployeeService.createCertiInWFD(certifi);
                return skillsInWFD;
            } else {
                return "Certifiction ID is not present CWFM";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String assignmentTowfd(String gmId) {
        try {
            CertificationAssignmentRequestDTO postSkillWfd = this.gatePassToOnBoardService.assignmentCertific(gmId);

            for(CertificationAssignmentRequestDTO.Assignment assignment : postSkillWfd.getAssignments()) {
                String qualifier = assignment.getCertification().getQualifier();
                boolean b = this.wfdEmployeeService.verifyCertiInWFD(qualifier);
                if (!b) {
                    PostSkillWfd postSkillWfd1 = new PostSkillWfd();
                    postSkillWfd1.setName(qualifier);
                    String createSkillResponse = this.wfdEmployeeService.createCertiInWFD(postSkillWfd1);
                    if (!this.wfdEmployeeService.verifyCertiInWFD(qualifier)) {
                        return "Cretificate creation failed: " + createSkillResponse;
                    }
                }

                String proQualifier = assignment.getProficiencyLevel().getQualifier();
                boolean b1 = this.wfdEmployeeService.verifyProfInWFD(proQualifier);
                if (!b1) {
                    ProficiencyDTO profDto = new ProficiencyDTO();
                    profDto.setId(329);
                    profDto.setActive(true);
                    profDto.setProficiencyLevelNumeric(329);
                    profDto.setVersion(0);
                    profDto.setName(proQualifier);
                    String createProfResponse = this.wfdEmployeeService.createProfInWFD(profDto);
                    if (!this.wfdEmployeeService.verifyProfInWFD(proQualifier)) {
                        return "Proficiency creation failed: " + createProfResponse;
                    }
                }
            }

            String s = this.wfdEmployeeService.AssignCertificateInWFD(postSkillWfd, gmId);
            return s;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String assignmentSkillsProTowfd(String gmId) {
        try {
            PersonSkillAssignmentDTO personSkillAssignmentDTO = this.gatePassToOnBoardService.assignmentSkillsPro(gmId);

            for(PersonSkillAssignmentDTO.Assignment assignment : personSkillAssignmentDTO.getAssignments()) {
                String skillName = assignment.getSkill().getQualifier();
                boolean b = this.wfdEmployeeService.verifySkillsInWFD(skillName);
                if (!b) {
                    PostSkillWfd postSkill = new PostSkillWfd();
                    postSkill.setName(skillName);
                    String createSkillResponse = this.wfdEmployeeService.createSkillsInWFD(postSkill);
                    if (!this.wfdEmployeeService.verifySkillsInWFD(skillName)) {
                        return "Skill creation failed: " + createSkillResponse;
                    }
                }

                String proQualifier = assignment.getProficiencyLevel().getQualifier();
                boolean b1 = this.wfdEmployeeService.verifyProfInWFD(proQualifier);
                if (!b1) {
                    ProficiencyDTO profDto = new ProficiencyDTO();
                    profDto.setId(329);
                    profDto.setActive(true);
                    profDto.setProficiencyLevelNumeric(329);
                    profDto.setVersion(0);
                    profDto.setName(proQualifier);
                    String createProfResponse = this.wfdEmployeeService.createProfInWFD(profDto);
                    if (!this.wfdEmployeeService.verifyProfInWFD(proQualifier)) {
                        return "Proficiency creation failed: " + createProfResponse;
                    }
                }
            }

            String s = this.wfdEmployeeService.AssignSkillsProInWFD(personSkillAssignmentDTO, gmId);
            return s;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String updateEmpstatusTrorAc(String gmId, EmployeeStatusType empStatus) {
        ActiveEmpStatusDto dto = this.gatePassToOnBoardService.updateEmpStatusTr(gmId, empStatus);
        return dto == null ? "Employment status not updated" : this.wfdEmployeeService.updateEmpStatusTarminate(dto, gmId);
    }


    @Scheduled(
            cron = "0 0 10 * * ?"
    )
    public Map<String, List<String>> updateEmpstatusTrSchedule() {
        List<ActiveEmpStatusDto> dtoList = this.gatePassToOnBoardService.updateEmpStatusTrSchedule();
        Map<String, List<String>> result = new HashMap();
        List<String> successList = new ArrayList();
        List<String> failedList = new ArrayList();
        if (dtoList != null && !dtoList.isEmpty()) {
            for(ActiveEmpStatusDto dt : dtoList) {
                String gpId = dt.getPersonInformation().getPerson().getPersonNumber();
                String gpTransactionId = gpId;

                try {
                    WfdResponse res = this.wfdEmployeeService.updateEmpStatusTarminateSch(dt, gpId);
                    if (res.isSuccess()) {
                        successList.add("GPID: " + gpId + " → Updated Successfully");
                        this.gatePassToOnBoardService.saveSuccessTraceTr(gpTransactionId, 200);
                    } else {
                        failedList.add("GPID: " + gpId + " → Failed: " + res.getMessage());
                        this.gatePassToOnBoardService.saveErrorTraceTr(gpTransactionId, 500, res.getMessage());
                    }
                } catch (Exception e) {
                    failedList.add("GPID: " + gpId + " → Exception: " + e.getMessage());
                    if (gpId != null) {
                        this.gatePassToOnBoardService.saveErrorTraceTr(gpId, 500, e.getMessage());
                    }
                }
            }

            result.put("SUCCESS", successList);
            result.put("FAILED", failedList);
            return result;
        } else {
            failedList.add("No Employment records found");
            result.put("FAILED", failedList);
            return result;
        }
    }


    @Scheduled(
            cron = "0 */15 * * * *"
    )
    @Transactional
    public synchronized Map<String, List<String>> updateEmpstatusTrScheduleTrace() {
        List<ActiveEmpStatusDto> dtoList = this.gatePassToOnBoardService.updateEmpStatusTrScheduleTrace();
        Map<String, List<String>> result = new HashMap();
        List<String> successList = new ArrayList();
        List<String> failedList = new ArrayList();
        if (dtoList != null && !dtoList.isEmpty()) {
            for(ActiveEmpStatusDto dt : dtoList) {
                String gpId = dt.getPersonInformation().getPerson().getPersonNumber();
                String gpTransactionId = gpId;

                try {
                    WfdResponse res = this.wfdEmployeeService.updateEmpStatusTarminateSch(dt, gpId);
                    if (res.isSuccess()) {
                        successList.add("GPID: " + gpId + " → Updated Successfully");
                        this.gatePassToOnBoardService.saveSuccessTraceTrTrace(gpTransactionId, 200);
                    } else {
                        failedList.add("GPID: " + gpId + " → Failed: " + res.getMessage());
                        this.gatePassToOnBoardService.saveErrorTraceTrTrace( gpTransactionId,500, res.getMessage());
                    }
                } catch (Exception e) {
                    failedList.add("GPID: " + gpId + " → Exception: " + e.getMessage());
                    if (gpId != null) {
                        this.gatePassToOnBoardService.saveErrorTraceTrTrace(gpId,500, e.getMessage());
                    }
                }
            }

            result.put("SUCCESS", successList);
            result.put("FAILED", failedList);
            return result;
        } else {
            failedList.add("No Employment records found");
            result.put("FAILED", failedList);
            return result;
        }
    }

    public String punchMatched(FaceLogFetchDto faceLogFetchDto) {
        try {
            if (faceLogFetchDto != null) {
                PunchRequestDTO punchRequestDTO = new PunchRequestDTO();
                PunchRequestDTO.DoDTO doDTO = new PunchRequestDTO.DoDTO();
                PunchRequestDTO.PunchesDTO punchesDTO = new PunchRequestDTO.PunchesDTO();
                PunchRequestDTO.AddedPunchDTO addedPunchDTO = new PunchRequestDTO.AddedPunchDTO();
                List<PunchRequestDTO.AddedPunchDTO> addedPunchDTOList = new ArrayList();
                addedPunchDTO.setPunchDtm(faceLogFetchDto.getPunchDtm());
                PunchRequestDTO.EmployeeDTO employeeDTO = new PunchRequestDTO.EmployeeDTO();
                employeeDTO.setQualifier(faceLogFetchDto.getPersonNum());
                addedPunchDTO.setEmployee(employeeDTO);
                addedPunchDTOList.add(addedPunchDTO);
                punchesDTO.setAdded(addedPunchDTOList);
                doDTO.setPunches(punchesDTO);
                PunchRequestDTO.WhereDTO whereDTO = new PunchRequestDTO.WhereDTO();
                PunchRequestDTO.DateRangeDTO dateRangeDTO = new PunchRequestDTO.DateRangeDTO();
                LocalDate punchDate = LocalDate.parse(faceLogFetchDto.getPunchDtm(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                String formattedDate = punchDate.toString();
                dateRangeDTO.setStartDate(formattedDate);
                dateRangeDTO.setEndDate(formattedDate);
                whereDTO.setEmployee(employeeDTO);
                whereDTO.setDateRange(dateRangeDTO);
                punchRequestDTO.setDoObj(doDTO);
                punchRequestDTO.setWhere(whereDTO);
                String s = this.wfdEmployeeService.addEmployeePunchFace(punchRequestDTO);
                return s;
            } else {
                return "Punch not get for WFD";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getTokenCheck(String username, String password){
        try{
            return  wfdEmployeeService.getAuthToken( username,  password);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public Object getAuthCheckup(String username, String password) {

        try {
            return wfdEmployeeService.getAuthCheck(username, password);
        } catch (Exception e) {
            return null;
        }
    }
    public Object getDetailsPerson(String username) {

        try {
            return wfdEmployeeService.getDeatilsPersonViaUKGAUTH(username);
        } catch (Exception e) {
            return null;
        }
    }



    public String getVerifyLabor(String username) {

        try {
            boolean b = wfdEmployeeService.verifyLaborCatEnInWFD(username);
            if(b){
                return "avalaible";
            }else{
                return "not avaliable";
            }
        } catch (Exception e) {
            return null;
        }
    }



    public EmployeeRequestDTO gatePassEmpDto(String GatePassId) {
        try {
            GatePassToOnBoard individualOnBoardDetailsByTrnId = this.gatePassToOnBoardService.getIndividualOnBoardDetailsByTrnId(GatePassId);
            String issandorpoc1 = getISSANDORPOC();

            if (issandorpoc1 != null) {
                issandorpoc1 = issandorpoc1.trim();
            }
            if (individualOnBoardDetailsByTrnId != null && "no".equalsIgnoreCase(issandorpoc1)) {
                EmployeeRequestDTO dto = new EmployeeRequestDTO();
                EmployeeRequestDTO.PersonInformation personInfo = new EmployeeRequestDTO.PersonInformation();
                EmployeeRequestDTO.AccessAssignment access = new EmployeeRequestDTO.AccessAssignment();
                access.setAccessProfileName(individualOnBoardDetailsByTrnId.getAccessProfileName());
                access.setPreferenceProfileName(individualOnBoardDetailsByTrnId.getPreferenceProfileName());
                access.setProfessionalPayCodeName(individualOnBoardDetailsByTrnId.getProfessionalPayCodeName());
                access.setProfessionalWorkRuleName(individualOnBoardDetailsByTrnId.getProfessionalWorkRuleName());
                access.setShiftCodeName(individualOnBoardDetailsByTrnId.getShiftCodeName());
                personInfo.setAccessAssignment(access);
                EmployeeRequestDTO.EmailAddress email = new EmployeeRequestDTO.EmailAddress();
                email.setAddress(individualOnBoardDetailsByTrnId.getAddressEmail());
                email.setContactTypeName(individualOnBoardDetailsByTrnId.getContactTypeName());
                email.setHasEmailNotificationDelivery(false);
                personInfo.setEmailAddresses(Arrays.asList(email));
                EmployeeRequestDTO.EmploymentStatus empStatus = new EmployeeRequestDTO.EmploymentStatus();
                empStatus.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
                empStatus.setEmploymentStatusName(individualOnBoardDetailsByTrnId.getEmploymentStatus());
                empStatus.setExpirationDate("3000-01-01");
                personInfo.setEmploymentStatusList(Arrays.asList(empStatus));
                EmployeeRequestDTO.Person person = new EmployeeRequestDTO.Person();
                person.setHireDate(individualOnBoardDetailsByTrnId.getHireDate());
                person.setPersonNumber(individualOnBoardDetailsByTrnId.getGatePassId());
                person.setBirthDate(individualOnBoardDetailsByTrnId.getBirthDate());
                String firstName = individualOnBoardDetailsByTrnId.getFirstName() != null ? individualOnBoardDetailsByTrnId.getFirstName().trim() : "";
                String lastName = individualOnBoardDetailsByTrnId.getLastName() != null ? individualOnBoardDetailsByTrnId.getLastName().trim() : "";
                if (lastName.isEmpty()) {
                    lastName = ".";
                }

                person.setFirstName(firstName);
                person.setLastName(lastName);
                String fullName = firstName;
                if (!lastName.equals(".")) {
                    fullName = firstName + " " + lastName;
                }

                person.setFullName(fullName);
                String shortName = firstName;
                if (!lastName.equals(".")) {
                    shortName = firstName + " " + lastName.substring(0, 1);
                }

                person.setShortName(individualOnBoardDetailsByTrnId.getShortName());
                personInfo.setPerson(person);
                ArrayList<EmployeeRequestDTO.CustomDataDTO> addCustomeList = new ArrayList();
                if (individualOnBoardDetailsByTrnId.getGender() != null && !individualOnBoardDetailsByTrnId.getGender().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO gender = new EmployeeRequestDTO.CustomDataDTO();
                    gender.setCustomDataTypeName("Gender");
                    gender.setText(individualOnBoardDetailsByTrnId.getGender());
                    addCustomeList.add(gender);
                }

                if (individualOnBoardDetailsByTrnId.getAadharNumber() != null && !individualOnBoardDetailsByTrnId.getAadharNumber().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO aadharNumber = new EmployeeRequestDTO.CustomDataDTO();
                    aadharNumber.setCustomDataTypeName("Aadhaar Number");
                    aadharNumber.setText(individualOnBoardDetailsByTrnId.getAadharNumber());
                    addCustomeList.add(aadharNumber);
                }

                if (individualOnBoardDetailsByTrnId.getAadharName() != null && !individualOnBoardDetailsByTrnId.getAadharName().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO aadharName = new EmployeeRequestDTO.CustomDataDTO();
                    aadharName.setCustomDataTypeName("Name as per Aadhaar");
                    aadharName.setText(individualOnBoardDetailsByTrnId.getAadharName());
                    addCustomeList.add(aadharName);
                }

                if (individualOnBoardDetailsByTrnId.getRelativeName() != null && !individualOnBoardDetailsByTrnId.getRelativeName().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO relativeName = new EmployeeRequestDTO.CustomDataDTO();
                    relativeName.setCustomDataTypeName("Father’s or Husband’s Name");
                    relativeName.setText(individualOnBoardDetailsByTrnId.getRelativeName());
                    addCustomeList.add(relativeName);
                }

                if (individualOnBoardDetailsByTrnId.getAddress() != null && !individualOnBoardDetailsByTrnId.getAddress().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentAddress = new EmployeeRequestDTO.CustomDataDTO();
                    permanentAddress.setCustomDataTypeName("Permanent Address");
                    permanentAddress.setText(individualOnBoardDetailsByTrnId.getAddress());
                    addCustomeList.add(permanentAddress);
                }

//                if (individualOnBoardDetailsByTrnId.getPermanentDistrict() != null && !individualOnBoardDetailsByTrnId.getPermanentDistrict().isEmpty()) {
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Permanent District");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentDistrict());
//                    addCustomeList.add(permanentDistrict);
//                }
//
//                if (individualOnBoardDetailsByTrnId.getPermanentState() != null && !individualOnBoardDetailsByTrnId.getPermanentState().isEmpty()) {
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Permanent State");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentState());
//                    addCustomeList.add(permanentDistrict);
//                }
//
//                if (individualOnBoardDetailsByTrnId.getPermanentPincode() != null && !individualOnBoardDetailsByTrnId.getPermanentPincode().isEmpty()) {
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Permanent Pin code");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentPincode());
//                    addCustomeList.add(permanentDistrict);
//                }

                if (individualOnBoardDetailsByTrnId.getIdMark() != null && !individualOnBoardDetailsByTrnId.getIdMark().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Identification Mark");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getIdMark());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getUanNumber() != null && !individualOnBoardDetailsByTrnId.getUanNumber().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("UAN Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getUanNumber());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getMaritalStatus() != null && !individualOnBoardDetailsByTrnId.getMaritalStatus().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Marital Status");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getMaritalStatus());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getTechnical() != null && !individualOnBoardDetailsByTrnId.getTechnical().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Technical Qualification");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getTechnical());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getAcademic() != null && !individualOnBoardDetailsByTrnId.getAcademic().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Academic Qualification");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getAcademic());
                    addCustomeList.add(permanentDistrict);
                }

//                if (individualOnBoardDetailsByTrnId.getShoeSize() != null && !individualOnBoardDetailsByTrnId.getShoeSize().isEmpty()) {
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Shoe Size");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getShoeSize());
//                    addCustomeList.add(permanentDistrict);
//                }

                if (individualOnBoardDetailsByTrnId.getBloodGroup() != null && !individualOnBoardDetailsByTrnId.getBloodGroup().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Blood Group");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getBloodGroup());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getWorkmenType() != null && !individualOnBoardDetailsByTrnId.getWorkmenType().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Workmen Type");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getWorkmenType());
                    addCustomeList.add(permanentDistrict);
                }
                if (individualOnBoardDetailsByTrnId.getWorkmenType() != null && !individualOnBoardDetailsByTrnId.getWorkmenType().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Onboarding Type");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getOnboardingType());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getWorkmenType() != null && !individualOnBoardDetailsByTrnId.getWorkmenType().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("ESIC IP Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getESICIPNumber());
                    addCustomeList.add(permanentDistrict);
                }
                if (individualOnBoardDetailsByTrnId.getWorkmenType() != null && !individualOnBoardDetailsByTrnId.getWorkmenType().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Accommodation");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getAccommodation());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getWorkmenType() != null && !individualOnBoardDetailsByTrnId.getWorkmenType().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Identification Mark");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getIdMark());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getNatureOfJob() != null && !individualOnBoardDetailsByTrnId.getNatureOfJob().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Nature of Job OR Work");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getNatureOfJob());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getPanNumber() != null && !individualOnBoardDetailsByTrnId.getPanNumber().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("PAN Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPanNumber());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getPfNumber() != null && !individualOnBoardDetailsByTrnId.getPfNumber().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("PF Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPfNumber());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getAccountNumber() != null && !individualOnBoardDetailsByTrnId.getAccountNumber().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Bank Account Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getAccountNumber());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getBankName() != null && !individualOnBoardDetailsByTrnId.getBankName().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Bank Name");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getBankName());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getIfscCode() != null && !individualOnBoardDetailsByTrnId.getIfscCode().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("IFSC Code");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getIfscCode());
                    addCustomeList.add(permanentDistrict);
                }
//                if (individualOnBoardDetailsByTrnId.getOnboardingType() != null && !individualOnBoardDetailsByTrnId.getOnboardingType().isEmpty()) {
//                    EmployeeRequestDTO.CustomDataDTO onboardingType = new EmployeeRequestDTO.CustomDataDTO();
//                    onboardingType.setCustomDataTypeName("IFSC Code");
//                    onboardingType.setText(individualOnBoardDetailsByTrnId.getOnboardingType());
//                    addCustomeList.add(onboardingType);
//                }

                personInfo.setCustomDataList(addCustomeList);
                EmployeeRequestDTO.PersonAuthenticationType auth = new EmployeeRequestDTO.PersonAuthenticationType();
                auth.setActiveFlag(true);
                auth.setAuthenticationTypeName("Basic");
                personInfo.setPersonAuthenticationTypes(Arrays.asList(auth));
                EmployeeRequestDTO.PersonLicenseType licenseEmployee = new EmployeeRequestDTO.PersonLicenseType();
                licenseEmployee.setActiveFlag(true);
                licenseEmployee.setLicenseTypeName("Employee");
                EmployeeRequestDTO.PersonLicenseType licenseAbsence = new EmployeeRequestDTO.PersonLicenseType();
                licenseAbsence.setActiveFlag(true);
                licenseAbsence.setLicenseTypeName("Absence");
                EmployeeRequestDTO.PersonLicenseType licensehourlyTimekeeping = new EmployeeRequestDTO.PersonLicenseType();
                licensehourlyTimekeeping.setActiveFlag(true);
                licensehourlyTimekeeping.setLicenseTypeName("Hourly Timekeeping");
                EmployeeRequestDTO.PersonLicenseType licenseScheduling = new EmployeeRequestDTO.PersonLicenseType();
                licenseScheduling.setActiveFlag(true);
                licenseScheduling.setLicenseTypeName("Advanced Scheduling");
                EmployeeRequestDTO.PersonLicenseType licenseAnalytics = new EmployeeRequestDTO.PersonLicenseType();
                licenseAnalytics.setActiveFlag(true);
                licenseAnalytics.setLicenseTypeName("Analytics");
                personInfo.setPersonLicenseTypes(Arrays.asList(licenseEmployee, licenseAbsence, licensehourlyTimekeeping, licenseScheduling,licenseAnalytics));
                EmployeeRequestDTO.UserAccountStatus userStatus = new EmployeeRequestDTO.UserAccountStatus();
                userStatus.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
                userStatus.setExpirationDate("3000-01-01");
                userStatus.setUserAccountStatusName(individualOnBoardDetailsByTrnId.getUserAccountStatus());
                personInfo.setUserAccountStatusList(Arrays.asList(userStatus));
                dto.setPersonInformation(personInfo);
                EmployeeRequestDTO.JobAssignment job = new EmployeeRequestDTO.JobAssignment();
//                EmployeeRequestDTO.BaseWageRate wage = new EmployeeRequestDTO.BaseWageRate();
//                wage.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
//                wage.setExpirationDate("3000-01-01");
//                wage.setHourlyRate(20.15);
//                job.setBaseWageRates(Arrays.asList(wage));
                EmployeeRequestDTO.JobAssignmentDetails jobDetails = new EmployeeRequestDTO.JobAssignmentDetails();
                jobDetails.setPayRuleName(individualOnBoardDetailsByTrnId.getPayRuleName());
                jobDetails.setSupervisorName(individualOnBoardDetailsByTrnId.getSupervisorName());
                jobDetails.setSupervisorPersonNumber(individualOnBoardDetailsByTrnId.getSupervisorPersonNumber());
                jobDetails.setTimeZoneName("(GMT +05:30) Calcutta");
                job.setJobAssignmentDetails(jobDetails);
                EmployeeRequestDTO.PrimaryLaborAccount labor = new EmployeeRequestDTO.PrimaryLaborAccount();
                labor.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
                labor.setExpirationDate("3000-01-01");

                boolean b = this.wfdEmployeeService.verifyLaborCatEnInWFD(individualOnBoardDetailsByTrnId.getCategory());
                System.out.println(b +" :- avaiable to set");
                if(b){

                    String category = individualOnBoardDetailsByTrnId.getCategory();
                    System.out.println(category +" get from query workorder");

                    if (category != null && !category.isEmpty()) {
                        labor.setLaborCategoryName(category);
                        System.out.println(labor.getLaborCategoryName() +" :- get this workorder to put in json");
                    }

                }else{
                    System.out.println(individualOnBoardDetailsByTrnId.getCategory() +"going for create workorder");
                    PostLaborCatDTO laborCategoryDto = gatePassToOnBoardService.createLaborCategoryDto(individualOnBoardDetailsByTrnId.getCategory());
                    String laborCatInWFD = wfdEmployeeService.createLaborCatInWFD(laborCategoryDto);
                    System.out.println(laborCatInWFD +"check that successfull or not");
                    if (laborCatInWFD != null &&
                            (laborCatInWFD.startsWith("SUCCESS") || laborCatInWFD.contains("This name is already")))
                    {  String category = individualOnBoardDetailsByTrnId.getCategory();
                        if (category != null && !category.isEmpty()) {
//                            labor.setLaborCategoryName(category + ",,,,,");
                            labor.setLaborCategoryName(category);
                            System.out.println("format of to set labour category to post :- "+labor.getLaborCategoryName());
                        }
                    }

                }
                System.out.println(labor.getLaborCategoryName() +" :- final set json");




                String skill = individualOnBoardDetailsByTrnId.getSkill();
                boolean checkJob = wfdEmployeeService.verifyJobInWFD(skill,"1900-01-01");
                System.out.println("check job found or not - "+ checkJob);

                if(!checkJob){
                    PostJobWfd jobByname = gatePassToOnBoardService.createJobByname(skill);
                    this.wfdEmployeeService.createJobInWFD(jobByname);
                    System.out.println("created job - "+ checkJob);
                }
                boolean checkJob1 = wfdEmployeeService.verifyJobInWFD(skill,"1900-01-01");
                System.out.println("job check again found or not -"+checkJob);


                String  orgPath= individualOnBoardDetailsByTrnId.getCompany() + "/" + individualOnBoardDetailsByTrnId.getLocation()+ "/" + individualOnBoardDetailsByTrnId.getDepartment() + "/" + individualOnBoardDetailsByTrnId.getSection() + "/" +individualOnBoardDetailsByTrnId.getContractorCode() + "/"+skill;
                System.out.println(orgPath);


                labor.setOrganizationPath(
                        resolveOrganizationPath1(orgPath)
                );
                job.setPrimaryLaborAccounts(Arrays.asList(labor));
                dto.setJobAssignment(job);
                EmployeeRequestDTO.User user = new EmployeeRequestDTO.User();
                EmployeeRequestDTO.UserAccount userAcc = new EmployeeRequestDTO.UserAccount();
                userAcc.setLogonProfileName(individualOnBoardDetailsByTrnId.getLogonProfileName());
                userAcc.setUserName(individualOnBoardDetailsByTrnId.getUserAccountName());
                userAcc.setUserPassword(individualOnBoardDetailsByTrnId.getUserPassword());
                user.setUserAccount(userAcc);
                dto.setUser(user);
                return dto != null ? dto : null;
            }


            else if ("yes".equalsIgnoreCase(issandorpoc1) && individualOnBoardDetailsByTrnId != null)
            {
                EmployeeRequestDTO dto = new EmployeeRequestDTO();
                EmployeeRequestDTO.PersonInformation personInfo = new EmployeeRequestDTO.PersonInformation();
                EmployeeRequestDTO.AccessAssignment access = new EmployeeRequestDTO.AccessAssignment();
                access.setAccessProfileName(individualOnBoardDetailsByTrnId.getAccessProfileName());
                access.setPreferenceProfileName(individualOnBoardDetailsByTrnId.getPreferenceProfileName());
                access.setProfessionalPayCodeName(individualOnBoardDetailsByTrnId.getProfessionalPayCodeName());
                access.setProfessionalWorkRuleName(individualOnBoardDetailsByTrnId.getProfessionalWorkRuleName());
                access.setShiftCodeName(individualOnBoardDetailsByTrnId.getShiftCodeName());
                personInfo.setAccessAssignment(access);
                EmployeeRequestDTO.EmailAddress email = new EmployeeRequestDTO.EmailAddress();
                email.setAddress(individualOnBoardDetailsByTrnId.getAddressEmail());
                email.setContactTypeName(individualOnBoardDetailsByTrnId.getContactTypeName());
                email.setHasEmailNotificationDelivery(false);
                personInfo.setEmailAddresses(Arrays.asList(email));
                EmployeeRequestDTO.EmploymentStatus empStatus = new EmployeeRequestDTO.EmploymentStatus();
                empStatus.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
                empStatus.setEmploymentStatusName(individualOnBoardDetailsByTrnId.getEmploymentStatus());
                empStatus.setExpirationDate("3000-01-01");
                personInfo.setEmploymentStatusList(Arrays.asList(empStatus));
                EmployeeRequestDTO.Person person = new EmployeeRequestDTO.Person();
                person.setHireDate(individualOnBoardDetailsByTrnId.getHireDate());
                person.setPersonNumber(individualOnBoardDetailsByTrnId.getGatePassId());
                person.setBirthDate(individualOnBoardDetailsByTrnId.getBirthDate());
                String firstName = individualOnBoardDetailsByTrnId.getFirstName() != null ? individualOnBoardDetailsByTrnId.getFirstName().trim() : "";
                String lastName = individualOnBoardDetailsByTrnId.getLastName() != null ? individualOnBoardDetailsByTrnId.getLastName().trim() : "";
                if (lastName.isEmpty()) {
                    lastName = ".";
                }

                person.setFirstName(firstName);
                person.setLastName(lastName);
                String fullName = firstName;
                if (!lastName.equals(".")) {
                    fullName = firstName + " " + lastName;
                }

                person.setFullName(fullName);
                String shortName = firstName;
                if (!lastName.equals(".")) {
                    shortName = firstName + " " + lastName.substring(0, 1);
                }

                person.setShortName(shortName);
                personInfo.setPerson(person);
                ArrayList<EmployeeRequestDTO.CustomDataDTO> addCustomeList = new ArrayList();
                if (individualOnBoardDetailsByTrnId.getGender() != null && !individualOnBoardDetailsByTrnId.getGender().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO gender = new EmployeeRequestDTO.CustomDataDTO();
                    gender.setCustomDataTypeName("Gender");
                    gender.setText(individualOnBoardDetailsByTrnId.getGender());
                    addCustomeList.add(gender);
                }

                if (individualOnBoardDetailsByTrnId.getAadharNumber() != null && !individualOnBoardDetailsByTrnId.getAadharNumber().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO aadharNumber = new EmployeeRequestDTO.CustomDataDTO();
                    aadharNumber.setCustomDataTypeName("Aadhar Number");
                    aadharNumber.setText(individualOnBoardDetailsByTrnId.getAadharNumber());
                    addCustomeList.add(aadharNumber);
                }

                if (individualOnBoardDetailsByTrnId.getAadharName() != null && !individualOnBoardDetailsByTrnId.getAadharName().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO aadharName = new EmployeeRequestDTO.CustomDataDTO();
                    aadharName.setCustomDataTypeName("Name as Per Aadhar");
                    aadharName.setText(individualOnBoardDetailsByTrnId.getAadharName());
                    addCustomeList.add(aadharName);
                }

                if (individualOnBoardDetailsByTrnId.getRelativeName() != null && !individualOnBoardDetailsByTrnId.getRelativeName().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO relativeName = new EmployeeRequestDTO.CustomDataDTO();
                    relativeName.setCustomDataTypeName("Father or Husband Name");
                    relativeName.setText(individualOnBoardDetailsByTrnId.getRelativeName());
                    addCustomeList.add(relativeName);
                }

                if (individualOnBoardDetailsByTrnId.getAddress() != null && !individualOnBoardDetailsByTrnId.getAddress().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentAddress = new EmployeeRequestDTO.CustomDataDTO();
                    permanentAddress.setCustomDataTypeName("Permanent Address");
                    permanentAddress.setText(individualOnBoardDetailsByTrnId.getRelativeName());
                    addCustomeList.add(permanentAddress);
                }

                if (individualOnBoardDetailsByTrnId.getPermanentDistrict() != null && !individualOnBoardDetailsByTrnId.getPermanentDistrict().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Permanent District");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentDistrict());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getPermanentState() != null && !individualOnBoardDetailsByTrnId.getPermanentState().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Permanent State");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentState());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getPermanentPincode() != null && !individualOnBoardDetailsByTrnId.getPermanentPincode().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Permanent Pin code");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentPincode());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getIdMark() != null && !individualOnBoardDetailsByTrnId.getIdMark().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("ID Mark");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getIdMark());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getUanNumber() != null && !individualOnBoardDetailsByTrnId.getUanNumber().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("UAN Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getUanNumber());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getMaritalStatus() != null && !individualOnBoardDetailsByTrnId.getMaritalStatus().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Marital Status");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getMaritalStatus());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getTechnical() != null && !individualOnBoardDetailsByTrnId.getTechnical().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Technical Qualification");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getTechnical());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getAcademic() != null && !individualOnBoardDetailsByTrnId.getAcademic().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Academic Qualification");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getAcademic());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getShoeSize() != null && !individualOnBoardDetailsByTrnId.getShoeSize().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Shoe Size");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getShoeSize());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getBloodGroup() != null && !individualOnBoardDetailsByTrnId.getBloodGroup().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Blood Group");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getBloodGroup());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getWorkmenType() != null && !individualOnBoardDetailsByTrnId.getWorkmenType().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Workmen Type");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getWorkmenType());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getNatureOfJob() != null && !individualOnBoardDetailsByTrnId.getNatureOfJob().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Nature Of Job");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getNatureOfJob());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getPanNumber() != null && !individualOnBoardDetailsByTrnId.getPanNumber().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("PAN Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPanNumber());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getPfNumber() != null && !individualOnBoardDetailsByTrnId.getPfNumber().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("PF Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPfNumber());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getAccountNumber() != null && !individualOnBoardDetailsByTrnId.getAccountNumber().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Account Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getAccountNumber());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getBankName() != null && !individualOnBoardDetailsByTrnId.getBankName().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Bank Name");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getBankName());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getIfscCode() != null && !individualOnBoardDetailsByTrnId.getIfscCode().isEmpty()) {
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("IFSC Code");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getIfscCode());
                    addCustomeList.add(permanentDistrict);
                }

                personInfo.setCustomDataList(addCustomeList);
                EmployeeRequestDTO.PersonAuthenticationType auth = new EmployeeRequestDTO.PersonAuthenticationType();
                auth.setActiveFlag(true);
                auth.setAuthenticationTypeName("Basic");
                personInfo.setPersonAuthenticationTypes(Arrays.asList(auth));
                EmployeeRequestDTO.PersonLicenseType licenseEmployee = new EmployeeRequestDTO.PersonLicenseType();
                licenseEmployee.setActiveFlag(true);
                licenseEmployee.setLicenseTypeName("Employee");
                EmployeeRequestDTO.PersonLicenseType licenseAbsence = new EmployeeRequestDTO.PersonLicenseType();
                licenseAbsence.setActiveFlag(true);
                licenseAbsence.setLicenseTypeName("Absence");
                EmployeeRequestDTO.PersonLicenseType licensehourlyTimekeeping = new EmployeeRequestDTO.PersonLicenseType();
                licensehourlyTimekeeping.setActiveFlag(true);
                if (!String.valueOf(individualOnBoardDetailsByTrnId.getGatePassTypeId()).equals(GatePassType.CREATE.getStatus()) && !String.valueOf(individualOnBoardDetailsByTrnId.getGatePassTypeId()).equals(GatePassType.RENEW.getStatus())) {
                    if (String.valueOf(individualOnBoardDetailsByTrnId.getGatePassTypeId()).equals(GatePassType.PROJECT.getStatus())) {
                        licensehourlyTimekeeping.setLicenseTypeName("Salaried Timekeeping");
                    }
                } else {
                    licensehourlyTimekeeping.setLicenseTypeName("Hourly Timekeeping");
                }

                EmployeeRequestDTO.PersonLicenseType licenseScheduling = new EmployeeRequestDTO.PersonLicenseType();
                licenseScheduling.setActiveFlag(true);
                licenseScheduling.setLicenseTypeName("Scheduling");
                personInfo.setPersonLicenseTypes(Arrays.asList(licenseEmployee, licenseAbsence, licensehourlyTimekeeping, licenseScheduling));
                EmployeeRequestDTO.UserAccountStatus userStatus = new EmployeeRequestDTO.UserAccountStatus();
                userStatus.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
                userStatus.setExpirationDate("3000-01-01");
                userStatus.setUserAccountStatusName(individualOnBoardDetailsByTrnId.getUserAccountStatus());
                personInfo.setUserAccountStatusList(Arrays.asList(userStatus));
                dto.setPersonInformation(personInfo);
                EmployeeRequestDTO.JobAssignment job = new EmployeeRequestDTO.JobAssignment();
//                EmployeeRequestDTO.BaseWageRate wage = new EmployeeRequestDTO.BaseWageRate();
//                wage.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
//                wage.setExpirationDate("3000-01-01");
//                wage.setHourlyRate(20.15);
//                job.setBaseWageRates(Arrays.asList(wage));
                EmployeeRequestDTO.JobAssignmentDetails jobDetails = new EmployeeRequestDTO.JobAssignmentDetails();
                jobDetails.setPayRuleName(individualOnBoardDetailsByTrnId.getPayRuleName());
                jobDetails.setSupervisorName(individualOnBoardDetailsByTrnId.getSupervisorName());
                jobDetails.setSupervisorPersonNumber(individualOnBoardDetailsByTrnId.getSupervisorPersonNumber());
                jobDetails.setTimeZoneName("(GMT +05:30) Calcutta");
                job.setJobAssignmentDetails(jobDetails);
                EmployeeRequestDTO.PrimaryLaborAccount labor = new EmployeeRequestDTO.PrimaryLaborAccount();
                labor.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
                labor.setExpirationDate("3000-01-01");

                boolean b = this.wfdEmployeeService.verifyLaborCatEnInWFD(individualOnBoardDetailsByTrnId.getCategory());
                System.out.println(b +" :- avaiable to set");
                if(b){

                    String category = individualOnBoardDetailsByTrnId.getCategory();
                    System.out.println(category +" get from query workorder");

                    if (category != null && !category.isEmpty()) {
                        labor.setLaborCategoryName(category + ",,,,,");
                        System.out.println(labor.getLaborCategoryName() +" :- get this workorder to put in json");
                    }

                }else{
                    System.out.println(individualOnBoardDetailsByTrnId.getCategory() +"going for create workorder");
                    PostLaborCatDTO laborCategoryDto = gatePassToOnBoardService.createLaborCategoryDto(individualOnBoardDetailsByTrnId.getCategory());
                    String laborCatInWFD = wfdEmployeeService.createLaborCatInWFD(laborCategoryDto);
                    System.out.println(laborCatInWFD +"check that successfull or not");
                    if (laborCatInWFD != null && laborCatInWFD.startsWith("SUCCESS")) {
                        String category = individualOnBoardDetailsByTrnId.getCategory();
                        if (category != null && !category.isEmpty()) {
                            labor.setLaborCategoryName(category + ",,,,,");
                            System.out.println("format of to set labour category to post"+labor.getLaborCategoryName());
                        }
                    }

                }
                System.out.println(labor.getLaborCategoryName() +" :- final set json");


                String issandorpoc = getISSANDORPOC();
                String orgPath = "";
                if (issandorpoc != null) {
                    issandorpoc = issandorpoc.trim();

                }
                if ("yes".equalsIgnoreCase(issandorpoc)) {

                    String var10000 = individualOnBoardDetailsByTrnId.getLocation();
                    orgPath= var10000 + "/" + individualOnBoardDetailsByTrnId.getCompany() + "/" + individualOnBoardDetailsByTrnId.getPlantLocation() + "/" + individualOnBoardDetailsByTrnId.getDepartment() + "/" + individualOnBoardDetailsByTrnId.getSection() + "/" + individualOnBoardDetailsByTrnId.getSubSection() + "/" + individualOnBoardDetailsByTrnId.getContractorCode() + "/Team Lead";

                } else if ("no".equalsIgnoreCase(issandorpoc)) {

                    String skill = individualOnBoardDetailsByTrnId.getSkill();
                    boolean checkJob = wfdEmployeeService.verifyJobInWFD(skill,"1900-01-01");
                    if(!checkJob){
                        PostJobWfd jobByname = gatePassToOnBoardService.createJobByname(skill);
                        String jobInWFD = this.wfdEmployeeService.createJobInWFD(jobByname);


                    }
                    orgPath= individualOnBoardDetailsByTrnId.getCompany() + "/" + individualOnBoardDetailsByTrnId.getLocation()+ "/" + individualOnBoardDetailsByTrnId.getDepartment() + "/" + individualOnBoardDetailsByTrnId.getSection() + "/" +individualOnBoardDetailsByTrnId.getContractorCode() + "/"+skill;

                }else{

                }
                System.out.println(orgPath);

                labor.setOrganizationPath(
                        resolveOrganizationPath1(orgPath)
                );
                job.setPrimaryLaborAccounts(Arrays.asList(labor));
                dto.setJobAssignment(job);
                EmployeeRequestDTO.User user = new EmployeeRequestDTO.User();
                EmployeeRequestDTO.UserAccount userAcc = new EmployeeRequestDTO.UserAccount();
                userAcc.setLogonProfileName(individualOnBoardDetailsByTrnId.getLogonProfileName());
                userAcc.setUserName(individualOnBoardDetailsByTrnId.getUserAccountName());
                userAcc.setUserPassword(individualOnBoardDetailsByTrnId.getUserPassword());
                user.setUserAccount(userAcc);
                dto.setUser(user);
                return dto != null ? dto : null;
            }
            else{
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getISSANDORPOC() {
        return QueryFileWatcher.getQuery("ISSAND");
    }
    public String resolveOrganizationPath1(String orgPath) {
        try {
            String issandorpoc = getISSANDORPOC();

            if (issandorpoc != null) {
                issandorpoc = issandorpoc.trim();
            }

            if ("yes".equalsIgnoreCase(issandorpoc)) {
                return resolveOrganizationPath(orgPath);
            } else if ("no".equalsIgnoreCase(issandorpoc)) {
                return resolveOrganizationPathPOC(orgPath);
            }else {
                throw  new IllegalArgumentException(
                        "Invalid value for ISSAND in query properties file: while creating bs" + issandorpoc
                );
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }




    public String resolveOrganizationPath(String orgPath) {

        if (gatePassToOnBoardService.checkLocationPath(orgPath)) {
            return orgPath;
        }

        if (wfdEmployeeService.checkLocationInUKG(orgPath)) {
            gatePassToOnBoardService.storeHierarchyInDB(orgPath);
            return orgPath;
        }

        gatePassToOnBoardService.createBusinessStructure(orgPath);

        if (wfdEmployeeService.checkLocationInUKG(orgPath)) {
            gatePassToOnBoardService.storeHierarchyInDB(orgPath);
            return orgPath;
        }

        throw new RuntimeException("Failed to resolve orgPath: " + orgPath);
    }


    public String resolveOrganizationPathPOC(String orgPath) {

        if (gatePassToOnBoardService.checkLocationPath(orgPath)) {
            return orgPath;
        }
        boolean b = wfdEmployeeService.checkLocationInUKG(orgPath);
        System.out.println(b +"Location check");

        if (wfdEmployeeService.checkLocationInUKG(orgPath)) {
            gatePassToOnBoardService.storeHierarchyInDBPOC(orgPath);
            return orgPath;
        }

        gatePassToOnBoardService.createBusinessStructurePOC(orgPath);

        if (wfdEmployeeService.checkLocationInUKG(orgPath)) {
            gatePassToOnBoardService.storeHierarchyInDBPOC(orgPath);
            return orgPath;
        }else{
            return orgPath;

        }

//        throw new RuntimeException("Failed to resolve orgPath: " + orgPath);
    }

    public String gatePassEmpDtoDynamic(String gatePassId) {
        try {
            EmployeeRequestDTO employeeRequestDTO = this.gatePassEmpDto(gatePassId);
            if (employeeRequestDTO == null) {
                return "STATUS:400\nTransaction Id Not Found";
            } else {
                String employeeResponse = this.wfdEmployeeService.createEmployee(employeeRequestDTO);
                String[] parts = employeeResponse.split("\nBODY:", 2);
                if (parts.length < 2) {
                    return "STATUS:500\nInvalid response format";
                } else {
                    int statusCode = Integer.parseInt(parts[0].replace("STATUS:", "").trim());
                    String body = parts[1];
                    if (statusCode != 200) {
                        return "STATUS:" + statusCode + "\n" + body;
                    } else {
                        JsonNode rootNode = this.objectMapper.readTree(body);
                        JsonNode personKeyNode = rootNode.path("personIdentity").path("personKey");
                        if (!personKeyNode.isMissingNode() && !personKeyNode.isNull()) {
                            Long personKey = personKeyNode.asLong();
                            SkillProLevelDateDTO skillData = this.getSkillPRoLevelDate(gatePassId);
                            if (skillData == null) {
                                return "STATUS:400\nSkill data not found from SQL query";
                            } else {
                                String skillName = skillData.getSkill();
                                String profName = skillData.getProficiencyLevel();
                                if (!this.wfdEmployeeService.verifySkillsInWFD(skillName)) {
                                    PostSkillWfd postSkill = new PostSkillWfd();
                                    postSkill.setName(skillName);
                                    this.wfdEmployeeService.createSkillsInWFD(postSkill);
                                }

                                if (!this.wfdEmployeeService.verifyProfInWFD(profName)) {
                                    System.out.println("going for create Prof"+profName);
                                    ProficiencyDTO profDto = new ProficiencyDTO();
                                    profDto.setId(329);
                                    profDto.setActive(true);
                                    profDto.setProficiencyLevelNumeric(329);
                                    profDto.setVersion(0);
                                    profDto.setName(profName);
                                    this.wfdEmployeeService.createProfInWFD(profDto);
                                }

                                this.wfdEmployeeService.addPersonSkill(skillData.getPersonNumber(), skillName, profName, skillData.getEffectiveDate());
                                return personKey.toString();
                            }
                        } else {
                            return "STATUS:400\n" + body;
                        }
                    }
                }
            }
        } catch (Exception e) {
            return "STATUS:500\n" + e.getMessage();
        }
    }

    public SkillProLevelDateDTO getSkillPRoLevelDate(String trndID) {
        try {
            SkillProLevelDateDTO onlySkillProByTrnId = this.gatePassToOnBoardService.getOnlySkillProByTrnId(trndID);
            return onlySkillProByTrnId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UpdateEmployeeRequestDTO gatePassUpdateEmpDto(String GatePassId) {



        try {
            GatePassToOnBoard individualOnBoardDetailsByTrnId = this.gatePassToOnBoardService.getIndividualOnBoardDetailsByTrnIdUpdated(GatePassId);
            String issandorpoc1 = getISSANDORPOC();

            if (issandorpoc1 != null) {
                issandorpoc1 = issandorpoc1.trim();
            }


            if (individualOnBoardDetailsByTrnId != null && "no".equalsIgnoreCase(issandorpoc1)) {
                UpdateEmployeeRequestDTO dto = new UpdateEmployeeRequestDTO();
                UpdateEmployeeRequestDTO.PersonInformation personInfo = new UpdateEmployeeRequestDTO.PersonInformation();
                UpdateEmployeeRequestDTO.AccessAssignment access = new UpdateEmployeeRequestDTO.AccessAssignment();
                access.setAccessProfileName(individualOnBoardDetailsByTrnId.getAccessProfileName());
                access.setPreferenceProfileName(individualOnBoardDetailsByTrnId.getPreferenceProfileName());
                access.setProfessionalPayCodeName(individualOnBoardDetailsByTrnId.getProfessionalPayCodeName());
                access.setProfessionalWorkRuleName(individualOnBoardDetailsByTrnId.getProfessionalWorkRuleName());
                access.setShiftCodeName(individualOnBoardDetailsByTrnId.getShiftCodeName());
                personInfo.setAccessAssignment(access);
                UpdateEmployeeRequestDTO.EmailAddress email = new UpdateEmployeeRequestDTO.EmailAddress();
                email.setAddress(individualOnBoardDetailsByTrnId.getAddressEmail());
                email.setContactTypeName(individualOnBoardDetailsByTrnId.getContactTypeName());
                email.setHasEmailNotificationDelivery(false);
                personInfo.setEmailAddresses(Arrays.asList(email));
                UpdateEmployeeRequestDTO.EmploymentStatus empStatus = new UpdateEmployeeRequestDTO.EmploymentStatus();
                empStatus.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
                empStatus.setEmploymentStatusName(individualOnBoardDetailsByTrnId.getEmploymentStatus());
                empStatus.setExpirationDate("3000-01-01");
                personInfo.setEmploymentStatusList(Arrays.asList(empStatus));
                UpdateEmployeeRequestDTO.Person person = new UpdateEmployeeRequestDTO.Person();
                person.setHireDate(individualOnBoardDetailsByTrnId.getHireDate());
                person.setPersonNumber(individualOnBoardDetailsByTrnId.getGatePassId());
                person.setBirthDate(individualOnBoardDetailsByTrnId.getBirthDate());
                String firstName = individualOnBoardDetailsByTrnId.getFirstName() != null ? individualOnBoardDetailsByTrnId.getFirstName().trim() : "";
                String lastName = individualOnBoardDetailsByTrnId.getLastName() != null ? individualOnBoardDetailsByTrnId.getLastName().trim() : "";
                if (lastName.isEmpty()) {
                    lastName = ".";
                }

                person.setFirstName(firstName);
                person.setLastName(lastName);
                String fullName = firstName;
                if (!lastName.equals(".")) {
                    fullName = firstName + " " + lastName;
                }

                person.setFullName(fullName);
                String shortName = firstName;
                if (!lastName.equals(".")) {
                    shortName = firstName + " " + lastName.substring(0, 1);
                }

                person.setShortName(individualOnBoardDetailsByTrnId.getShortName());
                personInfo.setPerson(person);
                ArrayList<UpdateEmployeeRequestDTO.CustomDataDTO> addCustomeList = new ArrayList();
                if (individualOnBoardDetailsByTrnId.getGender() != null && !individualOnBoardDetailsByTrnId.getGender().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO gender = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    gender.setCustomDataTypeName("Gender");
                    gender.setText(individualOnBoardDetailsByTrnId.getGender());
                    addCustomeList.add(gender);
                }

                if (individualOnBoardDetailsByTrnId.getAadharNumber() != null && !individualOnBoardDetailsByTrnId.getAadharNumber().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO aadharNumber = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    aadharNumber.setCustomDataTypeName("Aadhaar Number");
                    aadharNumber.setText(individualOnBoardDetailsByTrnId.getAadharNumber());
                    addCustomeList.add(aadharNumber);
                }

                if (individualOnBoardDetailsByTrnId.getAadharName() != null && !individualOnBoardDetailsByTrnId.getAadharName().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO aadharName = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    aadharName.setCustomDataTypeName("Name as per Aadhaar");
                    aadharName.setText(individualOnBoardDetailsByTrnId.getAadharName());
                    addCustomeList.add(aadharName);
                }

                if (individualOnBoardDetailsByTrnId.getRelativeName() != null && !individualOnBoardDetailsByTrnId.getRelativeName().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO relativeName = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    relativeName.setCustomDataTypeName("Father’s or Husband’s Name");
                    relativeName.setText(individualOnBoardDetailsByTrnId.getRelativeName());
                    addCustomeList.add(relativeName);
                }

                if (individualOnBoardDetailsByTrnId.getAddress() != null && !individualOnBoardDetailsByTrnId.getAddress().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentAddress = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentAddress.setCustomDataTypeName("Permanent Address");
                    permanentAddress.setText(individualOnBoardDetailsByTrnId.getAddress());
                    addCustomeList.add(permanentAddress);
                }

//                if (individualOnBoardDetailsByTrnId.getPermanentDistrict() != null && !individualOnBoardDetailsByTrnId.getPermanentDistrict().isEmpty()) {
//                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Permanent District");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentDistrict());
//                    addCustomeList.add(permanentDistrict);
//                }
//
//                if (individualOnBoardDetailsByTrnId.getPermanentState() != null && !individualOnBoardDetailsByTrnId.getPermanentState().isEmpty()) {
//                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Permanent State");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentState());
//                    addCustomeList.add(permanentDistrict);
//                }
//
//                if (individualOnBoardDetailsByTrnId.getPermanentPincode() != null && !individualOnBoardDetailsByTrnId.getPermanentPincode().isEmpty()) {
//                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Permanent Pin code");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentPincode());
//                    addCustomeList.add(permanentDistrict);
//                }

                if (individualOnBoardDetailsByTrnId.getIdMark() != null && !individualOnBoardDetailsByTrnId.getIdMark().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Identification Mark");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getIdMark());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getUanNumber() != null && !individualOnBoardDetailsByTrnId.getUanNumber().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("UAN Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getUanNumber());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getMaritalStatus() != null && !individualOnBoardDetailsByTrnId.getMaritalStatus().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Marital Status");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getMaritalStatus());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getTechnical() != null && !individualOnBoardDetailsByTrnId.getTechnical().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Technical Qualification");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getTechnical());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getAcademic() != null && !individualOnBoardDetailsByTrnId.getAcademic().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Academic Qualification");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getAcademic());
                    addCustomeList.add(permanentDistrict);
                }

//                if (individualOnBoardDetailsByTrnId.getShoeSize() != null && !individualOnBoardDetailsByTrnId.getShoeSize().isEmpty()) {
//                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Shoe Size");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getShoeSize());
//                    addCustomeList.add(permanentDistrict);
//                }

                if (individualOnBoardDetailsByTrnId.getBloodGroup() != null && !individualOnBoardDetailsByTrnId.getBloodGroup().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Blood Group");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getBloodGroup());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getWorkmenType() != null && !individualOnBoardDetailsByTrnId.getWorkmenType().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Workmen Type");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getWorkmenType());
                    addCustomeList.add(permanentDistrict);
                }
                if (individualOnBoardDetailsByTrnId.getWorkmenType() != null && !individualOnBoardDetailsByTrnId.getWorkmenType().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Onboarding Type");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getOnboardingType());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getWorkmenType() != null && !individualOnBoardDetailsByTrnId.getWorkmenType().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("ESIC IP Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getESICIPNumber());
                    addCustomeList.add(permanentDistrict);
                }
                if (individualOnBoardDetailsByTrnId.getWorkmenType() != null && !individualOnBoardDetailsByTrnId.getWorkmenType().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Accommodation");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getAccommodation());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getWorkmenType() != null && !individualOnBoardDetailsByTrnId.getWorkmenType().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Identification Mark");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getIdMark());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getNatureOfJob() != null && !individualOnBoardDetailsByTrnId.getNatureOfJob().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Nature of Job OR Work");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getNatureOfJob());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getPanNumber() != null && !individualOnBoardDetailsByTrnId.getPanNumber().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("PAN Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPanNumber());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getPfNumber() != null && !individualOnBoardDetailsByTrnId.getPfNumber().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("PF Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPfNumber());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getAccountNumber() != null && !individualOnBoardDetailsByTrnId.getAccountNumber().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Bank Account Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getAccountNumber());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getBankName() != null && !individualOnBoardDetailsByTrnId.getBankName().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Bank Name");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getBankName());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getIfscCode() != null && !individualOnBoardDetailsByTrnId.getIfscCode().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("IFSC Code");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getIfscCode());
                    addCustomeList.add(permanentDistrict);
                }
//                if (individualOnBoardDetailsByTrnId.getOnboardingType() != null && !individualOnBoardDetailsByTrnId.getOnboardingType().isEmpty()) {
//                    UpdateEmployeeRequestDTO.CustomDataDTO onboardingType = new UpdateEmployeeRequestDTO.CustomDataDTO();
//                    onboardingType.setCustomDataTypeName("IFSC Code");
//                    onboardingType.setText(individualOnBoardDetailsByTrnId.getOnboardingType());
//                    addCustomeList.add(onboardingType);
//                }

                personInfo.setCustomDataList(addCustomeList);
                UpdateEmployeeRequestDTO.PersonAuthenticationType auth = new UpdateEmployeeRequestDTO.PersonAuthenticationType();
                auth.setActiveFlag(true);
                auth.setAuthenticationTypeName("Basic");
                personInfo.setPersonAuthenticationTypes(Arrays.asList(auth));
                UpdateEmployeeRequestDTO.PersonLicenseType licenseEmployee = new UpdateEmployeeRequestDTO.PersonLicenseType();
                licenseEmployee.setActiveFlag(true);
                licenseEmployee.setLicenseTypeName("Employee");
                UpdateEmployeeRequestDTO.PersonLicenseType licenseAbsence = new UpdateEmployeeRequestDTO.PersonLicenseType();
                licenseAbsence.setActiveFlag(true);
                licenseAbsence.setLicenseTypeName("Absence");
                UpdateEmployeeRequestDTO.PersonLicenseType licensehourlyTimekeeping = new UpdateEmployeeRequestDTO.PersonLicenseType();
                licensehourlyTimekeeping.setActiveFlag(true);
                licensehourlyTimekeeping.setLicenseTypeName("Hourly Timekeeping");
                UpdateEmployeeRequestDTO.PersonLicenseType licenseScheduling = new UpdateEmployeeRequestDTO.PersonLicenseType();
                licenseScheduling.setActiveFlag(true);
                licenseScheduling.setLicenseTypeName("Advanced Scheduling");
                UpdateEmployeeRequestDTO.PersonLicenseType licenseAnalytics = new UpdateEmployeeRequestDTO.PersonLicenseType();
                licenseAnalytics.setActiveFlag(true);
                licenseAnalytics.setLicenseTypeName("Analytics");
                personInfo.setPersonLicenseTypes(Arrays.asList(licenseEmployee, licenseAbsence, licensehourlyTimekeeping, licenseScheduling,licenseAnalytics));
                UpdateEmployeeRequestDTO.UserAccountStatus userStatus = new UpdateEmployeeRequestDTO.UserAccountStatus();
                userStatus.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
                userStatus.setExpirationDate("3000-01-01");
                userStatus.setUserAccountStatusName(individualOnBoardDetailsByTrnId.getUserAccountStatus());
                personInfo.setUserAccountStatusList(Arrays.asList(userStatus));
                dto.setPersonInformation(personInfo);
                UpdateEmployeeRequestDTO.JobAssignment job = new UpdateEmployeeRequestDTO.JobAssignment();
//                UpdateEmployeeRequestDTO.BaseWageRate wage = new UpdateEmployeeRequestDTO.BaseWageRate();
//                wage.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
//                wage.setExpirationDate("3000-01-01");
//                wage.setHourlyRate(20.15);
//                job.setBaseWageRates(Arrays.asList(wage));
                UpdateEmployeeRequestDTO.JobAssignmentDetails jobDetails = new UpdateEmployeeRequestDTO.JobAssignmentDetails();
                jobDetails.setPayRuleName(individualOnBoardDetailsByTrnId.getPayRuleName());
                jobDetails.setSupervisorName(individualOnBoardDetailsByTrnId.getSupervisorName());
                jobDetails.setSupervisorPersonNumber(individualOnBoardDetailsByTrnId.getSupervisorPersonNumber());
                jobDetails.setTimeZoneName("(GMT +05:30) Calcutta");
                job.setJobAssignmentDetails(jobDetails);
                UpdateEmployeeRequestDTO.PrimaryLaborAccount labor = new UpdateEmployeeRequestDTO.PrimaryLaborAccount();
                labor.setEffectiveDate(individualOnBoardDetailsByTrnId.getEmploymentStatusEffectiveDate());
                labor.setExpirationDate("3000-01-01");

                boolean b = this.wfdEmployeeService.verifyLaborCatEnInWFD(individualOnBoardDetailsByTrnId.getCategory());
                System.out.println(b +" :- avaiable to set");
                if(b){

                    String category = individualOnBoardDetailsByTrnId.getCategory();
                    System.out.println(category +" get from query workorder");

                    if (category != null && !category.isEmpty()) {
                        labor.setLaborCategoryName(category);
                        System.out.println(labor.getLaborCategoryName() +" :- get this workorder to put in json");
                    }

                }else{
                    System.out.println(individualOnBoardDetailsByTrnId.getCategory() +"going for create workorder");
                    PostLaborCatDTO laborCategoryDto = gatePassToOnBoardService.createLaborCategoryDto(individualOnBoardDetailsByTrnId.getCategory());
                    String laborCatInWFD = wfdEmployeeService.createLaborCatInWFD(laborCategoryDto);
                    System.out.println(laborCatInWFD +"check that successfull or not");
                    if (laborCatInWFD != null &&
                            (laborCatInWFD.startsWith("SUCCESS") || laborCatInWFD.contains("This name is already")))
                    {  String category = individualOnBoardDetailsByTrnId.getCategory();
                        if (category != null && !category.isEmpty()) {
//                            labor.setLaborCategoryName(category + ",,,,,");
                            labor.setLaborCategoryName(category);
                            System.out.println("format of to set labour category to post :- "+labor.getLaborCategoryName());
                        }
                    }

                }
                System.out.println(labor.getLaborCategoryName() +" :- final set json");




                String skill = individualOnBoardDetailsByTrnId.getSkill();
                boolean checkJob = wfdEmployeeService.verifyJobInWFD(skill,"1900-01-01");
                System.out.println("check job found or not - "+ checkJob);

                if(!checkJob){
                    PostJobWfd jobByname = gatePassToOnBoardService.createJobByname(skill);
                    this.wfdEmployeeService.createJobInWFD(jobByname);
                    System.out.println("created job - "+ checkJob);
                }
                boolean checkJob1 = wfdEmployeeService.verifyJobInWFD(skill,"1900-01-01");
                System.out.println("job check again found or not -"+checkJob);


                String  orgPath= individualOnBoardDetailsByTrnId.getCompany() + "/" + individualOnBoardDetailsByTrnId.getLocation()+ "/" + individualOnBoardDetailsByTrnId.getDepartment() + "/" + individualOnBoardDetailsByTrnId.getSection() + "/" +individualOnBoardDetailsByTrnId.getContractorCode() + "/"+skill;
                System.out.println(orgPath);


                labor.setOrganizationPath(
                        resolveOrganizationPath1(orgPath)
                );
                job.setPrimaryLaborAccounts(Arrays.asList(labor));
                dto.setJobAssignment(job);
                UpdateEmployeeRequestDTO.User user = new UpdateEmployeeRequestDTO.User();
                UpdateEmployeeRequestDTO.UserAccount userAcc = new UpdateEmployeeRequestDTO.UserAccount();
                userAcc.setLogonProfileName(individualOnBoardDetailsByTrnId.getLogonProfileName());
                userAcc.setUserName(individualOnBoardDetailsByTrnId.getUserAccountName());
                userAcc.setUserPassword(individualOnBoardDetailsByTrnId.getUserPassword());
                user.setUserAccount(userAcc);
                dto.setUser(user);
                return dto != null ? dto : null;
            }
            else if ("yes".equalsIgnoreCase(issandorpoc1) && individualOnBoardDetailsByTrnId != null)
            {
                UpdateEmployeeRequestDTO dto = new UpdateEmployeeRequestDTO();
                UpdateEmployeeRequestDTO.PersonInformation personInfo = new UpdateEmployeeRequestDTO.PersonInformation();
                UpdateEmployeeRequestDTO.AccessAssignment access = new UpdateEmployeeRequestDTO.AccessAssignment();
                access.setAccessProfileName(individualOnBoardDetailsByTrnId.getAccessProfileName());
                access.setPreferenceProfileName(individualOnBoardDetailsByTrnId.getPreferenceProfileName());
                access.setProfessionalPayCodeName(individualOnBoardDetailsByTrnId.getProfessionalPayCodeName());
                access.setProfessionalWorkRuleName(individualOnBoardDetailsByTrnId.getProfessionalWorkRuleName());
                access.setShiftCodeName(individualOnBoardDetailsByTrnId.getShiftCodeName());
                personInfo.setAccessAssignment(access);
                UpdateEmployeeRequestDTO.EmailAddress email = new UpdateEmployeeRequestDTO.EmailAddress();
                email.setAddress(individualOnBoardDetailsByTrnId.getAddressEmail());
                email.setContactTypeName(individualOnBoardDetailsByTrnId.getContactTypeName());
                email.setHasEmailNotificationDelivery(false);
                personInfo.setEmailAddresses(Arrays.asList(email));
                UpdateEmployeeRequestDTO.EmploymentStatus empStatus = new UpdateEmployeeRequestDTO.EmploymentStatus();
                empStatus.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
                empStatus.setEmploymentStatusName(individualOnBoardDetailsByTrnId.getEmploymentStatus());
                empStatus.setExpirationDate("3000-01-01");
                personInfo.setEmploymentStatusList(Arrays.asList(empStatus));
                UpdateEmployeeRequestDTO.Person person = new UpdateEmployeeRequestDTO.Person();
                person.setHireDate(individualOnBoardDetailsByTrnId.getHireDate());
                person.setPersonNumber(individualOnBoardDetailsByTrnId.getGatePassId());
                person.setBirthDate(individualOnBoardDetailsByTrnId.getBirthDate());
                String firstName = individualOnBoardDetailsByTrnId.getFirstName() != null ? individualOnBoardDetailsByTrnId.getFirstName().trim() : "";
                String lastName = individualOnBoardDetailsByTrnId.getLastName() != null ? individualOnBoardDetailsByTrnId.getLastName().trim() : "";
                if (lastName.isEmpty()) {
                    lastName = ".";
                }

                person.setFirstName(firstName);
                person.setLastName(lastName);
                String fullName = firstName;
                if (!lastName.equals(".")) {
                    fullName = firstName + " " + lastName;
                }

                person.setFullName(fullName);
                String shortName = firstName;
                if (!lastName.equals(".")) {
                    shortName = firstName + " " + lastName.substring(0, 1);
                }

                person.setShortName(shortName);
                personInfo.setPerson(person);
                ArrayList<UpdateEmployeeRequestDTO.CustomDataDTO> addCustomeList = new ArrayList();
                if (individualOnBoardDetailsByTrnId.getGender() != null && !individualOnBoardDetailsByTrnId.getGender().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO gender = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    gender.setCustomDataTypeName("Gender");
                    gender.setText(individualOnBoardDetailsByTrnId.getGender());
                    addCustomeList.add(gender);
                }

                if (individualOnBoardDetailsByTrnId.getAadharNumber() != null && !individualOnBoardDetailsByTrnId.getAadharNumber().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO aadharNumber = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    aadharNumber.setCustomDataTypeName("Aadhar Number");
                    aadharNumber.setText(individualOnBoardDetailsByTrnId.getAadharNumber());
                    addCustomeList.add(aadharNumber);
                }

                if (individualOnBoardDetailsByTrnId.getAadharName() != null && !individualOnBoardDetailsByTrnId.getAadharName().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO aadharName = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    aadharName.setCustomDataTypeName("Name as Per Aadhar");
                    aadharName.setText(individualOnBoardDetailsByTrnId.getAadharName());
                    addCustomeList.add(aadharName);
                }

                if (individualOnBoardDetailsByTrnId.getRelativeName() != null && !individualOnBoardDetailsByTrnId.getRelativeName().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO relativeName = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    relativeName.setCustomDataTypeName("Father or Husband Name");
                    relativeName.setText(individualOnBoardDetailsByTrnId.getRelativeName());
                    addCustomeList.add(relativeName);
                }

                if (individualOnBoardDetailsByTrnId.getAddress() != null && !individualOnBoardDetailsByTrnId.getAddress().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentAddress = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentAddress.setCustomDataTypeName("Permanent Address");
                    permanentAddress.setText(individualOnBoardDetailsByTrnId.getRelativeName());
                    addCustomeList.add(permanentAddress);
                }

                if (individualOnBoardDetailsByTrnId.getPermanentDistrict() != null && !individualOnBoardDetailsByTrnId.getPermanentDistrict().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Permanent District");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentDistrict());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getPermanentState() != null && !individualOnBoardDetailsByTrnId.getPermanentState().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Permanent State");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentState());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getPermanentPincode() != null && !individualOnBoardDetailsByTrnId.getPermanentPincode().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Permanent Pin code");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentPincode());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getIdMark() != null && !individualOnBoardDetailsByTrnId.getIdMark().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("ID Mark");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getIdMark());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getUanNumber() != null && !individualOnBoardDetailsByTrnId.getUanNumber().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("UAN Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getUanNumber());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getMaritalStatus() != null && !individualOnBoardDetailsByTrnId.getMaritalStatus().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Marital Status");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getMaritalStatus());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getTechnical() != null && !individualOnBoardDetailsByTrnId.getTechnical().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Technical Qualification");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getTechnical());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getAcademic() != null && !individualOnBoardDetailsByTrnId.getAcademic().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Academic Qualification");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getAcademic());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getShoeSize() != null && !individualOnBoardDetailsByTrnId.getShoeSize().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Shoe Size");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getShoeSize());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getBloodGroup() != null && !individualOnBoardDetailsByTrnId.getBloodGroup().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Blood Group");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getBloodGroup());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getWorkmenType() != null && !individualOnBoardDetailsByTrnId.getWorkmenType().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Workmen Type");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getWorkmenType());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getNatureOfJob() != null && !individualOnBoardDetailsByTrnId.getNatureOfJob().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Nature Of Job");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getNatureOfJob());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getPanNumber() != null && !individualOnBoardDetailsByTrnId.getPanNumber().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("PAN Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPanNumber());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getPfNumber() != null && !individualOnBoardDetailsByTrnId.getPfNumber().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("PF Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPfNumber());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getAccountNumber() != null && !individualOnBoardDetailsByTrnId.getAccountNumber().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Account Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getAccountNumber());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getBankName() != null && !individualOnBoardDetailsByTrnId.getBankName().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Bank Name");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getBankName());
                    addCustomeList.add(permanentDistrict);
                }

                if (individualOnBoardDetailsByTrnId.getIfscCode() != null && !individualOnBoardDetailsByTrnId.getIfscCode().isEmpty()) {
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("IFSC Code");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getIfscCode());
                    addCustomeList.add(permanentDistrict);
                }

                personInfo.setCustomDataList(addCustomeList);
                UpdateEmployeeRequestDTO.PersonAuthenticationType auth = new UpdateEmployeeRequestDTO.PersonAuthenticationType();
                auth.setActiveFlag(true);
                auth.setAuthenticationTypeName("Basic");
                personInfo.setPersonAuthenticationTypes(Arrays.asList(auth));
                UpdateEmployeeRequestDTO.PersonLicenseType licenseEmployee = new UpdateEmployeeRequestDTO.PersonLicenseType();
                licenseEmployee.setActiveFlag(true);
                licenseEmployee.setLicenseTypeName("Employee");
                UpdateEmployeeRequestDTO.PersonLicenseType licenseAbsence = new UpdateEmployeeRequestDTO.PersonLicenseType();
                licenseAbsence.setActiveFlag(true);
                licenseAbsence.setLicenseTypeName("Absence");
                UpdateEmployeeRequestDTO.PersonLicenseType licensehourlyTimekeeping = new UpdateEmployeeRequestDTO.PersonLicenseType();
                licensehourlyTimekeeping.setActiveFlag(true);
                if (!String.valueOf(individualOnBoardDetailsByTrnId.getGatePassTypeId()).equals(GatePassType.CREATE.getStatus()) && !String.valueOf(individualOnBoardDetailsByTrnId.getGatePassTypeId()).equals(GatePassType.RENEW.getStatus())) {
                    if (String.valueOf(individualOnBoardDetailsByTrnId.getGatePassTypeId()).equals(GatePassType.PROJECT.getStatus())) {
                        licensehourlyTimekeeping.setLicenseTypeName("Salaried Timekeeping");
                    }
                } else {
                    licensehourlyTimekeeping.setLicenseTypeName("Hourly Timekeeping");
                }

                UpdateEmployeeRequestDTO.PersonLicenseType licenseScheduling = new UpdateEmployeeRequestDTO.PersonLicenseType();
                licenseScheduling.setActiveFlag(true);
                licenseScheduling.setLicenseTypeName("Scheduling");
                personInfo.setPersonLicenseTypes(Arrays.asList(licenseEmployee, licenseAbsence, licensehourlyTimekeeping, licenseScheduling));
                UpdateEmployeeRequestDTO.UserAccountStatus userStatus = new UpdateEmployeeRequestDTO.UserAccountStatus();
                userStatus.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
                userStatus.setExpirationDate("3000-01-01");
                userStatus.setUserAccountStatusName(individualOnBoardDetailsByTrnId.getUserAccountStatus());
                personInfo.setUserAccountStatusList(Arrays.asList(userStatus));
                dto.setPersonInformation(personInfo);
                UpdateEmployeeRequestDTO.JobAssignment job = new UpdateEmployeeRequestDTO.JobAssignment();
//                UpdateEmployeeRequestDTO.BaseWageRate wage = new UpdateEmployeeRequestDTO.BaseWageRate();
//                wage.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
//                wage.setExpirationDate("3000-01-01");
//                wage.setHourlyRate(20.15);
//                job.setBaseWageRates(Arrays.asList(wage));
                UpdateEmployeeRequestDTO.JobAssignmentDetails jobDetails = new UpdateEmployeeRequestDTO.JobAssignmentDetails();
                jobDetails.setPayRuleName(individualOnBoardDetailsByTrnId.getPayRuleName());
                jobDetails.setSupervisorName(individualOnBoardDetailsByTrnId.getSupervisorName());
                jobDetails.setSupervisorPersonNumber(individualOnBoardDetailsByTrnId.getSupervisorPersonNumber());
                jobDetails.setTimeZoneName("(GMT +05:30) Calcutta");
                job.setJobAssignmentDetails(jobDetails);
                UpdateEmployeeRequestDTO.PrimaryLaborAccount labor = new UpdateEmployeeRequestDTO.PrimaryLaborAccount();
                labor.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
                labor.setExpirationDate("3000-01-01");

                boolean b = this.wfdEmployeeService.verifyLaborCatEnInWFD(individualOnBoardDetailsByTrnId.getCategory());
                System.out.println(b +" :- avaiable to set");
                if(b){

                    String category = individualOnBoardDetailsByTrnId.getCategory();
                    System.out.println(category +" get from query workorder");

                    if (category != null && !category.isEmpty()) {
                        labor.setLaborCategoryName(category + ",,,,,");
                        System.out.println(labor.getLaborCategoryName() +" :- get this workorder to put in json");
                    }

                }else{
                    System.out.println(individualOnBoardDetailsByTrnId.getCategory() +"going for create workorder");
                    PostLaborCatDTO laborCategoryDto = gatePassToOnBoardService.createLaborCategoryDto(individualOnBoardDetailsByTrnId.getCategory());
                    String laborCatInWFD = wfdEmployeeService.createLaborCatInWFD(laborCategoryDto);
                    System.out.println(laborCatInWFD +"check that successfull or not");
                    if (laborCatInWFD != null && laborCatInWFD.startsWith("SUCCESS")) {
                        String category = individualOnBoardDetailsByTrnId.getCategory();
                        if (category != null && !category.isEmpty()) {
                            labor.setLaborCategoryName(category + ",,,,,");
                            System.out.println("format of to set labour category to post"+labor.getLaborCategoryName());
                        }
                    }

                }
                System.out.println(labor.getLaborCategoryName() +" :- final set json");


                String issandorpoc = getISSANDORPOC();
                String orgPath = "";
                if (issandorpoc != null) {
                    issandorpoc = issandorpoc.trim();

                }
                if ("yes".equalsIgnoreCase(issandorpoc)) {

                    String var10000 = individualOnBoardDetailsByTrnId.getLocation();
                    orgPath= var10000 + "/" + individualOnBoardDetailsByTrnId.getCompany() + "/" + individualOnBoardDetailsByTrnId.getPlantLocation() + "/" + individualOnBoardDetailsByTrnId.getDepartment() + "/" + individualOnBoardDetailsByTrnId.getSection() + "/" + individualOnBoardDetailsByTrnId.getSubSection() + "/" + individualOnBoardDetailsByTrnId.getContractorCode() + "/Team Lead";

                } else if ("no".equalsIgnoreCase(issandorpoc)) {

                    String skill = individualOnBoardDetailsByTrnId.getSkill();
                    boolean checkJob = wfdEmployeeService.verifyJobInWFD(skill,"1900-01-01");
                    if(!checkJob){
                        PostJobWfd jobByname = gatePassToOnBoardService.createJobByname(skill);
                        String jobInWFD = this.wfdEmployeeService.createJobInWFD(jobByname);


                    }
                    orgPath= individualOnBoardDetailsByTrnId.getCompany() + "/" + individualOnBoardDetailsByTrnId.getLocation()+ "/" + individualOnBoardDetailsByTrnId.getDepartment() + "/" + individualOnBoardDetailsByTrnId.getSection() + "/" +individualOnBoardDetailsByTrnId.getContractorCode() + "/"+skill;

                }else{

                }
                System.out.println(orgPath);

                labor.setOrganizationPath(
                        resolveOrganizationPath1(orgPath)
                );
                job.setPrimaryLaborAccounts(Arrays.asList(labor));
                dto.setJobAssignment(job);
                UpdateEmployeeRequestDTO.User user = new UpdateEmployeeRequestDTO.User();
                UpdateEmployeeRequestDTO.UserAccount userAcc = new UpdateEmployeeRequestDTO.UserAccount();
                userAcc.setLogonProfileName(individualOnBoardDetailsByTrnId.getLogonProfileName());
                userAcc.setUserName(individualOnBoardDetailsByTrnId.getUserAccountName());
                userAcc.setUserPassword(individualOnBoardDetailsByTrnId.getUserPassword());
                user.setUserAccount(userAcc);
                dto.setUser(user);
                return dto != null ? dto : null;
            }



            else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String updatePassEmpDtoDynamic(String GatePassId) {
        try {
            UpdateEmployeeRequestDTO employeeRequestDTO = this.gatePassUpdateEmpDto(GatePassId);
            if (employeeRequestDTO == null) {
                return "STATUS:400\nTransaction Id Not Found";
            } else {

                String employeeResponse = this.wfdEmployeeService.updateEmployee(employeeRequestDTO);
                String[] parts = employeeResponse.split("\nBODY:", 2);
                if (parts.length < 2) {
                    return "STATUS:500\nInvalid response format";
                } else {
                    int statusCode = Integer.parseInt(parts[0].replace("STATUS:", "").trim());
                    String body = parts[1];
                    if (statusCode != 200) {
                        return "STATUS:" + statusCode + "\n" + body;
                    } else {
                        JsonNode rootNode = this.objectMapper.readTree(body);
                        JsonNode personKeyNode = rootNode.path("personIdentity").path("personKey");
                        if (!personKeyNode.isMissingNode() && !personKeyNode.isNull()) {
                            Long personKey = personKeyNode.asLong();
                            SkillProLevelDateDTO skillData = this.getSkillPRoLevelDate(GatePassId);
                            if (skillData == null) {
                                return "STATUS:400\nSkill data not found from SQL query";
                            } else {
                                String skillName = skillData.getSkill();
                                String profName = skillData.getProficiencyLevel();
                                if (!this.wfdEmployeeService.verifySkillsInWFD(skillName)) {
                                    PostSkillWfd postSkill = new PostSkillWfd();
                                    postSkill.setName(skillName);
                                    this.wfdEmployeeService.createSkillsInWFD(postSkill);
                                }

                                if (!this.wfdEmployeeService.verifyProfInWFD(profName)) {
                                    System.out.println("going for create Prof"+profName);
                                    ProficiencyDTO profDto = new ProficiencyDTO();
                                    profDto.setId(329);
                                    profDto.setActive(true);
                                    profDto.setProficiencyLevelNumeric(329);
                                    profDto.setVersion(0);
                                    profDto.setName(profName);
                                    this.wfdEmployeeService.createProfInWFD(profDto);
                                }

                                this.wfdEmployeeService.addPersonSkill(skillData.getPersonNumber(), skillName, profName, skillData.getEffectiveDate());
                                return personKey.toString();
                            }
                        } else {
                            return "STATUS:400\n" + body;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public EmployeeRequestDTO mapFromGatePass(GatePassMain gatePass) {
        EmployeeRequestDTO dto = new EmployeeRequestDTO();
        EmployeeRequestDTO.PersonInformation personInfo = new EmployeeRequestDTO.PersonInformation();
        EmployeeRequestDTO.AccessAssignment access = new EmployeeRequestDTO.AccessAssignment();
        access.setAccessProfileName("Employee FAP");
        access.setPreferenceProfileName("Employee");
        access.setProfessionalPayCodeName("Empty Profile");
        access.setProfessionalWorkRuleName("Empty Profile");
        access.setShiftCodeName("Empty Profile");
        personInfo.setAccessAssignment(access);
        EmployeeRequestDTO.EmailAddress email = new EmployeeRequestDTO.EmailAddress();
        email.setAddress("");
        email.setContactTypeName("Work");
        email.setHasEmailNotificationDelivery(false);
        personInfo.setEmailAddresses(Arrays.asList(email));
        EmployeeRequestDTO.EmploymentStatus empStatus = new EmployeeRequestDTO.EmploymentStatus();
        empStatus.setEffectiveDate(gatePass.getDoj());
        empStatus.setEmploymentStatusName("Active");
        empStatus.setExpirationDate("3000-01-01");
        personInfo.setEmploymentStatusList(Arrays.asList(empStatus));
        EmployeeRequestDTO.Person person = new EmployeeRequestDTO.Person();
        person.setBirthDate(gatePass.getDateOfBirth());
        person.setFirstName(gatePass.getFirstName());
        person.setLastName(gatePass.getLastName());
        String var10001 = gatePass.getLastName();
        person.setFullName(var10001 + ", " + gatePass.getFirstName());
        person.setHireDate(gatePass.getDoj());
        person.setPersonNumber(gatePass.getGatePassId() != null ? gatePass.getGatePassId() : gatePass.getTransactionId());
        var10001 = gatePass.getFirstName();
        person.setShortName(var10001 + String.valueOf(gatePass.getLastName() != null ? gatePass.getLastName().charAt(0) : ""));
        personInfo.setPerson(person);
        EmployeeRequestDTO.PersonAuthenticationType auth = new EmployeeRequestDTO.PersonAuthenticationType();
        auth.setActiveFlag(true);
        auth.setAuthenticationTypeName("Basic");
        personInfo.setPersonAuthenticationTypes(Arrays.asList(auth));
        EmployeeRequestDTO.PersonLicenseType licenseEmployee = new EmployeeRequestDTO.PersonLicenseType();
        licenseEmployee.setActiveFlag(true);
        licenseEmployee.setLicenseTypeName("Employee");
        EmployeeRequestDTO.PersonLicenseType licenseAbsence = new EmployeeRequestDTO.PersonLicenseType();
        licenseAbsence.setActiveFlag(true);
        licenseAbsence.setLicenseTypeName("Absence");
        EmployeeRequestDTO.PersonLicenseType licensehourlyTimekeeping = new EmployeeRequestDTO.PersonLicenseType();
        licensehourlyTimekeeping.setActiveFlag(true);
        licensehourlyTimekeeping.setLicenseTypeName("Hourly Timekeeping");
        EmployeeRequestDTO.PersonLicenseType licenseScheduling = new EmployeeRequestDTO.PersonLicenseType();
        licenseScheduling.setActiveFlag(true);
        licenseScheduling.setLicenseTypeName("Scheduling");
        personInfo.setPersonLicenseTypes(Arrays.asList(licenseEmployee, licenseAbsence, licensehourlyTimekeeping, licenseScheduling));
        EmployeeRequestDTO.UserAccountStatus userStatus = new EmployeeRequestDTO.UserAccountStatus();
        userStatus.setEffectiveDate(gatePass.getDoj());
        userStatus.setExpirationDate("3000-01-01");
        userStatus.setUserAccountStatusName("Active");
        personInfo.setUserAccountStatusList(Arrays.asList(userStatus));
        dto.setPersonInformation(personInfo);
        EmployeeRequestDTO.JobAssignment job = new EmployeeRequestDTO.JobAssignment();
        EmployeeRequestDTO.BaseWageRate wage = new EmployeeRequestDTO.BaseWageRate();
        wage.setEffectiveDate(gatePass.getDoj());
        wage.setExpirationDate("3000-01-01");
        if (gatePass.getBasic() != null) {
            double hourlyRate = gatePass.getBasic().doubleValue() / (double)173.0F;
            wage.setHourlyRate(hourlyRate);
        } else {
            wage.setHourlyRate((double)0.0F);
        }

        job.setBaseWageRates(Arrays.asList(wage));
        EmployeeRequestDTO.JobAssignmentDetails jobDetails = new EmployeeRequestDTO.JobAssignmentDetails();
        jobDetails.setPayRuleName(gatePass.getWageCategory() != null ? gatePass.getWageCategory() : "Default Rule");
        jobDetails.setSupervisorName(gatePass.getEic());
        jobDetails.setSupervisorPersonNumber("BR0001");
        jobDetails.setTimeZoneName("(GMT +05:30) Calcutta");
        job.setJobAssignmentDetails(jobDetails);
        EmployeeRequestDTO.PrimaryLaborAccount labor = new EmployeeRequestDTO.PrimaryLaborAccount();
        labor.setEffectiveDate(gatePass.getDoj());
        labor.setExpirationDate("3000-01-01");
        labor.setOrganizationPath("DOT1 Solutions Pvt Ltd/Banglore/Main Plant/IT/IT/General/Bravispach/Team Lead");
        job.setPrimaryLaborAccounts(Arrays.asList(labor));
        dto.setJobAssignment(job);
        EmployeeRequestDTO.User user = new EmployeeRequestDTO.User();
        EmployeeRequestDTO.UserAccount userAcc = new EmployeeRequestDTO.UserAccount();
        userAcc.setLogonProfileName("Default");
        userAcc.setUserName(gatePass.getUserId() != null ? gatePass.getUserId() : gatePass.getFirstName().toLowerCase() + "." + gatePass.getLastName().toLowerCase());
        userAcc.setUserPassword("Kronos@12321");
        user.setUserAccount(userAcc);
        dto.setUser(user);
        return dto;
    }

    @Scheduled(
            cron = "0 0 10 * * ?"
    )
    public void setupWorkorderMail() {
        try {
            log.info("Email Service Start");
            List<WorkOrderDTOMail> expiringWorkOrders = this.gatePassToOnBoardService.getExpiringWorkOrders();
            String regardsEmail = this.getRegardsEmail();
            Map<Long, List<WorkOrderDTOMail>> grouped = (Map)expiringWorkOrders.stream().collect(Collectors.groupingBy(WorkOrderDTOMail::getContractorId));

            for(Map.Entry<Long, List<WorkOrderDTOMail>> entry : grouped.entrySet()) {
                List<WorkOrderDTOMail> value = (List)entry.getValue();
                String bodyMail = this.buildHtmlTable(value, regardsEmail);
                Set<String> mailSends = new HashSet();

                for(WorkOrderDTOMail order : value) {
                    if (order.getConEmail() != null && !order.getConEmail().isEmpty()) {
                        mailSends.add(order.getConEmail());
                    }
                }

                if (!mailSends.isEmpty()) {
                    String subject = "Workorder Expiry Notification " + String.valueOf(LocalDate.now());
                    this.emailService.sendHtmlMail(mailSends, subject, bodyMail);
                }
            }

            Map<String, List<WorkOrderDTOMail>> groupedHr = (Map)expiringWorkOrders.stream().collect(Collectors.groupingBy(WorkOrderDTOMail::getUnitCode));

            for(Map.Entry<String, List<WorkOrderDTOMail>> hrMailEnty : groupedHr.entrySet()) {
                List<WorkOrderDTOMail> value = (List)hrMailEnty.getValue();
                String bodyMail = this.buildHtmlTable(value, regardsEmail);
                Set<String> hrMailByunitName = this.gatePassToOnBoardService.getHrMailByunitName((String)hrMailEnty.getKey());
                if (!hrMailByunitName.isEmpty() && hrMailByunitName != null) {
                    String subject = "Workorder Expiry Notification " + String.valueOf(LocalDate.now());
                    this.emailService.sendHtmlMail(hrMailByunitName, subject, bodyMail);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String buildHtmlTable(List<WorkOrderDTOMail> list, String regards) {
        StringBuilder html = new StringBuilder();
        html.append("<html><body>");
        html.append("<h3>Work Orders Expiring Within 1 Month</h3>");
        html.append("<table border='1' cellpadding='5' cellspacing='0' style='border-collapse:collapse;'>");
        html.append("<tr style='background-color:#f2f2f2;'>").append("<th>Contractor ID</th>").append("<th>Contractor Code</th>").append("<th>Principal Employer</th>").append("<th>Contractor</th>").append("<th>Work Order ID</th>").append("<th>SAP Work Order</th>").append("<th>Valid Till</th>").append("</tr>");

        for(WorkOrderDTOMail dto : list) {
            html.append("<tr>").append("<td>").append(dto.getContractorId()).append("</td>").append("<td>").append(dto.getCode()).append("</td>").append("<td>").append(dto.getUnitName()).append("</td>").append("<td>").append(dto.getContractor()).append("</td>").append("<td>").append(dto.getWorkOrderId()).append("</td>").append("<td>").append(dto.getSapWorkOrderNum()).append("</td>").append("<td>").append(dto.getValidDt()).append("</td>").append("</tr>");
        }

        html.append("</table>");
        html.append("<br><br>Regards,<br>");
        html.append(regards);
        return html.toString();
    }

    @Scheduled(
            cron = "0 0 10 * * ?"
    )
    public void setupLaborLMail() {
        try {
            log.info("Email Service Start");
            List<WorkOrderDTOMail> expiringWorkOrders = this.gatePassToOnBoardService.getExpiringLL();
            String regardsEmail = this.getRegardsEmail();
            Map<Long, List<WorkOrderDTOMail>> grouped = (Map)expiringWorkOrders.stream().collect(Collectors.groupingBy(WorkOrderDTOMail::getContractorId));

            for(Map.Entry<Long, List<WorkOrderDTOMail>> entry : grouped.entrySet()) {
                List<WorkOrderDTOMail> value = (List)entry.getValue();
                String bodyMail = this.buildHtmlTableLL(value, regardsEmail);
                Set<String> mailSends = new HashSet();

                for(WorkOrderDTOMail order : value) {
                    if (order.getConEmail() != null && !order.getConEmail().isEmpty()) {
                        mailSends.add(order.getConEmail());
                    }
                }

                if (!mailSends.isEmpty()) {
                    String subject = "Labor License Expiry Notification " + String.valueOf(LocalDate.now());
                    this.emailService.sendHtmlMail(mailSends, subject, bodyMail);
                }
            }

            Map<String, List<WorkOrderDTOMail>> groupedHr = (Map)expiringWorkOrders.stream().collect(Collectors.groupingBy(WorkOrderDTOMail::getUnitCode));

            for(Map.Entry<String, List<WorkOrderDTOMail>> hrMailEnty : groupedHr.entrySet()) {
                List<WorkOrderDTOMail> value = (List)hrMailEnty.getValue();
                String bodyMail = this.buildHtmlTableLL(value, regardsEmail);
                Set<String> hrMailByunitName = this.gatePassToOnBoardService.getHrMailByunitName((String)hrMailEnty.getKey());
                if (!hrMailByunitName.isEmpty() && hrMailByunitName != null) {
                    String subject = "Labor License Expiry Notification " + String.valueOf(LocalDate.now());
                    this.emailService.sendHtmlMail(hrMailByunitName, subject, bodyMail);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String buildHtmlTableLL(List<WorkOrderDTOMail> list, String regards) {
        StringBuilder html = new StringBuilder();
        html.append("<html><body>");
        html.append("<h3>Labor License Expiring Within 1 Month</h3>");
        html.append("<table border='1' cellpadding='5' cellspacing='0' style='border-collapse:collapse;'>");
        html.append("<tr style='background-color:#f2f2f2;'>").append("<th>Contractor ID</th>").append("<th>Contractor Code</th>").append("<th>Principal Employer</th>").append("<th>Contractor</th>").append("<th>WorkOrder Number</th>").append("<th>License Number</th>").append("<th>Valid Till</th>").append("</tr>");

        for(WorkOrderDTOMail dto : list) {
            html.append("<tr>").append("<td>").append(dto.getContractorId()).append("</td>").append("<td>").append(dto.getCode()).append("</td>").append("<td>").append(dto.getUnitName()).append("</td>").append("<td>").append(dto.getContractor()).append("</td>").append("<td>").append(dto.getWorkOrderId()).append("</td>").append("<td>").append(dto.getSapWorkOrderNum()).append("</td>").append("<td>").append(dto.getValidDt()).append("</td>").append("</tr>");
        }

        html.append("</table>");
        html.append("<br><br>Regards,<br>");
        html.append(regards);
        html.append("</body></html>");
        return html.toString();
    }
}
