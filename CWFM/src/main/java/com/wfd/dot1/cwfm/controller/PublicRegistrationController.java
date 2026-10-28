package com.wfd.dot1.cwfm.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wfd.dot1.cwfm.dao.RegistrationLinkDao;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.DeptMapping;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.RegistrationLink;
import com.wfd.dot1.cwfm.pojo.RegistrationLinkDetails;
import com.wfd.dot1.cwfm.pojo.Zone;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.PublicSelfRegistrationService;
import com.wfd.dot1.cwfm.service.RegistrationLinkService;
import com.wfd.dot1.cwfm.service.WorkmenService;

@Controller
@RequestMapping("/register")
public class PublicRegistrationController {
	@Autowired
	CommonService commonService;
    
    @Autowired
	WorkmenService workmenService;

    @Autowired
    private PublicSelfRegistrationService publicSelfRegistrationService;

    private final RegistrationLinkService registrationLinkService;
    
    @Autowired
     RegistrationLinkDao registrationLinkDao;

    public PublicRegistrationController(
            RegistrationLinkService registrationLinkService) {
        this.registrationLinkService = registrationLinkService;
    }

    @GetMapping("/{token}")
    public String openRegistrationPage(
            @PathVariable("token") String token,
            Model model) {

        RegistrationLink link;

        try {
            link = registrationLinkService.validateAndGetLink(token);
            
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "contractWorkmen/registrationLinkError";
        }

        model.addAttribute("registrationLink", link);
        model.addAttribute("registrationToken", token);
        
        String userAccount = workmenService.getUserAccountByUserId(link.getCreatedBy());
        
        
        RegistrationLinkDetails details =
                registrationLinkDao.getRegistrationLinkDetails(link.getLinkId());

        if (details == null) {
            throw new IllegalStateException(
                    "Registration link employment details not found");
        }

        model.addAttribute("selectedEicId", link.getEicId());

        model.addAttribute("registrationToken", token);
        model.addAttribute("registrationLink", link);
        model.addAttribute("employmentDetails", details);
        
        List<DeptMapping> departmentList =
                workmenService.getAllDepartmentsOnPE(link.getUnitId());

        String departmentName = departmentList.stream()
                .filter(d -> link.getDepartmentId().equals(
                        String.valueOf(d.getDepartmentId())))
                .map(DeptMapping::getDepartment)
                .findFirst()
                .orElse("");

        List<DeptMapping> areaList =
                workmenService.getAllSubDepartments(
                        link.getUnitId(), link.getDepartmentId());

        String areaName = areaList.stream()
                .filter(a -> link.getAreaId().equals(
                        String.valueOf(a.getSubDepartmentId())))
                .map(DeptMapping::getSubDepartment)
                .findFirst()
                .orElse("");
        
       
        List<CmsGeneralMaster> gmList =
                workmenService.getAllGeneralMaster(); // Replace with your existing method

        Map<String, List<CmsGeneralMaster>> groupedByGmType =
                gmList.stream()
                      .collect(Collectors.groupingBy(
                              CmsGeneralMaster::getGmType));

        Map<String, String> attributeMapping = Map.of(
                "GENDER", "GenderOptions",
                "BLOODGROUP", "BloodGroup",
                "ACCESSAREA", "AccessArea",
                "ACADEMICS", "Academics",
                "WAGECATEGORY", "WageCategory",
                "BONUSPAYOUT", "BonusPayout",
                "WORKMENTYPE", "WorkmenType",
                "PROFICIENCY", "Proficiency"
        );

        attributeMapping.forEach((type, attributeName) -> {
            model.addAttribute(
                    attributeName,
                    groupedByGmType.getOrDefault(
                            type, Collections.emptyList())
            );
        });
        List<Zone> zones = workmenService.getAllZonesBasedOnPE(link.getUnitId());
        model.addAttribute("Zones", zones);
  

        model.addAttribute("selectedDepartmentName", departmentName);
        model.addAttribute("selectedAreaName", areaName);

  

       
        model.addAttribute("selectedDepartmentId", departmentName);
        model.addAttribute("selectedAreaId", areaName);
        
        String unitId = String.valueOf(link.getUnitId());
        String departmentId = String.valueOf(link.getDepartmentId());
        String selectedEicId = String.valueOf(link.getEicId());

        String eicName = selectedEicId;

        List<MasterUser> eicList =
                workmenService.getAllEicManager(unitId, departmentId);

        if (eicList != null) {
            for (MasterUser eic : eicList) {
                if (selectedEicId.equals(String.valueOf(eic.getUserId()))) {
                    eicName = eic.getFullName();
                    break;
                }
            }
        }

        model.addAttribute("selectedEicName", eicName);

        return "contractWorkmen/PublicSelfRegistration";

    }
    @PostMapping(value = "/{token}/submit", consumes = "multipart/form-data")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> submitRegistration(
            @PathVariable("token") String token,
            @RequestParam Map<String, String> fields,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "aadharFile", required = false) MultipartFile aadharFile,
            @RequestParam(value = "appointmentFile", required = false) MultipartFile appointmentFile,
            @RequestParam(value = "policeFile", required = false) MultipartFile policeFile) {
        Map<String, MultipartFile> uploads = new HashMap<>();
        uploads.put("imageFile", imageFile);
        uploads.put("aadharFile", aadharFile);
        uploads.put("appointmentFile", appointmentFile);
        uploads.put("policeFile", policeFile);
        try {
            long id = publicSelfRegistrationService.submit(token, fields, uploads);
            return ResponseEntity.ok(Map.of("success", true, "registrationId", id,
                    "message", "Registration submitted for supervisor review"));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", ex.getMessage()));
        } catch (Exception ex) {
            // Log server-side without printing Aadhaar, request fields, or token.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false, "message", "Unable to submit registration. Please contact support."));
        }
    }
    
    @GetMapping("/success")
    public String registrationSuccess(
           // @RequestParam("ref") Long registrationId,
            Model model) {

        //model.addAttribute("registrationId", registrationId);

        return "contractWorkmen/registrationSuccess";
    }
}