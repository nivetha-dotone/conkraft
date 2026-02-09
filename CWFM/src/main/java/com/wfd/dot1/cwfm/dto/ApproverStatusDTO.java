package com.wfd.dot1.cwfm.dto;

import java.time.LocalDateTime;

public class ApproverStatusDTO {
    private String userId;          // Approver's User ID
    private String userRole;        // Approver's Role
    private String status;          // "Approved", "Rejected", "Pending"
    private String comments;        // Optional comments from the approver
    private String timestamp; // Timestamp of the approval/rejection

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String string) {
        this.timestamp = string;
    }
}

