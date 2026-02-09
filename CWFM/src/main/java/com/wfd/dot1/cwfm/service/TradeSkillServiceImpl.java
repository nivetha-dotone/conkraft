package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.TradeSkillDao;
import com.wfd.dot1.cwfm.dto.CertificationDTO;
import com.wfd.dot1.cwfm.dto.GatePassTradeSkillDTO;
import com.wfd.dot1.cwfm.dto.TradeSkillDTO;
import com.wfd.dot1.cwfm.dto.TradeSkillListingDto;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
@Service
public class TradeSkillServiceImpl implements TradeSkillService{

	@Autowired
	TradeSkillDao dao;
	@Override
	public List<TradeSkillListingDto> getWorkmenListBasedOnPE(String unitId) {
		// TODO Auto-generated method stub
		return dao.geWorkmenListBasedOnPE(unitId);
	}
	@Override
	public List<CmsGeneralMaster> getAllTradeSkillBasedOnPe(String unitId) {
		// TODO Auto-generated method stub
		return dao.getAllTradeSkillBasedOnPe(unitId);
	}
	@Override
	public List<CmsGeneralMaster> getAllProLevel() {
		// TODO Auto-generated method stub
		return dao.getAllProLevel();
	}
	@Override
	public void saveTradeSkill(GatePassTradeSkillDTO dto, String user) {
	    dao.deleteByGatePass(dto.getGatePassId());
	    dao.deleteCertification(dto.getGatePassId());
	    dao.batchInsert(dto, user);
	    dao.batchInsertCertification(dto, user);
	}
	@Override
	public List<TradeSkillDTO> viewTradeSkill(String gatePassId) {
		// TODO Auto-generated method stub
		return dao.viewTradeSkill(gatePassId);
	}

	@Override
	public List<CertificationDTO> getCertification(String gatePassId){
		return dao.getCertification(gatePassId);
	}
	@Override
	public List<CmsGeneralMaster> getAllCert() {
		// TODO Auto-generated method stub
		return dao.getAllCert();
	}
}
