package com.wfd.dot1.cwfm.dao;

import java.util.List;
import java.util.Map;

import com.wfd.dot1.cwfm.pojo.Workorder;

public interface WorkorderDao {

	Workorder getWorkorderById(String id);
	Map<String, Workorder> getWorkOrdersByCodes(List<String> cleanedCodes);
	
	List<Workorder> getWorkorderLicenseInfo(String id);
	List<Workorder> getWorkOrdersByContractorIdAndUnitId(String contractorId, String principalEmployerId);

}
