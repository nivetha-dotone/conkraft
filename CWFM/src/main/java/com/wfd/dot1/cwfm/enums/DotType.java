package com.wfd.dot1.cwfm.enums;

public enum DotType {
LLWCWO(4,"LLWCWO"),
LLWC(5,"LLWC"),
LLWO(6,"LLWO"),
WCWO(7,"WCWO"),
LL(8,"LL"),
WC(9,"WC"),
WO(10,"WO"),
RETIRE(11,"RETIRE");	
	private int status;
	private String name;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private DotType(int status, String name) {
		this.status = status;
		this.name = name;
	}
	
	
}
