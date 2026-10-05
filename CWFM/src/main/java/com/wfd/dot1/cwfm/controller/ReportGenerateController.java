package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.dto.FetchTotalRequestDto;
import com.wfd.dot1.cwfm.dto.FetchTotalResponseDto;
import com.wfd.dot1.cwfm.dto.ReportGenerateDto;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.ReportGenerateService;
import com.wfd.dot1.cwfm.service.WorkmenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reportGenerate")
public class ReportGenerateController {
	private static final Logger log = LoggerFactory.getLogger(ReportGenerateController.class.getName());

    @Autowired
    private WorkmenService workmenService;

    @Autowired
    private CommonService commonService;
    
    @Autowired
    private ReportGenerateService reportGenerateService;


	@Autowired
	CreateEmpFetchByGatePassAPICALL api;

    @GetMapping("/reportGenerateList")
    public String list(HttpServletRequest request,HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
        List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMaster();

        Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream().collect(
        		             Collectors.groupingBy(CmsGeneralMaster::getGmType));

        List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
        Map<String, List<PersonOrgLevel>> groupedByLevelDef =orgLevel.stream()
                        .collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
        List<PersonOrgLevel> principalEmployerList = groupedByLevelDef.getOrDefault("Principal Employer",new ArrayList<>());
        request.setAttribute("PrincipalEmployer",principalEmployerList);
        List<PersonOrgLevel> contractorList = groupedByLevelDef.getOrDefault("Contractor",new ArrayList<>());
        request.setAttribute("Contractors",contractorList);

		// Grouping the CmsGeneralMaster objects by gmType

		// Define the types and their corresponding request attribute names
		Map<String, String> attributeMapping = Map.of("REPORTTYPE", "ReportType");

		// Iterate over the attribute mappings and set the request attributes dynamically
		attributeMapping.forEach((type, attributeName) -> {
		    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
		    request.setAttribute(attributeName, gmList1);
		});
        return "reportGenerate/generateReports";
    }
    
    @PostMapping("/getGatePassIds")
    @ResponseBody
    public ResponseEntity<?> getGatePassIds(
    		@RequestParam("reportType") String reportType,
            @RequestParam("unitId") String unitId,
            @RequestParam("contractorId") String contractorId,
            @RequestParam("departmentId") String departmentId,
            @RequestParam("fromDate") String fromDate,
            @RequestParam("toDate") String toDate, 
            HttpServletRequest request,HttpServletResponse response) {

        try {
        	 HttpSession session = request.getSession(false);
             MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
             int requestedBy = user.getUserId();
             ReportGenerateDto result   = reportGenerateService.getGatePassIds(reportType,unitId,contractorId,departmentId,fromDate,toDate,requestedBy);
            
             Long requestId = result.getRequestId();
             List<String> gatePassIds = result.getGatePassIds();
             
             log.info("Request ID: {}", requestId);
             log.info("GatePass IDs: {}", gatePassIds);
             // Call UKG API only when GatePassIDs are available
            if (result != null) {

                FetchTotalRequestDto ukgRequest =new FetchTotalRequestDto();

                ukgRequest.setReqId(String.valueOf((requestId)));
                
                ukgRequest.setPersonNumbers(gatePassIds);

                ukgRequest.setStartDate(fromDate);

                ukgRequest.setEndDate(toDate);

                // 3. Call your existing API client method
                
                ResponseEntity<FetchTotalResponseDto> totalApiResponse=  api.fetchTotalFromUKG(ukgRequest);
                ResponseEntity<FetchTotalResponseDto> punchApiResponse=  api.fetchPunchFromUKG(ukgRequest);
                ResponseEntity<FetchTotalResponseDto> scheduleApiResponse=  api.fetchScheduleFromUKG(ukgRequest);
                
                System.out.println("Total api reponse:"+totalApiResponse.getStatusCode());
                String message = totalApiResponse.getBody().getMessage();
                System.out.println(message);
                System.out.println("Punch api reponse:"+punchApiResponse.getStatusCode());
                System.out.println("Schedule api reponse:"+scheduleApiResponse.getStatusCode());
               // api.fetchPunchFromUKG(ukgRequest);
              //  api.fetchScheduleFromUKG(ukgRequest);
                
            }

            // 4. Existing response
            return ResponseEntity.ok(result);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unable to fetch GatePass IDs");
        }
    }
}

