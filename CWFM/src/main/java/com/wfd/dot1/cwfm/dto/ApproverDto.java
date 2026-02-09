package com.wfd.dot1.cwfm.dto;

public class ApproverDto {
	 private String actionId;
	 private String actionName;
	    private String roleId;
	    private String roleName;
	    private Integer hierarchy;
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
		public String getRoleId() {
			return roleId;
		}
		public void setRoleId(String roleId) {
			this.roleId = roleId;
		}
		public String getRoleName() {
			return roleName;
		}
		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}
		public Integer getHierarchy() {
			return hierarchy;
		}
		public void setHierarchy(Integer hierarchy) {
			this.hierarchy = hierarchy;
		}
	    
}
