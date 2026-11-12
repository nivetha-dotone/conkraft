
package com.wfd.dot1.cwfm.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BankWageDetailDTO {

    private Long uploadId;
    private String gatePassId;
    private String workmenName;
    private String accountNumber;
    private String ifscCode;

    private BigDecimal expectedAmount;
    private BigDecimal paidAmount;
    private BigDecimal differenceAmount;
    private BigDecimal variancePercentage;
    private BigDecimal paymentPercentage;

    private Integer rating;
    private String ratingDescription;


@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
private LocalDate transactionDate;

@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
private LocalDate dueDate;

    private String utrNumber;

    private String amountStatus;
    private String timelinessStatus;
	public Long getUploadId() {
		return uploadId;
	}
	public void setUploadId(Long uploadId) {
		this.uploadId = uploadId;
	}
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
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public BigDecimal getExpectedAmount() {
		return expectedAmount;
	}
	public void setExpectedAmount(BigDecimal expectedAmount) {
		this.expectedAmount = expectedAmount;
	}
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	public BigDecimal getDifferenceAmount() {
		return differenceAmount;
	}
	public void setDifferenceAmount(BigDecimal differenceAmount) {
		this.differenceAmount = differenceAmount;
	}
	public BigDecimal getVariancePercentage() {
		return variancePercentage;
	}
	public void setVariancePercentage(BigDecimal variancePercentage) {
		this.variancePercentage = variancePercentage;
	}
	public BigDecimal getPaymentPercentage() {
		return paymentPercentage;
	}
	public void setPaymentPercentage(BigDecimal paymentPercentage) {
		this.paymentPercentage = paymentPercentage;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public String getRatingDescription() {
		return ratingDescription;
	}
	public void setRatingDescription(String ratingDescription) {
		this.ratingDescription = ratingDescription;
	}
	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public String getUtrNumber() {
		return utrNumber;
	}
	public void setUtrNumber(String utrNumber) {
		this.utrNumber = utrNumber;
	}
	public String getAmountStatus() {
		return amountStatus;
	}
	public void setAmountStatus(String amountStatus) {
		this.amountStatus = amountStatus;
	}
	public String getTimelinessStatus() {
		return timelinessStatus;
	}
	public void setTimelinessStatus(String timelinessStatus) {
		this.timelinessStatus = timelinessStatus;
	}

}
