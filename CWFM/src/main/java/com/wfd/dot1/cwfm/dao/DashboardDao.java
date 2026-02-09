package com.wfd.dot1.cwfm.dao;

public interface DashboardDao {

	int getGatePassListingDetails(String unitId, String deptId, String gatePassTypeId, String type);

	int getGatePassActionListingDetails(String unitId, String deptId, String gatePassTypeId);

	int getWorkFlowTYpeNew(String principalEmployer, String gatePassAction);

	int getGatePassListingForApprovers(String roleId, int workFlowType, String gatePassTypeId, String deptId,
			String unitId, String type);

	int getGatePassActionListingForApprovers(String roleId, int workFlowType, String gatePassTypeId, String deptId,
			String unitId);

}
