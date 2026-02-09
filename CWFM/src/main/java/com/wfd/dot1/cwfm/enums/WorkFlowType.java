package com.wfd.dot1.cwfm.enums;

public enum WorkFlowType {

	AUTO(1,"AUTO"),
	SEQUENTIAL(2,"SEQUENTIAL"),
	PARALLEL(3,"PARALLEL");
	
	private int workFlowTypeId;
	private String name;
	private WorkFlowType(int workFlowTypeId, String name) {
		this.workFlowTypeId = workFlowTypeId;
		this.name = name;
	}
	public int getWorkFlowTypeId() {
		return workFlowTypeId;
	}
	public void setWorkFlowTypeId(int workFlowTypeId) {
		this.workFlowTypeId = workFlowTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
