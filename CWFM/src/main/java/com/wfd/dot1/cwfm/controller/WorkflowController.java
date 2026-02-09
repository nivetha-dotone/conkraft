package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.dto.WorkflowRequestDto;
import com.wfd.dot1.cwfm.dto.WorkflowResponseDto;
import com.wfd.dot1.cwfm.pojo.BusinessType;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;
import com.wfd.dot1.cwfm.service.WorkflowService;
import com.wfd.dot1.cwfm.service.WorkmenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/workflow")
public class WorkflowController {
	private static final Logger log = LoggerFactory.getLogger(WorkflowController.class.getName());
	
	@Autowired
	WorkmenService workmenService;
	
	@Autowired
	PrincipalEmployerService peService;
	
	@Autowired
	WorkflowService workflowService;
	
	@GetMapping("/list")
	public String getAllModules(HttpServletRequest request, HttpServletResponse response) {
		//Get All GeneralMaster
				List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMaster();

				// Grouping the CmsGeneralMaster objects by gmType
				Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
				        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

				// Define the types and their corresponding request attribute names
				Map<String, String> attributeMapping = Map.of(
				      				        "MODULE","Modules",
				      				      "ROLE", "Roles",
				      				      "ACTION","Actions"
				);

				// Iterate over the attribute mappings and set the request attributes dynamically
				attributeMapping.forEach((type, attributeName) -> {
				    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
				    request.setAttribute(attributeName, gmList1);
				});

				List<PrincipalEmployer> listDto = peService.getAllPrincipalEmployerForAdmin();
				request.setAttribute("PrincipalEmployer", listDto);
		return "workflow/setup";
	}
	
	@GetMapping("/getAllBusinessType")
	public ResponseEntity<List<BusinessType>> getAllBusinessType(
            @RequestParam("unitId") String unitId, 
            HttpServletRequest request,HttpServletResponse response) {
        log.info("Fetching contractors for unitId: " + unitId );
        try {
        	List<BusinessType> bts=new ArrayList<BusinessType>();
        	
    				bts = workflowService.getAllBusinessType(unitId);
    		
            if (bts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bts, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching businesstype: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping("/getExistingWorkflow")
    @ResponseBody
    public WorkflowResponseDto getExistingWorkflow(
            @RequestParam("unitId") String unitId,
            //@RequestParam("businessType") String businessType,
            @RequestParam("moduleId") String moduleId,
            @RequestParam("actionName") String actionName) {
		//return workflowService.fetchWorkflow(unitId, businessType, moduleId,actionName);
        return workflowService.fetchWorkflow(unitId, null,moduleId,actionName);
    }
	
	 @PostMapping("/saveWorkflow")
	    @ResponseBody
	    public String saveWorkflow(@RequestBody WorkflowRequestDto request,HttpServletRequest req, HttpServletResponse response) {
	    	HttpSession session = req.getSession(false); // Use `false` to avoid creating a new session
			MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
			request.setUserId(String.valueOf(user.getUserId()));
	        workflowService.saveWorkflow(request);
	        return "Workflow saved successfully";
	    }
}
