package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.EntrypassStatusDao;
import com.wfd.dot1.cwfm.dto.EntryPassStatusDto;
import com.wfd.dot1.cwfm.dto.GatePassListingDto;

@Service
public class EntrypassStatusServiceImpl implements EntrypassStatusService{

	 @Autowired
	    private EntrypassStatusDao entrypassDao;

	    @Override
	    public List<GatePassListingDto> getGatePassStatus(String transactionId, String gatepassId) {
	        return entrypassDao.searchGatePassStatus(transactionId, gatepassId);
	    }

		@Override
		public List<GatePassListingDto> getHistoryofEP(String aadharNumber) {
			// TODO Auto-generated method stub
			return entrypassDao.getHistoryofEP(aadharNumber);
		}
		@Override
		public List<EntryPassStatusDto> getEntryPassStatusReport(String unitId){
			return entrypassDao.getEntryPassStatusReport(unitId);
		}
	    
}
