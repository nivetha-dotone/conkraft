package com.wfd.dot1.cwfm.dto;

public class LLLicensesDTO {

	 private String expiryDate;
	    private String licenseNumber;
	    private String licenseType;
	    private String licenseExpiryCount;
	    private int daysLeft;
	    
		public String getLicenseType() {
			return licenseType;
		}
		public void setLicenseType(String licenseType) {
			this.licenseType = licenseType;
		}
		public String getExpiryDate() {
			return expiryDate;
		}
		public void setExpiryDate(String expiryDate) {
			this.expiryDate = expiryDate;
		}
		public String getLicenseNumber() {
			return licenseNumber;
		}
		public void setLicenseNumber(String licenseNumber) {
			this.licenseNumber = licenseNumber;
		}
		public String getLicenseExpiryCount() {
			return licenseExpiryCount;
		}
		public void setLicenseExpiryCount(String licenseExpiryCount) {
			this.licenseExpiryCount = licenseExpiryCount;
		}
		public int getDaysLeft() {
			return daysLeft;
		}
		public void setDaysLeft(int daysLeft) {
			this.daysLeft = daysLeft;
		}
	    
}
