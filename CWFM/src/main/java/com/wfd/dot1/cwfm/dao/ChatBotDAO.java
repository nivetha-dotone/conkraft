package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.dto.ActiveContractorDTO;
import com.wfd.dot1.cwfm.dto.ContractorDTO;
import com.wfd.dot1.cwfm.dto.PrincipalEmployerDTO;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;



public interface ChatBotDAO {

    //Integer getPendingApprovalCount(String userId);

    //Integer getActiveContractorCount(String peId);

    Integer getTodayGatePassCount(String peId);

    Integer getWorkOrderCount(String peId);

    Integer getLicenseExpiryCount(String peId);

    List<PrincipalEmployerDTO> getPrincipalEmployers();

    ContractorDTO searchContractor(String contractorName);

	Integer getPendingApprovalCount(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList);

	//Integer getActiveContractorCount(List<PersonOrgLevel> peList);

	List<ActiveContractorDTO> getActiveContractors(List<PersonOrgLevel> peList);

}
