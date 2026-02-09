package com.wfd.dot1.cwfm.dto;

public class RenewalDocumentDTO {
	private String documentType;
    private String documentNumber;
    private String coverage;
    private String validFrom;
    private String validTo;
    private boolean panIndia;
    private boolean subApplicable;
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getCoverage() {
		return coverage;
	}
	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}
	public String getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	public String getValidTo() {
		return validTo;
	}
	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}
	public boolean isPanIndia() {
		return panIndia;
	}
	public void setPanIndia(boolean panIndia) {
		this.panIndia = panIndia;
	}
	public boolean isSubApplicable() {
		return subApplicable;
	}
	public void setSubApplicable(boolean subApplicable) {
		this.subApplicable = subApplicable;
	}

}
