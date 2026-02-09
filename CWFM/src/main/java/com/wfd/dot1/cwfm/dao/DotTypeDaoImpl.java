package com.wfd.dot1.cwfm.dao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.enums.DotType;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.queries.WorkmenQueryBank;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Repository
public class DotTypeDaoImpl implements DotTypeDao {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public String findBuidByUnitId() {
	    return QueryFileWatcher.getQuery("FIND_BUID_BY_UNITID");
	}
	public String insertWorkflowType() {
	    return QueryFileWatcher.getQuery("INSERT_WORKFLOWTYPE");
	}
	public String getSelectedDotType() {
	    return QueryFileWatcher.getQuery("GET_SELECTED_DOT_TYPE");
	}
	 @Override
	    public Long findBuidByUnitId(Long unitId) {
		 String query = findBuidByUnitId();
		 //String sql = "SELECT BUID FROM CMSBUUnitMapping WHERE UnitID = ?";
		    try {
		        return jdbcTemplate.queryForObject(query, new Object[]{unitId}, Long.class);
		    } catch (EmptyResultDataAccessException e) {
		        return null;
		    }
	 }
	    @Override
	    public void insertWorkflowType(Long principalEmployerId, int workflowType, String createdBy) {
	    	String sql = "select  gwt.WorkflowType,gwt.WorkflowTypeId	from GATEPASSWORKFLOWTYPE gwt where  gwt.UnitID=? ";
	    	SqlRowSet rs = jdbcTemplate.queryForRowSet(sql,principalEmployerId);
			if(rs.next()) {// DOT already exists update otherwise insert
				String updateQuery = "update GATEPASSWORKFLOWTYPE set WorkflowType=? where WorkflowTypeId=?";
		       jdbcTemplate.update(updateQuery,  workflowType, rs.getInt("WorkflowTypeId"));
			}else {
				String query = insertWorkflowType();
		        jdbcTemplate.update(query, principalEmployerId, workflowType, createdBy, createdBy);
			}
	    	
	    }
	    @Override
	    public Integer  getSelectedDotType(Long principalEmployerId) {
	    	String sql = getSelectedDotType();
	        
	        List<Integer> results = jdbcTemplate.query(sql, new Object[]{ principalEmployerId},
	                (rs, rowNum) -> rs.getInt("WorkflowType"));

	            if (results.isEmpty()) {
	                return 0;
	            }

	            int workflowType = results.get(0);

	            // ✅ Map integer ID to enum name
	            for (DotType dotType : DotType.values()) {
	                if (dotType.getStatus() == workflowType) {
	                    return dotType.getStatus(); // returns the enum name like "LLWCWO"
	                }
	            }

	            return 0;
	        }

}
