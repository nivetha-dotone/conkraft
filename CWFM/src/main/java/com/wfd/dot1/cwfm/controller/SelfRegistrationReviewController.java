
package com.wfd.dot1.cwfm.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.wfd.dot1.cwfm.util.ApplicationConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.wfd.dot1.cwfm.enums.UserRole;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.SelfRegistrationReviewService;
import com.wfd.dot1.cwfm.service.SelfRegistrationReviewService.AuthorizedPair;
import com.wfd.dot1.cwfm.service.WorkmenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/self-registration/review")
public class SelfRegistrationReviewController {

    private final SelfRegistrationReviewService review;
    private final com.wfd.dot1.cwfm.service.SelfRegistrationAcceptanceService acceptance;
    private final CommonService common;
    private final WorkmenService workmenService;
    private final JdbcTemplate jdbcTemplate;

    public SelfRegistrationReviewController(
            SelfRegistrationReviewService review,
            com.wfd.dot1.cwfm.service.SelfRegistrationAcceptanceService acceptance,
            CommonService common,
            WorkmenService workmenService,
            JdbcTemplate jdbcTemplate) {

        this.review = review;
        this.acceptance = acceptance;
        this.common = common;
        this.workmenService = workmenService;
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
     * 1. GET CURRENT LOGGED-IN SUPERVISOR
     */
    private MasterUser current(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        Object value = session == null
                ? null
                : session.getAttribute("loginuser");

        if (!(value instanceof MasterUser user)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Session expired. Please log in again.");
        }

        String roleName = user.getRoleName();

        if (roleName == null ||
                !roleName.equalsIgnoreCase(
                        UserRole.CONTRACTORSUPERVISOR.getName())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Only Contractor Supervisors can access this page.");
        }

        return user;
    }

    /*
     * 2. GET AUTHORIZED PE AND CONTRACTOR IDS
     *
     * These IDs come from the logged-in supervisor's
     * organization-level mapping.
     */
    private List<PersonOrgLevel> getOrgLevels(MasterUser user) {

        List<PersonOrgLevel> levels =
                common.getPersonOrgLevelDetails(
                        user.getUserAccount());

        return levels == null ? List.of() : levels;
    }

    /*
     * 3. BUILD AUTHORIZED PAIRS
     *
     * IMPORTANT:
     * The existing PersonOrgLevel data exposes PE and
     * Contractor IDs separately.
     *
     * This method creates combinations from those IDs.
     * It is valid ONLY if your Conkraft authorization
     * rules permit every such combination.
     *
     * If mappings are pair-specific, replace this method
     * with a query returning the actual authorized pairs.
     */
    private List<AuthorizedPair> authorizedPairs(MasterUser user) {

        List<PersonOrgLevel> levels = getOrgLevels(user);

        Set<String> peIds = new LinkedHashSet<>();
        Set<String> contractorIds = new LinkedHashSet<>();

        for (PersonOrgLevel level : levels) {

            if (level == null || level.getId() == null) {
                continue;
            }

            if ("Principal Employer".equalsIgnoreCase(
                    level.getLevelDef())) {

                peIds.add(String.valueOf(level.getId()));

            } else if ("Contractor".equalsIgnoreCase(
                    level.getLevelDef())) {

                contractorIds.add(String.valueOf(level.getId()));
            }
        }

        List<AuthorizedPair> pairs = new ArrayList<>();

        for (String peId : peIds) {
            for (String contractorId : contractorIds) {
                pairs.add(new AuthorizedPair(peId, contractorId));
            }
        }

        return pairs;
    }

    /*
     * 4. VALIDATE SELECTED PE + CONTRACTOR
     */
    private void authorizePair(
            MasterUser user,
            String unitId,
            String contractorId) {

        boolean allowed = authorizedPairs(user).stream()
                .anyMatch(pair ->
                        pair.unitId().equals(unitId)
                        && pair.contractorId().equals(contractorId));

        if (!allowed) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not authorized for this PE and Contractor.");
        }
    }

