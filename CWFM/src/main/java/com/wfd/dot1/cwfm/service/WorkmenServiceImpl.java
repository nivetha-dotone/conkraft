package com.wfd.dot1.cwfm.service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.wfd.dot1.cwfm.controller.CreateEmpFetchByGatePassAPICALL;
import com.wfd.dot1.cwfm.dao.WorkmenDao;
import com.wfd.dot1.cwfm.dto.ApproveRejectGatePassDto;
import com.wfd.dot1.cwfm.dto.ApproverStatusDTO;
import com.wfd.dot1.cwfm.dto.CMSPerson;
import com.wfd.dot1.cwfm.dto.GatePassActionDto;
import com.wfd.dot1.cwfm.dto.GatePassListingDto;
import com.wfd.dot1.cwfm.dto.GatePassStatusLogDto;
import com.wfd.dot1.cwfm.dto.PersonStatusIds;
import com.wfd.dot1.cwfm.enums.DotType;
import com.wfd.dot1.cwfm.enums.GatePassStatus;
import com.wfd.dot1.cwfm.enums.GatePassType;
import com.wfd.dot1.cwfm.enums.WorkFlowType;
import com.wfd.dot1.cwfm.pojo.CmsContractorWC;
import com.wfd.dot1.cwfm.pojo.CmsGeneralMaster;
import com.wfd.dot1.cwfm.pojo.ContractWorkmenExportDto;
import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.DeptMapping;
import com.wfd.dot1.cwfm.pojo.GatePassMain;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Skill;
import com.wfd.dot1.cwfm.pojo.Trade;
import com.wfd.dot1.cwfm.pojo.WageDetailsDto;
import com.wfd.dot1.cwfm.pojo.Workorder;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Service
public class WorkmenServiceImpl implements WorkmenService{

	private static final Logger log = LoggerFactory.getLogger(WorkmenServiceImpl.class);

	@Autowired
	WorkmenDao workmenDao;
	
	
	@Override
	public List<PrincipalEmployer> getAllPrincipalEmployer(String userAccount) {
		
		return workmenDao.getAllPrincipalEmployer(userAccount);
	}
	@Override
	public List<Contractor> getAllContractorBasedOnPE(String unitId,String userAccount) {
		
		return workmenDao.getAllContractorBasedOnPE(unitId, userAccount);
	}
	@Override
	public List<Workorder> getAllWorkordersBasedOnPEAndContractor(String unitId, String contractorId) {
		return workmenDao.getAllWorkordersBasedOnPEAndContractor(unitId, contractorId);
	}
	@Override
	public List<Trade> getAllTradesBasedOnPE(String unitId) {
		return workmenDao.getAllTradesBasedOnPE(unitId);
	}
	@Override
	public List<Skill> getAllSkill() {
		return workmenDao.getAllSkill();
	}
	@Override
	public List<CmsGeneralMaster> getAllDepartmentAndSubDepartment(String userId) {
		return workmenDao.getAllDepartmentAndSubDepartment(userId);
	}
	@Override
	public List<MasterUser> getAllEicManager(String unitId,String deptId) {
		return workmenDao.getAllEicManager(unitId,deptId);
	}
	@Override
	public List<CmsContractorWC> getAllWCBasedOnPEAndCont(String unitId, String contractorId,String workorderId) {
		return workmenDao.getAllWCBasedOnPEAndCont(unitId, contractorId,workorderId);
	}
	@Override
	public List<CmsGeneralMaster> getAllGenders() {
		return workmenDao.getAllGenders();
	}
	@Override
	public List<CmsGeneralMaster> getAllGeneralMaster() {
		return workmenDao.getAllGeneralMasters();
	}

//	@Autowired
//	private EmployeeMapper employeeMapper;
//
//	@Autowired
//	private  WfdEmployeeService wfdEmployeeService;
	
	@Autowired
	CreateEmpFetchByGatePassAPICALL api;

	
	public String getWFDIntegration() {
		return QueryFileWatcher.getQuery("WFD_INTEGRATION");
	}
	@Override
	public String saveGatePass(GatePassMain gatePassMain) {
		String transactionId =null;
		
		try {
	
			String allowOnboarding = this.workmenCountCheck(gatePassMain);
			if("allow".equals(allowOnboarding)) {
			int workFlowTypeId = workmenDao.getWorkFlowTYpeNew(gatePassMain.getPrincipalEmployer(),GatePassType.CREATE.getStatus());
			gatePassMain.setWorkFlowType(workFlowTypeId);
			
			int dotTypeId = workmenDao.getDOTTYpe(gatePassMain.getPrincipalEmployer());
			gatePassMain.setDotType(dotTypeId);
			
			String dot = this.getDOT(gatePassMain);
			gatePassMain.setDot(dot);
			gatePassMain.setGatePassAction(GatePassType.CREATE.getStatus());
			if(workFlowTypeId == WorkFlowType.AUTO.getWorkFlowTypeId()) {
				gatePassMain.setGatePassStatus(GatePassStatus.APPROVED.getStatus());

				 transactionId = workmenDao.saveGatePass(gatePassMain);

//				EmployeeRequestDTO employeeDTO = employeeMapper.mapFromGatePass(gatePassMain);
//				String wfdResponse = wfdEmployeeService.createEmployee(employeeDTO);
//				log.info("WFD Response: {}", wfdResponse);



				 String gatePassId = workmenDao.updateGatePassIdByTransactionId(transactionId);
				gatePassMain.setGatePassId(gatePassId);
				GatePassStatusLogDto dto =new GatePassStatusLogDto();
				dto.setTransactionId(transactionId);
				dto.setGatePassId(gatePassId);
				dto.setGatePassType(GatePassType.CREATE.getStatus());
				dto.setStatus(Integer.parseInt(GatePassStatus.APPROVED.getStatus()));
				dto.setComments(gatePassMain.getComments());
				dto.setUpdatedBy(gatePassMain.getUserId());
				workmenDao.saveGatePassStatusLog(dto);
				
				boolean cmsDone = this.cmsPersonInsert(gatePassMain);

		        if (!cmsDone) {
		            throw new RuntimeException("CMS Person Insert failed unexpectedly.");
		        }try {
		        	String wfdIntegration = this.getWFDIntegration();
		        	if("yes".equalsIgnoreCase(wfdIntegration)) {
		        		api.addOnBoardingDetailsActual(dto.getTransactionId());
		        	}
		        	}catch(Exception e) {return transactionId;}

				return transactionId;
			}else {
				gatePassMain.setGatePassStatus(GatePassStatus.APPROVALPENDING.getStatus());
				transactionId = workmenDao.saveGatePass(gatePassMain);
				gatePassMain.setGatePassId(" ");
				GatePassStatusLogDto dto =new GatePassStatusLogDto();
				dto.setTransactionId(transactionId);
				dto.setGatePassId(" ");
				dto.setGatePassType(GatePassType.CREATE.getStatus());
				dto.setStatus(Integer.parseInt(GatePassStatus.APPROVED.getStatus()));
				dto.setComments(gatePassMain.getComments());
				dto.setUpdatedBy(gatePassMain.getUserId());
				workmenDao.saveGatePassStatusLog(dto);
				return transactionId;
				
			}
			}else {
				return allowOnboarding;
			}
		}catch(Exception e) {
			
		}
		return transactionId;
	}
	
