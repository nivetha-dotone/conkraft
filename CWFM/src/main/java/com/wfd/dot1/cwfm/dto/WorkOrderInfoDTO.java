package com.wfd.dot1.cwfm.dto;

import java.util.List;

public class WorkOrderInfoDTO {
    private Long contractorRegId;
    private List<String> selectedWOs;
    private List<String> licenseId;
    private String unitId;
    private String contractorId;
	public Long getContractorRegId() {
		return contractorRegId;
	}
	public void setContractorRegId(Long contractorRegId) {
		this.contractorRegId = contractorRegId;
	}
	public List<String> getSelectedWOs() {
		return selectedWOs;
	}
	public void setSelectedWOs(List<String> selectedWOs) {
		this.selectedWOs = selectedWOs;
	}
	public List<String> getLicenseId() {
		return licenseId;
	}
	public void setLicenseId(List<String> licenseId) {
		this.licenseId = licenseId;
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

    // Getters and setters
}

