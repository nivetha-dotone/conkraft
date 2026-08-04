package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.dto.ActiveContractorDTO;
import com.wfd.dot1.cwfm.dto.ChatBotVideoDTO;
import com.wfd.dot1.cwfm.dto.ContractorDTO;
import com.wfd.dot1.cwfm.dto.LicenseExpiryDTO;
import com.wfd.dot1.cwfm.dto.PrincipalEmployerDTO;
import com.wfd.dot1.cwfm.dto.WorkOrderDTO;
import com.wfd.dot1.cwfm.pojo.GatePassMain;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;



public interface ChatBotDAO {

    //Integer getPendingApprovalCount(String userId);

    //Integer getActiveContractorCount(String peId);

    Integer getTodayGatePassCount(String peId);

    Integer getWorkOrderCount(String peId);

    Integer getLicenseExpiryCount(String peId);

    List<PrincipalEmployerDTO> getPrincipalEmployers(List<PersonOrgLevel> peList);

    ContractorDTO searchContractor(String contractorName);

	Integer getPendingApprovalCount(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList);

	//Integer getActiveContractorCount(List<PersonOrgLevel> peList);

	List<ActiveContractorDTO> getActiveContractors(List<PersonOrgLevel> peList);

	List<GatePassMain> getRenewPendingApprovals(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList);

	List<GatePassMain> getCancelPendingApprovals(List<PersonOrgLevel> peList ,List<PersonOrgLevel> contList);

	List<GatePassMain> getDeblacklistPendingApprovals(List<PersonOrgLevel> peList,List<PersonOrgLevel> contList);

	List<GatePassMain> getBlacklistPendingApprovals(List<PersonOrgLevel> peList,List<PersonOrgLevel> contList);

	List<GatePassMain> getUnblockPendingApprovals(List<PersonOrgLevel> peList,List<PersonOrgLevel> contList);

	List<GatePassMain> getBlockPendingApprovals(List<PersonOrgLevel> peList,List<PersonOrgLevel> contList);

	List<GatePassMain> getProjectPendingApprovals(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList);

	List<GatePassMain> getCreatePendingApprovals(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList);

	List<GatePassMain> getQuickPendingApprovals(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList);

	List<GatePassMain> getTodayGatePass(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList);

	List<WorkOrderDTO> getWorkOrderList(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList);

	List<LicenseExpiryDTO> getLicenseExpiryList(List<PersonOrgLevel> peList, List<PersonOrgLevel> contList);

	ChatBotVideoDTO getTrainingVideo(String string);

}
