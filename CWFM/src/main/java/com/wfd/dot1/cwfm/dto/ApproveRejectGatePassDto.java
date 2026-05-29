package com.wfd.dot1.cwfm.dto;

import java.util.List;

public class ApproveRejectGatePassDto {

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
	private String gatePassId;
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
	public String getGatePassId() {
		return gatePassId;
	}
	public void setGatePassId(String gatePassId) {
		this.gatePassId = gatePassId;
	}
	
	private String gatePassType;
	public String getGatePassType() {
		return gatePassType;
	}
	public void setGatePassType(String gatePassType) {
		this.gatePassType = gatePassType;
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
	private String onboardingDocType;

	public String getOnboardingDocType() {
		return onboardingDocType;
	}
	public void setOnboardingDocType(String onboardingDocType) {
		this.onboardingDocType = onboardingDocType;
	}
	private String trainingType;
	private String trainingName;
	private String trainingFromDate;
	private String trainingToDate;
	private String fromTime;
	private String toTime;
	private String faculty;
	private String marks;
	private String efficency;
	private String nextTrainingDate;
	private String remarks;

	public String getTrainingFromDate() {
		return trainingFromDate;
	}
	public void setTrainingFromDate(String trainingFromDate) {
		this.trainingFromDate = trainingFromDate;
	}
	public String getTrainingToDate() {
		return trainingToDate;
	}
	public void setTrainingToDate(String trainingToDate) {
		this.trainingToDate = trainingToDate;
	}
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public String getFaculty() {
		return faculty;
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public String getEfficency() {
		return efficency;
	}
	public void setEfficency(String efficency) {
		this.efficency = efficency;
	}
	public String getNextTrainingDate() {
		return nextTrainingDate;
	}
	public void setNextTrainingDate(String nextTrainingDate) {
		this.nextTrainingDate = nextTrainingDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getTrainingType() {
		return trainingType;
	}
	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
	}
	public String getTrainingName() {
		return trainingName;
	}
	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}
	private List<ApproveRejectGatePassDto> trainingDetailsList;
	
	public List<ApproveRejectGatePassDto> getTrainingDetailsList() {
	    return trainingDetailsList;
	}

	public void setTrainingDetailsList(List<ApproveRejectGatePassDto> trainingDetailsList) {
	    this.trainingDetailsList = trainingDetailsList;
	}
}
