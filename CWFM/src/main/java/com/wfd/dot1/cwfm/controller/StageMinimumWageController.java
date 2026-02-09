package com.wfd.dot1.cwfm.controller;


import com.wfd.dot1.cwfm.dto.PlantZoneMappingDTO;
import com.wfd.dot1.cwfm.dto.RequestorListDTO;
import com.wfd.dot1.cwfm.dto.StageMinimumWageDTO;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.service.PlantZoneMappingService;
import com.wfd.dot1.cwfm.service.StageMinimumWageService;
import com.wfd.dot1.cwfm.service.WorkmenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/stageMinimumWage")
@Slf4j
public class StageMinimumWageController {

    @Autowired
    private StageMinimumWageService stageMinimumWageService;

    @Autowired
    private PlantZoneMappingService service;

    @Autowired
    WorkmenService workmenService;

    @GetMapping("/form")
    public String showForm(Model model,  HttpServletRequest request) {
        List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMaster();
        Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
                .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));
        Map<String, String> attributeMapping = Map.of(
                "GENDER", "GenderOptions",
                "BLOODGROUP", "BloodGroup",
                "ACCESSAREA", "AccessArea",
                "ACADEMICS", "Academics",
                "WAGECATEGORY", "WageCategory",
                "BONUSPAYOUT", "BonusPayout",
                "ZONE", "Zone"
        );
        attributeMapping.forEach((type, attributeName) -> {
            List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
            request.setAttribute(attributeName, gmList1);
        });
        List<Map<String, Object>> states = service.getAllStates();
        request.setAttribute("States", states);
        List<Map<String, Object>> trade = service.getAlltrade();
        request.setAttribute("Trade", trade);
        List<Map<String, Object>> skill = service.getAllskill();
        request.setAttribute("Skill", skill);
        model.addAttribute("stageMinimumWage", new StageMinimumWageDTO());
        return "stateWage/stageMinimumWageForm";
        }



    @PostMapping("/save")
    public String saveStageMinimumWage(@ModelAttribute StageMinimumWageDTO dto, Model model) {
        try {
            String s = stageMinimumWageService.insertStageMinimumWage(dto);
            model.addAttribute("successMessage", s + " record(s) inserted successfully.");

        } catch (Exception e) {
            log.error("Error inserting StageMinimumWage record", e);
            model.addAttribute("errorMessage", "Failed to insert record: " + e.getMessage());
        }
        return "stateWage/stageMinimumWageForm";
    }







    @GetMapping("/list")
    public String listStageMinimumWageList(HttpServletRequest request, Model model) {
        try {
            List<StageMinimumWageDTO> mappings = stageMinimumWageService.getListOfSTATEWAGE(request);
            model.addAttribute("StageWage", mappings);
            return "stateWage/stageMinimumWageList";
        } catch (Exception e) {
            model.addAttribute("error", "Error fetching Stage Wage Listing: " + e.getMessage());
            return "errorPage"; // fallback JSP for errors
        }
    }



}