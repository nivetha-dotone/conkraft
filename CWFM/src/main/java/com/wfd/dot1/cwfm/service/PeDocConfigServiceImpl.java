package com.wfd.dot1.cwfm.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.PeDocConfigDao;
import com.wfd.dot1.cwfm.pojo.BillReportFile;
import com.wfd.dot1.cwfm.pojo.KronosReport;


@Service
public class PeDocConfigServiceImpl implements PeDocConfigService{
	@Autowired
    private PeDocConfigDao peDocdao;
	
	@Override
    public List<String> getAllPeDocuments() {
        return peDocdao.fetchPeDocuments();
    }
	
	 @Override
	    public void saveDocuments(List<String> reportNames) {
		 peDocdao.clearExisting(); // truncate or delete before saving new
	        for (String name : reportNames) {
	            if (!name.trim().isEmpty()) {
	            	peDocdao.saveDocument(name);
	            }
	        }
	    }

	
}
