package com.wfd.dot1.cwfm.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dto.EmployeeRequestDTO;
import com.wfd.dot1.cwfm.dto.GatePassToOnBoard;
import com.wfd.dot1.cwfm.dto.PostSkillWfd;
import com.wfd.dot1.cwfm.dto.SkillProLevelDateDTO;
import com.wfd.dot1.cwfm.dto.UpdateEmployeeRequestDTO;
import com.wfd.dot1.cwfm.pojo.GatePassMain;


@Service
public class EmployeeMapper  {


    @Autowired
    private WfdEmployeeService wfdEmployeeService;

    @Autowired
    private GatePassToOnBoardService gatePassToOnBoardService;

    public String gatePassEmpDtoStatic(String GatePassId ){
        try {

            GatePassToOnBoard individualOnBoardDetailsByTrnId = gatePassToOnBoardService.getIndividualOnBoardDetailsByTrnId(GatePassId);
//Condition

            if(true){
            EmployeeRequestDTO dto = new EmployeeRequestDTO();

            // --- PersonInformation ---
            EmployeeRequestDTO.PersonInformation personInfo = new EmployeeRequestDTO.PersonInformation();

            // AccessAssignment mapping (example logic, adjust as per your rules)
            EmployeeRequestDTO.AccessAssignment access = new EmployeeRequestDTO.AccessAssignment();
            access.setAccessProfileName("Employee FAP");
            access.setPreferenceProfileName("Employee");
            access.setProfessionalPayCodeName("Empty Profile");
            access.setProfessionalWorkRuleName("Empty Profile");
            access.setShiftCodeName("Empty Profile");
            personInfo.setAccessAssignment(access);

            // Email address (if mobile/email available)
            EmployeeRequestDTO.EmailAddress email = new EmployeeRequestDTO.EmailAddress();
            email.setAddress(""); // if you have email in GatePassMain add here
            email.setContactTypeName("Work");
            email.setHasEmailNotificationDelivery(false);
            personInfo.setEmailAddresses(Arrays.asList(email));

            // Employment status
            EmployeeRequestDTO.EmploymentStatus empStatus = new EmployeeRequestDTO.EmploymentStatus();
            empStatus.setEffectiveDate("2025-08-15");  // joining date
            empStatus.setEmploymentStatusName("Active");
            empStatus.setExpirationDate("3000-01-01");
            personInfo.setEmploymentStatusList(Arrays.asList(empStatus));

            // Person details
            EmployeeRequestDTO.Person person = new EmployeeRequestDTO.Person();
            person.setBirthDate("2000-08-29");
            person.setFirstName("ritesh");
            person.setLastName("malhar");
            person.setFullName("malhar, ritesh");
            person.setHireDate("2025-08-15");
            person.setPersonNumber(GatePassId);
            person.setShortName("malharR");
            personInfo.setPerson(person);

            // Authentication types
            EmployeeRequestDTO.PersonAuthenticationType auth = new EmployeeRequestDTO.PersonAuthenticationType();
            auth.setActiveFlag(true);
            auth.setAuthenticationTypeName("Basic");
            personInfo.setPersonAuthenticationTypes(Arrays.asList(auth));

            // License types
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

            personInfo.setPersonLicenseTypes(Arrays.asList(
                    licenseEmployee,
                    licenseAbsence,
                    licensehourlyTimekeeping,
                    licenseScheduling
            ));


            // User account status
            EmployeeRequestDTO.UserAccountStatus userStatus = new EmployeeRequestDTO.UserAccountStatus();
            userStatus.setEffectiveDate("2025-08-15");
            userStatus.setExpirationDate("3000-01-01");
            userStatus.setUserAccountStatusName("Active");
            personInfo.setUserAccountStatusList(Arrays.asList(userStatus));

            dto.setPersonInformation(personInfo);

            // --- JobAssignment ---
            EmployeeRequestDTO.JobAssignment job = new EmployeeRequestDTO.JobAssignment();

            EmployeeRequestDTO.BaseWageRate wage = new EmployeeRequestDTO.BaseWageRate();
            wage.setEffectiveDate("2025-08-15");
            wage.setExpirationDate("3000-01-01");
            // Example: convert monthly basic to hourly rate
//            if (gatePass.getBasic() != null) {
//                double hourlyRate = gatePass.getBasic().doubleValue() / 173; // approx monthly to hourly
//                wage.setHourlyRate(hourlyRate);
//            } else {
            wage.setHourlyRate(20.15);
//            }
            job.setBaseWageRates(Arrays.asList(wage));

            EmployeeRequestDTO.JobAssignmentDetails jobDetails = new EmployeeRequestDTO.JobAssignmentDetails();
            jobDetails.setPayRuleName("CW BAR MALE PR");
            jobDetails.setSupervisorName("Bharthi"); // EIC → supervisor
            jobDetails.setSupervisorPersonNumber("BR0001");  // hardcoded, replace with mapping
            jobDetails.setTimeZoneName("(GMT +05:30) Calcutta");
            job.setJobAssignmentDetails(jobDetails);
            EmployeeRequestDTO.PrimaryLaborAccount labor = new EmployeeRequestDTO.PrimaryLaborAccount();
            labor.setEffectiveDate("2025-08-15");
            labor.setExpirationDate("3000-01-01");
            labor.setOrganizationPath("DOT1 Solutions Pvt Ltd/Banglore/Main Plant/IT/IT/General/Bravispach/Team Lead");
            job.setPrimaryLaborAccounts(Arrays.asList(labor));

            dto.setJobAssignment(job);

            // --- User ---
            EmployeeRequestDTO.User user = new EmployeeRequestDTO.User();
            EmployeeRequestDTO.UserAccount userAcc = new EmployeeRequestDTO.UserAccount();
            userAcc.setLogonProfileName("Default");
            userAcc.setUserName("ritesh.malhar");
            userAcc.setUserPassword("Kronos@123"); // default password, can be generated
            user.setUserAccount(userAcc);
            dto.setUser(user);

            String employee = wfdEmployeeService.createEmployee(dto);



            if (employee != null) {
                return employee;
            } else {
                return "issue into mapping method";
            }

        }else{
            return "not find or not working repo";
        }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public EmployeeRequestDTO gatePassEmpDto(String GatePassId ){
        try {

            GatePassToOnBoard individualOnBoardDetailsByTrnId = gatePassToOnBoardService.getIndividualOnBoardDetailsByTrnId(GatePassId);
//Condition

            if(individualOnBoardDetailsByTrnId!=null){
                EmployeeRequestDTO dto = new EmployeeRequestDTO();

                // --- PersonInformation ---
                EmployeeRequestDTO.PersonInformation personInfo = new EmployeeRequestDTO.PersonInformation();

                // AccessAssignment mapping (example logic, adjust as per your rules)
                EmployeeRequestDTO.AccessAssignment access = new EmployeeRequestDTO.AccessAssignment();
                access.setAccessProfileName(individualOnBoardDetailsByTrnId.getAccessProfileName());
                access.setPreferenceProfileName(individualOnBoardDetailsByTrnId.getPreferenceProfileName());
                access.setProfessionalPayCodeName(individualOnBoardDetailsByTrnId.getProfessionalPayCodeName());
                access.setProfessionalWorkRuleName(individualOnBoardDetailsByTrnId.getProfessionalWorkRuleName());
                access.setShiftCodeName(individualOnBoardDetailsByTrnId.getShiftCodeName());
                personInfo.setAccessAssignment(access);

                // Email address (if mobile/email available)// wants to add it into a config file
                EmployeeRequestDTO.EmailAddress email = new EmployeeRequestDTO.EmailAddress();
                email.setAddress(individualOnBoardDetailsByTrnId.getAddressEmail()); // if you have email in GatePassMain add here
                email.setContactTypeName(individualOnBoardDetailsByTrnId.getContactTypeName());
                email.setHasEmailNotificationDelivery(false);
                personInfo.setEmailAddresses(Arrays.asList(email));

                // Employment status
                EmployeeRequestDTO.EmploymentStatus empStatus = new EmployeeRequestDTO.EmploymentStatus();
                empStatus.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());  // joining date
                empStatus.setEmploymentStatusName(individualOnBoardDetailsByTrnId.getEmploymentStatus());
                empStatus.setExpirationDate("3000-01-01");
                personInfo.setEmploymentStatusList(Arrays.asList(empStatus));

                // Person details
                EmployeeRequestDTO.Person person = new EmployeeRequestDTO.Person();
                person.setBirthDate(individualOnBoardDetailsByTrnId.getBirthDate());
                person.setFirstName(individualOnBoardDetailsByTrnId.getFirstName());
                person.setLastName(individualOnBoardDetailsByTrnId.getLastName());
                person.setFullName(individualOnBoardDetailsByTrnId.getLastName()+ ", " + individualOnBoardDetailsByTrnId.getFirstName());
                person.setHireDate(individualOnBoardDetailsByTrnId.getHireDate());
                person.setPersonNumber(individualOnBoardDetailsByTrnId.getGatePassId());
                person.setShortName(individualOnBoardDetailsByTrnId.getFirstName() + (individualOnBoardDetailsByTrnId.getLastName() != null ? individualOnBoardDetailsByTrnId.getLastName().charAt(0) : ""));
                personInfo.setPerson(person);
                
                //CustomData details
//                ArrayList<>
//***********************Additional information********************
                ArrayList<EmployeeRequestDTO.CustomDataDTO> addCustomeList = new ArrayList<>();

                if(individualOnBoardDetailsByTrnId.getGender()!=null && !individualOnBoardDetailsByTrnId.getGender().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO gender = new EmployeeRequestDTO.CustomDataDTO();
                    gender.setCustomDataTypeName("Gender");
                    gender.setText(individualOnBoardDetailsByTrnId.getGender());
                    addCustomeList.add(gender);
                }  if ((individualOnBoardDetailsByTrnId.getAadharNumber()!=null && !individualOnBoardDetailsByTrnId.getAadharNumber().isEmpty()))
                {
                    EmployeeRequestDTO.CustomDataDTO aadharNumber = new EmployeeRequestDTO.CustomDataDTO();
                    aadharNumber.setCustomDataTypeName("Aadhar Number");
                    aadharNumber.setText(individualOnBoardDetailsByTrnId.getAadharNumber());
                    addCustomeList.add(aadharNumber);

                } if ((individualOnBoardDetailsByTrnId.getAadharName()!=null && !individualOnBoardDetailsByTrnId.getAadharName().isEmpty())){
                    EmployeeRequestDTO.CustomDataDTO aadharName = new EmployeeRequestDTO.CustomDataDTO();
                    aadharName.setCustomDataTypeName("Name as Per Aadhar");
                    aadharName.setText(individualOnBoardDetailsByTrnId.getAadharName());
                    addCustomeList.add(aadharName);

                } if(individualOnBoardDetailsByTrnId.getRelativeName()!=null && !individualOnBoardDetailsByTrnId.getRelativeName().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO relativeName = new EmployeeRequestDTO.CustomDataDTO();
                    relativeName.setCustomDataTypeName("Father or Husband Name");
                    relativeName.setText(individualOnBoardDetailsByTrnId.getRelativeName());
                    addCustomeList.add(relativeName);

                } if(individualOnBoardDetailsByTrnId.getAddress()!=null && !individualOnBoardDetailsByTrnId.getAddress().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO permanentAddress = new EmployeeRequestDTO.CustomDataDTO();
                    permanentAddress.setCustomDataTypeName("Permanent Address");
                    permanentAddress.setText(individualOnBoardDetailsByTrnId.getRelativeName());
                    addCustomeList.add(permanentAddress);

                } if(individualOnBoardDetailsByTrnId.getPermanentDistrict()!=null && !individualOnBoardDetailsByTrnId.getPermanentDistrict().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Permanent District");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentDistrict());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getPermanentState()!=null && !individualOnBoardDetailsByTrnId.getPermanentState().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Permanent State");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentState());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getPermanentPincode()!=null && !individualOnBoardDetailsByTrnId.getPermanentPincode().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Permanent Pin code");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentPincode());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getIdMark()!=null && !individualOnBoardDetailsByTrnId.getIdMark().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("ID Mark");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getIdMark());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getUanNumber()!=null && !individualOnBoardDetailsByTrnId.getUanNumber().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("UAN Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getUanNumber());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getMaritalStatus()!=null && !individualOnBoardDetailsByTrnId.getMaritalStatus().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Marital Status");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getMaritalStatus());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getTechnical()!=null && !individualOnBoardDetailsByTrnId.getTechnical().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Technical Qualification");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getTechnical());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getAcademic()!=null && !individualOnBoardDetailsByTrnId.getAcademic().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Academic Qualification");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getAcademic());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getShoeSize()!=null && !individualOnBoardDetailsByTrnId.getShoeSize().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Shoe Size");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getShoeSize());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getBloodGroup()!=null && !individualOnBoardDetailsByTrnId.getBloodGroup().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Blood Group");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getBloodGroup());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getWorkmenType()!=null && !individualOnBoardDetailsByTrnId.getWorkmenType().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Workmen Type");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getWorkmenType());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getNatureOfJob()!=null && !individualOnBoardDetailsByTrnId.getNatureOfJob().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Nature Of Job");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getNatureOfJob());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getPanNumber()!=null && !individualOnBoardDetailsByTrnId.getPanNumber().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("PAN Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPanNumber());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getPfNumber()!=null && !individualOnBoardDetailsByTrnId.getPfNumber().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("PF Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPfNumber());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getAccountNumber()!=null && !individualOnBoardDetailsByTrnId.getAccountNumber().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Account Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getAccountNumber());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getBankName()!=null && !individualOnBoardDetailsByTrnId.getBankName().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Bank Name");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getBankName());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getIfscCode()!=null && !individualOnBoardDetailsByTrnId.getIfscCode().isEmpty()){
                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("IFSC Code");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getIfscCode());
                    addCustomeList.add(permanentDistrict);

                }
                personInfo.setCustomDataList(addCustomeList);

                // Authentication types
                EmployeeRequestDTO.PersonAuthenticationType auth = new EmployeeRequestDTO.PersonAuthenticationType();
                auth.setActiveFlag(true);
                auth.setAuthenticationTypeName("Basic");
                personInfo.setPersonAuthenticationTypes(Arrays.asList(auth));

                // License types
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

                personInfo.setPersonLicenseTypes(Arrays.asList(
                        licenseEmployee,
                        licenseAbsence,
                        licensehourlyTimekeeping,
                        licenseScheduling
                ));


                // User account status
                EmployeeRequestDTO.UserAccountStatus userStatus = new EmployeeRequestDTO.UserAccountStatus();
                userStatus.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
                userStatus.setExpirationDate("3000-01-01");
                userStatus.setUserAccountStatusName(individualOnBoardDetailsByTrnId.getUserAccountStatus());
                personInfo.setUserAccountStatusList(Arrays.asList(userStatus));

                dto.setPersonInformation(personInfo);

                // --- JobAssignment ---
                EmployeeRequestDTO.JobAssignment job = new EmployeeRequestDTO.JobAssignment();

                EmployeeRequestDTO.BaseWageRate wage = new EmployeeRequestDTO.BaseWageRate();
                wage.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
                wage.setExpirationDate("3000-01-01");
                // Example: convert monthly basic to hourly rate
