package com.wfd.dot1.cwfm.dto;


import java.io.Serializable;

public class ActiveContractorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer contractorCount;
    
    private Long contractorId;

    public Integer getContractorCount() {
        return contractorCount;
    }

    public Long getContractorId() {
		return contractorId;
	}

	public void setContractorId(long l) {
		this.contractorId = l;
	}

	public void setContractorCount(Integer contractorCount) {
        this.contractorCount = contractorCount;
    }
    
    private String contractorName;

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
