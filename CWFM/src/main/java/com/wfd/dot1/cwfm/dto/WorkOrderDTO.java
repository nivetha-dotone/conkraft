package com.wfd.dot1.cwfm.dto;

public class WorkOrderDTO {
    private long woId;
    private String woNumber;
    private String contractorName;
    private int workmenCount;
    private String gatepassId;
    private String aadharNumber;
    private String fullname;
    private String contractorId;
    private Integer workOrderCount;
    private String peCode;
    private String contCode;
    private String validFrom;
    private String validTo;
    
	public String getPeCode() {
		return peCode;
	}
	public void setPeCode(String peCode) {
		this.peCode = peCode;
	}
	public String getContCode() {
		return contCode;
	}
	public void setContCode(String contCode) {
		this.contCode = contCode;
	}
	public String getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	public String getValidTo() {
		return validTo;
	}
	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}
	public Integer getWorkOrderCount() {
		return workOrderCount;
	}
	public void setWorkOrderCount(Integer workOrderCount) {
		this.workOrderCount = workOrderCount;
	}
	public String getContractorId() {
		return contractorId;
	}
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
	public String getGatepassId() {
		return gatepassId;
	}
	public void setGatepassId(String gatepassId) {
		this.gatepassId = gatepassId;
	}
	public String getAadharNumber() {
		return aadharNumber;
	}
	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public long getWoId() {
		return woId;
	}
	public void setWoId(long woId) {
		this.woId = woId;
	}
	public String getWoNumber() {
		return woNumber;
	}
	public void setWoNumber(String woNumber) {
		this.woNumber = woNumber;
	}
	public String getContractorName() {
		return contractorName;
	}
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}
	public int getWorkmenCount() {
		return workmenCount;
	}
	public void setWorkmenCount(int workmenCount) {
		this.workmenCount = workmenCount;
	}
	private String plantName;

	public String getPlantName() {
	    return plantName;
	}

	public void setPlantName(String plantName) {
	    this.plantName = plantName;
	}
}
