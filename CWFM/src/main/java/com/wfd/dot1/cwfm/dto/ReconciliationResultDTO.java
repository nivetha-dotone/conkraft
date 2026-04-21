package com.wfd.dot1.cwfm.dto;



import java.util.ArrayList;
import java.util.List;

public class ReconciliationResultDTO {

    private String status;
    private int totalCount;
    private int verifiedCount;
    private int unverifiedCount;
    private String message;
    private Long uploadId;
    private List<ReconciliationMismatchDTO> mismatchList = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getVerifiedCount() {
        return verifiedCount;
    }

    public void setVerifiedCount(int verifiedCount) {
        this.verifiedCount = verifiedCount;
    }

    public int getUnverifiedCount() {
        return unverifiedCount;
    }

    public void setUnverifiedCount(int unverifiedCount) {
        this.unverifiedCount = unverifiedCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUploadId() {
        return uploadId;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public List<ReconciliationMismatchDTO> getMismatchList() {
        return mismatchList;
    }

    public void setMismatchList(List<ReconciliationMismatchDTO> mismatchList) {
        this.mismatchList = mismatchList;
    }
}