package com.wfd.dot1.cwfm.dao;

import java.util.List;
import java.util.Map;

import com.wfd.dot1.cwfm.dto.ApproveRejectContRenewDto;
import com.wfd.dot1.cwfm.dto.CMSWageCostDTO;
import com.wfd.dot1.cwfm.pojo.CMSContrPemm;
import com.wfd.dot1.cwfm.pojo.CMSContractorRegistrationLLWC;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.ContractorComplianceDto;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
import com.wfd.dot1.cwfm.pojo.ContractorRegistrationPolicy;
import com.wfd.dot1.cwfm.pojo.ContractorRenewal;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.Workorder;

public interface ContractorDao {

	public Contractor getContractorById(String contractorId);

	public List<Workorder> getWorkOrdersByContractorIdAndUnitId(String contractorId, String unitId);

	public CMSContrPemm getMappingByContractorIdAndUnitId(String contractorId, String principalEmployerId);

	

	public List<CmsContractorWC> getcontrsByContractorIdAndUnitIdAndLicenseType(String contractorId,
			String principalEmployerId, String string);

	public List<CmsContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseTypes(String contractorId,
			String principalEmployerId, List<String> licenseTypes);

	public String saveReg(ContractorRegistration contreg);

	public ContractorRenewal getContractorDetails(String contractorRenewId);
	
	public List<ContractorRegistration> getContractorRegistrationList();

	public List<ContractorRegistration> getContractorRegistrationList(String contractorregId);
	
	public List<ContractorRegistration> getContractorRenewList(String contractorregId);

	public ContractorRegistration viewContractorDetails(String contractorregId);

	public List<ContractorRegistration> getContractorRenewalList(String contractorregId);

	public List<ContractorRegistrationPolicy> viewContractorAddDetails(String contractorregId);
	
	public ContractorRenewal viewContractorRenewDetails(String contractorRenewId);

	public List<ContractorRenewal> viewContractorRenewAddDetails(String contractorRenewId);

	public List<MasterUser> getRoleList(String userId);

	public String saveRole(MasterUser user);

	public String saveRenew(ContractorRenewal contrenew);

	public boolean checkIfIdExists(String contractorregId);

	public String generateContractorRegistrationId();

	public List<Contractor> getAllContractorForReg(String unitId);

	public Contractor getAllContractorDetailForReg(String unitId, String contractorId);

	public void savePolicies(List<ContractorRegistrationPolicy> policies,ContractorRegistration contreg);

	public List<Workorder> getAllWorkordersBasedOnPEAndContractor(String unitId, String contractorId);

	public List<ContractorRegistration> getContractorMasterExportData(String unitId);

	public List<ContractorComplianceDto> getContractorComplianceExportData(String unitId);

	ContractorRegistration getAllContractorDetailForRenewal(String unitId, String contractorId);

	ContractorRegistration getWOAndLicense(String contractorId, String contractorCode, String unitId, String regId);

	ContractorRegistration getContractorRegistration(String contractorRegId);

	List<ContractorRegistrationPolicy> getPoliciesByContractorRegId(String contractorRegId);

	List<CMSContractorRegistrationLLWC> getLLWCByContractorRegId(String contractorRegId);

	public List<ContractorRegistration> getContRenewList(String userId, String deptId, String principalEmployerId);

	
	int getWorkflowType(String module, String unitId);

	public List<ContractorRegistration> getContRenewListForApprovers(String roleId, int workFlowType, String deptId,
			String principalEmployerId);

	public String approveRejectContRenew(ApproveRejectContRenewDto dto);

	boolean updateContStatusByTransactionId(String transactionId, String status);

	int getWorkFlowTYpeByTransactionId(String transactionId);

	boolean isLastApprover(String roleName,String unitId);

	boolean isLastApproverForParallel(String transactionId, String roleId,String unitId);
	
	 void insertWorkOrderLLWC(String contractorRegId, String contractorId, String unitId, String createdBy);

	public void saveContractorPemm(ContractorRegistration reg);

	public void saveContractorWC(List<ContractorRegistrationPolicy> policy, ContractorRegistration gpm);

	public Map<String, String> getContractorPreviousDocuments(String contractorRegId, String requestType);

	public Contractor getAllContractorProfileDetailForReg(String unitId, String contractorId);

	public void updateContractorPemm(ContractorRegistration reg);

	public boolean contractorExistsForPeContractor(String contractorId, Long unitId);

	public ApproveRejectContRenewDto getContractorRenewComments(String contractorRegId);

	   
	}


