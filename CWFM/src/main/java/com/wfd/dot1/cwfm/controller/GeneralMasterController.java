package com.wfd.dot1.cwfm.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.dto.GeneralMasterDTO;
import com.wfd.dot1.cwfm.dto.SaveMappingRequest;
import com.wfd.dot1.cwfm.pojo.CMSGMType;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.OrgLevel;
import com.wfd.dot1.cwfm.service.CommonService;

@Controller
@RequestMapping("/generalController")
public class GeneralMasterController {

    @Autowired
    private CommonService commonService;

    // Display GMType page
    @GetMapping("/gmType")
    public String showGMTypePage(Model model) {
        List<CMSGMType> gmTypes = commonService.getAllGMTypes();
        model.addAttribute("gmTypes", gmTypes);
        return "generalMaster/gmType";
    }
    // Display GMType page
//    @GetMapping("/gmType")
//    public String showGMTypePage(@RequestParam(required = false) Long pageId, @RequestParam(required = false) Long selectedRoleId, HttpServletRequest request, HttpServletResponse response,Model model) {
//    	HttpSession session = request.getSession(false); 
//		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
//		 if (pageId != null) {
//		        session.setAttribute("selectedPageId", pageId);
//		    } else {
//		        pageId = (Long) session.getAttribute("selectedPageId");
//		    }
//		    
//		   // Long selectedRoleId = (Long) session.getAttribute("selectedRoleId");
//		    System.out.println("pageId---"+pageId);
//		    System.out.println("selectedRoleId---"+selectedRoleId);
//		    session.setAttribute("selectedPageId", pageId); // Set pageId
//		    session.setAttribute("selectedRoleId", selectedRoleId); 
//		    List<CMSRoleRights> userRights = commonService.getRoleRightsByRoleIdAndPageId(selectedRoleId, pageId); // 10029 = Contractor Page ID
//		    request.setAttribute("userRights", userRights);
//    	List<CMSGMType> gmTypes = commonService.getAllGMTypes();
//    	System.out.println("pageId (after transaction): " + pageId);
//    	System.out.println("selectedRoleId (after transaction): " + selectedRoleId);
//        model.addAttribute("gmTypes", gmTypes);
//        
//        return "generalMaster/gmType";
//    }
    // Save GMType
//    @PostMapping("/saveGMType")
//    public String saveGMType(@RequestParam("gmTypeName") String gmTypeName) {
//        if (!commonService.isGMTypeNameDuplicate(gmTypeName)) {
//            CMSGMType gmType = new CMSGMType();
//            gmType.setGmType(gmTypeName);
//            commonService.saveGMType(gmType);
//        }
//        return  "redirect:/CWFM/generalController/gmType";
//    }
//    @PostMapping("/saveGMType")
//    public String saveGMType(@RequestParam("gmTypeName") String gmTypeName, RedirectAttributes redirectAttributes) {
//        if (!commonService.isGMTypeNameDuplicate(gmTypeName)) {
////            CMSGMType gmType = new CMSGMType();
////            gmType.setGmType(gmTypeName);
//            commonService.saveGMType(gmTypeName);
//            redirectAttributes.addFlashAttribute("successMessage", "GM Type saved successfully!");
//        } else {
//        	   redirectAttributes.addFlashAttribute("errorMessage", "Failed to save GM Type.");
//            // Optionally, handle the case when the GM Type name is a duplicate
//        }
//        return "redirect:/generalController/gmType";
//    }

    

    

        @PostMapping("/saveGMType")
        public ResponseEntity<Map<String, String>> saveGMType(@RequestParam("gmTypeName") String gmTypeName) {
            Map<String, String> response = new HashMap<>();

            // Trim and check if input is empty
            if (gmTypeName.trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "GM Type name cannot be empty!");
                return ResponseEntity.badRequest().body(response);
            }

            // Check if GM Type is a duplicate
            if (commonService.isGMTypeNameDuplicate(gmTypeName)) {
                response.put("status", "error");
                response.put("message", "Warning: GM Type name already exists!");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            // Save GM Type if it's valid (NO SUCCESS MESSAGE RETURNED)
            commonService.saveGMType(gmTypeName);
            return ResponseEntity.ok().build(); // No response needed on success
        }
    

    
    
