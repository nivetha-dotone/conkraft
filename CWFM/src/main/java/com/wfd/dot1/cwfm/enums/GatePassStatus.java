package com.wfd.dot1.cwfm.enums;

public enum GatePassStatus {

	DRAFT("1","DRAFT"),
	SUBMIT("2","SUBMIT"),
	APPROVALPENDING("3","APPROVAL PENDING"),
	APPROVED("4","APPROVED"),
	REJECTED("5","REJECTED");
	
	private String status;
	private String name;
	private GatePassStatus(String status, String name) {
		this.status = status;
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
