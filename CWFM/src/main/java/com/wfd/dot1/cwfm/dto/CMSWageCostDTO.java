package com.wfd.dot1.cwfm.dto;

public class CMSWageCostDTO {
    private Long wcTransId;
    private String unitId;
    private String unitCode;
    private Integer contractorId;
    private String contractorCode;
    private String workOrderNumber;
    private String startDate;
    private String endDate;
    private String woValidFrom;
    private String woValidTo;
    private String billType;
    private String services;
    private String statusValue;
    public String getStatusValue() {
		return statusValue;
	}
	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}
	// Getters & Setters
	public Long getWcTransId() {
		return wcTransId;
	}
	public void setWcTransId(Long wcTransId) {
		this.wcTransId = wcTransId;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public Integer getContractorId() {
		return contractorId;
	}
	public void setContractorId(Integer contractorId) {
		this.contractorId = contractorId;
	}
	public String getContractorCode() {
		return contractorCode;
	}
	public void setContractorCode(String contractorCode) {
		this.contractorCode = contractorCode;
	}
	public String getWorkOrderNumber() {
		return workOrderNumber;
	}
	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getWoValidFrom() {
		return woValidFrom;
	}
	public void setWoValidFrom(String woValidFrom) {
		this.woValidFrom = woValidFrom;
	}
	public String getWoValidTo() {
		return woValidTo;
	}
	public void setWoValidTo(String woValidTo) {
		this.woValidTo = woValidTo;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	
	private int status;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	private String createdBy;
	private String updateBy;
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	private String comments;
	private String actionPlan;
	private String preComments;
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getActionPlan() {
		return actionPlan;
	}
	public void setActionPlan(String actionPlan) {
		this.actionPlan = actionPlan;
	}
	public String getPreComments() {
		return preComments;
	}
	public void setPreComments(String preComments) {
		this.preComments = preComments;
	}
	
	private String unitName;
	private String contractorName;
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getContractorName() {
		return contractorName;
	}
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}
	private String actionId;
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	
	private String moduleId;
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
}