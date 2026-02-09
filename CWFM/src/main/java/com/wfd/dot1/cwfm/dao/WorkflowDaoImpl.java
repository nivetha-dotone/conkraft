package com.wfd.dot1.cwfm.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.ApproverDto;
import com.wfd.dot1.cwfm.dto.WorkflowRequestDto;
import com.wfd.dot1.cwfm.dto.WorkflowResponseDto;
import com.wfd.dot1.cwfm.enums.GatePassType;
import com.wfd.dot1.cwfm.enums.WorkFlowType;
import com.wfd.dot1.cwfm.pojo.BusinessType;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
@Repository
public class WorkflowDaoImpl implements WorkflowDao{

	private static final Logger log = LoggerFactory.getLogger(WorkflowDaoImpl.class.getName());
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	 public String getAllBusinessType() {
		    return QueryFileWatcher.getQuery("GET_ALL_BT");
		}
	 
	 public String getExistingWorkflowAndHierarchy() {
		    return QueryFileWatcher.getQuery("GET_EXISTING_WORKFLOW_AND_HIERARCHY");
		}
	 
	 
	@Override
	public List<BusinessType> getAllBusinessType(String unitId) {
		log.info("Entering into getAllBusinessType dao method "+unitId);
		List<BusinessType> btList= new ArrayList<BusinessType>();
		String query=getAllBusinessType();
		log.info("Query to getAllBusinessType "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId);
		while(rs.next()) {
			BusinessType bt =new BusinessType();
			bt.setBuId(rs.getInt("BUId"));
			bt.setBuName(rs.getString("BUName"));
			btList.add(bt);
		}
		log.info("Exiting from getAllBusinessType dao method "+btList.size());
		return btList;
	}
	
	@Override
	public WorkflowResponseDto fetchWorkflow(String unitId, String businessType, String moduleId,String actionName) {
	    String query = getExistingWorkflowAndHierarchy();

	   // SqlRowSet rs = jdbcTemplate.queryForRowSet(query, unitId, businessType, moduleId,actionName);
	    SqlRowSet rs = jdbcTemplate.queryForRowSet(query, unitId, moduleId,actionName);

	    WorkflowResponseDto response = new WorkflowResponseDto();
	    List<ApproverDto> approvers = new ArrayList<>();

	    boolean isFirstRow = true;

	    while (rs.next()) {
	        if (isFirstRow) {
	            response.setWorkflowType(rs.getInt("WORKFLOWTYPE"));
	            isFirstRow = false;
	        }

	        String actionId = rs.getString("ACTION_ID");
	        String roleId = rs.getString("ROLE_ID");

	        // Only add approver if both action and role IDs exist (could be null if no approvers yet)
	        if (actionId != null && !actionId.isEmpty() && roleId != null && !roleId.isEmpty()) {
	            ApproverDto dto = new ApproverDto();
	            dto.setActionId(actionId);
	            dto.setActionName(rs.getString("ACTION_NAME"));
	            dto.setRoleId(roleId);
	            dto.setRoleName(rs.getString("ROLE_NAME"));
	            dto.setHierarchy(rs.getInt("HIERARCHYINDEX"));
	            approvers.add(dto);
	        }
	    }

	    response.setHierarchy(approvers);
	    return response;
	}
	
