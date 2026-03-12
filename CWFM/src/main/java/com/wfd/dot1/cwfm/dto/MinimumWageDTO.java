package com.wfd.dot1.cwfm.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class MinimumWageDTO {
	private String unitCode;
	private String stateName;
	private String zoneName;
	private String skillName;
	private BigDecimal basic;
    private BigDecimal da;
    private BigDecimal otherAllowance;
    private Date toDate;
    private Date fromDate;
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
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
	public BigDecimal getOtherAllowance() {
		return otherAllowance;
	}
	public void setOtherAllowance(BigDecimal otherAllowance) {
		this.otherAllowance = otherAllowance;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
    
	

	
    
    
}

