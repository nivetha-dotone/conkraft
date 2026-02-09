package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wfd.dot1.cwfm.dto.EntryPassStatusDto;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.EntrypassStatusService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/entryPassStatus")
public class EntryPassStatus {

	@Autowired
	EntrypassStatusService epService;
	@Autowired
	CommonService commonService;
	@Autowired
	PrincipalEmployerService peService;
	
	 @GetMapping("/list")
		public String getAllPrincipalEmployer(HttpServletRequest request, HttpServletResponse response) {
			HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
			MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

			
			List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
	    	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
	    			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
	    	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
	    	List<PersonOrgLevel> businessType = groupedByLevelDef.getOrDefault("Business Type", new ArrayList<>());
	    	
	        CMSRoleRights rr =new CMSRoleRights();
	        rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/entryPassStatus/list");
	   	    request.setAttribute("UserPermission", rr);
	    	request.setAttribute("principalEmployers", peList);
	    	request.setAttribute("BusinessType", businessType) ;
	    	  
			return "entrypassstatus/list";
		}
	 
	 @PostMapping("/report")
	    public ResponseEntity<List<EntryPassStatusDto>> gatePassListingDetails(
	    		@RequestParam(value = "principalEmployerId", required = false) String principalEmployerId,
	    				HttpServletRequest request,HttpServletResponse response) {
	    	
	    	try {
				HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
				MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
				List<EntryPassStatusDto> listDto = new ArrayList<EntryPassStatusDto>();
				try {
					listDto = epService.getEntryPassStatusReport(principalEmployerId);
				}catch(Exception e) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
					if (listDto.isEmpty()) {
						return new ResponseEntity<>(HttpStatus.NO_CONTENT);
					}
				
				return new ResponseEntity<>(listDto, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
	    }
}