	@Override
	public void saveWorkflow(WorkflowRequestDto request) {
        // Check if workflow already exists
		/*
		 * String checkQuery = "SELECT w.WorkflowTypeId FROM CMSWORKFLOWTYPE  w" +
		 * "			join CMSAPPROVERHIERARCHY a on a.workflowtypeid = w.workflowtypeid"
		 * +
		 * "		WHERE w.UNITID = ? AND w.BUSINESSTYPEID = ? AND  w.MODULEID =? AND a.Action_Name=?"
		 * ;
		 */
        
        String checkQuery = "SELECT w.WorkflowTypeId FROM CMSWORKFLOWTYPE  w"
        		+ "			join CMSAPPROVERHIERARCHY a on a.workflowtypeid = w.workflowtypeid"
        		+ "		WHERE w.UNITID = ?  AND  w.MODULEID =? AND a.Action_Name=?";

        //SqlRowSet rs = jdbcTemplate.queryForRowSet(checkQuery, request.getUnitId(), request.getBusinessType(), request.getModuleId(),request.getActionName());
        
        SqlRowSet rs = jdbcTemplate.queryForRowSet(checkQuery, request.getUnitId(),  request.getModuleId(),request.getActionName());

        int workflowTypeId;
        if (rs.next()) {
            workflowTypeId = rs.getInt("WorkflowtypeId");
            // Update existing workflow type
            String updateQuery = "UPDATE CMSWORKFLOWTYPE SET WORKFLOWTYPE = ? WHERE WorkflowTypeId = ?";
            jdbcTemplate.update(updateQuery, request.getWorkflowType(), workflowTypeId);

            // Delete existing approvers
            jdbcTemplate.update("DELETE FROM CMSAPPROVERHIERARCHY WHERE WORKFLOWTYPEID = ?", workflowTypeId);
        } else {
        	// Insert new workflow type and retrieve generated ID
        	//String insertQuery = "INSERT INTO CMSWORKFLOWTYPE (UNITID, BUSINESSTYPEID, MODULEID, WORKFLOWTYPE,UpdatedBy,UpdatedDate) VALUES (?, ?, ?, ?,?,GETDATE())";
        	
        	String insertQuery = "INSERT INTO CMSWORKFLOWTYPE (UNITID, MODULEID, WORKFLOWTYPE,UpdatedBy,UpdatedDate) VALUES (?, ?, ?,?,GETDATE())";
        	KeyHolder keyHolder = new GeneratedKeyHolder();
        	jdbcTemplate.update(connection -> {
        	    PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
        	    ps.setString(1, request.getUnitId());
        	   // ps.setString(2, request.getBusinessType());
        	    ps.setString(2, request.getModuleId());
        	    ps.setInt(3, request.getWorkflowType());
        	    ps.setString(4, request.getUserId());
        	    return ps;
        	}, keyHolder);

        	workflowTypeId = keyHolder.getKey().intValue();
        	 
        }

        
     // Map action names to their corresponding status
        Map<String, String> actionStatusMap = new HashMap<>();
        actionStatusMap.put(GatePassType.CREATE.getName(), GatePassType.CREATE.getStatus());
        actionStatusMap.put(GatePassType.RENEW.getName(), GatePassType.RENEW.getStatus());
        actionStatusMap.put(GatePassType.CANCEL.getName(), GatePassType.CANCEL.getStatus());
        actionStatusMap.put(GatePassType.BLOCK.getName(), GatePassType.BLOCK.getStatus());
        actionStatusMap.put(GatePassType.UNBLOCK.getName(), GatePassType.UNBLOCK.getStatus());
        actionStatusMap.put(GatePassType.BLACKLIST.getName(), GatePassType.BLACKLIST.getStatus());
        actionStatusMap.put(GatePassType.DEBLACKLIST.getName(), GatePassType.DEBLACKLIST.getStatus());
        actionStatusMap.put(GatePassType.LOSTORDAMAGE.getName(), GatePassType.LOSTORDAMAGE.getStatus());
        actionStatusMap.put(GatePassType.BILLVERIFICATION.getName(), GatePassType.BILLVERIFICATION.getStatus());
        actionStatusMap.put(GatePassType.CONTRACTORRENEWAL.getName(), GatePassType.CONTRACTORRENEWAL.getStatus());
        actionStatusMap.put(GatePassType.PROJECT.getName(), GatePassType.PROJECT.getStatus());
     // Insert approver hierarchy
        String insertApprover = "INSERT INTO CMSAPPROVERHIERARCHY (WORKFLOWTYPEID, ACTION_ID, ACTION_NAME,ROLE_ID,ROLE_NAME, [INDEX]) VALUES (?, ?, ?, ?,?,?)";
       
        if(request.getWorkflowType() == WorkFlowType.AUTO.getWorkFlowTypeId()) {
        	ApproverDto approver = new ApproverDto();
        	// Get action ID from map using the action name
            String actionId = actionStatusMap.get(request.getActionName());
            if (actionId != null) {
                approver.setActionId(actionId);
            }else {
            	approver.setActionId(request.getActionId());
            }
        	approver.setActionName(request.getActionName());
        	approver.setRoleId("0");
        	approver.setRoleName("NA");
        	approver.setHierarchy(0);
        	jdbcTemplate.update(insertApprover, workflowTypeId, approver.getActionId(),approver.getActionName() ,
            		approver.getRoleId(), approver.getRoleName(),approver.getHierarchy());
        }else {
         for (ApproverDto approver : request.getHierarchy()) {
        	// Get action ID from map using the action name
            String actionId = actionStatusMap.get(approver.getActionName());
            if (actionId != null) {
                approver.setActionId(actionId);
            }else {
            	approver.setActionId(request.getActionId());
            }
            jdbcTemplate.update(insertApprover, workflowTypeId, approver.getActionId(),approver.getActionName() ,
            		approver.getRoleId(), approver.getRoleName(),approver.getHierarchy());
        }
        }
        
    
    }


}