//            if (gatePass.getBasic() != null) {
//                double hourlyRate = gatePass.getBasic().doubleValue() / 173; // approx monthly to hourly
//                wage.setHourlyRate(hourlyRate);
//            } else {
                wage.setHourlyRate(20.15);
//            }
                job.setBaseWageRates(Arrays.asList(wage));
                EmployeeRequestDTO.JobAssignmentDetails jobDetails = new EmployeeRequestDTO.JobAssignmentDetails();
                jobDetails.setPayRuleName(individualOnBoardDetailsByTrnId.getPayRuleName());
                jobDetails.setSupervisorName(individualOnBoardDetailsByTrnId.getSupervisorName()); // EIC → supervisor
                jobDetails.setSupervisorPersonNumber(individualOnBoardDetailsByTrnId.getSupervisorPersonNumber());  // hardcoded, replace with mapping
                jobDetails.setTimeZoneName("(GMT +05:30) Calcutta");
                job.setJobAssignmentDetails(jobDetails);
                EmployeeRequestDTO.PrimaryLaborAccount labor = new EmployeeRequestDTO.PrimaryLaborAccount();
                labor.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
                labor.setExpirationDate("3000-01-01");
//              labor.setOrganizationPath(individualOnBoardDetailsByTrnId.getCompany()+ "/" +individualOnBoardDetailsByTrnId.getLocation()+ "/" + individualOnBoardDetailsByTrnId.getPlantLocation() + "/" + individualOnBoardDetailsByTrnId.getDepartment() + "/" + individualOnBoardDetailsByTrnId.getSection() + "/" + individualOnBoardDetailsByTrnId.getSubSection() + "/" + individualOnBoardDetailsByTrnId.getContractorCode() + "/" +individualOnBoardDetailsByTrnId.getCategory());
              //  labor.setOrganizationPath("DOT1 Solutions Pvt Ltd/Banglore/Main Plant/IT/IT/General/Bravispach/Team Lead");
                
               String orgPath = individualOnBoardDetailsByTrnId.getLocation()+"/"+individualOnBoardDetailsByTrnId.getCompany()+"/"+
            		   individualOnBoardDetailsByTrnId.getPlantLocation()+"/"+individualOnBoardDetailsByTrnId.getDepartment()+"/"+
            		   individualOnBoardDetailsByTrnId.getSection()+"/"+individualOnBoardDetailsByTrnId.getSubSection()+"/"+
            		   individualOnBoardDetailsByTrnId.getContractorCode()+"/Team Lead";
               System.out.println("orgPath"+orgPath);
               labor.setOrganizationPath(orgPath);
                job.setPrimaryLaborAccounts(Arrays.asList(labor));
                dto.setJobAssignment(job);
                // --- User ---
                EmployeeRequestDTO.User user = new EmployeeRequestDTO.User();
                EmployeeRequestDTO.UserAccount userAcc = new EmployeeRequestDTO.UserAccount();
                userAcc.setLogonProfileName(individualOnBoardDetailsByTrnId.getLogonProfileName());
                userAcc.setUserName(individualOnBoardDetailsByTrnId.getUserAccountName());
                userAcc.setUserPassword(individualOnBoardDetailsByTrnId.getUserPassword()); // default password, can be generated
                user.setUserAccount(userAcc);
                dto.setUser(user);

