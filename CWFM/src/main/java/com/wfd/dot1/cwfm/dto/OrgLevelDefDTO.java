package com.wfd.dot1.cwfm.dto;

import java.util.List;

import com.wfd.dot1.cwfm.dao.OrgLevelDao;

public class OrgLevelDefDTO {
    private String name;
    private Long orgLevelDefId; 
    private List<OrgLevelEntryDTO> availableEntries; // List for available entries
    private List<OrgLevelEntryDTO> selectedEntries;
    public Long getOrgLevelDefId() {
        return orgLevelDefId;
    }

    public void setOrgLevelDefId(Long orgLevelDefId) {
        this.orgLevelDefId = orgLevelDefId;
    }
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public int getOrgHierarchyLevel() {
		return orgHierarchyLevel;
	}
	public void setOrgHierarchyLevel(int orgHierarchyLevel) {
		this.orgHierarchyLevel = orgHierarchyLevel;
	}
	public int getMinimumLength() {
		return minimumLength;
	}
	public void setMinimumLength(int minimumLength) {
		this.minimumLength = minimumLength;
	}
	public int getMaximumLength() {
		return maximumLength;
	}
	public void setMaximumLength(int maximumLength) {
		this.maximumLength = maximumLength;
	}
	public boolean isOverrideSwitch() {
		return overrideSwitch;
	}
	public void setOverrideSwitch(boolean overrideSwitch) {
		this.overrideSwitch = overrideSwitch;
	}
	private String shortName;
    private int orgHierarchyLevel;
    private int minimumLength = 0;  // default value
    private int maximumLength = 100;  // default value
    private boolean overrideSwitch = false;  // default value
    private int updatedByUsrAcctId;
	public int getUpdatedByUsrAcctId() {
		return updatedByUsrAcctId;
	}
	public void setUpdatedByUsrAcctId(int updatedByUsrAcctId) {
		this.updatedByUsrAcctId = updatedByUsrAcctId;
	}

	 public List<OrgLevelEntryDTO> getAvailableEntries() { return availableEntries; }
	    public void setAvailableEntries(List<OrgLevelEntryDTO> availableEntries) { this.availableEntries = availableEntries; }
	    public List<OrgLevelEntryDTO> getSelectedEntries() { return selectedEntries; }
	    public void setSelectedEntries(List<OrgLevelEntryDTO> selectedEntries) { this.selectedEntries = selectedEntries; }


	

}

