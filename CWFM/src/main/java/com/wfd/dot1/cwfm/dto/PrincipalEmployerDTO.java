package com.wfd.dot1.cwfm.dto;


import java.io.Serializable;

public class PrincipalEmployerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String principalEmployerId;

    private String principalEmployerName;
    
    private String pecode;

    public String getPecode() {
		return pecode;
	}

	public void setPecode(String pecode) {
		this.pecode = pecode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPrincipalEmployerId() {
        return principalEmployerId;
    }

    public void setPrincipalEmployerId(String principalEmployerId) {
        this.principalEmployerId = principalEmployerId;
    }

    public String getPrincipalEmployerName() {
        return principalEmployerName;
    }

    public void setPrincipalEmployerName(String principalEmployerName) {
        this.principalEmployerName = principalEmployerName;
    }

}
