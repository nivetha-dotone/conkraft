package com.wfd.dot1.cwfm.pojo;

import java.util.List;

public class OrgLevelMappingRequest {

    private int orgAcctSetId;  // ORGACCTSETID (this is the ID from the ORGACCTSET table)
    private List<String> selectedEntryIds;  // List of selected ORGLEVELENTRYID values

    // Getters and Setters
    public int getOrgAcctSetId() {
        return orgAcctSetId;
    }

    public void setOrgAcctSetId(int orgAcctSetId) {
        this.orgAcctSetId = orgAcctSetId;
    }

    public List<String> getSelectedEntryIds() {
        return selectedEntryIds;
    }

    public void setSelectedEntryIds(List<String> selectedEntryIds) {
        this.selectedEntryIds = selectedEntryIds;
    }
}

