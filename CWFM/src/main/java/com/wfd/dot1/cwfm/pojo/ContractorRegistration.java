package com.wfd.dot1.cwfm.pojo;

import java.util.List;

public class ContractorRegistration{
    private String contractorregId;
	private String principalEmployer;
	private String vendorCode;
	private String contractorId;
	private String contractorName;
	private String managerName;
	private String locofWork;
	private String totalStrength;
	
	private String rcMaxEmp;
	private String pfNum;
	private String natureOfWork;
	private String contractFrom;
	private String contractTo;
	private String contractType;
	private String mainContractor;
	private String status;
	private String requestType;
	private String rcVerified;
	private String createdBy;
	
	private String email;
	private String mobile;
	private String aadhar;
	private String aadharDoc;
	private String pan;
	private String panDoc;
	private String gst;
	private String address;
	private String pfApplyDate;
	private List<String> availableWos;
	private List<String> selectedWos;
	
	private List<String> availableLicenses;
	private List<String> selectedLicenses;
	private String pfDoc;
	
	public String getPfDoc() {
		return pfDoc;
	}
	public void setPfDoc(String pfDoc) {
		this.pfDoc = pfDoc;
	}
	public List<String> getAvailableLicenses() {
		return availableLicenses;
	}
	public void setAvailableLicenses(List<String> availableLicenses) {
		this.availableLicenses = availableLicenses;
	}
	public List<String> getSelectedLicenses() {
		return selectedLicenses;
	}
	public void setSelectedLicenses(List<String> selectedLicenses) {
		this.selectedLicenses = selectedLicenses;
	}
	public List<String> getSelectedWos() {
		return selectedWos;
	}
	public void setSelectedWos(List<String> selectedWos) {
		this.selectedWos = selectedWos;
	}
	public List<String> getAvailableWos() {
		return availableWos;
	}
	public void setAvailableWos(List<String> availableWos) {
		this.availableWos = availableWos;
	}
	public String getAadhar() {
		return aadhar;
	}
	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}
	public String getAadharDoc() {
		return aadharDoc;
	}
	public void setAadharDoc(String aadharDoc) {
		this.aadharDoc = aadharDoc;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getPanDoc() {
		return panDoc;
	}
	public void setPanDoc(String panDoc) {
		this.panDoc = panDoc;
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
	private List<ContractorRegistrationPolicy> regPolicy;
	
	public List<ContractorRegistrationPolicy> getRegPolicy() {
		return regPolicy;
	}
	public void setRegPolicy(List<ContractorRegistrationPolicy> regPolicy) {
		this.regPolicy = regPolicy;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getContractorName() {
		return contractorName;
	}
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getContractorregId() {
		return contractorregId;
	}
	public void setContractorregId(String contractorregId) {
		this.contractorregId = contractorregId;
	}
	public String getPrincipalEmployer() {
		return principalEmployer;
	}
	public void setPrincipalEmployer(String principalEmployer) {
		this.principalEmployer = principalEmployer;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
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
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	
	public String getContractTo() {
		return contractTo;
	}
	public void setContractTo(String contractTo) {
		this.contractTo = contractTo;
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
	public String getContractorId() {
		return contractorId;
	}
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	private String services;


	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}

private String esicRegNo;


public String getEsicRegNo() {
	return esicRegNo;
}
public void setEsicRegNo(String esicRegNo) {
	this.esicRegNo = esicRegNo;
}
	
	
	private String statusValue;


	public String getStatusValue() {
		return statusValue;
	}
	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}
	private String actionId;


	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	
	private Long unitId;

	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	
	private String moduleId;

	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
}
