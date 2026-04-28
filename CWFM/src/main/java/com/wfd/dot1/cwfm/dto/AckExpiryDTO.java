package com.wfd.dot1.cwfm.dto;

public class AckExpiryDTO {

    private String gatePassId;
    private String workmanName;
    private String contractorName;
    private String expiryDate;
    private int daysLeft;

    public String getGatePassId() {
        return gatePassId;
    }

    public void setGatePassId(String gatePassId) {
        this.gatePassId = gatePassId;
    }

    public String getWorkmanName() {
        return workmanName;
    }

    public void setWorkmanName(String workmanName) {
        this.workmanName = workmanName;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }
}