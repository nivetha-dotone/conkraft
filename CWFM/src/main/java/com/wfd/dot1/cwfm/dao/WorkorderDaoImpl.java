package com.wfd.dot1.cwfm.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.Workorder;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
@Repository
public class WorkorderDaoImpl implements WorkorderDao{

	
	private static final Logger log = LoggerFactory.getLogger(WorkorderDaoImpl.class.getName());
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	
	 
	 public String getWOById() {
		    return QueryFileWatcher.getQuery("GET_WO_BY_ID");
		}
	 
	 public String getWOLicenseInfo() {
		    return QueryFileWatcher.getQuery("GET_WO_LICENSE_INFO");
		}
	@Override
	public Workorder getWorkorderById(String id) {
		String query=getWOById();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,id);
        
        if (rs.next()) {
            Workorder wo = new Workorder();
            wo.setWorkorderId(rs.getString("WORKORDERID"));
            wo.setName(rs.getString("NAME"));
            wo.setSapWorkorderNumber(rs.getString("SAP_WORKORDER_NUM"));
            wo.setContractorId(rs.getString("CONTRACTORID"));
            wo.setValidFrom(rs.getString("VALIDFROM"));
            wo.setValidTo(rs.getString("VALIDDT"));
            wo.setUnitId(rs.getString("UNITID"));
            wo.setTypeId(rs.getString("SAP_TYPE"));
            wo.setDepId(rs.getString("DESCRIPTION"));
            wo.setSecId(rs.getString("SECID"));
            wo.setStatus(String.valueOf(rs.getInt("STATUS")));
            wo.setEicNumber(rs.getString("EICNUMBER"));
            wo.setGlCode(rs.getString("GLCODE"));
            wo.setCostCenter(rs.getString("COSTCENTER"));
            return wo;
        }
    

return null;
	}
	
	@Override
    public Map<String, Workorder> getWorkOrdersByCodes(List<String> cleanedCodes) {
        if (cleanedCodes == null || cleanedCodes.isEmpty()) return Collections.emptyMap();

        String inSql = String.join(",", Collections.nCopies(cleanedCodes.size(), "?"));

        String sql = "select WORKORDERID,SAP_WORKORDER_NUM from CMSWORKORDER where SAP_WORKORDER_NUM IN (" + inSql + ")";

        List<Workorder> list = jdbcTemplate.query(sql, cleanedCodes.toArray(), (rs, rowNum) -> {
        	Workorder wo = new Workorder();
            wo.setWorkorderId(String.valueOf(rs.getInt("WORKORDERID")));
            wo.setSapWorkorderNumber(rs.getString("SAP_WORKORDER_NUM"));
            return wo;
        });

        return list.stream().collect(Collectors.toMap(Workorder::getSapWorkorderNumber, wo -> wo));
    }
	
	 public String getWorkorderLicenseInfo() {
		    return QueryFileWatcher.getQuery("GET_WORKORDER_LICENSE_INFO");
		}
	 
	@Override
	public List<Workorder> getWorkorderLicenseInfo(String workorderId) {
		String sql=getWorkorderLicenseInfo();
	  //  String sql = "select cwln.JOB,cmssm.SERVICECODE,cmsgm1.gmname as TRADE,cmsgm2.gmname as SKILL,cwln.QTY,cwln.RATE,"
	   // 		+ "cwln.SERVICE_LN_ITEM_NUM,cwln.WBS_ELEMENT,cwln.UOM from CMSWORKORDERLN cwln "
	   // 		+ "join CMSSERVICEMASTER cmssm on cmssm.SERVICEID=cwln.SERVICEID "
	   // 		+ "join CMSGENERALMASTER cmsgm1 on cmsgm1.GMID=cmssm.TRADEID "
	   // 		+ "join CMSGENERALMASTER cmsgm2 on cmsgm2.GMID=cmssm.SKILLID where cwln.WORKORDERID=?";
	    return jdbcTemplate.query(sql, new Object[]{workorderId}, new BeanPropertyRowMapper<>(Workorder.class));
	}
	public String getWorkOrdersByContractorIdAndUnitId() {
	    return QueryFileWatcher.getQuery("GET_WORKORDERS_BY_CONTRACTORID_AND_UNITID");
	}
	@Override
	public List<Workorder> getWorkOrdersByContractorIdAndUnitId(String contractorId, String unitId) {
		log.info("Entering into getAllWorkordersBasedOnPEAndContractor dao method "+unitId+" "+contractorId);
		List<Workorder> woList= new ArrayList<Workorder>();
		String query =getWorkOrdersByContractorIdAndUnitId();
		//String query="select cmwo.WORKORDERID, SAP_WORKORDER_NUM as WORKORDERNUMBER,cwln.JOB as JOB,cwty.SAP_TYPE as WORKORDERTYPE,SECID,VALIDFROM,VALIDDT,UNITID,cmwo.STATUS  from CMSWORKORDER cmwo\r\n"
		//		+ "	join CMSWORKORDERLN cwln on cwln.WORKORDERID=cmwo.WORKORDERID\r\n"
		//		+ "	join CMSWORKORDERTYP cwty on cwty.TYPEID = cmwo.TYPEID WHERE cmwo.CONTRACTORID=? and cmwo.UNITID=?";
		log.info("Query to getAllWorkordersBasedOnPEAndContractor "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,contractorId,unitId);
		while(rs.next()) {
			Workorder wo = new Workorder();
			wo.setWorkorderId(rs.getString("WORKORDERID"));
            wo.setSapWorkorderNumber(rs.getString("WORKORDERNUMBER"));
            wo.setJob(rs.getString("JOB"));
            wo.setTypeId(rs.getString("WORKORDERTYPE"));;
            wo.setSecId(rs.getString("SECID"));
            wo.setValidFrom(rs.getString("VALIDFROM"));
            wo.setValidTo(rs.getString("VALIDDT"));
            wo.setUnitId(rs.getString("UNITID"));
            wo.setStatus(String.valueOf(rs.getInt("STATUS")));
			woList.add(wo);
		}
		log.info("Exiting from getAllWorkordersBasedOnPEAndContractor dao method "+woList.size());
		return woList;
	}

	
	

}
