package com.wfd.dot1.cwfm.pojo;

import java.util.Date;

public class CMSContrPemm {
	private Long refId;
    private Long contractorId;
    private Long unitId;
    private String managerNm;
    private String managerEmail;
    private String managerMobile;
    private Date periodStartDt;
    private Date periodEndDt;
    private String pfNum;
    private Date pfApplyDt;
    private String esicNum;
    private boolean rcValidated;
    private Contractor contractor;
    private String licenseNumber;
    private Date licenseValidFrom;
    private Date licenseValidTo;
    private String coverage;
    private int totalStrength;
    private int maxNoEmp;
    private String natureofWork;
    private String locationofWork;
    private String pfCode;
    private String esiwc;
    private Date esiValidFrom;
    private Date esiValidTo;
    
    

    public Long getRefId() {
        return refId;
    }

    public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public Date getLicenseValidFrom() {
		return licenseValidFrom;
	}

	public void setLicenseValidFrom(Date licenseValidFrom) {
		this.licenseValidFrom = licenseValidFrom;
	}

	public Date getLicenseValidTo() {
		return licenseValidTo;
	}

	public void setLicenseValidTo(Date licenseValidTo) {
		this.licenseValidTo = licenseValidTo;
	}

	public String getCoverage() {
		return coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	public int getTotalStrength() {
		return totalStrength;
	}

	public void setTotalStrength(int totalStrength) {
		this.totalStrength = totalStrength;
	}

	public int getMaxNoEmp() {
		return maxNoEmp;
	}

	public void setMaxNoEmp(int maxNoEmp) {
		this.maxNoEmp = maxNoEmp;
	}

	public String getNatureofWork() {
		return natureofWork;
	}

	public void setNatureofWork(String natureofWork) {
		this.natureofWork = natureofWork;
	}

	public String getLocationofWork() {
		return locationofWork;
	}

	public void setLocationofWork(String locationofWork) {
		this.locationofWork = locationofWork;
	}

	public String getPfCode() {
		return pfCode;
	}

	public void setPfCode(String pfCode) {
		this.pfCode = pfCode;
	}

	public String getEsiwc() {
		return esiwc;
	}

	public void setEsiwc(String esiwc) {
		this.esiwc = esiwc;
	}

	public Date getEsiValidFrom() {
		return esiValidFrom;
	}

	public void setEsiValidFrom(Date esiValidFrom) {
		this.esiValidFrom = esiValidFrom;
	}

	public Date getEsiValidTo() {
		return esiValidTo;
	}

	public void setEsiValidTo(Date esiValidTo) {
		this.esiValidTo = esiValidTo;
	}

	public void setRefId(Long refId) {
        this.refId = refId;
    }

    public Long getContractorId() {
        return contractorId;
    }

    public void setContractorId(Long contractorId) {
        this.contractorId = contractorId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getManagerNm() {
        return managerNm;
    }

    public void setManagerNm(String managerNm) {
        this.managerNm = managerNm;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getManagerMobile() {
        return managerMobile;
    }

    public void setManagerMobile(String managerMobile) {
        this.managerMobile = managerMobile;
    }

    public Date getPeriodStartDt() {
        return periodStartDt;
    }

    public void setPeriodStartDt(Date periodStartDt) {
        this.periodStartDt = periodStartDt;
    }

    public Date getPeriodEndDt() {
        return periodEndDt;
    }

    public void setPeriodEndDt(Date periodEndDt) {
        this.periodEndDt = periodEndDt;
    }

    public String getPfNum() {
        return pfNum;
    }

    public void setPfNum(String pfNum) {
        this.pfNum = pfNum;
    }

    public Date getPfApplyDt() {
        return pfApplyDt;
    }

    public void setPfApplyDt(Date pfApplyDt) {
        this.pfApplyDt = pfApplyDt;
    }

    public String getEsicNum() {
        return esicNum;
    }

    public void setEsicNum(String esicNum) {
        this.esicNum = esicNum;
    }

    public boolean getRcValidated() {
        return rcValidated;
    }

    public void setRcValidated(boolean b) {
        this.rcValidated = b;
    }
    
	public Contractor getContractor() {
		return contractor;
	}

	public void setContractor(Contractor contractor) {
		this.contractor = contractor;
	}
}
