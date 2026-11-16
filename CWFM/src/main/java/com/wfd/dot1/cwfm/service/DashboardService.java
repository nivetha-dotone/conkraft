package com.wfd.dot1.cwfm.service;

import java.util.List;

import com.wfd.dot1.cwfm.dto.ContractorComplianceScoreDTO;
import com.wfd.dot1.cwfm.dto.DashboardDTO;
import com.wfd.dot1.cwfm.dto.WorkOrderDTO;
import com.wfd.dot1.cwfm.pojo.MasterUser;

public interface DashboardService {

    DashboardDTO getDashboard(MasterUser user);

    List<WorkOrderDTO> getWorkmenByWO(long woId, String contractorId);

	List<String> getAllowedPagesForRole(String roleId);
	List<ContractorComplianceScoreDTO> getContractorComplianceScores(
	        MasterUser user, int year, int month);
    
}