//                String employee = wfdEmployeeService.createEmployee(dto);


                if (dto != null) {
                    return dto;
                } else {
                    return null;
                }

            }else{
                return null;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }




    }

    public String gatePassEmpDtoDynamic(String GatePassId ){
        try {
            EmployeeRequestDTO employeeRequestDTO = gatePassEmpDto(GatePassId);

           if(employeeRequestDTO!=null){
                String employee = wfdEmployeeService.createEmployee(employeeRequestDTO);


                if (employee != null) {

                    SkillProLevelDateDTO skillPRoLevelDate = getSkillPRoLevelDate(GatePassId);


                    String s = wfdEmployeeService.addPersonSkill(skillPRoLevelDate.getPersonNumber(), skillPRoLevelDate.getSkill(), skillPRoLevelDate.getProficiencyLevel(), skillPRoLevelDate.getEffectiveDate());


                    return employee+"*****************************************************************"+s;


                } else {
                    return "Issue in Json or API";
                }

            }else{
                return "Not fount transcation Id";
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }




    }





    public SkillProLevelDateDTO getSkillPRoLevelDate(String trndID){
        try{
            SkillProLevelDateDTO onlySkillProByTrnId = gatePassToOnBoardService.getOnlySkillProByTrnId(trndID);
            return onlySkillProByTrnId;


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UpdateEmployeeRequestDTO  gatePassUpdateEmpDto(String GatePassId ){
        try {
            GatePassToOnBoard individualOnBoardDetailsByTrnId = gatePassToOnBoardService.getIndividualOnBoardDetailsByTrnId(GatePassId);

            //Condition
            if(individualOnBoardDetailsByTrnId!=null){
                UpdateEmployeeRequestDTO dto = new UpdateEmployeeRequestDTO();

                // --- PersonInformation ---

                UpdateEmployeeRequestDTO.PersonInformation personInfo = new UpdateEmployeeRequestDTO.PersonInformation();

                // AccessAssignment mapping (example logic, adjust as per your rules)
                UpdateEmployeeRequestDTO.AccessAssignment access = new UpdateEmployeeRequestDTO.AccessAssignment();
                access.setAccessProfileName(individualOnBoardDetailsByTrnId.getAccessProfileName());
                access.setPreferenceProfileName(individualOnBoardDetailsByTrnId.getPreferenceProfileName());
                access.setProfessionalPayCodeName(individualOnBoardDetailsByTrnId.getProfessionalPayCodeName());
                access.setProfessionalWorkRuleName(individualOnBoardDetailsByTrnId.getProfessionalWorkRuleName());
                access.setShiftCodeName(individualOnBoardDetailsByTrnId.getShiftCodeName());
                personInfo.setAccessAssignment(access);

                // Email address (if mobile/email available)// wants to add it into a config file
                UpdateEmployeeRequestDTO.EmailAddress email = new UpdateEmployeeRequestDTO.EmailAddress();
                email.setAddress(individualOnBoardDetailsByTrnId.getAddressEmail()); // if you have email in GatePassMain add here
                email.setContactTypeName(individualOnBoardDetailsByTrnId.getContactTypeName());
                email.setHasEmailNotificationDelivery(false);
                personInfo.setEmailAddresses(Arrays.asList(email));

                // Employment status
                UpdateEmployeeRequestDTO.EmploymentStatus empStatus = new UpdateEmployeeRequestDTO.EmploymentStatus();
                empStatus.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());  // joining date
                empStatus.setEmploymentStatusName(individualOnBoardDetailsByTrnId.getEmploymentStatus());
                empStatus.setExpirationDate("3000-01-01");
                personInfo.setEmploymentStatusList(Arrays.asList(empStatus));

                // Person details
                UpdateEmployeeRequestDTO.Person person = new UpdateEmployeeRequestDTO.Person();
                person.setBirthDate(individualOnBoardDetailsByTrnId.getBirthDate());
                person.setFirstName(individualOnBoardDetailsByTrnId.getFirstName());
                person.setLastName(individualOnBoardDetailsByTrnId.getLastName());
                person.setFullName(individualOnBoardDetailsByTrnId.getLastName()+ ", " + individualOnBoardDetailsByTrnId.getFirstName());
                person.setHireDate(individualOnBoardDetailsByTrnId.getHireDate());
                person.setPersonNumber(individualOnBoardDetailsByTrnId.getGatePassId());
                person.setShortName(individualOnBoardDetailsByTrnId.getFirstName() + (individualOnBoardDetailsByTrnId.getLastName() != null ? individualOnBoardDetailsByTrnId.getLastName().charAt(0) : ""));
                personInfo.setPerson(person);

                //CustomData details
//                ArrayList<>
//***********************Additional information********************
                ArrayList<UpdateEmployeeRequestDTO.CustomDataDTO> addCustomeList = new ArrayList<>();

                if(individualOnBoardDetailsByTrnId.getGender()!=null && !individualOnBoardDetailsByTrnId.getGender().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO gender = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    gender.setCustomDataTypeName("Gender");
                    gender.setText(individualOnBoardDetailsByTrnId.getGender());
                    addCustomeList.add(gender);
                }  if ((individualOnBoardDetailsByTrnId.getAadharNumber()!=null && !individualOnBoardDetailsByTrnId.getAadharNumber().isEmpty()))
                {
                    UpdateEmployeeRequestDTO.CustomDataDTO aadharNumber = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    aadharNumber.setCustomDataTypeName("Aadhar Number");
                    aadharNumber.setText(individualOnBoardDetailsByTrnId.getAadharNumber());
                    addCustomeList.add(aadharNumber);

                } if ((individualOnBoardDetailsByTrnId.getAadharName()!=null && !individualOnBoardDetailsByTrnId.getAadharName().isEmpty())){
                    UpdateEmployeeRequestDTO.CustomDataDTO aadharName = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    aadharName.setCustomDataTypeName("Name as Per Aadhar");
                    aadharName.setText(individualOnBoardDetailsByTrnId.getAadharName());
                    addCustomeList.add(aadharName);

                } if(individualOnBoardDetailsByTrnId.getRelativeName()!=null && !individualOnBoardDetailsByTrnId.getRelativeName().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO relativeName = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    relativeName.setCustomDataTypeName("Father or Husband Name");
                    relativeName.setText(individualOnBoardDetailsByTrnId.getRelativeName());
                    addCustomeList.add(relativeName);

                } if(individualOnBoardDetailsByTrnId.getAddress()!=null && !individualOnBoardDetailsByTrnId.getAddress().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentAddress = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentAddress.setCustomDataTypeName("Permanent Address");
                    permanentAddress.setText(individualOnBoardDetailsByTrnId.getRelativeName());
                    addCustomeList.add(permanentAddress);

                } if(individualOnBoardDetailsByTrnId.getPermanentDistrict()!=null && !individualOnBoardDetailsByTrnId.getPermanentDistrict().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Permanent District");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentDistrict());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getPermanentState()!=null && !individualOnBoardDetailsByTrnId.getPermanentState().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Permanent State");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentState());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getPermanentPincode()!=null && !individualOnBoardDetailsByTrnId.getPermanentPincode().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Permanent Pin code");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentPincode());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getIdMark()!=null && !individualOnBoardDetailsByTrnId.getIdMark().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("ID Mark");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getIdMark());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getUanNumber()!=null && !individualOnBoardDetailsByTrnId.getUanNumber().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("UAN Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getUanNumber());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getMaritalStatus()!=null && !individualOnBoardDetailsByTrnId.getMaritalStatus().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Marital Status");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getMaritalStatus());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getTechnical()!=null && !individualOnBoardDetailsByTrnId.getTechnical().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Technical Qualification");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getTechnical());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getAcademic()!=null && !individualOnBoardDetailsByTrnId.getAcademic().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Academic Qualification");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getAcademic());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getShoeSize()!=null && !individualOnBoardDetailsByTrnId.getShoeSize().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Shoe Size");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getShoeSize());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getBloodGroup()!=null && !individualOnBoardDetailsByTrnId.getBloodGroup().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Blood Group");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getBloodGroup());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getWorkmenType()!=null && !individualOnBoardDetailsByTrnId.getWorkmenType().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Workmen Type");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getWorkmenType());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getNatureOfJob()!=null && !individualOnBoardDetailsByTrnId.getNatureOfJob().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Nature Of Job");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getNatureOfJob());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getPanNumber()!=null && !individualOnBoardDetailsByTrnId.getPanNumber().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("PAN Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPanNumber());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getPfNumber()!=null && !individualOnBoardDetailsByTrnId.getPfNumber().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("PF Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPfNumber());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getAccountNumber()!=null && !individualOnBoardDetailsByTrnId.getAccountNumber().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Account Number");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getAccountNumber());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getBankName()!=null && !individualOnBoardDetailsByTrnId.getBankName().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("Bank Name");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getBankName());
                    addCustomeList.add(permanentDistrict);

                } if(individualOnBoardDetailsByTrnId.getIfscCode()!=null && !individualOnBoardDetailsByTrnId.getIfscCode().isEmpty()){
                    UpdateEmployeeRequestDTO.CustomDataDTO permanentDistrict = new UpdateEmployeeRequestDTO.CustomDataDTO();
                    permanentDistrict.setCustomDataTypeName("IFSC Code");
                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getIfscCode());
                    addCustomeList.add(permanentDistrict);

                }
                personInfo.setCustomDataList(addCustomeList);

                // Authentication types
                UpdateEmployeeRequestDTO.PersonAuthenticationType auth = new UpdateEmployeeRequestDTO.PersonAuthenticationType();
                auth.setActiveFlag(true);
                auth.setAuthenticationTypeName("Basic");
                personInfo.setPersonAuthenticationTypes(Arrays.asList(auth));

                // License types
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
                licenseScheduling.setLicenseTypeName("Scheduling");

                personInfo.setPersonLicenseTypes(Arrays.asList(
                        licenseEmployee,
                        licenseAbsence,
                        licensehourlyTimekeeping,
                        licenseScheduling
                ));


                // User account status
                UpdateEmployeeRequestDTO.UserAccountStatus userStatus = new UpdateEmployeeRequestDTO.UserAccountStatus();
                userStatus.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
                userStatus.setExpirationDate("3000-01-01");
                userStatus.setUserAccountStatusName(individualOnBoardDetailsByTrnId.getUserAccountStatus());
                personInfo.setUserAccountStatusList(Arrays.asList(userStatus));

                dto.setPersonInformation(personInfo);

                // --- JobAssignment ---
                UpdateEmployeeRequestDTO.JobAssignment job = new UpdateEmployeeRequestDTO.JobAssignment();

                UpdateEmployeeRequestDTO.BaseWageRate wage = new UpdateEmployeeRequestDTO.BaseWageRate();
                wage.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
                wage.setExpirationDate("3000-01-01");
                // Example: convert monthly basic to hourly rate
