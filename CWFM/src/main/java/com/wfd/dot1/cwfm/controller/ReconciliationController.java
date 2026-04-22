package com.wfd.dot1.cwfm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wfd.dot1.cwfm.dto.ReconciliationResultDTO;
import com.wfd.dot1.cwfm.dto.WorkmenReconciliationDTO;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.service.ReconciliationService;

@Controller
@RequestMapping("/contractor")
public class ReconciliationController {

    @Autowired
    private ReconciliationService reconciliationService;

    @GetMapping("/reconciliation")
    public ModelAndView loadReconciliationScreen(HttpSession session) {
        ModelAndView mv = new ModelAndView("contractors/reconciliation");
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
        
        
        Long contractorId = Long.parseLong(String.valueOf(user.getUserId()));

        List<WorkmenReconciliationDTO> pfList = reconciliationService.getPfReconciliationList(contractorId);
        List<WorkmenReconciliationDTO> esicList = reconciliationService.getEsicReconciliationList(contractorId);

        mv.addObject("pfList", pfList);
        mv.addObject("esicList", esicList);

        return mv;
    }

    @PostMapping("/reconciliation/upload")
    @ResponseBody
    public Map<String, Object> uploadReconciliation(@RequestParam("reconType") String reconType,
                                                    @RequestParam("file") MultipartFile file,
                                                    HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        try {
        	MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
            
            
            Long contractorId = Long.parseLong(String.valueOf(user.getUserId()));
            String uploadedBy = String.valueOf(contractorId);

            ReconciliationResultDTO result = reconciliationService.processReconciliation(
                    contractorId, reconType, file, uploadedBy);

            response.put("status", "success");
            response.put("data", result);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage() != null ? e.getMessage() : "Error while processing reconciliation.");
            e.printStackTrace();
        }

        return response;
    }
}