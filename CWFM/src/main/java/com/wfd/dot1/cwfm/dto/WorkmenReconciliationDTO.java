package com.wfd.dot1.cwfm.dto;

import java.math.BigDecimal;



public class WorkmenReconciliationDTO {

    private String gatePassId;
    private String workmenName;
    private String pfNumber;
    private String esicNumber;
    private BigDecimal pfPrice;
    private BigDecimal esicPrice;

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

    public String getPfNumber() {
        return pfNumber;
    }

    public void setPfNumber(String pfNumber) {
        this.pfNumber = pfNumber;
    }

    public String getEsicNumber() {
        return esicNumber;
    }

    public void setEsicNumber(String esicNumber) {
        this.esicNumber = esicNumber;
    }

    public BigDecimal getPfPrice() {
        return pfPrice;
    }

    public void setPfPrice(BigDecimal pfPrice) {
        this.pfPrice = pfPrice;
    }

    public BigDecimal getEsicPrice() {
        return esicPrice;
    }

    public void setEsicPrice(BigDecimal esicPrice) {
        this.esicPrice = esicPrice;
    }
}