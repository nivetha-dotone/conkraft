package com.wfd.dot1.cwfm.dto;



import java.io.Serializable;
import java.util.Date;

public class LicenseExpiryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String contractorCode;

    private String contractorName;

    private String principalEmployer;

    private Date expiryDate;

    private Integer daysLeft;

    private String licenseNumber;
    
    private Integer expiryCount;
    
    private String validTo;

    public String getValidTo() {
		return validTo;
	}

	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}

	public Integer getExpiryCount() {
		return expiryCount;
	}

	public void setExpiryCount(Integer expiryCount) {
		this.expiryCount = expiryCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getContractorCode() {
        return contractorCode;
    }

    public void setContractorCode(String contractorCode) {
        this.contractorCode = contractorCode;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public String getPrincipalEmployer() {
        return principalEmployer;
    }

    public void setPrincipalEmployer(String principalEmployer) {
        this.principalEmployer = principalEmployer;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(Integer daysLeft) {
        this.daysLeft = daysLeft;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

}