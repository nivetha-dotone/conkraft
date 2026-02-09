package com.wfd.dot1.cwfm.service;

import java.util.List;

import com.wfd.dot1.cwfm.dto.GatePassTradeSkillDTO;
import com.wfd.dot1.cwfm.dto.TradeSkillDTO;
import com.wfd.dot1.cwfm.dto.TradeSkillListingDto;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;

public interface TradeSkillService {

	List<TradeSkillListingDto> getWorkmenListBasedOnPE(String unitId);

	List<CmsGeneralMaster> getAllTradeSkillBasedOnPe(String unitId);

	List<CmsGeneralMaster> getAllProLevel();

	void saveTradeSkill(GatePassTradeSkillDTO dto, String user);

	List<TradeSkillDTO> viewTradeSkill(String gatePassId);

}
