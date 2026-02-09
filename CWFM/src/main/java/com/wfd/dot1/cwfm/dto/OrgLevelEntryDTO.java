package com.wfd.dot1.cwfm.dto;

import java.util.List;

public class OrgLevelEntryDTO {

    private long orgLevelEntryId;  // Primary Key
    private int orgLevelDefId;    // Foreign Key to ORGLEVELDEF
    private String name;          // Name of the entry
    private String description;   // Description of the entry
    private boolean inactive;     // Active or inactive status (mapped to 1 for active, 0 for inactive)
    private int updatedByUsrAcctId;  // ID of the user who last updated the record
    private List<Long> selectedEntryIds; 
    // Default constructor
    public OrgLevelEntryDTO() {}

    // Constructor with parameters for initializing the fields
    public OrgLevelEntryDTO(int orgLevelEntryId, int orgLevelDefId, String name, String description, boolean inactive, int updatedByUsrAcctId) {
        this.orgLevelEntryId = orgLevelEntryId;
        this.orgLevelDefId = orgLevelDefId;
        this.name = name;
        this.description = description;
        this.inactive = inactive;
        this.updatedByUsrAcctId = updatedByUsrAcctId;
    }

    // Getters and setters for each field

    public long getOrgLevelEntryId() {
    	return orgLevelEntryId;
    }

    public void setOrgLevelEntryId(long l) {
        this.orgLevelEntryId = l;
    }

    public int getOrgLevelDefId() {
        return orgLevelDefId;
    }

    public void setOrgLevelDefId(int orgLevelDefId) {
        this.orgLevelDefId = orgLevelDefId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public int getUpdatedByUsrAcctId() {
        return updatedByUsrAcctId;
    }

    public void setUpdatedByUsrAcctId(int updatedByUsrAcctId) {
        this.updatedByUsrAcctId = updatedByUsrAcctId;
    }

    public List<Long> getSelectedEntryIds() {
        return selectedEntryIds;
    }

    public void setSelectedEntryIds(List<Long> selectedEntryIds) {
        this.selectedEntryIds = selectedEntryIds;
    }

    @Override
    public String toString() {
        return "OrgLevelEntryDTO{" +
                "orgLevelEntryId=" + orgLevelEntryId +
                ", orgLevelDefId=" + orgLevelDefId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", inactive=" + inactive +
                ", updatedByUsrAcctId=" + updatedByUsrAcctId +
                ", selectedEntryIds=" + selectedEntryIds +
                '}';
    }
   
	
}

