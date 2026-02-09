package com.wfd.dot1.cwfm.dto;

import java.util.List;

public class WorkflowResponseDto {
	    private Integer workflowType;
	    private List<ApproverDto> hierarchy;
		public Integer getWorkflowType() {
			return workflowType;
		}
		public void setWorkflowType(Integer workflowType) {
			this.workflowType = workflowType;
		}
		public List<ApproverDto> getHierarchy() {
			return hierarchy;
		}
		public void setHierarchy(List<ApproverDto> hierarchy) {
			this.hierarchy = hierarchy;
		}

	    

}
