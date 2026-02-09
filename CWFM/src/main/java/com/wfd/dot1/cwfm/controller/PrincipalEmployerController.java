package com.wfd.dot1.cwfm.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.MediaType;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.dao.PrincipalEmployerDao;
import com.wfd.dot1.cwfm.pojo.CMSRoleRights;
import com.wfd.dot1.cwfm.pojo.KronosReport;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployerDocument;
import com.wfd.dot1.cwfm.service.CommonService;
import com.wfd.dot1.cwfm.service.PeDocConfigService;
import com.wfd.dot1.cwfm.service.PrincipalEmployerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

@Controller
@RequestMapping("/principalEmployer")
public class PrincipalEmployerController {
	
	@Autowired
	PrincipalEmployerService peService;
	
	@Autowired
    private PeDocConfigService peDocService;
	
	@Autowired
	PrincipalEmployerDao peDao;
	
	@Autowired
	CommonService commonService;
	@GetMapping("/list")
    public String getAllPrincipalEmployer(HttpServletRequest request,HttpServletResponse response) {
    	 HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
         MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
         List<PrincipalEmployer> listDto =new ArrayList<PrincipalEmployer>();
        
         CMSRoleRights rr =new CMSRoleRights();
         if(user!=null) {
         if(user.getRoleName().equals("System Admin")) {
        	
        	 listDto = peService.getAllPrincipalEmployerForAdmin();
         }else {
        	
        	 listDto = peService.getAllPrincipalEmployer(user.getUserAccount());
         }
         }
    		request.setAttribute("cmSPRINCIPALEMPLOYERs", listDto);
    		rr = commonService.hasPageActionPermissionForRole(user.getRoleId(), "/principalEmployer/list");
    		request.setAttribute("UserPermission", rr);
    	 
    	return "principalEmployer/list";
    }
	
	@GetMapping("/view/{id}")
	public String getIndividualPEDetailByUnitId(@PathVariable("id") String id,
	                                            HttpServletRequest request,
	                                            HttpServletResponse response,
	                                            Model model) {
	    // 1️⃣ Fetch PE details
	    PrincipalEmployer principalEmployer = peService.getIndividualPEDetailByUnitId(id);
	    request.setAttribute("principalEmployer", principalEmployer);

	    // 2️⃣ Get documents grouped by type
	    Map<String, List<PrincipalEmployerDocument>> docsByType = peService.getDocumentsGroupedByType(id);

	    // 3️⃣ Encode document IDs for masking
	    for (List<PrincipalEmployerDocument> docList : docsByType.values()) {
	        for (PrincipalEmployerDocument doc : docList) {
	            if (doc.getId() != 0) {
	                // Encode doc.id → Base64
	                String encodedId = Base64.getUrlEncoder()
	                        .encodeToString(String.valueOf(doc.getId()).getBytes(StandardCharsets.UTF_8));
	                // Set a new field for JSP usage
	                doc.setEncodedId(encodedId);
	            }
	        }
	    }

	    // 4️⃣ Get all document types
	    List<KronosReport> docTypes = peService.getAllDocTypes();

	    // 5️⃣ Add to model
	    model.addAttribute("docTypes", docTypes);
	    model.addAttribute("docsByType", docsByType);

	    return "principalEmployer/view";
	}

	@GetMapping("/edit/{id}")
    public String editIndividualPEDetailByUnitId(@PathVariable("id") String id,HttpServletRequest request,HttpServletResponse response,Model model) {
		PrincipalEmployer principalEmployer = peService.getIndividualPEDetailByUnitId(id);
        request.setAttribute("principalEmployer", principalEmployer);
        List<String>  pedocs= peDocService.getAllPeDocuments();
		request.setAttribute("reportName", pedocs);
		Map<String, List<PrincipalEmployerDocument>> docsByType = peService.getDocumentsGroupedByType(id);
        List<KronosReport> docTypes = peService.getAllDocTypes();

        //model.addAttribute("employerId", id);
        model.addAttribute("docTypes", docTypes);
        model.addAttribute("docsByType", docsByType);
        return "principalEmployer/peedit";
    }

	@PostMapping("/saveAll")
    public ResponseEntity<String> saveAllDocuments(@RequestParam("jsonData") String jsonData,
                                                   MultipartHttpServletRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonData);

            int employerId = root.get("employerId").asInt();
            HttpSession session = request.getSession(false); // Use `false` to avoid creating a new session
    		MasterUser user = (MasterUser) (session != null ? session.getAttribute("loginuser") : null);
    		String uploadedBy = user.getUserAccount(); 
            //String uploadedBy = root.get("uploadedBy").asText();

            Iterator<String> fileNames = request.getFileNames();

            while (fileNames.hasNext()) {
                String inputName = fileNames.next(); // e.g., files_License
                String docType = inputName.replace("files_", "");
                List<MultipartFile> files = request.getFiles(inputName);

                for (MultipartFile file : files) {
                    if (!file.isEmpty()) {
                    	peService.saveDocument(employerId, docType, file, uploadedBy);
                    }
                }
            }

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }
	 

	//@GetMapping("/download/{docId}")
	//public void downloadFile(@PathVariable("docId") int docId, HttpServletResponse response) {
	   // try {
	     //   PrincipalEmployerDocument doc = peDao.getDocumentById(docId);
	     //   if (doc == null || doc.getFilePath() == null) {
	      //      response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
	     //       return;
	    //    }

	      //  File file = new File(doc.getFilePath());
	      //  if (!file.exists()) {
	      //      response.sendError(HttpServletResponse.SC_NOT_FOUND, "File does not exist");
	      //      return;
	      //  }

	        // Detect content type
	       // String contentType = Files.probeContentType(file.toPath());
	       // if (contentType == null) {
	       //     contentType = "application/octet-stream"; // fallback
	       // }

	      //  response.setContentType(contentType);

	        // Open inline in browser (do not set attachment)
	     //   response.setHeader("Content-Disposition", "inline; filename=\"" + doc.getFileName() + "\"");

	    //    Files.copy(file.toPath(), response.getOutputStream());
	    //    response.getOutputStream().flush();
	   // } catch (Exception e) {
	    //    e.printStackTrace();
	   //     try {
	   //         response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error opening file");
	   //     } catch (IOException ignored) {}
	 //   }
	//}


	    // ✅ Download masked link: /principalEmployer/download/{encodedId}
	@GetMapping("/download/{encodedId}")
	public void downloadFile(@PathVariable("encodedId") String encodedId, HttpServletResponse response) {
	    try {
	        // 🔒 Decode Base64 to get original docId
	        String decoded = new String(Base64.getUrlDecoder().decode(encodedId), StandardCharsets.UTF_8);
	        int docId = Integer.parseInt(decoded);

	        // Fetch document details securely
	        PrincipalEmployerDocument doc = peDao.getDocumentById(docId);
	        if (doc == null || doc.getFilePath() == null) {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
	            return;
	        }

	        File file = new File(doc.getFilePath());
	        if (!file.exists()) {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File does not exist");
	            return;
	        }

	        // Detect file type
	        String contentType = Files.probeContentType(file.toPath());
	        if (contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        // Serve inline (viewable in browser)
	        response.setContentType(contentType);
	        response.setHeader("Content-Disposition", "inline; filename=\"" + doc.getFileName() + "\"");

	        Files.copy(file.toPath(), response.getOutputStream());
	        response.getOutputStream().flush();

	    } catch (IllegalArgumentException e) {
	        // Invalid Base64 string
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    } catch (Exception e) {
	        e.printStackTrace();
	        try {
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error opening file");
	        } catch (IOException ignored) {}
	    }
	}

	}
