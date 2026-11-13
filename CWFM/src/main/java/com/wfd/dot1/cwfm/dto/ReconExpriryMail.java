package com.wfd.dot1.cwfm.dto;

public class ReconExpriryMail {

    private String gatepassID;
    private String workmenName;
    private String wageNumber;
    private String documentNumber;
    private String wageCostAmount;
    private String documentAmount;
    private String errorMsg;
    private String reconType;
    private String emailId;

    // Generate getters and setters for all fields

    public String getGatepassID() {
        return gatepassID;
    }

    public void setGatepassID(String gatepassID) {
        this.gatepassID = gatepassID;
    }

    public String getWorkmenName() {
        return workmenName;
    }

    public void setWorkmenName(String workmenName) {
        this.workmenName = workmenName;
    }

    public String getWageNumber() {
        return wageNumber;
    }

    public void setWageNumber(String wageNumber) {
        this.wageNumber = wageNumber;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getWageCostAmount() {
        return wageCostAmount;
    }

    public void setWageCostAmount(String wageCostAmount) {
        this.wageCostAmount = wageCostAmount;
    }

    public String getDocumentAmount() {
        return documentAmount;
    }

    public void setDocumentAmount(String documentAmount) {
        this.documentAmount = documentAmount;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getReconType() {
        return reconType;
    }

    public void setReconType(String reconType) {
        this.reconType = reconType;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}