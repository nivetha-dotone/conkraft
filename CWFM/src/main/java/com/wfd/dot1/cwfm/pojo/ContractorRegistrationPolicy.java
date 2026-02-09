package com.wfd.dot1.cwfm.pojo;

public class ContractorRegistrationPolicy {
	
	private String contractorRegPolicyId;
	private String contractorRegId;
	private String contractorId;
	private String unitId;
	private String woNumber;
	private String documentType;
	private String documentNumber;
	private String natureOfJob;
	private int coverage;
	private String validFrom;
	private String validTo;
	private String fileName;
	public String getContractorRegPolicyId() {
		return contractorRegPolicyId;
	}
	public void setContractorRegPolicyId(String contractorRegPolicyId) {
		this.contractorRegPolicyId = contractorRegPolicyId;
	}
	public String getContractorRegId() {
		return contractorRegId;
	}
	public void setContractorRegId(String contractorRegId) {
		this.contractorRegId = contractorRegId;
	}
	public String getContractorId() {
		return contractorId;
	}
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getWoNumber() {
		return woNumber;
	}
	public void setWoNumber(String woNumber) {
		this.woNumber = woNumber;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getNatureOfJob() {
		return natureOfJob;
	}
	public void setNatureOfJob(String natureOfJob) {
		this.natureOfJob = natureOfJob;
	}
	public int getCoverage() {
		return coverage;
	}
	public void setCoverage(int coverage) {
		this.coverage = coverage;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public boolean isPanIndia() {
		return isPanIndia;
	}
	public void setPanIndia(boolean isPanIndia) {
		this.isPanIndia = isPanIndia;
	}
	public boolean isSubApplicable() {
		return isSubApplicable;
	}
	public void setSubApplicable(boolean isSubApplicable) {
		this.isSubApplicable = isSubApplicable;
	}
	private boolean isPanIndia;
	private boolean isSubApplicable;
	private String sapWoNumber;
	public String getSapWoNumber() {
		return sapWoNumber;
	}
	public void setSapWoNumber(String sapWoNumber) {
		this.sapWoNumber = sapWoNumber;
	}

}
