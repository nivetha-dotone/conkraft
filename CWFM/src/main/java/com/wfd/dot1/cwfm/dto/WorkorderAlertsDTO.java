package com.wfd.dot1.cwfm.dto;

public class WorkorderAlertsDTO {

	 private String expiryDate;
	    private String workorderNumber;
	    private String workorderExpiryCount;
	    private int daysLeft;
		public String getExpiryDate() {
			return expiryDate;
		}
		public void setExpiryDate(String expiryDate) {
			this.expiryDate = expiryDate;
		}
		public String getWorkorderNumber() {
			return workorderNumber;
		}
		public void setWorkorderNumber(String workorderNumber) {
			this.workorderNumber = workorderNumber;
		}
		public String getWorkorderExpiryCount() {
			return workorderExpiryCount;
		}
		public void setWorkorderExpiryCount(String workorderExpiryCount) {
			this.workorderExpiryCount = workorderExpiryCount;
		}
		public int getDaysLeft() {
			return daysLeft;
		}
		public void setDaysLeft(int daysLeft) {
			this.daysLeft = daysLeft;
		}
	    
}
