package com.wfd.dot1.cwfm.dto;


import java.math.BigDecimal;

public class WorkmenReconciliationDTO {

    private String gatePassId;
    private String workmenName;

    private String uanNumber;
    private String pfNumber;
    private BigDecimal pfAmount;

    private String esicNumber;
    private BigDecimal esicAmount;

    public String getGatePassId() {
        return gatePassId;
    }

    public void setGatePassId(String gatePassId) {
        this.gatePassId = gatePassId;
    }

    public String getWorkmenName() {
        return workmenName;
    }

    public void setWorkmenName(String workmenName) {
        this.workmenName = workmenName;
    }

    public String getUanNumber() {
        return uanNumber;
    }

    public void setUanNumber(String uanNumber) {
        this.uanNumber = uanNumber;
    }

    public String getPfNumber() {
        return pfNumber;
    }

    public void setPfNumber(String pfNumber) {
        this.pfNumber = pfNumber;
    }

    public BigDecimal getPfAmount() {
        return pfAmount;
    }

    public void setPfAmount(BigDecimal pfAmount) {
        this.pfAmount = pfAmount;
    }

    public String getEsicNumber() {
        return esicNumber;
    }

    public void setEsicNumber(String esicNumber) {
        this.esicNumber = esicNumber;
    }

    public BigDecimal getEsicAmount() {
        return esicAmount;
    }

    public void setEsicAmount(BigDecimal esicAmount) {
        this.esicAmount = esicAmount;
    }
}