//            if (gatePass.getBasic() != null) {
//                double hourlyRate = gatePass.getBasic().doubleValue() / 173; // approx monthly to hourly
//                wage.setHourlyRate(hourlyRate);
//            } else {
                wage.setHourlyRate(20.15);
//            }
                job.setBaseWageRates(Arrays.asList(wage));
                UpdateEmployeeRequestDTO.JobAssignmentDetails jobDetails = new UpdateEmployeeRequestDTO.JobAssignmentDetails();
                jobDetails.setPayRuleName(individualOnBoardDetailsByTrnId.getPayRuleName());
                jobDetails.setSupervisorName(individualOnBoardDetailsByTrnId.getSupervisorName()); // EIC → supervisor
                jobDetails.setSupervisorPersonNumber(individualOnBoardDetailsByTrnId.getSupervisorPersonNumber());  // hardcoded, replace with mapping
                jobDetails.setTimeZoneName("(GMT +05:30) Calcutta");
                job.setJobAssignmentDetails(jobDetails);
                UpdateEmployeeRequestDTO.PrimaryLaborAccount labor = new UpdateEmployeeRequestDTO.PrimaryLaborAccount();
                labor.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
                labor.setExpirationDate("3000-01-01");
//              labor.setOrganizationPath(individualOnBoardDetailsByTrnId.getCompany()+ "/" +individualOnBoardDetailsByTrnId.getLocation()+ "/" + individualOnBoardDetailsByTrnId.getPlantLocation() + "/" + individualOnBoardDetailsByTrnId.getDepartment() + "/" + individualOnBoardDetailsByTrnId.getSection() + "/" + individualOnBoardDetailsByTrnId.getSubSection() + "/" + individualOnBoardDetailsByTrnId.getContractorCode() + "/" +individualOnBoardDetailsByTrnId.getCategory());
                labor.setOrganizationPath("DOT1 Solutions Pvt Ltd/Banglore/Main Plant/IT/IT/General/Bravispach/Team Lead");
                job.setPrimaryLaborAccounts(Arrays.asList(labor));
                dto.setJobAssignment(job);
                // --- User ---
                UpdateEmployeeRequestDTO.User user = new UpdateEmployeeRequestDTO.User();
                UpdateEmployeeRequestDTO.UserAccount userAcc = new UpdateEmployeeRequestDTO.UserAccount();
                userAcc.setLogonProfileName(individualOnBoardDetailsByTrnId.getLogonProfileName());
                userAcc.setUserName(individualOnBoardDetailsByTrnId.getUserAccountName());
                userAcc.setUserPassword(individualOnBoardDetailsByTrnId.getUserPassword()); // default password, can be generated
                user.setUserAccount(userAcc);
                dto.setUser(user);

