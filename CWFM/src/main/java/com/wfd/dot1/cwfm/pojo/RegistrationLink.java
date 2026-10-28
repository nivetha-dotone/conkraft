package com.wfd.dot1.cwfm.pojo;


import java.time.LocalDateTime;

public class RegistrationLink {

    private Long linkId;

    private String unitId;
    private String contractorId;
    private String workorderId;
    private String departmentId;
    private String areaId;
    private String wcEsicNo;
    private String llNo;

    private String createdBy;
    private String  createdDate;
    private String  expiryDate;

    private boolean active = true;

	public Long getLinkId() {
		return linkId;
	}

	public void setLinkId(Long linkId) {
		this.linkId = linkId;
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

	public String getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(String workorderId) {
		this.workorderId = workorderId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getWcEsicNo() {
		return wcEsicNo;
	}

	public void setWcEsicNo(String wcEsicNo) {
		this.wcEsicNo = wcEsicNo;
	}

	public String getLlNo() {
		return llNo;
	}

	public void setLlNo(String llNo) {
		this.llNo = llNo;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

   
}
