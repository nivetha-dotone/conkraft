package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.WorkflowDao;
import com.wfd.dot1.cwfm.dto.WorkflowRequestDto;
import com.wfd.dot1.cwfm.dto.WorkflowResponseDto;
import com.wfd.dot1.cwfm.pojo.BusinessType;
@Service
public class WorkflowServiceImpl implements WorkflowService{

	
	@Autowired
	WorkflowDao workflowDao;
	@Override
	public List<BusinessType> getAllBusinessType(String unitId) {
		return workflowDao.getAllBusinessType(unitId);
	}
	@Override
	public WorkflowResponseDto fetchWorkflow(String unitId, String businessType, String moduleId,String actionName) {
		
		
		return workflowDao.fetchWorkflow(unitId, businessType, moduleId,actionName);
	}
	@Override
	public void saveWorkflow(WorkflowRequestDto request) {
		workflowDao.saveWorkflow(request);
	}

}
