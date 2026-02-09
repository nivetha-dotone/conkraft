package com.wfd.dot1.cwfm.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.GatePassListingDto;
import com.wfd.dot1.cwfm.enums.GatePassStatus;
import com.wfd.dot1.cwfm.enums.GatePassType;
import com.wfd.dot1.cwfm.enums.WorkFlowType;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
@Repository
public class DashboardDaoImpl implements DashboardDao{

	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	
	 public String getDashboardCreationQueryForContractor() {
		    return QueryFileWatcher.getQuery("GET_ALL_GATE_PASS_COUNT_FOR_CREATOR");
		}
	 public String getDashboardGatePassActionQueryForContractor() {
		    return QueryFileWatcher.getQuery("GET_ALL_GATE_PASS_ACTION_COUNT_FOR_CREATOR");
		}
	 
	@Override
	public int getGatePassListingDetails(String unitId, String deptId, String gatePassTypeId, String type
			) {
		String query =getDashboardCreationQueryForContractor();
		int count=0;
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,gatePassTypeId,deptId,unitId,type,gatePassTypeId,type,deptId,unitId);
		while (rs.next()) {
			count = rs.getInt("count");
		}
		return count;
	}

	@Override
	public int getGatePassActionListingDetails(String unitId, String deptId, String gatePassTypeId
			) {
		String query =getDashboardGatePassActionQueryForContractor();
		int count=0;
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,deptId,unitId,gatePassTypeId);
		while (rs.next()) {
			count = rs.getInt("count");
		}
		return count;
	}
	public String getWorkflowTypeNew() {
	    return QueryFileWatcher.getQuery("GET_WORKFLOW_TYPE_BY_PE_ACTIONID");
	}
	@Override
	public int getWorkFlowTYpeNew(String principalEmployer,String gatePassAction) {
		int workflowTypeId = 0;
		String query = getWorkflowTypeNew();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,principalEmployer,gatePassAction);
		if(rs.next()) {
			workflowTypeId = rs.getInt("WorkflowType");
		}
		return workflowTypeId;
	}
	public String getAllGatePassForSquential() {
	    return QueryFileWatcher.getQuery("GET_ALL_GATEPASS_COUNT_FOR_SEQ_APPROVER");
	}
	
	public String getAllGatePassForParallel() {
	    return QueryFileWatcher.getQuery("GET_ALL_GATE_PASS_COUNT_FOR_PARALLEL_APPROVER");
	}
	@Override//
	public int getGatePassListingForApprovers(String roleId,int workFlowType,String gatePassTypeId,String deptId,String unitId,String type) {
		SqlRowSet rs =null;
		String query=null;
		int count=0;
		if(workFlowType == WorkFlowType.SEQUENTIAL.getWorkFlowTypeId()) {
			query=this.getAllGatePassForSquential();
			
			 rs = jdbcTemplate.queryForRowSet(query,deptId,unitId,roleId,gatePassTypeId,type);
		}else {
			query=this.getAllGatePassForParallel();
			 rs = jdbcTemplate.queryForRowSet(query,roleId,gatePassTypeId,roleId,gatePassTypeId,deptId,unitId,type);
		}
		
		while(rs.next()) {
			count = rs.getInt("count");
		}
		return count;
	}

	public String getAllGatePassActionForSquential() {
	    return QueryFileWatcher.getQuery("GET_ALL_GATE_PASS_ACTION_COUNT_FOR_SEQUENTIAL_APPROVER");
	}
	
	public String getAllGatePassActionForParallel() {
	    return QueryFileWatcher.getQuery("GET_ALL_GATE_PASS_ACTION_COUNT_FOR_PARALLEL_APPROVER");
	}
	@Override
	public int getGatePassActionListingForApprovers(String roleId,int workFlowType,String gatePassTypeId,String deptId,String unitId) {
		SqlRowSet rs =null;
		String query=null;
		int count=0;
		if(workFlowType == WorkFlowType.SEQUENTIAL.getWorkFlowTypeId()) {
			query=this.getAllGatePassActionForSquential();
			
			 rs = jdbcTemplate.queryForRowSet(query,gatePassTypeId,gatePassTypeId,gatePassTypeId,roleId,deptId,unitId);
		}else {
			query=this.getAllGatePassActionForParallel();
			 rs = jdbcTemplate.queryForRowSet(query,roleId,gatePassTypeId,roleId,gatePassTypeId,deptId,unitId);
		}
		
		while(rs.next()) {
			count = rs.getInt("count");
		}return count;
	}
}
