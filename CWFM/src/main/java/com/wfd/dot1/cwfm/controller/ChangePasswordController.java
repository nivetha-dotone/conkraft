package com.wfd.dot1.cwfm.controller;

import java.util.List;
import java.util.stream.Stream;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.service.MasterUserService;

@Controller
@RequestMapping("/password")
public class ChangePasswordController{
	
	@Autowired
	MasterUserService masterUserService;
	
	@GetMapping("/change")
	public String changePassword(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

		  return "changePassword";
	}
	@PostMapping("/savePassword")
    public ResponseEntity<String> savePassword(@RequestBody MasterUser masteruser, HttpSession session) {
        String currentUserId = (String) session.getAttribute("userId"); // Fetch the logged-in user's ID from the session

        if (currentUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in.");
        }

        if (masterUserService.validateOldPassword(currentUserId, masteruser.getPassword())) {
        	masterUserService.updatePassword(currentUserId, masteruser.getNewPassword());
            return ResponseEntity.ok("Password changed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect.");
        }
    }
}