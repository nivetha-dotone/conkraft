package com.wfd.dot1.cwfm.controller;

import java.util.List;

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

import com.wfd.dot1.cwfm.service.PeDocConfigService;
@Controller
@RequestMapping("/pedocsetup")
public class PeDocumentsSetupController {
	@Autowired
    private PeDocConfigService peDocService;
	
	@GetMapping("/list")
    public String showConfigPage(Model model) {
        model.addAttribute("Documents", peDocService.getAllPeDocuments());
        return "peDoc/setup";
    }
	
	 @PostMapping("/save")
	    @ResponseBody
	    public ResponseEntity<String> saveDocuments(@RequestParam("peDoc") List<String> documentNames) {
	        try {
	            peDocService.saveDocuments(documentNames);
	            return ResponseEntity.ok("Documents saved successfully!");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                                 .body("Error while saving documents.");
	        }
	    }
}
