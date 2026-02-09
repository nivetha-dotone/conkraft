package com.wfd.dot1.cwfm.dto;

public class ApproveRejectContRenewDto {


		private String approverId;
		private String approverRole;
		public String getApproverRole() {
			return approverRole;
		}
		public void setApproverRole(String approverRole) {
			this.approverRole = approverRole;
		}
		private String comments;
		private String status;
		public String getApproverId() {
			return approverId;
		}
		public void setApproverId(String approverId) {
			this.approverId = approverId;
		}
		public String getComments() {
			return comments;
		}
		public void setComments(String comments) {
			this.comments = comments;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
		
		
		private String roleId;
		public String getRoleId() {
			return roleId;
		}
		public void setRoleId(String roleId) {
			this.roleId = roleId;
		}
		
		private String transactionId;
		public String getTransactionId() {
			return transactionId;
		}
		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}
		
		private String unitId;
		public String getUnitId() {
			return unitId;
		}
		public void setUnitId(String unitId) {
			this.unitId = unitId;
		}
		
	}


