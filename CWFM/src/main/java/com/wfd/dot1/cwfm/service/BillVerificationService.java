package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
import com.wfd.dot1.cwfm.pojo.ContractorRenewal;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Workorder;
import com.wfd.dot1.cwfm.dto.ApproveRejectBillDto;
import com.wfd.dot1.cwfm.dto.CMSWageCostDTO;
import com.wfd.dot1.cwfm.dto.ChecklistItemDTO;
import com.wfd.dot1.cwfm.pojo.BillReportFile;
import com.wfd.dot1.cwfm.pojo.BillVerification;

public interface BillVerificationService {

	public List<CMSWageCostDTO> getBillVerificationList(String userId, String deptId, String unitId) ;

	public BillVerification viewbillDetails(String transactionId);

	public BillVerification viewbillReportsDetails(String transactionId);

	public BillVerification viewbillhrclearanceDetails(String transactionId);
	
	public BillVerification viewbillprecomments(String transactionId);
		
	public BillVerification viewbilleicDetails(String transactionId);

	String generateWCTransactionId();

	public List<PrincipalEmployer> getPEDetailByUser(String userAccount);

	public String save(CMSWageCostDTO workflowData,  List<ChecklistItemDTO> listDto);

	public List<CMSWageCostDTO> getBillVerificationListForApprovers(String principalEmployerId, String deptId,
			MasterUser user);

	public CMSWageCostDTO getIndividualBillDetails(String transactionId);

	public void saveFile(BillReportFile b);

	public List<BillReportFile> findByTransactionIdAndType(String transactionId, String type);


	void saveChecklist(List<ChecklistItemDTO> checklistItems, String wcTransId, String userId);

	List<ChecklistItemDTO> getChecklistByTransactionId(String wcTransId);

	public String approveRejectBill(ApproveRejectBillDto dto);

}
