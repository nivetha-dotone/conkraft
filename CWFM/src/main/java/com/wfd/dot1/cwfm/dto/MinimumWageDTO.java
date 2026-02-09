package com.wfd.dot1.cwfm.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MinimumWageDTO {
	private BigDecimal basic;
    private BigDecimal da;
    private BigDecimal allowance;
    private LocalDate fromDate;
	public BigDecimal getBasic() {
		return basic;
	}
	public void setBasic(BigDecimal basic) {
		this.basic = basic;
	}
	public BigDecimal getDa() {
		return da;
	}
	public void setDa(BigDecimal da) {
		this.da = da;
	}
	public BigDecimal getAllowance() {
		return allowance;
	}
	public void setAllowance(BigDecimal allowance) {
		this.allowance = allowance;
	}
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	
    
    
}

