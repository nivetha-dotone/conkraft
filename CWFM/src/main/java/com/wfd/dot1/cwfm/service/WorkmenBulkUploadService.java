package com.wfd.dot1.cwfm.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.pojo.WorkmenBulkUpload;


public interface WorkmenBulkUploadService {

	List<WorkmenBulkUpload> getAllWorkmenBulkUploadData();
	
	Map<String, Object> validateAndSaveSelectedWorkmen(List<Integer> transactionIds, String createdBy);

}
