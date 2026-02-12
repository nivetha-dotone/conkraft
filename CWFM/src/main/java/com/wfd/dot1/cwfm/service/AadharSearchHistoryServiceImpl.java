package com.wfd.dot1.cwfm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.AadharSearchHistoryDao;
import com.wfd.dot1.cwfm.dao.BillVerificationDao;
import com.wfd.dot1.cwfm.dto.WorkmanFullHistoryDTO;
@Service
public class AadharSearchHistoryServiceImpl implements AadharSearchHistoryService{

	@Autowired
	AadharSearchHistoryDao aadharhistoryDao;

	 @Override
	    public WorkmanFullHistoryDTO getFullHistory(String aadhaar) {
	        return aadharhistoryDao.getFullHistory(aadhaar);
	    }
	
}
