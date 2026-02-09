package com.wfd.dot1.cwfm.controller;

import com.wfd.dot1.cwfm.dto.PlantZoneMappingDTO;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.GatePassToOnBoardService;
import com.wfd.dot1.cwfm.service.PlantZoneMappingService;
import com.wfd.dot1.cwfm.service.WorkmenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/plantZone")
public class PlantZoneMappingController {

    @Autowired
    private PlantZoneMappingService service;
    @Autowired
    WorkmenService workmenService;
    @Autowired
    CommonService commonService;
    private static final Logger log = LoggerFactory.getLogger(PlantZoneMappingController.class.getName());


    @PutMapping("/toggle/{refMppId}")
    public String togglePlantZoneMapping(@PathVariable("refMppId") Integer refMppId, Model model) {
        try {
            String message = service.updatePlantZoneMapping(refMppId);
            model.addAttribute("successMessage", message);

        } catch (Exception e) {
            log.error("Error updating PlantZoneMapping record", e);
            model.addAttribute("errorMessage", "Failed to update record: " + e.getMessage());
        }

        // 👇 redirect or return view name (adjust as per your JSP/Thymeleaf setup)
        return "stateWage/plantZoneMappingList";

    }

    @GetMapping("/form")
    public String showForm(Model model, HttpServletRequest request) {
        model.addAttribute("plantZoneMapping", new PlantZoneMappingDTO());

        HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
        List<PersonOrgLevel> orgLevel = service.getAllPRINCIPLE();
        Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
                .collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
        List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
        //List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
        //List<PersonOrgLevel> subdepartments = groupedByLevelDef.getOrDefault("Area", new ArrayList<>());
        request.setAttribute("PrincipalEmployer", peList);

        // Zones (already handled)
        List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMaster();

        // Grouping the CmsGeneralMaster objects by gmType
        Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
                .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

        // Define the types and their corresponding request attribute names
        Map<String, String> attributeMapping = Map.of(
                "GENDER", "GenderOptions",
                "BLOODGROUP", "BloodGroup",
                "ACCESSAREA", "AccessArea",
                "ACADEMICS", "Academics",
                "WAGECATEGORY", "WageCategory",
                "BONUSPAYOUT", "BonusPayout",
                "ZONE", "Zone"
        );

        // Iterate over the attribute mappings and set the request attributes dynamically
        attributeMapping.forEach((type, attributeName) -> {
            List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
            request.setAttribute(attributeName, gmList1);
        });

        List<Map<String, Object>> states = service.getAllStates();
        request.setAttribute("States", states);

        return "stateWage/plantZoneMappingForm";
    }

    @GetMapping("/list")
    public String listPlantZoneMappings(HttpServletRequest request, Model model) {
        try {
            // Fetch list from service
            List<PlantZoneMappingDTO> mappings = service.getListOfPlantZoneMappingDTO(request);

            // Add to model so JSP can display it
            model.addAttribute("mappings", mappings);

            return "stateWage/plantZoneMappingList";  // JSP page name (plantZoneMappingList.jsp)
        } catch (Exception e) {
            model.addAttribute("error", "Error fetching plant-zone mappings: " + e.getMessage());
            return "errorPage"; // fallback JSP for errors
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveMapping(
            @ModelAttribute("plantZoneMapping") PlantZoneMappingDTO dto) {

        try {
            log.info("[v0] Saving Plant Zone Mapping - Unit: {}, State: {}, Zone: {}",
                    dto.getUnitIdS(), dto.getStateS(), dto.getZoneS());

            String result = service.insertPlantZoneMapping(dto);

            log.info("[v0] Successfully saved Plant Zone Mapping");
            return ResponseEntity.ok()
                    .header("Content-Type", "text/plain;charset=UTF-8")
                    .body(result);

        } catch (PlantZoneMappingService.DuplicateEntryException e) {
            log.warn("[v0] Duplicate entry attempted: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("Content-Type", "text/plain;charset=UTF-8")
                    .body(e.getMessage());
        } catch (Exception e) {
            log.error("[v0] Error saving Plant Zone Mapping: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "text/plain;charset=UTF-8")
                    .body("Error: " + e.getMessage());
        }
    }
}