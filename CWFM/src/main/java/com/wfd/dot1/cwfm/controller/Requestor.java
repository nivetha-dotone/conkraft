package com.wfd.dot1.cwfm.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.dto.RequesterDto;
import com.wfd.dot1.cwfm.dto.RequestorListDTO;
import com.wfd.dot1.cwfm.dto.UpdateRequestDTO;
import com.wfd.dot1.cwfm.dto.workMenFaceDropDto;
import com.wfd.dot1.cwfm.pojo.*;
import com.wfd.dot1.cwfm.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;





@Controller
@RequestMapping("/requestor")
public class Requestor {

    @Autowired
    WorkmenService workmenService;
    @Autowired
    CommonService commonService;
    @Autowired
    private RequestorService service;
    @Autowired
    private PrincipalEmployerService peService;

    @Autowired
    private FaceRegistrationRepository faceRegistrationRepository;
    @GetMapping("/mobilePunchFace")
    public String cwfmfacePunch(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        MasterUser user = (session != null)
                ? (MasterUser) session.getAttribute("loginuser")
                : null;

        if (user == null) {
            return "redirect:/login"; // or error page
        }
        Integer userId = user.getUserId();
        List<workMenFaceDropDto> contractors=null;
        System.out.println(userId);
        if(userId!=null){
            contractors=new ArrayList<>();
            contractors = faceRegistrationRepository.getListWorkmen(userId);

        }
        request.setAttribute("contractors",contractors);

        return "requesTor/mobilePunchFace";
    }

    @GetMapping("/mFaceRegistration")
    public String cwfmfaceRegistration(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        MasterUser user = (session != null)
                ? (MasterUser) session.getAttribute("loginuser")
                : null;

        if (user == null) {
            return "redirect:/login";
        }
        Integer userId = user.getUserId();
        List<workMenFaceDropDto> contractors=null;
        System.out.println(userId);
        if(userId!=null){
            contractors=new ArrayList<>();
            contractors = faceRegistrationRepository.getListWorkmen(userId);

        }
        request.setAttribute("contractors",contractors);


        return "requesTor/mobilePunchf";
    }


