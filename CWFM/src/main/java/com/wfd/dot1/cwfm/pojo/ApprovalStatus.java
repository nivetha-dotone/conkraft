package com.wfd.dot1.cwfm.pojo;

public class ApprovalStatus {
private String gatePassApprovalStatusId;
private String gatePassId;
private String userId;
private String userRole;
private int status;
private String comments;
private String lastUpdatedDate;
private int gatePassTypeId;
public String getGatePassApprovalStatusId() {
	return gatePassApprovalStatusId;
}
public void setGatePassApprovalStatusId(String gatePassApprovalStatusId) {
	this.gatePassApprovalStatusId = gatePassApprovalStatusId;
}
public String getGatePassId() {
	return gatePassId;
}
public void setGatePassId(String gatePassId) {
	this.gatePassId = gatePassId;
}
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getUserRole() {
	return userRole;
}
public void setUserRole(String userRole) {
	this.userRole = userRole;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public String getComments() {
	return comments;
}
public void setComments(String comments) {
	this.comments = comments;
}
public String getLastUpdatedDate() {
	return lastUpdatedDate;
}
public void setLastUpdatedDate(String lastUpdatedDate) {
	this.lastUpdatedDate = lastUpdatedDate;
}
public int getGatePassTypeId() {
	return gatePassTypeId;
}
public void setGatePassTypeId(int gatePassTypeId) {
	this.gatePassTypeId = gatePassTypeId;
}

private String transactionId;
public String getTransactionId() {
	return transactionId;
}
public void setTransactionId(String transactionId) {
	this.transactionId = transactionId;
}
}
