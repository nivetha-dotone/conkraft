package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.dto.GatePassListingDto;
import com.wfd.dot1.cwfm.dto.PageDto;
import com.wfd.dot1.cwfm.dto.SectionDto;
import com.wfd.dot1.cwfm.enums.GatePassType;
import com.wfd.dot1.cwfm.enums.UserRole;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.MasterUserService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;
import com.wfd.dot1.cwfm.service.WorkmenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	MasterUserService masterUserService;
	 @Autowired
	 private CommonService commonService;
	 @Autowired
	    private BCryptPasswordEncoder passwordEncoder;
	 
	 @Autowired
		WorkmenService workmenService;

		
		@Autowired
		PrincipalEmployerService peService;
	@SuppressWarnings("null")
	@RequestMapping(path = "/userlogin", method = RequestMethod.POST)
	public String login(@RequestParam("email") String em, @RequestParam("password") String pwd, HttpSession session) {
		String encoded = passwordEncoder.encode("nivetha");
		 MasterUser user = masterUserService.findMasterUserDetailsByUserName(em);
			
		   if (user != null) {
		        String storedPassword = user.getPassword();

		        // Validate the stored password before checking
		        if (storedPassword != null && !storedPassword.isEmpty() && BCrypt.checkpw(pwd, storedPassword)) {
		            // Generate initials
		            String initials = Stream.of(user.getFirstName(), user.getLastName())
		                                    .map(name -> String.valueOf(name.charAt(0)))  // Get the first character
		                                    .reduce("", (a, b) -> a + b);

		            // Set session attributes
		            session.setAttribute("userInitials", initials);
		            session.setAttribute("loginuser", user);
		            System.out.println("user.getUserId() -- "+user.getUserId());
		            List<CmsGeneralMaster> roles = commonService.getRolesByUserId(user.getUserId());
		            session.setAttribute("roles", roles);
		            session.setAttribute("selectedRole", "");
		            if (roles==null) {
		                session.setAttribute("msg", "No roles assigned to the user.");
		                return "redirect:/UserLogin.jsp"; // Redirect to error page
		            } else if (roles.size() == 1) {
		                // Single role: Fetch pages and redirect to welcome page
		            	CmsGeneralMaster role = roles.get(0); // Get the role
		                List<CmsGeneralMaster> pages;
		                
		                // If the role is Admin, fetch all pages
		                if ("System Admin".equals(role.getGmName())) {  // Use the role name
		                    pages = commonService.getAllPages();  // Fetch all pages for Admin
		                    
		                } else {
		                    // Fetch pages specific to the roleId
		                    pages = commonService.getPagesByRoleId(role.getGmId());
		                }
		              //  List<CmsGeneralMaster> pages = commonService.getPagesByRoleId(roles.get(0).getGmId());
		                session.setAttribute("pages", pages);
		                session.setAttribute("selectedRole", role.getGmName());
		                System.out.println("Selected role in session: " + session.getAttribute("selectedRole"));
		                System.out.println("Role name: " + role.getGmName());
		                session.setAttribute("roles", roles);
		                return "WelcomePage"; // Redirect to welcome page
		            } else {
		                // Multiple roles: Redirect to role selection page
		            	//CmsGeneralMaster defaultRole = roles.get(0);
		                session.setAttribute("roles", roles);
		              //  session.setAttribute("selectedRole", defaultRole.getGmName());
		                System.out.println("Default selected role in session: " + session.getAttribute("selectedRole"));

		                return "WelcomePage"; // Redirect to role selection JSP
		            }
		            //return "WelcomePage";
		        }
		    } 
		   session.setAttribute("msg", "invalid email and password");
		    return "redirect:/UserLogin.jsp";
//		   else {
//		        // Set error message
//		        session.setAttribute("msg", "invalid email and password");
//		        return "redirect:/UserLogin.jsp";
//		    }

	}
	@RequestMapping(path = "/updateRole", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateRole(@RequestBody Map<String, String> payload, HttpSession session) {
	    try {
	        String roleId = payload.get("roleId");
	        String roleName = payload.get("roleName");

	        System.out.println("Fetched roleId: " + roleId);
	        System.out.println("Fetched roleName: " + roleName);

	        List<SectionDto> sections;

	        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
	        user.setRoleId(roleId);
	        user.setRoleName(roleName);
	        session.setAttribute("loginuser", user);

	        if ("System Admin".equals(roleName)) {  

	            System.out.println("Admin role detected. Fetching all sections and pages.");
	            sections = commonService.getAllSectionsWithPages(); // Fetch all sections and pages for Admin
	            session.setAttribute("sections", sections);

	            List<PageDto> allPages = new ArrayList<>();
	            for (SectionDto section : sections) {
	                for (PageDto page : section.getPages()) {
	                    page.setAccessibleForRole(true); // Set accessibleForRole to true for Admin
	                }
	                allPages.addAll(section.getPages());
	            }
	            session.setAttribute("pages", allPages); // Store all pages for Admin
	            
	            // Set isAdmin to true for Admin role
	            session.setAttribute("isAdmin", true);
	        } else {
	            System.out.println("Fetching sections and pages based on role: " + roleName);
	            sections = commonService.getSectionsByRoleId(roleId);
	            for (SectionDto section : sections) {
	                for (PageDto page : section.getPages()) {
	                    // Check if the page is accessible for the current role based on role rights
	                    boolean hasAccess = commonService.hasPageAccessForRole(roleId, page.getPageId());
	                    page.setAccessibleForRole(hasAccess); // Set the flag to indicate role-based access
	                }
	            }
	            // Set isAdmin to false for non-Admin roles
	            session.setAttribute("isAdmin", false);
	        }

	        // Print the fetched sections and pages for debugging
	        System.out.println("Fetched Sections:");
	        sections.forEach(section -> {
	            System.out.println("Section ID: " + section.getSectionId() + ", Name: " + section.getSectionName());
	            section.getPages().forEach(page -> {
	                System.out.println("   Page ID: " + page.getPageId() + ", Name: " + page.getPageName() + ", URL: " + page.getPageUrl());
	            });
	        });

	        // Save additional session attributes
	        session.setAttribute("selectedRole", roleName);
	        session.setAttribute("roleName", roleName);

	        // Return the sections to the client
	        return ResponseEntity.ok(sections);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Failed to load sections and pages for the selected role.");
	    }
	}


//	@RequestMapping(path = "/updateRole", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseEntity<?> updateRole(@RequestBody Map<String, String> payload, HttpSession session) {
//	    try {
//	        String roleId = payload.get("roleId");
//	        String roleName = payload.get("roleName");
//
//	        System.out.println("Fetched roleId: " + roleId);
//	        System.out.println("Fetched roleName: " + roleName);
//
//	        // Fetch all sections and their associated pages
//	        List<SectionDto> allSections = commonService.getAllSectionsWithPages();
//
//	        // Filter pages based on role rights (if the role is not Admin)
//	        List<SectionDto> filteredSections;
//	        if ("SystemAdmin".equalsIgnoreCase(roleName)) {
//	            // Admin sees all sections and pages
//	            filteredSections = allSections;
//	        } else {
//	            // Filter pages based on role rights
//	            List<Long> allowedPageIds = commonService.getPageIdsByRoleId(roleId); // Fetch page IDs allowed for the role
//	            System.out.println("Page IDs for role: " + roleId + " are " + allowedPageIds);
//	            filteredSections = allSections.stream()
//	                .map(section -> {
//	                    // Filter pages within the section
//	                    List<PageDto> filteredPages = section.getPages().stream()
//	                        .filter(page -> allowedPageIds.contains(page.getPageId()))
//	                        .collect(Collectors.toList());
//
//	                    // Create a new SectionDto with filtered pages
//	                    SectionDto filteredSection = new SectionDto();
//	                    filteredSection.setSectionId(section.getSectionId());
//	                    filteredSection.setSectionName(section.getSectionName());
//	                    filteredSection.setPages(filteredPages);
//	                    return filteredSection;
//	                })
//	                .collect(Collectors.toList());
//	        }
//
//	        // Save sections and role in the session
//	        session.setAttribute("sections", filteredSections);
//	        session.setAttribute("selectedRole", roleName);
//
//	        // Prepare response for the frontend
//	        List<Map<String, Object>> responseSections = filteredSections.stream()
//	            .map(section -> {
//	                Map<String, Object> sectionData = new HashMap<>();
//	                sectionData.put("sectionId", section.getSectionId());
//	                sectionData.put("sectionName", section.getSectionName());
//	                sectionData.put("pages", section.getPages().stream().map(page -> {
//	                    Map<String, String> pageData = new HashMap<>();
//	                    pageData.put("gmId", page.getPageId().toString());
//	                    pageData.put("gmName", page.getPageName());
//	                    pageData.put("gmdescription", page.getPageUrl() != null ? page.getPageUrl() : "default-url");
//	                    return pageData;
//	                }).collect(Collectors.toList()));
//	                return sectionData;
//	            })
//	            .collect(Collectors.toList());
//
//	        return ResponseEntity.ok(responseSections);
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                .body("Failed to retrieve sections and pages for the selected role.");
//	    }
//	}

	
//	@RequestMapping(path = "/updateRole", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseEntity<?> updateRole(@RequestBody Map<String, String> payload, HttpSession session) {
//	    try {
//	        String roleId = payload.get("roleId");
//	        String roleName = payload.get("roleName");
//
//	        System.out.println("Fetched roleId: " + roleId);
//	        System.out.println("Fetched roleName: " + roleName);
//
//	        // Fetch all sections and their associated pages based on role
//	        List<SectionDto> sections = commonService.getSectionsByRoleId(roleId);
//	        session.setAttribute("sections", sections); // Save sections in session
//	        session.setAttribute("selectedRole", roleName); // Save selected role in session
//	        session.setAttribute("roleName", roleName);
//
//	        // Save pages in session for side menu rendering
//			/*
//			 * List<Map<String, String>> pages = sections.stream() .flatMap(section ->
//			 * section.getPages().stream()) // Flatten pages from all sections .map(page ->
//			 * { Map<String, String> pageData = new HashMap<>(); pageData.put("gmId",
//			 * page.getPageId().toString()); pageData.put("gmName", page.getPageName());
//			 * pageData.put("gmdescription", page.getPageUrl() != null ? page.getPageUrl() :
//			 * "default-url"); System.out.println("Page added: " + page.getPageName() + " ("
//			 * + page.getPageUrl() + ")"); return pageData; })
//			 * .collect(Collectors.toList()); session.setAttribute("pages", pages);
//			 */
//	        List<PageDto> pages = sections.stream()
//	        	    .flatMap(section -> section.getPages().stream())
//	        	    .collect(Collectors.toList());
//
//	        	System.out.println("Pages data:");
//	        	pages.forEach(page -> {
//	        	    System.out.println("Page ID: " + page.getPageId());
//	        	    System.out.println("Page Name: " + page.getPageName());
//	        	    System.out.println("Page URL: " + page.getPageUrl());
//	        	});
//
//	        	session.setAttribute("pages", pages);
//
//	        // Prepare response for client
//	        List<Map<String, Object>> responseSections = sections.stream()
//	            .map(section -> {
//	                Map<String, Object> sectionData = new HashMap<>();
//	                sectionData.put("sectionId", section.getSectionId());
//	                sectionData.put("sectionName", section.getSectionName());
//	                sectionData.put("pages", section.getPages().stream().map(page -> {
//	                    Map<String, String> pageData = new HashMap<>();
//	                    pageData.put("gmId", page.getPageId().toString());
//	                    pageData.put("gmName", page.getPageName());
//	                    pageData.put("gmdescription", page.getPageUrl());
//	                    return pageData;
//	                }).collect(Collectors.toList()));
//	                return sectionData;
//	            })
//	            .collect(Collectors.toList());
//
//	        return ResponseEntity.ok(responseSections);
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                             .body("Failed to retrieve sections and pages for the selected role.");
//	    }
//	}

	
//	@RequestMapping(path = "/updateRole", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseEntity<?> updateRole(@RequestBody Map<String, String> payload, HttpSession session) {
//	    try {
//	        String roleId = payload.get("roleId");
//	        String roleName = payload.get("roleName");
//
//	        System.out.println("Fetched roleId: " + roleId);
//	        System.out.println("Fetched roleName: " + roleName);
//
//	        List<CmsGeneralMaster> pages;
//	        if ("SystemAdmin".equals(roleName)) {
//	            pages = commonService.getAllPages();
//	        } else {
//	            try {
//	                //Long parsedRoleId = Long.parseLong(roleId); // Convert String to Long
//	                pages = commonService.getPagesByRoleId(roleId);
//	            } catch (NumberFormatException e) {
//	                return ResponseEntity.badRequest().body("Invalid roleId format.");
//	            }
//	        }
//
//	        if (pages == null || pages.isEmpty()) {
//	            return ResponseEntity.ok(Collections.emptyList()); // Return an empty list
//	        }
//
//	        session.setAttribute("pages", pages);
//	        session.setAttribute("selectedRole", roleName);
//	        session.setAttribute("roleName", roleName);
//	        List<Map<String, String>> responsePages = pages.stream().map(page -> {
//	            Map<String, String> pageData = new HashMap<>();
//	            pageData.put("gmId", page.getGmId().toString());
//	            pageData.put("gmName", page.getGmName());
//	            pageData.put("gmdescription", page.getGmdescription());
//	            return pageData;
//	        }).collect(Collectors.toList());
//
//	        return ResponseEntity.ok(responsePages);
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                             .body("Failed to retrieve pages for the selected role.");
//	    }
//	}
//	@RequestMapping(path = "/updateRole", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseEntity<?> updateRole(@RequestBody Map<String, String> payload, HttpSession session) {
//	    try {
//	        String roleId = payload.get("roleId");
//	        String roleName = payload.get("roleName");
//
//	        System.out.println("Fetched roleId: " + roleId);
//	        System.out.println("Fetched roleName: " + roleName);
//
//	        // Fetch sections based on role
////	        List<CmsGeneralMaster> pages;
////	        if ("SystemAdmin".equals(roleName)) {
////	            pages = commonService.getAllPages();
////	        } else {
////	            try {
////	                //Long parsedRoleId = Long.parseLong(roleId); // Convert String to Long
////	                pages = commonService.getPagesByRoleId(roleId);
////	            } catch (NumberFormatException e) {
////	                return ResponseEntity.badRequest().body("Invalid roleId format.");
////	            }
////	        }
////
////	        if (pages == null || pages.isEmpty()) {
////	            return ResponseEntity.ok(Collections.emptyList()); // Return an empty list
////	        }
//
//	        // Save sections in session for rendering the side menu
//	        List<SectionDto> sections = commonService.getAllSectionsWithPages();
//	        session.setAttribute("sections", sections);
//	        session.setAttribute("selectedRole", roleName);
//	        session.setAttribute("roleName", roleName);
//
//	        // Prepare response for client-side
//	        List<Map<String, Object>> responseSections = sections.stream().map(section -> {
//	            Map<String, Object> sectionData = new HashMap<>();
//	            sectionData.put("sectionId", section.getSectionId());
//	            sectionData.put("sectionName", section.getSectionName());
//	            System.out.println("section.getSectionName(): " + section.getSectionName());
//                System.out.println("section.getSectionId(): " + section.getSectionId());
//	            sectionData.put("pages", section.getPages().stream().map(page -> {
//	                Map<String, String> pageData = new HashMap<>();
//	                pageData.put("gmId", page.getPageId().toString());
//	                pageData.put("gmName", page.getPageName());
//	                pageData.put("gmdescription", page.getPageUrl());
//	                System.out.println("page.getPageUrl(): " + page.getPageUrl());
//	                System.out.println("page.getPageName(): " + page.getPageName());
//	                return pageData;
//	            }).collect(Collectors.toList()));
//	            return sectionData;
//	        }).collect(Collectors.toList());
//
//	        return ResponseEntity.ok(responseSections);
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                             .body("Failed to retrieve sections and pages for the selected role.");
//	    }
//	}

	@GetMapping("/loadSections")
	public String loadSections(Model model) {
	    List<SectionDto> sections = commonService.getAllSectionsWithPages();
	    model.addAttribute("sections", sections);
	    return "WelcomePage";
	}

	@RequestMapping(path = "/userlogin1", method = RequestMethod.POST)
	public String login1(@RequestParam("email") String em, @RequestParam("password") String pwd,
			HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		String encoded = passwordEncoder.encode("nivetha");
		 MasterUser user = masterUserService.findMasterUserDetailsByUserName(em);
			
		   if (user != null) {
		        String storedPassword = user.getPassword();

		        if (storedPassword != null && !storedPassword.isEmpty() && BCrypt.checkpw(pwd, storedPassword)) {
		            String initials = Stream.of(user.getFirstName(), user.getLastName())
		                                    .map(name -> String.valueOf(name.charAt(0)))  
		                                    .reduce("", (a, b) -> a + b);

		            session.setAttribute("userInitials", initials);
		            session.setAttribute("loginuser", user);
		            List<CmsGeneralMaster> roles = commonService.getRolesByUserId(user.getUserId());
		            session.setAttribute("roles", roles);
		            session.setAttribute("selectedRole", "");
		            
		           
			        
		            if (roles==null) {
		                session.setAttribute("msg", "No roles assigned to the user.");
		                return "redirect:/UserLogin1.jsp"; 
		            } else if (roles.size() == 1) {
		            	CmsGeneralMaster role = roles.get(0); 
		                List<CmsGeneralMaster> pages;
		                MasterUser user1 = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
		                
		    	        user1.setRoleId(role.getGmId());
		    	        user1.setRoleName(role.getGmName());
		    	        session.setAttribute("loginuser", user1);
		    	        
//		                if ("System Admin".equals(role.getGmName())) {  
//		                    pages = commonService.getAllPages();  
//		                } else {
//		                    pages = commonService.getPagesByRoleId(role.getGmId());
//		                }
//		                session.setAttribute("pages", pages);
		                session.setAttribute("selectedRole", role.getGmName());
		                session.setAttribute("roles", roles);

		        		String type="regular";
		        		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user1.getUserAccount());
		            	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
		            			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
		            	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
		            	List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
		            	String principalEmployerId = peList.get(0).getId();
		            	String deptId  = departments.get(0).getId();
		            	List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
		                CMSRoleRights rr =new CMSRoleRights();
		                rr = commonService.hasPageActionPermissionForRole(user1.getRoleId(), "/contractworkmen/list");
		           	    listDto = peService.getAllPrincipalEmployer(user1.getUserAccount());
		           	    request.setAttribute("UserPermission", rr);
		            	request.setAttribute("principalEmployers", peList);
		            	request.setAttribute("Dept", departments);
		            	request.setAttribute("selectedPE", principalEmployerId);
		            	request.setAttribute("selectedDept", deptId);
		            	  
		            	List<GatePassListingDto> gplistDto = new ArrayList<GatePassListingDto>();
		        		if(user1.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
		        			gplistDto= workmenService.getGatePassListingDetails(principalEmployerId,deptId,String.valueOf(user1.getUserId()),GatePassType.CREATE.getStatus(),type);
		        		}else {	
		        			gplistDto = workmenService.getGatePassListingForApprovers(principalEmployerId,deptId,user1,GatePassType.CREATE.getStatus(),type);
		        		}
		        		request.setAttribute("GatePassListingDto", gplistDto);
		        		return  "WelcomePageNew";
		            } else {
		                session.setAttribute("roles", roles);
		                return "WelcomePageNew"; 
		            }
		        }
		    } 
		   session.setAttribute("msg", "invalid email and password");
		    return "redirect:/UserLogin1.jsp";


	}
	@RequestMapping(path = "/home", method = RequestMethod.GET)
	public String home(HttpSession session) {

	    MasterUser user = (MasterUser) session.getAttribute("loginuser");

	    if (user == null) {
	        return "redirect:/UserLogin.jsp";   // Not logged in — go to login
	    }

	    return "WelcomePage";   // Main page of application
	}

}
