package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.dto.CertificationDTO;
import com.wfd.dot1.cwfm.dto.GatePassTradeSkillDTO;
import com.wfd.dot1.cwfm.dto.TradeSkillDTO;
import com.wfd.dot1.cwfm.dto.TradeSkillListingDto;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;

public interface TradeSkillDao {

	List<TradeSkillListingDto> geWorkmenListBasedOnPE(String unitId);

	List<CmsGeneralMaster> getAllTradeSkillBasedOnPe(String unitId);

	List<CmsGeneralMaster> getAllProLevel();

	void deleteByGatePass(String gatePassId);

	void batchInsert(GatePassTradeSkillDTO dto, String user);

	List<TradeSkillDTO> viewTradeSkill(String gatePassId);

	void deleteCertification(String gatePassId);

	void batchInsertCertification(GatePassTradeSkillDTO dto, String user);

	List<CertificationDTO> getCertification(String gatePassId);

	List<CmsGeneralMaster> getAllCert();

	List<TradeSkillDTO> viewExistingTradeSkill(String gatePassId);

	List<CertificationDTO> viewCertification(String gatePassId);

	
}
