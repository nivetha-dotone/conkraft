package com.wfd.dot1.cwfm.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.pojo.HrChecklistItem;
import com.wfd.dot1.cwfm.service.BillConfigService;

import jakarta.servlet.http.HttpServletRequest;
@Controller
@RequestMapping("/billSetup")
public class BillVerificationSetupController {	

	@Autowired
    private BillConfigService billConfigService;
	
	@GetMapping("/list")
    public String showConfigPage(Model model) {
        model.addAttribute("kronosReports", billConfigService.getAllKronosReports());
        model.addAttribute("statutoryReports", billConfigService.getAllStatutoryAttachments());
        model.addAttribute("checklistItems", billConfigService.getAllChecklistItems());
        return "billVerification/setup";
    }
	

	 
	
	@PostMapping("/saveReportConfigAjax")
	@ResponseBody
	public ResponseEntity<String> saveReportConfigAjax(@RequestBody Map<String, Object> payload,HttpServletRequest request) {
	    @SuppressWarnings("unchecked")
		List<String> kronosReports = (List<String>) payload.get("kronosReports");
	    @SuppressWarnings("unchecked")
		List<String> statutoryReports = (List<String>) payload.get("statutoryReports");

	    @SuppressWarnings("unchecked")
		List<Map<String, Object>> checklistMap = (List<Map<String, Object>>) payload.get("checklistItems");
	    List<HrChecklistItem> checklistItems = new ArrayList<>();

	    for (Map<String, Object> item : checklistMap) {
	        HrChecklistItem hrItem = new HrChecklistItem();
	        hrItem.setCheckpointName((String) item.get("checkpointName"));
	        hrItem.setLicenseRequired((Boolean) item.get("licenseRequired"));
	        hrItem.setValidUptoRequired((Boolean) item.get("validUptoRequired"));
	        checklistItems.add(hrItem);
	    }

	    billConfigService.saveAll(kronosReports, statutoryReports, checklistItems);
	    request.setAttribute("kronosReports", billConfigService.getAllKronosReports());
	    request.setAttribute("statutoryReports", billConfigService.getAllStatutoryAttachments());
	    request.setAttribute("checklistItems", billConfigService.getAllChecklistItems());
	    return ResponseEntity.ok("Saved");
	}

}
