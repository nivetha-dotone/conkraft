package com.wfd.dot1.cwfm.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wfd.dot1.cwfm.dto.ApproveRejectBillDto;
import com.wfd.dot1.cwfm.dto.ApproveRejectContRenewDto;
import com.wfd.dot1.cwfm.dto.ApproverStatusDTO;
import com.wfd.dot1.cwfm.dto.CMSWageCostDTO;
import com.wfd.dot1.cwfm.dto.ChecklistItemDTO;
import com.wfd.dot1.cwfm.dto.LicenseDto;
import com.wfd.dot1.cwfm.pojo.BillReportFile;
import com.wfd.dot1.cwfm.pojo.BillStatusLogDto;
import com.wfd.dot1.cwfm.pojo.BillVerification;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;

public interface BillVerificationDao {

	public List<CMSWageCostDTO> getBillVerificationList(String userId, String deptId, String unitId);

	public BillVerification viewbillDetails(String transactionId);

	public BillVerification viewbillReportsDetails(String transactionId);

	public BillVerification viewbillhrclearanceDetails(String transactionId);

	public BillVerification viewbillprecomments(String transactionId);

	public BillVerification viewbilleicDetails(String transactionId);

	String generateWCTransactionId();

	public List<PrincipalEmployer> getPEDetailByUser(String userAccount);

	public String saveBill(CMSWageCostDTO workflowData);

	public List<CMSWageCostDTO> getBillVerificationListForApprovers(String roleId, int workFlowType, String deptId,
			String principalEmployerId);

	public int getWorkflowType(String module, String unitId);

	public void saveBillStatusLog(BillStatusLogDto dto);

	String getActionIdForBill();

	CMSWageCostDTO getIndividualBVRDetails(String transactionId);

	public void saveFile(BillReportFile b);

	List<BillReportFile> findByTransactionIdAndType(Long transactionId, String reportType);

	void saveChecklist(List<ChecklistItemDTO> checklistItems, String wcTransId, String userId);

	List<ChecklistItemDTO> fetchChecklistByTransactionId(String wcTransId);

	public String approveRejectBill(ApproveRejectBillDto dto);

	public boolean updateBillStatusByTransactionId(String transactionId, String status);

	public int getWorkFlowTYpeByTransactionId(String transactionId);

	boolean isLastApproverForParallel(String transactionId, String roleId, String unitId);

	boolean isLastApprover(String roleName, String unitId);

	public List<LicenseDto> getLicensesByWorkOrder(String workOrder);

	public String getValidUptoByLicense(String licenseNumber);

	public List<ApproverStatusDTO> getBillApprovalDetails(String transactionId, String unitId, String gatePassTypeId);

	Long findContractorIdByUserAccount(String userAccount);

	List<String> findEntryPassNumbersNotBelongingToContractors(List<String> entryPassNumbers, List<Long> contractorIds);

	int saveWageCostReport(List<Map<String, Object>> reportRows, List<PersonOrgLevel> contList, int updatedBy);

	public Set<String> findExistingGatePassNumbers(List<String> entryPassNumbers, LocalDate startDate,
			LocalDate endDate);

	List<String> findExistingGatePassNumbersForRanges(List<Map<String, Object>> reportRows);

	void insertWageCostReportRows(List<Map<String, Object>> rows, String updatedBy);
}