	public String workmenCountCheck(GatePassMain gatePassMain) {
		int activeCount = workmenDao.getActiveWorkmenCount(gatePassMain.getPrincipalEmployer(), gatePassMain.getContractor(), 
				GatePassStatus.APPROVED.getStatus(), GatePassType.CREATE.getStatus());
		int llDeployedCount = workmenDao.getLLDeploymentCountByUnitId(gatePassMain.getPrincipalEmployer());
		if(activeCount < llDeployedCount) {
			return "allow";
		}else {
			return this.licenseExistsAndCount(gatePassMain, activeCount);
			
		}
	}
//	public String licenseExistsAndCount(GatePassMain gatePassMain, int activeCount) {
//
//	    String unit = gatePassMain.getPrincipalEmployer();
//	    String contractor = gatePassMain.getContractor();
//	    String workorder = gatePassMain.getWorkorder();
//	    
//	    
//
//	    Map<String, Object> llResult =workmenDao.licenseExistsAndCount(unit, contractor, workorder, "LL", gatePassMain.getLlNo());
//	    Map<String, Object> wcResult = workmenDao.licenseExistsAndCount(unit, contractor, workorder, "WC", gatePassMain.getWcEsicNo());
//
//	    // --- Step 1: Mandatory validation ---
//	    boolean llMandatory = (llCount == 0);
//	    boolean wcMandatory = (wcCount == 0);
//
//	    if (llMandatory || wcMandatory) {
//	        if (llMandatory && wcMandatory) {
//	            return "LL and WC/ESIC are mandatory";
//	        }
//	        if (llMandatory) {
//	            return "LL is mandatory";
//	        }
//	        // wcMandatory must be true here
//	        return "WC/ESIC is mandatory";
//	    }
//
//	    // --- Step 2: Capacity check ---
//	    boolean llExceeded = activeCount >= llCount;
//	    boolean wcExceeded = activeCount >= wcCount;
//
//	    if (llExceeded || wcExceeded) {
//	        if (llExceeded && wcExceeded) {
//	            return "LL and WC exceeded";
//	        }
//	        if (llExceeded) {
//	            return "LL exceeded";
//	        }
//	        return "WC exceeded";
//	    }
//
//	    // --- Step 3: All good ---
//	    return "allow";
//	}

	@Override
	public List<GatePassListingDto> getGatePassListingDetails(String unitId,String deptId,String userId,String gatePassTypeId,String type) {
		return workmenDao.getGatePassListingDetails(unitId,deptId,userId,gatePassTypeId,type);
	}
	@Override
	public List<GatePassListingDto> getGatePassListingForApprovers(String unitId ,String deptId,MasterUser user,String gatePassTypeId,String type) {
		int workFlowTypeId = workmenDao.getWorkFlowTYpeNew(unitId,gatePassTypeId);
			return workmenDao.getGatePassListingForApprovers(user.getRoleId(),workFlowTypeId,gatePassTypeId,deptId,unitId,type);
	}
	@Override
	public GatePassMain getIndividualContractWorkmenDetails(String transactionId) {
		return workmenDao.getIndividualContractWorkmenDetails(transactionId);
	}
	@Override
	public GatePassMain getIndividualContractWorkmenDetailsByGatePassId(String gatepassid) {
		return workmenDao.getIndividualContractWorkmenDetailsByGatePassId(gatepassid);
	}
	
