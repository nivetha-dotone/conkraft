package com.wfd.dot1.cwfm.service;

import com.wfd.dot1.cwfm.pojo.MasterUser;

public interface DashboardService {

	public int getGatePassListingDetails(String unitId,String deptId,String gatePassTypeId,String type);
	public int getGatePassActionListingDetails(String unitId,String deptId, String gatePassTypeId) ;//supervisor gatepass action approval pending,approved,rejected
	int getGatePassListingForApprovers(String unitId, String deptId, MasterUser user, String gatePassTypeId,
			String type);
	int getGatePassActionListingForApprovers(String unitId, String deptId, MasterUser user, String gatePassTypeId);
		
}
