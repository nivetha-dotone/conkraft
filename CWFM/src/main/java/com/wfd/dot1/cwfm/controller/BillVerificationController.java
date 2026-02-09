package com.wfd.dot1.cwfm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.dto.ApproveRejectBillDto;
import com.wfd.dot1.cwfm.dto.CMSWageCostDTO;
import com.wfd.dot1.cwfm.dto.ChecklistItemDTO;
import com.wfd.dot1.cwfm.enums.UserRole;
import com.wfd.dot1.cwfm.pojo.BillReportFile;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.KronosReport;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.StatutoryAttachment;
import com.wfd.dot1.cwfm.service.BillConfigService;
import com.wfd.dot1.cwfm.service.BillVerificationService;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.ContractorService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;
import com.wfd.dot1.cwfm.service.WorkmenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/billVerification")
public class BillVerificationController {
	
	@Autowired
	BillVerificationService billService;
	
	@Autowired
	WorkmenService workmenService;

	@Autowired
	PrincipalEmployerService peService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
    private BillConfigService billConfigService;

	@Autowired
	ContractorService contrService;
	private static final String ROOT_DIRECTORY = "C:/wfd_cwfm/ep_docs/";
	@GetMapping("/viewlist")
    public String billVerification(HttpServletRequest request,HttpServletResponse response) {
    	 
    	return "bill/billverification";
	 }
    @GetMapping("/listingFilter")
    public String getbillList(HttpServletRequest request, HttpServletResponse response) {

    	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);

		
		List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());
    	Map<String,List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
    			.collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
    	List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
    	List<PersonOrgLevel> contractors = groupedByLevelDef.getOrDefault("Contractor", new ArrayList<>());
    	
    	List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
        CMSRoleRights rr =new CMSRoleRights();
        rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/billVerification/listingFilter");
   	    listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
   	    request.setAttribute("UserPermission", rr);
    	request.setAttribute("principalEmployers", peList);
    	  request.setAttribute("Contr", contractors);
    		return "bill/list";

    	
    }
    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<List<CMSWageCostDTO>> list(
    		@RequestParam(value = "principalEmployerId", required = false) String principalEmployerId,
    		@RequestParam(value = "deptId", required = false) String deptId,//deptId is contractorId not department
    		HttpServletRequest request,HttpServletResponse response) {
    	
    	try {
			HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
			MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
			List<CMSWageCostDTO> listDto = new ArrayList<CMSWageCostDTO>();
			if(user.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){
    		listDto=billService.getBillVerificationList(String.valueOf(user.getUserId()),deptId,principalEmployerId);
			}else {	
			
				listDto=billService.getBillVerificationListForApprovers(principalEmployerId,deptId,user);
    		}	
				if (listDto.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
			
			return new ResponseEntity<>(listDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    
    @GetMapping("/view/{transactionId}")
    public String viewIndividualBillDetails(@PathVariable("transactionId") String transactionId,HttpServletRequest request,HttpServletResponse response) {
    	CMSWageCostDTO obj =null;
    	HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    	try {
    		CMSWageCostDTO  dto= billService.getIndividualBillDetails(transactionId);
    		request.setAttribute("bvr", dto);
    		List<BillReportFile>  kronosReport= billService.findByTransactionIdAndType(transactionId, "Kronos");
    		request.setAttribute("kronosFiles", kronosReport);
    		List<BillReportFile>  statReport= billService.findByTransactionIdAndType(transactionId, "Statutory");
    		request.setAttribute("statutoryFiles", statReport);
    		 List<ChecklistItemDTO> checklistItems = billService.getChecklistByTransactionId(transactionId);
    	        request.setAttribute("checklistItems", checklistItems);
    	}catch(Exception e) {
    		
    	}
		return "bill/view"; // Return the JSP file name
	}
    @GetMapping("/viewFile/{encodedData}")
    public ResponseEntity<InputStreamResource> viewFile(@PathVariable("encodedData") String encodedData) {
        try {
            // Decode the Base64-encoded JSON
            String decodedJson = new String(Base64.getUrlDecoder().decode(encodedData), StandardCharsets.UTF_8);
            System.out.println("🔍 Decoded JSON: " + decodedJson);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> data = mapper.readValue(decodedJson, new TypeReference<>() {});

            String reportType = data.get("reportType");
            String transactionId = data.get("transactionId");
            String fileName = data.get("fileName");

            // ✅ Build file path based on report type
            String baseDir;
            if ("Kronos".equalsIgnoreCase(reportType)) {
                baseDir = "D:/wfd_cwfm/Bill/KronosReports/" + transactionId + "/";
            } else if ("Statutory".equalsIgnoreCase(reportType)) {
                baseDir = "D:/wfd_cwfm/Bill/StatutoryReports/" + transactionId + "/";
            } else {
                System.out.println("❌ Unknown reportType: " + reportType);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                     .body(null);
            }

            File file = new File(baseDir + fileName);
            System.out.println("📁 Looking for file: " + file.getAbsolutePath());

            if (!file.exists()) {
                System.out.println("❌ File not found at: " + file.getAbsolutePath());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Determine MIME type
            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            // Serve file for inline view (not download)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"");

            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    
    @GetMapping("/createBill")
    public String createBill(HttpServletRequest request,HttpServletResponse response) {
	
		HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
        MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
        String id = billService.generateWCTransactionId();
		request.setAttribute("transactionId", id);
		List<PrincipalEmployer> peList = billService.getPEDetailByUser(user.getUserAccount());
		request.setAttribute("peList", peList);
		
		List<CmsGeneralMaster> gmList = workmenService.getAllGeneralMaster();

		// Grouping the CmsGeneralMaster objects by gmType
		Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
		        .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

		// Define the types and their corresponding request attribute names
		Map<String, String> attributeMapping = Map.of(
		        "BILL TYPE","BillType",
		        "BILL CATEGORY","BillCategory",
		        "CHECKLIST STATUS","ChecklistStatus"
		);

		// Iterate over the attribute mappings and set the request attributes dynamically
		attributeMapping.forEach((type, attributeName) -> {
		    List<CmsGeneralMaster> gmList1 = groupedByGmType.getOrDefault(type, new ArrayList<>());
		    request.setAttribute(attributeName, gmList1);
		});

		request.setAttribute("kronosReports", billConfigService.fetchKronosReportsWithId());
		request.setAttribute("statutoryReports", billConfigService.fetchStatutoryReportsWithId());
		request.setAttribute("checklistItems", billConfigService.getAllChecklistItems());
		
        return "bill/add";
    }
    
    @PostMapping("/saveBill")
    public ResponseEntity<String> saveBill(
        @RequestParam("jsonData") String jsonData,
        @RequestParam("checklistJson") String checklistJson,
        @RequestParam Map<String, MultipartFile> files,HttpServletRequest request,HttpServletResponse response
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
            MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
        	
            CMSWageCostDTO workflowData = mapper.readValue(jsonData, CMSWageCostDTO.class);
            workflowData.setCreatedBy(String.valueOf(user.getUserId()));
            workflowData.setUpdateBy(String.valueOf(user.getUserId()));
            List<ChecklistItemDTO> checklistItems = Arrays.asList(
                mapper.readValue(checklistJson, ChecklistItemDTO[].class)
            );

            // Save core data
          String wcTransId= billService.save(workflowData,checklistItems);
          if (wcTransId != null) {
        	  //save reports to drive and checklist to db
        	  
        	  String kronosBasePath = "D:/wfd_cwfm/Bill/KronosReports/" + wcTransId + "/";
        	  String statutoryBasePath = "D:/wfd_cwfm/Bill/StatutoryReports/" + wcTransId + "/";

        	  // Create directories if they don't exist
        	  new File(kronosBasePath).mkdirs();
        	  new File(statutoryBasePath).mkdirs();
        	  
        	 
        	  List<KronosReport> kronosReportName=billConfigService.fetchKronosReportsWithId();

        		List<StatutoryAttachment> statReportName=billConfigService.fetchStatutoryReportsWithId();
        		Map<Integer, String> reportMap = kronosReportName.stream()
        			    .collect(Collectors.toMap(KronosReport::getId, KronosReport::getReportName));
        		
        		Map<Integer, String> reportMap1 = statReportName.stream()
        			    .collect(Collectors.toMap(StatutoryAttachment::getId, StatutoryAttachment::getAttachmentName));

        	  for (Map.Entry<String, MultipartFile> entry : files.entrySet()) {
        	      String key = entry.getKey();
        	      int repotrId ; 
        	      String reportName = null;
        	      MultipartFile file = entry.getValue();

        	      if (file != null && !file.isEmpty()) {
        	          String originalFilename = file.getOriginalFilename();
        	          String targetDir;
        	          BillReportFile b = new BillReportFile();
    	              b.setTransactionId(workflowData.getWcTransId());
    	             
        	          if (key.startsWith("kronosFile")) {
        	        	  b.setReportType("Kronos");
        	              targetDir = kronosBasePath;
        	               repotrId =Integer.parseInt( key.replaceAll("\\D+", "")); 
                	       reportName = reportMap.get(repotrId);
        	          } else if (key.startsWith("statutoryFile")) {
        	              targetDir = statutoryBasePath;
        	              b.setReportType("Statutory");
        	              repotrId =Integer.parseInt( key.replaceAll("\\D+", "")); 
               	       reportName = reportMap1.get(repotrId);
        	          } else {
        	              // Skip unknown file types
        	              continue;
        	          }
        	          b.setReportName(reportName);
        	          try {
        	              File destination = new File(targetDir + originalFilename);
        	              file.transferTo(destination);
        	              System.out.println("Saved: " + destination.getAbsolutePath());
        	              b.setFileName(originalFilename);
        	              billService.saveFile(b);
        	          } catch (IOException e) {
        	              e.printStackTrace();
        	              // Optionally log or handle failure
        	          }
        	      }
        	  }

        	  //save checklist
        	 //billService.saveChecklist(checklistItems,wcTransId,String.valueOf(user.getUserId()));
        	  
        	   return new ResponseEntity<>("billVerification/list", HttpStatus.OK);
          }
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Save failed");
        }
    }
    
    @GetMapping("/downloadFile/{reportType}/{transactionId}/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("reportType") String reportType,@PathVariable("transactionId") String transactionId,  @PathVariable("fileName") String fileName) {
        try {
        	String filePath=null;
        	if(reportType.equals("Kronos")) {
        		filePath= "D:/wfd_cwfm/Bill/KronosReports/" + transactionId + "/" + fileName;
        	}else {
        		filePath= "D:/wfd_cwfm/Bill/StatutoryReports/" + transactionId + "/" + fileName;
        	}
        	
        	
        	
            File file = new File(filePath);
            Resource resource = new FileSystemResource(file);

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/approveRejectBill")
    public ResponseEntity<String> approveRejectBill(@RequestBody ApproveRejectBillDto dto,HttpServletRequest request,HttpServletResponse response) {
    	String result=null; 
    	
         try {
        	 result = billService.approveRejectBill(dto);
         	if(null!=result) {
         		return new ResponseEntity<>(result,HttpStatus.OK);
         	}
         	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                  .body("Error saving data: " + e.getMessage());
         } 
    }

}
