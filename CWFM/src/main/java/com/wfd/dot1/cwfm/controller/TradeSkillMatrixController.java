package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.dto.CertificationDTO;
import com.wfd.dot1.cwfm.dto.GatePassTradeSkillDTO;
import com.wfd.dot1.cwfm.dto.TradeSkillDTO;
import com.wfd.dot1.cwfm.dto.TradeSkillListingDto;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;
import com.wfd.dot1.cwfm.service.TradeSkillService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/tradeSkillMatrix")
public class TradeSkillMatrixController {
	
	@Autowired
	TradeSkillService service;
	
	
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
	    	//List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
	    	List<PersonOrgLevel> businessType = groupedByLevelDef.getOrDefault("Business Type", new ArrayList<>());
	    	
	    	List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
	        CMSRoleRights rr =new CMSRoleRights();
	        rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/tradeSkillMatrix/list");
	   	    listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
	   	    request.setAttribute("UserPermission", rr);
	    	request.setAttribute("principalEmployers", peList);
	    	  
			return "tradeSkillMatrix/list";
		}
	 
	 @PostMapping("/listingDetails")
	    public ResponseEntity<List<TradeSkillListingDto>> listingDetails(
	    		@RequestParam(value = "principalEmployerId", required = false) String unitId,
	    		HttpServletRequest request,HttpServletResponse response) {
	    	
	    	try {
				HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
				MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
				List<TradeSkillListingDto> listDto = new ArrayList<TradeSkillListingDto>();
				listDto = service.getWorkmenListBasedOnPE(unitId);
					if (listDto.isEmpty()) {
						return new ResponseEntity<>(HttpStatus.NO_CONTENT);
					}
				
				return new ResponseEntity<>(listDto, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
	    }
	 
	 @GetMapping("/createTradeSkillMapping")
	    public String createTradeSkillMapping(@RequestParam(value = "unitId", required = false) String unitId,
	    		@RequestParam(value = "gatePassId", required = false) String gatePassId,
	    		HttpServletRequest request,HttpServletResponse response) {
		
			HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
	        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
	        List<CmsGeneralMaster> tradeList  = service.getAllTradeSkillBasedOnPe(unitId);
	        List<CmsGeneralMaster> proficiencyList  = service.getAllProLevel();
	        List<CmsGeneralMaster> certList  = service.getAllCert();
	        request.setAttribute("PROLEVEL", proficiencyList);
	    	request.setAttribute("TRADE", tradeList);
	    	request.setAttribute("unitId", unitId);
	    	request.setAttribute("gatePassId", gatePassId);
	    	request.setAttribute("CertificationList", certList);
	        return "tradeSkillMatrix/tradeSkillMappingCreate";
	    }
	    
	 @PostMapping("/saveTradeSkill")
	 @ResponseBody
	 public ResponseEntity<String> saveTradeSkill(
	         @RequestParam("jsonData") String jsonData,
	         HttpSession session) {

	     try {
	         ObjectMapper mapper = new ObjectMapper();
	         GatePassTradeSkillDTO dto =
	                 mapper.readValue(jsonData, GatePassTradeSkillDTO.class);

	         MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
	         try {
	         service.saveTradeSkill(dto, String.valueOf(user.getUserId()));

	         return ResponseEntity.ok("SUCCESS");
	         }catch (DuplicateKeyException e) {

	             // ✅ friendly message for UI
	             return ResponseEntity
	                     .status(409)
	                     .body("DUPLICATE");

	         } catch (Exception e) {
	             e.printStackTrace();
	             return ResponseEntity.status(500).body("ERROR");
	         }

	     } catch (Exception e) {
	         e.printStackTrace();
	         return ResponseEntity.status(500).body("ERROR");
	     }
	 }
	 
	 @GetMapping("/viewTradeSkill/{gatePassId}")
	 public String viewTradeSkill(@PathVariable String gatePassId,
			 HttpServletRequest request,HttpServletResponse response) {
		 List<TradeSkillDTO> list =  service.viewTradeSkill(gatePassId);
		  List<CertificationDTO>  cert= service.getCertification(gatePassId);
		  request.setAttribute("Certification", cert);
		 request.setAttribute("TradeSkillProList",list);
		 request.setAttribute("gatePassId",gatePassId);
		 return "tradeSkillMatrix/view";
	 }


}
