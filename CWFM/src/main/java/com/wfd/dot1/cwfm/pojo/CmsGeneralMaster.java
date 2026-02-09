package com.wfd.dot1.cwfm.pojo;

public class CmsGeneralMaster {

	private String gmId;
	
	private String gmType;
	
	private String masterName;
	
    private String masterValue;
    
    private String gmName;
    
    private String gmDescription;
    
    private int gmTypeId;
    
    private boolean isActive;
    
    private String createdTM;
    
    private String updatedTM;
    
    private String updatedBy;
    
    
	
	public String getGmDescription() {
		return gmDescription;
	}

	public void setGmDescription(String gmDescription) {
		this.gmDescription = gmDescription;
	}

	public int getGmTypeId() {
		return gmTypeId;
	}

	public void setGmTypeId(int gmTypeId) {
		this.gmTypeId = gmTypeId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getCreatedTM() {
		return createdTM;
	}

	public void setCreatedTM(String createdTM) {
		this.createdTM = createdTM;
	}

	public String getUpdatedTM() {
		return updatedTM;
	}

	public void setUpdatedTM(String updatedTM) {
		this.updatedTM = updatedTM;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getGmType() {
		return gmType;
	}

	public void setGmType(String gmType) {
		this.gmType = gmType;
	}

	private String authorizationOn;
	
	private String authorizationValue;

	public String getGmId() {
		return gmId;
	}

	public void setGmId(String gmId) {
		this.gmId = gmId;
	}

	public String getGmName() {
		return gmName;
	}

	public void setGmName(String gmName) {
		this.gmName = gmName;
	}

	public String getAuthorizationOn() {
		return authorizationOn;
	}

	public void setAuthorizationOn(String authorizationOn) {
		this.authorizationOn = authorizationOn;
	}

	public String getAuthorizationValue() {
		return authorizationValue;
	}

	public void setAuthorizationValue(String authorizationValue) {
		this.authorizationValue = authorizationValue;
	}

	public void setGmdescription(String string) {
		// TODO Auto-generated method stub
		
	}

	public Object getGmdescription() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public CmsGeneralMaster(String gmName, String gmDescription, int gmTypeId,boolean isActive,String createdTM,String updatedTM,String updatedBy) {
        this.gmName = gmName;
        this.gmDescription = gmDescription;
        this.gmTypeId = gmTypeId;
        this.isActive = isActive;
        this.createdTM = createdTM;
        this.updatedTM = updatedTM;
        this.updatedBy = updatedBy;
    }

	public String getMasterValue() {
		return masterValue;
	}

	public void setMasterValue(String masterValue) {
		this.masterValue = masterValue;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}
	 public CmsGeneralMaster() {
	    }
}
