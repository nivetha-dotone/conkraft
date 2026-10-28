package com.wfd.dot1.cwfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wfd.dot1.cwfm.dao.RegistrationLinkDao;
import com.wfd.dot1.cwfm.pojo.DeptMapping;
import com.wfd.dot1.cwfm.pojo.RegistrationLink;
import com.wfd.dot1.cwfm.pojo.RegistrationLinkDetails;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.RegistrationLinkService;
import com.wfd.dot1.cwfm.service.WorkmenService;

@Controller
@RequestMapping("/register")
public class PublicRegistrationController {
	@Autowired
	CommonService commonService;
    
    @Autowired
	WorkmenService workmenService;

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
        
       

  

        model.addAttribute("selectedDepartmentName", departmentName);
        model.addAttribute("selectedAreaName", areaName);

  

       
        model.addAttribute("selectedDepartmentId", departmentName);
        model.addAttribute("selectedAreaId", areaName);

        return "contractWorkmen/PublicSelfRegistration";

    }
}