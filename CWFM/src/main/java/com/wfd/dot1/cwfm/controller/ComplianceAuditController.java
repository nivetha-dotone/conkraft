package com.wfd.dot1.cwfm.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wfd.dot1.cwfm.dto.ComplianceAuditDto;
import com.wfd.dot1.cwfm.service.ComplianceAuditService;
@Controller
@RequestMapping("/complianceAudit")
public class ComplianceAuditController {
	
	@Autowired
	private  ComplianceAuditService complianceAuditService;

	@GetMapping("/complianceAuditOnAadhar")
	public String showComplianceAuditPage(Model model) {
		return "aadharCompliance/complianceAudit";
	}

	@PostMapping("/search")
	@ResponseBody
	public ResponseEntity<?> searchComplianceAudit(@RequestParam("aadharNumber") String aadharNumber) {

		if (aadharNumber == null || !aadharNumber.matches("\\d{12}")) {

			return ResponseEntity.badRequest().body(Map.of("message", "Please enter a valid 12-digit Aadhaar number."));
		}

		try {
			List<ComplianceAuditDto> result = complianceAuditService.searchComplianceAuditByAadhar(aadharNumber);

			return ResponseEntity.ok(result != null ? result : Collections.emptyList());

		} catch (IllegalArgumentException e) {

			return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));

		} catch (Exception e) {

			// Do not log Aadhaar numbers or expose internal exceptions.
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("message", "Unable to retrieve compliance audit details. Please try again."));
		}
	}
}
