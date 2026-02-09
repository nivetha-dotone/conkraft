package com.wfd.dot1.cwfm.pojo;
import java.sql.Timestamp;

public class CMSContractorRegistrationLLWC {
    private int contractorRegLLWCId;
    private long contractorRegId;
    private int contractorId;
    private int unitId;
    private int workOrderId;
    private String licenseType;
    private String workOrderNumber;
    private String wcCode;
    private String createdBy;
    private String updatedBy;
    private Timestamp createdDtm;
    private Timestamp updatedDtm;
	public int getContractorRegLLWCId() {
		return contractorRegLLWCId;
	}
	public void setContractorRegLLWCId(int contractorRegLLWCId) {
		this.contractorRegLLWCId = contractorRegLLWCId;
	}
	public long getContractorRegId() {
		return contractorRegId;
	}
	public void setContractorRegId(long contractorRegId) {
		this.contractorRegId = contractorRegId;
	}
	public int getContractorId() {
		return contractorId;
	}
	public void setContractorId(int contractorId) {
		this.contractorId = contractorId;
	}
	public int getUnitId() {
		return unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	public int getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(int workOrderId) {
		this.workOrderId = workOrderId;
	}
	public String getLicenseType() {
		return licenseType;
	}
	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}
	public String getWorkOrderNumber() {
		return workOrderNumber;
	}
	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}
	public String getWcCode() {
		return wcCode;
	}
	public void setWcCode(String wcCode) {
		this.wcCode = wcCode;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Timestamp getCreatedDtm() {
		return createdDtm;
	}
	public void setCreatedDtm(Timestamp createdDtm) {
		this.createdDtm = createdDtm;
	}
	public Timestamp getUpdatedDtm() {
		return updatedDtm;
	}
	public void setUpdatedDtm(Timestamp updatedDtm) {
		this.updatedDtm = updatedDtm;
	}

    // Getters and setters
}
