package com.wfd.dot1.cwfm.dto;



import java.math.BigDecimal;

public class ChallanEmployeeDTO {

    private String workmenName;
    private String pfNumber;
    private String esicNumber;
    private BigDecimal amount;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}