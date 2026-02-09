package com.wfd.dot1.cwfm.dto;

public class AadhaarCheckResponse {
    private String status;

    public AadhaarCheckResponse() {}

    public AadhaarCheckResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

