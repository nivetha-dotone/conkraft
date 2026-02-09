package com.wfd.dot1.cwfm.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.ApproverStatusDTO;
import com.wfd.dot1.cwfm.dto.EntryPassStatusDto;
import com.wfd.dot1.cwfm.dto.GatePassListingDto;
import com.wfd.dot1.cwfm.enums.GatePassStatus;
import com.wfd.dot1.cwfm.enums.GatePassType;
import com.wfd.dot1.cwfm.pojo.ApprovalStatus;
import com.wfd.dot1.cwfm.pojo.ApproverInfo;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Repository
public class EntrypassStatusDaoImpl implements EntrypassStatusDao {

	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 private static final Logger log = LoggerFactory.getLogger(EntrypassStatusDaoImpl.class.getName());
	 
	 public String getApproverHierarchy() {
		    return QueryFileWatcher.getQuery("GET_APPROVER_BY_GPTID");
		}
	 public  String getApprovalStatusOfGatePass() {
		 return QueryFileWatcher.getQuery("GET_APPROVAL_STATUS_BY_TRNSID_GATEPASSID");
		}
	 
	 private List<ApproverInfo> getApproversByGatePassId(String gatePassTypeId) {
		 SqlRowSet rs = jdbcTemplate.queryForRowSet(this.getApproverHierarchy(),gatePassTypeId);
		 List<ApproverInfo> list = new ArrayList<ApproverInfo>();
		 while(rs.next()) {
			 ApproverInfo info=new ApproverInfo();
			 info.setGatePassApproverInfoId(rs.getString("hierarchy_id"));
			// info.setGatePassId(rs.getString("GatePassId"));
			 info.setIndex(rs.getInt("Index"));
			 info.setUserRole(rs.getString("Role_Name"));
			 //info.setStatus(rs.getInt("Status"));
			// info.setCreatedBy(rs.getString("CreatedBy"));
			// info.setCreatedDate(rs.getString("CreatedDate"));
			 list.add(info);
		 }
		 return list;
	}
	 
