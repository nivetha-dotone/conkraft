
package com.wfd.dot1.cwfm.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReconciliationHistoryDTO {

    private Long uploadId;
    private String reconType;
    private Long principalEmployerId;
    private Long contractorId;
    private String reconPeriod;

    private Integer totalWorkmen;
    private Integer verifiedCount;
    private Integer unverifiedCount;

    private BigDecimal contractorRating;
    private String overallStatus;
    private String fileName;
    private String uploadedBy;
    private LocalDateTime uploadedDate;

    public Long getUploadId() {
        return uploadId;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public String getReconType() {
        return reconType;
    }

    public void setReconType(String reconType) {
        this.reconType = reconType;
    }

    public Long getPrincipalEmployerId() {
        return principalEmployerId;
    }

    public void setPrincipalEmployerId(Long principalEmployerId) {
        this.principalEmployerId = principalEmployerId;
    }

    public Long getContractorId() {
        return contractorId;
    }

    public void setContractorId(Long contractorId) {
        this.contractorId = contractorId;
    }

    public String getReconPeriod() {
        return reconPeriod;
    }

    public void setReconPeriod(String reconPeriod) {
        this.reconPeriod = reconPeriod;
    }

    public Integer getTotalWorkmen() {
        return totalWorkmen;
    }

    public void setTotalWorkmen(Integer totalWorkmen) {
        this.totalWorkmen = totalWorkmen;
    }

    public Integer getVerifiedCount() {
        return verifiedCount;
    }

    public void setVerifiedCount(Integer verifiedCount) {
        this.verifiedCount = verifiedCount;
    }

    public Integer getUnverifiedCount() {
        return unverifiedCount;
    }

    public void setUnverifiedCount(Integer unverifiedCount) {
        this.unverifiedCount = unverifiedCount;
    }

    public BigDecimal getContractorRating() {
        return contractorRating;
    }

    public void setContractorRating(BigDecimal contractorRating) {
        this.contractorRating = contractorRating;
    }

    public String getOverallStatus() {
        return overallStatus;
    }

    public void setOverallStatus(String overallStatus) {
        this.overallStatus = overallStatus;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public LocalDateTime getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(LocalDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
    }
}
