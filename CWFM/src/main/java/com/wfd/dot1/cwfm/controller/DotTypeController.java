package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Arrays;


import com.wfd.dot1.cwfm.dto.WorkflowRequestDto;
import com.wfd.dot1.cwfm.enums.DotType;
import com.wfd.dot1.cwfm.pojo.BusinessType;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.service.DotTypeService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;
import com.wfd.dot1.cwfm.service.WorkflowService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dottype")
public class DotTypeController {
	private static final Logger log = LoggerFactory.getLogger(WorkflowController.class.getName());
	
	@Autowired
	WorkflowService workflowService;
	@Autowired
	DotTypeService dotTypeService;
	@Autowired
	PrincipalEmployerService peService;
	
	@GetMapping("/list")
	public String getAllModules(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
		
				List<PrincipalEmployer> listDto = peService.getAllPrincipalEmployerForAdmin();
				request.setAttribute("PrincipalEmployer", listDto);
				 request.setAttribute("dotTypes", DotType.values()); 
		return "dotType/setup";
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
	
	@PostMapping("/save")
	public  ResponseEntity<String> saveWorkflowType(@RequestParam("principalEmployerId") Long principalEmployerId,
	                               
	                               @RequestParam("dotType") int dotTypeId,
	                               HttpServletRequest request, HttpServletResponse response ) {

	    // ✅ Get loginuser from session
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
		String createdBy = user.getUserAccount();
		Long businessTypeId=new Long(0);
	    // ✅ Convert dotTypeId to DotType enum
	    DotType selectedDot = Arrays.stream(DotType.values())
	            .filter(d -> d.getStatus() == dotTypeId)
	            .findFirst()
	            .orElseThrow(() -> new IllegalArgumentException("Invalid DotType ID"));

	    try{
	    	// ✅ Save through service
	    dotTypeService.saveWorkflowType(principalEmployerId, businessTypeId, dotTypeId, createdBy);
	    return ResponseEntity.ok("Dot Type saved successfully!");
	    } catch (IllegalStateException ex) {
	        return ResponseEntity.badRequest().body(ex.getMessage());
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
	    }
	}
	
	@GetMapping("/getDotTypeByEmployerAndBusiness")
	@ResponseBody
	public Integer getDotTypeByEmployerAndBusiness(
	        @RequestParam Long principalEmployerId
	       ) {
		Long businessTypeId=new Long(0);
	    return dotTypeService.getMappedDotType(principalEmployerId, businessTypeId);
	}
}
