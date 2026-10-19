package com.wfd.dot1.cwfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.enums.UserRole;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.DeptMapping;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.pojo.RegistrationLink;
import com.wfd.dot1.cwfm.pojo.Workorder;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.RegistrationLinkService;
import com.wfd.dot1.cwfm.service.WorkmenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/contractworkmen/registrationLinks")
public class RegistrationLinkController {

    @Autowired
    private RegistrationLinkService registrationLinkService;
    
    @Autowired
	CommonService commonService;
    
    @Autowired
	WorkmenService workmenService;

    @PostMapping("/generate")
    @ResponseBody
    public ResponseEntity<?> generateLink(
            @RequestBody RegistrationLink link,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Session expired"));
        }

        MasterUser user =
                (MasterUser) session.getAttribute("loginuser");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Please login again"));
        }

        if (!"CONTRACTORSUPERVISOR".equalsIgnoreCase(
                user.getRoleName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Access denied"));
        }

        try {
            /*
             * SECURITY REQUIREMENT:
             *
             * Before enabling link generation, validate:
             * 1. UnitId belongs to this supervisor's PE scope.
             * 2. ContractorId is mapped to this supervisor
             *    for the selected UnitId.
             * 3. WorkorderId belongs to the selected contractor
             *    and UnitId.
             * 4. DepartmentId and AreaId are authorized.
             * 5. WC/ESIC and LL are valid for the selected
             *    contractor and work order.
             *
             * Use your existing commonService and
             * workmenService methods for these checks.
             *
             * Do not trust IDs supplied by the browser.
             */

            String baseUrl =
                    request.getScheme() + "://"
                    + request.getServerName()
                    + (request.getServerPort() == 80
                            || request.getServerPort() == 443
                            ? ""
                            : ":" + request.getServerPort())
                    + request.getContextPath();

            validateSupervisorMappings(link, user);
            validateRegistrationSelections(link, user);
         // Generate link only after validation succeeds
            String generatedUrl =
                    registrationLinkService.generateRegistrationLink(
                            link,
                            String.valueOf(user.getUserId()),
                            baseUrl);

            return ResponseEntity.ok(
                    Map.of(
                            "message", "Registration link generated",
                            "registrationUrl", generatedUrl
                    )
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));

        } catch (Exception e) {

            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "message", "Unable to generate link"
                    ));
        }
    }
    
    private void validateSupervisorMappings(
            RegistrationLink link,
            MasterUser user) {

        List<PersonOrgLevel> mappings =
            commonService.getPersonOrgLevelDetails(
                user.getUserAccount()
            );

        boolean validPE = mappings.stream()
            .anyMatch(m ->
                "Principal Employer".equalsIgnoreCase(m.getLevelDef())
                && link.getUnitId().equals(String.valueOf(m.getId()))
            );

        boolean validContractor = mappings.stream()
            .anyMatch(m ->
                "Contractor".equalsIgnoreCase(m.getLevelDef())
                && link.getContractorId().equals(String.valueOf(m.getId()))
            );

        if (!validPE || !validContractor) {
            throw new IllegalArgumentException(
                "Selected Principal Employer or Contractor is not authorized"
            );
        }
     // Supervisor's authorized Department mappings
        List<PersonOrgLevel> authorizedDepts = mappings.stream()
                .filter(m -> "Dept".equalsIgnoreCase(m.getLevelDef()))
                .toList();

        boolean validSupervisorDept = authorizedDepts.stream()
                .anyMatch(m -> link.getDepartmentId()
                        .equals(String.valueOf(m.getId())));

        if (!validSupervisorDept) {
            throw new IllegalArgumentException(
                    "Department is not mapped to this supervisor");
        }

        // Supervisor's authorized Area mappings
        List<PersonOrgLevel> authorizedAreas = mappings.stream()
                .filter(m -> "Area".equalsIgnoreCase(m.getLevelDef()))
                .toList();

        boolean validSupervisorArea = authorizedAreas.stream()
                .anyMatch(m -> link.getAreaId()
                        .equals(String.valueOf(m.getId())));

        if (!validSupervisorArea) {
            throw new IllegalArgumentException(
                    "Area is not mapped to this supervisor");
        }
    }
    
    private void validateRegistrationSelections(
            RegistrationLink link,
            MasterUser user) {

        String unitId = link.getUnitId();
        String contractorId = link.getContractorId();
        String workorderId = link.getWorkorderId();

        // 1. Validate contractor against logged-in supervisor
        List<Contractor> contractors =
                workmenService.getAllContractorBasedOnPE(
                        unitId, user.getUserAccount());

        boolean validContractor = contractors.stream()
                .anyMatch(c -> contractorId.equals(c.getContractorId()));

        if (!validContractor) {
            throw new IllegalArgumentException(
                    "Invalid contractor for selected Principal Employer");
        }

        // 2. Validate work order
        List<Workorder> workorders =
                workmenService.getAllWorkordersBasedOnPEAndContractor(
                        unitId, contractorId);

        boolean validWorkorder = workorders.stream()
                .anyMatch(w -> workorderId.equals(w.getWorkorderId()));

        if (!validWorkorder) {
            throw new IllegalArgumentException(
                    "Invalid Work Order for selected contractor");
        }

        // 3. Validate WC / ESIC and Labour License
        List<CmsContractorWC> licenses =
                workmenService.getAllWCBasedOnPEAndCont(
                        unitId, contractorId, workorderId);

        boolean validWC = licenses.stream()
                .anyMatch(w ->
                        link.getWcEsicNo().equals(w.getWcId())
                        && ("WC".equalsIgnoreCase(w.getLicenceType())
                            || "ESIC".equalsIgnoreCase(w.getLicenceType())));

        boolean validLL = licenses.stream()
                .anyMatch(w ->
                        link.getLlNo().equals(w.getWcId())
                        && "LL".equalsIgnoreCase(w.getLicenceType()));

        if (!validWC || !validLL) {
            throw new IllegalArgumentException(
                    "Invalid WC/ESIC or Labour License selection");
        }
        
     // 4. Validate Department against selected Principal Employer
        List<DeptMapping> departments =
                workmenService.getAllDepartmentsOnPE(unitId);

        boolean validDepartment = departments.stream()
                .anyMatch(d ->
                        link.getDepartmentId().equals(
                                String.valueOf(d.getDepartmentId())));

        if (!validDepartment) {
            throw new IllegalArgumentException(
                    "Invalid Department for selected Principal Employer");
        }

        // 5. Validate Area against selected Department
        List<DeptMapping> areas =
                workmenService.getAllSubDepartments(
                        unitId, link.getDepartmentId());

        boolean validArea = areas.stream()
                .anyMatch(a ->
                        link.getAreaId().equals(
                                String.valueOf(a.getSubDepartmentId())));

        if (!validArea) {
            throw new IllegalArgumentException(
                    "Invalid Area for selected Department");
        }
    }
    
    @GetMapping("/page")
    public String registrationLinkPage(
            HttpServletRequest request,
            Model model) {

    	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

        if (user == null) {
            return "redirect:/login";
        }

        if (!UserRole.CONTRACTORSUPERVISOR.getName()
                .equalsIgnoreCase(user.getRoleName())) {
            throw new org.springframework.web.server
                    .ResponseStatusException(
                        HttpStatus.FORBIDDEN);
        }

        List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
		Map<String, List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
				.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
		List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
		
       
        request.setAttribute("principalEmployers", peList);

        return "contractWorkmen/generateRegistrationLink";
    }
}