    // Delete GMType hibernate
//    @PostMapping("/deleteGMType")
//    public String deleteGMType(@RequestParam("gmTypeId") Long gmTypeId) {
//        commonService.deleteGMType(gmTypeId);
//        return "generalMaster/gmType";
//    }

//    @PostMapping("/deleteGMTypes")
//    @ResponseBody
//    public ResponseEntity<Map<String, Object>> deleteGMTypes(@RequestBody List<Long> gmTypeIds) {
//        Map<String, Object> response = new HashMap<>();
//        List<Long> undeletableIds = new ArrayList<>();
//
//        for (Long gmTypeId : gmTypeIds) {
//            try {
//                commonService.deleteGMType(gmTypeId); // Attempt to delete
//            } catch (DataIntegrityViolationException ex) {
//                undeletableIds.add(gmTypeId); // Collect IDs that can't be deleted
//            }
//        }
//
//        if (undeletableIds.isEmpty()) {
//            response.put("success", true);
//        } else {
//            response.put("success", false);
//            response.put("message", "Some GM Types cannot be deleted due to dependencies: " + undeletableIds);
//        }
//
//        return ResponseEntity.ok(response);
//    }
    @PostMapping("/deleteGMTypes")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteGMTypes(@RequestBody List<Long> gmTypeIds) {
        System.out.println("Received gmTypeIds: " + gmTypeIds);
        Map<String, Object> response = new HashMap<>();
        if (gmTypeIds == null || gmTypeIds.isEmpty()) {
            response.put("success", false);
            response.put("message", "No IDs received.");
            return ResponseEntity.badRequest().body(response);
        }
        try {
            commonService.deleteGMType(gmTypeIds);
            response.put("success", true);
            response.put("message", "Selected GM Types deleted successfully.");
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
    
    @GetMapping("/generalMaster")
    public String showGeneralMasterPage(Model model) {
        List<CMSGMType> gmTypes = commonService.getAllGMTypes();  // Fetch GM Types for dropdown
       // List<GeneralMasterDTO> generalMasters = commonService.getAllCmsGeneralMasters();
        model.addAttribute("gmTypes", gmTypes);  // Add GM Types to model
      //  model.addAttribute("generalMasters", generalMasters);
        return "generalMaster/generalMaster";
    }

    // Save GeneralMaster with gmTypeId from dropdown
//    @PostMapping("/saveGeneralMaster")
//    public String saveGeneralMaster(@RequestParam("gmTypeId") Long gmTypeId,
//                                    @RequestParam("masterName") String masterName,
//                                    @RequestParam("masterValue") String masterValue) {
//        if (!commonService.isCmsGeneralMasterDuplicate(masterName, masterValue)) {
//        	CmsGeneralMaster gm = new CmsGeneralMaster();
//            CMSGMType gmType = commonService.findById(gmTypeId);  // Fetch GM Type by ID
//            gm.setGmTypes(gmType);  // Set GM Type
//            gm.setGmName(masterName);
//            gm.setGmdescription(masterValue);
//            gm.setUpdatedby("Admin");
//            commonService.saveCmsGeneralMaster(gm);
//        }
//        return "generalMaster/generalMaster";
//    }
    
//    @GetMapping("/getGmData")
//    public ResponseEntity<List<GeneralMasterDTO>> getGmData(@RequestParam("gmTypeId") Long gmTypeId) {
//        List<GeneralMasterDTO> gmData = commonService.getGeneralMastersWithTypeName(gmTypeId);
//        return ResponseEntity.ok(gmData);
//    }
   

    @GetMapping("/getGmData/{gmTypeId}")
    public String showEntriesForOrgLevel(@PathVariable Long gmTypeId, Model model) {
        System.out.println("Received orgLevelDefId: " + gmTypeId); // Debug log
        if (gmTypeId <= 0) {
            model.addAttribute("error", "Invalid General Type selected.");
            return "generalMaster/generalMaster";
        }
        List<GeneralMasterDTO> gmData = commonService.getGeneralMastersWithTypeName(gmTypeId);
        System.out.println("Retrieved entries: " + gmData); // Debug log
        model.addAttribute("generalMasters", gmData);
        model.addAttribute("gmTypeId", gmTypeId);
        List<CMSGMType> gmTypes = commonService.getAllGMTypes();  // Fetch GM Types for dropdown
         model.addAttribute("gmTypes", gmTypes); 
        return "generalMaster/generalMaster";
    }
    
    @PostMapping("/saveGeneralMaster")
    public String saveGeneralMaster(@RequestParam Long gmTypeId, 
                                    @RequestParam String gmName, 
                                    @RequestParam String gmDescription, 
                                    Model model) {
        System.out.println("🚀 saveGeneralMaster() called!"); // Log function call
        System.out.println("📩 Received gmTypeId: " + gmTypeId);
        System.out.println("📩 Received gmName: " + gmName);
        System.out.println("📩 Received gmDescription: " + gmDescription);

        try {
            if (gmName.isEmpty() || gmDescription.isEmpty()) {
                model.addAttribute("errorMessage", "Error: Name and Value are required!");
                return "generalMaster/generalMaster";
            }

            boolean isDuplicate = commonService.isDuplicateGMName(gmTypeId, gmName);
            if (isDuplicate) {
                model.addAttribute("errorMessage", "Error: Master Name already exists!");
            } else {
                GeneralMasterDTO generalMasterDTO = new GeneralMasterDTO();
                generalMasterDTO.setGmTypeId(gmTypeId);
                generalMasterDTO.setGmName(gmName);
                generalMasterDTO.setGmDescription(gmDescription);

                commonService.saveGeneralMaster(generalMasterDTO);
                System.out.println("✅ General Master saved successfully!");
                model.addAttribute("successMessage", "Master saved successfully!");
            }

            List<CMSGMType> gmTypes = commonService.getAllGMTypes();
            List<GeneralMasterDTO> updatedMasters = commonService.getGeneralMastersWithTypeName(gmTypeId);

            model.addAttribute("gmTypes", gmTypes);
            model.addAttribute("generalMasters", updatedMasters);
            model.addAttribute("gmTypeId", gmTypeId);

            return "generalMaster/generalMaster"; // Reload updated list
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to save General Master.");
            return "generalMaster/generalMaster";
        }
    }

    @GetMapping("/getGeneralMasters")
    public String getGeneralMasters(@RequestParam Long gmTypeId, Model model) {
        List<GeneralMasterDTO> updatedMasters = commonService.getGeneralMastersWithTypeName(gmTypeId); 
        model.addAttribute("generalMasters", updatedMasters);
        return "generalMaster/gmMasterTableFragment"; // Return only table rows
    }

    private String loadGeneralMasterPage(Model model, Long gmTypeId) {
        List<CMSGMType> gmTypes = commonService.getAllGMTypes(); // Ensure dropdown is always loaded
        List<GeneralMasterDTO> generalMasters = (gmTypeId != null) 
            ? commonService.getGeneralMastersWithTypeName(gmTypeId) 
            : Collections.emptyList();

        model.addAttribute("gmTypes", gmTypes);
        model.addAttribute("generalMasters", generalMasters);
        model.addAttribute("gmTypeId", gmTypeId); // Maintain the selected GM Type

        return "generalMaster/generalMaster";
    }



    @PostMapping("/deleteGmData")
    public String deleteOrgLevelEntry(@RequestParam Long gmId, @RequestParam Long gmTypeId, Model model) {
        try {
            // Perform the deletion
            commonService.deleteGmDataById(gmId);

            List<CMSGMType> gmTypes = commonService.getAllGMTypes(); // Fetch GM Types for the dropdown
            List<GeneralMasterDTO> updatedMasters =commonService.getGeneralMastersWithTypeName(gmTypeId);
            model.addAttribute("gmTypes", gmTypes);
            model.addAttribute("generalMasters", updatedMasters);
            model.addAttribute("gmTypeId", gmTypeId);

            return "generalMaster/generalMaster"; // Updated list view
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to delete entry.");
            return "generalMaster/generalMaster";
        }
    }


    // Delete GeneralMaster
    @PostMapping("/deleteGeneralMaster")
    public String deleteGeneralMaster(@RequestParam("gmId") Long gmId) {
        commonService.deleteCmsGeneralMaster(gmId);
        return "generalMaster/generalMaster";
    }
    
    @GetMapping("/addSection")
    public String showAddRoleRightForm(Model model) {
    	List<CmsGeneralMaster> sections = commonService.getAllSections();
        model.addAttribute("sections", sections);
        return "generalMaster/pageSection"; 
    }
    @RequestMapping(path = "/getSectionPages", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getSectionPages(@RequestParam("sectionId") Long sectionId) {
        try {
            List<CmsGeneralMaster> availablePages = commonService.getAvailablePagesForSection(sectionId);

            List<CmsGeneralMaster> selectedPages = commonService.getSelectedPagesForSection(sectionId);

            Map<String, Object> response = new HashMap<>();
            response.put("availablePages", availablePages);
            response.put("selectedPages", selectedPages);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to retrieve pages for the section.");
        }
    }
    @RequestMapping(value = "/saveMapping", method = RequestMethod.POST)
    public ResponseEntity<String> saveMapping(@RequestBody SaveMappingRequest request) {
        try {

            if (request.getPageIds() == null || request.getPageIds().isEmpty()) {
                return ResponseEntity.badRequest().body("Page IDs cannot be null or empty.");
            }
            commonService.saveSectionPage(request.getSectionId(), request.getPageIds(), "System Admin");
//            for (Long pageId : request.getPageIds()) {
//                commonService.saveSectionPage(request.getSectionId(), pageId, "Admin");
//            }
            return ResponseEntity.ok("Mapping saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving mapping");
        }
    }
    
   

}
