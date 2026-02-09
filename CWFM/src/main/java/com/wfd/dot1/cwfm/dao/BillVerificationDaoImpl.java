package com.wfd.dot1.cwfm.dao;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.ApproveRejectBillDto;
import com.wfd.dot1.cwfm.dto.CMSWageCostDTO;
import com.wfd.dot1.cwfm.dto.ChecklistItemDTO;
import com.wfd.dot1.cwfm.enums.GatePassStatus;
import com.wfd.dot1.cwfm.enums.GatePassType;
import com.wfd.dot1.cwfm.enums.WorkFlowType;
import com.wfd.dot1.cwfm.pojo.BillReportFile;
import com.wfd.dot1.cwfm.pojo.BillStatusLogDto;
import com.wfd.dot1.cwfm.pojo.BillVerification;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
@Repository
public class BillVerificationDaoImpl implements BillVerificationDao {

	private static final Logger log = LoggerFactory.getLogger(ContractorDaoImpl.class.getName());
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	 public String getBillList() {
		    return QueryFileWatcher.getQuery("GET_BILL_LIST");
		}
	 public String getAllbillDetailsView() {
		    return QueryFileWatcher.getQuery("GET_BILL_DETAIL_VIEW");
		}
	 public String getAllbillReportsDetailsView() {
		    return QueryFileWatcher.getQuery("GET_BILL_REPORTS_DETAIL_VIEW");
		}
	 public String getAllhrclearanceView() {
		    return QueryFileWatcher.getQuery("GET_BILL_HR_CLEARANCE_VIEW");
		}
	 public String getAllbillPrecommentsView() {
		    return QueryFileWatcher.getQuery("GET_BILL_PRECOMMENTS_VIEW");
		}
	 public String getAllbilleicView() {
		    return QueryFileWatcher.getQuery("GET_BILL_EIC_VIEW");
		}
	 public String getActionIdForBills() {
		    return QueryFileWatcher.getQuery("GET_ACTIONID_FOR_BILLS");
		}
	 public String saveBillQuery() {
		    return QueryFileWatcher.getQuery("SAVE_BILL");
		}
	 public String getMaxId() {
			return QueryFileWatcher.getQuery("GET_MAX_WC_TNX_ID");
		}
	 public String saveBillCMSWageCostWorkFlowQuery() {
			return QueryFileWatcher.getQuery("SAVE_BILL_CMSWAGECOST_WORKFLOW");
		}
	
	 
		