	@Override
	public List<CmsGeneralMaster> getAllGeneralMasterForGatePass(GatePassMain gatePassMainObj) {
		return workmenDao.getAllGeneralMastersForGatePass(gatePassMainObj);
	}
	@Override
	@Transactional
	public String approveRejectGatePass(ApproveRejectGatePassDto dto) {
		
		if((dto.getGatePassType().equals(GatePassType.CREATE.getStatus()) || dto.getGatePassType().equals(GatePassType.RENEW.getStatus()) ) 
				&& dto.getStatus().equals(GatePassStatus.APPROVED.getStatus())) {
			 GatePassMain gatePassMain = workmenDao.getActiveCountDetails(dto.getTransactionId());
			String allowOnboarding = this.workmenCountCheck(gatePassMain);
			if(!"allow".equals(allowOnboarding)) {
				return allowOnboarding;
			}
		}

	    // Load GatePassMain based on type (CREATE vs others)
	    GatePassMain gpm =( dto.getGatePassType().equals(GatePassType.CREATE.getStatus()) || dto.getGatePassType().equals(GatePassType.PROJECT.getStatus()))
	            ? workmenDao.getIndividualContractWorkmenDetailsByTransId(dto.getTransactionId())
	            : workmenDao.getIndividualContractWorkmenDetailsByGatePassIdForApprove(dto.getGatePassId());

	    if (gpm == null) {
	        throw new RuntimeException("GatePass details not found for approval.");
	    }

	    // Save Approve/Reject Action
	    String result = workmenDao.approveRejectGatePass(dto);

	    // Log status change
	    GatePassStatusLogDto statusLog = new GatePassStatusLogDto();
	    statusLog.setTransactionId(dto.getTransactionId());
	    statusLog.setGatePassId(dto.getGatePassId());
	    statusLog.setGatePassType(dto.getGatePassType());
	    statusLog.setStatus(Integer.parseInt(dto.getStatus()));
	    statusLog.setComments(dto.getComments());
	    statusLog.setUpdatedBy(dto.getApproverId());
	    workmenDao.saveGatePassStatusLog(statusLog);

	    // Handle REJECT first
	    if (dto.getStatus().equals(GatePassStatus.REJECTED.getStatus())) {

	        boolean updated;
	        
	        if (gpm.getGatePassAction().equals(GatePassType.CREATE.getStatus()) || gpm.getGatePassAction().equals(GatePassType.PROJECT.getStatus())) {
	            // Rejecting CREATE → update by transactionId
	            updated = workmenDao.updateGatePassMainStatusByTransactionId(
	                    dto.getTransactionId(), dto.getStatus());
	        } else {
	            // Rejecting other actions → revert to CREATE + Approved
	            updated = workmenDao.updateGatePassMainStatusAndType(
	                    dto.getGatePassId(),
	                    GatePassStatus.APPROVED.getStatus(),
	                    GatePassType.CREATE.getStatus()
	            );
	        }

	        if (!updated) {
	            throw new RuntimeException("Failed to update GatePass status on reject.");
	        }

	        return result;
	    }

	    // Approve Flow
	    boolean isLastApprover;
	    //int workFlowType = workmenDao.getWorkFlowTYpeByTransactionId(gpm.getTransactionId(), dto.getGatePassType());
	    int workFlowType = workmenDao.getWorkFlowTYpeNew(gpm.getUnitId(), dto.getGatePassType());

	    if (workFlowType == WorkFlowType.SEQUENTIAL.getWorkFlowTypeId()) {
	    	int workflowTypeId = workmenDao.getWorkFlowTypeId(gpm.getUnitId(), dto.getGatePassType());
	        isLastApprover = workmenDao.isLastApprover(dto.getApproverRole(), dto.getGatePassType(),workflowTypeId);
	    } else {
	        // Parallel approval
	        isLastApprover =( dto.getGatePassType().equals(GatePassType.CREATE.getStatus()) || dto.getGatePassType().equals(GatePassType.PROJECT.getStatus()))
	                ? workmenDao.isLastApproverForParallel(dto.getGatePassType(), dto.getTransactionId(), dto.getRoleId(),gpm.getUnitId())
	                : workmenDao.isLastApproverForParallelGatePassAction(dto.getGatePassType(), dto.getGatePassId(), dto.getRoleId(),gpm.getUnitId());
	    }

	    // If not last approver — don't perform final action
	    if (!isLastApprover) {
	        return result;
	    }

	    // LAST APPROVER LOGIC
	    String gatePassId = dto.getGatePassId();

	    // Case: UNBLOCK / DEBLACKLIST → revert type to CREATE
	    if (dto.getGatePassType().equals(GatePassType.UNBLOCK.getStatus())
	            || dto.getGatePassType().equals(GatePassType.DEBLACKLIST.getStatus())) {

	        boolean updated = workmenDao.updateGatePassMainStatusAndType(
	                dto.getGatePassId(),
	                dto.getStatus(),
	                GatePassType.CREATE.getStatus()
	        );

	        if (!updated) {
	            throw new RuntimeException("Failed to update GatePass on UNBLOCK/DEBLACKLIST.");
	        }

	        return result;
	    }

	    // Case: CREATE → insert CMS Person
	    if (dto.getGatePassType().equals(GatePassType.CREATE.getStatus())) {

	        gatePassId = workmenDao.updateGatePassIdByTransactionId(dto.getTransactionId());
	        gpm.setGatePassId(gatePassId);

	        boolean cmsDone = this.cmsPersonInsert(gpm);

	        if (!cmsDone) {
	            throw new RuntimeException("CMS Person Insert failed unexpectedly.");
	        }
	        boolean statusUpdated  = workmenDao.updateGatePassMainStatus(gatePassId,dto.getStatus());
	        if(!statusUpdated) {
	        	throw new RuntimeException("Gatepassmain status update failed unexpectedly.");
	        }else {
	        	try {
	        	String wfdIntegration = this.getWFDIntegration();
	        	if("yes".equalsIgnoreCase(wfdIntegration)) {
	        		api.addOnBoardingDetailsActual(dto.getTransactionId());
	        	}
	        	}catch(Exception e) {return result;}
	        }
	        return result;
	    }
	    if (dto.getGatePassType().equals(GatePassType.PROJECT.getStatus())) {
	    	   gatePassId = workmenDao.updateGatePassIdByTransactionId(dto.getTransactionId());
		        gpm.setGatePassId(gatePassId);
		        boolean statusUpdated  = workmenDao.updateGatePassMainStatus(gatePassId,dto.getStatus());
		        if(!statusUpdated) {
		        	throw new RuntimeException("Gatepassmain status update failed unexpectedly.");
		        }else {
		        	return result;
		        }
	    }

	    // Case: BLOCK, BLACKLIST, CANCEL → perform person action
	    if (dto.getGatePassType().equals(GatePassType.BLOCK.getStatus())
	            || dto.getGatePassType().equals(GatePassType.BLACKLIST.getStatus())
	            || dto.getGatePassType().equals(GatePassType.CANCEL.getStatus())) {

	        boolean actionDone = this.gatePassActionPersonInsert(gpm, dto.getGatePassType());

	        if (!actionDone) {
	            throw new RuntimeException("GatePass action insert failed unexpectedly.");
	        }

	        boolean statusUpdated  = workmenDao.updateGatePassMainStatus(gatePassId,dto.getStatus());
	        if(!statusUpdated) {
	        	throw new RuntimeException("Gatepassmain status update failed unexpectedly.");
	        }
	        return result;
	    } if (dto.getGatePassType().equals(GatePassType.RENEW.getStatus())) {
	    	try {
	        	String wfdIntegration = this.getWFDIntegration();
	        	if("yes".equalsIgnoreCase(wfdIntegration)) {
	        		api.updateOnBoardingDetails(dto.getTransactionId());
	        	}
	        	}catch(Exception e) {return result;}

	    }

	    // OTHER APPROVALS → just update GatePass status
	    boolean statusUpdated = workmenDao.updateGatePassMainStatus(
	            gatePassId, dto.getStatus());

	    if (!statusUpdated) {
	        throw new RuntimeException("Failed to update GatePass status on final approval.");
	    }

	    return result;
	}
	@Transactional
	public boolean cmsPersonInsert(GatePassMain gpm) {
    
	    long personId = saveIntoCMSPerson(gpm);
	    if (!logAndCheck("CMSPERSON", personId > 0)) return false;

	    if (!logAndCheck("CMSPERSONJOBHIST", saveIntoCMSPERSONJOBHIST(gpm, personId)))
	        return false;

	    if (!logAndCheck("CMSPERSONSTATUSMM", saveCMSPERSONSTATUSMM(gpm, personId)))
	        return false;

	    gpm.setGatePassStatus(GatePassStatus.APPROVED.getStatus());

	    return logAndCheck("CMSPERSONCUSTOMDATA", saveCMSPERSONCUSTOMDATA(gpm, personId));
 }
	@Transactional(rollbackFor = Exception.class)
	public boolean gatePassActionPersonInsert(GatePassMain gpm, String gatePassType) {

	    long personId = getPersonIdFromCmsPerson(gpm.getGatePassId());
	    if (personId <= 0) return false;

	    // Step 1: Close existing CUSTDATA rows
	    if (!logAndCheck("CUSTDATA_UPDATE",
	            workmenDao.updateCmsPersonCustDataEffectiveTill(personId)))
	        return false;

	    // Step 2: Insert new CUSTDATA row
	    if (!logAndCheck("CUSTDATA_INSERT",
	            insertCustData(gpm.getCreatedBy(), personId, gatePassType,gpm.getReasoning())))
	        return false;

	    // Step 3: Update StatusMM only if active
	    if (workmenDao.isPersonActiveInStatusMM(personId)) {

	        PersonStatusIds ids = workmenDao.getPersonStatusIds(personId);

	        if (ids.getActiveId() != null && ids.getInactiveId() != null) {

	            boolean statusUpdated =
	                    workmenDao.updatePersonStatusValidity(ids.getActiveId(), ids.getInactiveId());

	            if (!logAndCheck("STATUSMM_UPDATE", statusUpdated))
	                return false;
	        }
	    }

	    return true;
	}
	private boolean logAndCheck(String label, boolean success) {
	    log.info(label + " : " + (success ? "SUCCESS" : "FAILED"));
	    return success;
	}

	
	
	private boolean insertCustData(String createdBy, long personId, String gatePassType,String reasoning) {

	    GatePassType type = GatePassType.fromStatus(gatePassType);
	    if (type == null) {
	        log.info("Unknown GatePassType: " + gatePassType);
	        return false;
	    }

	    return workmenDao.insertIntoCustData(createdBy, personId, type.getStatus(),reasoning);
	}

