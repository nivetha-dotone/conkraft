package com.wfd.dot1.cwfm.dto;

import java.math.BigDecimal;

public class ContractorComplianceScoreDTO {

    private Long unitId;
    private Long contractorId;
    private String complianceType;
    private Integer workmenCount;
    private BigDecimal averageRating;

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getContractorId() {
        return contractorId;
    }

    public void setContractorId(Long contractorId) {
        this.contractorId = contractorId;
    }

    public String getComplianceType() {
        return complianceType;
    }

    public void setComplianceType(String complianceType) {
        this.complianceType = complianceType;
    }

    public Integer getWorkmenCount() {
        return workmenCount;
    }

    public void setWorkmenCount(Integer workmenCount) {
        this.workmenCount = workmenCount;
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }
    
    private String unitName;
    private String contractorName;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }
}