		@Override
		public String generateWCTransactionId() {
			String WCTransID = null;
		    try {
		    	String query = getMaxId();
		        SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		        if (rs.next()) {
		        	WCTransID = rs.getString("WCTransID");
		        }
		    } catch (Exception e) {
		        log.error("Error generating WCTransID", e);
		    }
		    return WCTransID;
		}
		 public String getContractorListing() {
				return QueryFileWatcher.getQuery("GET_BVR_FOR_CREATOR");
			}
		@Override
		public List<CMSWageCostDTO> getBillVerificationList(String userId,String deptId,String unitId) {//deptID is contractorID
			List<CMSWageCostDTO> listDto= new ArrayList<CMSWageCostDTO>();
			String query =getContractorListing();
			log.info("Query to GET_BVR_FOR_CREATOR "+query);
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId,deptId,userId);
			while(rs.next()) {
				CMSWageCostDTO d = new CMSWageCostDTO();
				d.setWcTransId(Long.parseLong(rs.getString("WCTransID")));
				d.setUnitCode(rs.getString("UnitCode"));
				d.setContractorCode(rs.getString("ContractorCode"));
				d.setContractorName(rs.getString("ContractorName"));
				d.setWorkOrderNumber(rs.getString("WorkOrderNumber"));
				d.setStartDate(rs.getString("StartDate"));
				d.setEndDate(rs.getString("EndDate"));
				d.setStatus(rs.getInt("Status"));
				d.setServices(rs.getString("Services"));
				
				String status =String.valueOf(rs.getInt("Status"));
				if(status.equals(GatePassStatus.APPROVALPENDING.getStatus())) {
					d.setStatusValue("Approval Pending");
				}else if(status.equals(GatePassStatus.APPROVED.getStatus())) {
					d.setStatusValue("Approved");
				}else if(status.equals(GatePassStatus.REJECTED.getStatus())) {
					d.setStatusValue("Rejected");
				}else if(status.equals(GatePassStatus.DRAFT.getStatus())) {
					d.setStatusValue("Draft");
				}
				listDto.add(d);
			}
			return listDto;
		}
		@Override
		public BillVerification viewbillDetails(String transactionId) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public BillVerification viewbillReportsDetails(String transactionId) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public BillVerification viewbillhrclearanceDetails(String transactionId) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public BillVerification viewbillprecomments(String transactionId) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public BillVerification viewbilleicDetails(String transactionId) {
			// TODO Auto-generated method stub
			return null;
		}
		
		 public String getAllPEBasedOnUser() {
			    return QueryFileWatcher.getQuery("GET_PE_DETAIL_FOR_BILL");
			}
		 
		@Override
		public List<PrincipalEmployer> getPEDetailByUser(String userAccount) {
			List<PrincipalEmployer> list= new ArrayList<PrincipalEmployer>();
			String query=getAllPEBasedOnUser();
			log.info("Query to getAllContractorBasedOnPE "+query);
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query,userAccount);
			while(rs.next()) {
				PrincipalEmployer pe = new PrincipalEmployer();
				pe.setUnitId(Integer.parseInt(rs.getString("Id")));
				pe.setName(rs.getString("Description"));
				pe.setCode(rs.getString("Code"));
				list.add(pe);
				
			}
			log.info("Exiting from getAllContractorBasedOnPE dao method "+list.size());
			return list;
		}
		

		@Override
		public String getActionIdForBill() {
			String sql=getActionIdForBills();
			String r=null;
			//String sql =" select cgm.GMID,cgm.GMNAME from CMSGENERALMASTER cgm\r\n"
			//   		+ "		    join CMSGMTYPE cgt on cgt.GMTYPEID=cgm.GMTYPEID\r\n"
			//   		+ "		    where cgt.GMTYPE='ACTION' and cgm.GMNAME like 'Bill%'";
			   SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
			   while(rs.next()) {
			   r=rs.getString("GMID");
			   }
			   return r;
		}
		
		@Override
		public String saveBill(CMSWageCostDTO dto) {
		    String transId = null;
		    
		   String sql =" select cgm.GMID,cgm.GMNAME from CMSGENERALMASTER cgm\r\n"
		   		+ "		    join CMSGMTYPE cgt on cgt.GMTYPEID=cgm.GMTYPEID\r\n"
		   		+ "		    where cgt.GMTYPE='MODULE' and cgm.GMNAME like 'Bill%'";
		    //String sql=saveBillQuery();

		   SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
		   while(rs.next()) {
		   dto.setModuleId(rs.getString("GMID"));
		   }
		   dto.setActionId(GatePassType.BILLVERIFICATION.getStatus());
		    String query = "INSERT INTO CMSWageCostWorkFlow " +
		            "(WCTransID, Status, UnitId, UnitCode, UnitName, ContractorId, ContractorCode, ContractorName, " +
		            "WorkOrderNumber, StartDate, EndDate, Services, CreatedBy, CreatedDate, UpdatedDate, " +
		            "WOValidFrom, WOValidTo, BillType, UpdatedBy, Comments, PreComments, ActionPlan,ActionId,ModuleId) " +
		            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE(), ?, ?, ?, ?, ?, ?, ?,?,?)";
		  // String query=saveBillCMSWageCostWorkFlowQuery();
		  

		    // Formatter to parse dd-MM-yyyy format coming from JSP
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    try {

		        Object[] parameters = new Object[]{
		            dto.getWcTransId(),
		            dto.getStatus(),
		            dto.getUnitId(),
		            dto.getUnitCode(),
		            dto.getUnitName(),
		            dto.getContractorId(),
		            dto.getContractorCode(),
		            dto.getContractorName(),
		            dto.getWorkOrderNumber(),
		            parseDate1(dto.getStartDate(), formatter1),
		            parseDate1(dto.getEndDate(), formatter1),
		            dto.getServices(),
		            dto.getCreatedBy(),
		            parseDate(dto.getWoValidFrom(), formatter),
		            parseDate(dto.getWoValidTo(), formatter),
		            dto.getBillType(),
		            dto.getUpdateBy(),
		            dto.getComments(),
		            dto.getPreComments(),
		            dto.getActionPlan(),
		            dto.getActionId(),dto.getModuleId()
		        };

		        int result = jdbcTemplate.update(query, parameters);

		        if (result > 0) {
		            log.info("Bill saved successfully for transId: " + dto.getWcTransId());
		            transId = String.valueOf(dto.getWcTransId());
		        } else {
		            log.warn("Failed to save Bill for transId: " + dto.getWcTransId());
		        }

		    } catch (Exception e) {
		        log.error("Error saving Bill for transId: " + dto.getWcTransId(), e);
		    }

		    log.info("Exiting from Bill for transId: " + dto.getWcTransId());
		    return transId;
		}
		private java.sql.Date parseDate(String dateStr, DateTimeFormatter formatter) {
		    if (dateStr == null || dateStr.trim().isEmpty()) return null;
		    LocalDate localDate = LocalDate.parse(dateStr, formatter);
		    return java.sql.Date.valueOf(localDate);
		}
		private java.sql.Date parseDate1(String dateStr, DateTimeFormatter formatter) {
		    if (dateStr == null || dateStr.trim().isEmpty()) return null;
		    LocalDate localDate = LocalDate.parse(dateStr, formatter);
		    return java.sql.Date.valueOf(localDate);
		}
		
		 public String getAllBVRForSquential() {
			    return QueryFileWatcher.getQuery("GET_BVR_FOR_SEQUENTIAL_APPROVER");
			}
		 
		 public String getAllBVRForParallel() {
			    return QueryFileWatcher.getQuery("GET_BVR_FOR_PARALLEL_APPROVER");
			}
		 
		@Override
		public List<CMSWageCostDTO> getBillVerificationListForApprovers(String roleId, int workFlowType, String deptId,
				String principalEmployerId) {
			//String actionId=this.getActionIdForBill();
			
			List<CMSWageCostDTO> listDto= new ArrayList<CMSWageCostDTO>();
			SqlRowSet rs =null;
			String query=null;
			if(workFlowType == WorkFlowType.SEQUENTIAL.getWorkFlowTypeId()) {
				//query=this.getAllBVRForSquential();
				
				query = "select wcw.WCTransID,wcw.UnitCode,wcw.ContractorCode,wcw.ContractorName,cwo.SAP_WORKORDER_NUM \r\n"
						+ "as WorkOrderNumber ,cgm.GMNAME as Services,CONVERT(VARCHAR(10), wcw.StartDate, 120) AS StartDate,CONVERT(VARCHAR(10), wcw.EndDate, 120) AS EndDate,wcw.Status \r\n"
						+ "from cmswagecostworkflow wcw \r\n"
						+ "join CMSGENERALMASTER cgm on cgm.GMID =wcw.Services \r\n"
						+ "join CMSWORKORDER cwo on cwo.WORKORDERID=wcw.WorkOrderNumber \r\n"
						+ "JOIN CMSWORKFLOWTYPE cwt \r\n"
						+ "       ON cwt.ModuleId = wcw.ModuleId \r\n"
						+ "      AND cwt.UnitId = wcw.UnitId\r\n"
						+ "JOIN CMSAPPROVERHIERARCHY cah   \r\n"
						+ "       ON cah.WORKFLOWTYPEID = cwt.WorkflowTypeId\r\n"
						+ "WHERE \r\n"
						+ "    wcw.status = '3'\r\n"
						+ "    AND wcw.ContractorId = ?\r\n"
						+ "    AND wcw.UnitId = ?\r\n"
						+ "    AND cah.ROLE_ID = ?\r\n"
						+ "    AND cah.[Index] = (\r\n"
						+ "            SELECT COUNT(DISTINCT gas.BILLApprovalStatusId) + 1\r\n"
						+ "            FROM BILLAPPROVALSTATUS gas\r\n"
						+ "            JOIN CMSAPPROVERHIERARCHY cah1 \r\n"
						+ "                  ON gas.RoleId = cah1.ROLE_ID\r\n"
						+ "            JOIN CMSWORKFLOWTYPE cwt1 \r\n"
						+ "                  ON cwt1.WorkflowTypeId = cah1.WORKFLOWTYPEID\r\n"
						+ "                 AND cwt1.UnitId = wcw.UnitId\r\n"
						+ "            WHERE gas.status = 4\r\n"
						+ "              AND gas.WCTransID = wcw.WCTransID\r\n"
						+ "        )\r\n"
						+ "    AND NOT EXISTS (\r\n"
						+ "        SELECT 1 \r\n"
						+ "        FROM BILLAPPROVALSTATUS gas2\r\n"
						+ "        WHERE gas2.WCTransID = wcw.WCTransID\r\n"
						+ "          AND gas2.RoleId = cah.ROLE_ID\r\n"
						+ "    );\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "";
				log.info("Query to getBVRListingForApprovers "+query);
				
				 rs = jdbcTemplate.queryForRowSet(query,deptId,principalEmployerId,roleId);
			}else {
				//query=this.getAllBVRForParallel();
				query="SELECT DISTINCT \r\n"
						+ "    wcw.WCTransID,\r\n"
						+ "    wcw.UnitCode,\r\n"
						+ "    wcw.ContractorCode,\r\n"
						+ "    wcw.ContractorName,\r\n"
						+ "    cwo.SAP_WORKORDER_NUM AS WorkOrderNumber,\r\n"
						+ "    cgm.GMNAME AS Services,\r\n"
						+ "    CONVERT(VARCHAR(10), wcw.StartDate, 120) AS StartDate,CONVERT(VARCHAR(10), wcw.EndDate, 120) AS EndDate,\r\n"
						//+ "    wcw.EndDate,\r\n"
						+ "    wcw.Status\r\n"
						+ "FROM cmswagecostworkflow wcw\r\n"
						+ "JOIN CMSGENERALMASTER cgm \r\n"
						+ "       ON cgm.GMID = wcw.Services\r\n"
						+ "JOIN CMSWORKORDER cwo \r\n"
						+ "       ON cwo.WORKORDERID = wcw.WorkOrderNumber\r\n"
						+ "JOIN CMSAPPROVERHIERARCHY cah\r\n"
						+ "       ON cah.ACTION_ID = wcw.ActionId\r\n"
						+ "WHERE \r\n"
						+ "    cah.ROLE_ID = ?\r\n"
						+ "    AND wcw.Status = '3'\r\n"
						+ "    AND wcw.ContractorId = ?\r\n"
						+ "    AND wcw.UnitId = ?\r\n"
						+ "    AND NOT EXISTS (\r\n"
						+ "        SELECT 1\r\n"
						+ "        FROM BILLAPPROVALSTATUS gas\r\n"
						+ "        WHERE gas.WCTransID = wcw.WCTransID\r\n"
						+ "          AND gas.RoleId = ?\r\n"
						+ "    );\r\n"
						+ "";
				log.info("Query to getBVRListingForApprovers "+query);
				 rs = jdbcTemplate.queryForRowSet(query,roleId,deptId,principalEmployerId,roleId);
			}
			
			while(rs.next()) {
				CMSWageCostDTO d  = new CMSWageCostDTO();
				d.setWcTransId(Long.parseLong(rs.getString("WCTransID")));
				d.setUnitCode(rs.getString("UnitCode"));
				d.setContractorCode(rs.getString("ContractorCode"));
				d.setContractorName(rs.getString("ContractorName"));
				d.setWorkOrderNumber(rs.getString("WorkOrderNumber"));
				d.setStartDate(rs.getString("StartDate"));
				d.setEndDate(rs.getString("EndDate"));
				d.setStatus(rs.getInt("Status"));
				d.setServices(rs.getString("Services"));
				String status =String.valueOf(rs.getInt("Status"));
				if(status.equals(GatePassStatus.APPROVALPENDING.getStatus())) {
					d.setStatusValue("Approval Pending");
				}else if(status.equals(GatePassStatus.APPROVED.getStatus())) {
					d.setStatusValue("Approved");
				}else if(status.equals(GatePassStatus.REJECTED.getStatus())) {
					d.setStatusValue("Rejected");
				}else if(status.equals(GatePassStatus.DRAFT.getStatus())) {
					d.setStatusValue("Draft");
				}
				listDto.add(d);
			}
			
			return listDto;
		}

		 public String getWorkflowType() {
			    return QueryFileWatcher.getQuery("GET_WORKFLOWTYPE");
			}
		 
