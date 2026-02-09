package com.wfd.dot1.cwfm.pojo;

public class Workorder {

	private String workorderId;
	private String unitId;
	private String contractorId;
	private String name;
	private String validFrom;
	private String validTo;
	private String sapWorkorderNumber;
	private String eicNumber;
	private String releasedDate;
	private String typeId;
	private String depId;
	private String secId;
	private String status;
	private String glCode;
	private String costCenter;
	
	
	public String getReleasedDate() {
		return releasedDate;
	}
	public void setReleasedDate(String releasedDate) {
		this.releasedDate = releasedDate;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getGlCode() {
		return glCode;
	}
	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getDepId() {
		return depId;
	}
	public void setDepId(String depId) {
		this.depId = depId;
	}
	public String getSecId() {
		return secId;
	}
	public void setSecId(String secId) {
		this.secId = secId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWorkorderId() {
		return workorderId;
	}
	public void setWorkorderId(String workorderId) {
		this.workorderId = workorderId;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getContractorId() {
		return contractorId;
	}
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getSapWorkorderNumber() {
		return sapWorkorderNumber;
	}
	public void setSapWorkorderNumber(String sapWorkorderNumber) {
		this.sapWorkorderNumber = sapWorkorderNumber;
	}
	public String getEicNumber() {
		return eicNumber;
	}
	public void setEicNumber(String eicNumber) {
		this.eicNumber = eicNumber;
	}
	
	private String job;
	private String serviceCode;
	private String trade;
	private String skill;
	private String itemQuantity;
	private String rate;
	private String serviceEntryQty;
	private String wbsCode;
	private String uom;

	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public String getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(String itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getServiceEntryQty() {
		return serviceEntryQty;
	}
	public void setServiceEntryQty(String serviceEntryQty) {
		this.serviceEntryQty = serviceEntryQty;
	}
	public String getWbsCode() {
		return wbsCode;
	}
	public void setWbsCode(String wbsCode) {
		this.wbsCode = wbsCode;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
private int activeWorkmenCount;
	
	public int getActiveWorkmenCount() {
		return activeWorkmenCount;
	}

	public void setActiveWorkmenCount(int activeCount) {
		this.activeWorkmenCount = activeCount;
	}
	
	private String classification;


	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	
}
