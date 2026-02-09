package com.wfd.dot1.cwfm.dao;

import com.wfd.dot1.cwfm.pojo.MasterUser;

public interface DotTypeDao {

	    Long findBuidByUnitId(Long principalEmployerId);
	    void insertWorkflowType(Long principalEmployerId, int workflowType, String createdBy);
	    
	    Integer   getSelectedDotType(Long principalEmployerId);
		
}