	private void log(String section, boolean status) {
	    System.out.println("Insert into " + section + " result: " + status);
	}
	private long getPersonIdFromCmsPerson(String gatePassId) {
		// TODO Auto-generated method stub
		return workmenDao.getPersonIdFromCmsPerson(gatePassId);
	}
	private boolean saveCMSPERSONCUSTOMDATA(GatePassMain gpm, long personInsert) {
		// TODO Auto-generated method stub
		return workmenDao.saveCMSPERSONCUSTDATA(gpm, personInsert);
	}
	@Override
	public String gatePassAction(GatePassActionDto dto) {
	String result=null;
		try {
			
			//approver logic needs to be added
			 GatePassMain gatePassMain = workmenDao.getIndividualContractWorkmenDetailsByGatePassId(dto.getGatePassId());

			if((gatePassMain.getGatePassAction().equals(GatePassType.CREATE.getStatus()) && gatePassMain.getGatePassStatus().equals(GatePassStatus.APPROVED.getStatus()))
					||(gatePassMain.getGatePassAction().equals(GatePassType.RENEW.getStatus()) && gatePassMain.getGatePassStatus().equals(GatePassStatus.APPROVED.getStatus()))
					||dto.getGatePassType().equals(GatePassType.UNBLOCK.getStatus()) || dto.getGatePassType().equals(GatePassType.DEBLACKLIST.getStatus())
					) {
				
			int workFlowTypeId = workmenDao.getWorkFlowTYpeNew(gatePassMain.getUnitId(),dto.getGatePassType());
			
			if(workFlowTypeId == WorkFlowType.AUTO.getWorkFlowTypeId() || dto.getGatePassType().equalsIgnoreCase(GatePassType.LOSTORDAMAGE.getStatus())) {
				dto.setGatePassStatus(GatePassStatus.APPROVED.getStatus());
				result = workmenDao.gatePassAction(dto);
				if(dto.getGatePassType().equals(GatePassType.DEBLACKLIST.getStatus()) || dto.getGatePassType().equals(GatePassType.UNBLOCK.getStatus())) {
					 workmenDao.updateGatePassMainStatusAndType(dto.getGatePassId(),dto.getGatePassStatus(),GatePassType.CREATE.getStatus());
				//rollback status and type to create
				}else {
					String gatePassId=dto.getGatePassId();
					
					
				 workmenDao.updateGatePassMainStatus(gatePassId,dto.getGatePassStatus());
				}
				 if(null!=result) {
						GatePassStatusLogDto statusLog = new GatePassStatusLogDto();
						statusLog.setGatePassId(dto.getGatePassId());
						statusLog.setTransactionId(dto.getTransactionId());;
						statusLog.setGatePassType(dto.getGatePassType());
						statusLog.setStatus(Integer.parseInt(dto.getGatePassStatus()));
						statusLog.setComments(dto.getComments());
						statusLog.setUpdatedBy(dto.getCreatedBy());
						workmenDao.saveGatePassStatusLog(statusLog);
					}
				 if (dto.getGatePassType().equals(GatePassType.BLOCK.getStatus())
				            || dto.getGatePassType().equals(GatePassType.BLACKLIST.getStatus())
				            || dto.getGatePassType().equals(GatePassType.CANCEL.getStatus())) {

				        boolean actionDone = this.gatePassActionPersonInsert(gatePassMain, dto.getGatePassType());

				        if (!actionDone) {
				            throw new RuntimeException("GatePass action insert failed unexpectedly.");
				        }

				        
				    }
			}else {
					dto.setGatePassStatus(GatePassStatus.APPROVALPENDING.getStatus());
				 result = workmenDao.gatePassAction(dto);
				 if(null!=result) {
						GatePassStatusLogDto statusLog = new GatePassStatusLogDto();
						statusLog.setGatePassId(dto.getGatePassId());
						statusLog.setTransactionId(dto.getTransactionId());
						statusLog.setGatePassType(dto.getGatePassType());
						statusLog.setStatus(Integer.parseInt(dto.getGatePassStatus()));
						statusLog.setComments(dto.getComments());
						statusLog.setUpdatedBy(dto.getCreatedBy());
						workmenDao.saveGatePassStatusLog(statusLog);
					}
//				//get approvers for gatepass
//				 String approverType = null;
//				 String gatePassType=null;
//				 if(dto.getGatePassType().equalsIgnoreCase(GatePassType.CANCEL.getStatus())) {
//					 approverType="cancelapprover";
//					 gatePassType=GatePassType.CANCEL.getStatus();
//				 }else if(dto.getGatePassType().equalsIgnoreCase(GatePassType.BLACKLIST.getStatus())) {
//					 approverType="blacklistapprover";
//					 gatePassType=GatePassType.BLACKLIST.getStatus();
//				 }else if(dto.getGatePassType().equalsIgnoreCase(GatePassType.BLOCK.getStatus())) {
//					 approverType="blockapprover";
//					 gatePassType=GatePassType.BLOCK.getStatus();
//				 }else if(dto.getGatePassType().equalsIgnoreCase(GatePassType.UNBLOCK.getStatus())) {
//					 approverType="unblockapprover";
//					 gatePassType=GatePassType.UNBLOCK.getStatus();
//				 }else if(dto.getGatePassType().equalsIgnoreCase(GatePassType.DEBLACKLIST.getStatus())) {
//					 approverType="deblacklistapprover";
//					 gatePassType=GatePassType.DEBLACKLIST.getStatus();
//				 }
//				List<MasterUser> approversList = workmenDao.getApproversForGatePassAction(gatePassMain.getCreatedBy(),approverType);
//				
//				if(null!=approversList && approversList.size()>0){
//					for(MasterUser mu :approversList) {
//						mu.setStatus(gatePassType);
//						if(mu.getRoleName().equalsIgnoreCase(UserRole.EIC.getName())) {
//								mu.setIndex(1);
//								
//						}else if(mu.getRoleName().equalsIgnoreCase(UserRole.SECURITY.getName())){
//								mu.setIndex(3);
//							
//						}else if(mu.getRoleName().equalsIgnoreCase(UserRole.HR.getName())) {
//								mu.setIndex(2);
//							
//						}
//					}
//					workmenDao.saveGatePassApprover(dto.getGatePassId(), approversList, gatePassMain.getCreatedBy());
//					
//				}  
				 
 			}//end workflow type
			}else {
				//other action performed on gatepass
				if(gatePassMain.getGatePassAction().equals(GatePassType.CANCEL.getStatus())) {
					result="Cancellation request already initiated for gatepassid :"+dto.getGatePassId();
				}else if(gatePassMain.getGatePassAction().equals(GatePassType.BLOCK.getStatus())) {
					result="Block request already initiated for gatepassid :"+dto.getGatePassId();
				}else if(gatePassMain.getGatePassAction().equals(GatePassType.UNBLOCK.getStatus())) {
					result="Unblock request already initiated for gatepassid :"+dto.getGatePassId();
				}else if(gatePassMain.getGatePassAction().equals(GatePassType.BLACKLIST.getStatus())) {
					result="Blacklist request already initiated for gatepassid :"+dto.getGatePassId();
				}else if(gatePassMain.getGatePassAction().equals(GatePassType.DEBLACKLIST.getStatus())) {
					result="Deblacklist request already initiated for gatepassid :"+dto.getGatePassId();
				}else if(gatePassMain.getGatePassAction().equals(GatePassType.LOSTORDAMAGE.getStatus())) {
					result="Lost or Damage request already initiated for gatepassid :"+dto.getGatePassId();
				}
				
			}
			
			
		}catch(Exception e) {
			
		}
		
		return result;
	}
	@Override
	public List<GatePassListingDto> getGatePassActionListingDetails(String unitId,String deptId,String userId, String gatePassTypeId,String previousGatePassAction,String renewGatePassAction) {
		return workmenDao.getGatePassActionListingDetails(unitId,deptId,userId,gatePassTypeId,previousGatePassAction,renewGatePassAction);
	}
	@Override
	public List<GatePassListingDto> getWorkmenDetailBasedOnId(String gatePassId) {
		// TODO Auto-generated method stub
		return workmenDao.getWorkmenDetailBasedOnId(gatePassId);
	}
	private String getEarliestDate(List<LocalDate> dates, DateTimeFormatter formatter) {
	    return dates.stream()
	            .filter(Objects::nonNull)   // 🔑 LL null? silently ignored
	            .min(LocalDate::compareTo)
	            .map(d -> d.format(formatter))
	            .orElse("No valid date available");
	}

