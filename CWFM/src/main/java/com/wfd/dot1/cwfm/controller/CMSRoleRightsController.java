package com.wfd.dot1.cwfm.controller;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.dto.RoleRightsForm;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.service.CommonService;

@Controller
@RequestMapping("/roleRights")
public class CMSRoleRightsController {

	
    @Autowired
    private CommonService commonService;

    @GetMapping("/roleRights")
    public ModelAndView showRoleRights() {
        List<CMSRoleRights> roleRightsList = commonService.getAllRoleRights();
        ModelAndView modelAndView = new ModelAndView("roleRightsList");
        modelAndView.addObject("roleRightsList", roleRightsList);
        List<CmsGeneralMaster> pages = commonService.getAllPages();
        List<CmsGeneralMaster> roles = commonService.getAllRoles();
        ((Model) modelAndView).addAttribute("roles", roles);
        ((Model) modelAndView).addAttribute("pages", pages);
        return modelAndView;
    }
    
    @RequestMapping("/roleRightsList")
    public String showRoleRights(Model model) {
        List<CMSRoleRights> roleRightsList = commonService.getAllRoleRights();
        model.addAttribute("roleRightsList", roleRightsList);
        List<CmsGeneralMaster> pages = commonService.getAllPages();
        List<CmsGeneralMaster> roles = commonService.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("pages", pages);
        return "roleRights/list";
    }
    

    @GetMapping("/add")
    public String showAddRoleRightForm(Model model) {
    	List<CmsGeneralMaster> pages = commonService.getAllPages();
        List<CmsGeneralMaster> roles = commonService.getAllRoles();
        RoleRightsForm roleRightsForm = new RoleRightsForm();
        roleRightsForm.setRoleRights(Collections.singletonList(new CMSRoleRights()));

        model.addAttribute("roles", roles);
        model.addAttribute("pages", pages);
        model.addAttribute("roleRightsForm", roleRightsForm);
        return "roleRights/add"; // This should map to addRoleRight.jsp
    }
//    @GetMapping("/getPagesByRole/{roleId}")
//    @ResponseBody
//    public List<CmsGeneralMaster> getPagesByRole(@PathVariable Long roleId) {
//        System.out.println("roleId---" + roleId);
//        List<CmsGeneralMaster> pages = commonService.getPagesByRole(roleId);
//        System.out.println("Pages returned: " + pages); // Check if this prints the correct data
//        return pages;
//    }
    
