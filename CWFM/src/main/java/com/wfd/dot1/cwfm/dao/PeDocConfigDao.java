package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.pojo.BillReportFile;

public interface PeDocConfigDao {
	void clearExisting();
	
	List<String> fetchPeDocuments();
	
    void saveDocument(String reportName);
    
	

}
