package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.dto.WorkflowRequestDto;
import com.wfd.dot1.cwfm.dto.WorkflowResponseDto;
import com.wfd.dot1.cwfm.pojo.BusinessType;

public interface WorkflowDao {
	public List<BusinessType> getAllBusinessType(String unitId);

	WorkflowResponseDto fetchWorkflow(String unitId, String businessType, String moduleId,String actionName);

	void saveWorkflow(WorkflowRequestDto request);
}
