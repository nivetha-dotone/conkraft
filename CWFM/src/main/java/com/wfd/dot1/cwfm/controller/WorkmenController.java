package com.wfd.dot1.cwfm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.dao.WorkmenDao;
import com.wfd.dot1.cwfm.dto.ApproveRejectGatePassDto;
import com.wfd.dot1.cwfm.dto.ApproverStatusDTO;
import com.wfd.dot1.cwfm.dto.GatePassActionDto;
import com.wfd.dot1.cwfm.dto.GatePassListingDto;
import com.wfd.dot1.cwfm.dto.RequestPayload;
import com.wfd.dot1.cwfm.enums.GatePassStatus;
import com.wfd.dot1.cwfm.enums.GatePassType;
import com.wfd.dot1.cwfm.enums.UserRole;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.DeptMapping;
import com.wfd.dot1.cwfm.pojo.GatePassMain;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Trade;
import com.wfd.dot1.cwfm.pojo.Workorder;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;
import com.wfd.dot1.cwfm.service.WorkmenService;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
import com.wfd.dot1.cwfm.util.VerhoeffAlgorithm;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/contractworkmen")
public class WorkmenController {

	private static final Logger log = LoggerFactory.getLogger(WorkmenController.class.getName());
	
	@Autowired
	WorkmenService workmenService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	PrincipalEmployerService peService;
	
	@Autowired
	WorkmenDao workmenDao;
	
	@Autowired
	VerhoeffAlgorithm verhoeff;
	
	@GetMapping("/addQuickOB")
    public String createGatePass(HttpServletRequest request,HttpServletResponse response) {
	
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    	log.info("Entered into addQuickOBForm"+user.getUserId());
    	
    	String transactionId= workmenService.generateTransactionId();
    	request.setAttribute("transactionId", transactionId);
    	// workmenService.createDraftGatepass(transactionId,String.valueOf(user.getUserId()));
    	List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
    	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
    			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
    	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
    	//List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
    	//List<PersonOrgLevel> subdepartments = groupedByLevelDef.getOrDefault("Area", new ArrayList<>());
    	request.setAttribute("PrincipalEmployer", peList);
    	 // request.setAttribute("Dept", departments);
         // request.setAttribute("Subdept", subdepartments);
          
        //Skills
		//List<Skill> skillList = workmenService.getAllSkill();
		//request.setAttribute("Skills", skillList);
		//Eic 
		//List<MasterUser> eicList = workmenService.getAllEicManager(user.getUserAccount());
		//request.setAttribute("EIC", eicList);
		//Get All GeneralMaster
		List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMaster();

		// Grouping the CmsGeneralMaster objects by gmType
		Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
		        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

		// Define the types and their corresponding request attribute names
		Map<String, String> attributeMapping = Map.of(
		        "GENDER", "GenderOptions",
		        "BLOODGROUP", "BloodGroup",
		        "ACCESSAREA", "AccessArea",
		        "ACADEMICS", "Academics",
		        "WAGECATEGORY", "WageCategory",
		        "BONUSPAYOUT", "BonusPayout",
		        "ZONE", "Zone",
		        "WORKMENTYPE","WorkmenType"
		);

		// Iterate over the attribute mappings and set the request attributes dynamically
		attributeMapping.forEach((type, attributeName) -> {
		    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
		    request.setAttribute(attributeName, gmList1);
		});

        return "contractWorkmen/quickOBAdd";
    }
	
