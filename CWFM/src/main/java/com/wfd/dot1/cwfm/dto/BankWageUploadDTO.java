
package com.wfd.dot1.cwfm.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BankWageUploadDTO {

    private Long uploadId;

    private Long principalEmployerId;
    private Long contractorId;

    private LocalDate wageMonth;

    private String fileName;
    private String filePath;

    private Integer totalWorkmen;

    private BigDecimal totalExpectedAmount;
    private BigDecimal totalPaidAmount;
    private BigDecimal totalShortfall;

    private BigDecimal contractorRating;

    private String uploadedBy;
    private LocalDate uploadedDate;

    private String status;

	public Long getUploadId() {
		return uploadId;
	}

	public void setUploadId(Long uploadId) {
		this.uploadId = uploadId;
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

	public LocalDate getWageMonth() {
		return wageMonth;
	}

	public void setWageMonth(LocalDate wageMonth) {
		this.wageMonth = wageMonth;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getTotalWorkmen() {
		return totalWorkmen;
	}

	public void setTotalWorkmen(Integer totalWorkmen) {
		this.totalWorkmen = totalWorkmen;
	}

	public BigDecimal getTotalExpectedAmount() {
		return totalExpectedAmount;
	}

	public void setTotalExpectedAmount(BigDecimal totalExpectedAmount) {
		this.totalExpectedAmount = totalExpectedAmount;
	}

	public BigDecimal getTotalPaidAmount() {
		return totalPaidAmount;
	}

	public void setTotalPaidAmount(BigDecimal totalPaidAmount) {
		this.totalPaidAmount = totalPaidAmount;
	}

	public BigDecimal getTotalShortfall() {
		return totalShortfall;
	}

	public void setTotalShortfall(BigDecimal totalShortfall) {
		this.totalShortfall = totalShortfall;
	}

	public BigDecimal getContractorRating() {
		return contractorRating;
	}

	public void setContractorRating(BigDecimal contractorRating) {
		this.contractorRating = contractorRating;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public LocalDate getUploadedDate() {
		return uploadedDate;
	}

	public void setUploadedDate(LocalDate uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
