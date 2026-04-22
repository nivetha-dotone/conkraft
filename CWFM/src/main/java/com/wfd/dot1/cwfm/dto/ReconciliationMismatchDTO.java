package com.wfd.dot1.cwfm.dto;

import java.math.BigDecimal;

public class ReconciliationMismatchDTO {

    private String gatePassId;
    private String workmenName;
    private String dbNumber;
    private String docNumber;
    private BigDecimal dbAmount;
    private BigDecimal docAmount;
    private String mismatchReason;
    private String reconType;

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

    public String getDbNumber() {
        return dbNumber;
    }

    public void setDbNumber(String dbNumber) {
        this.dbNumber = dbNumber;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public BigDecimal getDbAmount() {
        return dbAmount;
    }

    public void setDbAmount(BigDecimal dbAmount) {
        this.dbAmount = dbAmount;
    }

    public BigDecimal getDocAmount() {
        return docAmount;
    }

    public void setDocAmount(BigDecimal docAmount) {
        this.docAmount = docAmount;
    }

    public String getMismatchReason() {
        return mismatchReason;
    }

    public void setMismatchReason(String mismatchReason) {
        this.mismatchReason = mismatchReason;
    }

    public String getReconType() {
        return reconType;
    }

    public void setReconType(String reconType) {
        this.reconType = reconType;
    }
}