package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.dto.ApproveRejectContRenewDto;
import com.wfd.dot1.cwfm.dto.WorkOrderInfoDTO;
import com.wfd.dot1.cwfm.enums.UserRole;
import com.wfd.dot1.cwfm.pojo.CMSContractorRegistrationLLWC;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
import com.wfd.dot1.cwfm.pojo.ContractorRegistrationPolicy;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.ContractorLLWCService;
import com.wfd.dot1.cwfm.service.ContractorService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;
import com.wfd.dot1.cwfm.service.WorkmenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/renewal")
public class ContractorRenewalController {

	@Autowired
	PrincipalEmployerService peService;

	@Autowired
	ContractorService contrService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	WorkmenService workmenService;
	  @GetMapping("/listingFilter")
	    public String getRenewalList(HttpServletRequest request, HttpServletResponse response) {

	    	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
			MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

			
			List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
	    	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
	    			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
	    	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
	    	List<PersonOrgLevel> contractors = groupedByLevelDef.getOrDefault("Contractor", new ArrayList<>());
	    	
	    	List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
	        CMSRoleRights rr =new CMSRoleRights();
	        rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/renewal/listingFilter");
	   	    listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
	   	    request.setAttribute("UserPermission", rr);
	    	request.setAttribute("principalEmployers", peList);
	    	  request.setAttribute("Contr", contractors);
	    		return "contRenewal/list";

	    	
	    }

//	@GetMapping("/list")
//	public String getContractorRenewalList(HttpServletRequest request, HttpServletResponse response) {
//
//		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
//		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
//
//		List<ContractorRegistration> listDto = contrService.getContractorRenewalList(String.valueOf(user.getUserId()));
//		List<PrincipalEmployer> peList =new ArrayList<PrincipalEmployer>();
//		 CMSRoleRights rr =new CMSRoleRights();
//       if(user!=null) {
//       if(user.getRoleName().equals("System Admin")) {
//       	 rr.setAddRights(1);  // Changed getInt() to getBoolean()
//		        rr.setEditRights(1);
//		        rr.setDeleteRights(1);
//		        rr.setImportRights(1);
//		        rr.setExportRights(1);
//		        rr.setViewRights(1);
//       	peList = peService.getAllPrincipalEmployerForAdmin();
//       }else {
//       	rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/renewal/list");
//       	peList = peService.getAllPrincipalEmployer(user.getUserAccount());
//       }
//       }
//		request.setAttribute("contractorlist", listDto);
//		request.setAttribute("UserPermission", rr);
//		return "contRenewal/list";
//
//	}
	
