package com.wfd.dot1.cwfm.dto;

public class PlantWorkmenDTO {
    private String plantName;
    private int activeCount;
    private String contractorName;
    private int contractorCount;
    public int getContractorCount() {
		return contractorCount;
	}
	public void setContractorCount(int contractorCount) {
		this.contractorCount = contractorCount;
	}
	public String getContractorName() {
		return contractorName;
	}
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}
	// getters/setters
	public String getPlantName() {
		return plantName;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	public int getActiveCount() {
		return activeCount;
	}
	public void setActiveCount(int activeCount) {
		this.activeCount = activeCount;
	}
}