	 private List<ApprovalStatus> getApprovalStatusByGatePassId(String transactionId,String gatepassId) {
		 SqlRowSet rs = jdbcTemplate.queryForRowSet(this.getApprovalStatusOfGatePass(),transactionId,gatepassId);
		 List<ApprovalStatus> list = new ArrayList<ApprovalStatus>();
		 while(rs.next()) {
			 ApprovalStatus info=new ApprovalStatus();
			 info.setGatePassApprovalStatusId(rs.getString("GatePassApprovalStatusId"));
			 info.setTransactionId(rs.getString("TransactionId"));
			 info.setGatePassId(rs.getString("GatePassId"));
			 info.setGatePassTypeId(rs.getInt("GatePassTypeId"));
			 info.setUserRole(rs.getString("UserRole"));
			 info.setUserId(rs.getString("UserId"));
			 info.setStatus(rs.getInt("Status"));
			 info.setComments(rs.getString("Comments"));
			 info.setLastUpdatedDate(rs.getString("LastUpdatedDate"));
			 list.add(info);
		 }
		 return list;
	}
	 @Override
	 public List<GatePassListingDto> searchGatePassStatus(String transactionId, String gatepassId) {
		 
		 if ((transactionId == null || transactionId.trim().isEmpty()) &&
				    (gatepassId == null || gatepassId.trim().isEmpty())) {
				    return Collections.emptyList(); // or throw an exception
				}

		 StringBuilder sql = new StringBuilder(
			        "SELECT gpm.transactionId, gpm.gatePassId, gpm.firstName, gpm.lastName, gpm.AadharNumber " +
			        "FROM GATEPASSMAIN gpm WHERE 1=1"
			    );
			    List<Object> params = new ArrayList<>();

			    if (transactionId != null && !transactionId.trim().isEmpty()) {
			        sql.append(" AND gpm.TransactionId = ?");
			        params.add(transactionId);
			    }
			    if (gatepassId != null && !gatepassId.trim().isEmpty()) {
			        sql.append(" AND gpm.GatePassId = ?");
			        params.add(gatepassId);
			    }

			    List<GatePassListingDto> resultList = new ArrayList<>();
			    SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), params.toArray());

	     while (rs.next()) {
	         String txnId = rs.getString("transactionId");

	      // Existing GatePassListingDto setup
	         GatePassListingDto dto = new GatePassListingDto();
	         dto.setTransactionId(txnId);
	         //dto.setGatePassId(rs.getString("gatePassId"));
	         dto.setFirstName(rs.getString("firstName"));
	         dto.setLastName(rs.getString("lastName"));
	         dto.setAadhaarNumber(rs.getString("AadharNumber"));

	         // Fetch approvers and statuses
	         List<ApproverInfo> approverList = this.getApproversByGatePassId(GatePassType.CREATE.getStatus());
	         List<ApprovalStatus> approvalStatuses = this.getApprovalStatusByGatePassId(transactionId,gatepassId);

	         // Map for quick lookup
	       //  Map<String, ApprovalStatus> statusMap = approvalStatuses.stream()
	        //     .collect(Collectors.toMap(ApprovalStatus::getUserRole, status -> status));

	         // Create status DTOs and lists for approvedBy & pendingWith
	         List<ApproverStatusDTO> approverStatusList = new ArrayList<>();
	         List<String> approvedBy = new ArrayList<>();
	         List<String> pendingWith = new ArrayList<>();

	      // Build statusMap with normalized role
	         Map<String, ApprovalStatus> statusMap = approvalStatuses.stream()
	             .filter(s -> s.getUserRole() != null)
	             .collect(Collectors.toMap(
	                 s -> s.getUserRole().trim().toUpperCase(), // normalize key
	                 Function.identity(),
	                 (a, b) -> a // in case of duplicates, keep first
	             ));

	         for (ApproverInfo approver : approverList) {
	             String role = approver.getUserRole() != null ? approver.getUserRole().trim().toUpperCase() : "";

	             ApproverStatusDTO dtos = new ApproverStatusDTO();
	             dtos.setUserRole(role);

	             if (statusMap.containsKey(role)) {
	                 ApprovalStatus status = statusMap.get(role);
	                 if (status.getStatus() == 4) {  // Approved
	                     dtos.setStatus("Approved");
	                     approvedBy.add(role);
						} /*
							 * else if (status.getStatus() == 3) { // Pending dtos.setStatus("Pending");
							 * pendingWith.add(role); } else { // Rejected or others
							 * dtos.setStatus("Rejected"); }
							 */
	             } else {
	                 dtos.setStatus("Pending");
	                 pendingWith.add(role);
	             }

	             approverStatusList.add(dtos);
	         }


	         // Set final values
	        // dto.setApproverStatusList(approverStatusList);
	         dto.setApprovedBy(approvedBy);
	         dto.setPendingWith(pendingWith);

	         resultList.add(dto);
	     }
	     return resultList;
	 }

	 @Override
	 public List<GatePassListingDto> getHistoryofEP(String aadharNumber) {
	     String sql = "SELECT gpm.transactionId, gpm.gatePassId, gpm.firstName, gpm.lastName, " +
	                  "gpm.GatePassTypeId, gpm.GatePassStatus, " +
	                  "cpe.NAME AS UnitName " +
	                  "FROM GATEPASSMAIN gpm " +
	                  "JOIN CMSPRINCIPALEMPLOYER cpe ON cpe.UNITID = gpm.UnitId " +
	                  "WHERE gpm.AadharNumber = ?";

	     List<GatePassListingDto> listDto = new ArrayList<>();

	     SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, aadharNumber);

	     while (rs.next()) {
	         GatePassListingDto dto = new GatePassListingDto();
	         dto.setTransactionId(rs.getString("transactionId"));
	         dto.setGatePassId(rs.getString("gatePassId"));
	         dto.setFirstName(rs.getString("firstName"));
	         dto.setLastName(rs.getString("lastName"));
	         
	        // dto.setAadhaarNumber(rs.getString("aadharNumber")); // optional, since you're passing it

	         // Set GatePassType as human-readable name using Enum
	         String gatePassType = rs.getString("GatePassTypeId");
	         if (GatePassType.CREATE.getStatus().equals(gatePassType)) {
	             dto.setGatePassType("Create");
	         } else if (GatePassType.BLOCK.getStatus().equals(gatePassType)) {
	             dto.setGatePassType("Block");
	         } else if (GatePassType.UNBLOCK.getStatus().equals(gatePassType)) {
	             dto.setGatePassType("Unblock");
	         } else if (GatePassType.BLACKLIST.getStatus().equals(gatePassType)) {
	             dto.setGatePassType("Blacklist");
	         } else if (GatePassType.DEBLACKLIST.getStatus().equals(gatePassType)) {
	             dto.setGatePassType("Deblacklist");
	         } else if (GatePassType.CANCEL.getStatus().equals(gatePassType)) {
	             dto.setGatePassType("Cancel");
	         } else if (GatePassType.LOSTORDAMAGE.getStatus().equals(gatePassType)) {
	             dto.setGatePassType("Lost/Damage");
	         } else {
	             dto.setGatePassType("Unknown");
	         }

	         // Set GatePassStatus as readable name using Enum
	         String status = rs.getString("GatePassStatus");
	         if (GatePassStatus.DRAFT.getStatus().equals(status)) {
	             dto.setStatus("Draft");
	         } else if (GatePassStatus.SUBMIT.getStatus().equals(status)) {
	             dto.setStatus("Submit");
	         } else if (GatePassStatus.APPROVALPENDING.getStatus().equals(status)) {
	             dto.setStatus("Approval Pending");
	         } else if (GatePassStatus.APPROVED.getStatus().equals(status)) {
	             dto.setStatus("Approved");
	         } else if (GatePassStatus.REJECTED.getStatus().equals(status)) {
	             dto.setStatus("Rejected");
	         } else {
	             dto.setStatus("Unknown");
	         }

	         dto.setUnitName(rs.getString("unitName"));

	         listDto.add(dto);
	     }

	     return listDto;
	 }
	@Override
	public List<EntryPassStatusDto> getEntryPassStatusReport(String unitId) {
		String sql = "select  ROW_NUMBER() OVER (\r\n"
				+ "        ORDER BY TransactionId\r\n"
				+ "    ) AS SNo,TransactionId,EntryPassNo,UnitId,PrincipalEmployer,ContractWorkmenCode,FirstName,LastName,Department,VendorCode,\r\n"
				+ " VendorName,WorkOrder,EicNumber,EntryPassAction,EntryPassType,Status, LastApprovers,NextApprovers\r\n"
				+ " from VW_GATEPASS_APPROVAL_REPORT \r\n"
				+ " where unitid=?";

   List<EntryPassStatusDto> listDto = new ArrayList<>();

   SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, unitId);

   while (rs.next()) {
	   EntryPassStatusDto dto = new EntryPassStatusDto();
	   dto.setTransactionId(rs.getString("TransactionId"));
	   dto.setEntryPassNo(rs.getString("EntryPassNo"));
	   dto.setUnitId(rs.getString("UnitId"));
	   dto.setPrincipalEmployer(rs.getString("PrincipalEmployer"));
	   dto.setContractWorkmenCode(rs.getString("ContractWorkmenCode"));
	   dto.setFirstName(rs.getString("FirstName"));
	   dto.setLastName(rs.getString("LastName"));
	   dto.setDepartment(rs.getString("Department"));
	   dto.setVendorCode(rs.getString("VendorCode"));
	   dto.setVendorName(rs.getString("VendorName"));
	   dto.setWorkOrder(rs.getString("WorkOrder"));
	   dto.setEicNumber(rs.getString("EicNumber"));
	   dto.setEntryPassAction(rs.getString("EntryPassAction"));
	   dto.setEntryPassType(rs.getString("EntryPassType"));
	   dto.setStatus(rs.getString("Status"));
	   dto.setLastApprover(
			    Optional.ofNullable(rs.getString("LastApprovers")).orElse("-")
			);

	   dto.setNextApprover(Optional.ofNullable(
			   rs.getString("NextApprovers")).orElse("-"));
	   dto.setSno(rs.getString("SNo"));
       listDto.add(dto);
   }

   return listDto;
	}







}
