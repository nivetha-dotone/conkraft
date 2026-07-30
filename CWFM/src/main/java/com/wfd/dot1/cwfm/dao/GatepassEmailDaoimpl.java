package com.wfd.dot1.cwfm.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.GatepassEmailDTO;
import com.wfd.dot1.cwfm.dto.WorkOrderDTOMail;
import com.wfd.dot1.cwfm.service.GatePassToOnBoardService;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Repository
public class GatepassEmailDaoimpl implements GatepassEmailDao{

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonDaoImpl.class);
	private static final Logger log = LoggerFactory.getLogger(GatePassToOnBoardService.class.getName());
	//private static final String INSERT_GMTYPE_QUERY = "INSERT INTO CMSGMTYPE (GMTYPE) VALUES (?)";
	 @Autowired
	    private JdbcTemplate jdbcTemplate;
	 @Autowired
	    private GatepassEmailDao gatepassEmailDAO;
	 
	 public String getRegardsEmail() {
	        return QueryFileWatcher.getQuery("getRegards");
	    }
	 public String getCreateApprovalPendingRecordsQuery() {
	        return QueryFileWatcher.getQuery("GET_CREATE_APPROVAL_PENDING_RECORDS_FOR_MAILS");
	    }
	 public String getCreateApproverListMailsQuery() {
	        return QueryFileWatcher.getQuery("GET_CREATE_LIST_OF_APPROVERS_MAILS_QUERY");
	    }
	 public List<GatepassEmailDTO> getCreateApprovalPendingRecords() {
	        try {
	            log.info("create gatepass approval pending record fetch");
	            String sql =getCreateApprovalPendingRecordsQuery();
//	           String sql = "select CONCAT(COALESCE(gpm.FirstName, ''), ' ', COALESCE(gpm.LastName, '')) AS fullName,gpm.AadharNumber as aadharNumber,pe.ORGANIZATION as principalemployer,cmc.NAME as contractor,wo.SAP_WORKORDER_NUM as workorder,\r\n"
//	           		+ "cgm.GMNAME as department,cgm1.GMNAME  area,cgm2.GMNAME trade,cgm3.GMNAME as skill, ISNULL(cwc1.LICENSE_NUMBER,'') AS llNumber,cwc.LICENSE_NUMBER as wcesic,gpm.EsicNumber as esic,pe.code as unitCode,cmc.EMAILADDRESS AS ContractorMail,gpm.TransactionId as transactionid,gpm.unitid as unitId\r\n"
//	           		+ "from GATEPASSMAIN gpm\r\n"
//	           		+ "join CMSPRINCIPALEMPLOYER pe on pe.UNITID=gpm.UnitId\r\n"
//	           		+ "join CMSCONTRACTOR cmc on cmc.CONTRACTORID=gpm.ContractorId\r\n"
//	           		+ "join CMSWORKORDER wo on wo.WORKORDERID=gpm.WorkorderId \r\n"
//	           		+ "join CMSGENERALMASTER cgm on cgm.GMID=gpm.DepartmentId\r\n"
//	           		+ "join CMSGENERALMASTER cgm1 on cgm1.GMID=gpm.AreaId\r\n"
//	           		+ "join CMSGENERALMASTER cgm2 on cgm2.GMID=gpm.TradeId\r\n"
//	           		+ "join CMSGENERALMASTER cgm3 on cgm3.GMID=gpm.SkillId\r\n"
//	           		+ "left join CMSWORKORDER_LLWC cwc on cwc.WOLLID=gpm.WcEsicNo\r\n"
//	           		+ "left join CMSWORKORDER_LLWC cwc1 on cwc1.WOLLID=gpm.LLNo and cwc1.LICENSE_TYPE='LL'\r\n"
//	           		+ "where gpm.GatePassTypeId=1 and GatePassStatus=3";
	            return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
	            	GatepassEmailDTO dto = new GatepassEmailDTO();
	                dto.setFullName(rs.getString("fullName"));
	                dto.setAadhar(rs.getString("aadharNumber"));
	                dto.setPrincipalEmployer(rs.getString("principalemployer"));
	                dto.setContractor(rs.getString("contractor"));
	                dto.setWorkorder(rs.getString("workorder"));
	                dto.setDepartment(rs.getString("department"));
	                dto.setArea(rs.getString("area"));
	                dto.setTrade(rs.getString("trade"));
	                dto.setSkill(rs.getString("skill"));
	                dto.setLlNumber(rs.getString("llNumber"));
	                dto.setWcesic(rs.getString("wcesic"));
	                dto.setEsic(rs.getString("esic"));
	                dto.setUnitCode(rs.getString("unitCode"));
	                dto.setContractorMail(rs.getString("ContractorMail"));
	                dto.setTransactionid(rs.getString("transactionid"));
	                dto.setUnitId(rs.getString("unitId"));
	                return dto;
	            });
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
	 @Override
	 public Set<String> getCreateApproverMails(String unitId, String unitCode) {
		 String sql =getCreateApproverListMailsQuery();
		   // String sql =
//		            "SELECT DISTINCT mu.EmailId " +
//		            "FROM masteruser mu " +
//		            "JOIN UserRoleMapping urm ON mu.UserId = urm.UserId " +
//		            "JOIN CMSGENERALMASTER gm ON gm.GMID = urm.RoleId " +
//		            "JOIN CMSGMTYPE gty ON gty.GMTYPEID = gm.GMTYPEID " +
//		            "JOIN CMSAPPROVERHIERARCHY cah ON cah.ROLE_ID = gm.GMID " +
//		            "JOIN CMSWORKFLOWTYPE cwt ON cwt.WorkflowTypeId = cah.WORKFLOWTYPEID " +
//		            "JOIN ORGACCTSET oas ON oas.SHORTNM = mu.userAccount " +
//		            "JOIN OLACCTSETMM oasm ON oasm.ORGACCTSETID = oas.ORGACCTSETID " +
//		            "JOIN ORGLEVELENTRY ole ON ole.ORGLEVELENTRYID = oasm.ORGLEVELENTRYID " +
//		            "JOIN ORGLEVELDEF old ON old.ORGLEVELDEFID = ole.ORGLEVELDEFID " +
//		            "WHERE old.NAME = 'Principal Employer' " +
//		            "AND ole.NAME = ? " +
//		            "AND gty.GMTYPE = 'ROLE' " +
//		            "AND mu.Status = 'A' " +
//		            "AND gm.ISACTIVE = 1 " +
//		            "AND cah.ACTION_ID = 1 " +
//		            "AND cwt.UnitId = ? " +
//		            "AND cah.[INDEX] <> 0";

		    List<String> emails = jdbcTemplate.queryForList(sql,String.class,unitCode,unitId);

		    return new HashSet<>(emails);
		}
}