@Override
public int getWorkflowType(String module,String unitId) {
	log.info("Entering into getWorkFlowTYpeNew dao method ");
	int workflowTypeId = 0;
	String query = getWorkflowType();
	log.info("Query to getWorkFlowTYpeNew "+query);
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query,module,unitId);
	if(rs.next()) {
		workflowTypeId = rs.getInt("WorkflowType");
	}
	log.info("Exiting from getWorkFlowTYpeNew dao method "+unitId);
	return workflowTypeId;
}
public String getSaveBillStatusLog() {
	return QueryFileWatcher.getQuery("SAVE_BILL_STATUSLOG");
}
@Override
public void saveBillStatusLog(BillStatusLogDto dto) {
	log.info("Entered into saveBillStatusLog for gatePassId: "+dto.getTransactionId() );

    Object[] parameters = new Object[] {dto.getTransactionId(),dto.getStatus(),dto.getComments(),dto.getUpdatedBy()};

    try {
    	String query = getSaveBillStatusLog();
        int result = jdbcTemplate.update(query,parameters );
        if (result > 0) {
           // log.info("GatePass status log saved successfully for GatePassId: " + dto.getGatePassId());
        } else {
           // log.warn("Failed to save GatePass status log for GatePassId: " + dto.getGatePassId());
        }
    } catch (Exception e) {
        //log.error("Error saving GatePass status log for GatePassId: " + dto.getGatePassId(), e);
    }
}
public String getBVRDetails() {
	return QueryFileWatcher.getQuery("GET_BVR_VIEW");
}
@Override
public CMSWageCostDTO getIndividualBVRDetails(String transactionId) {
	CMSWageCostDTO d=new CMSWageCostDTO();
	String query = getBVRDetails();
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query,transactionId);
	while(rs.next()) {
		d.setWcTransId(rs.getLong("WCTransID"));
		d.setUnitName(rs.getString("UnitName"));
		d.setUnitCode(rs.getString("UnitCode"));
		d.setContractorName(rs.getString("ContractorName"));
		d.setContractorCode(rs.getString("ContractorCode"));
		d.setWorkOrderNumber(rs.getString("WorkOrderNumber"));
		d.setServices(rs.getString("Services"));
		d.setStartDate(rs.getString("StartDate"));
		d.setEndDate(rs.getString("EndDate"));
		d.setWoValidFrom(rs.getString("WOValidFrom"));
		d.setWoValidTo(rs.getString("WOValidTo"));
		d.setStatus(rs.getInt("Status"));
		d.setBillType(rs.getString("BillType"));
		d.setActionPlan(rs.getString("ActionPlan"));
		d.setComments(rs.getString("Comments"));d.setUnitId(rs.getString("UNITID"));
	}
	return d;
	
}