	private String getDOT(GatePassMain gatePassMain) {

	    String dot = null;
	    int dotTypeId = gatePassMain.getDotType();

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    // ---------------- Retirement handling ----------------
	    if (dotTypeId == DotType.RETIRE.getStatus()) {
	        int retirementAge = 60;
	        LocalDate dob = LocalDate.parse(gatePassMain.getDateOfBirth(), formatter);
	        return dob.plusYears(retirementAge).format(formatter);
	    }

	    // ---------------- Fetch validity dates ----------------
	    Map<String, LocalDate> validityDates =
	            workmenDao.getValidityDates(
	                    gatePassMain.getWorkorder(),
	                    gatePassMain.getWcEsicNo(),
	                    gatePassMain.getLlNo()   // may be null
	            );

	    LocalDate woDate = validityDates.get("WO");
	    LocalDate wcDate = validityDates.get("WC");
	    LocalDate llDate = validityDates.get("LL"); // may be null

	    // ---------------- DOT calculation ----------------
	    if (dotTypeId == DotType.LLWCWO.getStatus()) {

	        dot = getEarliestDate(Arrays.asList(woDate, wcDate, llDate), formatter);

	    } else if (dotTypeId == DotType.LLWC.getStatus()) {

	        dot = getEarliestDate(Arrays.asList(wcDate, llDate), formatter);

	    } else if (dotTypeId == DotType.LLWO.getStatus()) {

	        dot = getEarliestDate(Arrays.asList(woDate, llDate), formatter);

	    } else if (dotTypeId == DotType.WCWO.getStatus()) {

	        dot = getEarliestDate(Arrays.asList(woDate, wcDate), formatter);

	    } else if (dotTypeId == DotType.LL.getStatus()) {

	        dot = getEarliestDate(Collections.singletonList(llDate), formatter);

	    } else if (dotTypeId == DotType.WC.getStatus()) {

	        dot = getEarliestDate(Collections.singletonList(wcDate), formatter);

	    } else if (dotTypeId == DotType.WO.getStatus()) {

	        dot = getEarliestDate(Collections.singletonList(woDate), formatter);
	    }

	    return dot;
	}

	@Override
	public List<ApproverStatusDTO> getApprovalDetails(String transactionId,String unitId,String gatePassTypeId) {
		// TODO Auto-generated method stub
		return workmenDao.getApprovalDetails( transactionId,unitId,gatePassTypeId);
		
	}
	
	@Override
	public List<Contractor> getAllContractorForAdmin(String unitId) {
		
		return workmenDao.getAllContractorForAdmin(unitId);
	}
	
	@Override
	public String draftGatePass(GatePassMain gatePassMain) {
		String transactionId =null;
		try {
			
				gatePassMain.setGatePassStatus(GatePassStatus.DRAFT.getStatus());
				gatePassMain.setGatePassId(" ");
				gatePassMain.setGatePassAction(GatePassType.CREATE.getStatus());
				transactionId = workmenDao.draftGatePass(gatePassMain); 
				return transactionId;
		}catch(Exception e) {
			
		}
		return transactionId;
	}
	
	@Override
	public String generateTransactionId() {
		return workmenDao.getNextTransactionId();
	}
	
	@Override
	public GatePassMain getIndividualContractWorkmenDraftDetails(String transactionId) {
		return workmenDao.getIndividualContractWorkmenDraftDetails(transactionId);
	}
	@Override
	public int getWorkFlowTYpe(String principalEmployer) {
		// TODO Auto-generated method stub
		return workmenDao.getWorkFlowTYpe(principalEmployer);
	}

	@Override
	public List<GatePassListingDto> getRenewListingDetails(String userId,String gatePassTypeId,String gatePassStatus,String deptId,String unitId) {
		return workmenDao.getRenewListingDetails( userId, gatePassTypeId, gatePassStatus, deptId, unitId) ;
	}
	
	@Override
	public String renewGatePass(GatePassMain gatePassMain) {
		String transactionId =gatePassMain.getTransactionId();
		try {
			int workFlowTypeId = workmenDao.getWorkFlowTYpeNew(gatePassMain.getPrincipalEmployer(),GatePassType.RENEW.getStatus());
			gatePassMain.setWorkFlowType(workFlowTypeId);

			
			int dotTypeId = workmenDao.getDOTTYpe(gatePassMain.getPrincipalEmployer());
			gatePassMain.setDotType(dotTypeId);
			
			String dot = this.getDOT(gatePassMain);
			gatePassMain.setDot(dot);
			gatePassMain.setGatePassAction(GatePassType.RENEW.getStatus());
			if(workFlowTypeId == WorkFlowType.AUTO.getWorkFlowTypeId()) {
				gatePassMain.setGatePassStatus(GatePassStatus.APPROVED.getStatus());
				 transactionId = workmenDao.renewGatePass(gatePassMain); 
				 //String gatePassId = workmenDao.updateGatePassIdByTransactionId(transactionId);
				//gatePassMain.setGatePassId(gatePassId);
				GatePassStatusLogDto dto =new GatePassStatusLogDto();
				dto.setTransactionId(transactionId);
				dto.setGatePassId(gatePassMain.getGatePassId());
				dto.setGatePassType(GatePassType.RENEW.getStatus());
				dto.setStatus(Integer.parseInt(GatePassStatus.APPROVED.getStatus()));
				dto.setComments(gatePassMain.getComments());
				dto.setUpdatedBy(gatePassMain.getUserId());
				workmenDao.saveGatePassStatusLog(dto);
				if (dto.getGatePassType().equals(GatePassType.RENEW.getStatus())) {
					try {
			        	String wfdIntegration = this.getWFDIntegration();
			        	if("yes".equalsIgnoreCase(wfdIntegration)) {
			        		api.updateOnBoardingDetails(dto.getTransactionId());
			        	}
			        	}catch(Exception e) {return transactionId;}

			    }
				return transactionId;
			}else {
				gatePassMain.setGatePassStatus(GatePassStatus.APPROVALPENDING.getStatus());
				transactionId = workmenDao.renewGatePass(gatePassMain);
				GatePassStatusLogDto dto =new GatePassStatusLogDto();
				dto.setTransactionId(transactionId);
				dto.setGatePassId(gatePassMain.getGatePassId());
				dto.setGatePassType(GatePassType.RENEW.getStatus());
				dto.setStatus(Integer.parseInt(GatePassStatus.APPROVED.getStatus()));
				dto.setComments(gatePassMain.getComments());
				dto.setUpdatedBy(gatePassMain.getUserId());
				workmenDao.saveGatePassStatusLog(dto);
	
				
			}
		}catch(Exception e) {
			
		}
		return transactionId;
	}
	
