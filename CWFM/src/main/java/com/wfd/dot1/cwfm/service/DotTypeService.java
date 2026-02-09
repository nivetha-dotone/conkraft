package com.wfd.dot1.cwfm.service;

import com.wfd.dot1.cwfm.enums.DotType;
import com.wfd.dot1.cwfm.pojo.MasterUser;

public interface DotTypeService {

	 void saveWorkflowType(Long principalEmployerId,Long businessTypeId, int dotTypeId, String createdBy);

	Integer getMappedDotType(Long principalEmployerId, Long businessTypeId);
	

}
