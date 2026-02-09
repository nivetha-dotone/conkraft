package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.dao.FileUploadDao;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
import com.wfd.dot1.cwfm.pojo.ContractorRegistrationPolicy;
import com.wfd.dot1.cwfm.pojo.GatePassMain;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.WorkmenBulkUpload;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.FileUploadService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;
import com.wfd.dot1.cwfm.service.WorkmenBulkUploadService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/workmenBulkUpload")
public class WorkmenBulkUploadController {

	@Autowired
	WorkmenBulkUploadService workmenuploadService;
	
	@Autowired
	PrincipalEmployerService peService;
	
	@Autowired
    private FileUploadService fileUploadService;
	
	 @Autowired
	    private FileUploadDao fileUploadDao;
	
	@Autowired
	CommonService commonService;
	
	@GetMapping("/list")
    public String getAllWorkmenUploadList(HttpServletRequest request,HttpServletResponse response,Model model) {
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
        	rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/workmenBulkUpload/list");
        	 //listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
         }
         }
    		//request.setAttribute("cmSPRINCIPALEMPLOYERs", listDto);
    		request.setAttribute("UserPermission", rr);
    		
    		List<WorkmenBulkUpload> wbu = workmenuploadService.getAllWorkmenBulkUploadData();
    	   	 model.addAttribute("wbudata", wbu);
    	return "contractWorkmenBulkUpload/workmenBulkUpload";
    }
	
	@PostMapping("/validateAndSave")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> validateAndSaveWorkmen(@RequestBody List<Integer> transactionIds,HttpServletRequest request, HttpServletResponse response) {
		
		// ✅ Get loginuser from session
				HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
				MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
				String createdBy = String.valueOf(user.getUserId()); 
		
		//workmenbulkupload.setCreatedBy(String.valueOf(user.getUserId()));
		 Map<String, Object> result = workmenuploadService.validateAndSaveSelectedWorkmen(transactionIds,createdBy);
		 
		 List<Map<String, Object>> successData = (List<Map<String, Object>>) result.get("successData");
		    List<Map<String, Object>> errorData = (List<Map<String, Object>>) result.get("errorData");

		    Map<String, Object> responses = new HashMap<>();
		    
		    if (!errorData.isEmpty() && successData.isEmpty()) {
		    	responses.put("status", "error");
		    	responses.put("message", "Selected records have errors.");
		    } else if (!errorData.isEmpty()) {
		    	responses.put("status", "partial");
		    	responses.put("message", "Some records were saved successfully. Some had errors.");
		    } else {
		    	responses.put("status", "success");
		    	responses.put("message", "Selected records saved successfully.");
		    }

		    responses.put("successData", successData);
		    responses.put("errorData", errorData);


		    return ResponseEntity.ok(responses);
	}


	
}
