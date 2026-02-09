package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.DeptMapping;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.DepartmentMappingService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;
import com.wfd.dot1.cwfm.service.WorkmenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/departmentMapping")
public class DepartmentMappingController {

	@Autowired
	DepartmentMappingService deptMapService;
	
	@Autowired
	PrincipalEmployerService peService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	WorkmenService workmenService;
	
	@GetMapping("/list")
	public String getAllModules(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
		
		
		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
    	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
    			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
    	List<PersonOrgLevel> peList =new ArrayList<>();
    	
    	if(user.getRoleName().equals("System Admin")) {
        	
    		List<PrincipalEmployer> listDto = peService.getAllPrincipalEmployerForAdmin();
    		for(PrincipalEmployer pe :listDto) {
    			PersonOrgLevel org = new PersonOrgLevel();
    			org.setId(String.valueOf(pe.getUnitId()));
    			org.setDescription(pe.getName());
    			peList.add(org);
    		}
    		
         }else {
        	 peList =  groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
         }
    	request.setAttribute("PrincipalEmployer", peList);
    	
   	 List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMaster();

		// Grouping the CmsGeneralMaster objects by gmType
		Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
		        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

		// Define the types and their corresponding request attribute names
		Map<String, String> attributeMapping = Map.of(
		        "AREA", "AreaOptions",
		        "DEPARTMENT", "Department"
		        
		);

		// Iterate over the attribute mappings and set the request attributes dynamically
		attributeMapping.forEach((type, attributeName) -> {
		    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
		    request.setAttribute(attributeName, gmList1);
		});

				
		return "deptMapping/unitDeptMapping";
	}
	@PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> saveMappings(@RequestBody List<DeptMapping> mappings) {
        try {
        	deptMapService.saveMappings(mappings);
            return ResponseEntity.ok("All mappings saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving mappings: " + e.getMessage());
        }
    }

	
	 @GetMapping("/existinglist")
	    public String getAllMappings(HttpServletRequest request,HttpServletResponse response,Model model) {
	    	 HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
	         MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
	         List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
	         CMSRoleRights rr =new CMSRoleRights();
	         if(user!=null) {
	         if(user.getRoleName().equals("System Admin")) {
	        	 rr.setAddRights(1);  // Changed getInt() to getBoolean()
			        rr.setEditRights(1);
			        rr.setDeleteRights(1);
			        rr.setImportRights(1);
			        rr.setExportRights(1);
			        rr.setViewRights(1);
	        	 listDto = peService.getAllPrincipalEmployerForAdmin();
	         }else {
	        	rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/departmentMapping/existinglist");
	        	 //listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
	         }
	         }
	    		//request.setAttribute("cmSPRINCIPALEMPLOYERs", listDto);
	    		request.setAttribute("UserPermission", rr);
	    		
	    		List<DeptMapping> wbu = deptMapService.getAllMappings();
	    	   	 model.addAttribute("wbudata", wbu);
	    	return "deptMapping/view";
	    }
	 
	 @GetMapping("/existingTradeSkilllist")
	    public String getAllTradeSkillMappings(HttpServletRequest request,HttpServletResponse response,Model model) {
	    	 HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
	         MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
	         List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
	         CMSRoleRights rr =new CMSRoleRights();
	         if(user!=null) {
	         if(user.getRoleName().equals("System Admin")) {
	        	 rr.setAddRights(1);  // Changed getInt() to getBoolean()
			        rr.setEditRights(1);
			        rr.setDeleteRights(1);
			        rr.setImportRights(1);
			        rr.setExportRights(1);
			        rr.setViewRights(1);
	        	 listDto = peService.getAllPrincipalEmployerForAdmin();
	         }else {
	        	rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/departmentMapping/existingTradeSkilllist");
	        	 //listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
	         }
	         }
	    		//request.setAttribute("cmSPRINCIPALEMPLOYERs", listDto);
	    		request.setAttribute("UserPermission", rr);
	    		
	    		List<DeptMapping> wbu = deptMapService.getAllTradeSkillMappings();
	    	   	 model.addAttribute("wbudata", wbu);
	    	return "deptMapping/tradeSkillList";
	    }
	 
