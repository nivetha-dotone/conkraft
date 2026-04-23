package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.ReconciliationService;

@Controller
@RequestMapping("/contractor")
public class ReconciliationController {

    @Autowired
    private ReconciliationService reconciliationService;
    
    @Autowired
    private CommonService commonService;

    @GetMapping("/reconciliation")
    public ModelAndView loadReconciliationScreen(HttpSession session) {
        ModelAndView mv = new ModelAndView("contractors/reconciliation");
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
        
        
        Long contractorId = Long.parseLong(String.valueOf(user.getUserId()));

        List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());

        Map<String, List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
                .collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));

        List<PersonOrgLevel> contList = groupedByLevelDef.getOrDefault("Contractor", new ArrayList<>());
        mv.addObject("contList", contList);
        //List<WorkmenReconciliationDTO> pfList = reconciliationService.getPfReconciliationList(contractorId);
        //List<WorkmenReconciliationDTO> esicList = reconciliationService.getEsicReconciliationList(contractorId);
        List<WorkmenReconciliationDTO> pfList = new ArrayList<>();
        	List<WorkmenReconciliationDTO> esicList = new ArrayList<>();

        mv.addObject("pfList", pfList);
        mv.addObject("esicList", esicList);

        return mv;
    }
    
    @PostMapping("/getReconciliationData")
    @ResponseBody
    public Map<String, Object> getReconciliationData(
            @RequestParam("contractorId") Long contractorId) {

        Map<String, Object> response = new HashMap<>();

        List<WorkmenReconciliationDTO> pfList =
                reconciliationService.getPfReconciliationList(contractorId);

        List<WorkmenReconciliationDTO> esicList =
                reconciliationService.getEsicReconciliationList(contractorId);

        response.put("pfList", pfList);
        response.put("esicList", esicList);

        return response;
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