    /*
     * 5. LOAD SUPERVISOR LISTING JSP
     */
    @GetMapping
    public String listing(
            HttpServletRequest request,
            Model model) {

        MasterUser user = current(request);

        List<PersonOrgLevel> levels = getOrgLevels(user);

        model.addAttribute(
                "peOptions",
                levels.stream()
                        .filter(x ->
                                "Principal Employer".equalsIgnoreCase(
                                        x.getLevelDef()))
                        .toList());

        model.addAttribute(
                "contractorOptions",
                levels.stream()
                        .filter(x ->
                                "Contractor".equalsIgnoreCase(
                                        x.getLevelDef()))
                        .toList());

        return "contractWorkmen/selfRegistrationList";
    }

    /*
     * 6. FETCH LISTING ROWS
     *
     * URL:
     * /self-registration/review/rows?unitId=34&contractorId=265
     */
    @GetMapping("/rows")
    @ResponseBody
    public List<Map<String, Object>> rows(
            @RequestParam String unitId,
            @RequestParam String contractorId,
            HttpServletRequest request) {

        MasterUser user = current(request);

        authorizePair(user, unitId, contractorId);

        return review.list(unitId, contractorId);
    }

    /*
     * 7. VIEW REGISTRATION DETAILS
     *
     * URL:
     * /self-registration/review/1
     *
     * UnitId and ContractorId are read from the
     * registration record inside the service.
     */
    @GetMapping("/view")
    public String detail(
            @RequestParam("registrationId") long registrationId,
            HttpServletRequest request,
            Model model) {

        MasterUser user = current(request);

        List<AuthorizedPair> pairs = authorizedPairs(user);

        try {
            Map<String, Object> item =
                    review.detail(registrationId, pairs);

            Map<String, String> fields =
                    resolvedFields(review.fields(item), item);

            List<Map<String, Object>> documents =
                    review.documents(registrationId, pairs);

            model.addAttribute("item", item);
            model.addAttribute("fields", fields);
            model.addAttribute("documents", documents);
            model.addAttribute("eicName", resolveEicName(item));
            model.addAttribute("wcEsicName", resolveLicence(item.get("WcEsicNo")));
            model.addAttribute("llName", resolveLicence(item.get("LlNo")));

            return "contractWorkmen/selfRegistrationReview";

        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Registration not found");
        }
    }
    


    private String resolveEicName(Map<String, Object> item) {
        String id = value(item.get("EicId"));
        if (id.isBlank()) return "";
        List<MasterUser> managers = workmenService.getAllEicManager(
                value(item.get("UnitId")), value(item.get("DepartmentId")));
        if (managers != null) {
            for (MasterUser manager : managers) {
                if (id.equals(String.valueOf(manager.getUserId()))) {
                    return manager.getFullName();
                }
            }
        }
        return id;
    }

    private String resolveLicence(Object idObject) {
        String id = value(idObject);
        if (id.isBlank()) return "";
        List<String> numbers = jdbcTemplate.queryForList(
                "SELECT LICENSE_NUMBER FROM dbo.CMSWORKORDER_LLWC WHERE WOLLID = ?",
                String.class, id);
        return numbers.isEmpty() || numbers.get(0) == null ? id : numbers.get(0);
    }

    private String value(Object object) {
        return object == null ? "" : String.valueOf(object);
    }