	 @PostMapping("/list")
	    @ResponseBody
	    public ResponseEntity<List<ContractorRegistration>> list(
	    		@RequestParam(value = "principalEmployerId", required = false) String principalEmployerId,
	    		@RequestParam(value = "deptId", required = false) String deptId,//deptId is contractorId not department
	    		HttpServletRequest request,HttpServletResponse response) {
	    	
	    	try {
				HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
				MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
				List<ContractorRegistration> listDto = new ArrayList<ContractorRegistration>();
				if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
					listDto=contrService.getContRenewList(String.valueOf(user.getUserId()),deptId,principalEmployerId);
				}else {	
				
					listDto=contrService.getContRenewListForApprovers(principalEmployerId,deptId,user);
	    		}	
					if (listDto.isEmpty()) {
						return new ResponseEntity<>(HttpStatus.NO_CONTENT);
					}
				
				return new ResponseEntity<>(listDto, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
	    }
	
	@GetMapping("/create")
	public String createRewnal(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
		List<PrincipalEmployer> peList = workmenService.getAllPrincipalEmployer(user.getUserAccount());
		request.setAttribute("PrincipalEmployer", peList);
		
		String contractorRegId = contrService.generateContractorRegistrationId();
		request.setAttribute("contractorregId", contractorRegId);
		List<CmsGeneralMaster> gmList2 = workmenService.getAllGeneralMaster();

		// Grouping the CmsGeneralMaster objects by gmType
		Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList2.stream()
		        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

		// Define the types and their corresponding request attribute names
		Map<String, String> attributeMapping = Map.of(
		        "DOCUMENT TYPE", "DocumentType"
		        
		);

		// Iterate over the attribute mappings and set the request attributes dynamically
		attributeMapping.forEach((type, attributeName) -> {
		    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
		    request.setAttribute(attributeName, gmList1);
		});
		
		return "contRenewal/create";
	}
	
	@GetMapping("/getAllContractorDetailForRenewal")
	@ResponseBody 
		public ResponseEntity<ContractorRegistration> getAllContractorDetailForRenewal(
	            @RequestParam("unitId") String unitId, @RequestParam("contractorId") String contractorId, 
	            HttpServletRequest request,HttpServletResponse response) {
	        try {
	        	ContractorRegistration contractors=new ContractorRegistration();
	    			
	    				contractors = contrService.getAllContractorDetailForRenewal(unitId,contractorId);
	    			
	    				if (contractors == null || contractors.getContractorId() == null) {
	    		            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    		        }
	            return new ResponseEntity<>(contractors, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	 
	 @GetMapping("/getAllAvailableWoAndLicense")
		@ResponseBody 
			public ResponseEntity<ContractorRegistration> getAllAvailableWoAndLicense(
		            @RequestParam("unitId") String unitId, @RequestParam("contractorId") String contractorId, @RequestParam("vendorCode") String contractorCode, @RequestParam("contractorregId") String regId, 
		            HttpServletRequest request,HttpServletResponse response) {
		        try {
		        	ContractorRegistration contractors=new ContractorRegistration();
		    			
		    				contractors = contrService.getAllAvailableWoAndLicense(unitId,contractorId,contractorCode,regId);
		    			
		    				if (contractors == null || contractors.getContractorId() == null) {
		    					return new ResponseEntity<>(new ContractorRegistration(), HttpStatus.OK);
		    		        }
		            return new ResponseEntity<>(contractors, HttpStatus.OK);
		        } catch (Exception e) {
		            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
		 
	 
	 @PostMapping("/save")
	    @ResponseBody
	    public ResponseEntity<String> saveRenewal(
	            @RequestParam("jsonData") String jsonData,
	            @RequestParam("aadharFile") MultipartFile aadharFile,
	            @RequestParam("panFile") MultipartFile panFile,
	            @RequestParam("pfFile") MultipartFile pfFile,
	            @RequestParam("renewalAttachments") List<MultipartFile> renewalAttachments,
	            HttpServletRequest request) {

		 HttpSession session = request.getSession(false);
         MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
	        String username = String.valueOf(user.getUserId());

	        try {
	        	contrService.saveRenewal(jsonData, aadharFile, panFile,pfFile, renewalAttachments, username);
	            return ResponseEntity.ok("Success");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                                 .body("Failed to save renewal: " + e.getMessage());
	        }
	    }
	 @Autowired
	    private ContractorLLWCService contractorLLWCService;

	    @PostMapping("/saveWorkOrderInfo")
	    @ResponseBody
	    public ResponseEntity<String> saveWorkOrderInfo(@RequestBody WorkOrderInfoDTO workOrderInfo,
	                                                    HttpSession session) {
	        try {
	           MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
		        String createdBy = String.valueOf(user.getUserId());
	            if (createdBy == null) createdBy = "SYSTEM";

	            contractorLLWCService.saveLLWCRecords(workOrderInfo, createdBy);
	            return ResponseEntity.ok("Work order info saved.");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                                 .body("Failed to save work order info.");
	        }
	    }
	    
	    @GetMapping("/contRenewList")
		public String getContractorRenewList(HttpServletRequest request, HttpServletResponse response) {

			HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
			MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

			List<ContractorRegistration> listDto = contrService.getContractorRenewList(String.valueOf(user.getUserId()));
			List<PrincipalEmployer> peList =new ArrayList<PrincipalEmployer>();
			 CMSRoleRights rr =new CMSRoleRights();
	       if(user!=null) {
	       if(user.getRoleName().equals("System Admin")) {
	       	 rr.setAddRights(1);  // Changed getInt() to getBoolean()
			        rr.setEditRights(1);
			        rr.setDeleteRights(1);
			        rr.setImportRights(1);
			        rr.setExportRights(1);
			        rr.setViewRights(1);
	       	peList = peService.getAllPrincipalEmployerForAdmin();
	       }else {
	       	rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/renewal/contRenewList");
	       	peList = peService.getAllPrincipalEmployer(user.getUserAccount());
	       }
	       }
			request.setAttribute("contractorlist", listDto);
			request.setAttribute("UserPermission", rr);
			return "contRenewal/list";

		}
	    
	   

	    @GetMapping("/view/{contractorRegId}")
	    public String viewContractorDetails(@PathVariable("contractorRegId") String contractorRegId,
	                                        HttpServletRequest request) {
	        try {
	        	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
				MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

	            ContractorRegistration contractor = contrService.getContractorRegistration(contractorRegId);
	            request.setAttribute("contractor", contractor);

	            List<ContractorRegistrationPolicy> policies = contrService.getPolicies(contractorRegId);
	            request.setAttribute("policies", policies);

	            List<CMSContractorRegistrationLLWC> llwcRecords = contrService.getLLWC(contractorRegId);
	            request.setAttribute("llwcRecords", llwcRecords);
	            
	            ApproveRejectContRenewDto comments = contrService.getContractorRenewComments(contractorRegId);
	            request.setAttribute("comments", comments);
	         // ✅ Pass versioned documents to JSP
	         //   List<Map<String, Object>> allVersionedDocs = contrService.getAllContractorVersionedDocuments(contractorRegId, user.getUserId(),contractor.getRequestType());
	          //  request.setAttribute("PreviousDocuments", allVersionedDocs);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return "contRenewal/view";  // ✅ JSP page that will display all details
	    }
	    @PostMapping("/approveRejectContRenew")
	    public ResponseEntity<String> approveRejectBill(@RequestBody ApproveRejectContRenewDto dto,HttpServletRequest request,HttpServletResponse response) {
	    	String result=null; 
	    	
	         try {
	        	 result = contrService.approveRejectContRenew(dto);
	         	if(null!=result) {
	         		return new ResponseEntity<>(result,HttpStatus.OK);
	         	}
	         	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	         } catch (Exception e) {
	             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                                  .body("Error saving data: " + e.getMessage());
	         } 
	    }
}
