package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wfd.dot1.cwfm.dto.CardDto;
import com.wfd.dot1.cwfm.enums.GatePassType;
import com.wfd.dot1.cwfm.enums.UserRole;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.DashboardService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/dashboard")
public class DashboardController {
	@Autowired
	CommonService commonService;
	
	@Autowired
	DashboardService service;
	@GetMapping("/load")
    public String createGatePass(HttpServletRequest request,HttpServletResponse response) {
	
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
        String role = user.getRoleName().toUpperCase();
        String result=null;
        if (role.contains(UserRole.CONTRACTORSUPERVISOR.getName())) {
        	
            result = "dashboard/csDashboard";
        }else  {
        	
        	result = "dashboard/managerDashboard";
        	
        }
        return result;
	}
	
	@Autowired
	PrincipalEmployerService peService;
	
	@GetMapping("/getOrgDetails")
	public String loadOrg(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		 
   		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
   		
   		if(user!=null) {
   			
            if(user.getRoleName().equals("System Admin")) {
            	List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
       	   		List<PrincipalEmployer> departments =new ArrayList<PrincipalEmployer>();
           	 listDto = peService.getAllPrincipalEmployerForAdmin();
           	departments = peService.getAllDepartmentForAdmin();
         	request.setAttribute("principalEmployers", listDto);
         	  request.setAttribute("Dept", departments);
            }else {
   		
   		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
       	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
       			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
       	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
       	List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
            
       	request.setAttribute("principalEmployers", peList);
       	  request.setAttribute("Dept", departments);
            }
   		}
		return "dashboard/primarySelect";
	}
	
