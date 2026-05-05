package com.wfd.dot1.cwfm.dto;

public class LicenseDto {


	    private Long id;
	    private String licenseNumber;

	    public LicenseDto() {
	    }

	    public LicenseDto(Long id, String licenseNumber) {
	        this.id = id;
	        this.licenseNumber = licenseNumber;
	    }

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getLicenseNumber() {
	        return licenseNumber;
	    }

	    public void setLicenseNumber(String licenseNumber) {
	        this.licenseNumber = licenseNumber;
	    }
	}