	@Override
	public List<GatePassListingDto> getGatePassActionListingForApprovers(String unitId ,String deptId,MasterUser user,String gatePassTypeId) {
		int workFlowTypeId = workmenDao.getWorkFlowTYpeNew(unitId,gatePassTypeId);
			return workmenDao.getGatePassActionListingForApprovers(user.getRoleId(),workFlowTypeId,gatePassTypeId,deptId,unitId);
	}

	
	public String getSurePassURL() {
	    return QueryFileWatcher.getQuery("SUREPASS_VERIFY_OTP_URL");
	}


	public String getToken() {
		return QueryFileWatcher.getQuery("TOKEN");
	}

    
    public String getGenderIdFromCode(String code, List<CmsGeneralMaster> genderList) {
        for (CmsGeneralMaster gm : genderList) {
            if (code.equalsIgnoreCase("M") && gm.getGmName().equalsIgnoreCase("MALE")) {
                return String.valueOf(gm.getGmId());
            } else if (code.equalsIgnoreCase("F") && gm.getGmName().equalsIgnoreCase("FEMALE")) {
                return String.valueOf(gm.getGmId());
            }
        }
        return ""; // or null if not found
    }

    
	@Override
	public Map<String, Object> verifyOtpWithSurepass(String clientId, String otp) {
        String url =getSurePassURL();
        String bearerToken = getToken();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", bearerToken);

        Map<String, String> body = new HashMap<>();
		body.put("client_id", clientId);
        body.put("otp", otp);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            Map<String, Object> responseBody = response.getBody();
            Map<String, Object> data = (Map<String, Object>) responseBody.get("data");

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("fullName", data.get("full_name"));
            result.put("dob", data.get("dob"));
            
        	List<CmsGeneralMaster> gmList = this.getAllGeneralMaster();
            Map<String, List<CmsGeneralMaster>> groupedByGmType = gmList.stream()
                    .collect(Collectors.groupingBy(CmsGeneralMaster::getGmType));

            List<CmsGeneralMaster> genderList = groupedByGmType.get("GENDER");

            String genderCode = (String) data.get("gender"); // "M" or "F"
            String genderId = getGenderIdFromCode(genderCode, genderList);

            result.put("gender", genderId); // send this to frontend

            //result.put("gender", data.get("gender"));

            // Construct address string
            Map<String, Object> addr = (Map<String, Object>) data.get("address");
            String address = String.join(", ",
                    Objects.toString(addr.get("house"), ""),
                    Objects.toString(addr.get("street"), ""),
                    Objects.toString(addr.get("loc"), ""),
                    Objects.toString(addr.get("dist"), ""),
                    Objects.toString(addr.get("state"), ""));

            result.put("address", address);
            return result;

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "OTP verification failed: " + e.getStatusText());
            return error;
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Unexpected error: " + e.getMessage());
            return error;
        }
    }
	@Override
	public boolean isAadhaarExists(String aadhaarNumber) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isAadharDuplicate(String aadharNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ContractWorkmenExportDto> getContractWorkmenExportData(String unitId) {
		// TODO Auto-generated method stub
		return workmenDao.getContractWorkmenExportData(unitId);
	}
	@Override
	public boolean isAadharExists(String aadharNumber,String transactionId) {
		// TODO Auto-generated method stub
		return workmenDao.isAadharExists(aadharNumber,transactionId);
	}

	/*
	 * @Override public boolean checkAadharDuplicate(String aadhaarNumber) { return
	 * workmenDao.isAadharExists(aadhaarNumber); }
	 */
	
	@Override
	public List<WageDetailsDto> getWageMasterExportData(String unitId) {
		// TODO Auto-generated method stub
		return workmenDao.getWageMasterExportData(unitId);
	}
	@Override
	public String getOtherAadharLinkedToUan(String uan, String aadhar) {
	    return workmenDao.findAadharByUanIfExistsWithDifferentAadhar(uan, aadhar);
	}
	@Override
	public String getOtherAadharLinkedTopfNumber(String pfNumber, String aadhar) {
	    return workmenDao.findAadharBypfNumberIfExistsWithDifferentAadhar(pfNumber, aadhar);
	}
	@Override
	public void createDraftGatepass(String transactionId, String userId) {
		// TODO Auto-generated method stub
		workmenDao.createDraftGatepass(transactionId, userId);
	}
	
	@Override
	public List<GatePassListingDto> getGatePassActionListingDetailsForDashboardNav(String unitId,String deptId,String userId, String gatePassTypeId) {
		return workmenDao.getGatePassActionListingDetailsDashboardNav(unitId,deptId,userId,gatePassTypeId);
	}
	@Override
	public  List<DeptMapping> getAllSkills(String unitId, String tradeId){
		return workmenDao.getAllSkills(unitId,tradeId);
	}
	@Override
	public List<DeptMapping> getAllDepartmentsOnPE(String unitId) {
		return workmenDao.getAllDepartmentsOnPE(unitId);
	}
	@Override
	public List<DeptMapping> getAllSubDepartments(String unitId, String departmentId) {
		return workmenDao.getAllSubDepartments(unitId,departmentId);
	}
	@Override
	public String getTransactionIdByGPId(String gatepassid,String gatepasstypeid) {
		return workmenDao.getTransactionIdByGPId(gatepassid,gatepasstypeid);
	}
	
	public long saveIntoCMSPerson(GatePassMain gpm) {
		
		CMSPerson person = new CMSPerson();
		person.setEmployeeCode(gpm.getGatePassId());
		person.setFirstName(gpm.getFirstName());
		person.setLastName(gpm.getLastName());
		person.setRelationName(gpm.getRelationName());
		person.setDateOfBirth(gpm.getDateOfBirth());
		person.setDateOfJoining(gpm.getDoj());
		person.setDateOfTermination(gpm.getDot()!=null?gpm.getDot().toString():" ");
		//person.setBloodGroup(Integer.parseInt(gpm.getBloodGroup()));
		person.setBloodGroup(gpm.getBloodGroup() != null && !gpm.getBloodGroup().trim().isEmpty()? Integer.parseInt(gpm.getBloodGroup()): 0);

		person.setHazardousArea(gpm.getHazardousArea());
		person.setGender(Integer.parseInt(gpm.getGender()));
		person.setAcademics(gpm.getAcademic() != null && !gpm.getAcademic().trim().isEmpty()? Integer.parseInt(gpm.getAcademic()): 0);
		person.setAccomodation(gpm.getAccommodation() != null && gpm.getAccommodation().trim().equalsIgnoreCase("Yes") ? 1 : 0);
		person.setBankBranch(gpm.getIfscCode());
		person.setAccountNo(gpm.getAccountNumber() != null && !gpm.getAccountNumber().trim().isEmpty()? gpm.getAccountNumber(): " ");
		person.setEmergencyName(gpm.getEmergencyName());
		person.setEmergencyNumber(gpm.getEmergencyNumber());
		person.setMobileNumber(gpm.getMobileNumber());
		person.setAccessLevel(Integer.parseInt(gpm.getAccessArea()));
		person.setEsicNumber(gpm.getEsicNumber());
		person.setUanNumber(gpm.getUanNumber());
		person.setIsPfEligible(gpm.getPfApplicable().equals("Yes")?1:0);
		person.setIdMark(gpm.getIdMark());
		person.setPanNumber(gpm.getPfNumber());
		person.setAadharNumber(gpm.getAadhaarNumber());
		person.setUpdatedBy(gpm.getCreatedBy());
		
		return workmenDao.saveIntoCMSPerson(person);
	}
	
	public boolean saveIntoCMSPERSONJOBHIST(GatePassMain gpm,long employeeId) {
		
		return workmenDao.saveIntoCMSPERSONJOBHIST(gpm,employeeId);
	}
	
	public boolean saveCMSPERSONSTATUSMM(GatePassMain gpm,long employeeId) {
		String dot = gpm.getDot();  // e.g. "2025-12-31"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(dot, formatter);
		LocalDate nextDate = date.plusDays(1);
		String newDot = nextDate.format(formatter);
		gpm.setNewDot(newDot);
		return workmenDao.saveCMSPERSONSTATUSMM(gpm,employeeId);
	}
	

	
	@Override
    public Map<String, String> getPreviousDocuments(String transactionId) {
        return workmenDao.getPreviousDocuments(transactionId);
    }
	 @Override
	    public List<Map<String, Object>> getAllVersionedDocuments(String transactionId, Integer userId) {
	        List<Map<String, Object>> allDocs = new ArrayList<>();

	        // Base directory for files
	        String baseDir = "D:/wfd_cwfm/ep_docs/" + userId + "/" + transactionId + "/";
	        File folder = new File(baseDir);
	        File[] files = folder.listFiles();

	        // 1️⃣ Version 1 docs (from GATEPASSMAIN)
	        Map<String, String> v1Docs = workmenDao.getPreviousDocuments(transactionId);
	        for (Map.Entry<String, String> entry : v1Docs.entrySet()) {
	            String docType = entry.getKey();
	            String value = entry.getValue();
	            if (value != null && !value.trim().isEmpty()) {
	                String matchedFile = null;
	                if (files != null) {
	                    for (File f : files) {
	                        if (f.getName().toLowerCase().startsWith(value.toLowerCase())) {
	                            matchedFile = f.getName();
	                            break;
	                        }
	                    }
	                }
	                if (matchedFile != null) {
	                    Map<String, Object> map = new LinkedHashMap<>();
	                    map.put("DOCTYPE", docType);
	                    map.put("FILENAME", matchedFile);
	                    map.put("VERSIONNO", 1);
	                    allDocs.add(map);
	                }
	            }
	        }

	        // 2️⃣ Renewal docs (V2 and beyond)
	        List<Map<String, Object>> renewalDocs = workmenDao.getRenewalDocs(transactionId);
	        allDocs.addAll(renewalDocs);

	        // 3️⃣ Sort by DOC_TYPE, then version
	        allDocs.sort(Comparator.comparing((Map<String, Object> m) -> (String) m.get("DOCTYPE"))
	                .thenComparingInt(m -> ((Number) m.get("VERSIONNO")).intValue()));

	        return allDocs;
	    }
	 @Override
	 public void saveRenewedDocuments(String transactionId, String userId,
	                                  MultipartFile aadharFile,
	                                  MultipartFile policeFile,
	                                  MultipartFile profilePic,
	                                  List<MultipartFile> additionalFiles,
	                                  List<String> documentTypes,String filePath) {
	     workmenDao.saveRenewedDocuments(transactionId, userId, aadharFile, policeFile, profilePic, additionalFiles, documentTypes, filePath);
	 }
	 @Override
		public List<GatePassListingDto> getGatePassUnblockDeblackListingDetails(String unitId,String deptId,String userId, String gatePassTypeId,String previousGatePassAction,String renewGatePassAction) {
			return workmenDao.getGatePassUnblockDeblackListingDetails(unitId,deptId,userId,gatePassTypeId,previousGatePassAction,renewGatePassAction);
		}
	 @Override
	 public String getAadharStatus(String aadharNumber) {
		    return workmenDao.getAadharStatus(aadharNumber);
		}
	 @Override
	 public String checkAadharUniqueness(String aadharNumber, String gatePassId, String transactionId) {

	        return workmenDao.checkAadharUniqueness(aadharNumber, gatePassId, transactionId);
	    }
	@Override
	public GatePassMain getIndividualContractWorkmenDetailsByGatePassIdRenew(String gatePassId) {
		return workmenDao.getIndividualContractWorkmenDetailsByGatePassIdRenew(gatePassId);
	}
	@Override
	public String saveWorkmenBulkUploadGatePass(GatePassMain gatePassMain) {
		String transactionId =null;
		
		try {
	
			String allowOnboarding = this.workmenCountCheck(gatePassMain);
			if("allow".equals(allowOnboarding)) {
			int workFlowTypeId =gatePassMain.getWorkFlowType();
			gatePassMain.setWorkFlowType(workFlowTypeId);
			
			int dotTypeId = workmenDao.getDOTTYpe(gatePassMain.getPrincipalEmployer());
			gatePassMain.setDotType(dotTypeId);
			
			String dot = this.getDOT(gatePassMain);
			gatePassMain.setDot(dot);
			gatePassMain.setGatePassAction(GatePassType.CREATE.getStatus());
			if(workFlowTypeId == WorkFlowType.AUTO.getWorkFlowTypeId()) {
				gatePassMain.setGatePassStatus(GatePassStatus.APPROVED.getStatus());

				 transactionId = workmenDao.saveGatePass(gatePassMain);

//				EmployeeRequestDTO employeeDTO = employeeMapper.mapFromGatePass(gatePassMain);
//				String wfdResponse = wfdEmployeeService.createEmployee(employeeDTO);
//				log.info("WFD Response: {}", wfdResponse);



				 String gatePassId = workmenDao.updateGatePassIdByTransactionId(transactionId);
				gatePassMain.setGatePassId(gatePassId);
				GatePassStatusLogDto dto =new GatePassStatusLogDto();
				dto.setTransactionId(transactionId);
				dto.setGatePassId(gatePassId);
				dto.setGatePassType(GatePassType.CREATE.getStatus());
				dto.setStatus(Integer.parseInt(GatePassStatus.APPROVED.getStatus()));
				dto.setComments(gatePassMain.getComments());
				dto.setUpdatedBy(gatePassMain.getUserId());
				workmenDao.saveGatePassStatusLog(dto);
				
				boolean cmsDone = this.cmsPersonInsert(gatePassMain);

		        if (!cmsDone) {
		            throw new RuntimeException("CMS Person Insert failed unexpectedly.");
		        }try {
		        	String wfdIntegration = this.getWFDIntegration();
		        	if("yes".equalsIgnoreCase(wfdIntegration)) {
		        		api.addOnBoardingDetailsActual(dto.getTransactionId());
		        	}
		        	}catch(Exception e) {return transactionId;}

				return transactionId;
			}
			}else {
				return allowOnboarding;
			}
		}catch(Exception e) {
			
		}
		return transactionId;
	}
	
	@Override
	public String saveProjectGatePass(GatePassMain gatePassMain) {
		String transactionId =null;
		
		try {
	
			int workFlowTypeId = workmenDao.getWorkFlowTYpeNew(gatePassMain.getPrincipalEmployer(),GatePassType.PROJECT.getStatus());
			gatePassMain.setWorkFlowType(workFlowTypeId);
			gatePassMain.setDotType(0);
			String dot = null;
			gatePassMain.setDot(dot);
			gatePassMain.setGatePassAction(GatePassType.PROJECT.getStatus());
			if(workFlowTypeId == WorkFlowType.AUTO.getWorkFlowTypeId()) {
				gatePassMain.setGatePassStatus(GatePassStatus.APPROVED.getStatus());
				 transactionId = workmenDao.saveGatePass(gatePassMain);

				 String gatePassId = workmenDao.updateGatePassIdByTransactionId(transactionId);
				gatePassMain.setGatePassId(gatePassId);
				GatePassStatusLogDto dto =new GatePassStatusLogDto();
				dto.setTransactionId(transactionId);
				dto.setGatePassId(gatePassId);
				dto.setGatePassType(GatePassType.PROJECT.getStatus());
				dto.setStatus(Integer.parseInt(GatePassStatus.APPROVED.getStatus()));
				dto.setComments(gatePassMain.getComments());
				dto.setUpdatedBy(gatePassMain.getUserId());
				workmenDao.saveGatePassStatusLog(dto);
				
				

				return transactionId;
			}else {
				gatePassMain.setGatePassStatus(GatePassStatus.APPROVALPENDING.getStatus());
				transactionId = workmenDao.saveGatePass(gatePassMain);
				gatePassMain.setGatePassId(" ");
				GatePassStatusLogDto dto =new GatePassStatusLogDto();
				dto.setTransactionId(transactionId);
				dto.setGatePassId(" ");
				dto.setGatePassType(GatePassType.PROJECT.getStatus());
				dto.setStatus(Integer.parseInt(GatePassStatus.APPROVED.getStatus()));
				dto.setComments(gatePassMain.getComments());
				dto.setUpdatedBy(gatePassMain.getUserId());
				workmenDao.saveGatePassStatusLog(dto);
				return transactionId;
				
			}
			
		}catch(Exception e) {
			
		}
		return transactionId;
	}
	
	public String licenseExistsAndCount(GatePassMain gatePassMain, int activeCount) {

	    String unit = gatePassMain.getPrincipalEmployer();
	    String contractor = gatePassMain.getContractor();
	    String workorder = gatePassMain.getWorkorder();

	    // ---------- Fetch Results ----------
	    Map<String, Object> llResult =
	            workmenDao.licenseExistsAndCount(unit, contractor, workorder, "LL", gatePassMain.getLlNo());

	    Map<String, Object> wcResult =
	            workmenDao.licenseExistsAndCount(unit, contractor, workorder, "WC", gatePassMain.getWcEsicNo());

	    Map<String, Object> esicResult = null;
	    boolean hasEsic =
	            gatePassMain.getEsicNumber() != null &&
	            !gatePassMain.getEsicNumber().trim().isEmpty() &&
	            !"NULL".equalsIgnoreCase(gatePassMain.getEsicNumber().trim());


	    if (hasEsic) {
	        esicResult =
	                workmenDao.licenseExistsAndCount(unit, contractor, workorder, "ESIC", gatePassMain.getWcEsicNo());
	    }

	    // ---------- Extract LL ----------
	    boolean llExists = (Boolean) llResult.get("exists");
	    int llCount = (Integer) llResult.get("count");
	    Date llExpiry = (Date) llResult.get("expiryDate");

	    // ---------- Extract WC ----------
	    boolean wcExists = (Boolean) wcResult.get("exists");
	    int wcCount = (Integer) wcResult.get("count");
	    Date wcExpiry = (Date) wcResult.get("expiryDate");

	    // ---------- Extract ESIC ----------
	    boolean esicExists = false;
	    int esicCount = 0;
	    Date esicExpiry = null;

	    if (hasEsic && esicResult != null) {
	        esicExists = (Boolean) esicResult.get("exists");
	        esicCount = (Integer) esicResult.get("count");
	        esicExpiry = (Date) esicResult.get("expiryDate");
	    }

	    Date today = new Date();

	    // ---------- LL Mandatory ----------
	    boolean llMandatory =
	            llExists &&
	            (
	                (llExpiry != null && llExpiry.before(today)) ||
	                (llExpiry != null && !llExpiry.before(today) && llCount == 0)
	            );

	    // ---------- WC / ESIC Mandatory (either one is enough) ----------
	    boolean wcInvalid =
	            !wcExists ||
	            (wcExpiry != null && wcExpiry.before(today)) ||
	            (wcExpiry != null && !wcExpiry.before(today) && wcCount == 0);

	    boolean esicInvalid = hasEsic &&
	            (
	                !esicExists ||
	                (esicExpiry != null && esicExpiry.before(today)) ||
	                (esicExpiry != null && !esicExpiry.before(today) && esicCount == 0)
	            );

	    boolean wcEsicMandatory = wcInvalid && esicInvalid;

	    // ---------- Mandatory Validation ----------
	    if (llMandatory || wcEsicMandatory) {
	        if (llMandatory && wcEsicMandatory) {
	            return "LL and WC/ESIC are mandatory";
	        }
	        if (llMandatory) {
	            return "LL is mandatory";
	        }
	        return "WC/ESIC is mandatory";
	    }

	    // ---------- Capacity Validation ----------
	    boolean llExceeded = llExists && activeCount >= llCount;

	    boolean wcExceeded =
	            wcExists && activeCount >= wcCount;

	    boolean esicExceeded =
	            hasEsic && esicExists && activeCount >= esicCount;

	    boolean wcEsicExceeded = wcExceeded && (!hasEsic || esicExceeded);

	    if (llExceeded || wcEsicExceeded) {
	        if (llExceeded && wcEsicExceeded) {
	            return "LL and WC/ESIC exceeded";
	        }
	        if (llExceeded) {
	            return "LL exceeded";
	        }
	        return "WC/ESIC exceeded";
	    }

	    return "allow";
	}

	@Override
	public String getRenewTransactionIfExists(String gatePassId) {
		return workmenDao.getRenewTransactionIfExists(gatePassId);
	}
	@Override
	public boolean  updateGatePassMainWithReasoningTab(GatePassActionDto dto, MultipartFile exitFile, MultipartFile fnfFile,
			MultipartFile feedbackFile, MultipartFile rateManagerFile, MultipartFile locFile) {
		return workmenDao.updateGatePassMainWithReasoningTab(dto,exitFile,fnfFile,feedbackFile,rateManagerFile,locFile);
		
	}
	
	}

