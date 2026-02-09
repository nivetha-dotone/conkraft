package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.BillConfigDao;
import com.wfd.dot1.cwfm.dao.DotTypeDao;
import com.wfd.dot1.cwfm.enums.DotType;
import com.wfd.dot1.cwfm.pojo.MasterUser;

@Service
public class DotTypeServiceImpl implements DotTypeService {
	@Autowired
    private DotTypeDao dotTypedao;
	
	 @Override
	    public void saveWorkflowType(Long principalEmployerId,Long businessTypeId, int dotType, String createdBy) {
//		 Long buid = dotTypedao.findBuidByUnitId(principalEmployerId);
//		 if (buid == null) {
//			 throw new IllegalStateException("There is no Business Type for this Principal Employer");
//			 }
	        dotTypedao.insertWorkflowType(principalEmployerId, dotType, createdBy);
	    }

	 @Override
	 public Integer getMappedDotType(Long principalEmployerId, Long businessTypeId) {
		 Integer  workflowType = dotTypedao.getSelectedDotType(principalEmployerId);

		 if (workflowType == null) {
		        return null;
		    }

		    // Compare numeric status values against enum entries
		    for (DotType dt : DotType.values()) {
		        if (dt.getStatus() == workflowType.intValue()) {
		            return Integer.valueOf(dt.getStatus());
		        }
		    }

		    // No matching enum found
		    return null;
		}

	

}
