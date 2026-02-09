package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.wfd.dot1.cwfm.dto.ChangePasswordDTO;
import com.wfd.dot1.cwfm.dto.GatePassListingDto;
import com.wfd.dot1.cwfm.dto.ResetPasswordDTO;
import com.wfd.dot1.cwfm.dto.UserDTO;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;
import com.wfd.dot1.cwfm.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usersController")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class.getName());
    @Autowired
    private UserService userService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
	PrincipalEmployerService peService;
    @GetMapping("/new")
    public String createUserForm(Model model) {
    	 List<CmsGeneralMaster> roles = commonService.getAllRoles();
    	 model.addAttribute("roles", roles);
        model.addAttribute("userDTO", new UserDTO());
        return "users/add";
    }

   

    @GetMapping("/userList")
    public String listUsers(Model model, HttpServletRequest request,HttpServletResponse response) {
    	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
        List<PrincipalEmployer> peList =new ArrayList<PrincipalEmployer>();
		 CMSRoleRights rr =new CMSRoleRights();
	       if(user!=null) {
	       if(user.getRoleName().equals("System Admin")) {
	       	 rr.setAddRights(1);  // Changed getInt() to getBoolean()
			        rr.setEditRights(1);
			        rr.setDeleteRights(1);
			        rr.setImportRights(1);
			        rr.setExportRights(1);
			        rr.setViewRights(1);
	       	peList = peService.getAllPrincipalEmployerForAdmin();
	       }else {
	       	rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/usersController/userList");
	       	peList = peService.getAllPrincipalEmployer(user.getUserAccount());
	       }
	       }
	       request.setAttribute("UserPermission", rr);
        model.addAttribute("users", userService.getAllUsers());
        List<CmsGeneralMaster> roles = commonService.getAllRoles();
   	 model.addAttribute("roles", roles);
        return "users/list";
    }
    @PostMapping("/saveUsers")
    public ResponseEntity<Map<String, String>> saveUsers(@RequestBody UserDTO userDTO) {
        try {
        	 userService.saveUser(userDTO.getUser(), userDTO.getRoleIds());
            Map<String, String> response = new HashMap<>();
            response.put("message", "User saved successfully!");
            return ResponseEntity.ok(response);  // Return JSON response
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to save user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

//    @PostMapping("/saveUsers")
//    public ResponseEntity<?> saveUsers(@RequestBody UserDTO userDTO) {
//        try {
//
//            // Validate input
//            if (userDTO.getUser() == null) {
//                throw new IllegalArgumentException("User data is required");
//            }
//            if (userDTO.getRoleIds() == null || userDTO.getRoleIds().isEmpty()) {
//                throw new IllegalArgumentException("At least one role ID is required");
//            }
//
//            // Save user
//            userService.saveUser(userDTO.getUser(), userDTO.getRoleIds());
//
//            return ResponseEntity.ok("User saved successfully!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                                 .body("Error saving user: " + e.getMessage());
//        }
//    }
   
    @GetMapping("/loadChangePwdPage")
    public String loadChangePwdPage(HttpSession session, Model model) {
    	// String loggedInUser = (String) session.getAttribute("loginuser"); // Assuming session stores the user account
    	 MasterUser user = (MasterUser) session.getAttribute("loginuser");
    	 String userAccount = user.getUserAccount();  
    	 model.addAttribute("userAccount", userAccount);
    	    System.out.println("loggedInUser: " + userAccount);
        return "users/changePassword";
    }
    @GetMapping("/loadResetPwdPage")
    public String loadResetPwdPage(HttpSession session, Model model) {
    	// String loggedInUser = (String) session.getAttribute("loginuser"); // Assuming session stores the user account
    	 MasterUser user = (MasterUser) session.getAttribute("loginuser");
    	 String userAccount = user.getUserAccount();  
    	 model.addAttribute("userAccount", userAccount);
    	    System.out.println("loggedInUser: " + userAccount);
        return "users/resetPassword";
    }
    
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, HttpSession session) {
    	// String sessionUser = (String) session.getAttribute("loginuser");
    	 MasterUser user = (MasterUser) session.getAttribute("loginuser");
    	 String sessionUser = user.getUserAccount(); 
    	    if (sessionUser == null || !sessionUser.equals(changePasswordDTO.getUserAccount())) {
    	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action detected!");
    	    }
        boolean isChanged = userService.changeUserPassword(changePasswordDTO);
        System.out.println("isChanged: " + isChanged);
        System.out.println("password : " + changePasswordDTO.getUserAccount()+" - "+changePasswordDTO.getOldPassword()+" - "+changePasswordDTO.getNewPassword());
        if (isChanged) {
            return ResponseEntity.ok("Password changed successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID or old password!");
        }
    }
    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO, HttpSession session) {
        // 🔒 Check if the logged-in user is an Admin
    	MasterUser user = (MasterUser) session.getAttribute("loginuser");
   	 String loggedInRole = user.getRoleName(); 
       // String loggedInRole = (String) session.getAttribute("loggedInRole");
        if (loggedInRole == null || !loggedInRole.equals("System Admin")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied! Only Admins can reset passwords.");
        }

        boolean success = userService.resetUserPassword(resetPasswordDTO);
        if (success) {
            return ResponseEntity.ok("Password reset successfully for user: " + resetPasswordDTO.getUserAccount());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found or update failed.");
        }
    }
    @GetMapping("/userview/{userId}")
	public String viewUserDetails(@PathVariable String userId, HttpServletRequest request,
			HttpServletResponse response) {
		MasterUser usersview = userService.viewUserDetails(userId);
		request.setAttribute("users", usersview);
		List<Long> assignedRoleIds = userService.getUserRoleIds(userId);
//		String assignedRoleIdsStr = assignedRoleIds.stream()
//		                                           .map(String::valueOf)
//		                                           .collect(Collectors.joining(","));

		request.setAttribute("assignedRoles", assignedRoleIds);
		 List<CmsGeneralMaster> roles = commonService.getAllRoles();
		request.setAttribute("roles",roles);
		return "users/view"; // Return the JSP file name
	}
    
    @GetMapping("/edit/{userId}")
    public String editUser(@PathVariable("userId") String userId, HttpServletRequest request) {
        // Fetch user details
        MasterUser user = userService.viewUserDetails(userId);
        request.setAttribute("user", user);

        // Fetch assigned roles for this user
        List<Long> assignedRoleIds = userService.getUserRoleIds(userId);
        request.setAttribute("assignedRoles", assignedRoleIds);

        // Fetch all roles for selection
        List<CmsGeneralMaster> roles = commonService.getAllRoles();
        request.setAttribute("roles", roles);

        return "users/edit"; // Navigate to edit.jsp
    }
   
    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        try {
            // Validate input
        	System.out.println("Received userDTO: " + userDTO);
            if (userDTO.getUser() == null || userDTO.getUser().getUserId() == null) {
                throw new IllegalArgumentException("User ID and data are required for update");
            }
            if (userDTO.getRoleIds() == null || userDTO.getRoleIds().isEmpty()) {
                throw new IllegalArgumentException("At least one role ID is required");
            }

            // Update user
            userService.updateUser(userDTO.getUser(), userDTO.getRoleIds());

            return ResponseEntity.ok("User updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error updating user: " + e.getMessage());
        }
    }
    @PutMapping("/deleteUsers")
    public ResponseEntity<String> deleteUsers(@RequestBody Map<String, List<Integer>> request) {
        List<Integer> userIds = request.get("userIds");

        if (userIds == null || userIds.isEmpty()) {
            return ResponseEntity.badRequest().body("No users selected for deletion.");
        }

        userService.deleteUsers(userIds);
        return ResponseEntity.ok("Users deleted successfully!");
    }
    @PostMapping("/getUserRoles")
    public ResponseEntity<Map<Long, List<String>>> getUserRoles(@RequestBody Map<String, List<Long>> request) {
        List<Long> userIds = request.get("userIds");

        if (userIds == null || userIds.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyMap());
        }

        // Fetch roles for each user
        Map<Long, List<String>> rolesMap = new HashMap<>();
        for (Long userId : userIds) {
            List<String> roles = userService.getRolesByUserId(userId);
            rolesMap.put(userId, roles.isEmpty() ? List.of("N/A") : roles);
        }

        return ResponseEntity.ok(rolesMap);
    }
    @GetMapping("/checkUserExists")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> checkUserExists(@RequestParam String userAccount) {
        boolean exists = userService.doesUserExist(userAccount);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }
	/*
	 * @PostMapping("/getUserRoles") public Map<Long, List<String>>
	 * getUserRoles(@RequestBody List<Long> userIds) { Map<Long, List<String>>
	 * userRolesMap = new HashMap<>(); for (Long userId : userIds) { List<String>
	 * roles = userService.getRolesByUserId(userId); userRolesMap.put(userId,
	 * roles); } return userRolesMap; }
	 */

    @GetMapping("/getUserWithUserAccount")
   	public ResponseEntity<List<MasterUser>> getUserWithUserAccount(
               @RequestParam String userAccount,HttpServletRequest request,HttpServletResponse response) {
          
           	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
               MasterUser loginuser = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
           
           	  // Sample data (replace with your database queries)
   	        List<MasterUser> users = userService.getUserWithUserAccount(userAccount);
   	        
   	     return ResponseEntity.ok(users);

              
    }
}
