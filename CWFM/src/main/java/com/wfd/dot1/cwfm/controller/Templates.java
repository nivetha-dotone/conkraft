package com.wfd.dot1.cwfm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/template")
public class Templates {
	@GetMapping("/generalmasterView")
    public String generalMasterView() {
        // Return the JSP page name or JSON response
        return "reportsUpload/fileUpload";
    }

	/*
	 * @GetMapping("/generalmasterDownload") public ResponseEntity<Resource>
	 * generalMasterDownload() { // Path to the file String filePath =
	 * "/path/to/generalmaster.xlsx"; // Update with actual file location
	 * 
	 * Resource file = new FileSystemResource(filePath); return ResponseEntity.ok()
	 * .contentType(MediaType.APPLICATION_OCTET_STREAM)
	 * .header(HttpHeaders.CONTENT_DISPOSITION,
	 * "attachment; filename=generalmaster.xlsx") .body(file); }
	 */

    @GetMapping("/organizationlevelView")
    public String organizationLevelView() {
        return "This is the Organization Level View page.";
    }
    @GetMapping("/generalmasterDownload")
    public String generalMasterDownload() {
        return "This is the generalMasterDownload page.";
    }

	/*
	 * @GetMapping("/organizationlevelDownload") public ResponseEntity<Resource>
	 * organizationLevelDownload() { String filePath =
	 * "/path/to/organizationlevel.xlsx"; // Update with actual file location
	 * 
	 * Resource file = new FileSystemResource(filePath); return ResponseEntity.ok()
	 * .contentType(MediaType.APPLICATION_OCTET_STREAM)
	 * .header(HttpHeaders.CONTENT_DISPOSITION,
	 * "attachment; filename=organizationlevel.xlsx") .body(file); }
	 */
    @GetMapping("/organizationlevelDownload")
    public String organizationLevelDownload() {
        return "This is the organizationlevelDownload  page.";
    }

}