    @GetMapping("/getPagesByRole")
    @ResponseBody
    public List<CmsGeneralMaster> getPagesByRole(@RequestParam(required = false) Long roleId) {
        System.out.println("roleId---" + roleId);
        return commonService.getPagesByRole(roleId); // Fetch pages based on selected role
    }
    
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            MasterUser user = (MasterUser) authentication.getPrincipal();
            return (long) user.getUserId();// Replace with actual logic to retrieve the user ID
        }
        return 1L; // Default user ID for testing or fallback
    }
   
    

    @PostMapping("/saveRoleRights")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> saveRoleRights(@RequestBody RoleRightsForm roleRights) {
        Map<String, Object> response = new HashMap<>();
        List<String> errorMessages = new ArrayList<>();

        // Validate before saving
        for (CMSRoleRights roleRight : roleRights.getRoleRights()) {
            boolean isDuplicate = commonService.checkDuplicateRoleRight(roleRight.getRoleId(), roleRight.getPageId());

            if (isDuplicate) {
                CmsGeneralMaster roleMaster = commonService.findByGMId(roleRight.getRoleId());
                CmsGeneralMaster pageMaster = commonService.findByGMId(roleRight.getPageId());

                String roleName = (roleMaster != null) ? roleMaster.getGmName() : "Unknown Role";
                String pageName = (pageMaster != null) ? pageMaster.getGmName() : "Unknown Page";

                errorMessages.add("Duplicate Role-Page combination detected for Role: " + roleName + " and Page: " + pageName);
            }
        }

        // Stop saving if any row has an error
        if (!errorMessages.isEmpty()) {
            response.put("status", "error");
            response.put("errors", errorMessages);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // Save all valid rows
        try {
            for (CMSRoleRights roleRight : roleRights.getRoleRights()) {
                processRoleRight(roleRight, getCurrentUserId());
                commonService.saveRoleRights(roleRight);
            }
            response.put("status", "success");
            response.put("message", "Role rights saved successfully!");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to save role rights. Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> saveOrUpdateRoleRights(@RequestBody RoleRightsForm roleRightsForm) {
        try {
            System.out.println("Received Role Rights: " + roleRightsForm); 
            ObjectMapper objectMapper = new ObjectMapper();
            String receivedJson = objectMapper.writeValueAsString(roleRightsForm);
            System.out.println("Raw JSON Received: " + receivedJson);

            if (roleRightsForm == null || roleRightsForm.getRoleRights() == null || roleRightsForm.getRoleRights().isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("message", "No role rights provided."));
            }

            Long currentUserId = getCurrentUserId();
            if (currentUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("message", "User is not authorized."));
            }

            for (CMSRoleRights roleRight : roleRightsForm.getRoleRights()) {
                processRoleRight(roleRight, currentUserId);
                System.out.println("Before saving:");
                System.out.println("RoleRight ID: " + roleRight.getRoleRightId());
                System.out.println("Role ID: " + roleRight.getRoleId());
                System.out.println("Page ID: " + roleRight.getPageId());
                System.out.println("Values: " +
                		roleRight.getAddRights() + ", " +
                		roleRight.getEditRights() + ", " +
                		roleRight.getDeleteRights() + ", " +
	        	        roleRight.getImportRights() + ", " +
	        	        roleRight.getExportRights() + ", " +
	        	        roleRight.getViewRights() + ", " +
	        	        roleRight.getListRights() + ", " +
	        	        roleRight.getEnabledFlag() + ", " +
	        	        roleRight.getDeletedFlag() + ", " +
	        	        roleRight.getCreatedBy() + ", " +
	        	        roleRight.getCreationDate() + ", " +
	        	        roleRight.getLastUpdatedBy() + ", " +
	        	        roleRight.getLastUpdatedDate() + ", " +
	        	        roleRight.getRoleRightId()
	        	);
                System.out.println("Before saving, RoleRight ID: " + roleRight.getRoleRightId());
                commonService.saveRoleRights(roleRight);
            }

            return ResponseEntity.ok(Collections.singletonMap("message", "Role rights saved/updated successfully!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Failed to save/update role rights."));
        }
    }
    @PostMapping("/export")
    public ResponseEntity<?> exportRoleRights(@RequestBody RoleRightsForm request) {
        System.out.println("Received export request: " + request);
        if (request == null || request.getRoleRights().isEmpty()) {
            System.out.println("ERROR: No data received in the backend!");
            return ResponseEntity.badRequest().body("No data received");
        }
        return ResponseEntity.ok(request);
    }
    @PutMapping("/deleteRights")
    public ResponseEntity<String> deleteRights(@RequestBody Map<String, List<Integer>> request) {
        List<Integer> roleIds = request.get("roleIds");

        if (roleIds == null || roleIds.isEmpty()) {
            return ResponseEntity.badRequest().body("No Role Right selected for deletion.");
        }

        commonService.deleteRoleRights(roleIds);
        return ResponseEntity.ok("Role Rights deleted successfully!");
    }




//    @RequestMapping(value = "/saveRoleRights", method = RequestMethod.POST)
//    public String saveRoleRights(RoleRightsForm roleRights, RedirectAttributes redirectAttributes) {
//        try {
//            for (CMSRoleRights roleRight : roleRights.getRoleRights()) {
//                if (!isDuplicateRoleRight(roleRight)) {
//                    processRoleRight(roleRight, getCurrentUserId()); // Additional processing if needed
//                    commonService.saveRoleRights(roleRight);
//                } else {
//                    redirectAttributes.addFlashAttribute("error", "Duplicate Role-Page combination detected for Role: " 
//                                                         + roleRight.getRoleId() + " and Page: " + roleRight.getPageId());
//                    return "redirect:/roleRights/roleRightsList";
//                }
//            }
//            redirectAttributes.addFlashAttribute("success", "Role rights saved successfully!");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Failed to save role rights. Error: " + e.getMessage());
//        }
//
//        // Redirect back to the role rights list
//        return "redirect:/roleRights/roleRightsList";
//    }
//
//    @PostMapping("/saveRoleRights")
//    @ResponseBody
//    public ResponseEntity<Map<String, Object>> saveRoleRights(@RequestBody RoleRightsForm roleRights) {
//        Map<String, Object> response = new HashMap<>();
//        
//        try {
//            for (CMSRoleRights roleRight : roleRights.getRoleRights()) {
//                // Check for duplicate Role-Page combination
//                boolean isDuplicate = commonService.checkDuplicateRoleRight(roleRight.getRoleId(), roleRight.getPageId());
//
//                if (isDuplicate) {
//                    response.put("status", "error");
//                    response.put("message", "Duplicate Role-Page combination detected for Role ID: " 
//                            + roleRight.getRoleId() + " and Page ID: " + roleRight.getPageId());
//                    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
//                }
//
//                // Save if no duplicate
//                commonService.saveRoleRights(roleRight);
//            }
//            response.put("status", "success");
//            response.put("message", "Role rights saved successfully!");
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            response.put("status", "error");
//            response.put("message", "Failed to save role rights. Error: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }


	/*
	 * @PostMapping("/saveRoleRights") public ModelAndView
	 * saveRoleRights(@ModelAttribute RoleRightsForm roleRightsForm) {
	 * roleRightsForm.getRoleRights().forEach(roleRight -> {
	 * processRoleRight(roleRight, getCurrentUserId());
	 * commonService.saveRoleRights(roleRight); }); ModelAndView mav = new
	 * ModelAndView("roleRights/list"); mav.addObject("roleRights",
	 * commonService.getAllRoleRights()); return mav; }
	 */
//    @PostMapping("/saveRoleRights")
//    public ResponseEntity<String> saveRoleRights(@ModelAttribute RoleRightsForm roleRightsForm) {
//        Long currentUserId = getCurrentUserId();
//        roleRightsForm.getRoleRights().forEach(roleRight -> {
//            processRoleRight(roleRight, currentUserId);
//            commonService.saveRoleRights(roleRight);
//        });
//
//        return ResponseEntity.ok("Role rights saved successfully.");
//    }
//    @PostMapping("/saveRoleRights")
//    public ResponseEntity<String> saveRoleRights(@RequestBody RoleRightsForm roleRightsForm) {
////        Long currentUserId = 1L;
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////      if (authentication != null && authentication.isAuthenticated()) {
////          MasterUser user = (MasterUser) authentication.getPrincipal();
////          currentUserId = (long) 1;
////      }
//    	  Long currentUserId = getCurrentUserId();
//        roleRightsForm.getRoleRights().forEach(roleRight -> {
//            processRoleRight(roleRight, currentUserId);
//            commonService.saveRoleRights(roleRight);
//        });
//
//        return ResponseEntity.ok("Role rights saved successfully.");
//    }
    private void processRoleRight(CMSRoleRights roleRight, Long currentUserId) {
        roleRight.setCreatedBy(currentUserId);
        roleRight.setLastUpdatedBy(currentUserId);
        roleRight.setCreationDate(LocalDateTime.now());
        roleRight.setEnabledFlag(1);
        roleRight.setDeletedFlag(0);

        if (roleRight.getAddRights() == null) {
            roleRight.setAddRights(0); // Or keep it unchanged
        }
        if (roleRight.getEditRights() == null) {
            roleRight.setEditRights(0);
        }
        if (roleRight.getDeleteRights() == null) {
            roleRight.setDeleteRights(0);
        }
        if (roleRight.getViewRights() == null) {
            roleRight.setViewRights(0);
        }
        if (roleRight.getImportRights() == null) {
            roleRight.setImportRights(0);
        }
        if (roleRight.getExportRights() == null) {
            roleRight.setExportRights(0);
        }
        if (roleRight.getListRights() == null) {
            roleRight.setListRights(0);
        }


        // Fetch page information
        if (roleRight.getPage() != null && roleRight.getPage().getGmId() != null) {
            CmsGeneralMaster page = commonService.findCmsGeneralMasterById(roleRight.getPage().getGmId());
            roleRight.setPage(page);
        }
    }


//    @PostMapping("/saveRoleRights")
//    public ModelAndView saveRoleRights(@ModelAttribute("roleRightsForm") RoleRightsForm roleRightsForm) {
//        List<CMSRoleRights> roleRightsList = roleRightsForm.getRoleRights();
//        Long currentUserId = 1L; // Replace with actual user ID from authentication context
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            MasterUser user = (MasterUser) authentication.getPrincipal();
//            currentUserId = (long) 1;
//        }
//
//        for (CMSRoleRights roleRight : roleRightsList) {
//            roleRight.setCreatedBy(currentUserId);
//            roleRight.setLastUpdatedBy(currentUserId);
//            roleRight.setCreationDate(LocalDateTime.now());
//            roleRight.setEnabledFlag(1); // Default active
//            roleRight.setDeletedFlag(0); // Not deleted
//         // Initialize rights to 0 if they are not explicitly set
//            roleRight.setAddRights(roleRight.getAddRights() != null ? roleRight.getAddRights() : 0);
//            roleRight.setEditRights(roleRight.getEditRights() != null ? roleRight.getEditRights() : 0);
//            roleRight.setDeleteRights(roleRight.getDeleteRights() != null ? roleRight.getDeleteRights() : 0);
//            roleRight.setViewRights(roleRight.getViewRights() != null ? roleRight.getViewRights() : 0);
//            roleRight.setImportRights(roleRight.getImportRights() != null ? roleRight.getImportRights() : 0);
//            roleRight.setExportRights(roleRight.getExportRights() != null ? roleRight.getExportRights() : 0);
//            roleRight.setListRights(roleRight.getListRights() != null ? roleRight.getListRights() : 0);  
//            System.out.println("roleId---" + roleRight.getAddRights());
//            roleRight.setAddRights(roleRight.getAddRights());
//            if (roleRight.getPage() != null && roleRight.getPage().getGmId() != null) {
//                CmsGeneralMaster page = commonService.findCmsGeneralMasterById(roleRight.getPage().getGmId());
//                roleRight.setPage(page);
//            }
//
//            // Save to the database
//            commonService.saveRoleRights(roleRight);
//            System.out.println("roleId---" + roleRight);
//        }
//
//        return new ModelAndView("roleRights/roleRightsList");
//    }

    
    @GetMapping("/edit/{id}")
    public String showEditRoleRightForm(@PathVariable Long id, Model model) {
    	CMSRoleRights roleRightDto = (CMSRoleRights) commonService.getRoleRightsByRoleId(id);
        model.addAttribute("roleRightDto", roleRightDto);
        model.addAttribute("roleList", commonService.getAllRoleRights());
        model.addAttribute("pages", commonService.getAllPages());
        return "editRoleRight"; // This should map to editRoleRight.jsp
    }

    @PostMapping("/update/{id}")
    public String updateRoleRight(@PathVariable Long id, CMSRoleRights roleRightDto, Model model) {
        try {
        	//commonService.updateRoleRights(id, roleRightDto);
            model.addAttribute("successMessage", "Role rights updated successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating role rights: " + e.getMessage());
        }
        return "redirect:/roleRights/list"; // Redirect to the list page
    }

    @GetMapping("/delete/{id}")
    public String deleteRoleRight(@PathVariable Long id, Model model) {
        try {
        	//commonService.deleteRoleRight(id);
            model.addAttribute("successMessage", "Role rights deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error deleting role rights: " + e.getMessage());
        }
        return "redirect:/roleRights/list"; // Redirect to the list page
    }
}
