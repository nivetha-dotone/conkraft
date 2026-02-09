package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.dto.OrgLevelDefDTO;
import com.wfd.dot1.cwfm.pojo.OrgLevel;
import com.wfd.dot1.cwfm.service.OrgLevelService;

@Controller
@RequestMapping("/org-level")
public class OrgLevelController {
    @Autowired
    private OrgLevelService orgLevelService;
    @PostMapping("/save")
    public ResponseEntity<?> saveOrgLevels(@RequestBody List<OrgLevelDefDTO> orgLevels) throws Exception {
        System.out.println("Received orgLevels: " + orgLevels);

        List<OrgLevelDefDTO> validEntries = new ArrayList<>();
        List<OrgLevelDefDTO> duplicateEntries = new ArrayList<>();
        List<OrgLevelDefDTO> updates = new ArrayList<>();
        List<OrgLevelDefDTO> newEntries = new ArrayList<>();
        List<OrgLevelDefDTO> invalidIdEntries = new ArrayList<>();

        Set<String> seen = new HashSet<>();

        System.out.println("Total orgLevels: " + orgLevels.size());

        for (OrgLevelDefDTO orgLevel : orgLevels) {
            System.out.println("Processing OrgLevel: " + orgLevel);

            String key = (orgLevel.getName() + "|" + orgLevel.getShortName() + "|" + orgLevel.getOrgHierarchyLevel()).toLowerCase();

            if (orgLevel.getOrgLevelDefId() != null && orgLevel.getOrgLevelDefId() > 0) {
                // Check if the ID exists for update
                if (orgLevelService.existsById(orgLevel.getOrgLevelDefId())) {
                    System.out.println("Updating record with ID: " + orgLevel.getOrgLevelDefId());
                    updates.add(orgLevel);
                } else {
                    System.out.println("Invalid ID detected: " + orgLevel.getOrgLevelDefId());
                    invalidIdEntries.add(orgLevel);  // Instead of marking as duplicate
                }
            } else {
                // Handle new entries
                if (seen.contains(key) || orgLevelService.existsByNameShortNameAndHierarchy(
                        orgLevel.getName(), orgLevel.getShortName(), orgLevel.getOrgHierarchyLevel())) {
                    System.out.println("Duplicate detected: " + key);
                    duplicateEntries.add(orgLevel);
                } else {
                    System.out.println("New entry detected: " + key);
                    newEntries.add(orgLevel);
                    seen.add(key);
                }
            }
        }

        // Save new entries
        if (!newEntries.isEmpty()) {
            orgLevelService.saveOrgLevels(newEntries);
        }

        // Update existing entries
        if (!updates.isEmpty()) {
            orgLevelService.updateOrgLevel(updates);
        }

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        if (!duplicateEntries.isEmpty() || !invalidIdEntries.isEmpty()) {
            response.put("status", "partial");
            response.put("message", "Some entries were not processed due to duplicates or invalid IDs.");
            response.put("duplicates", duplicateEntries);
            response.put("invalidIds", invalidIdEntries);
        } else {
            response.put("status", "success");
            response.put("message", "All entries processed successfully.");
        }

        return ResponseEntity.ok(response);
    }

//    @PostMapping("/save")
//    public ResponseEntity<?> saveOrgLevels(@RequestBody List<OrgLevelDefDTO> orgLevels) throws Exception {
//    	System.out.println("Received orgLevels: " + orgLevels);
//        List<OrgLevelDefDTO> validEntries = new ArrayList<>();
//        List<OrgLevelDefDTO> duplicateEntries = new ArrayList<>();
//        List<OrgLevelDefDTO> updates = new ArrayList<>();
//        List<OrgLevelDefDTO> newEntries = new ArrayList<>();
//
//        // Track duplicates locally and against the database
//        Set<String> seen = new HashSet<>();
//        System.out.println("Total orgLevels: " + orgLevels.size());
//        orgLevels.forEach(orgLevel -> {
//            System.out.println("Org Level Name: " + orgLevel.getName() +
//                               ", Short Name: " + orgLevel.getShortName() +
//                               ", Hierarchy: " + orgLevel.getOrgHierarchyLevel());
//        });
//
//        for (OrgLevelDefDTO orgLevel : orgLevels) {
//            System.out.println("Processing OrgLevel with ID: " + orgLevel.getOrgLevelDefId());
//
//            String key = (orgLevel.getName() + "|" + orgLevel.getShortName() + "|" + orgLevel.getOrgHierarchyLevel()).toLowerCase();
//            System.out.println("Generated key: " + key);
//
//            if (orgLevel.getOrgLevelDefId() != null && orgLevel.getOrgLevelDefId() > 0) {
//                // Handle existing record with ID
//                System.out.println("orgLevelDefId is present and greater than 0. Checking if exists by ID...");
//                if (orgLevelService.existsById(orgLevel.getOrgLevelDefId())) {
//                    System.out.println("Record with ID: " + orgLevel.getOrgLevelDefId() + " found. Marking for update.");
//                    updates.add(orgLevel); // If the record exists, mark for update
//                } else {
//                    System.out.println("Record with ID: " + orgLevel.getOrgLevelDefId() + " not found. Marking as duplicate.");
//                    duplicateEntries.add(orgLevel); // If the ID is not found, mark as duplicate
//                }
//            } if (orgLevel.getOrgLevelDefId() == null || orgLevel.getOrgLevelDefId() <= 0) {
//                // Handle new entry
//                System.out.println("orgLevelDefId is null or <= 0. Checking for duplicates based on Name, ShortName, and Hierarchy...");
//                if (seen.contains(key) || orgLevelService.existsByNameShortNameAndHierarchy(orgLevel.getName(), orgLevel.getShortName(), orgLevel.getOrgHierarchyLevel())) {
//                    System.out.println("Duplicate detected for Name: " + orgLevel.getName() + ", Short Name: " + orgLevel.getShortName() + ", Hierarchy Level: " + orgLevel.getOrgHierarchyLevel());
//                    duplicateEntries.add(orgLevel);
//                } else {
//                    System.out.println("New entry detected for Name: " + orgLevel.getName() + ", Short Name: " + orgLevel.getShortName() + ", Hierarchy Level: " + orgLevel.getOrgHierarchyLevel());
//                    newEntries.add(orgLevel); // If the entry is valid and new, add to the newEntries
//                    seen.add(key);
//                }
//            }
//        }
//
//
//
//        // Save new entries
//        if (!newEntries.isEmpty()) {
//            orgLevelService.saveOrgLevels(newEntries);
//        }
//
//        // Update existing entries
//        if (!updates.isEmpty()) {
//            orgLevelService.updateOrgLevel(updates); // Ensure this method can accept a list
//        }
//
//        // Prepare response
//        Map<String, Object> response = new HashMap<>();
//        if (!duplicateEntries.isEmpty()) {
//            response.put("status", "partial");
//            response.put("message", "Some entries were not saved due to duplicates or invalid updates.");
//            response.put("duplicates", duplicateEntries);
//        } else {
//            response.put("status", "success");
//            response.put("message", "All entries saved successfully.");
//        }
//
//        return ResponseEntity.ok(response);
//    }





//    
//    @PostMapping("/save")
//    public ResponseEntity<String> saveOrgLevels(@RequestBody List<OrgLevelDefDTO> orgLevels) {
//    	ResponseEntity<String> response;
//        try {
//            for (OrgLevelDefDTO orgLevel : orgLevels) {
//                // Log the orgLevelName to verify it's not null
//                System.out.println("Received orgLevelName: " + orgLevel.getName() + " --- " + orgLevel.getOrgHierarchyLevel());
//
//                // Validate required fields
//                if (orgLevel.getName() == null || orgLevel.getName().isEmpty()) {
//                	 response =  ResponseEntity.badRequest().body("orgLevelName cannot be null or empty.");
//                }
//                if (orgLevel.getShortName() == null || orgLevel.getShortName().isEmpty()) {
//                	 response =  ResponseEntity.badRequest().body("shortName cannot be null or empty.");
//                }
//                if (orgLevel.getOrgHierarchyLevel() == 0) {
//                	 response =  ResponseEntity.badRequest().body("orgHierarchyLevel must be greater than 0.");
//                }
//
//                // Check for duplicate entries in the database
//                boolean exists = orgLevelService.existsByNameShortNameAndHierarchy(
//                        orgLevel.getName(), orgLevel.getShortName(), orgLevel.getOrgHierarchyLevel());
//
//                if (exists) {
//                	 response =  ResponseEntity.badRequest().body(
//                            "Duplicate entry detected for Name: " + orgLevel.getName() +
//                                    ", Short Name: " + orgLevel.getShortName() +
//                                    ", Hierarchy Level: " + orgLevel.getOrgHierarchyLevel());
//                }
//
//                // Set default values if necessary
//                if (orgLevel.getMinimumLength() == 0) {
//                    orgLevel.setMinimumLength(0); // set default value
//                }
//                if (orgLevel.getMaximumLength() == 0) {
//                    orgLevel.setMaximumLength(255); // set default value
//                }
//                if (!orgLevel.isOverrideSwitch()) {
//                    orgLevel.setOverrideSwitch(false); // set default value
//                }
//                orgLevel.setUpdatedByUsrAcctId(0);
//            }
//
//            // Save valid org levels
//            orgLevelService.saveOrgLevels(orgLevels);
//            response =  ResponseEntity.ok("Organizational Levels saved successfully.");
//        } catch (Exception e) {
//        	 response =  ResponseEntity.badRequest().body(e.getMessage());
//        }
//        System.out.println("Response: " + response);
//        return response;
//    }

//    @PostMapping("/save")
//    public ResponseEntity<String> saveOrgLevels(@RequestBody List<OrgLevelDefDTO> orgLevels) {
//        try {
//            for (OrgLevelDefDTO orgLevel : orgLevels) {
//                // Log the orgLevelName to verify it's not null
//                System.out.println("Received orgLevelName: " + orgLevel.getName()+"hhhh---"+orgLevel.getOrgHierarchyLevel());
//
//                if (orgLevel.getName() == null || orgLevel.getName().isEmpty()) {
//                    return ResponseEntity.badRequest().body("orgLevelName cannot be null or empty.");
//                }
//
//                // Set default values if necessary
//                if (orgLevel.getMinimumLength() == 0) {
//                    orgLevel.setMinimumLength(0); // set default value
//                }
//                if (orgLevel.getMaximumLength() == 0) {
//                    orgLevel.setMaximumLength(255); // set default value
//                }
//                if (!orgLevel.isOverrideSwitch()) {
//                    orgLevel.setOverrideSwitch(false); // set default value
//                }
//                orgLevel.setUpdatedByUsrAcctId(0);
//            }
//            orgLevelService.saveOrgLevels(orgLevels);
//            return ResponseEntity.ok("Organizational Levels saved successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    
    @GetMapping("/list")
    public String loadOrgLevelPage(Model model) {
        // Fetch data from the database
        List<OrgLevel> orgLevels = orgLevelService.getAllOrgLevels();

        // Add data to the model
        model.addAttribute("orgLevels", orgLevels);

        // Return the JSP view name
        return "orgLevels/orgLevels"; // Ensure this maps to your JSP file in the view resolver
    }
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteOrgLevel(@PathVariable int id) {
//        try {
//            orgLevelService.deleteOrgLevel(id);
//            return ResponseEntity.ok("Organizational Level deleted successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//    @PostMapping("/deleteGMTypes")
//    @ResponseBody
//    public ResponseEntity<Map<String, Object>> deleteGMTypes(@RequestBody List<Long> gmTypeIds) {
//    	
//    }
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrgLevels(@RequestBody List<Integer> ids) {
        List<Integer> failedDeletions = new ArrayList<>();
        System.out.println("Received DELETE request for Org Levels with IDs: " + ids);
        
        try {
            for (int id : ids) {
                try {
                    orgLevelService.deleteOrgLevel(id);  // Deleting each Org Level
                } catch (Exception e) {
                    failedDeletions.add(id);  // Collect failed deletions
                    System.err.println("Failed to delete Org Level with ID: " + id);
                }
            }
            
            if (failedDeletions.isEmpty()) {
                return ResponseEntity.ok("Organizational Levels deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .body("Failed to delete the following Org Levels: " + failedDeletions);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateOrgLevels(@RequestBody List<Integer> ids) {
        List<Integer> failedUpdates = new ArrayList<>();
        System.out.println("Received UPDATE request for Org Levels with IDs: " + ids);

        try {
            for (int id : ids) {
                try {
                    orgLevelService.updateOrgLevel(id); // Update logic by ID
                } catch (Exception e) {
                    failedUpdates.add(id);
                    System.err.println("Update failed for ID " + id + ": " + e.getMessage());
                }
            }

            if (failedUpdates.isEmpty()) {
                return ResponseEntity.ok("Organizational Levels updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .body("Failed to update the following Org Levels: " + failedUpdates);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }
    @PostMapping("/deleteOrgLevel")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteOrgLevel(@RequestBody List<Long> orgLevelDefId) {
        System.out.println("Received orgLevelDefId: " + orgLevelDefId);
        Map<String, Object> response = new HashMap<>();
        if (orgLevelDefId == null || orgLevelDefId.isEmpty()) {
            response.put("success", false);
            response.put("message", "No IDs received.");
            return ResponseEntity.badRequest().body(response);
        }
        try {
        	orgLevelService.deleteOrgLevel(orgLevelDefId);
            response.put("success", true);
            response.put("message", "Selected deleteOrgLevel deleted successfully.");
        } catch (DataIntegrityViolationException ex) {
            response.put("success", false);
            response.put("message", ex.getMessage());
        } catch (Exception ex) { // Catch any other exceptions
            response.put("success", false);
            response.put("message", "An unexpected error occurred: " + ex.getMessage());
            ex.printStackTrace(); // Print stack trace for debugging
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping("/showOrgLevelPage")
    public String showOrgLevelPage(Model model) {
        List<OrgLevel> name = orgLevelService.getAllOrgLevel();
        model.addAttribute("name", name);
        return "org-level/orgLevels";
    }



   
}
