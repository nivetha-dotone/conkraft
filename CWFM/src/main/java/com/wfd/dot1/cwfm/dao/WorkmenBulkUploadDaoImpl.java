package com.wfd.dot1.cwfm.dao;

import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.enums.GatePassStatus;
import com.wfd.dot1.cwfm.enums.GatePassType;
import com.wfd.dot1.cwfm.pojo.ContractorRegistration;
import com.wfd.dot1.cwfm.pojo.WorkmenBulkUpload;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
import com.wfd.dot1.cwfm.enums.GatePassStatus;
import com.wfd.dot1.cwfm.enums.GatePassType;
import com.wfd.dot1.cwfm.enums.WorkFlowType;
@Repository
public class WorkmenBulkUploadDaoImpl implements WorkmenBulkUploadDao{
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	@Autowired
    private WorkmenBulkUploadDao workmenUploadDao;
	
	 public String getAllWorkmenBulkUploadList() {
		    return QueryFileWatcher.getQuery("GET_ALL_WORKMEN_BULK_UPLOAD_LIST");
		}
	 public String getAllWorkmenBulkUploadListByTransacionId() {
		    return QueryFileWatcher.getQuery("GET_ALL_WORKMEN_BULK_UPLOAD_BY_TRANSACTIONID");
		}
	 public String saveToGatePassMainWorkmenBulkupload() {
		    return QueryFileWatcher.getQuery("SAVE_TO_GATEPASSMAIN_WORKMEN_BULK_UPLOAD");
		}
	 public String updateRecordStatusByTransactionId() {
		    return QueryFileWatcher.getQuery("UPDATE_RECORDSTATUS_BY_TRANSACTIONID_WORKMEN_BULKUPLOAD");
		}
	 public String updateRecordProcessedByTransactionId() {
		    return QueryFileWatcher.getQuery("UPDATE_RECORDPROCESSED_BY_TRANSACTIONID_WORKMEN_BULKUPLOAD");
		}
	 public String isAadharExistsInWorkmenBulkUpload() {
		    return QueryFileWatcher.getQuery("IS_AADHAR_EXISTS_IN_WORKMENBULK_UPLOAD");
		}
	 public String isAadharExistsInGatepass() {
		    return QueryFileWatcher.getQuery("IS_AADAHAR_EXISTS_IN_GATEPASS");
		}
	 public String workorderValidityCheck() {
		    return QueryFileWatcher.getQuery("WORKORDER_VALIDITY_CHECK");
		}

	@Override
	public List<WorkmenBulkUpload> getAllWorkmenBulkUploadData() {
	List<WorkmenBulkUpload> peList= new ArrayList<WorkmenBulkUpload>();
	 String query=getAllWorkmenBulkUploadList();
	//String query = "SELECT cribu.TransactionID,cribu.FirstName,cribu.LastName,cgm.GMNAME as Gender,cribu.DOB,cribu.AadharNumber,cmsc.NAME AS vendorcode,cpe.NAME AS unitcode,cribu.RecordStatus\r\n"
		//	+ "FROM CMSRequestItemBulkUpload cribu\r\n"
		//	+ "LEFT JOIN CMSPRINCIPALEMPLOYER cpe ON cpe.UnitId = TRY_CAST(cribu.UnitId AS BIGINT)\r\n"
		//	+ "LEFT JOIN CMSGENERALMASTER cgm ON cgm.GMID = TRY_CAST(cribu.Gender AS BIGINT)\r\n"
		//	+ "LEFT JOIN CMSCONTRACTOR cmsc ON cmsc.ContractorId = TRY_CAST(cribu.ContractorId AS BIGINT)\r\n"
		//	+ "LEFT JOIN CMSWORKORDER cwo ON cwo.WorkorderId = TRY_CAST(cribu.WorkorderId AS BIGINT)\r\n"
		//	+ "WHERE  cribu.RecordProcessed='N' and cribu.UpdatedDate >= CAST(DATEADD(DAY, -3, GETDATE()) AS DATE)";
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
	while(rs.next()) {
		WorkmenBulkUpload pe = new WorkmenBulkUpload();
		pe.setTransactionid(rs.getString("TransactionId"));
		//pe.setGatepassid((rs.getString("GatePassId")));
		pe.setFirstName(rs.getString("firstName"));
		pe.setLastName(rs.getString("lastName"));
		pe.setGender(rs.getString("gender"));
		pe.setDateOfBirth(rs.getString("dob"));
		pe.setAadhaarNumber(rs.getString("AadharNumber"));
		pe.setVendorCode(rs.getString("VendorCode"));
		pe.setUnitCode(rs.getString("unitcode"));
		pe.setRecordstatus(rs.getString("RecordStatus"));
		peList.add(pe);
	}
	return peList;
	}

