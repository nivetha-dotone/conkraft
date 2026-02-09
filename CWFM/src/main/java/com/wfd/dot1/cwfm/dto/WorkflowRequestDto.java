package com.wfd.dot1.cwfm.dto;

import java.util.List;

public class WorkflowRequestDto {
	 private String unitId;
	    private String businessType;
	    private String moduleId;
	    private Integer workflowType;
	    private List<ApproverDto> hierarchy;
	    private String actionName;
	    private String actionId;
		public String getActionId() {
			return actionId;
		}
		public void setActionId(String actionId) {
			this.actionId = actionId;
		}
		public String getActionName() {
			return actionName;
		}
		public void setActionName(String actionName) {
			this.actionName = actionName;
		}
		public String getUnitId() {
			return unitId;
		}
		public void setUnitId(String unitId) {
			this.unitId = unitId;
		}
		public String getBusinessType() {
			return businessType;
		}
		public void setBusinessType(String businessType) {
			this.businessType = businessType;
		}
		public String getModuleId() {
			return moduleId;
		}
		public void setModuleId(String moduleId) {
			this.moduleId = moduleId;
		}
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
	    private String userId;
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
}
