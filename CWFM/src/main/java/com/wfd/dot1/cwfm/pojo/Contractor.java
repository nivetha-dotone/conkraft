package com.wfd.dot1.cwfm.pojo;

public class Contractor {

	private String contractorId;
	
	private String contractorName;
	
	private String unitId;
	
	private String contractorCode;
	
	private String contractorAddress;
	
	private String principalEmployer;
	
    private boolean isBlocked;
	   
    private String city;
	   
	private String reference;
	   
	private long mobileNumber;
	
	private String emailaddress;
	
	private String managerAddress;
	   
	
	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getManagerAddress() {
		return managerAddress;
	}

	public void setManagerAddress(String managerAddress) {
		this.managerAddress = managerAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	

	public Contractor(String contractorName, String contractorAddress, String contractorCode, boolean isBlocked){
		// TODO Auto-generated constructor stub
		this.contractorName = contractorName;
        this.contractorAddress = contractorAddress;
        this.contractorCode = contractorCode;
        this.isBlocked = isBlocked;
	}

	public Contractor() {
		// TODO Auto-generated constructor stub
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public String getPrincipalEmployer() {
		return principalEmployer;
	}

	public void setPrincipalEmployer(String principalEmployer) {
		this.principalEmployer = principalEmployer;
	}

	public String getContractorCode() {
		return contractorCode;
	}

	public void setContractorCode(String contractorCode) {
		this.contractorCode = contractorCode;
	}

	public String getContractorAddress() {
		return contractorAddress;
	}

	public void setContractorAddress(String contractorAddress) {
		this.contractorAddress = contractorAddress;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	

	private String managerName;
	private String esiwc;
	private String licenseNumber;
	private String validFrom;
	private String validTo;
	private int coverage;
	private int totalStrength;
	private int maxNoEmp;
	private String natureOfWork;
	private String locationOfWork;
	private String periodStartDate;
	private String periodEndDate;
	private String vendorCode;
	private String pfCode;
	private String esiValidFrom;
	private String esiValidTo;
	private int escWcCoverage;
	private String pfNum;
	private String pfApplyDate;

	private String rcValidated;
	private String llValidated;
	private String wcValidated;

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getEsiwc() {
		return esiwc;
	}

	public void setEsiwc(String esiwc) {
		this.esiwc = esiwc;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
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

	public int getCoverage() {
		return coverage;
	}

	public void setCoverage(int coverage) {
		this.coverage = coverage;
	}

	public int getTotalStrength() {
		return totalStrength;
	}

	public void setTotalStrength(int totalStrength) {
		this.totalStrength = totalStrength;
	}

	public int getMaxNoEmp() {
		return maxNoEmp;
	}

	public void setMaxNoEmp(int maxNoEmp) {
		this.maxNoEmp = maxNoEmp;
	}

	public String getNatureOfWork() {
		return natureOfWork;
	}

	public void setNatureOfWork(String natureOfWork) {
		this.natureOfWork = natureOfWork;
	}

	public String getLocationOfWork() {
		return locationOfWork;
	}

	public void setLocationOfWork(String locationOfWork) {
		this.locationOfWork = locationOfWork;
	}

	public String getPeriodStartDate() {
		return periodStartDate;
	}

	public void setPeriodStartDate(String periodStartDate) {
		this.periodStartDate = periodStartDate;
	}

	public String getPeriodEndDate() {
		return periodEndDate;
	}

	public void setPeriodEndDate(String periodEndDate) {
		this.periodEndDate = periodEndDate;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getPfCode() {
		return pfCode;
	}

	public void setPfCode(String pfCode) {
		this.pfCode = pfCode;
	}

	public String getEsiValidFrom() {
		return esiValidFrom;
	}

	public void setEsiValidFrom(String esiValidFrom) {
		this.esiValidFrom = esiValidFrom;
	}

	public String getEsiValidTo() {
		return esiValidTo;
	}

	public void setEsiValidTo(String esiValidTo) {
		this.esiValidTo = esiValidTo;
	}

	public int getEscWcCoverage() {
		return escWcCoverage;
	}

	public void setEscWcCoverage(int escWcCoverage) {
		this.escWcCoverage = escWcCoverage;
	}

	public String getPfNum() {
		return pfNum;
	}

	public void setPfNum(String pfNum) {
		this.pfNum = pfNum;
	}

	public String getPfApplyDate() {
		return pfApplyDate;
	}

	public void setPfApplyDate(String pfApplyDate) {
		this.pfApplyDate = pfApplyDate;
	}

	public String getRcValidated() {
		return rcValidated;
	}

	public void setRcValidated(String rcValidated) {
		this.rcValidated = rcValidated;
	}

	public String getLlValidated() {
		return llValidated;
	}

	public void setLlValidated(String llValidated) {
		this.llValidated = llValidated;
	}

	public String getWcValidated() {
		return wcValidated;
	}

	public void setWcValidated(String wcValidated) {
		this.wcValidated = wcValidated;
	}
	


	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
