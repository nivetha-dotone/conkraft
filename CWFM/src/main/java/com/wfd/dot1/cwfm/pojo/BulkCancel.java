package com.wfd.dot1.cwfm.pojo;

public class BulkCancel {

	private String gatepassNumber;
	private String cancelReason;
	private String dot;
	
	public String getGatepassNumber() {
		return gatepassNumber;
	}
	public String getDot() {
		return dot;
	}
	public void setDot(String dot) {
		this.dot = dot;
	}
	public void setGatepassNumber(String gatepassNumber) {
		this.gatepassNumber = gatepassNumber;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	
	
}
