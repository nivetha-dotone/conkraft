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
import com.wfd.dot1.cwfm.dto.WorkmanFullHistoryDTO;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.GatePassMain;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.WorkmenBulkUpload;
import com.wfd.dot1.cwfm.service.AadharSearchHistoryService;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.EntrypassStatusService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;
import com.wfd.dot1.cwfm.service.WorkmenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/aadharSearchHistory")
public class AadharSearchHistoryController{
	
	@Autowired
	AadharSearchHistoryService aadharhistoryService;
	
	
	@PostMapping("/history")
    @ResponseBody
    public WorkmanFullHistoryDTO getHistory(
            @RequestParam("aadhaar") String aadhaar) {

        return aadharhistoryService.getFullHistory(aadhaar);
    }
}