public String saveFile() {
	return QueryFileWatcher.getQuery("SAVE_BILL_REPORT_FILE");
}
public String findByTransactionIdAndType() {
	return QueryFileWatcher.getQuery("FIND_TRANSACTIONID_AND_REPORTTYPE");
}
public String saveChecklist() {
	return QueryFileWatcher.getQuery("SAVE_CHECKLIST");
}
public String fetchChecklistByTransactionId() {
	return QueryFileWatcher.getQuery("FETCH_CHECKLIST_BY_TRANSACTIONID");
}
@Override
public void saveFile(BillReportFile file) {
	String sql =saveFile();
	//String sql = "INSERT INTO BillReportFiles (TransactionId, ReportType, ReportName, FileName) VALUES (?, ?, ?, ?)";
    jdbcTemplate.update(sql, file.getTransactionId(), file.getReportType(), file.getReportName(), file.getFileName());

}
@Override
public List<BillReportFile> findByTransactionIdAndType(Long transactionId, String reportType) {
	String sql =findByTransactionIdAndType();
    //String sql = "SELECT * FROM BillReportFiles WHERE TransactionId = ? AND ReportType = ?";
    return jdbcTemplate.query(sql, new Object[]{transactionId, reportType}, (rs, rowNum) -> {
        BillReportFile f = new BillReportFile();
        f.setId(rs.getInt("ID"));
        f.setTransactionId(rs.getLong("TransactionId"));
        f.setReportType(rs.getString("ReportType"));
        f.setReportName(rs.getString("ReportName"));
        f.setFileName(rs.getString("FileName"));
        return f;
    });
}
@Override
public void saveChecklist(List<ChecklistItemDTO> checklistItems, String wcTransId, String userId) {
   // String sql = "INSERT INTO BillChecklist (wcTransId, checklistId, statusValue, licenseNumber, validUpto, createdBy) " +
   //              "VALUES (?, ?, ?, ?, ?, ?)";
	String sql =saveChecklist();
    for (ChecklistItemDTO item : checklistItems) {
        jdbcTemplate.update(sql,
            wcTransId,
            item.getId(),
            item.getStatusValue(),
            item.getLicenseNumber(),
            item.getValidUpto() != null && !item.getValidUpto().isEmpty() ? java.sql.Date.valueOf(item.getValidUpto()) : null,
            userId
        );
    }
}
@Override
public List<ChecklistItemDTO> fetchChecklistByTransactionId(String wcTransId) {
	String query =fetchChecklistByTransactionId();
    //String query = "SELECT bc.CHECKPOINTNAME, cgm.GMDESCRIPTION as statusValue, bcl.licenseNumber, bcl.validUpto FROM BillChecklist bcl "
    //		+ " join BillConfigHrChecklist bc on bc.ID=bcl.checklistId  join CMSGENERALMASTER cgm on cgm.GMID = bcl.statusValue WHERE wcTransId = ?";
    SqlRowSet rs = jdbcTemplate.queryForRowSet(query,wcTransId);
    List<ChecklistItemDTO> list = new ArrayList<ChecklistItemDTO>();
    try {
	while(rs.next()) {
		 ChecklistItemDTO dto = new ChecklistItemDTO();
		 dto.setId(rs.getString("CHECKPOINTNAME"));
	        dto.setStatusValue(rs.getString("statusValue"));
	        dto.setLicenseNumber(rs.getString("licenseNumber"));
	        dto.setValidUpto(rs.getDate("validUpto") != null ? rs.getDate("validUpto").toString() : null);
	        list.add(dto);
	}}
    catch(Exception e) {
    	e.printStackTrace();
    }
    return list;
}

