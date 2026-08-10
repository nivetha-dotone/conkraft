package com.wfd.dot1.cwfm.dto;

public class GatepassExpiryDTO {
	 private String expiryDate;
	    private String gatepassId;
	    private String fullName;
	    private int daysLeft;
		public String getExpiryDate() {
			return expiryDate;
		}
		public void setExpiryDate(String expiryDate) {
			this.expiryDate = expiryDate;
		}
		public String getGatepassId() {
			return gatepassId;
		}
		public void setGatepassId(String gatepassId) {
			this.gatepassId = gatepassId;
		}
		public String getFullName() {
			return fullName;
		}
		public void setFullName(String fullName) {
			this.fullName = fullName;
		}
		public int getDaysLeft() {
			return daysLeft;
		}
		public void setDaysLeft(int daysLeft) {
			this.daysLeft = daysLeft;
		}
}
