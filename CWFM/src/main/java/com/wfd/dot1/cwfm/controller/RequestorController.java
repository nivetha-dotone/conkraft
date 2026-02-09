package com.wfd.dot1.cwfm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.dto.*;
import com.wfd.dot1.cwfm.pojo.*;
import com.wfd.dot1.cwfm.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/RequestorCon")
public class RequestorController {

    @Autowired
    private RequestorService service;

    @Autowired
    private PlantZoneMappingService plantzoneServ;

    @Autowired
    private PrincipalEmployerService peService;
    @Autowired
    CommonService commonService;
    @Autowired
    WorkmenService workmenService;
    private static final Logger log = LoggerFactory.getLogger(RequestorController.class.getName());


    @PostMapping(value = "/saveRequestor", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> approveRejectBill(@RequestPart("request") String dto, @RequestParam(value = "attachCV", required = false) MultipartFile attachCV, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        try {
            String result=null;
            ObjectMapper mapper = new ObjectMapper();
            RequesterDto requesterDto = mapper.readValue(dto, RequesterDto.class);
            HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
            MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
            String updatedBy=user.getUserAccount();
            result = service.insertRequestor(requesterDto, attachCV,updatedBy);
            if(null!=result) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving data: " + e.getMessage());
        }
    }

    @GetMapping("/getGmData/{gmTypeId}")
    public String showEntriesForOrgLevel(@PathVariable Long gmTypeId, Model model) {
        System.out.println("Received orgLevelDefId: " + gmTypeId); // Debug log
        if (gmTypeId <= 0) {
            model.addAttribute("error", "Invalid General Type selected.");
            return "generalMaster/generalMaster";
        }
        List<GeneralMasterDTO> gmData = commonService.getGeneralMastersWithTypeName(gmTypeId);
        System.out.println("Retrieved entries: " + gmData); // Debug log
        model.addAttribute("generalMasters", gmData);
        model.addAttribute("gmTypeId", gmTypeId);
        List<CMSGMType> gmTypes = commonService.getAllGMTypes();  // Fetch GM Types for dropdown
        model.addAttribute("gmTypes", gmTypes);
        return "generalMaster/generalMaster";
    }

    @GetMapping("/addQuickOB")
    public String createGatePass(HttpServletRequest request,HttpServletResponse response) {

        HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

        String transactionId= workmenService.generateTransactionId();
        request.setAttribute("transactionId", transactionId);
        // workmenService.createDraftGatepass(transactionId,String.valueOf(user.getUserId()));
        List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
        Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
                .collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
        List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
        //List<PersonOrgLevel> departments = groupedByLevelDef.getOrDefault("Dept", new ArrayList<>());
        //List<PersonOrgLevel> subdepartments = groupedByLevelDef.getOrDefault("Area", new ArrayList<>());
        request.setAttribute("PrincipalEmployer", peList);
        // request.setAttribute("Dept", departments);
        // request.setAttribute("Subdept", subdepartments);

        //Skills
        //List<Skill> skillList = workmenService.getAllSkill();
        //request.setAttribute("Skills", skillList);
        //Eic
        //List<MasterUser> eicList = workmenService.getAllEicManager(user.getUserAccount());
        //request.setAttribute("EIC", eicList);
        //Get All GeneralMaster
        List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMaster();

        // Grouping the CmsGeneralMaster objects by gmType
        Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
                .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

        // Define the types and their corresponding request attribute names
        Map<String, String> attributeMapping = Map.of(
                "GENDER", "GenderOptions",
                "BLOODGROUP", "BloodGroup",
                "ACCESSAREA", "AccessArea",
                "ACADEMICS", "Academics",
                "WAGECATEGORY", "WageCategory",
                "BONUSPAYOUT", "BonusPayout",
                "ZONE", "Zone"
        );

        // Iterate over the attribute mappings and set the request attributes dynamically
        attributeMapping.forEach((type, attributeName) -> {
            List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
            request.setAttribute(attributeName, gmList1);
        });

        return "contractWorkmen/quickOBAdd";
    }

    @GetMapping("/files/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get("D:/utkarsh/attachment_docs/").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getListOfRequestor")
    public ResponseEntity<?> getListRequestor(  HttpServletRequest request){
        try{

            HttpSession session = request.getSession(false);
            MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
            String userAccount = user.getUserAccount();
            List<RequestorListDTO> listOfRequestor = service.getListOfRequestor(userAccount, request);

            if(listOfRequestor!=null){
                return new ResponseEntity<>(listOfRequestor, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getAttachCV/{tranId}")
    public ResponseEntity<Resource> getAttachCV(@PathVariable("tranId") String tranId,
                                                HttpServletRequest request) throws MalformedURLException {
        try{

            return service.callFileUrl(tranId, request);

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/getRequestBytr/{tranId}")
    public  ResponseEntity<?> grtRequestor(@PathVariable("tranId") String tranId){
        try{

            RequestorListDTO single = service.findSingle(tranId);
            if (single!=null){
                return new ResponseEntity<>(single,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/getListHRequestorController")
    public  ResponseEntity<?> getListForHRRequestor(){
        try{


//            user.getRoleName().toUpperCase().equals(UserRole.HR.getName()) &&
            if( true){

                List<RequestorListDTO> requestorListHR = service.getRequestorListHR("36528474");
                return new ResponseEntity<>(requestorListHR,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }


        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/updateRequest", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<?> updateRequestor(
            @RequestPart("data") String data,
            @RequestPart(value = "files", required = false) MultipartFile[] files,
            HttpServletRequest request) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            UpdateRequestDTO requesterDto = mapper.readValue(data, UpdateRequestDTO.class);

//
//            HttpSession session = request.getSession(false);
//            if (session == null) {
//                return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
//            }
//
//            MasterUser user = (MasterUser) session.getAttribute("loginuser");
//            if (user == null) {
//                return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
//            }

            String userAccount = "70012841";
//            String userAccount = user.getUserAccount();

            // Call service method
            String result = service.updateRequestor(requesterDto, userAccount, files);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getHRDOC/{attachId}")
    public ResponseEntity<Resource> getHRDOC(@PathVariable("attachId") String attachId,
                                             HttpServletRequest request) throws MalformedURLException {
        try{
            return service.callFileUrlHR(attachId, request);

        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/list")
    public ResponseEntity<?> listPlantZoneMappings(HttpServletRequest request, Model model) {
        try {
            // Fetch list from service
            List<PlantZoneMappingDTO> mappings = plantzoneServ.getListOfPlantZoneMappingDTO(request);

            return new ResponseEntity<>(mappings,HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }



}