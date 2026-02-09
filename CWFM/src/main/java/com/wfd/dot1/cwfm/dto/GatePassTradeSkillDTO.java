package com.wfd.dot1.cwfm.dto;

import java.util.List;

public class GatePassTradeSkillDTO {

    private String gatePassId;
    private List<TradeSkillDTO> tradeSkills;
	public String getGatePassId() {
		return gatePassId;
	}
	public void setGatePassId(String gatePassId) {
		this.gatePassId = gatePassId;
	}
	public List<TradeSkillDTO> getTradeSkills() {
		return tradeSkills;
	}
	public void setTradeSkills(List<TradeSkillDTO> tradeSkills) {
		this.tradeSkills = tradeSkills;
	}

    // getters & setters
	 private List<CertificationDTO> certifications;
	public List<CertificationDTO> getCertifications() {
		return certifications;
	}
	public void setCertifications(List<CertificationDTO> certifications) {
		this.certifications = certifications;
	}
}