	 @GetMapping("/getAllEic")
		public ResponseEntity<List<MasterUser>> getAllEic(
	            @RequestParam("unitId") String unitId, 
	            @RequestParam("deptId") String deptId) {
	        log.info("Fetching contractors for unitId: " + unitId + " and userId: " + deptId);
	        try {
	        	List<MasterUser> eicList = workmenService.getAllEicManager(unitId,deptId);
	            if (eicList.isEmpty()) {
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	            }
	            return new ResponseEntity<>(eicList, HttpStatus.OK);
	        } catch (Exception e) {
	            log.error("Error fetching contractors: ", e);
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
    
    @GetMapping("/getAllContractors")
	public ResponseEntity<List<Contractor>> getAllContractors(
            @RequestParam("unitId") String unitId, 
            @RequestParam("userAccount") String userAccount,
            HttpServletRequest request,HttpServletResponse response) {
        log.info("Fetching contractors for unitId: " + unitId + " and userId: " + userAccount);
        try {
        	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
			MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
        	List<Contractor> contractors=new ArrayList<Contractor>();
        	if(user!=null) {
    			if("System Admin".equals(user.getRoleName())) {
    				contractors = workmenService.getAllContractorForAdmin(unitId);
    			}else {
    				contractors = workmenService.getAllContractorBasedOnPE(unitId,
    						userAccount);
    			}
        	}
            //List<Contractor> contractors = workmenService.getAllContractorBasedOnPE(unitId, userAccount);
            if (contractors.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(contractors, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching contractors: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getAllWorkOrders")
    public ResponseEntity<List<Workorder>> getAllWorkorders(
    		@RequestParam("unitId") String unitId, 
            @RequestParam("contractorId") String contractorId){
    	 log.info("Entering into getAllWorkorders for: " + unitId + " and contractorId: " + contractorId);
    	 try {
    		 List<Workorder> workorders = workmenService.getAllWorkordersBasedOnPEAndContractor(unitId,contractorId);
    		 if(workorders.isEmpty()) {
    			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    		 }
    		 return new ResponseEntity<>(workorders,HttpStatus.OK);
    	 }catch(Exception e) {
    		 log.error("Error fetching workorders: ", e);
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	 }
    
    	
    }
    
    @GetMapping("/getAllWC")
    public ResponseEntity<List<CmsContractorWC>> getAllWC(
    		@RequestParam("unitId") String unitId, 
            @RequestParam("contractorId") String contractorId,
            @RequestParam("workorderId") String workorderId){
    	 log.info("Entering into getAllWCs for: " + unitId + " contractorId: " + contractorId+" and workorderId: " + workorderId);
    	 try {
    		 List<CmsContractorWC> wcs = workmenService.getAllWCBasedOnPEAndCont(unitId,contractorId,workorderId);
    		 if(wcs.isEmpty()) {
    			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    		 }
    		 return new ResponseEntity<>(wcs,HttpStatus.OK);
    	 }catch(Exception e) {
    		 log.error("Error fetching getAllWCs: ", e);
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	 }
    
    	
    }
    
    @GetMapping("/getAllSkills")
    public ResponseEntity<List<DeptMapping>> getAllSkills(
    		@RequestParam("unitId") String unitId, 
            @RequestParam("tradeId") String tradeId){
    	 log.info("Entering into getAllSkills for: " + unitId + " tradeId: " + tradeId);
    	 try {
    		 List<DeptMapping> skills = workmenService.getAllSkills(unitId,tradeId);
    		 if(skills.isEmpty()) {
    			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    		 }
    		 return new ResponseEntity<>(skills,HttpStatus.OK);
    	 }catch(Exception e) {
    		 log.error("Error fetching getAllSkills: ", e);
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	 }
    
    	
    }
    
    @GetMapping("/getAllTrades")
    public ResponseEntity<Set<Trade>> getAllTrades(@RequestParam("unitId")String unitId){
    	log.info("Entered into getAllTrades for unitId:"+unitId);
    	try {
    		List<Trade> trades = workmenService.getAllTradesBasedOnPE(unitId);
    		Set<Trade> tradeSet = new HashSet<>(trades);
    		if(trades.isEmpty()) {
    			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    		}
    		return new ResponseEntity<>(tradeSet,HttpStatus.OK);
    	}catch(Exception e) {
    		log.error("Error fetching trades: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    
    }
    
    @GetMapping("/getAllDepartments")
    public ResponseEntity<Set<DeptMapping>> getAllDepartments(HttpServletRequest request,HttpServletResponse response,@RequestParam("unitId")String unitId){
    	log.info("Entered into getAllDepartments for unitId:"+unitId);
    	try {
    		
    		
    		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
            MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    		
    		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
        	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
        			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
        	
        	List<PersonOrgLevel> dept = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
        	List<PersonOrgLevel> subdepartments = groupedByLevelDef.getOrDefault("Area", new ArrayList<>());
        	
        	
    		List<DeptMapping> departments = workmenService.getAllDepartmentsOnPE(unitId);
    		
    		
    		List<DeptMapping> loggedDept = new ArrayList<>();
    		for(PersonOrgLevel p : dept) {
    			DeptMapping d = new DeptMapping();
    			d.setDepartment(p.getDescription());
    			d.setDepartmentId(Integer.parseInt(p.getId()));
    			loggedDept.add(d);
    		}

    		

    		// Build Set<String> of logged dept IDs
    		Set<String> loggedDeptIds = dept.stream()
    		        .map(PersonOrgLevel::getId) // keep as String
    		        .collect(Collectors.toSet());

    		// Filter departments
    		Set<DeptMapping> depSet = departments.stream()
    		        .filter(d -> loggedDeptIds.contains(String.valueOf(d.getDepartmentId())))
    		        .collect(Collectors.toSet());

    		
    		if(depSet.isEmpty()) {
    			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    		}
    		return new ResponseEntity<>(depSet,HttpStatus.OK);
    	}catch(Exception e) {
    		log.error("Error fetching Departments: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    
    }
    
    @GetMapping("/getAllSubDepartments")
    public ResponseEntity<Set<DeptMapping>> getAllSubDepartments(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("unitId") String unitId, 
            @RequestParam("departmentId") String departmentId){
    	 log.info("Entering into getAllSubDepartments for: " + unitId + " departmentId: " + departmentId);
    	 try {
    		 HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
             MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
     		
     		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
         	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
         			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
         	
         	List<PersonOrgLevel> dept = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
         	List<PersonOrgLevel> subdepartments = groupedByLevelDef.getOrDefault("Area", new ArrayList<>());
         	
         	List<DeptMapping> loggedDept = new ArrayList<>();
         	for(PersonOrgLevel p : subdepartments) {
    			DeptMapping d = new DeptMapping();
    			d.setDepartment(p.getDescription());
    			d.setDepartmentId(Integer.parseInt(p.getId()));
    			loggedDept.add(d);
    		}
    		 List<DeptMapping> Subdept = workmenService.getAllSubDepartments(unitId,departmentId);
    		 
    		// Build Set<String> of logged dept IDs
     		Set<String> loggedDeptIds = subdepartments.stream()
     		        .map(PersonOrgLevel::getId) // keep as String
     		        .collect(Collectors.toSet());

     		// Filter departments
     		Set<DeptMapping> depSet = Subdept.stream()
     		        .filter(d -> loggedDeptIds.contains(String.valueOf(d.getSubDepartmentId())))
     		        .collect(Collectors.toSet());

    		 
    		 if(depSet.isEmpty()) {
    			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    		 }
    		 return new ResponseEntity<>(depSet,HttpStatus.OK);
    	 }catch(Exception e) {
    		 log.error("Error fetching SubDepartments: ", e);
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	 }
    
    	
    }
    
    private static final String ROOT_DIRECTORY = "D:/wfd_cwfm/ep_docs/";

    public String uploadDocuments( MultipartFile aadharFile,
                                         MultipartFile policeFile,
                                         MultipartFile profilePic,
                                         MultipartFile appointmentFile,
                                         String userId,
                                         String gatePassId) {

        // Create directory path
        String directoryPath = ROOT_DIRECTORY + userId + "/"+gatePassId+"/";
        
        try {
            // Ensure the directory exists, if not create it
            Path path = Paths.get(directoryPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            // Save Aadhar PDF
            if (!aadharFile.isEmpty()) {
                String aadharFilePath = directoryPath + "aadhar.pdf";
                saveFile(aadharFile, aadharFilePath);
            }

            // Save Police Verification PDF
            if (!policeFile.isEmpty()) {
                String policeFilePath = directoryPath + "police.pdf";
                saveFile(policeFile, policeFilePath);
            }
            
            // Save Profile Pic
            if(!profilePic.isEmpty()) {
            	String profilePicPath = directoryPath +profilePic.getOriginalFilename();
            	saveFile(profilePic,profilePicPath);
            }
            //Save appointment PDF
            if (!appointmentFile.isEmpty()) {
                String appointmentFilePath = directoryPath + "appointment.pdf";
                saveFile(appointmentFile, appointmentFilePath);
            }

            // Return success message
            return "success";

        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
    }

    // Utility method to save file
    private void saveFile(MultipartFile file, String path) throws IOException {
        byte[] bytes = file.getBytes();
        Path filePath = Paths.get(path);
        Files.write(filePath, bytes);
    }
    
    @PostMapping("/saveGatePass")
    @ResponseBody
    public ResponseEntity<String> saveGatePass(
            @RequestParam("jsonData") String jsonData,
            @RequestParam(value = "aadharFile", required = false) MultipartFile aadharFile,
            @RequestParam(value = "policeFile", required = false) MultipartFile policeFile,
            @RequestParam(value = "profilePic", required = false) MultipartFile profilePic,
            @RequestParam(value = "appointmentFile", required = false) MultipartFile appointmentFile,
            @RequestParam(value = "additionalFiles", required = false) List<MultipartFile> additionalFiles,
            @RequestParam(value = "documentTypes", required = false) List<String> documentTypes,
            HttpServletRequest request, HttpServletResponse response) {
    	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

        String transactionId = null;
        GatePassMain gatePassMain;

        try {
            // Convert the JSON string back to the GatePassMain object
            ObjectMapper objectMapper = new ObjectMapper();
            gatePassMain = objectMapper.readValue(jsonData, GatePassMain.class);
            
            // Log the received GatePassMain object
            log.info("Received GatePassMain: {}", gatePassMain);

            gatePassMain.setCreatedBy(String.valueOf(user.getUserId()));
            gatePassMain.setAadharDocName(aadharFile != null && !aadharFile.isEmpty() ? "aadhar":"");
            gatePassMain.setPoliceVerificationDocName(policeFile!=null && !policeFile.isEmpty() ? "police":"");
            gatePassMain.setPhotoName(profilePic!=null && !profilePic.isEmpty()?profilePic.getOriginalFilename():"");
            gatePassMain.setAppointmentDocName(appointmentFile!=null && !appointmentFile.isEmpty() ? "appointment":"");
         // Mapping document types to their corresponding setter methods
            Map<String, Consumer<String>> docTypeSetterMap = new HashMap<>();
            docTypeSetterMap.put("Bank", gatePassMain::setBankDocName);
            docTypeSetterMap.put("Id2", gatePassMain::setIdProof2DocName);
            docTypeSetterMap.put("Other", gatePassMain::setOtherDocName);
            docTypeSetterMap.put("Medical", gatePassMain::setMedicalDocName);
            docTypeSetterMap.put("Education", gatePassMain::setEducationDocName);
            docTypeSetterMap.put("Training", gatePassMain::setTrainingDocName);
            docTypeSetterMap.put("Form11", gatePassMain::setForm11DocName);
            if(additionalFiles != null && !additionalFiles.isEmpty()) {
            // Set document names based on additionalFiles and documentTypes
            for (int i = 0; i < additionalFiles.size(); i++) {
                String docType = documentTypes.get(i);
                if (docType != null) {
                    Consumer<String> setter = docTypeSetterMap.get(docType);
                    if (setter != null) {
                        setter.accept(docType);
                    }
                }
            }
            }
            
            if("project".equals(gatePassMain.getOnboardingType())) {
            	transactionId = workmenService.saveProjectGatePass(gatePassMain);
            }else {
            	transactionId = workmenService.saveGatePass(gatePassMain);
            }
            
            
            if (transactionId != null) {
            	if (transactionId.contains("mandatory") || transactionId.contains("exceeded")) {
            		//if user wants we can draft the record
            		//workmenService.draftGatePass(gatePassMain);
                    return new ResponseEntity<>(transactionId, HttpStatus.BAD_REQUEST);
            		//Map<String, String> errorResponse = new HashMap<>();
                    //errorResponse.put("status", "error");
                    //errorResponse.put("message", transactionId);

                    //return ResponseEntity
                     //       .status(HttpStatus.BAD_REQUEST)
                     //       .body(new ObjectMapper().writeValueAsString(errorResponse));
                }else {
                if (aadharFile != null && !aadharFile.isEmpty() && policeFile!=null && !policeFile.isEmpty()) {
                    uploadDocuments(aadharFile, policeFile,profilePic,appointmentFile ,String.valueOf(user.getUserId()), transactionId);
                }
                // Upload additional files
                if (additionalFiles != null && documentTypes != null) {
                    uploadAdditionalDocuments(additionalFiles, documentTypes, String.valueOf(user.getUserId()), transactionId);
                }
                return new ResponseEntity<>("contractWorkmen/list", HttpStatus.OK);
            	}
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            
        } catch (Exception e) {
            log.error("Error saving data: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error saving data: " + e.getMessage());
        }
    }

    @GetMapping("/list")
	public String getAllPrincipalEmployer(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

		
		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
    	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
    			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
    	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
    	//List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
    	List<PersonOrgLevel> businessType = groupedByLevelDef.getOrDefault("Business Type", new ArrayList<>());
    	
    	List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
        CMSRoleRights rr =new CMSRoleRights();
        rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/list");
   	    listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
   	    request.setAttribute("UserPermission", rr);
    	request.setAttribute("principalEmployers", peList);
    	 // request.setAttribute("Dept", departments);
    	request.setAttribute("BusinessType", businessType) ;
    	  
		return "contractWorkmen/approverList";
	}
    
    @PostMapping("/quickOBList")
    public ResponseEntity<List<GatePassListingDto>> gatePassListingDetails(
    		@RequestParam(value = "principalEmployerId", required = false) String principalEmployerId,
    		@RequestParam(value = "deptId", required = false) String deptId,
    		@RequestParam(value = "businessType", required = false) String businessType,
    		@RequestParam(value = "type", required = false) String type,
    		HttpServletRequest request,HttpServletResponse response) {
    	
    	try {
			HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
			MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
			List<GatePassListingDto> listDto = new ArrayList<GatePassListingDto>();
			if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
				if("project".equals(type)) {
					listDto= workmenService.getGatePassListingDetails(principalEmployerId,deptId,String.valueOf(user.getUserId()),GatePassType.PROJECT.getStatus(),type);
				}else {
    			listDto= workmenService.getGatePassListingDetails(principalEmployerId,deptId,String.valueOf(user.getUserId()),GatePassType.CREATE.getStatus(),type);
				}
    		}else {	
    			if("project".equals(type)) {
    				listDto = workmenService.getGatePassListingForApprovers(principalEmployerId,deptId,user,GatePassType.PROJECT.getStatus(),type);
				}else {
					listDto = workmenService.getGatePassListingForApprovers(principalEmployerId,deptId,user,GatePassType.CREATE.getStatus(),type);
    		}
    		}	
				if (listDto.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
			
			return new ResponseEntity<>(listDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    

    
    @PostMapping("/getWorkmenDetailBasedOnId")
	public ResponseEntity<List<GatePassListingDto>> getWorkmenDetailBasedOnId(
            @RequestParam(value = "gatePassId", required = false) String gatePassId,HttpServletRequest request,HttpServletResponse response) {
        try {
        	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
            MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
        
        	  // Sample data (replace with your database queries)
	        List<GatePassListingDto> listDto = workmenService.getWorkmenDetailBasedOnId(gatePassId);
	        
	        

            if (listDto.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
 }
    
    @GetMapping("/view/{transactionId}")
    public String viewIndividualContractWorkmenDetails(@PathVariable("transactionId") String transactionId,HttpServletRequest request,HttpServletResponse response) {
    	log.info("Entered into viewIndividualContractWorkmenDetails: "+transactionId);
    	GatePassMain gatePassMainObj =null;
    	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    	try {
    		gatePassMainObj = workmenService.getIndividualContractWorkmenDetails(transactionId);
    		request.setAttribute("GatePassObj", gatePassMainObj);
          
    		if(null != gatePassMainObj.getPhotoName()) {
       		 String profilePicFilePath =  "/imageinline/"+user.getUserId()+"/" + transactionId + "/" +gatePassMainObj.getPhotoName();
       		 request.setAttribute("imagePath", profilePicFilePath);
       		}
    		 
    		//Get All GeneralMaster
    		List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMasterForGatePass(gatePassMainObj);
    		for (CmsGeneralMaster generalMaster : gmList) {
    		    String gmType = generalMaster.getGmType();
    		if ("GENDER".equals(gmType)) {
    	        gatePassMainObj.setGender(generalMaster.getGmName()); 
    	    } else if ("BLOODGROUP".equals(gmType)) {
    	        gatePassMainObj.setBloodGroup(generalMaster.getGmName()); 
    	    } else if ("ACADEMICS".equals(gmType)) {
    	        gatePassMainObj.setAcademic(generalMaster.getGmName()); 
    	    } else if ("ZONE".equals(gmType)) {
    	        gatePassMainObj.setZone(generalMaster.getGmName()); 
    	    } else if ("ACCESSAREA".equals(gmType)) {
    	        gatePassMainObj.setAccessArea(generalMaster.getGmName()); 
    	    } else if ("WAGECATEGORY".equals(gmType)) {
    	        gatePassMainObj.setWageCategory(generalMaster.getGmName()); 
    	    } else if ("BONUSPAYOUT".equals(gmType)) {
    	        gatePassMainObj.setBonusPayout(generalMaster.getGmName()); 
    	    } else if("DEPARTMENT".equals(gmType)){
    	    	gatePassMainObj.setDepartment(generalMaster.getGmName());
    	    } else if("AREA".equals(gmType)) {
    	    	gatePassMainObj.setSubdepartment(generalMaster.getGmName());
    	    }else if("TRADE".equals(gmType)){
    	    	gatePassMainObj.setTrade(generalMaster.getGmName());
    	    } else if("SKILL".equals(gmType)) {
    	    	gatePassMainObj.setSkill(generalMaster.getGmName());
    		} else if("WORKMENTYPE".equals(gmType)) {
    	    	gatePassMainObj.setWorkmenType(generalMaster.getGmName());
    		}
    		}
    		List<ApproverStatusDTO> approvers = new ArrayList<ApproverStatusDTO>();
    		
    		if(gatePassMainObj.getOnboardingType().equals("project")) {
    			approvers = workmenService.getApprovalDetails(transactionId,gatePassMainObj.getUnitId(),GatePassType.PROJECT.getStatus());
    		}else
    		{
    			approvers = workmenService.getApprovalDetails(transactionId,gatePassMainObj.getUnitId(),GatePassType.CREATE.getStatus());
    		}
    		request.setAttribute("approvers", approvers);
    	}catch(Exception e) {
    		log.error("Error getting workmen details ", e);
    	}
    	log.info("Exiting from viewIndividualContractWorkmenDetails: "+transactionId);
    	if(gatePassMainObj.getOnboardingType().equals("regular"))
    		return "contractWorkmen/view";
    	else if(gatePassMainObj.getOnboardingType().equals("quick"))
    		return "contractWorkmen/quickOnboardingView";
    	else
    		return "contractWorkmen/projectOnboardingView";
    }
    @PostMapping("/approveRejectGatePass")
    public ResponseEntity<String> approveRejectGatePass(@RequestBody ApproveRejectGatePassDto dto,HttpServletRequest request,HttpServletResponse response) {
    	String result=null; 
    	try {
             ObjectMapper objectMapper = new ObjectMapper();
             String approveRejectGatePass = objectMapper.writeValueAsString(dto);
             log.info("Received approveRejectGatePass JSON: {}", approveRejectGatePass);
         } catch (Exception e) {
             log.error("Error converting approveRejectGatePass to JSON", e);
         }
         try {
        	 result = workmenService.approveRejectGatePass(dto);
         	if(null!=result) {
         		if (result.contains("mandatory") || result.contains("exceeded")) {
                    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
                }
         		return new ResponseEntity<>(result,HttpStatus.OK);
         	}
         	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                  .body("Error saving data: " + e.getMessage());
         } 
    }
    
    @GetMapping("/viewFile/{encodedData}")
    public void viewFile(@PathVariable("encodedData") String encodedData, HttpServletResponse response) {
        try {
            // 🔐 Decode Base64 encoded JSON
            String decodedJson = new String(Base64.getUrlDecoder().decode(encodedData), StandardCharsets.UTF_8);

            // Parse decoded JSON to extract parameters
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> data = mapper.readValue(decodedJson, new TypeReference<>() {});
            String transactionId = data.get("transactionId");
            String userId = data.get("userId");
            String docType = data.get("docType");

            // Determine file path
            String filePath;
            if (docType.equals("aadhar") || docType.equals("police") || docType.equals("bank") || docType.equals("appointment")||
                docType.equals("training") || docType.equals("other") || docType.equals("id2") ||
                docType.equals("medical") || docType.equals("education") || docType.equals("form11")
                || docType.equals("exitletter")|| docType.equals("fnf")|| docType.equals("feedback")
                || docType.equals("ratemanager")|| docType.equals("loc")) {
                filePath = ROOT_DIRECTORY + userId + "/" + transactionId + "/" + docType + ".pdf";
            } else {
                filePath = ROOT_DIRECTORY + userId + "/" + transactionId + "/" + docType;
            }

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

            // Stream file content
            Files.copy(file.toPath(), response.getOutputStream());
            response.getOutputStream().flush();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error opening file");
            } catch (IOException ignored) {}
        }
    }


//
//
    private String uploadAdditionalDocuments(List<MultipartFile> additionalFiles,
    		List<String> documentTypes,
    		String userId,
    		String gatePassId) {
    	// Create directory path
    	String directoryPath = ROOT_DIRECTORY + userId + "/" + gatePassId + "/";

    	try {
    		// Ensure the directory exists, if not create it
    		Path path = Paths.get(directoryPath);
    		if (!Files.exists(path)) {
    			Files.createDirectories(path);
    		}

    		for (int i = 0; i < additionalFiles.size(); i++) {
    			MultipartFile file = additionalFiles.get(i);
    			String docType = documentTypes.get(i);

    			// Create a filename based on the document type
    			String fileName = docType + ".pdf"; // or any other format you prefer
    			String filePath = directoryPath + fileName;

    			// Save the file
    			if (!file.isEmpty()) {
    				saveFile(file, filePath);
    			}
    		}

    		return "success";

    	} catch (IOException e) {
    		log.error("Failed to save additional documents: ", e);
    		return "failed";
    	}
    }
    
    
//    @PostMapping("/gatePassAction")
//    public ResponseEntity<String> gatePassAction(@RequestBody GatePassActionDto dto,HttpServletRequest request,HttpServletResponse response) {
//    	String result=null; 
//    	try {
//             ObjectMapper objectMapper = new ObjectMapper();
//             String gatePassActionDto = objectMapper.writeValueAsString(dto);
//             log.info("Received gatePassActionDto JSON: {}", gatePassActionDto);
//         } catch (Exception e) {
//             log.error("Error converting gatePassActionDto to JSON", e);
//         }
//         try {
//        	 result = workmenService.gatePassAction(dto);
//         	if(null!=result) {
//         		
//         		if(dto.getGatePassType().equals(GatePassType.CREATE.getStatus())) {
//            		result="contractWorkmen/view";
//            	}else if(dto.getGatePassType().equals(GatePassType.CANCEL.getStatus()))
//            	{
//            		result="contractWorkmen/cancelView";
//            	}else if(dto.getGatePassType().equals(GatePassType.BLOCK.getStatus()))
//            	{
//            		result="contractWorkmen/blockView";
//            	}else if(dto.getGatePassType().equals(GatePassType.UNBLOCK.getStatus()))
//            	{
//            		result="contractWorkmen/unblockView";
//            	}else if(dto.getGatePassType().equals(GatePassType.BLACKLIST.getStatus()))
//            	{
//            		result="contractWorkmen/blackView";
//            	}else if(dto.getGatePassType().equals(GatePassType.DEBLACKLIST.getStatus()))
//            	{
//            		result="contractWorkmen/deblackView";
//            	}else if(dto.getGatePassType().equals(GatePassType.LOSTORDAMAGE.getStatus()))
//            	{
//            		result="contractWorkmen/lostView";
//            	}else if(dto.getGatePassType().equals(GatePassType.RENEW.getStatus())) {
//            		result="contractWorkmen/renewView";
//            	}
//         		return new ResponseEntity<>(result,HttpStatus.OK);
//         	}
//         	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                  .body("Error saving data: " + e.getMessage());
//         } 
//    }
    @PostMapping("/gatePassAction")
    public ResponseEntity<String> gatePassAction(
            @RequestParam("jsonData") String jsonData,
            @RequestParam(value = "exitFile", required = false) MultipartFile exitFile,
            @RequestParam(value = "fnfFile", required = false) MultipartFile fnfFile,
            @RequestParam(value = "feedbackFile", required = false) MultipartFile feedbackFile,
            @RequestParam(value = "rateManagerFile", required = false) MultipartFile rateManagerFile,
            @RequestParam(value = "locFile", required = false) MultipartFile locFile,
            HttpServletRequest request, HttpServletResponse response) {
    	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
        String result = null;
        GatePassActionDto dto = null;
        GatePassMain gatePassMain = null;

        try {
            ObjectMapper mapper = new ObjectMapper();

            // Parse JSON into DTO (for workflow)
            dto = mapper.readValue(jsonData, GatePassActionDto.class);

            // Parse JSON into GatePassMain (for DB entity)
            gatePassMain = mapper.readValue(jsonData, GatePassMain.class);

            log.info("Received DTO: {}", dto);
            log.info("Received GatePassMain: {}", gatePassMain);

            // Set created by
           // User user = (User) request.getSession().getAttribute("user");
           // gatePassMain.setCreatedBy(String.valueOf(user.getUserId()));

            // ===== SET DOCUMENT FLAGS / NAMES =====
            gatePassMain.setExitLetterDocName(exitFile != null && !exitFile.isEmpty() ? "exitletter": "");
            gatePassMain.setFNFDocName(fnfFile != null && !fnfFile.isEmpty() ? "fnf" : "");
            gatePassMain.setFeedbackFormDocName(feedbackFile != null && !feedbackFile.isEmpty() ? "feedback" : "");
            gatePassMain.setRateManagerDocName(rateManagerFile != null && !rateManagerFile.isEmpty() ? "ratemanager" : "");
            gatePassMain.setLOCDocName(locFile != null && !locFile.isEmpty() ? "loc" : "");

       
        	
        	 // ===== SAVE GATEPASSMAIN ENTITY =====
        	boolean status = workmenService.updateGatePassMainWithReasoningTab(dto,exitFile,fnfFile,feedbackFile,rateManagerFile,locFile);
        	 if (status) {
        		 uploadReasoningDocuments(exitFile, fnfFile,feedbackFile,rateManagerFile ,locFile,String.valueOf(user.getUserId()), dto.getTransactionId());
        	 }
        	 else{
        		 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                         .body("Failed to update GatePassMain");
             }

        	 result = workmenService.gatePassAction(dto);
        	 
       	if(null!=result) {
       		
                if (dto.getGatePassType().equals(GatePassType.CREATE.getStatus())) {
                    result = "contractWorkmen/view";

                } else if (dto.getGatePassType().equals(GatePassType.CANCEL.getStatus())) {
                    result = "contractWorkmen/cancelView";

                } else if (dto.getGatePassType().equals(GatePassType.BLOCK.getStatus())) {
                    result = "contractWorkmen/blockView";

                } else if (dto.getGatePassType().equals(GatePassType.UNBLOCK.getStatus())) {
                    result = "contractWorkmen/unblockView";

                } else if (dto.getGatePassType().equals(GatePassType.BLACKLIST.getStatus())) {
                    result = "contractWorkmen/blackView";

                } else if (dto.getGatePassType().equals(GatePassType.DEBLACKLIST.getStatus())) {
                    result = "contractWorkmen/deblackView";

                } else if (dto.getGatePassType().equals(GatePassType.LOSTORDAMAGE.getStatus())) {
                    result = "contractWorkmen/lostView";

                } else if (dto.getGatePassType().equals(GatePassType.RENEW.getStatus())) {
                    result = "contractWorkmen/renewView";
                }

                return new ResponseEntity<>(result, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            log.error("Error saving data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving data: " + e.getMessage());
        }
    }

    
    @GetMapping("/blockListFilter")
   	public String blockListFilter(HttpServletRequest request, HttpServletResponse response) {
   		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
   		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
   		List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
        CMSRoleRights rr =new CMSRoleRights();
        rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/blockListFilter");
   	    listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
   	    request.setAttribute("UserPermission", rr);
   		
   		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
       	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
       			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
       	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
       //	List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
       	request.setAttribute("principalEmployers", peList);
       	  //request.setAttribute("Dept", departments);
   		return "contractWorkmen/blockListing";
   	}
    
    @PostMapping("/blockList")
    public ResponseEntity<List<GatePassListingDto>> blockList(
    		@RequestParam(value = "principalEmployerId", required = false) String principalEmployerId,
    		@RequestParam(value = "deptId", required = false) String deptId,
    		HttpServletRequest request,HttpServletResponse response) {
    	try {
			HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
			MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
			
			List<GatePassListingDto> listDto = new ArrayList<GatePassListingDto>();
			if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    			listDto= workmenService.getGatePassActionListingDetails(principalEmployerId,deptId,String.valueOf(user.getUserId()),GatePassType.BLOCK.getStatus(),GatePassType.CREATE.getStatus(),GatePassType.RENEW.getStatus());
        		
			}else {	
				listDto = workmenService.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.BLOCK.getStatus());
    		}	
				if (listDto.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
			
			return new ResponseEntity<>(listDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @GetMapping("/unblockListFilter")
   	public String unblockListFilter(HttpServletRequest request, HttpServletResponse response) {
   		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
   		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
   		List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
        CMSRoleRights rr =new CMSRoleRights();
        rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/unblockListFilter");
   	    listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
   	    request.setAttribute("UserPermission", rr);
   		
   		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
       	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
       			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
       	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
       	//List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
       	request.setAttribute("principalEmployers", peList);
       	  //request.setAttribute("Dept", departments);
   		return "contractWorkmen/unblockListing";
   	}
   
    @PostMapping("/unblockList")
    public ResponseEntity<List<GatePassListingDto>> unblockList(
    		@RequestParam(value = "principalEmployerId", required = false) String principalEmployerId,
    		@RequestParam(value = "deptId", required = false) String deptId,
    		HttpServletRequest request,HttpServletResponse response) {
    	try {
			HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
			MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
			
			List<GatePassListingDto> listDto = new ArrayList<GatePassListingDto>();
			if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    			listDto= workmenService.getGatePassUnblockDeblackListingDetails(principalEmployerId,deptId,String.valueOf(user.getUserId()),GatePassType.UNBLOCK.getStatus(),GatePassType.BLOCK.getStatus()," ");
        		
			}else {	
				listDto = workmenService.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.UNBLOCK.getStatus());
    		}	
				if (listDto.isEmpty()) {
					return new ResponseEntity<>(listDto,HttpStatus.NO_CONTENT);
				}
			
			return new ResponseEntity<>(listDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    			//listDto = workmenService.getGatePassListingForApprovers(user,GatePassType.UNBLOCK.getStatus());
    		
    }
    
    
    	
    
    @GetMapping("/blackListFilter")
   	public String blackListFilter(HttpServletRequest request, HttpServletResponse response) {
   		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
   		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
   		List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
        CMSRoleRights rr =new CMSRoleRights();
        rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/blackListFilter");
   	    listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
   	    request.setAttribute("UserPermission", rr);
   		
   		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
       	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
       			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
       	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
       //	List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
       	request.setAttribute("principalEmployers", peList);
       	  //request.setAttribute("Dept", departments);
   		return "contractWorkmen/blackListing";
   	}
    @PostMapping("/blackList")
    public ResponseEntity<List<GatePassListingDto>> blackList(
    		@RequestParam(value = "principalEmployerId", required = false) String principalEmployerId,
    		@RequestParam(value = "deptId", required = false) String deptId,
    		HttpServletRequest request,HttpServletResponse response) {
    	try {
			HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
			MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
			
			List<GatePassListingDto> listDto = new ArrayList<GatePassListingDto>();
			if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    			listDto= workmenService.getGatePassActionListingDetails(principalEmployerId,deptId,String.valueOf(user.getUserId()),GatePassType.BLACKLIST.getStatus(),GatePassType.CREATE.getStatus(),GatePassType.RENEW.getStatus());
        		
			}else {	
				listDto = workmenService.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.BLACKLIST.getStatus());
    		}	
				if (listDto.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
			
			return new ResponseEntity<>(listDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    			//listDto = workmenService.getGatePassListingForApprovers(user,GatePassType.BLACKLIST.getStatus());
    		
    }
    @GetMapping("/deblackListFilter")
   	public String deblackListFilter(HttpServletRequest request, HttpServletResponse response) {
   		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
   		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
   		List<PrincipalEmployer> listDtos =new ArrayList<PrincipalEmployer>();
        CMSRoleRights rr =new CMSRoleRights();
        rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/deblackListFilter");
   	    listDtos = peService.getAllPrincipalEmployer(user.getUserAccount());
   	    request.setAttribute("UserPermission", rr);
   		
   		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
       	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
       			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
       	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
       	//List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
       	request.setAttribute("principalEmployers", peList);
       	 // request.setAttribute("Dept", departments);
   		return "contractWorkmen/deblackListing";
   	}
    @PostMapping("/deblackList")
    public ResponseEntity<List<GatePassListingDto>> deblackList(
    		@RequestParam(value = "principalEmployerId", required = false) String principalEmployerId,
    		@RequestParam(value = "deptId", required = false) String deptId,
    		HttpServletRequest request,HttpServletResponse response) {
    	try {
			HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
			MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
			List<GatePassListingDto> listDto = new ArrayList<GatePassListingDto>();
			if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    			listDto= workmenService.getGatePassUnblockDeblackListingDetails(principalEmployerId,deptId,String.valueOf(user.getUserId()),GatePassType.DEBLACKLIST.getStatus(),GatePassType.BLACKLIST.getStatus()," ");
        		
			}else {	
				listDto = workmenService.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.DEBLACKLIST.getStatus());
    		}	
				if (listDto.isEmpty()) {
					return new ResponseEntity<>(listDto,HttpStatus.NO_CONTENT);
				}
			
			return new ResponseEntity<>(listDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    			//listDto = workmenService.getGatePassListingForApprovers(user,GatePassType.DEBLACKLIST.getStatus());
    		
    }
    @GetMapping("/cancelFilter")
   	public String cancelFilter(HttpServletRequest request, HttpServletResponse response) {
   		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
   		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
   		List<PrincipalEmployer> listDtos =new ArrayList<PrincipalEmployer>();
        CMSRoleRights rr =new CMSRoleRights();
        rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/cancelFilter");
   	    listDtos = peService.getAllPrincipalEmployer(user.getUserAccount());
   	    request.setAttribute("UserPermission", rr);
   		
   		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
       	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
       			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
       	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
       //	List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
       	request.setAttribute("principalEmployers", peList);
       	  //request.setAttribute("Dept", departments);
   		return "contractWorkmen/cancelListing";
   	}
    @PostMapping("/cancel")
    public ResponseEntity<List<GatePassListingDto>> cancel(
    		@RequestParam(value = "principalEmployerId", required = false) String principalEmployerId,
    		@RequestParam(value = "deptId", required = false) String deptId,
    		HttpServletRequest request,HttpServletResponse response) {
    	try {
			HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
			MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
			List<GatePassListingDto> listDto = new ArrayList<GatePassListingDto>();
			if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    			listDto= workmenService.getGatePassActionListingDetails(principalEmployerId,deptId,String.valueOf(user.getUserId()),GatePassType.CANCEL.getStatus(),GatePassType.CREATE.getStatus(),GatePassType.RENEW.getStatus());
        		
			}else {	
				listDto = workmenService.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.CANCEL.getStatus());
    		}	
				if (listDto.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
			
			return new ResponseEntity<>(listDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    		
    			//listDto = workmenService.getGatePassListingForApprovers(user,GatePassType.CANCEL.getStatus());
    }
    @GetMapping("/lostordamageFilter")
   	public String lostordamageFilter(HttpServletRequest request, HttpServletResponse response) {
   		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
   		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
   		List<PrincipalEmployer> listDtos =new ArrayList<PrincipalEmployer>();
        CMSRoleRights rr =new CMSRoleRights();
        rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/lostordamageFilter");
   	    listDtos = peService.getAllPrincipalEmployer(user.getUserAccount());
   	    request.setAttribute("UserPermission", rr);
   		
   		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
       	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
       			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
       	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
       	//List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
       	request.setAttribute("principalEmployers", peList);
       	  //request.setAttribute("Dept", departments);
   		return "contractWorkmen/lostListing";
   	}
    
    @PostMapping("/lostordamage")
    public ResponseEntity<List<GatePassListingDto>> lostordamage(
    		@RequestParam(value = "principalEmployerId", required = false) String principalEmployerId,
    		@RequestParam(value = "deptId", required = false) String deptId,
    		HttpServletRequest request,HttpServletResponse response) {
    	try {
			HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
			MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
			
			List<GatePassListingDto> listDto = new ArrayList<GatePassListingDto>();
			if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    			listDto= workmenService.getGatePassActionListingDetails(principalEmployerId,deptId,String.valueOf(user.getUserId()),GatePassType.LOSTORDAMAGE.getStatus(),GatePassType.CREATE.getStatus(),GatePassType.RENEW.getStatus());
        		
			}else {	
				listDto = workmenService.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.LOSTORDAMAGE.getStatus());
    		}	
				if (listDto.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
			
			return new ResponseEntity<>(listDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    			//listDto = workmenService.getGatePassListingForApprovers(user,GatePassType.LOSTORDAMAGE.getStatus());
    	
    	
    	 
    }
    
    @GetMapping("/cancelview/{gatePassId}/{mode}")
    public String cancelviewIndividualContractWorkmenDetails(@PathVariable("gatePassId") String gatePassId,@PathVariable("mode") String mode,HttpServletRequest request,HttpServletResponse response) {
    	log.info("Entered into viewIndividualContractWorkmenDetails: "+gatePassId);
    	GatePassMain gatePassMainObj =null;String transactionId=null;

    	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    	try {
    		if(mode.equals("add")){
     			 transactionId= workmenService.generateTransactionId();
     		}else {
     			transactionId=workmenService.getTransactionIdByGPId(gatePassId, GatePassType.CANCEL.getStatus());
     		}
      		gatePassMainObj = workmenService.getIndividualContractWorkmenDetailsByGatePassId(gatePassId);
      		gatePassMainObj.setTransactionId(transactionId);
    		request.setAttribute("GatePassObj", gatePassMainObj);

    		 request.setAttribute("mode", mode);
    		 String oldTransactionId=workmenDao.getTransactionIdByGatePassId(gatePassId);
    		 gatePassMainObj.setOldTransactionId(oldTransactionId);
    		 String renewTrans = workmenService.getRenewTransactionIfExists(gatePassId);
    		 if(null!=renewTrans) {
    			 oldTransactionId=renewTrans;
    			 gatePassMainObj.setOldTransactionId(oldTransactionId);
    		 }
    		 if(null != gatePassMainObj.getPhotoName()) {


           		 String profilePicFilePath =  "/imageinline/"+user.getUserId()+"/" + oldTransactionId + "/" +gatePassMainObj.getPhotoName();

           		 request.setAttribute("imagePath", profilePicFilePath);
           		}
    		//Get All GeneralMaster
    		List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMasterForGatePass(gatePassMainObj);
    		for (CmsGeneralMaster generalMaster : gmList) {
    		    String gmType = generalMaster.getGmType();
    		if ("GENDER".equals(gmType)) {
    	        gatePassMainObj.setGender(generalMaster.getGmName()); 
    	    } else if ("BLOODGROUP".equals(gmType)) {
    	        gatePassMainObj.setBloodGroup(generalMaster.getGmName()); 
    	    } else if ("ACADEMICS".equals(gmType)) {
    	        gatePassMainObj.setAcademic(generalMaster.getGmName()); 
    	    } else if ("ZONE".equals(gmType)) {
    	        gatePassMainObj.setZone(generalMaster.getGmName()); 
    	    } else if ("ACCESSAREA".equals(gmType)) {
    	        gatePassMainObj.setAccessArea(generalMaster.getGmName()); 
    	    } else if ("WAGECATEGORY".equals(gmType)) {
    	        gatePassMainObj.setWageCategory(generalMaster.getGmName()); 
    	    } else if ("BONUSPAYOUT".equals(gmType)) {
    	        gatePassMainObj.setBonusPayout(generalMaster.getGmName()); 
    	    } else if("DEPARTMENT".equals(gmType)){
    	    	gatePassMainObj.setDepartment(generalMaster.getGmName());
    	    } else if("AREA".equals(gmType)) {
    	    	gatePassMainObj.setSubdepartment(generalMaster.getGmName());
    	    }else if("TRADE".equals(gmType)){
    	    	gatePassMainObj.setTrade(generalMaster.getGmName());
    	    } else if("SKILL".equals(gmType)) {
    	    	gatePassMainObj.setSkill(generalMaster.getGmName());
    		}else if("WORKMENTYPE".equals(gmType)) {
    	    	gatePassMainObj.setWorkmenType(generalMaster.getGmName());
    		}else if("CAN".equals(gmType)) {
    	    	gatePassMainObj.setReasoning(generalMaster.getGmName());
    		}
    		}
    		List<CmsGeneralMaster> gmLists = workmenService.getAllGeneralMaster();

    		// Grouping the CmsGeneralMaster objects by gmType
    		Map<String, List<CmsGeneralMaster>> groupedByGmType = gmLists.stream()
    		        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

    		// Define the types and their corresponding request attribute names
    		Map<String, String> attributeMapping = Map.of(
    		        "CAN","can"
    		);

    		// Iterate over the attribute mappings and set the request attributes dynamically
    		attributeMapping.forEach((type, attributeName) -> {
    		    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
    		    request.setAttribute(attributeName, gmList1);
    		});

    		List<ApproverStatusDTO> approvers = new ArrayList<ApproverStatusDTO>();
    		if(gatePassMainObj.getGatePassAction().equals(GatePassType.CANCEL.getStatus())) {
    			approvers=	workmenService.getApprovalDetails(transactionId,gatePassMainObj.getUnitId(),GatePassType.CANCEL.getStatus());
    		}
    		request.setAttribute("approvers", approvers); 
    		 
    	}catch(Exception e) {
    		log.error("Error getting workmen details ", e);
    	}
    	log.info("Exiting from viewIndividualContractWorkmenDetails: "+gatePassId);
    	
    		return "contractWorkmen/cancelView";
    	
    }
    @GetMapping("/blockview/{gatePassId}/{mode}")
    public String blockviewIndividualContractWorkmenDetails(@PathVariable("gatePassId") String gatePassId, @PathVariable("mode") String mode,HttpServletRequest request,HttpServletResponse response) {
    	log.info("Entered into viewIndividualContractWorkmenDetails: "+gatePassId);
    	GatePassMain gatePassMainObj =null;String transactionId=null;

    	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    	try {
    		if(mode.equals("add")){
    			 transactionId= workmenService.generateTransactionId();
    		}else {
    			transactionId=workmenService.getTransactionIdByGPId(gatePassId, GatePassType.BLOCK.getStatus());
    		}
    		gatePassMainObj = workmenService.getIndividualContractWorkmenDetailsByGatePassId(gatePassId);
    		String oldTransactionId = gatePassMainObj.getTransactionId();
    		gatePassMainObj.setTransactionId(transactionId);
    		request.setAttribute("GatePassObj", gatePassMainObj);
    		 request.setAttribute("mode", mode);
    		// String oldTransactionId=workmenDao.getTransactionIdByGatePassId(gatePassId);
    		 gatePassMainObj.setOldTransactionId(oldTransactionId);
    		 String renewTrans = workmenService.getRenewTransactionIfExists(gatePassId);
    		 if(null!=renewTrans) {
    			 oldTransactionId=renewTrans;
    			 gatePassMainObj.setOldTransactionId(oldTransactionId);
    		 }
    		 if(null != gatePassMainObj.getPhotoName()) {
           		 String profilePicFilePath =  "/imageinline/"+user.getUserId()+"/" + oldTransactionId + "/" +gatePassMainObj.getPhotoName();
           		 request.setAttribute("imagePath", profilePicFilePath);
           		}
    		//Get All GeneralMaster
    		List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMasterForGatePass(gatePassMainObj);
    		for (CmsGeneralMaster generalMaster : gmList) {
    		    String gmType = generalMaster.getGmType();
    		if ("GENDER".equals(gmType)) {
    	        gatePassMainObj.setGender(generalMaster.getGmName()); 
    	    } else if ("BLOODGROUP".equals(gmType)) {
    	        gatePassMainObj.setBloodGroup(generalMaster.getGmName()); 
    	    } else if ("ACADEMICS".equals(gmType)) {
    	        gatePassMainObj.setAcademic(generalMaster.getGmName()); 
    	    } else if ("ZONE".equals(gmType)) {
    	        gatePassMainObj.setZone(generalMaster.getGmName()); 
    	    } else if ("ACCESSAREA".equals(gmType)) {
    	        gatePassMainObj.setAccessArea(generalMaster.getGmName()); 
    	    } else if ("WAGECATEGORY".equals(gmType)) {
    	        gatePassMainObj.setWageCategory(generalMaster.getGmName()); 
    	    } else if ("BONUSPAYOUT".equals(gmType)) {
    	        gatePassMainObj.setBonusPayout(generalMaster.getGmName()); 
    	    } else if("DEPARTMENT".equals(gmType)){
    	    	gatePassMainObj.setDepartment(generalMaster.getGmName());
    	    } else if("AREA".equals(gmType)) {
    	    	gatePassMainObj.setSubdepartment(generalMaster.getGmName());
    	    }else if("TRADE".equals(gmType)){
    	    	gatePassMainObj.setTrade(generalMaster.getGmName());
    	    } else if("SKILL".equals(gmType)) {
    	    	gatePassMainObj.setSkill(generalMaster.getGmName());
    		}else if("WORKMENTYPE".equals(gmType)) {
    	    	gatePassMainObj.setWorkmenType(generalMaster.getGmName());
    		}else if("BLK".equals(gmType)) {
    	    	gatePassMainObj.setReasoning(generalMaster.getGmName());
    		}
    		}
    		List<CmsGeneralMaster> gmLists = workmenService.getAllGeneralMaster();

    		// Grouping the CmsGeneralMaster objects by gmType
    		Map<String, List<CmsGeneralMaster>> groupedByGmType = gmLists.stream()
    		        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

    		// Define the types and their corresponding request attribute names
    		Map<String, String> attributeMapping = Map.of(
    		        "BLK","blk"
    		);

    		// Iterate over the attribute mappings and set the request attributes dynamically
    		attributeMapping.forEach((type, attributeName) -> {
    		    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
    		    request.setAttribute(attributeName, gmList1);
    		});

    		List<ApproverStatusDTO> approvers = new ArrayList<ApproverStatusDTO>();
    		if(gatePassMainObj.getGatePassAction().equals(GatePassType.BLOCK.getStatus())) {
    			approvers=	workmenService.getApprovalDetails(transactionId,gatePassMainObj.getUnitId(),GatePassType.BLOCK.getStatus());
    		}
    		request.setAttribute("approvers", approvers); 
    	}catch(Exception e) {
    		log.error("Error getting workmen details ", e);
    	}
    	log.info("Exiting from viewIndividualContractWorkmenDetails: "+gatePassId);
    	
    		return "contractWorkmen/blockView";
    	
    }
    @GetMapping("/unblockview/{gatePassId}/{mode}")
    public String unblockviewIndividualContractWorkmenDetails(@PathVariable("gatePassId") String gatePassId,
    		@PathVariable("mode") String mode,HttpServletRequest request,HttpServletResponse response) {
    	log.info("Entered into viewIndividualContractWorkmenDetails: "+gatePassId);
    	GatePassMain gatePassMainObj =null;
    	String transactionId=null;
    	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    	try {
    		if(mode.equals("add")){
   			 transactionId= workmenService.generateTransactionId();
   		}else {
   			transactionId=workmenService.getTransactionIdByGPId(gatePassId, GatePassType.UNBLOCK.getStatus());
   		}
    		gatePassMainObj = workmenService.getIndividualContractWorkmenDetailsByGatePassId(gatePassId);
    		String oldTransactionId = gatePassMainObj.getTransactionId();
    		gatePassMainObj.setTransactionId(transactionId);
    		request.setAttribute("GatePassObj", gatePassMainObj);
    		 request.setAttribute("mode", mode);
    		// String oldTransactionId=workmenDao.getTransactionIdByGatePassId(gatePassId);
    		 gatePassMainObj.setOldTransactionId(oldTransactionId);
    		 String renewTrans = workmenService.getRenewTransactionIfExists(gatePassId);
    		 if(null!=renewTrans) {
    			 oldTransactionId=renewTrans;
    			 gatePassMainObj.setOldTransactionId(oldTransactionId);
    		 }
    		 if(null != gatePassMainObj.getPhotoName()) {
           		 String profilePicFilePath =  "/imageinline/"+user.getUserId()+"/" + oldTransactionId + "/" +gatePassMainObj.getPhotoName();
           		 request.setAttribute("imagePath", profilePicFilePath);
           		}
    		 
    		//Get All GeneralMaster
    		List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMasterForGatePass(gatePassMainObj);
    		for (CmsGeneralMaster generalMaster : gmList) {
    		    String gmType = generalMaster.getGmType();
    		if ("GENDER".equals(gmType)) {
    	        gatePassMainObj.setGender(generalMaster.getGmName()); 
    	    } else if ("BLOODGROUP".equals(gmType)) {
    	        gatePassMainObj.setBloodGroup(generalMaster.getGmName()); 
    	    } else if ("ACADEMICS".equals(gmType)) {
    	        gatePassMainObj.setAcademic(generalMaster.getGmName()); 
    	    } else if ("ZONE".equals(gmType)) {
    	        gatePassMainObj.setZone(generalMaster.getGmName()); 
    	    } else if ("ACCESSAREA".equals(gmType)) {
    	        gatePassMainObj.setAccessArea(generalMaster.getGmName()); 
    	    } else if ("WAGECATEGORY".equals(gmType)) {
    	        gatePassMainObj.setWageCategory(generalMaster.getGmName()); 
    	    } else if ("BONUSPAYOUT".equals(gmType)) {
    	        gatePassMainObj.setBonusPayout(generalMaster.getGmName()); 
    	    } else if("DEPARTMENT".equals(gmType)){
    	    	gatePassMainObj.setDepartment(generalMaster.getGmName());
    	    } else if("AREA".equals(gmType)) {
    	    	gatePassMainObj.setSubdepartment(generalMaster.getGmName());
    	    }else if("TRADE".equals(gmType)){
    	    	gatePassMainObj.setTrade(generalMaster.getGmName());
    	    } else if("SKILL".equals(gmType)) {
    	    	gatePassMainObj.setSkill(generalMaster.getGmName());
    		}else if("WORKMENTYPE".equals(gmType)) {
    	    	gatePassMainObj.setWorkmenType(generalMaster.getGmName());
    		}else if("UBLK".equals(gmType)) {
    	    	gatePassMainObj.setReasoning(generalMaster.getGmName());
    		}else if("BLK".equals(gmType)) {
    			if(gatePassMainObj.getGatePassAction().equals(GatePassType.BLOCK.getStatus())) {
    				gatePassMainObj.setReasoning("");
    			}
    		}
    		
    		}
    		List<CmsGeneralMaster> gmLists = workmenService.getAllGeneralMaster();

    		// Grouping the CmsGeneralMaster objects by gmType
    		Map<String, List<CmsGeneralMaster>> groupedByGmType = gmLists.stream()
    		        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

    		// Define the types and their corresponding request attribute names
    		Map<String, String> attributeMapping = Map.of(
    		        "UBLK","ublk"
    		);

    		// Iterate over the attribute mappings and set the request attributes dynamically
    		attributeMapping.forEach((type, attributeName) -> {
    		    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
    		    request.setAttribute(attributeName, gmList1);
    		});

    		

    		List<ApproverStatusDTO> approvers = new ArrayList<ApproverStatusDTO>();
    		if(gatePassMainObj.getGatePassAction().equals(GatePassType.UNBLOCK.getStatus())) {
    			approvers=	workmenService.getApprovalDetails(transactionId,gatePassMainObj.getUnitId(),GatePassType.UNBLOCK.getStatus());
    		}
    		request.setAttribute("approvers", approvers); 
    		 
    	}catch(Exception e) {
    		log.error("Error getting workmen details ", e);
    	}
    	log.info("Exiting from viewIndividualContractWorkmenDetails: "+transactionId);
    	
    		return "contractWorkmen/unblockView";
    	
    }
    @GetMapping("/blackview/{gatePassId}/{mode}")
    public String blackviewIndividualContractWorkmenDetails(@PathVariable("gatePassId") String gatePassId,
    		@PathVariable("mode") String mode,HttpServletRequest request,HttpServletResponse response) {
    	log.info("Entered into viewIndividualContractWorkmenDetails: "+gatePassId);
    	GatePassMain gatePassMainObj =null;String transactionId=null;

    	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    	try {
    		if(mode.equals("add")){
      			 transactionId= workmenService.generateTransactionId();
      		}else {
      			transactionId=workmenService.getTransactionIdByGPId(gatePassId, GatePassType.BLACKLIST.getStatus());
      		}
       		gatePassMainObj = workmenService.getIndividualContractWorkmenDetailsByGatePassId(gatePassId);
       		String oldTransactionId = gatePassMainObj.getTransactionId();
       		gatePassMainObj.setTransactionId(transactionId);
    		request.setAttribute("GatePassObj", gatePassMainObj);
    		 request.setAttribute("mode", mode);
    		// String oldTransactionId=workmenDao.getTransactionIdByGatePassId(gatePassId);
    		 gatePassMainObj.setOldTransactionId(oldTransactionId);
    		 String renewTrans = workmenService.getRenewTransactionIfExists(gatePassId);
    		 if(null!=renewTrans) {
    			 oldTransactionId=renewTrans;
    			 gatePassMainObj.setOldTransactionId(oldTransactionId);
    		 }
    		 if(null != gatePassMainObj.getPhotoName()) {
           		 String profilePicFilePath =  "/imageinline/"+user.getUserId()+"/" + oldTransactionId + "/" +gatePassMainObj.getPhotoName();
           		 request.setAttribute("imagePath", profilePicFilePath);
           		}
    		 
    		//Get All GeneralMaster
    		List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMasterForGatePass(gatePassMainObj);
    		for (CmsGeneralMaster generalMaster : gmList) {
    		    String gmType = generalMaster.getGmType();
    		if ("GENDER".equals(gmType)) {
    	        gatePassMainObj.setGender(generalMaster.getGmName()); 
    	    } else if ("BLOODGROUP".equals(gmType)) {
    	        gatePassMainObj.setBloodGroup(generalMaster.getGmName()); 
    	    } else if ("ACADEMICS".equals(gmType)) {
    	        gatePassMainObj.setAcademic(generalMaster.getGmName()); 
    	    } else if ("ZONE".equals(gmType)) {
    	        gatePassMainObj.setZone(generalMaster.getGmName()); 
    	    } else if ("ACCESSAREA".equals(gmType)) {
    	        gatePassMainObj.setAccessArea(generalMaster.getGmName()); 
    	    } else if ("WAGECATEGORY".equals(gmType)) {
    	        gatePassMainObj.setWageCategory(generalMaster.getGmName()); 
    	    } else if ("BONUSPAYOUT".equals(gmType)) {
    	        gatePassMainObj.setBonusPayout(generalMaster.getGmName()); 
    	    } else if("DEPARTMENT".equals(gmType)){
    	    	gatePassMainObj.setDepartment(generalMaster.getGmName());
    	    } else if("AREA".equals(gmType)) {
    	    	gatePassMainObj.setSubdepartment(generalMaster.getGmName());
    	    }else if("TRADE".equals(gmType)){
    	    	gatePassMainObj.setTrade(generalMaster.getGmName());
    	    } else if("SKILL".equals(gmType)) {
    	    	gatePassMainObj.setSkill(generalMaster.getGmName());
    		}else if("WORKMENTYPE".equals(gmType)) {
    	    	gatePassMainObj.setWorkmenType(generalMaster.getGmName());
    		}else if("BL".equals(gmType)) {
    	    	gatePassMainObj.setReasoning(generalMaster.getGmName());
    		}
    		}
    		List<CmsGeneralMaster> gmLists = workmenService.getAllGeneralMaster();

    		// Grouping the CmsGeneralMaster objects by gmType
    		Map<String, List<CmsGeneralMaster>> groupedByGmType = gmLists.stream()
    		        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

    		// Define the types and their corresponding request attribute names
    		Map<String, String> attributeMapping = Map.of(
    		        "BL","bl"
    		);

    		// Iterate over the attribute mappings and set the request attributes dynamically
    		attributeMapping.forEach((type, attributeName) -> {
    		    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
    		    request.setAttribute(attributeName, gmList1);
    		});
    		

    		List<ApproverStatusDTO> approvers = new ArrayList<ApproverStatusDTO>();
    		if(gatePassMainObj.getGatePassAction().equals(GatePassType.BLACKLIST.getStatus())) {
    			approvers=	workmenService.getApprovalDetails(transactionId,gatePassMainObj.getUnitId(),GatePassType.BLACKLIST.getStatus());
    		}
    		request.setAttribute("approvers", approvers); 
    	}catch(Exception e) {
    		log.error("Error getting workmen details ", e);
    	}
    	log.info("Exiting from viewIndividualContractWorkmenDetails: "+transactionId);
    	
    		return "contractWorkmen/blackView";
    	
    }
    
    @GetMapping("/deblackview/{gatePassId}/{mode}")
    public String deblackviewIndividualContractWorkmenDetails(@PathVariable("gatePassId") String gatePassId,
    		@PathVariable("mode") String mode,
    		HttpServletRequest request,HttpServletResponse response) {
    	log.info("Entered into viewIndividualContractWorkmenDetails: "+gatePassId);
    	GatePassMain gatePassMainObj =null;String transactionId=null;

    	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    	try {
    		if(mode.equals("add")){
     			 transactionId= workmenService.generateTransactionId();
     		}else {
     			transactionId=workmenService.getTransactionIdByGPId(gatePassId, GatePassType.DEBLACKLIST.getStatus());
     		}
      		gatePassMainObj = workmenService.getIndividualContractWorkmenDetailsByGatePassId(gatePassId);
      		String oldTransactionId = gatePassMainObj.getTransactionId();
      		gatePassMainObj.setTransactionId(transactionId);
    		request.setAttribute("GatePassObj", gatePassMainObj);
          
    		 request.setAttribute("mode", mode);
    		 //String oldTransactionId=workmenDao.getTransactionIdByGatePassId(gatePassId);
    		 gatePassMainObj.setOldTransactionId(oldTransactionId);
    		 String renewTrans = workmenService.getRenewTransactionIfExists(gatePassId);
    		 if(null!=renewTrans) {
    			 oldTransactionId=renewTrans;
    			 gatePassMainObj.setOldTransactionId(oldTransactionId);
    		 }
    		 if(null != gatePassMainObj.getPhotoName()) {
           		 String profilePicFilePath =  "/imageinline/"+user.getUserId()+"/" + oldTransactionId + "/" +gatePassMainObj.getPhotoName();
           		 request.setAttribute("imagePath", profilePicFilePath);
           		}
    		 
    		//Get All GeneralMaster
    		List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMasterForGatePass(gatePassMainObj);
    		for (CmsGeneralMaster generalMaster : gmList) {
    		    String gmType = generalMaster.getGmType();
    		if ("GENDER".equals(gmType)) {
    	        gatePassMainObj.setGender(generalMaster.getGmName()); 
    	    } else if ("BLOODGROUP".equals(gmType)) {
    	        gatePassMainObj.setBloodGroup(generalMaster.getGmName()); 
    	    } else if ("ACADEMICS".equals(gmType)) {
    	        gatePassMainObj.setAcademic(generalMaster.getGmName()); 
    	    } else if ("ZONE".equals(gmType)) {
    	        gatePassMainObj.setZone(generalMaster.getGmName()); 
    	    } else if ("ACCESSAREA".equals(gmType)) {
    	        gatePassMainObj.setAccessArea(generalMaster.getGmName()); 
    	    } else if ("WAGECATEGORY".equals(gmType)) {
    	        gatePassMainObj.setWageCategory(generalMaster.getGmName()); 
    	    } else if ("BONUSPAYOUT".equals(gmType)) {
    	        gatePassMainObj.setBonusPayout(generalMaster.getGmName()); 
    	    } else if("DEPARTMENT".equals(gmType)){
    	    	gatePassMainObj.setDepartment(generalMaster.getGmName());
    	    } else if("AREA".equals(gmType)) {
    	    	gatePassMainObj.setSubdepartment(generalMaster.getGmName());
    	    }else if("TRADE".equals(gmType)){
    	    	gatePassMainObj.setTrade(generalMaster.getGmName());
    	    } else if("SKILL".equals(gmType)) {
    	    	gatePassMainObj.setSkill(generalMaster.getGmName());
    		}else if("WORKMENTYPE".equals(gmType)) {
    	    	gatePassMainObj.setWorkmenType(generalMaster.getGmName());
    		}else if("DBL".equals(gmType)) {
    	    	gatePassMainObj.setReasoning(generalMaster.getGmName());
    		}else if("BL".equals(gmType)) {
    			if(gatePassMainObj.getGatePassAction().equals(GatePassType.BLACKLIST.getStatus())) {
    				gatePassMainObj.setReasoning("");
    			}
    		}
    		}
    		List<CmsGeneralMaster> gmLists = workmenService.getAllGeneralMaster();

    		// Grouping the CmsGeneralMaster objects by gmType
    		Map<String, List<CmsGeneralMaster>> groupedByGmType = gmLists.stream()
    		        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

    		// Define the types and their corresponding request attribute names
    		Map<String, String> attributeMapping = Map.of(
    		        "DBL","dbl"
    		);

    		// Iterate over the attribute mappings and set the request attributes dynamically
    		attributeMapping.forEach((type, attributeName) -> {
    		    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
    		    request.setAttribute(attributeName, gmList1);
    		});

    		List<ApproverStatusDTO> approvers = new ArrayList<ApproverStatusDTO>();
    		if(gatePassMainObj.getGatePassAction().equals(GatePassType.DEBLACKLIST.getStatus())) {
    			approvers=	workmenService.getApprovalDetails(transactionId,gatePassMainObj.getUnitId(),GatePassType.DEBLACKLIST.getStatus());
    		}
    		request.setAttribute("approvers", approvers); 
    		 
    	}catch(Exception e) {
    		log.error("Error getting workmen details ", e);
    	}
    	log.info("Exiting from viewIndividualContractWorkmenDetails: "+transactionId);
    	
    		return "contractWorkmen/deblackView";
    	
    }
    @GetMapping("/lostordamageview/{gatePassId}/{mode}")
    public String lostordamageviewIndividualContractWorkmenDetails(@PathVariable("gatePassId") String gatePassId,
    		    		@PathVariable("mode") String mode,HttpServletRequest request,HttpServletResponse response) {
    	log.info("Entered into viewIndividualContractWorkmenDetails: "+gatePassId);
    	GatePassMain gatePassMainObj =null;String transactionId=null;

    	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    	try {
    		if(mode.equals("add")){
     			 transactionId= workmenService.generateTransactionId();
     		}else {
     			transactionId=workmenService.getTransactionIdByGPId(gatePassId, GatePassType.LOSTORDAMAGE.getStatus());
     		}
      		gatePassMainObj = workmenService.getIndividualContractWorkmenDetailsByGatePassId(gatePassId);
      		String oldTransactionId = gatePassMainObj.getTransactionId();
      		gatePassMainObj.setTransactionId(transactionId);
    		request.setAttribute("GatePassObj", gatePassMainObj);

    		 request.setAttribute("mode", mode);
    		 //String oldTransactionId=workmenDao.getTransactionIdByGatePassId(gatePassId);
    		 gatePassMainObj.setOldTransactionId(oldTransactionId);
    		 String renewTrans = workmenService.getRenewTransactionIfExists(gatePassId);
    		 if(null!=renewTrans) {
    			 oldTransactionId=renewTrans;
    			 gatePassMainObj.setOldTransactionId(oldTransactionId);
    		 }
    		 if(null != gatePassMainObj.getPhotoName()) {
           		 String profilePicFilePath =  "/imageinline/"+user.getUserId()+"/" + oldTransactionId + "/" +gatePassMainObj.getPhotoName();
           		 request.setAttribute("imagePath", profilePicFilePath);
           		}
    		 
    		//Get All GeneralMaster
    		List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMasterForGatePass(gatePassMainObj);
    		for (CmsGeneralMaster generalMaster : gmList) {
    		    String gmType = generalMaster.getGmType();
    		if ("GENDER".equals(gmType)) {
    	        gatePassMainObj.setGender(generalMaster.getGmName()); 
    	    } else if ("BLOODGROUP".equals(gmType)) {
    	        gatePassMainObj.setBloodGroup(generalMaster.getGmName()); 
    	    } else if ("ACADEMICS".equals(gmType)) {
    	        gatePassMainObj.setAcademic(generalMaster.getGmName()); 
    	    } else if ("ZONE".equals(gmType)) {
    	        gatePassMainObj.setZone(generalMaster.getGmName()); 
    	    } else if ("ACCESSAREA".equals(gmType)) {
    	        gatePassMainObj.setAccessArea(generalMaster.getGmName()); 
    	    } else if ("WAGECATEGORY".equals(gmType)) {
    	        gatePassMainObj.setWageCategory(generalMaster.getGmName()); 
    	    } else if ("BONUSPAYOUT".equals(gmType)) {
    	        gatePassMainObj.setBonusPayout(generalMaster.getGmName()); 
    	    } else if("DEPARTMENT".equals(gmType)){
    	    	gatePassMainObj.setDepartment(generalMaster.getGmName());
    	    } else if("AREA".equals(gmType)) {
    	    	gatePassMainObj.setSubdepartment(generalMaster.getGmName());
    	    }else if("TRADE".equals(gmType)){
    	    	gatePassMainObj.setTrade(generalMaster.getGmName());
    	    } else if("SKILL".equals(gmType)) {
    	    	gatePassMainObj.setSkill(generalMaster.getGmName());
    		}else if("WORKMENTYPE".equals(gmType)) {
    	    	gatePassMainObj.setWorkmenType(generalMaster.getGmName());
    		}
    		}
    		
    		 
    	}catch(Exception e) {
    		log.error("Error getting workmen details ", e);
    	}
    	log.info("Exiting from viewIndividualContractWorkmenDetails: "+transactionId);
    	
    		return "contractWorkmen/lostView";
    	
    }
    
    @PostMapping("/uploadImage")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "No file selected or file is empty.";
        }

        try {
                String uploadPath = ROOT_DIRECTORY + "user01/GP1234/";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String filePath = uploadPath + File.separator + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            return "File uploaded successfully: " + file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error uploading file: " + e.getMessage();
        }
    }
    
    @PostMapping("/draftGatePass")
    public ResponseEntity<String> draftGatePass(
            @RequestParam("jsonData") String jsonData,
            @RequestParam(value = "aadharFile", required = false) MultipartFile aadharFile,
            @RequestParam(value = "policeFile", required = false) MultipartFile policeFile,
            @RequestParam(value = "profilePic", required = false) MultipartFile profilePic,
            @RequestParam(value = "appointmentFile", required = false) MultipartFile appointmentFile,
            @RequestParam(value = "additionalFiles", required = false) List<MultipartFile> additionalFiles,
            @RequestParam(value = "documentTypes", required = false) List<String> documentTypes,
            HttpServletRequest request, HttpServletResponse response) {
    	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

        String transactionId = null;
        GatePassMain gatePassMain;

        try {
            // Convert the JSON string back to the GatePassMain object
            ObjectMapper objectMapper = new ObjectMapper();
            gatePassMain = objectMapper.readValue(jsonData, GatePassMain.class);
            
            // Log the received GatePassMain object
            log.info("Received GatePassMain: {}", gatePassMain);

            gatePassMain.setCreatedBy(String.valueOf(user.getUserId()));
            gatePassMain.setAadharDocName(aadharFile != null && !aadharFile.isEmpty() ? "aadhar":"");
            gatePassMain.setPoliceVerificationDocName(policeFile!=null && !policeFile.isEmpty() ? "police":"");
            gatePassMain.setPhotoName(profilePic!=null && !profilePic.isEmpty()?profilePic.getOriginalFilename():"");
            gatePassMain.setAppointmentDocName(appointmentFile!=null && !appointmentFile.isEmpty() ? "appointment":"");
            // Mapping document types to their corresponding setter methods
            Map<String, Consumer<String>> docTypeSetterMap = new HashMap<>();
            docTypeSetterMap.put("Bank", gatePassMain::setBankDocName);
            docTypeSetterMap.put("Id2", gatePassMain::setIdProof2DocName);
            docTypeSetterMap.put("Other", gatePassMain::setOtherDocName);
            docTypeSetterMap.put("Medical", gatePassMain::setMedicalDocName);
            docTypeSetterMap.put("Education", gatePassMain::setEducationDocName);
            docTypeSetterMap.put("Training", gatePassMain::setTrainingDocName);
            docTypeSetterMap.put("Form11", gatePassMain::setForm11DocName);
            if(additionalFiles != null && !additionalFiles.isEmpty()) {
            // Set document names based on additionalFiles and documentTypes
            for (int i = 0; i < additionalFiles.size(); i++) {
                String docType = documentTypes.get(i);
                if (docType != null) {
                    Consumer<String> setter = docTypeSetterMap.get(docType);
                    if (setter != null) {
                        setter.accept(docType);
                    }
                }
            }
            }
            transactionId = workmenService.draftGatePass(gatePassMain);

            if (transactionId != null) {
//                if (aadharFile != null && !aadharFile.isEmpty() && policeFile!=null && !policeFile.isEmpty()) {
//                    uploadDocuments(aadharFile, policeFile,profilePic, String.valueOf(user.getUserId()), gatePassId);
//                }
//                // Upload additional files
//                if (additionalFiles != null && documentTypes != null) {
//                    uploadAdditionalDocuments(additionalFiles, documentTypes, String.valueOf(user.getUserId()), gatePassId);
//                }
            	request.setAttribute("SUCCESS_MSG", "Gatepass drafted sucessfully.");
                return new ResponseEntity<>("contractWorkmen/list", HttpStatus.OK);
            }
            request.setAttribute("FAILED_MSG", "Failed to draft gatepass.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            
        } catch (Exception e) {
            log.error("Error saving data: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error saving data: " + e.getMessage());
        }
    }
  



    @GetMapping("/getDraftDetails/{transactionId}")
    public String getDraftDetails(@PathVariable("transactionId") String transactionId,HttpServletRequest request,HttpServletResponse response) {
    	log.info("Entered into viewIndividualContractWorkmenDetails: "+transactionId);

	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
    MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
	log.info("Entered into getDraftGatePass"+user.getUserId());
	
	//String transactionId= workmenService.generateTransactionId();
	//request.setAttribute("transactionId", transactionId);
	
	List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
	//List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
	//List<PersonOrgLevel> subdepartments = groupedByLevelDef.getOrDefault("Area", new ArrayList<>());
	request.setAttribute("PrincipalEmployer", peList);
	 // request.setAttribute("Dept", departments);
     // request.setAttribute("Subdept", subdepartments);
      
    //Skills
	//List<Skill> skillList = workmenService.getAllSkill();
	//request.setAttribute("Skills", skillList);
	
	List<CmsGeneralMaster> gmList2 = workmenService.getAllGeneralMaster();

	// Grouping the CmsGeneralMaster objects by gmType
	Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList2.stream()
	        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

	// Define the types and their corresponding request attribute names
	Map<String, String> attributeMapping = Map.of(
	        "GENDER", "GenderOptions",
	        "BLOODGROUP", "BloodGroup",
	        "ACCESSAREA", "AccessArea",
	        "ACADEMICS", "Academics",
	        "WAGECATEGORY", "WageCategory",
	        "BONUSPAYOUT", "BonusPayout",
	        "ZONE", "Zone",
	        "WORKMENTYPE","WorkmenType"
	        
	);

	// Iterate over the attribute mappings and set the request attributes dynamically
	attributeMapping.forEach((type, attributeName) -> {
	    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
	    request.setAttribute(attributeName, gmList1);
	});

	GatePassMain gatePassMainObj = workmenService.getIndividualContractWorkmenDraftDetails(transactionId);
	request.setAttribute("transactionId", gatePassMainObj.getTransactionId());
	request.setAttribute("GatePassObj", gatePassMainObj);
	
	List<Contractor> contractors= workmenService.getAllContractorBasedOnPE(gatePassMainObj.getUnitId(),user.getUserAccount());
	request.setAttribute("Contractors", contractors);	
	 List<Workorder> workorders = workmenService.getAllWorkordersBasedOnPEAndContractor(gatePassMainObj.getUnitId(),gatePassMainObj.getContractor());
	 request.setAttribute("Workorders", workorders);
	 List<Trade> trades = workmenService.getAllTradesBasedOnPE(gatePassMainObj.getUnitId());
	 request.setAttribute("Trades", trades);
	 List<MasterUser> eicList = workmenService.getAllEicManager(gatePassMainObj.getUnitId(),gatePassMainObj.getDepartment());
	 request.setAttribute("Eic", eicList);
	 List<CmsContractorWC> wcs = workmenService.getAllWCBasedOnPEAndCont(gatePassMainObj.getUnitId(),gatePassMainObj.getContractor(),gatePassMainObj.getWorkorder());
	 request.setAttribute("Wcs", wcs);
	 
	 List<DeptMapping> skills = workmenService.getAllSkills(gatePassMainObj.getUnitId(),gatePassMainObj.getTrade());
	 request.setAttribute("Skills", skills);
	 List<DeptMapping> departments = workmenService.getAllDepartmentsOnPE(gatePassMainObj.getUnitId());
	 
	 List<PersonOrgLevel> dept = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
 	List<PersonOrgLevel> subdepartments = groupedByLevelDef.getOrDefault("Area", new ArrayList<>());
 	List<DeptMapping> loggedDept = new ArrayList<>();
	for(PersonOrgLevel p : dept) {
		DeptMapping d = new DeptMapping();
		d.setDepartment(p.getDescription());
		d.setDepartmentId(Integer.parseInt(p.getId()));
		loggedDept.add(d);
	}

	

	// Build Set<String> of logged dept IDs
	Set<String> loggedDeptIds = dept.stream()
	        .map(PersonOrgLevel::getId) // keep as String
	        .collect(Collectors.toSet());

	// Filter departments
	Set<DeptMapping> depSet = departments.stream()
	        .filter(d -> loggedDeptIds.contains(String.valueOf(d.getDepartmentId())))
	        .collect(Collectors.toSet());

	 request.setAttribute("Departments", depSet);
	 List<DeptMapping> Subdept = workmenService.getAllSubDepartments(gatePassMainObj.getUnitId(),gatePassMainObj.getDepartment());
	 
	 List<DeptMapping> loggedSubDept = new ArrayList<>();
  	for(PersonOrgLevel p : subdepartments) {
			DeptMapping d = new DeptMapping();
			d.setDepartment(p.getDescription());
			d.setDepartmentId(Integer.parseInt(p.getId()));
			loggedSubDept.add(d);
		}
  	
 // Build Set<String> of logged dept IDs
		Set<String> loggedSubDeptIds = subdepartments.stream()
		        .map(PersonOrgLevel::getId) // keep as String
		        .collect(Collectors.toSet());

		// Filter departments
		Set<DeptMapping> subdepSet = Subdept.stream()
		        .filter(d -> loggedSubDeptIds.contains(String.valueOf(d.getSubDepartmentId())))
		        .collect(Collectors.toSet());

	 request.setAttribute("Subdept", subdepSet);
	 
    return "contractWorkmen/quickOBAdd";
}
    
    @GetMapping("/renewFilter")
   	public String renewFilter(HttpServletRequest request, HttpServletResponse response) {
   		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
   		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
   	
   		
   		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
       	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
       			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
       	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
       	List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
       	request.setAttribute("principalEmployers", peList);
       	  request.setAttribute("Dept", departments);
       	  
       	 CMSRoleRights rr =new CMSRoleRights();
         rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/renewFilter");
    	   
    	    request.setAttribute("UserPermission", rr);
    	    
   		return "contractWorkmen/renewListing";
   	}
    
    @PostMapping("/renewList")
    public ResponseEntity<List<GatePassListingDto>> renewList(
    		@RequestParam(value = "principalEmployerId", required = false) String principalEmployerId,
    		@RequestParam(value = "deptId", required = false) String deptId,
    		HttpServletRequest request,HttpServletResponse response) {
    	
    	try {
			HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
			MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
			List<GatePassListingDto> listDto = new ArrayList<GatePassListingDto>();
			if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    		//write union for renewal pending and renewed
				listDto= workmenService.getRenewListingDetails( String.valueOf(user.getUserId()), GatePassType.CREATE.getStatus(), GatePassStatus.APPROVED.getStatus(), deptId, principalEmployerId) ;
    		}else {	
    			listDto = workmenService.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.RENEW.getStatus());
    		}	
				if (listDto.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
			
			return new ResponseEntity<>(listDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @GetMapping("/renew/{gatePassId}")
    public String renew(@PathVariable("gatePassId") String gatePassId,HttpServletRequest request,HttpServletResponse response) {
    	log.info("Entered into viewIndividualContractWorkmenDetails: "+gatePassId);

	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
    MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
	log.info("Entered into getDraftGatePass"+user.getUserId());
	
	//String transactionId= workmenService.generateTransactionId();
	//request.setAttribute("transactionId", transactionId);
	
	List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
	//List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
	//List<PersonOrgLevel> subdepartments = groupedByLevelDef.getOrDefault("Area", new ArrayList<>());
	request.setAttribute("PrincipalEmployer", peList);
	 // request.setAttribute("Dept", departments);
     // request.setAttribute("Subdept", subdepartments);
      
    //Skills
	//List<Skill> skillList = workmenService.getAllSkill();
	//request.setAttribute("Skills", skillList);
	
	List<CmsGeneralMaster> gmList2 = workmenService.getAllGeneralMaster();

	// Grouping the CmsGeneralMaster objects by gmType
	Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList2.stream()
	        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

	// Define the types and their corresponding request attribute names
	Map<String, String> attributeMapping = Map.of(
	        "GENDER", "GenderOptions",
	        "BLOODGROUP", "BloodGroup",
	        "ACCESSAREA", "AccessArea",
	        "ACADEMICS", "Academics",
	        "WAGECATEGORY", "WageCategory",
	        "BONUSPAYOUT", "BonusPayout",
	        "ZONE", "Zone",
	        "WORKMENTYPE","WorkmenType"
	);

	// Iterate over the attribute mappings and set the request attributes dynamically
	attributeMapping.forEach((type, attributeName) -> {
	    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
	    request.setAttribute(attributeName, gmList1);
	});

	
	GatePassMain gpm = workmenService.getIndividualContractWorkmenDetailsByGatePassIdRenew(gatePassId);
	GatePassMain gatePassMainObj = workmenService.getIndividualContractWorkmenDraftDetails(gpm.getTransactionId());
	String transactionId= workmenService.generateTransactionId();
	gpm.setTransactionId(transactionId);
	request.setAttribute("transactionId", transactionId);
	request.setAttribute("GatePassObj", gpm);
	
	List<Contractor> contractors= workmenService.getAllContractorBasedOnPE(gatePassMainObj.getUnitId(),user.getUserAccount());
	request.setAttribute("Contractors", contractors);	
	 List<Workorder> workorders = workmenService.getAllWorkordersBasedOnPEAndContractor(gatePassMainObj.getUnitId(),gatePassMainObj.getContractor());
	 request.setAttribute("Workorders", workorders);
	 List<Trade> trades = workmenService.getAllTradesBasedOnPE(gatePassMainObj.getUnitId());
	 request.setAttribute("Trades", trades);
	 List<MasterUser> eicList = workmenService.getAllEicManager(gatePassMainObj.getUnitId(),gatePassMainObj.getDepartment());
	 request.setAttribute("Eic", eicList);
	 List<CmsContractorWC> wcs = workmenService.getAllWCBasedOnPEAndCont(gatePassMainObj.getUnitId(),gatePassMainObj.getContractor(),gatePassMainObj.getWorkorder());
	 request.setAttribute("Wcs", wcs);
	 
	 List<DeptMapping> skills = workmenService.getAllSkills(gatePassMainObj.getUnitId(),gatePassMainObj.getTrade());
	 request.setAttribute("Skills", skills);
//	 List<DeptMapping> departments = workmenService.getAllDepartmentsOnPE(gatePassMainObj.getUnitId());
//	 request.setAttribute("Departments", departments);
//	 List<DeptMapping> Subdept = workmenService.getAllSubDepartments(gatePassMainObj.getUnitId(),gatePassMainObj.getDepartment());
//	 request.setAttribute("Subdept", Subdept);
	 
    List<DeptMapping> departments = workmenService.getAllDepartmentsOnPE(gatePassMainObj.getUnitId());
	 
	 List<PersonOrgLevel> dept = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
 	List<PersonOrgLevel> subdepartments = groupedByLevelDef.getOrDefault("Area", new ArrayList<>());
 	List<DeptMapping> loggedDept = new ArrayList<>();
	for(PersonOrgLevel p : dept) {
		DeptMapping d = new DeptMapping();
		d.setDepartment(p.getDescription());
		d.setDepartmentId(Integer.parseInt(p.getId()));
		loggedDept.add(d);
	}

	

	// Build Set<String> of logged dept IDs
	Set<String> loggedDeptIds = dept.stream()
	        .map(PersonOrgLevel::getId) // keep as String
	        .collect(Collectors.toSet());

	// Filter departments
	Set<DeptMapping> depSet = departments.stream()
	        .filter(d -> loggedDeptIds.contains(String.valueOf(d.getDepartmentId())))
	        .collect(Collectors.toSet());

	 request.setAttribute("Departments", depSet);
	 List<DeptMapping> Subdept = workmenService.getAllSubDepartments(gatePassMainObj.getUnitId(),gatePassMainObj.getDepartment());
	 
	 List<DeptMapping> loggedSubDept = new ArrayList<>();
  	for(PersonOrgLevel p : subdepartments) {
			DeptMapping d = new DeptMapping();
			d.setDepartment(p.getDescription());
			d.setDepartmentId(Integer.parseInt(p.getId()));
			loggedSubDept.add(d);
		}
  	
 // Build Set<String> of logged dept IDs
		Set<String> loggedSubDeptIds = subdepartments.stream()
		        .map(PersonOrgLevel::getId) // keep as String
		        .collect(Collectors.toSet());

		// Filter departments
		Set<DeptMapping> subdepSet = Subdept.stream()
		        .filter(d -> loggedSubDeptIds.contains(String.valueOf(d.getSubDepartmentId())))
		        .collect(Collectors.toSet());

	 request.setAttribute("Subdept", subdepSet);
	 String oldTransactionId=workmenDao.getTransactionIdByGatePassId(gatePassId);
	 if(null != gatePassMainObj.getPhotoName()) {
   		 String profilePicFilePath =  "/imageinline/"+user.getUserId()+"/" + oldTransactionId + "/" +gatePassMainObj.getPhotoName();
   		 request.setAttribute("imagePath", profilePicFilePath);
   		}
		 
	 //List<Map<String, Object>> allVersionedDocs = workmenService.getAllVersionedDocuments(oldTransactionId, user.getUserId());

     // ✅ Pass versioned documents to JSP
     //request.setAttribute("PreviousDocuments", allVersionedDocs);


    return "contractWorkmen/renew";
}
    
    @PostMapping("/renewGatePass")
    public ResponseEntity<String> renewGatePass(
            @RequestParam("jsonData") String jsonData,
            @RequestParam(value = "aadharFile", required = false) MultipartFile aadharFile,
            @RequestParam(value = "policeFile", required = false) MultipartFile policeFile,
            @RequestParam(value = "profilePic", required = false) MultipartFile profilePic,
            @RequestParam(value = "appointmentFile", required = false) MultipartFile appointmentFile,
            @RequestParam(value = "additionalFiles", required = false) List<MultipartFile> additionalFiles,
            @RequestParam(value = "documentTypes", required = false) List<String> documentTypes,
            HttpServletRequest request, HttpServletResponse response) {
    	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

        String transactionId = null;
        GatePassMain gatePassMain;

        try {
            // Convert the JSON string back to the GatePassMain object
            ObjectMapper objectMapper = new ObjectMapper();
            gatePassMain = objectMapper.readValue(jsonData, GatePassMain.class);
            
            // Log the received GatePassMain object
            log.info("Received GatePassMain: {}", gatePassMain);

            gatePassMain.setCreatedBy(String.valueOf(user.getUserId()));
            gatePassMain.setAadharDocName(aadharFile != null && !aadharFile.isEmpty() ? "aadhar":"");
            gatePassMain.setPoliceVerificationDocName(policeFile!=null && !policeFile.isEmpty() ? "police":"");
            gatePassMain.setPhotoName(profilePic!=null && !profilePic.isEmpty()?profilePic.getOriginalFilename():"");
            gatePassMain.setAppointmentDocName(appointmentFile!=null && !appointmentFile.isEmpty() ? "appointment":"");
         // Mapping document types to their corresponding setter methods
            Map<String, Consumer<String>> docTypeSetterMap = new HashMap<>();
            docTypeSetterMap.put("Bank", gatePassMain::setBankDocName);
            docTypeSetterMap.put("Id2", gatePassMain::setIdProof2DocName);
            docTypeSetterMap.put("Other", gatePassMain::setOtherDocName);
            docTypeSetterMap.put("Medical", gatePassMain::setMedicalDocName);
            docTypeSetterMap.put("Education", gatePassMain::setEducationDocName);
            docTypeSetterMap.put("Training", gatePassMain::setTrainingDocName);
            docTypeSetterMap.put("Form11", gatePassMain::setForm11DocName);
            if(additionalFiles != null && !additionalFiles.isEmpty()) {
            // Set document names based on additionalFiles and documentTypes
            for (int i = 0; i < additionalFiles.size(); i++) {
                String docType = documentTypes.get(i);
                if (docType != null) {
                    Consumer<String> setter = docTypeSetterMap.get(docType);
                    if (setter != null) {
                        setter.accept(docType);
                    }
                }
            }
            }
            transactionId = workmenService.renewGatePass(gatePassMain);
            if (transactionId != null) {

            	// String oldTransactionId=workmenDao.getTransactionIdByGatePassId(gatePassMain.getGatePassId());
            	 if (aadharFile != null && !aadharFile.isEmpty() && policeFile!=null && !policeFile.isEmpty() && appointmentFile!=null && !appointmentFile.isEmpty()) {
                     uploadDocuments(aadharFile, policeFile,profilePic,appointmentFile, String.valueOf(user.getUserId()), transactionId);
                 }
                 // Upload additional files
                 if (additionalFiles != null && documentTypes != null) {
                     uploadAdditionalDocuments(additionalFiles, documentTypes, String.valueOf(user.getUserId()), transactionId);
                 }
                return new ResponseEntity<>("contractWorkmen/renewList", HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            
        } catch (Exception e) {
            log.error("Error saving data: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error saving data: " + e.getMessage());
        }
    }
    
    @GetMapping("/renewview/{gatePassId}")
    public String renewviewIndividualContractWorkmenDetails(@PathVariable("gatePassId") String gatePassId,HttpServletRequest request,HttpServletResponse response) {
    	log.info("Entered into viewIndividualContractWorkmenDetails: "+gatePassId);
    	GatePassMain gatePassMainObj =null;
    	String transactionId=null;
    	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    	try {

    		gatePassMainObj = workmenService.getIndividualContractWorkmenDetailsByGatePassId(gatePassId);
    		String oldTransactionId=gatePassMainObj.getTransactionId();
   		 	gatePassMainObj.setOldTransactionId(oldTransactionId);
    		transactionId=workmenService.getTransactionIdByGPId(gatePassId, GatePassType.RENEW.getStatus());
    		gatePassMainObj.setTransactionId(transactionId);
    		request.setAttribute("GatePassObj", gatePassMainObj);
    		 
    		 
    		 if(null != gatePassMainObj.getPhotoName()) {
    			 
           		 String profilePicFilePath =null;
           		 if(GatePassType.CREATE.getStatus().equals(gatePassMainObj.getGatePassAction()))
           			 profilePicFilePath =  "/imageinline/"+user.getUserId()+"/" + oldTransactionId + "/" +gatePassMainObj.getPhotoName();
           		 else
           			 profilePicFilePath =  "/imageinline/"+user.getUserId()+"/" + transactionId + "/" +gatePassMainObj.getPhotoName();
           		 request.setAttribute("imagePath", profilePicFilePath);
           		}
    		
    		//Get All GeneralMaster
    		List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMasterForGatePass(gatePassMainObj);
    		for (CmsGeneralMaster generalMaster : gmList) {
    		    String gmType = generalMaster.getGmType();
    		if ("GENDER".equals(gmType)) {
    	        gatePassMainObj.setGender(generalMaster.getGmName()); 
    	    } else if ("BLOODGROUP".equals(gmType)) {
    	        gatePassMainObj.setBloodGroup(generalMaster.getGmName()); 
    	    } else if ("ACADEMICS".equals(gmType)) {
    	        gatePassMainObj.setAcademic(generalMaster.getGmName()); 
    	    } else if ("ZONE".equals(gmType)) {
    	        gatePassMainObj.setZone(generalMaster.getGmName()); 
    	    } else if ("ACCESSAREA".equals(gmType)) {
    	        gatePassMainObj.setAccessArea(generalMaster.getGmName()); 
    	    } else if ("WAGECATEGORY".equals(gmType)) {
    	        gatePassMainObj.setWageCategory(generalMaster.getGmName()); 
    	    } else if ("BONUSPAYOUT".equals(gmType)) {
    	        gatePassMainObj.setBonusPayout(generalMaster.getGmName()); 
    	    } else if("DEPARTMENT".equals(gmType)){
    	    	gatePassMainObj.setDepartment(generalMaster.getGmName());
    	    } else if("AREA".equals(gmType)) {
    	    	gatePassMainObj.setSubdepartment(generalMaster.getGmName());
    	    }else if("TRADE".equals(gmType)){
    	    	gatePassMainObj.setTrade(generalMaster.getGmName());
    	    } else if("SKILL".equals(gmType)) {
    	    	gatePassMainObj.setSkill(generalMaster.getGmName());
    		}else if("WORKMENTYPE".equals(gmType)) {
    	    	gatePassMainObj.setWorkmenType(generalMaster.getGmName());
    		}
    		}
    		
    		 // ✅ Pass versioned documents to JSP
//    		 List<Map<String, Object>> allVersionedDocs = workmenService.getAllVersionedDocuments(transactionId, user.getUserId());
//    	     request.setAttribute("PreviousDocuments", allVersionedDocs);
//    	     
//    	     List<Map<String, Object>> renewDocsList=workmenDao.getRenewalDocs(transactionId);
//    	     Map<String, String> latestDocs = new HashMap<>();
//
//    	     for (Map<String, Object> doc : renewDocsList) {
//    	         String docType = doc.get("DOCTYPE").toString();   // PHOTO
//    	         String fileName = doc.get("FILENAME").toString(); // photo_V2.jpg
//
//    	         latestDocs.put(docType, fileName);  // Store latest version filename
//    	     }
//
//    	     // Send to JSP
//    	     request.setAttribute("LatestDocs", latestDocs);

    		List<ApproverStatusDTO> approvers = new ArrayList<ApproverStatusDTO>();
    		if(gatePassMainObj.getGatePassAction().equals(GatePassType.RENEW.getStatus())) {
    			approvers=	workmenService.getApprovalDetails(transactionId,gatePassMainObj.getUnitId(),GatePassType.RENEW.getStatus());
    		}
    		request.setAttribute("approvers", approvers); 
    	     
    	}catch(Exception e) {
    		log.error("Error getting workmen details ", e);
    	}
    	log.info("Exiting from viewIndividualContractWorkmenDetails: "+gatePassId);
    	
    		return "contractWorkmen/renewView";
    	
    }

    public String getSurePassURL() {
	    return QueryFileWatcher.getQuery("INITIALIZE_URL");
	}
    
    public String getToken() {
	    return QueryFileWatcher.getQuery("INITIALIZE_TOKEN");
	}
    
    
    @PostMapping("/generateOtp")
    @ResponseBody
    public Map<String, Object> generateOtp(@RequestBody Map<String, String> requestBody, HttpSession session) {
        String aadhaarNumber = requestBody.get("aadhaarNumber");
        String surepassUrl = getSurePassURL();
        String token = getToken();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        Map<String, String> payload = new HashMap<>();
        payload.put("id_number", aadhaarNumber);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(payload, headers);

        Map<String, Object> responseMap = new HashMap<>();

        try {
            ResponseEntity<Map> responseEntity = restTemplate.exchange(surepassUrl, HttpMethod.POST, entity, Map.class);
            Map<String, Object> responseBody = responseEntity.getBody();

            Map<String, Object> dataMap = (Map<String, Object>) responseBody.get("data");

            // Store client_id in session for later use in verify OTP
            if (dataMap != null && dataMap.containsKey("client_id")) {
                session.setAttribute("aadhaarClientId", dataMap.get("client_id"));
            }

            responseMap.put("success", responseBody.get("success"));
            responseMap.put("message", responseBody.get("message"));
            responseMap.put("statusCode", responseBody.get("status_code"));
            responseMap.put("status", dataMap != null ? dataMap.get("status") : null);

        } catch (HttpClientErrorException ex) {
            try {
                Map<String, Object> errorBody = new ObjectMapper().readValue(ex.getResponseBodyAsString(), Map.class);
                Map<String, Object> dataMap = (Map<String, Object>) errorBody.get("data");

                // Store client_id even in error case (if available)
                if (dataMap != null && dataMap.containsKey("client_id")) {
                    session.setAttribute("aadhaarClientId", dataMap.get("client_id"));
                }

                responseMap.put("success", false);
                responseMap.put("message", errorBody.get("message"));
                responseMap.put("statusCode", errorBody.get("status_code"));
                responseMap.put("status", dataMap != null ? dataMap.get("status") : null);

            } catch (Exception e) {
                responseMap.put("success", false);
                responseMap.put("message", "Unable to parse error response.");
                responseMap.put("statusCode", 500);
            }
        } catch (Exception e) {
            responseMap.put("success", false);
            responseMap.put("message", "Internal server error occurred.");
            responseMap.put("statusCode", 500);
        }

        return responseMap;
    }
    @PostMapping("/verifyOtp")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody Map<String, String> requestData, HttpSession session) {
        String otp = requestData.get("otp");
        String clientId = (String) session.getAttribute("aadhaarClientId");

        Map<String, Object> result = workmenService.verifyOtpWithSurepass(clientId, otp);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/checkAadharExists")
    @ResponseBody
    public Map<String, Boolean> checkAadharExists(@RequestParam("aadharNumber") String aadharNumber,@RequestParam("transactionId")String transactionId) {
        boolean exists = workmenService.isAadharExists(aadharNumber,transactionId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return response;
    }

    //quickOnboardingList
    @GetMapping("/quickOnboardingList")
    public String quickOnboardingList(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

		
		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
    	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
    			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
    	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
    	//List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
    	
    	List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
        CMSRoleRights rr =new CMSRoleRights();
        rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/quickOnboardingList");
   	    listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
   	    request.setAttribute("UserPermission", rr);
    	request.setAttribute("principalEmployers", peList);
    	  //request.setAttribute("Dept", departments);
    	  
		return "contractWorkmen/quickOnboardingList";
	}
  //quickOnboardingList
    
   
    @GetMapping("/quickOnboardingCreation")
    public String quickOnboardingCreation(HttpServletRequest request,HttpServletResponse response) {
	
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    	log.info("Entered into addQuickOBForm"+user.getUserId());
    	
    	String transactionId= workmenService.generateTransactionId();
    	request.setAttribute("transactionId", transactionId);
    	
    	List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
    	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
    			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
    	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
    	//List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
    	//List<PersonOrgLevel> subdepartments = groupedByLevelDef.getOrDefault("Area", new ArrayList<>());
    	request.setAttribute("PrincipalEmployer", peList);
    	  //request.setAttribute("Dept", departments);
         // request.setAttribute("Subdept", subdepartments);
          
        //Skills
		//List<Skill> skillList = workmenService.getAllSkill();
		//request.setAttribute("Skills", skillList);
		
		List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMaster();

		// Grouping the CmsGeneralMaster objects by gmType
		Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
		        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

		// Define the types and their corresponding request attribute names
		Map<String, String> attributeMapping = Map.of(
		        "GENDER", "GenderOptions",
		        "BLOODGROUP", "BloodGroup",
		        "ACCESSAREA", "AccessArea",
		        "ACADEMICS", "Academics",
		        "WAGECATEGORY", "WageCategory",
		        "BONUSPAYOUT", "BonusPayout",
		        "ZONE", "Zone",
		        "WORKMENTYPE","WorkmenType"
		);

		// Iterate over the attribute mappings and set the request attributes dynamically
		attributeMapping.forEach((type, attributeName) -> {
		    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
		    request.setAttribute(attributeName, gmList1);
		});
        return "contractWorkmen/quickOnboardingCreate";
    }
    
    @GetMapping("/checkUanExists")
    @ResponseBody
    public Map<String, Object> checkUanExists(@RequestParam("uan") String uan,
                                              @RequestParam("aadharNumber") String aadharNumber) {
        Map<String, Object> response = new HashMap<>();
        String otherAadhar = workmenService.getOtherAadharLinkedToUan(uan, aadharNumber);

        if (otherAadhar != null) {
            response.put("exists", true);
            response.put("otherAadhar", otherAadhar);
        } else {
            response.put("exists", false);
        }

        return response;
    }
    @GetMapping("/checkpfNumberExists")
    @ResponseBody
    public Map<String, Object> checkpfNumberExists(@RequestParam("pfNumber") String pfNumber,
                                              @RequestParam("aadharNumber") String aadharNumber) {
        Map<String, Object> response = new HashMap<>();
        String otherAadhar = workmenService.getOtherAadharLinkedTopfNumber(pfNumber, aadharNumber);

        if (otherAadhar != null) {
            response.put("exists", true);
            response.put("otherAadhar", otherAadhar);
        } else {
            response.put("exists", false);
        }

        return response;
    }
    
    @GetMapping("/quickOnboardingListDashboardNav")
    public String quickOnboardingListDashboardNav(HttpServletRequest request, HttpServletResponse response
    		,@RequestParam(value = "principalEmployerId", required = false) String principalEmployerId,
    		@RequestParam(value = "deptId", required = false) String deptId
    		) {
    	String type="quick";
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

		
		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
    	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
    			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
    	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
    	List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
    	
    	List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
        CMSRoleRights rr =new CMSRoleRights();
        rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/quickOnboardingList");
   	    listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
   	    request.setAttribute("UserPermission", rr);
    	request.setAttribute("principalEmployers", peList);
    	request.setAttribute("Dept", departments);
    	request.setAttribute("selectedPE", principalEmployerId);
    	request.setAttribute("selectedDept", deptId);
    	  
    	List<GatePassListingDto> gplistDto = new ArrayList<GatePassListingDto>();
		if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
			gplistDto= workmenService.getGatePassListingDetails(principalEmployerId,deptId,String.valueOf(user.getUserId()),GatePassType.CREATE.getStatus(),type);
		}else {	
			gplistDto = workmenService.getGatePassListingForApprovers(principalEmployerId,deptId,user,GatePassType.CREATE.getStatus(),type);
		}
		request.setAttribute("GatePassListingDto", gplistDto);
		return "contractWorkmen/quickOnboardingList";
	}
    
    @GetMapping("/regOnboardingListDashboardNav")
    public String regOnboardingListDashboardNav(HttpServletRequest request, HttpServletResponse response
    		,@RequestParam(value = "principalEmployerId", required = false) String principalEmployerId,
    		@RequestParam(value = "deptId", required = false) String deptId) {
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

		String type="regular";
		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
    	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
    			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
    	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
    	List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
    	
    	List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
        CMSRoleRights rr =new CMSRoleRights();
        rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/list");
   	    listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
   	    request.setAttribute("UserPermission", rr);
    	request.setAttribute("principalEmployers", peList);
    	request.setAttribute("Dept", departments);
    	request.setAttribute("selectedPE", principalEmployerId);
    	request.setAttribute("selectedDept", deptId);
    	  
    	List<GatePassListingDto> gplistDto = new ArrayList<GatePassListingDto>();
		if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
			gplistDto= workmenService.getGatePassListingDetails(principalEmployerId,deptId,String.valueOf(user.getUserId()),GatePassType.CREATE.getStatus(),type);
		}else {	
			gplistDto = workmenService.getGatePassListingForApprovers(principalEmployerId,deptId,user,GatePassType.CREATE.getStatus(),type);
		}
		request.setAttribute("GatePassListingDto", gplistDto);
		return "contractWorkmen/approverList";
	}

    @GetMapping("/gatepassActionListDashboardNav")
    public String gatepassActionListDashboardNav(HttpServletRequest request, HttpServletResponse response
    		,@RequestParam(value = "principalEmployerId", required = false) String principalEmployerId,
    		@RequestParam(value = "deptId", required = false) String deptId,@RequestParam(value = "action", required = false) String action) {
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

		String result=null;
		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
    	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
    			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
    	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
    	List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
    	
    	List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
        CMSRoleRights rr =new CMSRoleRights();
        List<GatePassListingDto> gplistDto = new ArrayList<GatePassListingDto>();
        if(action.equals(GatePassType.BLOCK.getStatus())) {
        rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/blockListFilter");
		if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
			gplistDto= workmenService.getGatePassActionListingDetailsForDashboardNav(principalEmployerId,deptId,String.valueOf(user.getUserId()),GatePassType.BLOCK.getStatus());
    		
		}else {	
			gplistDto = workmenService.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.BLOCK.getStatus());
		}
		result ="contractWorkmen/blockListing";
        }else if(action.equals(GatePassType.UNBLOCK.getStatus())) {
        	rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/unblockListFilter");
    		if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    			gplistDto= workmenService.getGatePassActionListingDetailsForDashboardNav(principalEmployerId,deptId,String.valueOf(user.getUserId()),GatePassType.UNBLOCK.getStatus());
        		
    		}else {	
    			gplistDto = workmenService.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.UNBLOCK.getStatus());
    		}
    		result ="contractWorkmen/unblockListing";
        }else if(action.equals(GatePassType.BLACKLIST.getStatus())) {
        	rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/blackListFilter");
    		if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    			gplistDto= workmenService.getGatePassActionListingDetailsForDashboardNav(principalEmployerId,deptId,String.valueOf(user.getUserId()),GatePassType.BLACKLIST.getStatus());
        		
    		}else {	
    			gplistDto = workmenService.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.BLACKLIST.getStatus());
    		}
    		result ="contractWorkmen/blackListing";
        }else if(action.equals(GatePassType.DEBLACKLIST.getStatus())) {
        	rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/deblackListFilter");
    		if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    			gplistDto= workmenService.getGatePassActionListingDetailsForDashboardNav(principalEmployerId,deptId,String.valueOf(user.getUserId()),GatePassType.DEBLACKLIST.getStatus());
        		
    		}else {	
    			gplistDto = workmenService.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.DEBLACKLIST.getStatus());
    		}
    		result ="contractWorkmen/deblackListing";
        }else if(action.equals(GatePassType.CANCEL.getStatus())) {
        	rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/cancelFilter");
    		if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    			gplistDto= workmenService.getGatePassActionListingDetailsForDashboardNav(principalEmployerId,deptId,String.valueOf(user.getUserId()),GatePassType.CANCEL.getStatus());
        		
    		}else {	
    			gplistDto = workmenService.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.CANCEL.getStatus());
    		}
    		result ="contractWorkmen/cancelListing";
        }else if(action.equals(GatePassType.LOSTORDAMAGE.getStatus())) {
        	rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/lostordamageFilter");
    		if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    			gplistDto= workmenService.getGatePassActionListingDetailsForDashboardNav(principalEmployerId,deptId,String.valueOf(user.getUserId()),GatePassType.LOSTORDAMAGE.getStatus());
        		
    		}else {	
    			gplistDto = workmenService.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.LOSTORDAMAGE.getStatus());
    		}
    		result ="contractWorkmen/lostListing";
        }else if(action.equals(GatePassType.RENEW.getStatus())) {
        	rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/renewFilter");
        	if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
        		//write union for renewal pending and renewed
    				gplistDto= workmenService.getRenewListingDetails( String.valueOf(user.getUserId()), GatePassType.CREATE.getStatus(), GatePassStatus.APPROVED.getStatus(), deptId, principalEmployerId) ;
        		}else {	
        			gplistDto = workmenService.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.RENEW.getStatus());
        		}	
    		result ="contractWorkmen/renewListing";
        }
		 listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
	   	    request.setAttribute("UserPermission", rr);
	    	request.setAttribute("principalEmployers", peList);
	    	request.setAttribute("Dept", departments);
	    	request.setAttribute("selectedPE", principalEmployerId);
	    	request.setAttribute("selectedDept", deptId);
		request.setAttribute("GatePassListingDto", gplistDto);
		return result;
	}
    
    @GetMapping("/demo")
    public String demo(HttpServletRequest request, HttpServletResponse response	) {
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user1 = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

		String type="regular";
		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user1.getUserAccount());
    	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
    			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
    	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
    	List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
    	String principalEmployerId = peList.get(0).getId();
    	String deptId  = departments.get(0).getId();
    	List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
        CMSRoleRights rr =new CMSRoleRights();
        rr = commonService.hasPageActionPermissionForRole(user1.getRoleId(), "/contractworkmen/list");
   	    listDto = peService.getAllPrincipalEmployer(user1.getUserAccount());
   	    request.setAttribute("UserPermission", rr);
    	request.setAttribute("principalEmployers", peList);
    	request.setAttribute("Dept", departments);
    	request.setAttribute("selectedPE", principalEmployerId);
    	request.setAttribute("selectedDept", deptId);
    	  
    	List<GatePassListingDto> gplistDto = new ArrayList<GatePassListingDto>();
		if(user1.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
			gplistDto= workmenService.getGatePassListingDetails(principalEmployerId,deptId,String.valueOf(user1.getUserId()),GatePassType.CREATE.getStatus(),type);
		}else {	
			gplistDto = workmenService.getGatePassListingForApprovers(principalEmployerId,deptId,user1,GatePassType.CREATE.getStatus(),type);
		}
		request.setAttribute("GatePassListingDto", gplistDto);
		return "contractWorkmen/approverList";
	}
    
    @PostMapping("/generateToken")
    @ResponseBody
    public Map<String, Object> generateToken(@RequestBody Map<String, String> requestBody, HttpSession session) {
        String aadhaarNumber = requestBody.get("aadhaarNumber");
        String surepassUrl = getSurePassURL();
        String token = getToken();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        // Create outer payload object
        RequestPayload payload = new RequestPayload();

        // Create inner data object
        RequestPayload.Data data = new RequestPayload.Data();
        data.setSignup_flow(true);
        data.setLogo_url("https://dot1.in/img/dot1.png");
        data.setSkip_main_screen(false);
        data.setExpiry_minutes(10);
        // Set inner object to outer
        payload.setData(data);

        HttpEntity<RequestPayload> entity = new HttpEntity<>(payload, headers);

        Map<String, Object> responseMap = new HashMap<>();

        try {
            ResponseEntity<Map> responseEntity = restTemplate.exchange(surepassUrl, HttpMethod.POST, entity, Map.class);
            Map<String, Object> responseBody = responseEntity.getBody();

            Map<String, Object> dataMap = (Map<String, Object>) responseBody.get("data");

            // Store client_id in session for later use in verify OTP
            if (dataMap != null && dataMap.containsKey("client_id")) {
                session.setAttribute("aadhaarClientId", dataMap.get("client_id"));
                String newtoken = String.valueOf(dataMap.get("token"));
                String newUrl = String.valueOf(dataMap.get("url"));
                responseMap.put("token", newtoken);
                responseMap.put("url", newUrl);
            }

            responseMap.put("success", responseBody.get("success"));
            responseMap.put("message", responseBody.get("message"));
            responseMap.put("status_code", responseBody.get("status_code"));
            responseMap.put("status", dataMap != null ? dataMap.get("status") : null);

        } catch (HttpClientErrorException ex) {
            try {
                Map<String, Object> errorBody = new ObjectMapper().readValue(ex.getResponseBodyAsString(), Map.class);
                Map<String, Object> dataMap = (Map<String, Object>) errorBody.get("data");

                // Store client_id even in error case (if available)
                if (dataMap != null && dataMap.containsKey("client_id")) {
                    session.setAttribute("aadhaarClientId", dataMap.get("client_id"));
                }

                responseMap.put("success", false);
                responseMap.put("message", errorBody.get("message"));
                responseMap.put("status_code", errorBody.get("status_code"));
                responseMap.put("status", dataMap != null ? dataMap.get("status") : null);

            } catch (Exception e) {
                responseMap.put("success", false);
                responseMap.put("message", "Unable to parse error response.");
                responseMap.put("status_code", 500);
            }
        } catch (Exception e) {
            responseMap.put("success", false);
            responseMap.put("message", "Internal server error occurred.");
            responseMap.put("status_code", 500);
        }

        return responseMap;
    }
    
//    @GetMapping("/digi")
//    public String digi(@RequestParam(value = "token", required = false) String redirectUrl,
//                               HttpServletRequest request) {
//        request.setAttribute("token", redirectUrl);
//        // This resolves to /WEB-INF/view/contractWorkmen/digi.jsp
//        return "contractWorkmen/digi";
//    }
    
//    @GetMapping("/digilocker")
//    public String loadDigiPage1(@RequestParam(value = "url", required = false) String redirectUrl,
//                               HttpServletRequest request) {
//        request.setAttribute("redirectUrl", redirectUrl);
//        // This resolves to /WEB-INF/view/contractWorkmen/digi.jsp
//        return "contractWorkmen/digilocker";
//    }
    
    public String getDownloadURL() {
	    return QueryFileWatcher.getQuery("DOWNLOAD_URL");
	}
    
    @GetMapping("/digiClientId")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> digiClientId(
            @RequestParam(value = "id", required = false) String id) {

        String surepassUrl = getDownloadURL() + "/" + id;
        System.out.println("URL is:"+surepassUrl);
        String token = getToken();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        Map<String, Object> responseMap = new HashMap<>();

        try {
            ResponseEntity<Map> responseEntity =
                    restTemplate.exchange(surepassUrl, HttpMethod.GET, entity, Map.class);

            Map<String, Object> responseBody = responseEntity.getBody();
            Map<String, Object> dataMap = (Map<String, Object>) responseBody.get("data");

            responseMap.put("success", true);
            responseMap.put("data", dataMap);
            responseMap.put("firstName", dataMap.get(token));

        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("success", false);
            responseMap.put("error", e.getMessage());
        }

        return ResponseEntity.ok(responseMap);
    }

    @GetMapping("/downloadPreviousDoc")
    public void downloadPreviousDoc(@RequestParam("transactionId") String transactionId,
                                    @RequestParam("fileName") String fileName,
                                    HttpServletResponse response,
                                    HttpServletRequest request) throws IOException {


        HttpSession session = request.getSession(false);
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

        if (user == null || fileName == null || fileName.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
            System.out.println(response);
            return;
        }

        // Construct secure file path
        String baseDir = "D:/wfd_cwfm/ep_docs/" + user.getUserId() + "/" + transactionId + "/";
        File file = new File(baseDir, fileName);

        if (!file.exists() || !file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            System.out.println(response);
            return;
        }

        // Detect MIME type dynamically
        String mimeType = request.getServletContext().getMimeType(file.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        response.setContentLengthLong(file.length());

        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
    public String uploadRenewDocuments( MultipartFile aadharFile,
            MultipartFile policeFile,
            MultipartFile profilePic,
            String userId,
            String gatePassId) {

          // Create directory path
           String directoryPath = ROOT_DIRECTORY + userId + "/"+gatePassId+"/";

          try {
                  // Ensure the directory exists, if not create it
           Path path = Paths.get(directoryPath);
           if (!Files.exists(path)) {
           Files.createDirectories(path);
         }

           // Save Aadhar PDF
         if (!aadharFile.isEmpty()) {
         String aadharFilePath = directoryPath + "aadharV.pdf";
         saveFile(aadharFile, aadharFilePath);
        }

          // Save Police Verification PDF
         if (!policeFile.isEmpty()) {
         String policeFilePath = directoryPath + "policeV.pdf";
         saveFile(policeFile, policeFilePath);
        }

        // Save Profile Pic
           if(!profilePic.isEmpty()) {
           String profilePicPath = directoryPath +profilePic.getOriginalFilename();
           saveFile(profilePic,profilePicPath);
        }

      // Return success message
      return "success";

      } catch (IOException e) {
      e.printStackTrace();
      return "failed";
    }
  }
    private String uploadRenewAdditionalDocuments(List<MultipartFile> additionalFiles,
    		List<String> documentTypes,
    		String userId,
    		String gatePassId) {
    	// Create directory path
    	String directoryPath = ROOT_DIRECTORY + userId + "/" + gatePassId + "/";

    	try {
    		// Ensure the directory exists, if not create it
    		Path path = Paths.get(directoryPath);
    		if (!Files.exists(path)) {
    			Files.createDirectories(path);
    		}

    		for (int i = 0; i < additionalFiles.size(); i++) {
    			MultipartFile file = additionalFiles.get(i);
    			String docType = documentTypes.get(i);

    			// Create a filename based on the document type
    			String fileName = docType + "V.pdf"; // or any other format you prefer
    			String filePath = directoryPath + fileName;

    			// Save the file
    			if (!file.isEmpty()) {
    				saveFile(file, filePath);
    			}
    		}

    		return "success";

    	} catch (IOException e) {
    		log.error("Failed to save additional documents: ", e);
    		return "failed";
    	}
    }
    
    public String getVerhoeffConfig() {
		return QueryFileWatcher.getQuery("VERHOEFF_CONFIG");
	}
    @RequestMapping(value = "/checkAadharExistsCreation", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> checkAadharExistsCreation(
            @RequestParam("aadharNumber") String aadharNumber,
            @RequestParam(value = "gatePassId", required = false) String gatePassId,
            @RequestParam(value = "transactionId", required = false) String transactionId) {

        String status = workmenService.checkAadharUniqueness(aadharNumber, gatePassId, transactionId);
if (status.contains("Unique")) {
	
	String config = this.getVerhoeffConfig();
	if("yes".equalsIgnoreCase(config)) {
	  boolean valid = VerhoeffAlgorithm.validateVerhoeff(aadharNumber); 
	  if(!valid)
	  { 
		  status = "Invalid"; 
		  }
	}
	 
}
        Map<String, String> result = new HashMap<>();
        result.put("status", status);   // "Unique", "Exists_Gatepass_Draft", etc.

        return result;
    }

    
    //projectOnboardingList
    @GetMapping("/projectOnboardingList")
    public String projectOnboardingList(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

		
		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
    	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
    			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
    	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
    	//List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
    	
    	List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
        CMSRoleRights rr =new CMSRoleRights();
        rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/projectOnboardingList");
   	    listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
   	    request.setAttribute("UserPermission", rr);
    	request.setAttribute("principalEmployers", peList);
    	  //request.setAttribute("Dept", departments);
    	  
		return "contractWorkmen/projectOnboardingList";
	}
    
    @GetMapping("/projectOnboardingCreation")
    public String projectOnboardingCreation(HttpServletRequest request,HttpServletResponse response) {
	
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    	log.info("Entered into projectOnboardingCreation"+user.getUserId());
    	
    	String transactionId= workmenService.generateTransactionId();
    	request.setAttribute("transactionId", transactionId);
    	
    	List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
    	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
    			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
    	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
    	//List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
    	//List<PersonOrgLevel> subdepartments = groupedByLevelDef.getOrDefault("Area", new ArrayList<>());
    	request.setAttribute("PrincipalEmployer", peList);
    	  //request.setAttribute("Dept", departments);
         // request.setAttribute("Subdept", subdepartments);
          
        //Skills
		//List<Skill> skillList = workmenService.getAllSkill();
		//request.setAttribute("Skills", skillList);
		
		List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMaster();

		// Grouping the CmsGeneralMaster objects by gmType
		Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
		        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

		// Define the types and their corresponding request attribute names
		Map<String, String> attributeMapping = Map.of(
		        "GENDER", "GenderOptions",
		        "BLOODGROUP", "BloodGroup",
		        "ACCESSAREA", "AccessArea",
		        "ACADEMICS", "Academics",
		        "WAGECATEGORY", "WageCategory",
		        "BONUSPAYOUT", "BonusPayout",
		        "ZONE", "Zone",
		        "WORKMENTYPE","WorkmenType"
		);

		// Iterate over the attribute mappings and set the request attributes dynamically
		attributeMapping.forEach((type, attributeName) -> {
		    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
		    request.setAttribute(attributeName, gmList1);
		});
        return "contractWorkmen/projectOnboarding";
    }
    public String uploadReasoningDocuments(
            MultipartFile exitFile,
            MultipartFile fnfFile,
            MultipartFile feedbackFile,
            MultipartFile rateManagerFile,
            MultipartFile locFile,
            String userId,
            String gatePassId) {

        String directoryPath = ROOT_DIRECTORY + userId + "/" + gatePassId + "/";

        try {
            // Create directory if not exists
            Path path = Paths.get(directoryPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            // Save Exit Letter
            if (exitFile != null && !exitFile.isEmpty()) {
                String exitFilePath = directoryPath + "exitletter.pdf";
                saveFile(exitFile, exitFilePath);
            }

            // Save FNF
            if (fnfFile != null && !fnfFile.isEmpty()) {
                String fnfFilePath = directoryPath + "fnf.pdf";
                saveFile(fnfFile, fnfFilePath);
            }

            // Save Feedback
            if (feedbackFile != null && !feedbackFile.isEmpty()) {
                String feedbackFilePath = directoryPath + "feedback.pdf";
                saveFile(feedbackFile, feedbackFilePath);
            }

            // Save Rate Manager
            if (rateManagerFile != null && !rateManagerFile.isEmpty()) {
                String rateManagerFilePath = directoryPath + "ratemanager.pdf";
                saveFile(rateManagerFile, rateManagerFilePath);
            }

            // Save LOC
            if (locFile != null && !locFile.isEmpty()) {
                String locFilePath = directoryPath + "loc.pdf";
                saveFile(locFile, locFilePath);
            }

            return "success";

        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
    }

    }
