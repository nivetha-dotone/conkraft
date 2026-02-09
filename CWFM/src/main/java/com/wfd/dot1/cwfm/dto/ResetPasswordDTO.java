package com.wfd.dot1.cwfm.dto;

public class ResetPasswordDTO {
	 private String userAccount;
	    public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
		private String newPassword;
}
