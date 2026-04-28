package com.wfd.dot1.cwfm.dto;

public class ContractorWorkmenDTO {

    private String contractorId;
    private String contractorName;
    private int workmenCount;

    public String getContractorId() {
        return contractorId;
    }

    public void setContractorId(String contractorId) {
        this.contractorId = contractorId;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public int getWorkmenCount() {
        return workmenCount;
    }

    public void setWorkmenCount(int workmenCount) {
        this.workmenCount = workmenCount;
    }
}