package com.wfd.dot1.cwfm.pojo;


import java.util.Date;
import java.util.List;

import com.wfd.dot1.cwfm.dto.OrgLevelEntryDTO;

public class OrgLevel {

    private int orgLevelDefId; // Maps to ORGLEVELDEFID
    private String name; // Maps to NAME
    private String shortName; // Maps to SHORTNAME
    private Integer orgHierarchyLevel; // Maps to ORGHIERARCHYLEVEL
    private Date updateDtm; // Maps to UPDATE_DTM
    private int updatedByUsrAcctId; // Maps to UPDATEDBYUSRACCTID
    private int minimumLength; // Maps to MINIMUMLENGTH
    private int maximumLength; // Maps to MAXIMUMLENGTH
    private Integer overrideSw; // Maps to OVERRIDE_SW
    private Integer version; // Maps to VERSION
    private Integer inactive; // Maps to INACTIVE

    // Getters and Setters
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getOrgHierarchyLevel() {
        return orgHierarchyLevel;
    }

    public void setOrgHierarchyLevel(Integer orgHierarchyLevel) {
        this.orgHierarchyLevel = orgHierarchyLevel;
    }

    public Date getUpdateDtm() {
        return updateDtm;
    }

    public void setUpdateDtm(Date updateDtm) {
        this.updateDtm = updateDtm;
    }

    public int getUpdatedByUsrAcctId() {
        return updatedByUsrAcctId;
    }

    public void setUpdatedByUsrAcctId(int updatedByUsrAcctId) {
        this.updatedByUsrAcctId = updatedByUsrAcctId;
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

    public Integer getOverrideSw() {
        return overrideSw;
    }

    public void setOverrideSw(Integer overrideSw) {
        this.overrideSw = overrideSw;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getInactive() {
        return inactive;
    }

    public void setInactive(Integer inactive) {
        this.inactive = inactive;
    }

    @Override
    public String toString() {
        return "OrgLevel{" +
                "orgLevelDefId=" + orgLevelDefId +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", orgHierarchyLevel=" + orgHierarchyLevel +
                ", updateDtm=" + updateDtm +
                ", updatedByUsrAcctId=" + updatedByUsrAcctId +
                ", minimumLength=" + minimumLength +
                ", maximumLength=" + maximumLength +
                ", overrideSw=" + overrideSw +
                ", version=" + version +
                ", inactive=" + inactive +
                '}';
    }
    private List<OrgLevelEntryDTO> availableEntries;
    private List<OrgLevelEntryDTO> selectedEntries; // New property for selected entries

    // Getter for availableEntries
    public List<OrgLevelEntryDTO> getAvailableEntries() {
        return availableEntries;
    }

    // Setter for availableEntries
    public void setAvailableEntries(List<OrgLevelEntryDTO> availableEntries) {
        this.availableEntries = availableEntries;
    }

    // Getter for selectedEntries
    public List<OrgLevelEntryDTO> getSelectedEntries() {
        return selectedEntries;
    }

    // Setter for selectedEntries
    public void setSelectedEntries(List<OrgLevelEntryDTO> selectedEntries) {
        this.selectedEntries = selectedEntries;
    }
}

