package com.wfd.dot1.cwfm.service;

import org.springframework.web.multipart.MultipartFile;

import com.wfd.dot1.cwfm.dto.MinimumWageDTO;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface FileUploadService {
    void uploadFiles(List<MultipartFile> files);
   
    Map<String, Object> processTemplateFile(MultipartFile file, String templateType ,String createdBy) throws Exception;
    
    static void saveMinimumWageTemplate(MinimumWageDTO dto) {
		// TODO Auto-generated method stub		
	}

	String getTemplateCSV(String templateType);
    
    }