    @GetMapping("/getAllWorkmen")
    public ResponseEntity<List<workMenFaceDropDto>> getAllWorkmenByID(@RequestParam("userId") Integer userId ,HttpServletRequest request,HttpServletResponse response)
    {
        try {
            HttpSession session = request.getSession(false);
            MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
//            Integer userId = user.getUserId();
            List<workMenFaceDropDto> contractors=null;
            System.out.println(userId);
            if(userId!=null){
                contractors=new ArrayList<>();
                contractors = faceRegistrationRepository.getListWorkmen(userId);

            }
            request.setAttribute("contractors",contractors);


            if(contractors.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(contractors,HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


// @GetMapping("/getAllWorkmen")
//    public ResponseEntity<List<workMenFaceDropDto>> getAllWorkmenByID(HttpServletRequest request,HttpServletResponse response)
//    {
//        try {
//            HttpSession session = request.getSession(false);
//            MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
//            Integer userId = user.getUserId();
//            List<workMenFaceDropDto> contractors=null;
//            if(userId!=null){
//                contractors=new ArrayList<>();
//                 contractors = faceRegistrationRepository.getListWorkmen(userId);
//
//            }
//            request.setAttribute("contractors",contractors);
//
//
//            if(contractors.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//
//            return new ResponseEntity<>(contractors,HttpStatus.OK);
//        }catch(Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping(value = "/saveRequestor", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> approveRejectBill(@RequestPart("request") String dto, @RequestParam(value = "attachCV", required = false) MultipartFile attachCV, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException
    {
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




    @GetMapping("/getRequestor")
    public String mastersAdminPage(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        MasterUser user = (session != null) ? (MasterUser) session.getAttribute("loginuser") : null;

        List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
        Map<String, List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
                .collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));

        List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
        request.setAttribute("PrincipalEmployer", peList);

//        if (!peList.isEmpty()) {
//            String firstPEId = peList.get(0).getId();
//
//            // Fetch contractors for the first PE
//            List<Contractor> contractors = service.getAllContractor(user.getUserAccount(), firstPEId);
//            request.setAttribute("contractors", contractors);
//
//            // Fetch departments for the first PE
//            List<DeptMapping> departments = workmenService.getAllDepartmentsOnPE(firstPEId);
//            request.setAttribute("departments", departments);
//        } else
        request.setAttribute("contractors", new ArrayList<>());
        request.setAttribute("departments", new ArrayList<>());
//        }

        List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMaster();
        Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
                .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

        Map<String, String> attributeMapping = Map.of(
                "GENDER", "GenderOptions",
                "BLOODGROUP", "BloodGroup",
                "ACCESSAREA", "AccessArea",
                "ACADEMICS", "Academics",
                "WAGECATEGORY", "WageCategory",
                "BONUSPAYOUT", "BonusPayout",
                "ZONE", "Zone"
        );

        attributeMapping.forEach((type, attributeName) -> {
            List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
            request.setAttribute(attributeName, gmList1);
        });

        return "requesTor/requester";
    }


    @GetMapping("/getdropCon/{unitId}")
    public ResponseEntity<List<Contractor>> getContractorsByUnit(HttpServletRequest request, @PathVariable String unitId) {
        HttpSession session = request.getSession(false);
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            // This calls your service which executes the SQL query you provided
            List<Contractor> result = service.getAllContractor(user.getUserAccount(), unitId);


            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getdropGEtPR")
    public ResponseEntity<?> getPrincipleEmployerByID(HttpServletRequest request ) {
        try {

            HttpSession session = request.getSession(false);
            MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
            Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
                    .collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
            List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
            request.setAttribute("PrincipalEmployer", peList);




            return ResponseEntity.ok(peList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("/getAllContractors")
    public ResponseEntity<List<Contractor>> getAllContractors(
            @RequestParam("unitId") String unitId,
            HttpServletRequest request,HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
            List<Contractor> contractors=new ArrayList<Contractor>();
            if(user!=null) {
                contractors = workmenService.getAllContractorBasedOnPE(unitId,
                        user.getUserAccount());
            }
            if (contractors.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(contractors, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/getAllDepartments")
    public ResponseEntity<List<DeptMapping>> getAllDepartments(@RequestParam("unitId")String unitId,HttpServletRequest request,HttpServletResponse response)
    {
        try {
            HttpSession session = request.getSession(false);
            MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
            List<DeptMapping> departments=null;
            if(user!=null){
                departments=new ArrayList<>();
                departments = workmenService.getAllDepartmentsOnPE(unitId);
            }

            request.setAttribute("departments",departments);

            if(departments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(departments,HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    @GetMapping("/getRequestorList")
    public String getReqestorList(HttpServletRequest request, HttpServletResponse response, Model model){
        try{

            HttpSession session = request.getSession(false);
            MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
            String userAccount = user.getUserAccount();

            List<RequestorListDTO> requestorList = service.getListOfRequestor(userAccount, request);
            model.addAttribute("requestorList", requestorList);
            return "requesTor/requestorList";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/getAttachCV/{tranId}")
    public ResponseEntity<Resource> getAttachCV(@PathVariable("tranId") String tranId,
                                                HttpServletRequest request) throws MalformedURLException {
        try{
            return service.callFileUrl(tranId, request);

        }catch (Exception e) {
            throw new RuntimeException(e);
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

    @PutMapping(value="/updateRequest",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> updateRequestor(
            @RequestPart("data") String data,
            @RequestPart(value = "files", required = false) MultipartFile[] files,
            HttpServletRequest request) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            UpdateRequestDTO requesterDto = mapper.readValue(data, UpdateRequestDTO.class);

            HttpSession session = request.getSession(false);
            if (session == null) {
                return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
            }

            MasterUser user = (MasterUser) session.getAttribute("loginuser");
            if (user == null) {
                return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
            }

            String userAccount = user.getUserAccount();

            if (requesterDto.getAORstatusBy() == null || requesterDto.getAORstatusBy().isEmpty()) {
                requesterDto.setAORstatusBy(userAccount);
            }

            // Call service method
            String result = service.updateRequestor(requesterDto, userAccount, files);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






    @GetMapping("/getListHRequestor")
    public  String getListForHRRequestor(HttpServletRequest request,HttpServletResponse response){
        try{

            HttpSession session = request.getSession(false);
            MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
            String userAccount = user != null ? user.getUserAccount() : null;
            if( userAccount!=null){
                List<RequestorListDTO> requestorListHR = service.getRequestorListHR(userAccount);
                request.setAttribute("requestorListHR", requestorListHR);
                return "requesTor/requestorHRList";
            }else{
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}


