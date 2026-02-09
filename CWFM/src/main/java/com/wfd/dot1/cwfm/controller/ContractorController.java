package com.wfd.dot1.cwfm.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
import com.wfd.dot1.cwfm.pojo.ContractorRegistrationPolicy;
import com.wfd.dot1.cwfm.pojo.ContractorRenewal;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Workorder;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.ContractorService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;
import com.wfd.dot1.cwfm.service.WorkmenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Base64;

@Controller
@RequestMapping("/contractor")
public class ContractorController {

	@Autowired
	WorkmenService workmenService;

	@Autowired
	PrincipalEmployerService peService;

	@Autowired
	ContractorService contrService;
	
	@Autowired
	CommonService commonService;

	
	
	@GetMapping("/list")
	public String getAllPrincipalEmployer(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
		
		//List<PrincipalEmployer> peList = workmenService.getAllPrincipalEmployer(user.getUserAccount());
		
		
		List<PrincipalEmployer> peList =new ArrayList<PrincipalEmployer>();
		 CMSRoleRights rr =new CMSRoleRights();
        if(user!=null) {
        if(user.getRoleName().equals("System Admin")) {
        	
        	peList = peService.getAllPrincipalEmployerForAdmin();
        	
        }else {
        
        	peList = peService.getAllPrincipalEmployer(user.getUserAccount());
        	
        }
        }
        rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractor/list");
        request.setAttribute("principalEmployers", peList);
        request.setAttribute("UserPermission", rr);
		return "contractors/list";
	}
//	@GetMapping("/list")
//	public String getAllPrincipalEmployer(@RequestParam(required = false) Long pageId, @RequestParam(required = false) Long selectedRoleId, HttpServletRequest request, HttpServletResponse response) {
//		HttpSession session = request.getSession(false); 
//		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
//		 if (pageId != null) {
//		        session.setAttribute("selectedPageId", pageId);
//		    } else {
//		        pageId = (Long) session.getAttribute("selectedPageId");
//		    }
//		    
//		   // Long selectedRoleId = (Long) session.getAttribute("selectedRoleId");
//		    System.out.println("pageId---"+pageId);
//		    System.out.println("selectedRoleId---"+selectedRoleId);
//		List<PrincipalEmployer> peList = workmenService.getAllPrincipalEmployer(String.valueOf(user.getUserId()));
//		request.setAttribute("principalEmployers", peList);
//
//		List<CMSRoleRights> userRights = commonService.getRoleRightsByRoleIdAndPageId(selectedRoleId, pageId); // 10029 = Contractor Page ID
//	    request.setAttribute("userRights", userRights);
//	    Enumeration<String> attributeNames = session.getAttributeNames();
//	    while (attributeNames.hasMoreElements()) {
//	        String attributeName = attributeNames.nextElement();
//	        System.out.println(attributeName + ": " + session.getAttribute(attributeName));
//	    }
//		return "contractors/list";
//	}


