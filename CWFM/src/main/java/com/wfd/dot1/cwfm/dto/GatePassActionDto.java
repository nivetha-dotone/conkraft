package com.wfd.dot1.cwfm.dto;

public class GatePassActionDto {
	
	private String gatePassId;
	
	private String createdBy;
	
	
	private String comments;

	
	private String gatePassType;
	
	private String gatePassStatus;
	
	 private String reasoning; 
	

	public String getReasoning() {
		return reasoning;
	}

	public void setReasoning(String reasoning) {
		this.reasoning = reasoning;
	}

	public String getGatePassType() {
		return gatePassType;
	}

	public void setGatePassType(String gatePassType) {
		this.gatePassType = gatePassType;
	}

	public String getGatePassStatus() {
		return gatePassStatus;
	}

	public void setGatePassStatus(String gatePassStatus) {
		this.gatePassStatus = gatePassStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getGatePassId() {
		return gatePassId;
	}

	public void setGatePassId(String gatePassId) {
		this.gatePassId = gatePassId;
	}
	
	private String transactionId;


	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	

}