//                String employee = wfdEmployeeService.createEmployee(dto);





                if (dto != null) {
                    return dto;
                } else {
                    return null;
                }

            }else{
                return null;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }




    }



    public String updatePassEmpDtoDynamic(String GatePassId ){
        try {
            UpdateEmployeeRequestDTO employeeRequestDTO = gatePassUpdateEmpDto(GatePassId);



           if(employeeRequestDTO!=null){
                String employee = wfdEmployeeService.updateEmployee(employeeRequestDTO);



                if (employee != null) {
                    SkillProLevelDateDTO skillPRoLevelDate = getSkillPRoLevelDate(GatePassId);


                    String s = wfdEmployeeService.addPersonSkill(skillPRoLevelDate.getPersonNumber(), skillPRoLevelDate.getSkill(), skillPRoLevelDate.getProficiencyLevel(), skillPRoLevelDate.getEffectiveDate());


                    return employee+"*******************************************"+s;
                } else {
                    return "Issue in Json or API";
                }

            }else{
                return "Not fount transcation Id";
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




//    public EmployeeRequestDTO updatePassEmpDto(String GatePassId ){
//        try {
//
//            GatePassToOnBoard individualOnBoardDetailsByTrnId = gatePassToOnBoardService.getIndividualOnBoardDetailsByTrnId(GatePassId);
//            //Condition
//            if(true){
//                UpdateEmployeeRequestDTO dto = new UpdateEmployeeRequestDTO();
//
//                // --- PersonInformation ---
//                EmployeeRequestDTO.PersonInformation personInfo = new EmployeeRequestDTO.PersonInformation();
//
//                // AccessAssignment mapping (example logic, adjust as per your rules)
//                EmployeeRequestDTO.AccessAssignment access = new EmployeeRequestDTO.AccessAssignment();
//                access.setAccessProfileName(individualOnBoardDetailsByTrnId.getAccessProfileName());
//                access.setPreferenceProfileName(individualOnBoardDetailsByTrnId.getPreferenceProfileName());
//                access.setProfessionalPayCodeName(individualOnBoardDetailsByTrnId.getProfessionalPayCodeName());
//                access.setProfessionalWorkRuleName(individualOnBoardDetailsByTrnId.getProfessionalWorkRuleName());
//                access.setShiftCodeName(individualOnBoardDetailsByTrnId.getShiftCodeName());
//                personInfo.setAccessAssignment(access);
//
//                // Email address (if mobile/email available)// wants to add it into a config file
//                EmployeeRequestDTO.EmailAddress email = new EmployeeRequestDTO.EmailAddress();
//                email.setAddress(individualOnBoardDetailsByTrnId.getAddressEmail()); // if you have email in GatePassMain add here
//                email.setContactTypeName(individualOnBoardDetailsByTrnId.getContactTypeName());
//                email.setHasEmailNotificationDelivery(false);
//                personInfo.setEmailAddresses(Arrays.asList(email));
//
//                // Employment status
//                EmployeeRequestDTO.EmploymentStatus empStatus = new EmployeeRequestDTO.EmploymentStatus();
//                empStatus.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());  // joining date
//                empStatus.setEmploymentStatusName(individualOnBoardDetailsByTrnId.getEmploymentStatus());
//                empStatus.setExpirationDate("3000-01-01");
//                personInfo.setEmploymentStatusList(Arrays.asList(empStatus));
//
//                // Person details
//                EmployeeRequestDTO.Person person = new EmployeeRequestDTO.Person();
//                person.setBirthDate(individualOnBoardDetailsByTrnId.getBirthDate());
//                person.setFirstName(individualOnBoardDetailsByTrnId.getFirstName());
//                person.setLastName(individualOnBoardDetailsByTrnId.getLastName());
//                person.setFullName(individualOnBoardDetailsByTrnId.getLastName()+ ", " + individualOnBoardDetailsByTrnId.getFirstName());
//                person.setHireDate(individualOnBoardDetailsByTrnId.getHireDate());
//                person.setPersonNumber(individualOnBoardDetailsByTrnId.getGatePassId());
//                person.setShortName(individualOnBoardDetailsByTrnId.getFirstName() + (individualOnBoardDetailsByTrnId.getLastName() != null ? individualOnBoardDetailsByTrnId.getLastName().charAt(0) : ""));
//                personInfo.setPerson(person);
//
//                //CustomData details
////                ArrayList<>
////***********************Additional information********************
//                ArrayList<EmployeeRequestDTO.CustomDataDTO> addCustomeList = new ArrayList<>();
//
//                if(individualOnBoardDetailsByTrnId.getGender()!=null && !individualOnBoardDetailsByTrnId.getGender().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO gender = new EmployeeRequestDTO.CustomDataDTO();
//                    gender.setCustomDataTypeName("Gendar");
//                    gender.setText(individualOnBoardDetailsByTrnId.getGender());
//                    addCustomeList.add(gender);
//                }  if ((individualOnBoardDetailsByTrnId.getAadharNumber()!=null && !individualOnBoardDetailsByTrnId.getAadharNumber().isEmpty()))
//                {
//                    EmployeeRequestDTO.CustomDataDTO aadharNumber = new EmployeeRequestDTO.CustomDataDTO();
//                    aadharNumber.setCustomDataTypeName("Aadhar Number");
//                    aadharNumber.setText(individualOnBoardDetailsByTrnId.getAadharNumber());
//                    addCustomeList.add(aadharNumber);
//
//                } if ((individualOnBoardDetailsByTrnId.getAadharName()!=null && !individualOnBoardDetailsByTrnId.getAadharName().isEmpty())){
//                    EmployeeRequestDTO.CustomDataDTO aadharName = new EmployeeRequestDTO.CustomDataDTO();
//                    aadharName.setCustomDataTypeName("Name as Per Aadhar");
//                    aadharName.setText(individualOnBoardDetailsByTrnId.getAadharName());
//                    addCustomeList.add(aadharName);
//
//                } if(individualOnBoardDetailsByTrnId.getRelativeName()!=null && !individualOnBoardDetailsByTrnId.getRelativeName().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO relativeName = new EmployeeRequestDTO.CustomDataDTO();
//                    relativeName.setCustomDataTypeName("Father or Husband Name");
//                    relativeName.setText(individualOnBoardDetailsByTrnId.getRelativeName());
//                    addCustomeList.add(relativeName);
//
//                } if(individualOnBoardDetailsByTrnId.getAddress()!=null && !individualOnBoardDetailsByTrnId.getAddress().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO permanentAddress = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentAddress.setCustomDataTypeName("Permanent Address");
//                    permanentAddress.setText(individualOnBoardDetailsByTrnId.getRelativeName());
//                    addCustomeList.add(permanentAddress);
//
//                } if(individualOnBoardDetailsByTrnId.getPermanentDistrict()!=null && !individualOnBoardDetailsByTrnId.getPermanentDistrict().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Permanent District");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentDistrict());
//                    addCustomeList.add(permanentDistrict);
//
//                } if(individualOnBoardDetailsByTrnId.getPermanentState()!=null && !individualOnBoardDetailsByTrnId.getPermanentState().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Permanent State");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentState());
//                    addCustomeList.add(permanentDistrict);
//
//                } if(individualOnBoardDetailsByTrnId.getPermanentPincode()!=null && !individualOnBoardDetailsByTrnId.getPermanentPincode().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Permanent Pin code");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPermanentPincode());
//                    addCustomeList.add(permanentDistrict);
//
//                } if(individualOnBoardDetailsByTrnId.getIdMark()!=null && !individualOnBoardDetailsByTrnId.getIdMark().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("ID Mark");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getIdMark());
//                    addCustomeList.add(permanentDistrict);
//
//                } if(individualOnBoardDetailsByTrnId.getUanNumber()!=null && !individualOnBoardDetailsByTrnId.getUanNumber().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("UAN Number");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getUanNumber());
//                    addCustomeList.add(permanentDistrict);
//
//                } if(individualOnBoardDetailsByTrnId.getMaritalStatus()!=null && !individualOnBoardDetailsByTrnId.getMaritalStatus().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Marital Status");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getMaritalStatus());
//                    addCustomeList.add(permanentDistrict);
//
//                } if(individualOnBoardDetailsByTrnId.getTechnical()!=null && !individualOnBoardDetailsByTrnId.getTechnical().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Technical Qualification");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getTechnical());
//                    addCustomeList.add(permanentDistrict);
//
//                } if(individualOnBoardDetailsByTrnId.getAcademic()!=null && !individualOnBoardDetailsByTrnId.getAcademic().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Academic Qualification");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getAcademic());
//                    addCustomeList.add(permanentDistrict);
//
//                } if(individualOnBoardDetailsByTrnId.getShoeSize()!=null && !individualOnBoardDetailsByTrnId.getShoeSize().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Shoe Size");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getShoeSize());
//                    addCustomeList.add(permanentDistrict);
//
//                } if(individualOnBoardDetailsByTrnId.getBloodGroup()!=null && !individualOnBoardDetailsByTrnId.getBloodGroup().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Blood Group");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getBloodGroup());
//                    addCustomeList.add(permanentDistrict);
//
//                } if(individualOnBoardDetailsByTrnId.getWorkmenType()!=null && !individualOnBoardDetailsByTrnId.getWorkmenType().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Workmen Type");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getWorkmenType());
//                    addCustomeList.add(permanentDistrict);
//
//                } if(individualOnBoardDetailsByTrnId.getNatureOfJob()!=null && !individualOnBoardDetailsByTrnId.getNatureOfJob().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Nature Of Job");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getNatureOfJob());
//                    addCustomeList.add(permanentDistrict);
//
//                } if(individualOnBoardDetailsByTrnId.getPanNumber()!=null && !individualOnBoardDetailsByTrnId.getPanNumber().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("PAN Number");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPanNumber());
//                    addCustomeList.add(permanentDistrict);
//
//                } if(individualOnBoardDetailsByTrnId.getPfNumber()!=null && !individualOnBoardDetailsByTrnId.getPfNumber().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("PF Number");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getPfNumber());
//                    addCustomeList.add(permanentDistrict);
//
//                } if(individualOnBoardDetailsByTrnId.getAccountNumber()!=null && !individualOnBoardDetailsByTrnId.getAccountNumber().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Account Number");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getAccountNumber());
//                    addCustomeList.add(permanentDistrict);
//
//                } if(individualOnBoardDetailsByTrnId.getBankName()!=null && !individualOnBoardDetailsByTrnId.getBankName().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("Bank Name");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getBankName());
//                    addCustomeList.add(permanentDistrict);
//
//                } if(individualOnBoardDetailsByTrnId.getIfscCode()!=null && !individualOnBoardDetailsByTrnId.getIfscCode().isEmpty()){
//                    EmployeeRequestDTO.CustomDataDTO permanentDistrict = new EmployeeRequestDTO.CustomDataDTO();
//                    permanentDistrict.setCustomDataTypeName("IFSC Code");
//                    permanentDistrict.setText(individualOnBoardDetailsByTrnId.getIfscCode());
//                    addCustomeList.add(permanentDistrict);
//
//                }
//                personInfo.setCustomDataList(addCustomeList);
//
//                // Authentication types
//                EmployeeRequestDTO.PersonAuthenticationType auth = new EmployeeRequestDTO.PersonAuthenticationType();
//                auth.setActiveFlag(true);
//                auth.setAuthenticationTypeName("Basic");
//                personInfo.setPersonAuthenticationTypes(Arrays.asList(auth));
//
//                // License types
//                EmployeeRequestDTO.PersonLicenseType licenseEmployee = new EmployeeRequestDTO.PersonLicenseType();
//                licenseEmployee.setActiveFlag(true);
//                licenseEmployee.setLicenseTypeName("Employee");
//
//                EmployeeRequestDTO.PersonLicenseType licenseAbsence = new EmployeeRequestDTO.PersonLicenseType();
//                licenseAbsence.setActiveFlag(true);
//                licenseAbsence.setLicenseTypeName("Absence");
//
//                EmployeeRequestDTO.PersonLicenseType licensehourlyTimekeeping = new EmployeeRequestDTO.PersonLicenseType();
//                licensehourlyTimekeeping.setActiveFlag(true);
//                licensehourlyTimekeeping.setLicenseTypeName("Hourly Timekeeping");
//
//
//                EmployeeRequestDTO.PersonLicenseType licenseScheduling = new EmployeeRequestDTO.PersonLicenseType();
//                licenseScheduling.setActiveFlag(true);
//                licenseScheduling.setLicenseTypeName("Scheduling");
//
//                personInfo.setPersonLicenseTypes(Arrays.asList(
//                        licenseEmployee,
//                        licenseAbsence,
//                        licensehourlyTimekeeping,
//                        licenseScheduling
//                ));
//
//
//                // User account status
//                EmployeeRequestDTO.UserAccountStatus userStatus = new EmployeeRequestDTO.UserAccountStatus();
//                userStatus.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
//                userStatus.setExpirationDate("3000-01-01");
//                userStatus.setUserAccountStatusName(individualOnBoardDetailsByTrnId.getUserAccountStatus());
//                personInfo.setUserAccountStatusList(Arrays.asList(userStatus));
//
//                dto.setPersonInformation(personInfo);
//
//                // --- JobAssignment ---
//                EmployeeRequestDTO.JobAssignment job = new EmployeeRequestDTO.JobAssignment();
//
//                EmployeeRequestDTO.BaseWageRate wage = new EmployeeRequestDTO.BaseWageRate();
//                wage.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
//                wage.setExpirationDate("3000-01-01");
//                // Example: convert monthly basic to hourly rate
////            if (gatePass.getBasic() != null) {
////                double hourlyRate = gatePass.getBasic().doubleValue() / 173; // approx monthly to hourly
////                wage.setHourlyRate(hourlyRate);
////            } else {
//                wage.setHourlyRate(20.15);
////            }
//                job.setBaseWageRates(Arrays.asList(wage));
//                EmployeeRequestDTO.JobAssignmentDetails jobDetails = new EmployeeRequestDTO.JobAssignmentDetails();
//                jobDetails.setPayRuleName(individualOnBoardDetailsByTrnId.getPayRuleName());
//                jobDetails.setSupervisorName(individualOnBoardDetailsByTrnId.getSupervisorName()); // EIC → supervisor
//                jobDetails.setSupervisorPersonNumber(individualOnBoardDetailsByTrnId.getSupervisorPersonNumber());  // hardcoded, replace with mapping
//                jobDetails.setTimeZoneName("(GMT +05:30) Calcutta");
//                job.setJobAssignmentDetails(jobDetails);
//                EmployeeRequestDTO.PrimaryLaborAccount labor = new EmployeeRequestDTO.PrimaryLaborAccount();
//                labor.setEffectiveDate(individualOnBoardDetailsByTrnId.getHireDate());
//                labor.setExpirationDate("3000-01-01");
////              labor.setOrganizationPath(individualOnBoardDetailsByTrnId.getCompany()+ "/" +individualOnBoardDetailsByTrnId.getLocation()+ "/" + individualOnBoardDetailsByTrnId.getPlantLocation() + "/" + individualOnBoardDetailsByTrnId.getDepartment() + "/" + individualOnBoardDetailsByTrnId.getSection() + "/" + individualOnBoardDetailsByTrnId.getSubSection() + "/" + individualOnBoardDetailsByTrnId.getContractorCode() + "/" +individualOnBoardDetailsByTrnId.getCategory());
//                labor.setOrganizationPath("DOT1 Solutions Pvt Ltd/Banglore/Main Plant/IT/IT/General/Bravispach/Team Lead");
//                job.setPrimaryLaborAccounts(Arrays.asList(labor));
//                dto.setJobAssignment(job);
//                // --- User ---
//                EmployeeRequestDTO.User user = new EmployeeRequestDTO.User();
//                EmployeeRequestDTO.UserAccount userAcc = new EmployeeRequestDTO.UserAccount();
//                userAcc.setLogonProfileName(individualOnBoardDetailsByTrnId.getLogonProfileName());
//                userAcc.setUserName(individualOnBoardDetailsByTrnId.getUserAccountName());
//                userAcc.setUserPassword(individualOnBoardDetailsByTrnId.getUserPassword()); // default password, can be generated
//                user.setUserAccount(userAcc);
//                dto.setUser(user);
//
////                String employee = wfdEmployeeService.createEmployee(dto);
//
//
//                if (dto != null) {
//                    return dto;
//                } else {
//                    return null;
//                }
//
//            }else{
//                return null;
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//
//
//
//    }
//

    public  EmployeeRequestDTO mapFromGatePass(GatePassMain gatePass) {
        EmployeeRequestDTO dto = new EmployeeRequestDTO();

        // --- PersonInformation ---
        EmployeeRequestDTO.PersonInformation personInfo = new EmployeeRequestDTO.PersonInformation();

        // AccessAssignment mapping (example logic, adjust as per your rules)
        EmployeeRequestDTO.AccessAssignment access = new EmployeeRequestDTO.AccessAssignment();
        access.setAccessProfileName("Employee FAP");
        access.setPreferenceProfileName("Employee");
        access.setProfessionalPayCodeName("Empty Profile");
        access.setProfessionalWorkRuleName("Empty Profile");
        access.setShiftCodeName("Empty Profile");
        personInfo.setAccessAssignment(access);

        // Email address (if mobile/email available)
        EmployeeRequestDTO.EmailAddress email = new EmployeeRequestDTO.EmailAddress();
        email.setAddress(""); // if you have email in GatePassMain add here
        email.setContactTypeName("Work");
        email.setHasEmailNotificationDelivery(false);
        personInfo.setEmailAddresses(Arrays.asList(email));

        // Employment status
        EmployeeRequestDTO.EmploymentStatus empStatus = new EmployeeRequestDTO.EmploymentStatus();
        empStatus.setEffectiveDate(gatePass.getDoj());  // joining date
        empStatus.setEmploymentStatusName("Active");
        empStatus.setExpirationDate("3000-01-01");
        personInfo.setEmploymentStatusList(Arrays.asList(empStatus));

        // Person details
        EmployeeRequestDTO.Person person = new EmployeeRequestDTO.Person();
        person.setBirthDate(gatePass.getDateOfBirth());
        person.setFirstName(gatePass.getFirstName());
        person.setLastName(gatePass.getLastName());
        person.setFullName(gatePass.getLastName() + ", " + gatePass.getFirstName());
        person.setHireDate(gatePass.getDoj());
        person.setPersonNumber(gatePass.getGatePassId() != null ? gatePass.getGatePassId() : gatePass.getTransactionId());
        person.setShortName(gatePass.getFirstName() + (gatePass.getLastName() != null ? gatePass.getLastName().charAt(0) : ""));
        personInfo.setPerson(person);

        // Authentication types
        EmployeeRequestDTO.PersonAuthenticationType auth = new EmployeeRequestDTO.PersonAuthenticationType();
        auth.setActiveFlag(true);
        auth.setAuthenticationTypeName("Basic");
        personInfo.setPersonAuthenticationTypes(Arrays.asList(auth));

        // License types
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

        personInfo.setPersonLicenseTypes(Arrays.asList(
                licenseEmployee,
                licenseAbsence,
                licensehourlyTimekeeping,
                licenseScheduling
        ));

        // User account status
        EmployeeRequestDTO.UserAccountStatus userStatus = new EmployeeRequestDTO.UserAccountStatus();
        userStatus.setEffectiveDate(gatePass.getDoj());
        userStatus.setExpirationDate("3000-01-01");
        userStatus.setUserAccountStatusName("Active");
        personInfo.setUserAccountStatusList(Arrays.asList(userStatus));

        dto.setPersonInformation(personInfo);

        // --- JobAssignment ---
        EmployeeRequestDTO.JobAssignment job = new EmployeeRequestDTO.JobAssignment();

        EmployeeRequestDTO.BaseWageRate wage = new EmployeeRequestDTO.BaseWageRate();
        wage.setEffectiveDate(gatePass.getDoj());
        wage.setExpirationDate("3000-01-01");
        // Example: convert monthly basic to hourly rate
        if (gatePass.getBasic() != null) {
            double hourlyRate = gatePass.getBasic().doubleValue() / 173; // approx monthly to hourly
            wage.setHourlyRate(hourlyRate);
        } else {
            wage.setHourlyRate(0.0);
        }
        job.setBaseWageRates(Arrays.asList(wage));

        EmployeeRequestDTO.JobAssignmentDetails jobDetails = new EmployeeRequestDTO.JobAssignmentDetails();
        jobDetails.setPayRuleName(gatePass.getWageCategory() != null ? gatePass.getWageCategory() : "Default Rule");
        jobDetails.setSupervisorName(gatePass.getEic()); // EIC → supervisor
        jobDetails.setSupervisorPersonNumber("BR0001");  // hardcoded, replace with mapping
        jobDetails.setTimeZoneName("(GMT +05:30) Calcutta");
        job.setJobAssignmentDetails(jobDetails);

        EmployeeRequestDTO.PrimaryLaborAccount labor = new EmployeeRequestDTO.PrimaryLaborAccount();
        labor.setEffectiveDate(gatePass.getDoj());
        labor.setExpirationDate("3000-01-01");
        labor.setOrganizationPath("DOT1 Solutions Pvt Ltd/Banglore/Main Plant/IT/IT/General/Bravispach/Team Lead");
        job.setPrimaryLaborAccounts(Arrays.asList(labor));

        dto.setJobAssignment(job);

        // --- User ---
        EmployeeRequestDTO.User user = new EmployeeRequestDTO.User();
        EmployeeRequestDTO.UserAccount userAcc = new EmployeeRequestDTO.UserAccount();
        userAcc.setLogonProfileName("Default");
        userAcc.setUserName(gatePass.getUserId() != null ? gatePass.getUserId() : gatePass.getFirstName().toLowerCase() + "." + gatePass.getLastName().toLowerCase());
        userAcc.setUserPassword("Kronos@12321"); // default password, can be generated
        user.setUserAccount(userAcc);
        dto.setUser(user);

        return dto;
    }


    public String postSkillTowfd(Integer gmId){
        try{
            PostSkillWfd skills = gatePassToOnBoardService.createSkills(gmId);

            if(skills!=null){

                String skillsInWFD = wfdEmployeeService.createSkillsInWFD(skills);
                return skillsInWFD;


            }else{
                return "Skill is not present";
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
