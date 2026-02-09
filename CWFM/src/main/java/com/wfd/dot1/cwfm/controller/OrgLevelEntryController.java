package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.dto.OrgLevelDefDTO;
import com.wfd.dot1.cwfm.dto.OrgLevelEntryDTO;
import com.wfd.dot1.cwfm.service.OrgLevelService;

@Controller
@RequestMapping("/org-level-entryController")
public class OrgLevelEntryController {

    @Autowired
    private OrgLevelService orgLevelService;  // Service to interact with DAO layer

//    @GetMapping("/org-level-entry")
//    public String showOrgLevelEntryPage(Model model) {
//        // Get all active organizational levels for the dropdown
//        List<OrgLevelDefDTO> orgLevels = orgLevelService.getActiveOrgLevels();
//        model.addAttribute("orgLevels", orgLevels);
//        return "orgLevels/orgLevelEntry";
//    }
    @GetMapping("/org-level-entry")
    public String getOrgLevelEntries(@RequestParam(required = false) Integer orgLevelDefId, Model model) {
        // Fetch all active org levels for dropdown
        List<OrgLevelDefDTO> orgLevels = orgLevelService.getActiveOrgLevels();
        model.addAttribute("orgLevels", orgLevels);

        // Handle entries based on orgLevelDefId if it's provided
        /*if (orgLevelDefId != null) {
            List<OrgLevelEntryDTO> entries = orgLevelService.getOrgLevelEntriesByDefId(orgLevelDefId);
            model.addAttribute("entries", entries); // Add entries to the model
        } else {
            model.addAttribute("entries", new ArrayList<>()); // Empty list if no org level selected
        }*/

        return "orgLevels/orgLevelEntry"; // Return the JSP view
    }


    @GetMapping("/org-level-entrys")
    public String getOrgLevelEntrie(@RequestParam(required = false) Integer orgLevelDefId, Model model) {
        // Fetch all active org levels for dropdown
        List<OrgLevelDefDTO> orgLevels = orgLevelService.getActiveOrgLevels();
        model.addAttribute("orgLevels", orgLevels);
        model.addAttribute("orgLevelDefId", orgLevelDefId);
        // Handle entries based on orgLevelDefId if it's provided
       /* if (orgLevelDefId != null) {
            List<OrgLevelEntryDTO> entries = orgLevelService.getOrgLevelEntriesByDefId(orgLevelDefId);
            model.addAttribute("entries", entries); // Add entries to the model
        } else {
            model.addAttribute("entries", new ArrayList<>()); // Empty list if no org level selected
        }*/

        return "orgLevels/orgLevelEntryView"; // Return the JSP view
    }

//
//    @GetMapping("/org-level-entry")
//    public String getOrgLevelEntries(@RequestParam(required = false) Integer orgLevelDefId, Model model) {
//    	 if (orgLevelDefId == null) {
//    	        // Set a default org level or handle the case when it's null
//    	        model.addAttribute("orgLevelDefId", null); // or provide a default value
//    	        model.addAttribute("entries", new ArrayList<>()); // Empty list if no selection is made
//    	    } else {
//    	        // Fetch entries based on the orgLevelDefId if it's not null
//    	        model.addAttribute("orgLevelDefId", orgLevelDefId);
//    	        model.addAttribute("entries", orgLevelService.getOrgLevelEntriesByDefId(orgLevelDefId));
//    	    }
//    	 List<OrgLevelDefDTO> orgLevels = orgLevelService.getActiveOrgLevels();
//    	 model.addAttribute("orgLevels", orgLevels);
//        return "orgLevels/orgLevelEntry"; // Your JSP view name
//    }

    @GetMapping("/showorg-level-entry/{orgLevelDefId}")
    public String showEntriesForOrgLevel(@PathVariable int orgLevelDefId, Model model) {
        System.out.println("Received orgLevelDefId: " + orgLevelDefId); // Debug log
        if (orgLevelDefId <= 0) {
            model.addAttribute("error", "Invalid organization level selected.");
            return "orgLevels/orgLevelEntry";
        }
        List<OrgLevelEntryDTO> entries = orgLevelService.getOrgLevelEntriesByDefId(orgLevelDefId);
        System.out.println("Retrieved entries: " + entries); // Debug log
        model.addAttribute("entries", entries);
       model.addAttribute("orgLevelDefId", orgLevelDefId);
        List<OrgLevelDefDTO> orgLevels = orgLevelService.getActiveOrgLevels();
        model.addAttribute("orgLevels", orgLevels);
        return "orgLevels/orgLevelEntryView";
    }

    @PostMapping("/save-org-level-entry")
    @ResponseBody
    public Map<String, Object> saveOrgLevelEntry(@RequestBody OrgLevelEntryDTO orgLevelEntry) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("Received Data: " + orgLevelEntry);

            if (orgLevelEntry.getName() == null || orgLevelEntry.getName().isEmpty()) {
                throw new IllegalArgumentException("Entry Name is required.");
            }