	@Override
	public WorkmenBulkUpload getByTransactionId(int transactionId) {
		 String query=getAllWorkmenBulkUploadListByTransacionId();
	    //String sql = "select cribu.AadharNumber as aadhaarNumber,cribu.FirstName as firstName,cribu.LastName as lastName,cribu.DOB as dateOfBirth,cgmg.GMNAME as gender,\r\n"
	    //		+ "	    		 cribu.RelativeName as relationName,cribu.IdMark as idMark,cribu.MobileNumber as mobileNumber,   \r\n"
	    //		+ "	    		 cribu.MaritalStatus as maritalStatus,cpe.CODE as unitCode,cmsc.CODE as vendorCode,cmswo.NAME as workorderNumber,   \r\n"
	    //		+ "	    		 cmst.NAME as trade,cmss.SKILLNM as skill,cmsgmdep.GMNAME as department,cmsgma.GMNAME as area,mu.userAccount  AS EICNumber,cribu.NatureOfJob as natureOfWork,   \r\n"
	    //		+ "	    		 ccwc.WC_CODE as ECnumber,cribu.HazardousArea as hazardousArea,cmsgmaa.GMNAME as accessArea,   \r\n"
	    //		+ "	    		 cribu.uanNumber,cribu.healthCheckDate,cmsgmb.GMNAME as bloodGroup,cribu.Accommodation as accommodation,cmsgmac.GMNAME as academic,cribu.Technical as technical,   \r\n"
	    //		+ "	    		 cribu.IfscCode as bankName,cribu.AccountNumber as accountNumber,cribu.EmergencyContactNumber as emergencyNumber,cribu.EmergencyContactName  as emergencyName   \r\n"
	    //		+ "	    		 ,cribu.doj,cribu.pfNumber,cribu.esicNumber,cribu.policeVerificationDate,cribu.pfApplicable,cmsgmz.GMNAME as zone,cribu.Address as address	,ccwc1.WCID as LLNumber			\r\n"
	    //		+ "				 from CMSRequestItemBulkUpload cribu   \r\n"
	    //		+ "	    		 left join CMSPRINCIPALEMPLOYER cpe on cpe.unitid=cribu.UnitId left join CMSCONTRACTOR cmsc on cmsc.CONTRACTORID=cribu.ContractorId left join CMSWORKORDER cmswo on cmswo.WORKORDERID=cribu.WorkorderId \r\n"
	   // 		+ "	    		 left join CMSTRADE cmst on cmst.TRADEID = cribu.TradeId   left join CMSSKILL cmss on cmss.skillid=cribu.SkillId \r\n"
	    //		+ "	    		 left join CMSGENERALMASTER cmsgmdep on cmsgmdep.GMID=cribu.DepartmentId left join CMSGENERALMASTER cmsgma on cmsgma.GMID=cribu.AreaId \r\n"
	    //		+ "	    		 left join CMSGENERALMASTER cmsgmaa on cmsgmaa.GMID=cribu.AccessAreaId left join CMSGENERALMASTER cmsgmb on cmsgmb.GMID=cribu.BloodGroupId \r\n"
	   // 		+ "	    		 left join CMSGENERALMASTER cmsgmac on cmsgmac.GMID=cribu.AcademicId left join CMSGENERALMASTER cmsgmz on cmsgmz.GMID=cribu.zoneid LEFT JOIN CMSGENERALMASTER cgmg ON cgmg.GMID = cribu.gender \r\n"
	   // 		+ "	    		 LEFT JOIN MASTERUSER mu ON mu.UserId = cribu.EicId left join CMSCONTRACTOR_WC ccwc on ccwc.WCID= try_cast(cribu.WcEsicNo as bigint)		\r\n"
	   // 		+ "				 left join CMSCONTRACTOR_WC ccwc1 on ccwc1.WCID = try_cast(cribu.LLNumber as bigint) \r\n"
	   // 		+ "				 where TransactionID=?";
	    return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(WorkmenBulkUpload.class), transactionId);
	}
	 
	 public String getMaxGatePassIdQuery() {
		 return QueryFileWatcher.getQuery("GET_NEXT_GATEPASSID_SEQ");
	 }
	 
	private String generateGatePassId() {
	    String gatePassId = null;
	    String maxTestReqId = null;	
	    DecimalFormat decimalFormat = new DecimalFormat("00");
	    try {
	    	String query = getMaxGatePassIdQuery();
	        SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
	        if(rs.next()){
				
				maxTestReqId=String.valueOf(rs.getInt(1));
				//log.info("maxTestReqId"+maxTestReqId);
				
			}
	        if(maxTestReqId==null  || maxTestReqId.equals("0")){
				
	        	gatePassId ="GP700001";
			}else{
							
				long incrMaxId = Long.parseLong(maxTestReqId)+1;
				gatePassId = "GP" + decimalFormat.format(incrMaxId);
			}
	    } catch (Exception e) {
	       // log.error("Error generating GatePassId", e);
	    }
	    return gatePassId;
	}
	
	public String getNextTransactionId() {
		String transactionId=null;
		try {
			 transactionId = jdbcTemplate.queryForObject("EXEC GetNextGatepassTransactionId", String.class);


	}catch(Exception e) {
		 System.out.println("Failed to fetch transaction ID: " + e.getMessage());
		e.printStackTrace();
	}
	    return transactionId;
	}
	@Override
	public void saveToGatePassMain(WorkmenBulkUpload data,String createdBy) {
		String gatePassId = this.generateGatePassId();
		
		String transactionId=getNextTransactionId();
		
		String sql=saveToGatePassMainWorkmenBulkupload();
		
	    //String sql = "INSERT INTO  GATEPASSMAIN (TransactionId, GatePassId, GatePassTypeId, GatePassStatus, AadharNumber, FirstName, LastName, DOB, Gender, RelativeName, IdMark, MobileNumber,\r\n"
	    //		+ "MaritalStatus, UnitId, ContractorId, WorkorderId, TradeId, SkillId, DepartmentId, AreaId, EicId, NatureOfJob, WcEsicNo, HazardousArea  \r\n"
	    	//	+ ",  AccessAreaId ,  UanNumber,  HealthCheckDate,  BloodGroupId,  Accommodation,  AcademicId ,  Technical ,  IfscCode,  AccountNumber,  EmergencyContactNumber  \r\n"
	    		//+ ",  EmergencyContactName, WorkmenWageCategoryId, BonusPayoutId, ZoneId, Basic, DA, HRA, WashingAllowance, OtherAllowance  \r\n"
	    		//+ ",  UniformAllowance,  PfCap,  AadharDocName ,  PhotoName ,  BankDocName ,  PoliceVerificationDocName,  IdProof2DocName,  MedicalDocName,  EducationDocName,  Form11DocName  \r\n"
	    		//+ ",  TrainingDocName,  OtherDocName,  UpdatedDate,  UpdatedBy,  WorkFlowType,  Comments,  Address,  DOJ,  DOT,  pfnumber,  esicNumber,  policeverificationDate  \r\n"
	    		//+ ",  OnboardingType ,  pfapplicable,LLNo )\r\n"
	    		//+ "VALUES ( ?,?,1,4,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,\r\n"
	    		//+ "?,?,?,?,?,?,?,?,?,?,?,null,null,?,'0.00','0.00','0.00','0.00','0.00','0.00','Yes',null,null,null,null,null,null,null,null,\r\n"
	    		//+ "null,null,getdate(),7,null,null,?,?,null,?,?,?,'regular',?,?)";
	    jdbcTemplate.update(sql, transactionId,gatePassId,
	    		data.getAadhaarNumber(),  data.getFirstName(),data.getLastName(), data.getDateOfBirth(), data.getGender(), data.getRelationName(),data.getIdMark(),data.getMobileNumber(),
	            data.getMaritalStatus(), data.getUnitCode(), data.getVendorCode(), data.getWorkorderNumber(),data.getTrade(),data.getSkill(),data.getDepartment(),data.getArea(),data.getEICNumber(),data.getNatureOfWork(),data.getECnumber(),data.getHazardousArea(),
	            data.getAccessArea(),data.getUanNumber(),data.getHealthCheckDate(),data.getBloodGroup(),data.getAccommodation(),data.getAcademic(),data.getTechnical(),data.getBankName(),data.getAccountNumber(),data.getEmergencyNumber(),
	            data.getEmergencyName(),data.getZone(),createdBy,data.getAddress(),data.getDoj(),data.getPfNumber(),data.getEsicNumber(),data.getPoliceVerificationDate(),data.getPfApplicable(),data.getLLnumber());
	}

	@Override
	public void updateRecordStatusByTransactionId(int txnId, String combinedErrors) {
		String sql=updateRecordStatusByTransactionId();
	    //String sql = "UPDATE CMSRequestItemBulkUpload SET RecordStatus = ? WHERE TransactionID = ?";
	    jdbcTemplate.update(sql, combinedErrors, txnId);
	}

	@Override
	public void updateRecordProcessedByTransactionId(Integer txnId) {
		String sql=updateRecordProcessedByTransactionId();
	   // String sql = "UPDATE CMSRequestItemBulkUpload SET RecordProcessed = 'Y' WHERE TransactionID = ?";
	    jdbcTemplate.update(sql, txnId);
	}

	@Override
	public boolean isAadharExists(String aadharNumber) {
		String sql1=isAadharExistsInWorkmenBulkUpload();
		String sql2=isAadharExistsInGatepass();
	   // String sql1 = "SELECT COUNT(*) FROM CMSRequestItemBulkUpload WHERE AadharNumber = ? and RecordProcessed !='N'";
	   // String sql2 = "SELECT COUNT(*) FROM GatePassMain WHERE AadharNumber = ?";

	    Integer count1 = jdbcTemplate.queryForObject(sql1, new Object[]{aadharNumber}, Integer.class);
	    Integer count2 = jdbcTemplate.queryForObject(sql2, new Object[]{aadharNumber}, Integer.class);

	    return (count1 != null && count1 > 0) || (count2 != null && count2 > 0);
	}
	
	@Override
	public LocalDate workorderValidityCheck(String workorderNumber) {
		String sql=workorderValidityCheck();
	   // String sql = "SELECT VALIDDT FROM CMSWORKORDER WHERE NAME = ?";

	    try {
	        // Query and return the date directly
	        Date validityDate = jdbcTemplate.queryForObject(sql, new Object[]{workorderNumber}, Date.class);

	        if (validityDate != null) {
	            return validityDate.toLocalDate(); // Convert java.sql.Date to LocalDate
	        }
	    } catch (EmptyResultDataAccessException e) {
	        // No workorder found for this number
	        return null;
	    }
	    return null;
	}


}
