package com.wfd.dot1.cwfm.dao;

import java.util.List;
import java.util.Set;

import com.wfd.dot1.cwfm.dto.GatepassEmailDTO;

public interface GatepassEmailDao {

	String getRegardsEmail();

	List<GatepassEmailDTO> getCreateApprovalPendingRecords();


	Set<String> getCreateApproverMails(String unitId, String unitCode);

}