	 @GetMapping("/addTradeSkill")
		public String addTradeSkill(HttpServletRequest request, HttpServletResponse response) {
			
			HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
			MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
			
		
			List<PersonOrgLevel> peList = new ArrayList<>();
			List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
	    	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
	    			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
	    	
	    	
	    	if(user.getRoleName().equals("System Admin")) {
	        	
	    		List<PrincipalEmployer> listDto = peService.getAllPrincipalEmployerForAdmin();
	    		for(PrincipalEmployer pe :listDto) {
	    			PersonOrgLevel org = new PersonOrgLevel();
	    			org.setId(String.valueOf(pe.getUnitId()));
	    			org.setDescription(pe.getName());
	    			peList.add(org);
	    		}
	    		
	         }else {
	        	 peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>()); 
	         }
	    	request.setAttribute("PrincipalEmployer", peList);
			 List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMaster();

				// Grouping the CmsGeneralMaster objects by gmType
				Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
				        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

				// Define the types and their corresponding request attribute names
				Map<String, String> attributeMapping = Map.of(
				        "TRADE", "TradeOptions",
				        "SKILL", "SkillOptions"
				        
				);

				// Iterate over the attribute mappings and set the request attributes dynamically
				attributeMapping.forEach((type, attributeName) -> {
				    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
				    request.setAttribute(attributeName, gmList1);
				});
					
			return "deptMapping/tradeSkillMapping";
		}
	 
	 @PostMapping("/saveTradeSkill")
	    @ResponseBody
	    public ResponseEntity<String> saveTradeSkill(@RequestBody List<DeptMapping> mappings) {
	        try {
	        	deptMapService.saveTradeSkillMappings(mappings);
	            return ResponseEntity.ok("All mappings saved successfully!");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Error saving mappings: " + e.getMessage());
	        }
	    }
	 @PostMapping("/deleteDepartmentMapping")
	 @ResponseBody
	 public ResponseEntity<Map<String, Object>> deleteDepartmentMapping(@RequestBody List<DeptMapping> mappings) {
		 Map<String, Object> response = new HashMap<>();
		 try {
	    	 if (mappings == null || mappings.isEmpty()) {
	             response.put("status", "error");
	             response.put("message", "No mappings received.");
	             return ResponseEntity.badRequest().body(response);
	         }
	         // Debug logs
	         mappings.forEach(m -> System.out.println(
	             "PE=" + m.getPrincipalEmployerId() +
	             ", Dept=" + m.getDepartmentId() +
	             ", SubDept=" + m.getSubDepartmentId()
	         ));
	         
	         Map<String, List<DeptMapping>> result  = deptMapService.deleteDeptMappingIfExistsInGatePass(mappings);

	         List<DeptMapping> deleted = result.get("deleted");
	         List<DeptMapping> skipped = result.get("skipped");

	         if (!skipped.isEmpty() && !deleted.isEmpty()) {
	             response.put("status", "partial");
	             response.put("message", "Some mappings deleted, some skipped due to dependency.");
	         } else if (!deleted.isEmpty()) {
	             response.put("status", "success");
	             response.put("message", "All selected mappings deleted successfully.");
	         } else {
	             response.put("status", "error");
	             response.put("message", "No mappings deleted, dependencies exist.");
	         }

	         response.put("deleted", deleted);
	         response.put("skipped", skipped);
	         return ResponseEntity.ok(response);

	     } catch (Exception e) {
	         e.printStackTrace();
	         response.put("status", "error");
	         response.put("message", "Error deleting mappings: " + e.getMessage());
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	     }
	 }
	 
	 @PostMapping("/deleteTradeMapping")
	 @ResponseBody
	 public ResponseEntity<Map<String, Object>> deleteTradeMapping(@RequestBody List<DeptMapping> mappings) {
	     Map<String, Object> response = new HashMap<>();
	     try {
	         if (mappings == null || mappings.isEmpty()) {
	             response.put("status", "error");
	             response.put("message", "No mappings received.");
	             return ResponseEntity.badRequest().body(response);
	         }

	         Map<String, List<DeptMapping>> result = deptMapService.deleteTradeMappingIfExistsInGatePass(mappings);

	         List<DeptMapping> deleted = result.get("deleted");
	         List<DeptMapping> skipped = result.get("skipped");

	         if (!skipped.isEmpty() && !deleted.isEmpty()) {
	             response.put("status", "partial");
	             response.put("message", "Some mappings deleted, some skipped due to dependency.");
	         } else if (!deleted.isEmpty()) {
	             response.put("status", "success");
	             response.put("message", "All selected mappings deleted successfully.");
	         } else {
	             response.put("status", "error");
	             response.put("message", "No mappings deleted, dependencies exist.");
	         }

	         response.put("deleted", deleted);
	         response.put("skipped", skipped);
	         return ResponseEntity.ok(response);

	     } catch (Exception e) {
	         e.printStackTrace();
	         response.put("status", "error");
	         response.put("message", "Error deleting mappings: " + e.getMessage());
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	     }
	 }
}
