package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.dto.ApproverStatusDTO;
import com.wfd.dot1.cwfm.dto.GatePassListingDto;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.GatePassMain;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.WorkmenBulkUpload;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.EntrypassStatusService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;
import com.wfd.dot1.cwfm.service.WorkmenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/entrypassstatus")
public class EntrypassstatusController {
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	PrincipalEmployerService peService;
	
	@Autowired
	WorkmenService workmenService;
	
	@Autowired
	EntrypassStatusService entrypassService;
	
	@GetMapping("/list")
    public String getAllWorkmenUploadList(HttpServletRequest request,HttpServletResponse response) {
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
        	rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/entrypassstatus/list");
        	 //listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
         }
         }
    		//request.setAttribute("cmSPRINCIPALEMPLOYERs", listDto);
    		request.setAttribute("UserPermission", rr);
    		
    		//List<WorkmenBulkUpload> wbu = workmenuploadService.getAllWorkmenBulkUploadData();
    	   //	 model.addAttribute("wbudata", wbu);
    		//GatePassMain gatePassMainObj =null;
	//gatePassMainObj = workmenService.getIndividualContractWorkmenDetails(transactionId);
	//request.setAttribute("GatePassObj", gatePassMainObj);
	//List<ApproverStatusDTO> approvers = workmenService.getApprovalDetails(transactionId,gatePassMainObj.getUnitId());
	// request.setAttribute("approvers", approvers);
    	return "entrypassstatus/entrypassstatus";
    }
	
	 @PostMapping("/statusList")
	    @ResponseBody
	    public List<GatePassListingDto> getQuickGatepassStatus(@RequestParam(required = false) String transactionId,
	                                                            @RequestParam(required = false) String gatepassId) {
	        return entrypassService.getGatePassStatus(transactionId, gatepassId);
	    }
	 
		@GetMapping("/historylist")
	    public String getAllHistory(HttpServletRequest request,HttpServletResponse response) {
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
	        	rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/entrypassstatus/list");
	        	 //listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
	         }
	         }
	    		//request.setAttribute("cmSPRINCIPALEMPLOYERs", listDto);
	    		request.setAttribute("UserPermission", rr);
	    		
	    	return "entrypassstatus/History";
	    }
		
		 @PostMapping("/history")
		    @ResponseBody
		    public List<GatePassListingDto> getHistoryofEP(@RequestParam String aadharNumber) {
		        return entrypassService.getHistoryofEP(aadharNumber);
		    }
}