            if (orgLevelEntry.getOrgLevelDefId() == 0 ) {
                throw new IllegalArgumentException("Organization Level is required.");
            }
            boolean isDuplicate = orgLevelService.isDuplicateEntry(orgLevelEntry.getOrgLevelDefId(), orgLevelEntry.getName());
            if (isDuplicate) {
                response.put("success", false);
                response.put("message", "Duplicate Entry Name is not allowed for this Organization Level.");
                return response;
            }
            if (orgLevelEntry.getOrgLevelEntryId() == 0) {
                orgLevelService.saveOrgLevelEntry(orgLevelEntry);
            } else {
                orgLevelService.updateOrgLevelEntry(orgLevelEntry);
            }

            response.put("success", true);
            response.put("message", "Org Level Entry saved successfully!");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error saving Org Level Entry: " + e.getMessage());
        }
        return response;
    }




    @PostMapping("/delete-org-level-entry")
    public String deleteOrgLevelEntry(@RequestParam int orgLevelEntryId, @RequestParam int orgLevelDefId, Model model) {
        // Perform the deletion
        orgLevelService.deleteOrgLevelEntry(orgLevelEntryId);
        List<OrgLevelEntryDTO> entries = orgLevelService.getOrgLevelEntriesByDefId(orgLevelDefId);
        System.out.println("Retrieved entries: " + entries); // Debug log
        model.addAttribute("entries", entries);
        model.addAttribute("orgLevelDefId", orgLevelDefId);
        List<OrgLevelDefDTO> orgLevels = orgLevelService.getActiveOrgLevels();
        model.addAttribute("orgLevels", orgLevels);
        return "orgLevels/orgLevelEntryView";
        // Redirect back to the org level entry page
//        return "redirect:/org-level-entry/" + orgLevelDefId;
    }
    
    @GetMapping("/edit-org-level-entry/{orgLevelEntryId}")
    public String editOrgLevelEntry(@PathVariable int orgLevelEntryId, Model model) {
        try {
            OrgLevelEntryDTO entry = (OrgLevelEntryDTO) orgLevelService.getOrgLevelEntryById(orgLevelEntryId);
            model.addAttribute("entry", entry);

            // Fetch active organization levels for dropdown
            List<OrgLevelDefDTO> orgLevels = orgLevelService.getActiveOrgLevels();
            model.addAttribute("orgLevels", orgLevels);

            return "orgLevels/orgLevelEntryEdit"; // Return the JSP view for editing
        } catch (Exception e) {
            model.addAttribute("error", "Error retrieving Org Level Entry: " + e.getMessage());
            return "orgLevels/orgLevelEntry"; // Redirect to the main entry page in case of an error
        }
    }

    @PostMapping("/update-org-level-entry")
    @ResponseBody
    public Map<String, Object> updateOrgLevelEntry(@RequestBody OrgLevelEntryDTO orgLevelEntry) {
        Map<String, Object> response = new HashMap<>();
        try {
            orgLevelService.updateOrgLevelEntry(orgLevelEntry);
            response.put("success", true);
            response.put("message", "Org Level Entry updated successfully!");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error updating Org Level Entry: " + e.getMessage());
        }
        return response;
    }
    
    @GetMapping("/org-level-entry-dropdowns")
    @ResponseBody
    public List<Map<String, Object>> getOrgLevelEntriesData(@RequestParam Integer orgLevelDefId,Model model) {
        List<Map<String, Object>> entries = new ArrayList<>();
        
        OrgLevelDefDTO levelDef = orgLevelService.getOrgLevelById(orgLevelDefId);
        if (levelDef != null) {
            String levelName = levelDef.getName();

            switch (levelName) {
                case "Principal Employer":
                    entries = orgLevelService.getPrincipalEmployers()
                            .stream()
                            .map(pe -> {
                                Map<String, Object> map = new HashMap<>();
                                map.put("entryName", pe.getCode());
                                map.put("description", pe.getName());
                                return map;
                            })
                            .toList();
                    break;

                case "Contractor":
                    entries = orgLevelService.getContractors()
                            .stream()
                            .map(c -> {
                                Map<String, Object> map = new HashMap<>();
                                map.put("entryName", c.getVendorCode());
                                map.put("description", c.getContractorName());
                                return map;
                            })
                            .toList();
                    break;
                    
                case "Work Order":
                    entries = orgLevelService.getWorkorders()
                            .stream()
                            .map(w -> {
                                Map<String, Object> map = new HashMap<>();
                                map.put("entryName", w.getSapWorkorderNumber());
                                map.put("description", w.getName());
                                return map;
                            })
                            .toList();
                    break;

                case "Dept":
                case "Area":
                    entries = orgLevelService.getGeneralMasters(levelName)
                            .stream()
                            .map(gm -> {
                                Map<String, Object> map = new HashMap<>();
                                map.put("entryName", gm.getGmName());
                                map.put("description", gm.getGmDescription());
                                return map;
                            })
                            .toList();
                    break;
            }
        }
        return entries;  // ✅ this will return JSON
        
    }


}
