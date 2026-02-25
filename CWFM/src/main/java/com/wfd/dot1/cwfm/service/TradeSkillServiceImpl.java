package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.controller.CreateEmpFetchByGatePassAPICALL;
import com.wfd.dot1.cwfm.controller.WorkmenController;
import com.wfd.dot1.cwfm.dao.TradeSkillDao;
import com.wfd.dot1.cwfm.dto.CertificationDTO;
import com.wfd.dot1.cwfm.dto.GatePassTradeSkillDTO;
import com.wfd.dot1.cwfm.dto.TradeSkillDTO;
import com.wfd.dot1.cwfm.dto.TradeSkillListingDto;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
@Service
public class TradeSkillServiceImpl implements TradeSkillService{

	@Autowired
	TradeSkillDao dao;
	
	  @Autowired
		CreateEmpFetchByGatePassAPICALL api;
	  
		private static final Logger log = LoggerFactory.getLogger(TradeSkillServiceImpl.class.getName());
	  
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
	public String getWFDIntegration() {
		return QueryFileWatcher.getQuery("TRADE_SKILL_MAPPING_WFD_INTEGRATION");
	}
	@Override
	public void saveTradeSkill(GatePassTradeSkillDTO dto, String user) {
	    dao.deleteByGatePass(dto.getGatePassId());
	    dao.deleteCertification(dto.getGatePassId());
	    try {
	    dao.batchInsert(dto, user);
	    String wfdIntegration = this.getWFDIntegration();
    	if("yes".equalsIgnoreCase(wfdIntegration)) {
    		    		api.assignmentSkillsPro(dto.getGatePassId());
    	}
	    }catch(Exception e) {
	    	log.info(e.getMessage());
	    }
	    
	    try {
	    	dao.batchInsertCertification(dto, user);
		    String wfdIntegration = this.getWFDIntegration();
	    	if("yes".equalsIgnoreCase(wfdIntegration)) {
	    		    		api.assignmentGatepassId(dto.getGatePassId());
	    	}
		    }catch(Exception e) {
		    	log.info(e.getMessage());
		    }
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
	@Override
	public List<TradeSkillDTO> viewExistingTradeSkill(String gatePassId) {
		// TODO Auto-generated method stub
		return dao.viewExistingTradeSkill(gatePassId);
	}
	@Override
	public List<CertificationDTO> viewExisitingCertification(String gatePassId) {
		// TODO Auto-generated method stub
		return dao.viewCertification(gatePassId);
	}
}
