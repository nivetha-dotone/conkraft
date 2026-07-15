package com.wfd.dot1.cwfm.dto;

public class LicensesDTO {

	private int totalLL;
    private int activeLL;
    private int LLPercentage;
	public int getTotalLL() {
		return totalLL;
	}
	public void setTotalLL(int totalLL) {
		this.totalLL = totalLL;
	}
	public int getActiveLL() {
		return activeLL;
	}
	public void setActiveLL(int activeLL) {
		this.activeLL = activeLL;
	}
	public int getLLPercentage() {
		return LLPercentage;
	}
	public void setLLPercentage(int lLPercentage) {
		LLPercentage = lLPercentage;
	}
    
}
