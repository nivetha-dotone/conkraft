package com.wfd.dot1.cwfm.pojo;

public class PrincipalEmployer {

	private int unitId;
	
	private String name;
	
	private String address;
	
	private String managerName;
	
	private String managerAddrs;
	
	private String businessType;
	
	private int maxWorkmen;
	
	private int maxCntrWorkmen;
	
	private int bocwApplicability;
	
	private int isMwApplicability;
	
	private String code;
	
	private String organization;
	
	private String pfCode;
	
	private String licenseNumber;
	
	private String wcNumber;
	
	private String esicNumber;
	
	private String ptRegNo;
	
	private String lwfRegNo;
	
	private String factoryLicenseNumber;
	
	private String isRcApplicable;
	
	private String rcNumber;
	
	private String attachmentNm;
	
	private int stateId;
	
	private int isActive;
	
	private String updatedBy;
	
	private String updatedTM;
	
	private String stateNM;

	public String getStateNM() {
		return stateNM;
	}

	public void setStateNM(String stateNM) {
		this.stateNM = stateNM;
	}

	public PrincipalEmployer( String organization, String code,String name, String address, String managerName, String managerAddrs, String businessType, int maxWorkmen,
			int maxCntrWorkmen, int bocwApplicability, int isMwApplicability,String licenseNumber, String pfCode,
			String wcNumber, String factoryLicenseNumber) {
		
		this.organization=organization;
		this.code=code;
		this.name=name;
		this.address=address;
		this.managerName=managerName;
		this.managerAddrs=managerAddrs;
		this.businessType=businessType;
		this.maxWorkmen=maxWorkmen;
		this.maxCntrWorkmen=maxCntrWorkmen;
		this.bocwApplicability=bocwApplicability;
		this.isMwApplicability=isMwApplicability;
		this.pfCode=pfCode;
		this.licenseNumber=licenseNumber;
		this.wcNumber=wcNumber;
		this.factoryLicenseNumber=factoryLicenseNumber;
		//this.stateNM=stateNM;
		
		
	}

	public PrincipalEmployer() {
		// TODO Auto-generated constructor stub
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerAddrs() {
		return managerAddrs;
	}

	public void setManagerAddrs(String managerAddrs) {
		this.managerAddrs = managerAddrs;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public int getMaxWorkmen() {
		return maxWorkmen;
	}

	public void setMaxWorkmen(int maxWorkmen) {
		this.maxWorkmen = maxWorkmen;
	}

	public int getMaxCntrWorkmen() {
		return maxCntrWorkmen;
	}

	public void setMaxCntrWorkmen(int maxCntrWorkmen) {
		this.maxCntrWorkmen = maxCntrWorkmen;
	}

	public int getBocwApplicability() {
		return bocwApplicability;
	}

	public void setBocwApplicability(int bocwApplicability) {
		this.bocwApplicability = bocwApplicability;
	}

	public int getIsMwApplicability() {
		return isMwApplicability;
	}

	public void setIsMwApplicability(int isMwApplicability) {
		this.isMwApplicability = isMwApplicability;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getPfCode() {
		return pfCode;
	}

	public void setPfCode(String pfCode) {
		this.pfCode = pfCode;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getWcNumber() {
		return wcNumber;
	}

	public void setWcNumber(String wcNumber) {
		this.wcNumber = wcNumber;
	}

	public String getEsicNumber() {
		return esicNumber;
	}

	public void setEsicNumber(String esicNumber) {
		this.esicNumber = esicNumber;
	}

	public String getPtRegNo() {
		return ptRegNo;
	}

	public void setPtRegNo(String ptRegNo) {
		this.ptRegNo = ptRegNo;
	}

	public String getLwfRegNo() {
		return lwfRegNo;
	}

	public void setLwfRegNo(String lwfRegNo) {
		this.lwfRegNo = lwfRegNo;
	}

	public String getFactoryLicenseNumber() {
		return factoryLicenseNumber;
	}

	public void setFactoryLicenseNumber(String factoryLicenseNumber) {
		this.factoryLicenseNumber = factoryLicenseNumber;
	}

	public String getIsRcApplicable() {
		return isRcApplicable;
	}

	public void setIsRcApplicable(String isRcApplicable) {
		this.isRcApplicable = isRcApplicable;
	}

	public String getRcNumber() {
		return rcNumber;
	}

	public void setRcNumber(String rcNumber) {
		this.rcNumber = rcNumber;
	}

	public String getAttachmentNm() {
		return attachmentNm;
	}

	public void setAttachmentNm(String attachmentNm) {
		this.attachmentNm = attachmentNm;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedTM() {
		return updatedTM;
	}

	public void setUpdatedTM(String updatedTM) {
		this.updatedTM = updatedTM;
	}
private String id;
private String description;

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}


	
}
