package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wfd.dot1.cwfm.dto.BankWageDetailDTO;
import com.wfd.dot1.cwfm.dto.BankWageExpectedDTO;
import com.wfd.dot1.cwfm.dto.ReconciliationResultDTO;
import com.wfd.dot1.cwfm.dto.WorkmenReconciliationDTO;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.ReconciliationService;

import jakarta.servlet.http.HttpSession;

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
        List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
        mv.addObject("peList", peList);
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
            @RequestParam("contractorId") Long contractorId,
            @RequestParam("fromDate") String fromDate,
            @RequestParam("toDate") String toDate) {

        Map<String, Object> response = new HashMap<>();

        List<WorkmenReconciliationDTO> pfList =
                reconciliationService.getPfReconciliationList(contractorId,fromDate,toDate);

        List<WorkmenReconciliationDTO> esicList =
                reconciliationService.getEsicReconciliationList(contractorId,fromDate,toDate);

        response.put("pfList", pfList);
        response.put("esicList", esicList);

        return response;
    }
    
//    @PostMapping("/reconciliation/upload")
//    @ResponseBody
//    public Map<String, Object> uploadReconciliation(@RequestParam("reconType") String reconType,
//    		@RequestParam("contractorId") Long contractorId,
//    		@RequestParam("fromDate") String fromDate,
//    		@RequestParam("toDate") String toDate,
//            @RequestParam("file") MultipartFile file, HttpSession session) {
//        Map<String, Object> response = new HashMap<>();
//
//        try {
//        	MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
//            
//            
//            //Long contractorId = Long.parseLong(String.valueOf(user.getUserId()));
//            String uploadedBy = String.valueOf(user.getUserId());
//
//            ReconciliationResultDTO result = reconciliationService.processReconciliation(
//                    contractorId, reconType,fromDate,toDate, file, uploadedBy);
//
//            response.put("status", "success");
//            response.put("data", result);
//
//        } catch (Exception e) {
//            response.put("status", "error");
//            response.put("message", e.getMessage() != null ? e.getMessage() : "Error while processing reconciliation.");
//            e.printStackTrace();
//        }
//
//        return response;
//    }
    

@PostMapping("/reconciliation/upload")
@ResponseBody
public Map<String, Object> uploadReconciliation(
        @RequestParam("reconType") String reconType,
        @RequestParam("contractorId") Long contractorId,
        @RequestParam(value = "principalEmployerId",
                      required = false) Long principalEmployerId,
        @RequestParam(value = "wageMonth",
                      required = false) String wageMonth,
        @RequestParam("fromDate") String fromDate,
        @RequestParam("toDate") String toDate,
        @RequestParam("file") MultipartFile file,
        HttpSession session) {

    Map<String, Object> response = new HashMap<>();

    try {
        MasterUser user = (MasterUser) (
                session != null
                ? session.getAttribute("loginuser")
                : null);

        if (user == null) {
            response.put("status", "error");
            response.put("message",
                    "Session expired. Please login again.");
            return response;
        }

        String uploadedBy =
                String.valueOf(user.getUserId());

        if (reconType == null
                || reconType.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Reconciliation type is mandatory.");
        }

        String type = reconType.trim()
                .toUpperCase(java.util.Locale.ROOT);

        if ("BANK".equals(type)) {

            if (principalEmployerId == null) {
                throw new IllegalArgumentException(
                        "Principal Employer is mandatory "
                        + "for Bank Wage Reconciliation.");
            }

            if (wageMonth == null
                    || wageMonth.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Wage month is mandatory "
                        + "for Bank Wage Reconciliation.");
            }

            Long uploadId =
                    reconciliationService
                    .processBankWageReconciliation(
                            principalEmployerId,
                            contractorId,
                            wageMonth,
                            fromDate,
                            toDate,
                            file,
                            uploadedBy);

            response.put("status", "success");
            response.put("uploadId", uploadId);
            response.put("message",
                    "Bank Wage Reconciliation "
                    + "completed successfully.");

        } else if ("PF".equals(type)
                || "ESIC".equals(type)) {

            ReconciliationResultDTO result =
                    reconciliationService
                    .processReconciliation(
                            contractorId,
                            type,
                            fromDate,
                            toDate,
                            file,
                            uploadedBy);

            response.put("status", "success");
            response.put("data", result);
            response.put("message",
                    type + " Reconciliation "
                    + "completed successfully.");

        } else {
            throw new IllegalArgumentException(
                    "Unsupported reconciliation type: "
                    + reconType);
        }

    } catch (Exception e) {

        response.put("status", "error");
        response.put("message",
                e.getMessage() != null
                ? e.getMessage()
                : "Error while processing reconciliation.");

        e.printStackTrace();
    }

    return response;
}


@GetMapping("/reconciliation/bank/expected")
@ResponseBody
public Map<String, Object> getBankExpectedWages(
        @RequestParam Long principalEmployerId,
        @RequestParam Long contractorId,
        @RequestParam String fromDate,
        @RequestParam String toDate) {

    Map<String, Object> response = new HashMap<>();

    try {
        List<BankWageExpectedDTO> data =
        		reconciliationService.getExpectedBankWages(
                        principalEmployerId,
                        contractorId,
                        fromDate,
                        toDate);

        response.put("status", "success");
        response.put("data", data);

    } catch (Exception ex) {
        response.put("status", "error");
        response.put("message", ex.getMessage());
    }

    return response;
}

@GetMapping("/reconciliation/bank/details")
@ResponseBody
public Map<String, Object> getBankWageDetails(
        @RequestParam("uploadId") Long uploadId,
        HttpSession session) {

    Map<String, Object> response = new HashMap<>();

    try {
        MasterUser user = (MasterUser)
                session.getAttribute("loginuser");

        if (user == null) {
            throw new IllegalArgumentException(
                    "Session expired. Please login again.");
        }

        List<BankWageDetailDTO> details =
        		reconciliationService.getBankWageDetails(uploadId);

        response.put("status", "success");
        response.put("data", details);

    } catch (Exception ex) {
        response.put("status", "error");
        response.put("message", ex.getMessage());
    }

    return response;
}


}