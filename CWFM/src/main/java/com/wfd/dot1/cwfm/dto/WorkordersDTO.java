package com.wfd.dot1.cwfm.dto;

public class WorkordersDTO {

	private int totalWO;
    private int activeWO;
    private int woPercentage;
    private String expiryDate;
    private String workorderNumber;
    private String workorderExpiryCount;
    private int daysLeft;
	public String getWorkorderExpiryCount() {
		return workorderExpiryCount;
	}
	public void setWorkorderExpiryCount(String workorderExpiryCount) {
		this.workorderExpiryCount = workorderExpiryCount;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public String getWorkorderNumber() {
		return workorderNumber;
	}
	public void setWorkorderNumber(String workorderNumber) {
		this.workorderNumber = workorderNumber;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public int getDaysLeft() {
		return daysLeft;
	}
	public void setDaysLeft(int daysLeft) {
		this.daysLeft = daysLeft;
	}
	public int getTotalWO() {
		return totalWO;
	}
	public void setTotalWO(int totalWO) {
		this.totalWO = totalWO;
	}
	public int getActiveWO() {
		return activeWO;
	}
	public void setActiveWO(int activeWO) {
		this.activeWO = activeWO;
	}
	public int getWoPercentage() {
		return woPercentage;
	}
	public void setWoPercentage(int woPercentage) {
		this.woPercentage = woPercentage;
	}
    
    
}
