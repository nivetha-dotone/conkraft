package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.WorkorderDao;
import com.wfd.dot1.cwfm.pojo.Workorder;
@Service
public class WorkorderServiceImpl implements WorkorderService{

	
	@Autowired
	WorkorderDao woDao;
	@Override
	public Workorder getWorkorderById(String id) {
		// TODO Auto-generated method stub
		return woDao.getWorkorderById(id);
	}
	@Override
	public List<Workorder> getWorkorderLicenseInfo(String id) {
		// TODO Auto-generated method stub
		return woDao.getWorkorderLicenseInfo(id);
	}
	@Override
	public List<Workorder> getWorkOrdersByContractorIdAndUnitId(String contractorId, String principalEmployerId) {
		// TODO Auto-generated method stub
		return woDao.getWorkOrdersByContractorIdAndUnitId( contractorId,  principalEmployerId);
	}

}
