package com.wfd.dot1.cwfm.service;

import java.util.List;

import com.wfd.dot1.cwfm.pojo.Workorder;

public interface WorkorderService {

	public Workorder getWorkorderById(String id);

	public List<Workorder> getWorkorderLicenseInfo(String id);

	public List<Workorder> getWorkOrdersByContractorIdAndUnitId(String contractorId, String principalEmployerId);

}