    private Map<String, String> resolvedFields(
            Map<String, String> fields, Map<String, Object> item) {

        Map<String, String> result = new HashMap<>(fields);
        List<CmsGeneralMaster> masters = workmenService.getAllGeneralMaster();
        if (masters != null) {
            Map<String, String> names = new HashMap<>();
            for (CmsGeneralMaster gm : masters) {
                names.put(String.valueOf(gm.getGmId()), gm.getGmName());
            }
            for (String key : List.of("bloodGroupId", "academicId", "wageCategoryId",
                    "bonusPayoutId", "accessArea", "workmenType", "gender")) {
                String value = fields.get(key);
                if (value != null && !value.isBlank()) {
                    result.put(key + "Name", names.getOrDefault(value, value));
                }
            }
        }

        String unitId = String.valueOf(item.get("UnitId"));
        String departmentId = String.valueOf(item.get("DepartmentId"));
        result.put("departmentName", departmentId);
        result.put("areaName", String.valueOf(item.get("AreaId")));
        if (workmenService.getAllDepartmentsOnPE(unitId) != null) {
            workmenService.getAllDepartmentsOnPE(unitId).forEach(d -> {
                if (departmentId.equals(String.valueOf(d.getDepartmentId()))) {
                    result.put("departmentName", d.getDepartment());
                }
            });
        }
        if (workmenService.getAllSubDepartments(unitId, departmentId) != null) {
            workmenService.getAllSubDepartments(unitId, departmentId).forEach(d -> {
                if (String.valueOf(item.get("AreaId")).equals(String.valueOf(d.getSubDepartmentId()))) {
                    result.put("areaName", d.getSubDepartment());
                }
            });
        }
        return result;
    }

    @GetMapping("/document/{documentId}")
    public ResponseEntity<Resource> viewDocument(
            @org.springframework.web.bind.annotation.PathVariable("documentId") long documentId,
            HttpServletRequest request) throws IOException {

        MasterUser user = current(request);
        Map<String, Object> doc;
        try {
            doc = review.documentForReview(documentId, authorizedPairs(user));
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found");
        }

        String configured = ApplicationConfig.getProperty("registration.upload.dir");
        if (configured == null || configured.isBlank()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Upload directory missing");
        }
        Path root = Paths.get(configured).toAbsolutePath().normalize();
        String filename = String.valueOf(doc.get("StoredFileName"));
        long registrationId = ((Number) doc.get("RegistrationId")).longValue();
        Path folder = root.resolve(Long.toString(registrationId)).normalize();
        Path path = folder.resolve(filename).normalize();
        Path recorded = Paths.get(String.valueOf(doc.get("StoragePath"))).toAbsolutePath().normalize();
        if (!folder.startsWith(root) || !path.startsWith(folder)
                || !path.equals(recorded)
                || !Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS)
                || !path.toRealPath().startsWith(root.toRealPath())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found");
        }
        String lower = filename.toLowerCase(Locale.ROOT);
        MediaType type;
        if (lower.endsWith(".pdf")) type = MediaType.APPLICATION_PDF;
        else if (lower.endsWith(".png")) type = MediaType.IMAGE_PNG;
        else if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) type = MediaType.IMAGE_JPEG;
        else throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE);

        return ResponseEntity.ok().contentType(type)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .header(HttpHeaders.CACHE_CONTROL, "no-store")
                .header("X-Content-Type-Options", "nosniff")
                .body(new FileSystemResource(path));
    }
    @PostMapping("/reject")
    @ResponseBody
    public ResponseEntity<Map<String, String>> reject(
            @RequestParam("registrationId") long registrationId,
            @RequestParam("reason") String reason,
            HttpServletRequest request) {
        MasterUser user = current(request);
        review.reject(registrationId, reason, String.valueOf(user.getUserId()), authorizedPairs(user));
        return ResponseEntity.ok(Map.of("message", "Registration rejected successfully."));
    }

    @PostMapping("/accept")
    @ResponseBody
    public ResponseEntity<Map<String, String>> accept(
            @RequestParam("registrationId") long registrationId,
            HttpServletRequest request) {
        MasterUser user = current(request);
        String transactionId = acceptance.accept(registrationId,
                String.valueOf(user.getUserId()), authorizedPairs(user));
        return ResponseEntity.ok(Map.of("message", "Draft created successfully.",
                "transactionId", transactionId));
    }

}
