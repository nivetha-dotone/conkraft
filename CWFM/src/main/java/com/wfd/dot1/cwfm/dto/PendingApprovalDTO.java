package com.wfd.dot1.cwfm.dto;


import java.io.Serializable;

public class PendingApprovalDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pendingCount;

    public Integer getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(Integer pendingCount) {
        this.pendingCount = pendingCount;
    }

}
