package com.wfd.dot1.cwfm.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.DashboardDao;
import com.wfd.dot1.cwfm.pojo.MasterUser;
@Service
public class DashboardServiceImpl implements DashboardService{

	@Autowired
	DashboardDao dao;
	@Override
	public int getGatePassListingDetails(String unitId, String deptId, String gatePassTypeId, String type
			) {
		// TODO Auto-generated method stub
		return dao.getGatePassListingDetails(unitId,deptId,gatePassTypeId,type);
	}
	@Override
	public int getGatePassActionListingDetails(String unitId, String deptId, String gatePassTypeId
			) {
		// TODO Auto-generated method stub
		return dao.getGatePassActionListingDetails( unitId,  deptId,  gatePassTypeId
				 );
	}

	@Override
	public int getGatePassListingForApprovers(String unitId ,String deptId,MasterUser user,String gatePassTypeId,String type) {
		int workFlowTypeId = dao.getWorkFlowTYpeNew(unitId,gatePassTypeId);
			return dao.getGatePassListingForApprovers(user.getRoleId(),workFlowTypeId,gatePassTypeId,deptId,unitId,type);
	}
	
	@Override
	public int getGatePassActionListingForApprovers(String unitId ,String deptId,MasterUser user,String gatePassTypeId) {
		int workFlowTypeId = dao.getWorkFlowTYpeNew(unitId,gatePassTypeId);
			return dao.getGatePassActionListingForApprovers(user.getRoleId(),workFlowTypeId,gatePassTypeId,deptId,unitId);
	}
}
