package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.ContractWorkmenExportDto;
import com.wfd.dot1.cwfm.pojo.ContractorComplianceDto;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.pojo.WageDetailsDto;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.ContractorService;
import com.wfd.dot1.cwfm.service.WorkmenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/export")
public class ExportController {
	
	@Autowired
	WorkmenService workmenService;
	@Autowired
	CommonService commonService;
	@Autowired
	ContractorService contrService;

	@GetMapping("/list")
    public String list(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
		List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMaster();

		// Grouping the CmsGeneralMaster objects by gmType
		Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
		        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

		// Define the types and their corresponding request attribute names
		Map<String, String> attributeMapping = Map.of(
		      				        "EXPORTOPTION","ExportOptions"
		      				      
		);

		// Iterate over the attribute mappings and set the request attributes dynamically
		attributeMapping.forEach((type, attributeName) -> {
		    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
		    request.setAttribute(attributeName, gmList1);
		});

	   	List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
    	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
    			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
    	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
    	request.setAttribute("PrincipalEmployer", peList);
    	return "export/list";
	 }
	
	
	@GetMapping("/fetchModuleData")
    @ResponseBody
    public Map<String, Object> fetchModuleData(@RequestParam String module, @RequestParam String unitId) {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> rows = new ArrayList<>();
        List<String> columns = new ArrayList<>();

        switch (module) {
            case "Contractor":
                List<ContractorRegistration> contractors = contrService.getContractorMasterExportData(unitId);
                columns = Arrays.asList("CODE", "CONTRACTORNAME", "ADDRESS", "MOBILENO", "MANAGERNM", "EMAILADDR");
                for (ContractorRegistration c : contractors) {
                    Map<String, String> row = new LinkedHashMap<>();
                    row.put("CODE", c.getVendorCode());
                    row.put("CONTRACTORNAME", c.getContractorName());
                    row.put("ADDRESS", c.getAddress());
                    row.put("MOBILENO", c.getMobile());
                    row.put("MANAGERNM", c.getManagerName());
                    row.put("EMAILADDR", c.getEmail());
                    rows.add(row);
                }
                break;

            case "Contractor Compliance":
              List<ContractorComplianceDto> compliance = contrService.getContractorComplianceExportData(unitId);
              columns = Arrays.asList("PE ID", "Contractor Code", "License Number", "License Validity StartDate", "License Validity End Date", "Contractor Coverage",
            		  "Total Strength of contractor","Nature of Work","Contract Start Date","Contract End Date","ESI Number","WC/ESI Valid From","WC/ESI Valid To","WC No",
            		  "WC Valid From","WC Valid To","PF Number","PF Apply Date","Labor welfare Fund","Professional tax","GST Number","ISMW Applicability","Effective Date");
              for (ContractorComplianceDto c : compliance) {
                  Map<String, String> row = new LinkedHashMap<>();
                  row.put("PE ID",c.getPeId());
                  row.put("Contractor Code",c.getContractorCode());
                  row.put("License Number", c.getLicenseNumber());
                  row.put("License Validity StartDate", c.getLicenseStartDate());
                  row.put("License Validity End Date", c.getLicenseEndDate());
                  row.put("Contractor Coverage", c.getContractorCoverge());
                  row.put("Total Strength of contractor", c.getTotalStrength());
                  row.put("Nature of Work", c.getNatureOfWork());
                  row.put("Contract Start Date", c.getContractStartDate());
                  row.put("Contract End Date", c.getContractEndDate());
                  row.put("ESI Number", c.getEsiNumber());
                  row.put("WC/ESI Valid From", c.getWcEsiValidFrom());
                  row.put("WC/ESI Valid To", c.getWcEsiValidTo());
                  row.put("WC No", c.getWcNo());
                  row.put("WC Valid From", c.getWcValidFrom());
                  row.put("WC Valid To", c.getWcValidTo());
                  row.put("PF Number", c.getPfNumber());
                  row.put("PF Apply Date", c.getPfApplyDate());
                  row.put("Labor welfare Fund", c.getLaborWelfare());
                  row.put("Professional tax", c.getProfessionalTax());
                  row.put("GST Number", c.getGst());
                  row.put("ISMW Applicability", c.getIsmwApplicable());
                  row.put("Effective Date", c.getEffectiveDate());
                
                  rows.add(row);
              }
                break;
            case "Contract Workmen":
                List<ContractWorkmenExportDto> workmen = workmenService.getContractWorkmenExportData(unitId);
                columns = Arrays.asList("Person Number(Employee id.)", "First Name", "Last Name", "Middle Initial/Name", "Short Name", "Badge Number (Punch Card)",
                		"Hire Date","Birth Date","Phone 1","Phone 2","Phone 3","Email","Address","City","State","Postal Code","Country","Employment Status","Employment Status Effective Date","Reports to Manager",
                		"Worker Type(Black Listed)","User Account Name","User Account Status","User Password","Company","Location","Plant Location","Department","Section","sub-section (Line)","Contractore Code",
                		"Home Business Structure Level 8","Home Business Structure Level 9","Category","Supervioser Id","Cost Center","Workorder","Home Labor Category Level 4","Home Labor Category Level 5","Home Labor Category Level 6","Home Job and Labor Category Effective Date",
                		"Gender","Aadhar Number","Name as Per Aadhar","Father or Husband Name","Permanent Address","Permanent District","Permanent State",
                		"Permanent Pincode","ID Mark","UAN Number","Marital Status","Technical Qualification","Academic Qualification","Shoe Size","Blood Group",
                		"Workmen Type","Nature Of Job/Work","ESIC IP Number","PAN Number","PF Number","Bank Account Number.","Bank Name","IFSC Code","Future Use 2","Future Use 3","Future Use 4",
                		"Future Use 5","Future Use 6","Future Use 7","Future Use 8","Future Use Date 1","Future Use Date 2","Future Use Date 3","Future Use Date 4","Future Use Date 5",
                		"Skill","Proficiency Level","Skill and Proficiency Level Effective Date","Certification 1","Certification 1 Start date","Certification 1 End date","Certification 2","Certification 2 Start date",
                		"Certification 2 End date","Certification 3","Certification 3 Start date","Certification 3 End date","Certification 4","Certification 4 Start date","Certification 4 End date"  	);
                
                
                for (ContractWorkmenExportDto c : workmen) {
                    Map<String, String> row = new LinkedHashMap<>();
                    row.put("Person Number(Employee id.)", c.getPersonNumber());
                    row.put("First Name", c.getFirstName());
                    row.put("Last Name", c.getLastName());
                    row.put("Middle Initial/Name", c.getMiddleName());
                    row.put("Short Name", c.getShortName());
                    row.put("Badge Number (Punch Card)", c.getBadgeNumber());
                    row.put("Hire Date", c.getHireDate());
                    row.put("Birth Date", c.getBirthDate());
                    row.put("Phone 1", c.getPhone1());
                    row.put("Phone 2", c.getPhone2());
                    row.put("Phone 3", c.getPhone3());
                    row.put("Email", c.getEmail());
                    row.put("Address", c.getAddress());
                    row.put("City", c.getCity());
                    row.put("State", c.getState());
                    row.put("Postal Code", c.getPostalCode());
                    row.put("Country", c.getCountry());
                    row.put("Employment Status", c.getEmploymentStatus());
                    row.put("Employment Status Effective Date", c.getEmploymentStatusEffectiveDate());
                    row.put("Reports to Manager", c.getReportsToManager());
                    row.put("Worker Type(Black Listed)", c.getWorkerType());
                    row.put("User Account Name", c.getUserAccountName());
                    row.put("User Account Status", c.getUserAccountStatus());
                    row.put("User Password", c.getUserPassword());
                    row.put("Company", c.getCompany());
                    row.put("Location", c.getLocation());
                    row.put("Plant Location", c.getPlantLocation());
                    row.put("Department", c.getDepartment());
                    row.put("Section", c.getSection());
                    row.put("sub-section (Line)", c.getSubSection());
                    row.put("Contractore Code", c.getContractorCOde());
                    row.put("Home Business Structure Level 8", c.getHome8());
                    row.put("Home Business Structure Level 9", c.getHome9());
                    row.put("Category", c.getCategory());
                    row.put("Supervioser Id", c.getSupervisorId());
                    row.put("Cost Center", c.getCostCenter());
                    row.put("Workorder", c.getWorkorder());
                    row.put("Home Labor Category Level 4", c.getHome4());
                    row.put("Home Labor Category Level 5", c.getHome5());
                    row.put("Home Labor Category Level 6", c.getHome6());
                    row.put("Home Job and Labor Category Effective Date", c.getCategoryEffectiveDate());
                    row.put("Gender", c.getGender());
                    row.put("Aadhar Number", c.getAadhar());
                    row.put("Name as Per Aadhar", c.getNameAsAadhar());
                    row.put("Father or Husband Name", c.getRelativeName());
                    row.put("Permanent Address", c.getPermanentAddress());
                    row.put("Permanent District", c.getPermanentDistrict());
                    row.put("Permanent State", c.getPermanentState());
                    row.put("Permanent Pincode", c.getPermanentpincode());
                    row.put("ID Mark", c.getIdMark());
                    row.put("UAN Number", c.getUanNumber());
                    row.put("Marital Status", c.getMaritalStatus());
                    row.put("Technical Qualification", c.getTechnical());
                    row.put("Academic Qualification", c.getAcademic());
                    row.put("Shoe Size", c.getShoeSize());
                    row.put("Blood Group", c.getBloodGroup());
                    row.put("Workmen Type", c.getWorkmenType());
                    row.put("Nature Of Job/Work", c.getNatureOfJob());
                    row.put("ESIC IP Number", c.getEsicNo());
                    row.put("PAN Number", c.getPanNumber());
                    row.put("PF Number", c.getPfNumber());
                    row.put("Bank Account Number.", c.getBankAccountNumber());
                    row.put("Bank Name", c.getBankName());
                    row.put("IFSC Code", c.getIfscCode());
                    row.put("Future Use 2", c.getFutureUse2());
                    row.put("Future Use 3", c.getFutureUse3());
                    row.put("Future Use 4", c.getFutureUse4());
                    row.put("Future Use 5", c.getFutureUse5());
                    row.put("Future Use 6", c.getFutureUse6());
                    row.put("Future Use 7", c.getFutureUse7());
                    row.put("Future Use 8", c.getFutureUse8());
                    row.put("Future Use Date 1", c.getFutureDate1());
                    row.put("Future Use Date 2", c.getFutureDate2());
                    row.put("Future Use Date 3", c.getFutureDate3());
                    row.put("Future Use Date 4", c.getFutureDate4());
                    row.put("Future Use Date 5", c.getFutureDate5());
                    row.put("Skill", c.getSkill());
                    row.put("Proficiency Level", c.getProLevel());
                    row.put("Skill and Proficiency Level Effective Date", c.getSkillDate());
                    row.put("Certification 1", c.getCert1());
                    row.put("Certification 1 Start date", c.getCert1StartDate());
                    row.put("Certification 1 End date", c.getCert1EndDate());
                    row.put("Certification 2", c.getCert2());
                    row.put("Certification 2 Start date", c.getCert2StartDate());
                    row.put("Certification 2 End date", c.getCert2EndDate());
                    row.put("Certification 3", c.getCert3());
                    row.put("Certification 3 Start date", c.getCert3StartDate());
                    row.put("Certification 3 End date", c.getCert3EndDate());
                    row.put("Certification 4", c.getCert4());
                    row.put("Certification 4 Start date", c.getCert4StartDate());
                    row.put("Certification 4 End date", c.getCert4EndDate());

                   
                    rows.add(row);
                }
                break;   
            case "Wage":
                List<WageDetailsDto> wage = workmenService.getWageMasterExportData(unitId);
                columns = Arrays.asList("PE Code", "Contractor Code", "Workmen ID", "Aadhar Number", "Basic", "DA","HRA","Conveyance Allowance","Special Allowance","Skill Allowance","Washing Allowance","Uniform Allowance","Hardship Allowance","Effective Date");
                for (WageDetailsDto w : wage) {
                    Map<String, String> row = new LinkedHashMap<>();
                    row.put("PE Code", w.getPeId() );
                    row.put("Contractor Code", w.getContractorCode());
                    row.put("Workmen ID", w.getWorkmenId());
                    row.put("Aadhar Number", w.getAadhar());
                    row.put("Basic", w.getBasic());
                    row.put("DA", w.getDa());
                    row.put("HRA", w.getHra());
                    row.put("Conveyance Allowance", w.getConveyenceAllow());
                    row.put("Special Allowance", w.getSpecialAllow());
                    row.put("Skill Allowance", w.getSkillAllow());
                    row.put("Washing Allowance", w.getWashingAllow());                    
                    row.put("Uniform Allowance", w.getUniformAllow());
                    row.put("Hardship Allowance", w.getHardshipAllow());
                    row.put("Effective Date", w.getEffectiveDate());
                    
                    rows.add(row);
                }
                break;

        }

        response.put("columns", columns);
        response.put("rows", rows);
        return response;
    }
}