	@GetMapping("/view") 
	public String view(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value = "principalEmployerId", required = false) String principalEmployerId,
			@RequestParam(value = "deptId", required = false) String deptId,Model model) { 
		HttpSession session = request.getSession(false);
		if(principalEmployerId!=null && deptId!=null ) {
		//user selected from pop up
			session.setAttribute("principalEmployerId", principalEmployerId);
	        session.setAttribute("deptId", deptId);
		}else {
			String pe = (session != null) ?(String)session.getAttribute("principalEmployerId") : null;
			String de = (session != null) ?(String)session.getAttribute("deptId") : null;
			if(pe == null && de == null) {
				session.setAttribute("principalEmployerId", principalEmployerId);
		        session.setAttribute("deptId", deptId);
			}else {
				principalEmployerId=pe;deptId=de;
			}
		}
		
		
		MasterUser user = (session != null) ? (MasterUser) session.getAttribute("loginuser") : null; 
		String role = user.getRoleName().toUpperCase();
		List<CardDto> cards = new ArrayList<>();
		if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())|| user.getRoleName().toUpperCase().equals(UserRole.SystemAdmin.getName())){
			int quick = service.getGatePassListingDetails(principalEmployerId, deptId, GatePassType.CREATE.getStatus(),"quick");
			String quickUrl = "/CWFM/contractworkmen/quickOnboardingListDashboardNav"
			        + "?principalEmployerId=" + principalEmployerId
			        + "&deptId=" + deptId;
			cards.add(new CardDto("Quick Onboarding ", "Manage new workmen", String.valueOf(quick), "border-onboarding", 
					"icon-onboarding", "resources/img/onboarding.png", quickUrl,"Quick Onboarding")); 
			
			int regular = service.getGatePassListingDetails(principalEmployerId, deptId, GatePassType.CREATE.getStatus(),"regular");
			String regUrl = "/CWFM/contractworkmen/regOnboardingListDashboardNav"
			        + "?principalEmployerId=" + principalEmployerId
			        + "&deptId=" + deptId;
			cards.add(new CardDto("Regular Onboarding", "Manage new workmen", String.valueOf(regular), "border-renewals",
					"icon-renewals", "resources/img/verification.png", regUrl,"Create"));  
			
			int block = service.getGatePassActionListingDetails(principalEmployerId, deptId, GatePassType.BLOCK.getStatus());
			String blockUrl = "/CWFM/contractworkmen/gatepassActionListDashboardNav"
					+ "?principalEmployerId=" + principalEmployerId
			        + "&deptId=" + deptId
			        + "&action=" + GatePassType.BLOCK.getStatus();
			cards.add(new CardDto("Block", "Manage blocked workmen", String.valueOf(block), "border-verifications", 
					"icon-verifications", "resources/img/official-document.png", blockUrl,"Block"));
			
			int unblock = service.getGatePassActionListingDetails(principalEmployerId, deptId, GatePassType.UNBLOCK.getStatus());
			String unblockUrl = "/CWFM/contractworkmen/gatepassActionListDashboardNav"
					+ "?principalEmployerId=" + principalEmployerId
			        + "&deptId=" + deptId
			        + "&action=" + GatePassType.UNBLOCK.getStatus();
			cards.add(new CardDto("Unblock ", "Manage unblocked workmen", String.valueOf(unblock), "border-onboarding", 
					"icon-onboarding", "resources/img/onboarding.png", unblockUrl,"Unblock")); 
			
			int black = service.getGatePassActionListingDetails(principalEmployerId, deptId, GatePassType.BLACKLIST.getStatus());
			String blackurl = "/CWFM/contractworkmen/gatepassActionListDashboardNav"
					+ "?principalEmployerId=" + principalEmployerId
			        + "&deptId=" + deptId
			        + "&action=" + GatePassType.BLACKLIST.getStatus();
			cards.add(new CardDto("Blacklist", "Manage blacklisted workmen",  String.valueOf(black), 
					"border-renewals", "icon-renewals", "resources/img/verification.png", blackurl,"Blacklist")); 
			
			int deblack = service.getGatePassActionListingDetails(principalEmployerId, deptId, GatePassType.DEBLACKLIST.getStatus());
			String deblackurl = "/CWFM/contractworkmen/gatepassActionListDashboardNav"
					+ "?principalEmployerId=" + principalEmployerId
			        + "&deptId=" + deptId
			        + "&action=" + GatePassType.DEBLACKLIST.getStatus();
			cards.add(new CardDto("DeBlacklist", "Manage deblacklisted workmen", String.valueOf(deblack), "border-renewals", 
					"icon-renewals", "resources/img/verification.png", deblackurl,"Deblacklist")); 
			
			int cancel = service.getGatePassActionListingDetails(principalEmployerId, deptId, GatePassType.CANCEL.getStatus());
			String cancelUrl = "/CWFM/contractworkmen/gatepassActionListDashboardNav"
					+ "?principalEmployerId=" + principalEmployerId
			        + "&deptId=" + deptId
			        + "&action=" + GatePassType.CANCEL.getStatus();
			cards.add(new CardDto("Cancel", "Manage cancelled workmen", String.valueOf(cancel), "border-renewals",
					"icon-renewals", "resources/img/verification.png", cancelUrl,"Cancel"));
			
			int renew = service.getGatePassActionListingDetails(principalEmployerId, deptId, GatePassType.RENEW.getStatus());
			String renewUrl = "/CWFM/contractworkmen/gatepassActionListDashboardNav"
					+ "?principalEmployerId=" + principalEmployerId
			        + "&deptId=" + deptId
			        + "&action=" + GatePassType.RENEW.getStatus();
			cards.add(new CardDto("Renew", "Track workmen renewals", String.valueOf(renew), "border-renewals"  ,
					 "icon-renewals", "resources/img/verification.png", renewUrl,"Renew")); 
			
			int lost = service.getGatePassActionListingDetails(principalEmployerId, deptId, GatePassType.LOSTORDAMAGE.getStatus());
			String lostUrl = "/CWFM/contractworkmen/gatepassActionListDashboardNav"
					+ "?principalEmployerId=" + principalEmployerId
			        + "&deptId=" + deptId
			        + "&action=" + GatePassType.LOSTORDAMAGE.getStatus();
			cards.add(new CardDto("Lost or Damage", "Manage lost or damaged gatepass", String.valueOf(lost), "border-renewals", "icon-renewals", 
					"resources/img/verification.png", lostUrl,"Lost or Damage")); 
		}else {//approvers
			
			 CMSRoleRights quickR =new CMSRoleRights();
			 quickR = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/quickOnboardingList");
		       if( quickR.getViewRights() != null && quickR.getViewRights()==1) {
			int quick = service.getGatePassListingForApprovers(principalEmployerId,deptId,user,GatePassType.CREATE.getStatus(),"quick");
			String quickUrl = "/CWFM/contractworkmen/quickOnboardingListDashboardNav"
			        + "?principalEmployerId=" + principalEmployerId
			        + "&deptId=" + deptId;
			cards.add(new CardDto("Quick Onboarding ", "Manage new workmen", String.valueOf(quick), "border-onboarding", 
					"icon-onboarding", "resources/img/onboarding.png", quickUrl,"Quick Onboarding")); 
		       }
		       CMSRoleRights regR =new CMSRoleRights();
		       regR = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/list");
			       if(regR.getViewRights() != null &&  regR.getViewRights()==1) {
			int regular = service.getGatePassListingForApprovers(principalEmployerId,deptId,user,GatePassType.CREATE.getStatus(),"regular");
			String regUrl = "/CWFM/contractworkmen/regOnboardingListDashboardNav"
			        + "?principalEmployerId=" + principalEmployerId
			        + "&deptId=" + deptId;
			cards.add(new CardDto("Regular Onboarding", "Manage new workmen", String.valueOf(regular), "border-renewals",
					"icon-renewals", "resources/img/verification.png", regUrl,"Create"));  
			       }
			       
			       CMSRoleRights blockR =new CMSRoleRights();
			       blockR = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/blockListFilter");
				       if(blockR.getViewRights() != null &&  blockR.getViewRights()==1) {       
			int block = service.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.BLOCK.getStatus());;
			String blockUrl = "/CWFM/contractworkmen/gatepassActionListDashboardNav"
					+ "?principalEmployerId=" + principalEmployerId
			        + "&deptId=" + deptId
			        + "&action=" + GatePassType.BLOCK.getStatus();
			cards.add(new CardDto("Block", "Manage blocked workmen", String.valueOf(block), "border-verifications", 
					"icon-verifications", "resources/img/official-document.png", blockUrl,"Block"));
				       }
				       CMSRoleRights unblockR =new CMSRoleRights();
				       unblockR = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/unblockListFilter");
					       if(unblockR.getViewRights() != null &&  unblockR.getViewRights()==1) {    
			int unblock = service.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.UNBLOCK.getStatus());
			String unblockUrl = "/CWFM/contractworkmen/gatepassActionListDashboardNav"
					+ "?principalEmployerId=" + principalEmployerId
			        + "&deptId=" + deptId
			        + "&action=" + GatePassType.UNBLOCK.getStatus();
			cards.add(new CardDto("Unblock ", "Manage unblocked workmen", String.valueOf(unblock), "border-onboarding", 
					"icon-onboarding", "resources/img/onboarding.png", unblockUrl,"Unblock")); 
					       }
					       CMSRoleRights blackR =new CMSRoleRights();
					       blackR = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/blackListFilter");
					       if(blackR.getViewRights() != null &&  blackR.getViewRights()==1) {  
			int black = service.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.BLACKLIST.getStatus());
			String blackurl = "/CWFM/contractworkmen/gatepassActionListDashboardNav"
					+ "?principalEmployerId=" + principalEmployerId
			        + "&deptId=" + deptId
			        + "&action=" + GatePassType.BLACKLIST.getStatus();
			cards.add(new CardDto("Blacklist", "Manage blacklisted workmen",  String.valueOf(black), 
					"border-renewals", "icon-renewals", "resources/img/verification.png", blackurl,"Blacklist")); 
					       }
					       
					       CMSRoleRights deblackR =new CMSRoleRights();
					       deblackR = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/deblackListFilter");
					       if(deblackR.getViewRights() != null &&  deblackR.getViewRights()==1) { 
			int deblack = service.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.DEBLACKLIST.getStatus());
			String deblackurl = "/CWFM/contractworkmen/gatepassActionListDashboardNav"
					+ "?principalEmployerId=" + principalEmployerId
			        + "&deptId=" + deptId
			        + "&action=" + GatePassType.DEBLACKLIST.getStatus();
			cards.add(new CardDto("DeBlacklist", "Manage deblacklisted workmen", String.valueOf(deblack), "border-renewals", 
					"icon-renewals", "resources/img/verification.png", deblackurl,"Deblacklist")); 
					       }
					       
					       CMSRoleRights cancelR =new CMSRoleRights();
					       cancelR = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/cancelFilter");
					       if( cancelR.getViewRights() != null && cancelR.getViewRights()==1) { 
			int cancel = service.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.CANCEL.getStatus());
			String cancelUrl = "/CWFM/contractworkmen/gatepassActionListDashboardNav"
					+ "?principalEmployerId=" + principalEmployerId
			        + "&deptId=" + deptId
			        + "&action=" + GatePassType.CANCEL.getStatus();
			cards.add(new CardDto("Cancel", "Manage cancelled workmen", String.valueOf(cancel), "border-renewals",
					"icon-renewals", "resources/img/verification.png", cancelUrl,"Cancel"));
					       }
					       CMSRoleRights renewR =new CMSRoleRights();
					       renewR = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/renewFilter");
					       if( renewR.getViewRights() != null && renewR.getViewRights()==1) { 	       
			int renew = service.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.RENEW.getStatus());
			String renewUrl = "/CWFM/contractworkmen/gatepassActionListDashboardNav"
					+ "?principalEmployerId=" + principalEmployerId
			        + "&deptId=" + deptId
			        + "&action=" + GatePassType.RENEW.getStatus();
			cards.add(new CardDto("Renew", "Track workmen renewals", String.valueOf(renew), "border-renewals"  ,
					 "icon-renewals", "resources/img/verification.png", renewUrl,"Renew")); 
					       }
					       CMSRoleRights lostR =new CMSRoleRights();
					       lostR = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/contractworkmen/lostordamageFilter");
					       if( lostR.getViewRights() != null && lostR.getViewRights()==1) { 	
			int lost = service.getGatePassActionListingForApprovers(principalEmployerId,deptId,user,GatePassType.LOSTORDAMAGE.getStatus());
			String lostUrl = "/CWFM/contractworkmen/gatepassActionListDashboardNav"
					+ "?principalEmployerId=" + principalEmployerId
			        + "&deptId=" + deptId
			        + "&action=" + GatePassType.LOSTORDAMAGE.getStatus();
			cards.add(new CardDto("Lost or Damage", "Manage lost or damaged gatepass", String.valueOf(lost), "border-renewals", "icon-renewals", 
					"resources/img/verification.png", lostUrl,"Lost or Damage")); 
					       }
		}
		model.addAttribute("cards", cards); 
		return "dashboard/csDashboard"; 
	}
}
