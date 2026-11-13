package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.wfd.dot1.cwfm.dto.ReconciliationComplianceDetailDTO;
import com.wfd.dot1.cwfm.dto.ReconciliationHistoryDTO;
import com.wfd.dot1.cwfm.dto.ReconciliationMismatchDTO;
import com.wfd.dot1.cwfm.dto.ReconciliationResultDTO;
import com.wfd.dot1.cwfm.dto.WorkmenReconciliationDTO;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.ReconciliationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/contractor")
public class ReconciliationController {
	private static final Logger logger =
	        LoggerFactory.getLogger(ReconciliationController.class);

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


private List<Long> getAuthorizedContractorIds(
        HttpSession session) {

    MasterUser user = session == null
            ? null
            : (MasterUser) session.getAttribute("loginuser");

    if (user == null) {
        throw new IllegalStateException(
                "Session expired. Please login again.");
    }

    List<PersonOrgLevel> orgLevels =
            commonService.getPersonOrgLevelDetails(
                    user.getUserAccount());

    if (orgLevels == null) {
        return Collections.emptyList();
    }

    return orgLevels.stream()
            .filter(level ->
                    "Contractor".equalsIgnoreCase(
                            level.getLevelDef()))
            .map(PersonOrgLevel::getId)
            .filter(Objects::nonNull)
            .map(id -> Long.valueOf(String.valueOf(id)))
            .distinct()
            .collect(Collectors.toList());
}



@GetMapping("/reconciliation/history")
@ResponseBody
public Map<String, Object> getReconciliationHistory(
        HttpSession session) {

    Map<String, Object> response = new HashMap<>();

    try {
        MasterUser user = session == null
                ? null
                : (MasterUser) session.getAttribute("loginuser");

        if (user == null) {
            response.put("status", "ERROR");
            response.put("message",
                    "Session expired. Please login again.");
            return response;
        }

        List<Long> contractorIds =
                getAuthorizedContractorIds(session);

        if (contractorIds.isEmpty()) {
            response.put("status", "SUCCESS");
            response.put("data", Collections.emptyList());
            return response;
        }

        List<ReconciliationHistoryDTO> history =
                reconciliationService.getReconciliationHistory(
                        contractorIds);

        response.put("status", "SUCCESS");
        response.put("data", history);

    } catch (Exception ex) {
        logger.error(
                "Error fetching reconciliation history", ex);

        response.put("status", "ERROR");
        response.put("message",
                "Unable to fetch reconciliation history.");
    }

    return response;
}


@GetMapping("/reconciliation/list")
public String getReconciliationHistoryPage(
        HttpServletRequest request) {

    HttpSession session = request.getSession(false);

    MasterUser user = (MasterUser) (
            session != null
                    ? session.getAttribute("loginuser")
                    : null);

    if (user == null) {
        return "redirect:/userlogin";
    }

    request.setAttribute("userId", user.getUserId());
    request.setAttribute("roleId", user.getRoleId());
    request.setAttribute("roleName", user.getRoleName());

    List<PersonOrgLevel> orgLevel =
            commonService.getPersonOrgLevelDetails(
                    user.getUserAccount());

    Map<String, List<PersonOrgLevel>> groupedByLevelDef =
            orgLevel.stream().collect(
                    Collectors.groupingBy(
                            PersonOrgLevel::getLevelDef));

    List<PersonOrgLevel> contList =
            groupedByLevelDef.getOrDefault(
                    "Contractor", new ArrayList<>());

    List<PersonOrgLevel> peList =
            groupedByLevelDef.getOrDefault(
                    "Principal Employer", new ArrayList<>());

    request.setAttribute("contList", contList);
    request.setAttribute("principalEmployers", peList);

    return "contractors/reconciliationHistory";
}

@GetMapping("/reconciliation/history/details")
@ResponseBody
public ResponseEntity<Map<String, Object>> getReconciliationHistoryDetails(
        @RequestParam("uploadId") Long uploadId,
        @RequestParam("reconType") String reconType,
        HttpSession session) {

    Map<String, Object> response = new HashMap<>();

    MasterUser user = session == null
            ? null
            : (MasterUser) session.getAttribute("loginuser");

    if (user == null) {
        response.put("status", "ERROR");
        response.put("message", "Session expired. Please login again.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    try {
        String type = reconType == null
                ? ""
                : reconType.trim().toUpperCase(Locale.ROOT);

        if (!Arrays.asList("BANK", "PF", "ESIC")
                .contains(type)) {
            response.put("status", "ERROR");
            response.put("message", "Invalid reconciliation type.");
            return ResponseEntity.badRequest().body(response);
        }

        List<Long> contractorIds =
                getAuthorizedContractorIds(session);

        boolean hasAccess =
                reconciliationService.hasReconciliationUploadAccess(
                        uploadId, type, contractorIds);

        if (!hasAccess) {
            response.put("status", "ERROR");
            response.put("message",
                    "Reconciliation record not found or access denied.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(response);
        }

        response.put("status", "SUCCESS");
        response.put("uploadId", uploadId);
        response.put("reconType", type);

        if ("BANK".equals(type)) {

            // Use your existing saved Bank details service method.
            // Replace this method name if yours differs.
            List<BankWageDetailDTO> details =
                    reconciliationService.getBankWageDetails(uploadId);

            response.put("details", details);
            response.put("mismatches", Collections.emptyList());

        } else {

            List<ReconciliationComplianceDetailDTO> details =
                    reconciliationService.getSavedComplianceDetails(uploadId);

            List<ReconciliationMismatchDTO> mismatches =
                    reconciliationService.getSavedMismatchDetails(uploadId);

            response.put("details", details);
            response.put("mismatches", mismatches);
        }

        return ResponseEntity.ok(response);

    } catch (Exception ex) {

        logger.error(
                "Error fetching reconciliation history details for uploadId="
                        + uploadId,
                ex);

        response.put("status", "ERROR");
        response.put("message",
                "Unable to retrieve reconciliation details.");

        return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}


@GetMapping("/reconciliation/history/view")
public String viewReconciliationDetails(
        @RequestParam("uploadId") Long uploadId,
        @RequestParam("reconType") String reconType,
        HttpServletRequest request,
        HttpServletResponse response) {

    HttpSession session = request.getSession(false);

    MasterUser user = session == null
            ? null
            : (MasterUser) session.getAttribute("loginuser");

    if (user == null) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return null;
    }

    String type = reconType == null
            ? ""
            : reconType.trim().toUpperCase(Locale.ROOT);

    if (!Arrays.asList("BANK", "PF", "ESIC").contains(type)
            || uploadId == null || uploadId <= 0) {

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return null;
    }

    List<Long> contractorIds =
            getAuthorizedContractorIds(session);

    boolean hasAccess =
            reconciliationService.hasReconciliationUploadAccess(
                    uploadId, type, contractorIds);

    if (!hasAccess) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return null;
    }

    request.setAttribute("uploadId", uploadId);
    request.setAttribute("reconType", type);

    return "contractors/reconciliationDetails";
}


}