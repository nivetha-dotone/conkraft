package com.wfd.dot1.cwfm.enums;

public enum UserRole {
	CONTRACTORSUPERVISOR("0","CONTRACTOR"),
	EIC("1","EIC"),
	SECURITY("2","SECURITY"),
	HR("3","HR"),
	SystemAdmin("4","SYSTEM ADMIN");
	
	private UserRole(String index, String name) {
		this.index = index;
		this.name = name;
	}
	private String index;
	private String name;
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
