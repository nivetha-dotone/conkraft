package com.wfd.dot1.cwfm.dto;

public class GeneralMasterDTO {
    private Long gmId;
    public Long getGmId() {
		return gmId;
	}

	public void setGmId(Long gmId) {
		this.gmId = gmId;
	}

	public String getGmTypeName() {
		return gmTypeName;
	}

	public void setGmTypeName(String gmTypeName) {
		this.gmTypeName = gmTypeName;
	}

	public String getGmName() {
		return gmName;
	}

	public void setGmName(String gmName) {
		this.gmName = gmName;
	}

	public String getGmDescription() {
		return gmDescription;
	}

	public void setGmDescription(String gmDescription) {
		this.gmDescription = gmDescription;
	}

	private String gmTypeName;
    private String gmName;
    private String gmDescription;
    private Long gmTypeId;
    public Long getGmTypeId() {
		return gmTypeId;
	}

	public void setGmTypeId(Long gmTypeId) {
		this.gmTypeId = gmTypeId;
	}

	public GeneralMasterDTO() {
    }
    public GeneralMasterDTO(Long gmId, Long gmTypeId, String gmTypeName, String gmName, String gmDescription) {
        this.gmId = gmId;
        this.gmTypeId = gmTypeId;
        this.gmTypeName = gmTypeName;
        this.gmName = gmName;
        this.gmDescription = gmDescription;
    }

    // Getters and setters
}

