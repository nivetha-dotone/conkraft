package com.wfd.dot1.cwfm.dto;


import java.util.Date;

public class OrgLevelMappingDTO {
    private int orgLevelEntryId;
    private int orgAcctSetId;
    private Date updatedTm;
    private String shortName;
    private String longDescription;

    // Default constructor
    public OrgLevelMappingDTO() {}

    // Constructor with fields
    public OrgLevelMappingDTO(int orgLevelEntryId, int orgAcctSetId, Date updatedTm, String shortName, String longDescription) {
        this.orgLevelEntryId = orgLevelEntryId;
        this.orgAcctSetId = orgAcctSetId;
        this.updatedTm = updatedTm;
        this.shortName = shortName;
        this.longDescription = longDescription;
    }

    // Getter and Setter methods
    public int getOrgLevelEntryId() {
        return orgLevelEntryId;
    }

    public void setOrgLevelEntryId(int orgLevelEntryId) {
        this.orgLevelEntryId = orgLevelEntryId;
    }

    public int getOrgAcctSetId() {
        return orgAcctSetId;
    }

    public void setOrgAcctSetId(int orgAcctSetId) {
        this.orgAcctSetId = orgAcctSetId;
    }

    public Date getUpdatedTm() {
        return updatedTm;
    }

    public void setUpdatedTm(Date updatedTm) {
        this.updatedTm = updatedTm;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
}

