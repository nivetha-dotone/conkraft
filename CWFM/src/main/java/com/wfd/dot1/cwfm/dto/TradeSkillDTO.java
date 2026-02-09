package com.wfd.dot1.cwfm.dto;

public class TradeSkillDTO {
    private String tradeId;
    private String skillId;
    private String proficiencyId;
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public String getSkillId() {
		return skillId;
	}
	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}
	public String getProficiencyId() {
		return proficiencyId;
	}
	public void setProficiencyId(String proficiencyId) {
		this.proficiencyId = proficiencyId;
	}
	private String tradeName;
	private String skillName;
	private String proficiencyName;
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	public String getProficiencyName() {
		return proficiencyName;
	}
	public void setProficiencyName(String proficiencyName) {
		this.proficiencyName = proficiencyName;
	}
	

}

