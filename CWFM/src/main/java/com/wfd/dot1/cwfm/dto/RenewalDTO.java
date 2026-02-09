package com.wfd.dot1.cwfm.dto;

import java.util.List;

public class RenewalDTO {
    private Long contractorregId;
    private Integer contractorId;
    private String principalEmployer;
    private String contractorName;
    private String email;
    private String mobile;
    private String aadhar;
    private String pan;
    private String gst;
    private String address;
    private String pfApplyDate;
    private String managerName;
    private String locofWork;
    private String totalStrength;
    private String rcMaxEmp;
    private String pfNum;
    private String natureOfWork;
    private String contractFrom;
    private String contractTo;
    private String contractType;
    private String rcVerified;
    private String mainContractor;
    private String requestType;
private String vendorCode;
    public String getVendorCode() {
	return vendorCode;
}
public void setVendorCode(String vendorCode) {
	this.vendorCode = vendorCode;
}
	private List<RenewalDocumentDTO> renewalDocuments;
    private List<String> selectedWorkOrders;
	public Long getContractorregId() {
		return contractorregId;
	}
	public void setContractorregId(Long contractorregId) {
		this.contractorregId = contractorregId;
	}
	public Integer getContractorId() {
		return contractorId;
	}
	public void setContractorId(Integer contractorId) {
		this.contractorId = contractorId;
	}
	public String getPrincipalEmployer() {
		return principalEmployer;
	}
	public void setPrincipalEmployer(String principalEmployer) {
		this.principalEmployer = principalEmployer;
	}
	public String getContractorName() {
		return contractorName;
	}
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAadhar() {
		return aadhar;
	}
	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getGst() {
		return gst;
	}
	public void setGst(String gst) {
		this.gst = gst;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPfApplyDate() {
		return pfApplyDate;
	}
	public void setPfApplyDate(String pfApplyDate) {
		this.pfApplyDate = pfApplyDate;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getLocofWork() {
		return locofWork;
	}
	public void setLocofWork(String locofWork) {
		this.locofWork = locofWork;
	}
	public String getTotalStrength() {
		return totalStrength;
	}
	public void setTotalStrength(String totalStrength) {
		this.totalStrength = totalStrength;
	}
	public String getRcMaxEmp() {
		return rcMaxEmp;
	}
	public void setRcMaxEmp(String rcMaxEmp) {
		this.rcMaxEmp = rcMaxEmp;
	}
	public String getPfNum() {
		return pfNum;
	}
	public void setPfNum(String pfNum) {
		this.pfNum = pfNum;
	}
	public String getNatureOfWork() {
		return natureOfWork;
	}
	public void setNatureOfWork(String natureOfWork) {
		this.natureOfWork = natureOfWork;
	}
	public String getContractFrom() {
		return contractFrom;
	}
	public void setContractFrom(String contractFrom) {
		this.contractFrom = contractFrom;
	}
	public String getContractTo() {
		return contractTo;
	}
	public void setContractTo(String contractTo) {
		this.contractTo = contractTo;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public String getRcVerified() {
		return rcVerified;
	}
	public void setRcVerified(String rcVerified) {
		this.rcVerified = rcVerified;
	}
	public String getMainContractor() {
		return mainContractor;
	}
	public void setMainContractor(String mainContractor) {
		this.mainContractor = mainContractor;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public List<RenewalDocumentDTO> getRenewalDocuments() {
		return renewalDocuments;
	}
	public void setRenewalDocuments(List<RenewalDocumentDTO> renewalDocuments) {
		this.renewalDocuments = renewalDocuments;
	}
	public List<String> getSelectedWorkOrders() {
		return selectedWorkOrders;
	}
	public void setSelectedWorkOrders(List<String> selectedWorkOrders) {
		this.selectedWorkOrders = selectedWorkOrders;
	}
	private Long unitId;

	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
    // Getters and setters
}