public String getSaveBillApprovalStatus() {
	return QueryFileWatcher.getQuery("SAVE_BILL_APPROVAL_STATUS");
}
@Override
public String approveRejectBill(ApproveRejectBillDto dto) {
	 String result = null; 


	        Object[] parameters = new Object[] {dto.getTransactionId(),dto.getApproverId(),dto.getApproverRole(),Integer.parseInt(dto.getStatus()),dto.getComments(),1,dto.getRoleId()}; 

	        try {
	        	String query = getSaveBillApprovalStatus();
	            int status = jdbcTemplate.update(query, parameters);
	            if (status > 0) {
	                result="GatePass approved/rejected successfully";
	            } else {
	                log.warn("Failed to approve/reject Bill for transactionId: " +  dto.getTransactionId());
	            }
	        } catch (Exception e) {
	            log.error("Error approving/rejecting Bill for transactionId: " +  dto.getTransactionId(), e);
	            return null;
	        }
	    

	    return result;
}

public String getUpdateBillStatusByTransactionId() {
	return QueryFileWatcher.getQuery("UPDATE_BILL_STATUS_BY_TRANSACTION_ID");
}
@Override
public synchronized boolean updateBillStatusByTransactionId(String transactionId, String status) {
	boolean res=false;
	Object[] object=new Object[]{status,transactionId};
	String query= getUpdateBillStatusByTransactionId();
	int i = jdbcTemplate.update(query,object);
	if(i>0){
		res=true;
	}
	return res;
}
public String getWorkflowTypeByTransactionIdQuery() {
	return QueryFileWatcher.getQuery("GET_BILL_WORKFLOW_TYPE_BY_TRANSACTION_ID");
}
@Override
public int getWorkFlowTYpeByTransactionId(String transactionId) {
	log.info("Entering into getWorkFlowTYpe dao method ");
	int workflowTypeId = 0;
	String query =getWorkflowTypeByTransactionIdQuery();
	log.info("Query to getWorkFlowTYpe "+query);
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query,transactionId);
	if(rs.next()) {
		workflowTypeId = rs.getInt("WorkflowType");
	}
	log.info("Exiting from getWorkFlowTYpe dao method "+transactionId);
	return workflowTypeId;
}

