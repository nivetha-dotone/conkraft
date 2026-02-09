package com.wfd.dot1.cwfm.dto;

import java.util.List;

public class GatePassListingDto {
	private String transactionId;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	private String gatePassId;
	private String firstName;
	private String lastName;
	private String gender;
	private String dateOfBirth;
	private String aadhaarNumber;
	private String contractorName;
	private String vendorCode;
	private String unitName;
	private String gatePassType;
	private String status;
	private List<String> approvedBy;
	private List<String> pendingWith;
	
	public List<String> getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(List<String> approvedBy) {
		this.approvedBy = approvedBy;
	}
	public List<String> getPendingWith() {
		return pendingWith;
	}
	public void setPendingWith(List<String> pendingWith) {
		this.pendingWith = pendingWith;
	}
	public String getGatePassType() {
		return gatePassType;
	}
	public void setGatePassType(String gatePassType) {
		this.gatePassType = gatePassType;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAadhaarNumber() {
		return aadhaarNumber;
	}
	public void setAadhaarNumber(String aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}
	public String getContractorName() {
		return contractorName;
	}
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
private String onboardingType;
public String getOnboardingType() {
	return onboardingType;
}
public void setOnboardingType(String onboardingType) {
	this.onboardingType = onboardingType;
}
}
