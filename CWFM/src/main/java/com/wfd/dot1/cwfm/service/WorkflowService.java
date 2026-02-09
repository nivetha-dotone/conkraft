package com.wfd.dot1.cwfm.service;

import java.util.List;


import com.wfd.dot1.cwfm.dto.WorkflowRequestDto;
import com.wfd.dot1.cwfm.dto.WorkflowResponseDto;
import com.wfd.dot1.cwfm.pojo.BusinessType;

public interface WorkflowService {
	public List<BusinessType> getAllBusinessType(String unitId);
	
	public WorkflowResponseDto fetchWorkflow(String unitId, String businessType, String moduleId,String actionName);

	public void saveWorkflow(WorkflowRequestDto request);
}