public String getIsLastApproverForParallel() {
	return QueryFileWatcher.getQuery("LAST_APPROVER_FOR_BILL_PAR");
}

public String getLastApproverQuery() {
	return QueryFileWatcher.getQuery("LAST_APPROVER_FOR_BILL_SEQ");
}

@Override
public boolean isLastApprover(String roleName,String unitId) {
	boolean status=false;
	
	SqlRowSet rs = jdbcTemplate.queryForRowSet(this.getLastApproverQuery(),unitId,unitId);
	if(rs.next()){
		if(roleName.equals(rs.getString("Role_Name")))
			status = true;
	}
	log.info("exit from isLastApprover method = "+status);
	return status; 
}

@Override
public boolean isLastApproverForParallel( String transactionId, String roleId,String unitId) {
    boolean status = false;

    String query = getIsLastApproverForParallel();

    try {
        // Ensure proper data type conversion
       
        int approverRoleId = Integer.parseInt(roleId);

        SqlRowSet rs = jdbcTemplate.queryForRowSet(query, unitId,transactionId, approverRoleId);
        if (rs.next()) {
            String result = rs.getString("IsLastApprover");
            status = "YES".equals(result);
        }
    } catch (NumberFormatException e) {
        log.error("Invalid number format: gatePassTypeId={}, roleId={}",  roleId, e);
    } catch (Exception e) {
        log.error("Error executing isLastApproverForParallel query", e);
    }

    log.info("Exit from isLastApproverForParallel method = " + status);
    return status;
}

}
