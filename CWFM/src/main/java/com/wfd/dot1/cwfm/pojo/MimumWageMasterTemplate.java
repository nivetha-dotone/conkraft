package com.wfd.dot1.cwfm.pojo;

public class MimumWageMasterTemplate {
	private String trade;
	private String skill;
	private int basic;
	private int da;
	private int allowance;
	private String fromDate;
	private String unitCode;
	private String organization;
	
	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public int getBasic() {
		return basic;
	}
	public void setBasic(int basic) {
		this.basic = basic;
	}
	public int getDa() {
		return da;
	}
	public void setDa(int da) {
		this.da = da;
	}
	public int getAllowance() {
		return allowance;
	}
	public void setAllowance(int allowance) {
		this.allowance = allowance;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public MimumWageMasterTemplate(String trade,String skill,int basic,int da,int allowance,String fromDate,String unitCode,String organization) {
		this.trade=trade;
		this.skill=skill;
		this.basic=basic;
		this.da=da;
		this.allowance=allowance;
		this.fromDate=fromDate;
		this.unitCode=unitCode;
		this.organization=organization;
	}
	
	public MimumWageMasterTemplate() {
		
	}
}