	@PostMapping("/getAllContractorsBasedOnPE")
	public ResponseEntity<List<Contractor>> searchResources(
			@RequestParam(value = "principalEmployerId", required = false) String principalEmployerId,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
			MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
			List<Contractor> contractorList = new ArrayList<Contractor>();
			if(user!=null) {
			if("System Admin".equals(user.getRoleName())) {
				contractorList = workmenService.getAllContractorForAdmin(principalEmployerId);
			}else {
				contractorList = workmenService.getAllContractorBasedOnPE(principalEmployerId,
						user.getUserAccount());
			}
			}
			 

			if (contractorList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(contractorList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/view/{contractorId}")
	public String viewContractor(@PathVariable("contractorId") String contractorId,
			@RequestParam("principalEmployerId") String principalEmployerId, HttpServletRequest request,
			HttpServletResponse response) {

		// Use contractorId and principalEmployerId to fetch data as needed
		Contractor contractor = contrService.getContractorById(contractorId);
		request.setAttribute("contractor", contractor);
		PrincipalEmployer principalEmployer = peService.getIndividualPEDetailByUnitId(principalEmployerId);
		request.setAttribute("principalEmployer", principalEmployer);

		//Contractor contractorPEMM = contrService.getAllContractorDetailForReg(principalEmployerId,contractorId);
		//request.setAttribute("contractorPEMM", contractorPEMM);

		Contractor contractorPEMM = contrService.getAllContractorProfileDetailForReg(principalEmployerId,contractorId);
		request.setAttribute("contractorPEMM", contractorPEMM);
		
		List<CmsContractorWC> laborLicenses = contrService
				.getMappingsByContractorIdAndUnitIdAndLicenseType(contractorId, principalEmployerId, "LL");
		request.setAttribute("laborLicenses", laborLicenses);

		List<String> licenseTypes = Arrays.asList("WC", "ESIC");
		List<CmsContractorWC> contractorWCList = contrService
				.getMappingsByContractorIdAndUnitIdAndLicenseTypes(contractorId, principalEmployerId, licenseTypes);
		request.setAttribute("contractorWCList", contractorWCList);

		List<Workorder> workOrderList = contrService.getWorkOrdersByContractorIdAndUnitId(contractorId,
				principalEmployerId);
		request.setAttribute("workOrderList", workOrderList);

		return "contractors/view";
	}

	@GetMapping("/contReg")
	public String getContractorRegistration(HttpServletRequest request, HttpServletResponse response) {
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
		
		
		return "contractors/contractorRegistration";
	}

	 @GetMapping("/getAllContractors")
		public ResponseEntity<List<Contractor>> getAllContractors(
	            @RequestParam("unitId") String unitId, 
	            HttpServletRequest request,HttpServletResponse response) {
	        try {
	        	List<Contractor> contractors=new ArrayList<Contractor>();
	    			
	    				contractors = contrService.getAllContractorForReg(unitId);
	    			
	            if (contractors.isEmpty()) {
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	            }
	            return new ResponseEntity<>(contractors, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	 @GetMapping("/getAllWorkorders")
		public ResponseEntity<List<Workorder>> getAllWorkorders(
	            @RequestParam("unitId") String unitId, @RequestParam("contractorId") String contractorId,
	            HttpServletRequest request,HttpServletResponse response) {
	        try {
	        	List<Workorder> workorders = contrService.getAllWorkordersBasedOnPEAndContractor(unitId,contractorId);
	            if (workorders.isEmpty()) {
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	            }
	            return new ResponseEntity<>(workorders, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	 
	 @GetMapping("/getAllContractorDetail")
		public ResponseEntity<Contractor> getAllContractorDetail(
	            @RequestParam("unitId") String unitId, @RequestParam("contractorId") String contractorId, 
	            HttpServletRequest request,HttpServletResponse response) {
	        try {
	        	Contractor contractors=new Contractor();
	    			
	    				contractors = contrService.getAllContractorDetailForReg(unitId,contractorId);
	    			
	    				if (contractors == null || contractors.getContractorId() == null) {
	    		            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    		        }
	            return new ResponseEntity<>(contractors, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	 

	 private static final String ROOT_DIRECTORY = "D:/wfd_cwfm/contractor_docs/";

	 public String uploadDocuments( MultipartFile aadharFile,
			 String userId,
			 String contractorRegId) {

		 // Create directory path
		 String directoryPath =ROOT_DIRECTORY + userId + "/"+contractorRegId+"/";

		 try {
			 // Ensure the directory exists, if not create it
			 Path path = Paths.get(directoryPath);
			 if (!Files.exists(path)) {
				 Files.createDirectories(path);
			 }

			 // Save Aadhar PDF
			 if (!aadharFile.isEmpty()) {
				 String aadharFilePath = directoryPath +aadharFile.getOriginalFilename();
				 saveFile(aadharFile, aadharFilePath);
			 }

			 

			 // Return success message
			 return "success";

		 } catch (IOException e) {
			 e.printStackTrace();
			 return "failed";
		 }
	 }
	 private void saveFile(MultipartFile file, String path) throws IOException {
	        byte[] bytes = file.getBytes();
	        Path filePath = Paths.get(path);
	        Files.write(filePath, bytes);
	    }
	  
	 @PostMapping("/saveReg")
	 public ResponseEntity<String> saveReg(
	     @RequestParam("jsonData") String jsonData,
	     @RequestParam("policyAttachments") List<MultipartFile> attachments,
	     @RequestParam(value = "aadharFile", required = false) MultipartFile aadharFile,
         @RequestParam(value = "panFile", required = false) MultipartFile panFile,
         @RequestParam(value = "pfFile", required = false) MultipartFile pfFile,
	     HttpServletRequest request
	 ) {
	     try {
	         // Parse the incoming JSON string into your Java object
	         ObjectMapper mapper = new ObjectMapper();
	         ContractorRegistration contreg = mapper.readValue(jsonData, ContractorRegistration.class);
	         contreg.setAadharDoc(aadharFile.getOriginalFilename());
	         contreg.setPanDoc(panFile.getOriginalFilename());
	         contreg.setPfDoc(pfFile.getOriginalFilename());
	         HttpSession session = request.getSession(false);
	         MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
	         contreg.setCreatedBy(String.valueOf(user.getUserId()));

	         // Save master registration
	         String regId = contrService.saveReg(contreg); // should return contractorregId

	         if (regId != null) {
	        	 uploadDocuments(aadharFile,String.valueOf(user.getUserId()),regId);
	        	 uploadDocuments(panFile,String.valueOf(user.getUserId()),regId);
	        	 uploadDocuments(pfFile,String.valueOf(user.getUserId()),regId);
	             List<ContractorRegistrationPolicy> policies = contreg.getRegPolicy();

	             // Set regId and file details in each policy
	             for (int i = 0; i < policies.size(); i++) {
	                 ContractorRegistrationPolicy policy = policies.get(i);
	                 policy.setContractorRegId(regId);

	                 MultipartFile file = attachments.get(i);
	                 if (file != null && !file.isEmpty()) {
	                     policy.setFileName(file.getOriginalFilename());
	                     // Optionally: save file bytes or store to disk
	                     uploadDocuments(file,String.valueOf(user.getUserId()),regId);
	                 } else {
	                     policy.setFileName(null);
	                 }
	             }

	             contrService.savePolicies(policies, contreg);
	         }

	         return new ResponseEntity<>("contractors/contractorRegistrationList", HttpStatus.OK);
	     } catch (Exception e) {
	         e.printStackTrace();
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                 .body("Error saving data: " + e.getMessage());
	     }
	 }



	@GetMapping("/contRegList")
	public String getContractorRegistrationList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

		List<ContractorRegistration> listDto = contrService.getContractorRegistrationList(String.valueOf(user.getUserId()));
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
       	rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractor/contRegList");
       	peList = peService.getAllPrincipalEmployer(user.getUserAccount());
       }
       }
		request.setAttribute("contractorlist", listDto);
		request.setAttribute("UserPermission", rr);
		return "contractors/contractorRegistrationList";

	}

	@GetMapping("/contractorview/{contractorregId}")
	public String viewContractorDetails(@PathVariable String contractorregId, HttpServletRequest request,
			HttpServletResponse response) {
		ContractorRegistration contractor = contrService.viewContractorDetails(contractorregId);
		request.setAttribute("principalEmployer", contractor);
		List<ContractorRegistrationPolicy> list = contrService.viewContractorAddDetails(contractorregId);
		request.setAttribute("additionalDetails", list);
		return "contractors/contractorView"; // Return the JSP file name
	}

	
	@GetMapping("/role")
	public String getRole(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
		/*
		 * List<PrincipalEmployer> peList = workmenService.getRole(user.getUserId());
		 * request.setAttribute("PrincipalEmployer", peList);
		 */
		return "contractors/role";
	}
	@GetMapping("/roleList")
	public String getRoleList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

		List<MasterUser> listDto = contrService.getRoleList(String.valueOf(user.getUserId()));
		request.setAttribute("contractorlist", listDto);
		return "contractors/rolelist";

	}
	@PostMapping("/saveRole")
	public ResponseEntity<String> saveRole(@RequestBody MasterUser user, HttpServletRequest request,
			HttpServletResponse response) {
		String result = contrService.saveRole(user);
		return new ResponseEntity<>("contractors/rolelist", HttpStatus.OK);
	}
	
	
	@GetMapping("/viewFile/{encodedData}")
	public void viewContractorFile(@PathVariable("encodedData") String encodedData, HttpServletResponse response) {
	    try {
	        // 🔐 Decode Base64 string back to JSON
	        String decodedJson = new String(Base64.getUrlDecoder().decode(encodedData), StandardCharsets.UTF_8);
	        
	        // Example decoded: {"contregId":"123","userId":"EMP01","docName":"PFDocument.pdf"}
	        ObjectMapper mapper = new ObjectMapper();
	        Map<String, String> data = mapper.readValue(decodedJson, new TypeReference<>() {});
	        
	        String contregId = data.get("contregId");
	        String userId = data.get("userId");
	        String docName = data.get("docName");

	        String filePath = ROOT_DIRECTORY + userId + "/" + contregId + "/" + docName;
	        File file = new File(filePath);

	        if (!file.exists()) {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
	            return;
	        }

	        // Detect file type
	        String contentType = Files.probeContentType(file.toPath());
	        if (contentType == null) contentType = "application/octet-stream";

	        response.setContentType(contentType);
	        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
	        Files.copy(file.toPath(), response.getOutputStream());
	        response.getOutputStream().flush();

	    } catch (Exception e) {
	        e.printStackTrace();
	        try {
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error displaying file");
	        } catch (IOException ignored) {}
	    }
	}

}
