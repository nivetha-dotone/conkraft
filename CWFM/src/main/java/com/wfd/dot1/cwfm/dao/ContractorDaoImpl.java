package com.wfd.dot1.cwfm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.ApproveRejectBillDto;
import com.wfd.dot1.cwfm.dto.ApproveRejectContRenewDto;
import com.wfd.dot1.cwfm.dto.CMSWageCostDTO;
import com.wfd.dot1.cwfm.enums.GatePassStatus;
import com.wfd.dot1.cwfm.enums.GatePassType;
import com.wfd.dot1.cwfm.enums.WorkFlowType;
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
import com.wfd.dot1.cwfm.queries.ContractorQueryBank;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
import java.sql.Timestamp;
import java.time.LocalDate;

@Repository
public class ContractorDaoImpl implements ContractorDao{
	
	private static final Logger log = LoggerFactory.getLogger(ContractorDaoImpl.class.getName());
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	 public String getContractorByIdQuery() {
		    return QueryFileWatcher.getQuery("GET_CONTRACOTR_BY_ID");
		}
	 
	 public String getContrpemmByPeAndCont() {
		    return QueryFileWatcher.getQuery("GET_CONTRPEMM_BY_PE_AND_CONT");
		}
	 
	 public String getMappingByPeCont() {
		    return QueryFileWatcher.getQuery("GET_MAPPING_BY_PE_CONT");
		}
	 
	 public String getWorkOrderQuery() {
		    return QueryFileWatcher.getQuery("GET_ALL_WORKORDER_BY_CONT");
		}
	 public String saveContractorDetails() {
		    return QueryFileWatcher.getQuery("SAVE_INSERT_CONTRACTOR_DETAILS");
		}
	 public String getAllContractors() {
		    return QueryFileWatcher.getQuery("GET_ALL_CONTRACTORS");
		}
	 public String getAllRenewalContractorsList() {
		    return QueryFileWatcher.getQuery("GET_ALL_CONTR_RENEWAL_LIST");
		}
	 public String getContractorsDetails() {
		    return QueryFileWatcher.getQuery("GET_CONTRACTOR_DETAILS");
		}
	 public String getAllContractorsDetailsView() {
		    return QueryFileWatcher.getQuery("GET_ALL_CONTRACTOR_DETAIL_VIEW");
		}
	 public String getAllRenewalContractors() {
		    return QueryFileWatcher.getQuery("GET_ALL_RENEWAL_LIST_CONTRACTORS");
		}
	 public String getAllContractorsAdditionalDetails() {
		    return QueryFileWatcher.getQuery("GET_ALL_CONTRACTOR_ADD_DETAIL_VIEW");
		}
	 public String getRenewalView() {
		    return QueryFileWatcher.getQuery("GET_RENEWAL_CONTRACTORS_VIEW");
		}
	 public String getRenewalContractorsView() {
		    return QueryFileWatcher.getQuery("GET_RENEW_CONTRACTOR_ADD_DETAIL_VIEW");
		}
	 public String setRenewalDetails() {
		    return QueryFileWatcher.getQuery("SET_RENEWAL_DETAILS");
		}
	 public String setuniqueregistrationid() {
		    return QueryFileWatcher.getQuery("SET_UNIQUE_REGISTRATION_ID");
		}
	 public String getGMID() {
		    return QueryFileWatcher.getQuery("GET_GMID_FOR_ACTIONID_IN_CONTRACTOR");
		}
	 public String saveRegistration() {
		    return QueryFileWatcher.getQuery("SAVE_CONTRACTOR_REGISTRATION");
		}
	 public String saveContractorPemm() {
		    return QueryFileWatcher.getQuery("SAVE_CONTRACTORPEMM");
		} 
	 public String getContractorRenewList() {
		    return QueryFileWatcher.getQuery("GET_CONTRACTOR_RENEW_LIST");
		}
	 public String savePolicies() {
		    return QueryFileWatcher.getQuery("SAVE_CONTRACTORREGPOLICY");
		}	
	 public String saveContractorWC() {
		    return QueryFileWatcher.getQuery("SAVE_CONTRACTOR_WC");
		}	
	 public String getContractorRegistration() {
		    return QueryFileWatcher.getQuery("GET_CONTRACTOR_REGISTRATION_DETAILS");
		}
	 public String getPoliciesByContractorRegId() {
		    return QueryFileWatcher.getQuery("GET_POLICIES_BY_CONTRACTORREGID");
		}
	 public String getLLWCByContractorRegId() {
		    return QueryFileWatcher.getQuery("GET_LLWC_BY_CONTRACTORREGID");
		}

	@Override
	public Contractor getContractorById(String contractorId) {
		String query=getContractorByIdQuery();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,contractorId);
        
                if (rs.next()) {
                    Contractor contractor = new Contractor();
                    contractor.setContractorId(rs.getString("CONTRACTORID"));
                    contractor.setContractorName(rs.getString("NAME"));
                    contractor.setContractorAddress(rs.getString("ADDRESS"));
                    contractor.setContractorCode(rs.getString("CODE"));
                    //contractor.setMobileNumber(rs.getLong("mobilenumber"));
                    String mobileStr = rs.getString("mobilenumber");

                    if (mobileStr != null && !mobileStr.trim().isEmpty()) {
                        contractor.setMobileNumber(Long.parseLong(mobileStr));
                    } else {
                        contractor.setMobileNumber(-1L); // sentinel value
                    }

                    contractor.setEmailaddress(rs.getString("EMAILADDRESS"));
                    contractor.setManagerAddress(rs.getString("MANAGERADDRESS"));
                    contractor.setBlocked(rs.getInt("ISBLOCKED") == 1);
                    return contractor;
                }
        return null;
    }

	@Override
	public List<Workorder> getWorkOrdersByContractorIdAndUnitId(String contractorId, String unitId) {
		log.info("Entering into getAllWorkordersBasedOnPEAndContractor dao method "+unitId+" "+contractorId);
		List<Workorder> woList= new ArrayList<Workorder>();
		String query=getWorkOrderQuery();
		log.info("Query to getAllWorkordersBasedOnPEAndContractor "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId,contractorId);
		while(rs.next()) {
			Workorder wo = new Workorder();
			wo.setWorkorderId(rs.getString("WORKORDERID"));
            wo.setSapWorkorderNumber(rs.getString("SAP_WORKORDER_NUM"));
            wo.setContractorId(rs.getString("CONTRACTORID"));
           // wo.setValidFrom(rs.getString("VALIDFROM"));
           // wo.setValidTo(rs.getString("VALIDDT"));
            Timestamp fromTs = rs.getTimestamp("VALIDFROM");
            if (fromTs != null) {
                wo.setValidFrom(fromTs.toLocalDateTime().toLocalDate().toString());
            }

            Timestamp toTs = rs.getTimestamp("VALIDDT");
            if (toTs != null) {
                wo.setValidTo(toTs.toLocalDateTime().toLocalDate().toString());
            }
            wo.setUnitId(rs.getString("UNITID"));
            wo.setTypeId(String.valueOf(rs.getInt("TYPEID")));
            wo.setDepId(String.valueOf(rs.getInt("DEPID")));
            wo.setSecId(String.valueOf(rs.getInt("SECID")));
            wo.setStatus(String.valueOf(rs.getInt("STATUS")));
            wo.setClassification(rs.getString("CLASSIFICATION"));
         // ✅ DYNAMIC ACTIVE WORKMEN COUNT
            int activeCount = getWorkorderActiveWorkmenCount(
                    contractorId,
                    unitId,
                    wo.getWorkorderId()
            );
            wo.setActiveWorkmenCount(activeCount);
			woList.add(wo);
		}
		log.info("Exiting from getAllWorkordersBasedOnPEAndContractor dao method "+woList.size());
		return woList;
	}
	
	@Override
	public List<CmsContractorWC> getcontrsByContractorIdAndUnitIdAndLicenseType(String contractorId,
			String principalEmployerId, String string) {
		log.info("Entering into getcontrsByContractorIdAndUnitIdAndLicenseType dao method  "+contractorId);
		List<CmsContractorWC> contrWcList= new ArrayList<CmsContractorWC>();
		log.info("Query to getcontrsByContractorIdAndUnitIdAndLicenseType "+getMappingByPeCont());
		SqlRowSet rs = jdbcTemplate.queryForRowSet(getMappingByPeCont(),contractorId,principalEmployerId,string);
		while(rs.next()) {
			CmsContractorWC contr = new CmsContractorWC();
			contr.setWcCode(rs.getString("WC_CODE"));
			contr.setContractorId(rs.getString("CONTRACTORID"));
             contr.setUnitId(rs.getString("UNITID"));
             //contr.setNatureOfId(rs.getInt("NATURE_OF_ID"));
             String natureOfJobStr = rs.getString("NATURE_OF_ID");
             if (natureOfJobStr != null && !natureOfJobStr.trim().isEmpty()) {
                 contr.setNatureOfId(Integer.parseInt(natureOfJobStr));
             }
             //contr.setWcFromDtm(rs.getString("WC_FROM_DTM"));
             //contr.setWcToDtm(rs.getString("WC_TO_DTM"));
             Timestamp fromTs = rs.getTimestamp("WC_FROM_DTM");
             if (fromTs != null) {
                 contr.setWcFromDtm(fromTs.toLocalDateTime().toLocalDate().toString());
             }

             Timestamp toTs = rs.getTimestamp("WC_TO_DTM");
             if (toTs != null) {
                 contr.setWcToDtm(toTs.toLocalDateTime().toLocalDate().toString());
             }
             contr.setWcTotal(rs.getInt("WC_TOTAL"));
             contr.setDeleteSw(rs.getInt("DELETE_SW"));
             contr.setLicenceType(rs.getString("LICENCE_TYPE"));
             contr.setIsVerified(rs.getString("ISVERIFIED"));
             contr.setAttachmentNm(rs.getString("ATTACHMENTNM"));
             contr.setExtendToSubcontractor(rs.getInt("EXTENDTOSUBCONTRACTOR"));
          // ✅ DYNAMIC ACTIVE WORKMEN COUNT
             int activeCount = getLLActiveWorkmenCount(
                     contractorId,
                     principalEmployerId,
                     contr.getWcCode()
             );
             contr.setActiveWorkmenCount(activeCount);
            contrWcList.add(contr);
		}
		log.info("Exiting from getcontrsByContractorIdAndUnitIdAndLicenseType dao method "+contrWcList.size());
		return contrWcList;
	}

	@Override
	public List<CmsContractorWC> getMappingsByContractorIdAndUnitIdAndLicenseTypes(String contractorId,
			String principalEmployerId, List<String> licenseTypes) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM CMSContractor_WC WHERE CONTRACTORID = ? AND UNITID = ? AND LICENCE_TYPE IN (");
	    for (int i = 0; i < licenseTypes.size(); i++) {
	        if (i != 0) {
	            sqlBuilder.append(", ");
	        }
	        sqlBuilder.append("?");
	    }
	    sqlBuilder.append(")");
	    String finalQuery = sqlBuilder.toString();
	    List<Object> params = new ArrayList<>();
	    params.add(contractorId);
	    params.add(principalEmployerId);
	    params.addAll(licenseTypes);
	    SqlRowSet rs = jdbcTemplate.queryForRowSet(finalQuery, params.toArray());
	    List<CmsContractorWC> contrWcList = new ArrayList<>();
	    while(rs.next()) {
			CmsContractorWC contr = new CmsContractorWC();
			contr.setWcCode(rs.getString("WC_CODE"));
			contr.setContractorId(rs.getString("CONTRACTORID"));
             contr.setUnitId(rs.getString("UNITID"));
            // contr.setNatureOfId(rs.getInt("NATURE_OF_ID"));
             String natureOfJobStr = rs.getString("NATURE_OF_ID");
             if (natureOfJobStr != null && !natureOfJobStr.trim().isEmpty()) {
                 contr.setNatureOfId(Integer.parseInt(natureOfJobStr));
             }
             // contr.setWcFromDtm(rs.getString("WC_FROM_DTM"));
            // contr.setWcToDtm(rs.getString("WC_TO_DTM"));
             Timestamp fromTs = rs.getTimestamp("WC_FROM_DTM");
             if (fromTs != null) {
                 contr.setWcFromDtm(fromTs.toLocalDateTime().toLocalDate().toString());
             }

             Timestamp toTs = rs.getTimestamp("WC_TO_DTM");
             if (toTs != null) {
                 contr.setWcToDtm(toTs.toLocalDateTime().toLocalDate().toString());
             }
             contr.setWcTotal(rs.getInt("WC_TOTAL"));
             contr.setDeleteSw(rs.getInt("DELETE_SW"));
             contr.setLicenceType(rs.getString("LICENCE_TYPE"));
             contr.setIsVerified(rs.getString("ISVERIFIED"));
             contr.setAttachmentNm(rs.getString("ATTACHMENTNM"));
             contr.setExtendToSubcontractor(rs.getInt("EXTENDTOSUBCONTRACTOR"));
             
          // ✅ DYNAMIC ACTIVE WORKMEN COUNT
             int activeCount = getWCESICActiveWorkmenCount(
                     contractorId,
                     principalEmployerId,
                     contr.getWcCode()
             );
             contr.setActiveWorkmenCount(activeCount);
             
            contrWcList.add(contr);
		}
	    return contrWcList;
	}

	@Override
	public CMSContrPemm getMappingByContractorIdAndUnitId(String contractorId, String principalEmployerId) {
		 CMSContrPemm contr = null;
			SqlRowSet rs = jdbcTemplate.queryForRowSet(getContrpemmByPeAndCont(),contractorId,principalEmployerId);
			if (rs.next()) {
	            contr = new CMSContrPemm();
	            //contr.setRefId(rs.getLong("REFID"));
	            contr.setContractorId(rs.getLong("CONTRACTORID"));
	            contr.setUnitId(rs.getLong("UNITID"));
	            contr.setManagerNm(rs.getString("MANAGERNM"));
	            contr.setManagerEmail(rs.getString("MANAGEREMAIL"));
	            contr.setManagerMobile(rs.getString("MANAGERMOBILE"));
	            contr.setPeriodStartDt(rs.getDate("PERIODSTARTDT"));
	            contr.setPeriodEndDt(rs.getDate("PERIODENDDT"));
	            contr.setPfNum(rs.getString("PFNUM"));
	            contr.setPfApplyDt(rs.getDate("PFAPPLYDT"));
	            contr.setEsicNum(rs.getString("ESICNUM"));
	            contr.setRcValidated(rs.getString("RCVALIDATED").equals("Y"));
	        }
			return null;
	}
	
	@Override
	public String saveReg(ContractorRegistration contreg) {
		int status=0;
		String sql =" select cgm.GMID,cgm.GMNAME from CMSGENERALMASTER cgm\r\n"
		   		+ "		    join CMSGMTYPE cgt on cgt.GMTYPEID=cgm.GMTYPEID\r\n"
		   		+ "		    where cgt.GMTYPE='MODULE' and cgm.GMNAME like 'Contractor Renewal%'";
		//String sql = getGMID();
		
		   SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
		   while(rs.next()) {
			   contreg.setModuleId(rs.getString("GMID"));
		   }
		   if( "Create".equalsIgnoreCase(contreg.getRequestType())) {
			   contreg.setActionId(GatePassType.CONTRACTOREGISTRATION.getStatus());
		   }else {
			   contreg.setActionId(GatePassType.CONTRACTORRENEWAL.getStatus());
		   }
		Object[] parameters = new Object[] {
			    contreg.getContractorregId(),
			    contreg.getContractorId(),
			    contreg.getVendorCode(),
			    contreg.getUnitId(),
			   // contreg.getPrincipalEmployer(),
			    contreg.getContractorName(),
			    contreg.getManagerName(),

			    // Ensure these are Integers
			    contreg.getTotalStrength() != null ? Integer.parseInt(contreg.getTotalStrength()) : null,
			    contreg.getRcMaxEmp() != null ? Integer.parseInt(contreg.getRcMaxEmp()) : null,

			    contreg.getNatureOfWork(),
			    contreg.getLocofWork(),
			    contreg.getPfNum(),
			    contreg.getRcVerified(),
			    contreg.getMainContractor(),
			    contreg.getContractType(),

			    // Convert to java.sql.Date
			    contreg.getContractFrom() != null ? java.sql.Date.valueOf(contreg.getContractFrom()) : null,
			    contreg.getContractTo() != null ? java.sql.Date.valueOf(contreg.getContractTo()) : null,

			    contreg.getRequestType(),
			    contreg.getStatus(),
			    contreg.getCreatedBy(),
			    contreg.getAadhar(),
			    contreg.getAadharDoc(),
			    contreg.getPan(),
			    contreg.getPanDoc(),

			    contreg.getPfApplyDate() != null ? java.sql.Date.valueOf(contreg.getPfApplyDate()) : null,
			    contreg.getGst(),
			    contreg.getAddress(),
			    contreg.getEmail(),
			    contreg.getMobile(),
			    contreg.getActionId(),
			    contreg.getPfDoc(),
			    contreg.getModuleId()
			};

        try {
        	String query = "INSERT INTO CMSContractorRegistration(" +
        		    "CONTRACTORREGID, CONTRACTORID, CODE, UNITID, CONTRACTORNAME, MANAGERNM, " +
        		    "TOTALSTRENGTH, MAXNOEMP, NATUREOFWORK, LOCOFWORK, PFNUM, RCVALIDATED, " +
        		    "MAINCONTRACTOR, CONTTYPE, PERIODSTARTDATE, PERIODENDDATE, TYPE, STATUS, " +
        		    "CREATEDBY, AADHARNUM, AADHARDOCNAME, PANNUM, PANDOCNAME, PFAPPLYDT, GST, " +
        		    "ADDRESS, EMAILADDR, MOBILENO, DELETESW, CREATEDDTM,ACTIONID,PFDOCNAME,MODULEID) " +
        		    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '0', GETDATE(),?,?,?)";
        	//String query = saveRegistration();
        	

           status  = jdbcTemplate.update(query, parameters);
           if (status > 0 && "Create".equalsIgnoreCase(contreg.getRequestType())) {
        boolean	  contExists= this.contractorExistsForPeContractor(contreg.getContractorId(),contreg.getUnitId());
        	   if(contExists) {
        		   updateContractorPemm(contreg);
        	   }else {
        	    saveContractorPemm(contreg);
        	}
           }


           return contreg.getContractorregId();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    return null;
	}
	
	
	public void saveContractorPemm(ContractorRegistration contreg) {
		String sql = saveContractorPemm();
	    //String sql = "INSERT INTO CMSCONTRPEMM(CONTRACTORID, UNITID, MANAGERNM, TOTALSTRENGTH, MAXNOEMP, NATUREOFWORK, LOCOFWORK, PFNUM, RCVALIDATED, PERIODSTARTDT, PERIODENDDT,PFAPPLYDT) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    try {
	        jdbcTemplate.update(sql,
	           
	            contreg.getContractorId(),
	            //contreg.getPrincipalEmployer(),
	            contreg.getUnitId(),
	            contreg.getManagerName(),
	            contreg.getTotalStrength() != null ? Integer.parseInt(contreg.getTotalStrength()) : null,
	    	    contreg.getRcMaxEmp() != null ? Integer.parseInt(contreg.getRcMaxEmp()) : null,
	    	    contreg.getNatureOfWork(),
	    		contreg.getLocofWork(),
	    	    contreg.getPfNum(),
	    	    contreg.getRcVerified() != null && 
                (contreg.getRcVerified().equalsIgnoreCase("Y") || contreg.getRcVerified().equalsIgnoreCase("YES"))
                ? "Y" : "N",
	    		//contreg.getContractType(),
	    	    contreg.getContractFrom() != null ? java.sql.Date.valueOf(contreg.getContractFrom()) : null,
	    		contreg.getContractTo() != null ? java.sql.Date.valueOf(contreg.getContractTo()) : null,
	    		contreg.getPfApplyDate() != null ? java.sql.Date.valueOf(contreg.getPfApplyDate()) : null
	        );
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public List<ContractorRegistration> getContractorRegistrationList(String userId) {
	List<ContractorRegistration> peList= new ArrayList<ContractorRegistration>();
	String query = getAllContractors();
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
	while(rs.next()) {
		ContractorRegistration pe = new ContractorRegistration();
		pe.setContractorregId(rs.getString("CONTRACTORREGID"));
		pe.setPrincipalEmployer(rs.getString("UNITCODE"));
		pe.setVendorCode(rs.getString("CODE"));
		pe.setContractorName(rs.getString("CONTRACTORNAME"));
		pe.setStatus(rs.getString("STATUS"));
		pe.setRequestType(rs.getString("TYPE"));
		
		peList.add(pe);
	}
	return peList;
	}
	
	@Override
	public List<ContractorRegistration> getContractorRenewList(String userId) {
	List<ContractorRegistration> peList= new ArrayList<ContractorRegistration>();
	String query = getContractorRenewList();
	//String query = "SELECT CONTRACTORREGID,UNITCODE,CODE,CONTRACTORNAME,STATUS,TYPE FROM CMSContractorRegistration where TYPE='Renew'";
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
	while(rs.next()) {
		ContractorRegistration pe = new ContractorRegistration();
		pe.setContractorregId(rs.getString("CONTRACTORREGID"));
		pe.setPrincipalEmployer(rs.getString("UNITCODE"));
		pe.setVendorCode(rs.getString("CODE"));
		pe.setContractorName(rs.getString("CONTRACTORNAME"));
		pe.setStatus(rs.getString("STATUS"));
		pe.setRequestType(rs.getString("TYPE"));
		
		peList.add(pe);
	}
	return peList;
	}
	
	@Override
	public List<ContractorRegistration> getContractorRenewalList(String userId) {
	List<ContractorRegistration> peList= new ArrayList<ContractorRegistration>();
	String query = getAllRenewalContractorsList();
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
	while(rs.next()) {
		ContractorRegistration pe = new ContractorRegistration();
		pe.setContractorregId(rs.getString("CONTRACTORREGID"));
		pe.setPrincipalEmployer(rs.getString("UNITCODE"));
		pe.setVendorCode(rs.getString("CODE"));
		pe.setContractorName(rs.getString("CONTRACTORNAME"));
		pe.setStatus(rs.getString("STATUS"));
		pe.setRequestType(rs.getString("TYPE"));
		
		peList.add(pe);
	}
	return peList;
	}
	
	@Override
	public ContractorRenewal getContractorDetails(String contractorRenewId) {
		ContractorRenewal contr = null;
		String query = getContractorsDetails();
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query, contractorRenewId);
			if (rs.next()) {
	            contr = new ContractorRenewal();
	            contr.setContractorRenewId(rs.getString("CONTRACTORREGID"));
	            contr.setUnitCode(rs.getString("UNITCODE"));
	            contr.setOrganisation(rs.getString("UNITNAME"));
	            contr.setContractorCode(rs.getString("CODE"));
	            contr.setContractorName(rs.getString("CONTRACTORNAME"));
	            contr.setManagerName(rs.getString("MANAGERNM"));
	            contr.setLocationOfWork(rs.getString("LOCOFWORK"));
	            contr.setSupervisorName(rs.getString("SUPERVISORNAME"));
	            contr.setPfCode(rs.getString("PFNUM"));
	            contr.setEmailAdd(rs.getString("EMAILADDR"));
	            contr.setMobileNumber(rs.getString("MOBILENO"));
	            contr.setEsicReg(rs.getString("ESICREGNO"));
	            contr.setContractorValidTo(rs.getString("CONTRACTVALIDTILL"));
	            contr.setContractorClass(rs.getString("SERVICES"));
	            contr.setContractorType(rs.getString("CONTYPE"));
	        }
			return null;
	}
	
	@Override
	public List<ContractorRegistration> getContractorRegistrationList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ContractorRegistration viewContractorDetails(String contractorregId) {
		ContractorRegistration contr = new ContractorRegistration();
		String query = getAllContractorsDetailsView();
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query,contractorregId);
			if (rs.next()) {
	            contr = new ContractorRegistration();
	            contr.setContractorregId(rs.getString("CONTRACTORREGID"));
	            contr.setPrincipalEmployer(rs.getString("UNITID"));
	            contr.setVendorCode(rs.getString("CODE"));
	            contr.setManagerName(rs.getString("MANAGERNM"));
	            contr.setLocofWork(rs.getString("LOCOFWORK"));
	            contr.setTotalStrength(rs.getString("TOTALSTRENGTH"));
	            contr.setRcMaxEmp(rs.getString("MAXNOEMP"));
	            contr.setPfNum(rs.getString("PFNUM"));
	            contr.setNatureOfWork(rs.getString("NATUREOFWORK"));
	            contr.setContractFrom(rs.getString("PERIODSTARTDATE"));
	            contr.setContractTo(rs.getString("PERIODENDDATE"));
	            contr.setContractType(rs.getString("CONTTYPE"));
	            contr.setRcVerified(rs.getString("RCVALIDATED"));
	            contr.setMainContractor(rs.getString("MAINCONTRACTOR"));
	            contr.setContractorName(rs.getString("CONTRACTORNAME"));
	            contr.setEmail(rs.getString("EMAILADDR"));
	            contr.setMobile(rs.getString("MOBILENO"));
	            contr.setAadhar(rs.getString("AADHARNUM"));
	            contr.setAadharDoc(rs.getString("AADHARDOCNAME"));
	            contr.setPan(rs.getString("PANNUM"));
	            contr.setPanDoc(rs.getString("PANDOCNAME"));
	            contr.setPfApplyDate(rs.getString("PFAPPLYDT"));
	            contr.setAddress(rs.getString("ADDRESS"));
	            contr.setGst(rs.getString("GST"));
	            contr.setCreatedBy(rs.getString("CREATEDBY"));
	            contr.setPfDoc(rs.getString("PFDOCNAME"));
	            }
			return contr;
	}
	
	
	@Override
	public List<ContractorRegistrationPolicy> viewContractorAddDetails(String contractorregId) {
	List<ContractorRegistrationPolicy> conList= new ArrayList<ContractorRegistrationPolicy>();
	String query = getAllContractorsAdditionalDetails();
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query,contractorregId);
	while(rs.next()) {
		ContractorRegistrationPolicy pe = new ContractorRegistrationPolicy();
		
		pe.setContractorRegId(rs.getString("CONTRACTORREGID"));
		pe.setWoNumber(rs.getString("WONUMBER"));
		pe.setNatureOfJob(rs.getString("NATUREOFID"));
		pe.setDocumentType(rs.getString("LICENCETYPE"));
		pe.setDocumentNumber(rs.getString("WCCODE"));
		pe.setCoverage(rs.getInt("WCTOTAL"));
		pe.setValidFrom(rs.getString("WCFROMDTM"));
		pe.setValidTo(rs.getString("WCTODTM"));
		pe.setFileName(rs.getString("ATTACHMENTNAME"));
		conList.add(pe);
	}
	return conList;
	}
	
	@Override
	public ContractorRenewal viewContractorRenewDetails(String contractorRenewId) {
		ContractorRenewal contr = new ContractorRenewal();
		String query = getRenewalView();
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query,contractorRenewId);
			if (rs.next()) {
	            contr = new ContractorRenewal();
	            contr.setContractorRenewId(rs.getString("CONTRACTORREGID"));
	            contr.setUnitCode(rs.getString("UNITCODE"));
	            contr.setOrganisation(rs.getString("UNITNAME"));
	            contr.setContractorCode(rs.getString("CONTRACTORID"));
	            contr.setContractorName(rs.getString("CONTRACTORNAME"));
	            contr.setManagerName(rs.getString("MANAGERNM"));
	            contr.setLocationOfWork(rs.getString("LOCOFWORK"));
	            contr.setSupervisorName(rs.getString("SUPERVISORNAME"));
	            contr.setPfCode(rs.getString("PFNUM"));
	            contr.setEmailAdd(rs.getString("EMAILADDR"));
	            contr.setMobileNumber(rs.getString("MOBILENO"));
	            contr.setEsicReg(rs.getString("ESICREGNO"));
	            contr.setContractorValidTo(rs.getString("CONTRACTVALIDTILL"));
	            contr.setContractorClass(rs.getString("SERVICES"));
	            contr.setContractorType(rs.getString("CONTTYPE"));
	        }
			return contr;
	}
	
	@Override
	public List<ContractorRenewal> viewContractorRenewAddDetails(String userId) {
	List<ContractorRenewal> conList= new ArrayList<ContractorRenewal>();
	String query = getRenewalContractorsView();
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
	while(rs.next()) {
		ContractorRenewal pe = new ContractorRenewal();
		pe.setDocumentType(rs.getString("LICENCETYPE"));
		pe.setDocumentNumber(rs.getString("WCCODE"));
		pe.setCoverage(rs.getString("WCTOTAL"));
		pe.setValidFrom(rs.getString("WCFROMDTM"));
		pe.setValidTo(rs.getString("WCTODTM"));
		pe.setAttachment(rs.getString("ATTACHMENTNAME"));
		pe.setPanIndia(rs.getString("ISGLOBAL"));
		pe.setSubContractor(rs.getString("SUBCONTAPPL"));
		
		conList.add(pe);
	
	}
	return conList;
	}
	@Override
	public List<MasterUser> getRoleList(String userId) {
	List<MasterUser> peList= new ArrayList<MasterUser>();
	SqlRowSet rs = jdbcTemplate.queryForRowSet(ContractorQueryBank.GET_ROLE_LIST);
	while(rs.next()) {
		MasterUser pe = new MasterUser();
		pe.setUserId(rs.getInt("UserId"));
		pe.setFirstName(rs.getString("FirstName"));
		pe.setLastName(rs.getString("LastName"));
		pe.setContactNumber(rs.getString("ContactNumber"));
		pe.setRoleName(rs.getString("RoleName"));
		
		peList.add(pe);
	}
	return peList;
	}

	
	@Override
	public String saveRole(MasterUser user) {
		String result = null;
		int status=0;
        Object[] parameters = new Object[] {user.getFirstName(),user.getLastName(),user.getUserId(),user.getEmailId(),user.getContactNumber(),user.getPassword(),user.getRoleName()}; 
        try {
           status  = jdbcTemplate.update(ContractorQueryBank.SAVE_ROLE_FORM, parameters);
           return String.valueOf(status);
        } catch (Exception e) {
        }
    return String.valueOf(status);
	}
	
	@Override
	public String saveRenew(ContractorRenewal contrenew) {
		String result = null; 
		String query = setRenewalDetails();
		int status=0;
        Object[] parameters = new Object[] {contrenew.getContractorRenewId(),contrenew.getUnitCode(),contrenew.getOrganisation(),contrenew.getContractorCode(),contrenew.getContractorName(),contrenew.getManagerName(),contrenew.getLocationOfWork(),contrenew.getSupervisorName(),contrenew.getPfCode(),contrenew.getEmailAdd(),contrenew.getMobileNumber(),contrenew.getEsicReg(),contrenew.getContractorValidTo(),contrenew.getContractorClass(),contrenew.getContractorType()}; 
        try {
           status  = jdbcTemplate.update(query, parameters);
           return String.valueOf(status);
        } catch (Exception e) {
        }
    return String.valueOf(status);
	}
	
	@Override
    public boolean checkIfIdExists(String contractorregId) {
        String sql = setuniqueregistrationid();
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{contractorregId}, Integer.class);
        return count != null && count > 0; // True if the ID exists
    }

	public String getMaxContrRegId() {
		return QueryFileWatcher.getQuery("GET_MAX_CONTRACTOR_REG_ID");
	}
	
	@Override
	public String generateContractorRegistrationId() {
		String contrRegId = null;
	    try {
	    	String query = getMaxContrRegId();
	        SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
	        if (rs.next()) {
	        	contrRegId = rs.getString("CONTRACTORREGID");
	        }
	    } catch (Exception e) {
	        log.error("Error generating contrRegId", e);
	    }
	    return contrRegId;
	}

	public String getAllContractorForReg() {
	    return QueryFileWatcher.getQuery("GET_ALL_CONTRACTOR_FOR_REG");
	}
	@Override
	public List<Contractor> getAllContractorForReg(String unitId) {
		log.info("Entering into getAllContractorForReg dao method "+unitId);
		List<Contractor> contList= new ArrayList<Contractor>();
		String query=getAllContractorForReg();
		log.info("Query to getAllContractorBasedOnPE "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId);
		while(rs.next()) {
			Contractor cont = new Contractor();
			cont.setContractorId(rs.getString("CONTRACTORID"));
			cont.setContractorName(rs.getString("NAME"));
			cont.setUnitId(unitId);
			cont.setContractorCode(rs.getString("CODE"));
			cont.setContractorAddress(rs.getString("ADDRESS"));
			contList.add(cont);
		}
		log.info("Exiting from getAllContractorForReg dao method "+contList.size());
		return contList;
	}
	
	public String getAllContractorDetailForReg() {
	    return QueryFileWatcher.getQuery("GET_ALL_CONTRACTOR_DETAIL_FOR_REG");
	}
	
	public String getAllContractorDetailForRenewal() {
	    return QueryFileWatcher.getQuery("GET_CONTRACTOR_DETAILS_FOR_RENEWAL");
	}
	
	public String getAllAvailableWos() {
	    return QueryFileWatcher.getQuery("AVAILABLE_WO_FOR_RENEW");
	}
	public String getAllAvailableLicense() {
	    return QueryFileWatcher.getQuery("AVAILABLE_LICENSE_FOR_RENEW");
	}
	@Override
	public Contractor getAllContractorDetailForReg(String unitId, String contractorId) {
		String query=getAllContractorDetailForReg();
		log.info("Query to getAllContractorBasedOnPE "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,contractorId,unitId);
		Contractor cont = new Contractor();
		while(rs.next()) {
			
			cont.setContractorId(rs.getString("CONTRACTORID"));
			cont.setContractorName(rs.getString("NAME"));
			cont.setUnitId(unitId);
			cont.setContractorCode(rs.getString("CODE"));
			cont.setContractorAddress(rs.getString("ADDRESS"));
			cont.setManagerName(rs.getString("MANAGERNM"));
			cont.setEsiwc(rs.getString("ESIWC"));
			cont.setLicenseNumber(rs.getString("LICENSENUM"));
			cont.setValidFrom(rs.getString("VALIDFROMDT"));
			cont.setValidTo(rs.getString("VALIDTODT"));
			cont.setCoverage(rs.getInt("COVERAGE"));
			cont.setTotalStrength(rs.getInt("TOTALSTRENGTH"));
			cont.setMaxNoEmp(rs.getInt("MAXNOEMP"));
			cont.setNatureOfWork(rs.getString("NATUREOFWORK"));
			cont.setLocationOfWork(rs.getString("LOCOFWORK"));
			cont.setPeriodStartDate(rs.getString("PERIODSTARTDT"));
			cont.setPeriodEndDate(rs.getString("PERIODENDDT"));
			cont.setVendorCode(rs.getString("VENDORCODE"));
			cont.setPfCode(rs.getString("PFCODE"));
			cont.setEsiValidFrom(rs.getString("ESIVALIDFROM"));
			cont.setEsiValidTo(rs.getString("ESIVALIDTO"));
			cont.setEscWcCoverage(rs.getInt("ESIWCCOVERAGE"));
			cont.setPfNum(rs.getString("PFNUM"));
			cont.setPfApplyDate(rs.getString("PFAPPLYDT"));
			cont.setBlocked(rs.getString("IS_BLOCKED").equals("Y")?true:false);
			cont.setRcValidated(rs.getString("RCVALIDATED"));
			cont.setLlValidated(rs.getString("LLVALIDATED"));
			cont.setWcValidated(rs.getString("WCVALIDATED"));
			
		}
		return cont;
	}

//	@Override
//		public void savePolicies(List<ContractorRegistrationPolicy> policies,ContractorRegistration contreg) {
//		    String sql = "INSERT INTO CMSContractorRegPolicy (" +
//		    		" [CONTRACTORID],[UNITID],[WONUMBER] ,[LICENCETYPE],[WCCODE] ,[NATUREOFID] ,[WCTOTAL]  ,[WCFROMDTM] ,[WCTODTM] ,[ATTACHMENTNAME],[CREATEDDTM] ,[CREATEDBY],[CONTRACTORREGID]) " +
//		            " VALUES (?,?,?,?, ?,?,?,? ,?,?,GETDATE(),?,?)";
//
//		    jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
//		        public void setValues(PreparedStatement ps, int i) throws SQLException {
//		            ContractorRegistrationPolicy policy = policies.get(i);
//		          
//		            ps.setString(1, contreg.getContractorId());
//		            ps.setString(2, contreg.getPrincipalEmployer());
//		            ps.setString(3, policy.getWoNumber());
//		            ps.setString(4, policy.getDocumentType());
//		            ps.setString(5, policy.getDocumentNumber());
//		            ps.setString(6, policy.getNatureOfJob());
//		            ps.setInt(7, policy.getCoverage());
//		            ps.setString(8, policy.getValidFrom());
//		            ps.setString(9, policy.getValidTo());
//		            ps.setString(10, policy.getFileName());
//		            ps.setString(11, contreg.getCreatedBy());
//		            ps.setString(12, contreg.getContractorregId());
//		        }
//
//		        public int getBatchSize() {
//		            return policies.size();
//		        }
//		    });
//		}

	@Override
	public void savePolicies(List<ContractorRegistrationPolicy> policies, ContractorRegistration contreg) {
	    try {
	        String requestType = contreg.getRequestType() != null ? contreg.getRequestType().trim() : "";
	        String sql=savePolicies();
	        // 🔹 Common insert into CMSContractorRegPolicy
	        //String sql = "INSERT INTO CMSContractorRegPolicy (" +
	       //         "CONTRACTORID, UNITID, WONUMBER, LICENCETYPE, WCCODE, NATUREOFID, " +
	       //         "WCTOTAL, WCFROMDTM, WCTODTM, ATTACHMENTNAME, CREATEDDTM, CREATEDBY, CONTRACTORREGID, DELETESW) " +
	       //         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, ?, 0)";

	        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
	            @Override
	            public void setValues(PreparedStatement ps, int i) throws SQLException {
	                ContractorRegistrationPolicy policy = policies.get(i);

	                ps.setString(1, contreg.getContractorId());
	              //  ps.setString(2, contreg.getPrincipalEmployer());
	                ps.setLong(2, contreg.getUnitId());
	                ps.setString(3, policy.getWoNumber());
	                ps.setString(4, policy.getDocumentType());
	                ps.setString(5, policy.getDocumentNumber());
	                ps.setString(6, policy.getNatureOfJob());
	                ps.setInt(7, policy.getCoverage());
	                ps.setString(8, policy.getValidFrom());
	                ps.setString(9, policy.getValidTo());
	                ps.setString(10, policy.getFileName());
	                ps.setString(11, contreg.getCreatedBy());
	                ps.setString(12, contreg.getContractorregId());
	            }

	            @Override
	            public int getBatchSize() {
	                return policies.size();
	            }
	        });

	        System.out.println("✅ Policies inserted successfully in CMSContractorRegPolicy.");

	        // 🔹 Additional insert into CMSContractorWC ONLY for 'Create'
	        if (requestType.equalsIgnoreCase("Create")) {
	            saveContractorWC(policies, contreg);
	            saveWorkorderLLWC(policies);
	            System.out.println("✅ Additional policies saved in CMSContractorWC for CREATE request.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println("❌ Error saving contractor policies: " + e.getMessage());
	    }
	}


	public void saveContractorWC(List<ContractorRegistrationPolicy> policies, ContractorRegistration contreg) {
		 String sql=saveContractorWC();
	     //String sql = "INSERT INTO CMSCONTRACTOR_WC (" +
	     //       "[CONTRACTORID],[UNITID],[LICENCE_TYPE],[WC_CODE],[WC_TOTAL]," +
	     //       "[WC_FROM_DTM],[WC_TO_DTM],[ATTACHMENTNM],[CREATED_DTM],[DELETE_SW]) " +
	     //       "VALUES (?,?,?,?,?,?,?,?,GETDATE(),0)";

	    for (ContractorRegistrationPolicy policy : policies) {
	        jdbcTemplate.update(sql,
	            contreg.getContractorId(),              // CONTRACTORID
	            //contreg.getPrincipalEmployer(),         // UNITID
	            contreg.getUnitId(),         // UNITID
	            policy.getDocumentType(),               // LICENCE_TYPE
	            policy.getDocumentNumber(),             // WC_CODE
	            //policy.getNatureOfJob(),                // [NATURE_OF_ID],
	            policy.getCoverage(),                   // WC_TOTAL
	            policy.getValidFrom(),                  // WC_FROM_DTM
	            policy.getValidTo(),                    // WC_TO_DTM
	            policy.getFileName()                    // ATTACHMENTNM
	        );
	    }
	}


	 public String getAllWoByPeAndCont() {
		    return QueryFileWatcher.getQuery("GET_WO_FOR_CONTRACTOR_REG");
		}
	@Override
	public List<Workorder> getAllWorkordersBasedOnPEAndContractor(String unitId, String contractorId) {
		List<Workorder> woList= new ArrayList<Workorder>();
		String query=getAllWoByPeAndCont();
		log.info("Query to getAllWorkordersBasedOnPEAndContractor "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query, contractorId,unitId);
		while(rs.next()) {
			Workorder wo = new Workorder();
			wo.setWorkorderId(rs.getString("WORKORDERID"));
			wo.setName(rs.getString("SAP_WORKORDER_NUM"));
			woList.add(wo);
		}
		log.info("Exiting from getAllWorkordersBasedOnPEAndContractor dao method "+woList.size());
		return woList;
	}

	 public String getContractorMasterExportQuery() {
		    return QueryFileWatcher.getQuery("GET_CONTRACTOR_MASTER_EXPORT");
		}
	 
	@Override
	public List<ContractorRegistration> getContractorMasterExportData(String unitId) {
		List<ContractorRegistration> peList= new ArrayList<ContractorRegistration>();
		String query = getContractorMasterExportQuery();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId);
		while(rs.next()) {
			ContractorRegistration pe = new ContractorRegistration();
			pe.setVendorCode(rs.getString("CODE"));
			pe.setContractorName(rs.getString("CONTRACTORNAME"));
			pe.setAddress(rs.getString("ADDRESS"));
			pe.setMobile(rs.getString("MOBILENO"));
			pe.setManagerName(rs.getString("MANAGERNM"));
			pe.setEmail(rs.getString("EMAILADDR"));
			peList.add(pe);
		}
		return peList;
	}

	 public String getContractorComplianceExportQuery() {
		    return QueryFileWatcher.getQuery("GET_CONTRACTOR_COMPLIANCE_EXPORT");
		}
	@Override
	public List<ContractorComplianceDto> getContractorComplianceExportData(String unitId) {
		List<ContractorComplianceDto> peList= new ArrayList<ContractorComplianceDto>();
		String query = getContractorComplianceExportQuery();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId);
		while(rs.next()) {
			ContractorComplianceDto pe = new ContractorComplianceDto();
			pe.setPeId(rs.getString("PEID"));
			pe.setContractorCode(rs.getString("CONTRACTORCODE"));
			pe.setLicenseNumber(rs.getString("LICENSENUMBER"));
			pe.setLicenseStartDate(rs.getString("LICENSEVALIDITYSTARTDATE"));
			pe.setLicenseEndDate(rs.getString("LICENSEVALIDITYENDDATE"));
			pe.setContractorCoverge(rs.getString("CONTRACTORCOVERAGE"));
			pe.setTotalStrength(rs.getString("TOTALSTRENGTH"));
			pe.setNatureOfWork(rs.getString("NATUREOFWORK"));
			pe.setContractStartDate(rs.getString("CONTRACTSTARTDATE"));
			pe.setContractEndDate(rs.getString("CONTRACTENDDATE"));
			pe.setEsiNumber(rs.getString("ESINUMBER"));
			pe.setWcEsiValidFrom(rs.getString("ESIVALIDFROM"));
			pe.setWcEsiValidTo(rs.getString("ESIVALIDTO"));
			pe.setWcNo(rs.getString("WCCODE"));
			pe.setWcValidFrom(rs.getString("WCFROMDTM"));
			pe.setWcValidTo(rs.getString("WCTODTM"));
			pe.setPfNumber(rs.getString("PFNUM"));
			pe.setPfApplyDate(rs.getString("PFAPPLYDT"));
			pe.setLaborWelfare(rs.getString("LaborWelfarefund"));
			pe.setProfessionalTax(rs.getString("ProfessionalTax"));
			pe.setGst(rs.getString("GST"));
			pe.setIsmwApplicable(rs.getString("ISMWApplicable"));
			pe.setEffectiveDate(rs.getString("CREATEDDTM"));
			peList.add(pe);
		}
		return peList;
	}
	
	@Override
	public ContractorRegistration getAllContractorDetailForRenewal(String unitId, String contractorId) {
		String query=getAllContractorDetailForRenewal();
		log.info("Query to getAllContractorBasedOnPE "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,contractorId,unitId);
		ContractorRegistration cont = new ContractorRegistration();
		while(rs.next()) {
			
			cont.setPrincipalEmployer(rs.getString("UNITID"));
			cont.setContractorName(rs.getString("CONTRACTORNAME"));
			cont.setVendorCode(rs.getString("CODE"));
			cont.setEmail(rs.getString("EMAILADDR"));
			cont.setMobile(rs.getString("MOBILENO"));
			cont.setAadhar(rs.getString("AADHARNUM"));
			cont.setAadharDoc(rs.getString("AADHARDOCNAME"));
			cont.setPan(rs.getString("PANNUM"));
			cont.setPanDoc(rs.getString("PANDOCNAME"));
			cont.setGst(rs.getString("GST"));
			cont.setAddress(rs.getString("ADDRESS"));
			cont.setPfNum(rs.getString("PFNUM"));
			cont.setNatureOfWork(rs.getString("NATUREOFWORK"));
			cont.setManagerName(rs.getString("MANAGERNM"));
			cont.setLocofWork(rs.getString("LOCOFWORK"));
			cont.setTotalStrength(rs.getString("TOTALSTRENGTH"));
			cont.setRcMaxEmp(rs.getString("MAXNOEMP"));
			cont.setContractFrom(rs.getString("PERIODSTARTDATE"));
			cont.setContractTo(rs.getString("PERIODENDDATE"));
			cont.setContractType(rs.getString("CONTTYPE"));
			cont.setServices(rs.getString("SERVICES"));
			cont.setEsicRegNo(rs.getString("ESICREGNO"));
			cont.setPfApplyDate(rs.getString("PFAPPLYDT"));
			cont.setContractorId(rs.getString("CONTRACTORID"));
		}
		//String sqlWO = this.getAllAvailableWos();
		//SqlRowSet rs1 = jdbcTemplate.queryForRowSet(sqlWO,cont.getVendorCode(),unitId);
		//List<String> woList = new ArrayList<>();
		//while(rs1.next()) {
		//	woList.add(rs1.getString("SAP_WORKORDER_NUM"));
		//}
		//cont.setAvailableWos(woList);
		return cont;
	}
	
	@Override
	public ContractorRegistration getWOAndLicense(String contractorId,String contractorCode,String unitId,String regId) {
		ContractorRegistration cont = new ContractorRegistration();
		String sqlWO = this.getAllAvailableWos();
		SqlRowSet rs1 = jdbcTemplate.queryForRowSet(sqlWO,contractorCode,unitId);
		List<String> woList = new ArrayList<>();
		while(rs1.next()) {
			woList.add(rs1.getString("SAP_WORKORDER_NUM"));
		}
		cont.setAvailableWos(woList);
		
		
		String query =this.getAllAvailableLicense();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,regId,contractorId,unitId);
		List<String> licenseList = new ArrayList<>();
		while(rs.next()) {
			licenseList.add(rs.getString("license"));
		}
		cont.setAvailableLicenses(licenseList);
		cont.setContractorId(contractorId);
		return cont;
	}
	
	@Override
	public ContractorRegistration getContractorRegistration(String contractorRegId) {
		String sql=getContractorRegistration();
	    //String sql = "SELECT *,cpe.NAME FROM CMSContractorRegistration ccr join CMSPRINCIPALEMPLOYER cpe on cpe.UNITID=ccr.UNITID WHERE CONTRACTORREGID = ?";
	    return jdbcTemplate.queryForObject(sql, new Object[]{contractorRegId}, (rs, rowNum) -> {
	        ContractorRegistration cr = new ContractorRegistration();
	        cr.setContractorregId(rs.getString("CONTRACTORREGID"));
	        cr.setContractorId(rs.getString("CONTRACTORID"));
	        cr.setVendorCode(rs.getString("CODE"));
	        cr.setPrincipalEmployer(rs.getString("NAME"));
	        cr.setContractorName(rs.getString("CONTRACTORNAME"));
	        cr.setManagerName(rs.getString("MANAGERNM"));
	        cr.setTotalStrength(String.valueOf(rs.getInt("TOTALSTRENGTH")));
	        cr.setRcMaxEmp(String.valueOf(rs.getInt("MAXNOEMP")));
	        cr.setNatureOfWork(rs.getString("NATUREOFWORK"));
	        cr.setLocofWork(rs.getString("LOCOFWORK"));
	        cr.setPfNum(rs.getString("PFNUM"));
	        cr.setRcVerified(rs.getString("RCVALIDATED"));
	        cr.setMainContractor(rs.getString("MAINCONTRACTOR"));
	        cr.setContractType(rs.getString("CONTTYPE"));
	        cr.setContractFrom(rs.getDate("PERIODSTARTDATE") != null ? rs.getDate("PERIODSTARTDATE").toString() : null);
	        cr.setContractTo(rs.getDate("PERIODENDDATE") != null ? rs.getDate("PERIODENDDATE").toString() : null);
	        cr.setRequestType(rs.getString("TYPE"));
	        cr.setCreatedBy(rs.getString("CREATEDBY"));
	        cr.setAadhar(rs.getString("AADHARNUM"));
	        cr.setAadharDoc(rs.getString("AADHARDOCNAME"));
	        cr.setPan(rs.getString("PANNUM"));
	        cr.setPanDoc(rs.getString("PANDOCNAME"));
	        cr.setPfApplyDate(rs.getDate("PFAPPLYDT") != null ? rs.getDate("PFAPPLYDT").toString() : null);
	        cr.setGst(rs.getString("GST"));
	        cr.setAddress(rs.getString("ADDRESS"));
	        cr.setEmail(rs.getString("EMAILADDR"));
	        cr.setMobile(rs.getString("MOBILENO"));
	        cr.setPfDoc(rs.getString("PFDOCNAME"));
	        cr.setUnitId(rs.getLong("UNITID"));
	        return cr;
	    });
	}

	@Override
	public List<ContractorRegistrationPolicy> getPoliciesByContractorRegId(String contractorRegId) {
		String sql=getPoliciesByContractorRegId();
	    //String sql = "SELECT * FROM CMSContractorRegPolicy WHERE CONTRACTORREGID = ?";
	    return jdbcTemplate.query(sql, new Object[]{contractorRegId}, (rs, rowNum) -> {
	        ContractorRegistrationPolicy policy = new ContractorRegistrationPolicy();
	        policy.setWoNumber(rs.getString("WONUMBER"));
	        policy.setDocumentType(rs.getString("LICENCETYPE"));
	        policy.setDocumentNumber(rs.getString("WCCODE"));
	        policy.setNatureOfJob(rs.getString("NATUREOFID"));
	        policy.setCoverage(rs.getInt("WCTOTAL"));
	        //policy.setValidFrom(rs.getString("WCFROMDTM"));
	        //policy.setValidTo(rs.getString("WCTODTM"));
	        policy.setValidFrom(rs.getTimestamp("WCFROMDTM") != null? rs.getTimestamp("WCFROMDTM").toLocalDateTime().toLocalDate().toString(): "");
	        policy.setValidTo(rs.getTimestamp("WCTODTM") != null? rs.getTimestamp("WCTODTM").toLocalDateTime().toLocalDate().toString(): "");
	        policy.setFileName(rs.getString("ATTACHMENTNAME"));
	        policy.setUnitId(rs.getString("UNITID"));
	        return policy;
	    });
	}

	@Override
	public List<CMSContractorRegistrationLLWC> getLLWCByContractorRegId(String contractorRegId) {
		String sql=getLLWCByContractorRegId();
	  //  String sql = "SELECT * FROM CMSContractorRegistrationLLWC WHERE CONTRACTORREGID = ?";
	    return jdbcTemplate.query(sql, new Object[]{contractorRegId}, (rs, rowNum) -> {
	        CMSContractorRegistrationLLWC record = new CMSContractorRegistrationLLWC();
	        record.setContractorRegId(rs.getLong("CONTRACTORREGID"));
	        record.setContractorId(rs.getInt("CONTRACTORID"));
	        record.setUnitId(rs.getInt("UNITID"));
	        record.setWorkOrderId(rs.getInt("WOID"));
	        record.setLicenseType(rs.getString("LICENCETYPE"));
	        record.setWorkOrderNumber(rs.getString("WONUMBER"));
	        record.setWcCode(rs.getString("WCCODE"));
	        //record.setCreatedDtm(rs.getTimestamp("CREATEDDTM"));
	        Timestamp createdTs = rs.getTimestamp("CREATEDDTM");
	        record.setCreatedDtm(createdTs == null? null: Timestamp.valueOf(createdTs.toLocalDateTime().toLocalDate().atStartOfDay()));
	        record.setCreatedBy(rs.getString("CREATEDBY"));
	        return record;
	    });
	}

	
	 public String getAllRenewalContractorsListForCreator() {
	    return QueryFileWatcher.getQuery("GET_CONTRENEW_FOR_CREATOR");
	}
	@Override
	public List<ContractorRegistration> getContRenewList(String userId, String deptId, String principalEmployerId) {
		List<ContractorRegistration> peList= new ArrayList<ContractorRegistration>();
		String query = getAllRenewalContractorsListForCreator();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,userId,deptId,principalEmployerId);
		while(rs.next()) {
			ContractorRegistration pe = new ContractorRegistration();
			pe.setContractorregId(rs.getString("CONTRACTORREGID"));
			pe.setPrincipalEmployer(rs.getString("NAME"));
			pe.setVendorCode(rs.getString("CODE"));
			pe.setContractorName(rs.getString("CONTRACTORNAME"));
			pe.setStatus(rs.getString("STATUS"));
			pe.setRequestType(rs.getString("TYPE"));
			String status =String.valueOf(rs.getInt("Status"));
			if(status.equals(GatePassStatus.APPROVALPENDING.getStatus())) {
				pe.setStatusValue("Approval Pending");
			}else if(status.equals(GatePassStatus.APPROVED.getStatus())) {
				pe.setStatusValue("Approved");
			}else if(status.equals(GatePassStatus.REJECTED.getStatus())) {
				pe.setStatusValue("Rejected");
			}else if(status.equals(GatePassStatus.DRAFT.getStatus())) {
				pe.setStatusValue("Draft");
			}
			peList.add(pe);
		}
		return peList;
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
public String getContRenewListForSequentialApprovers() {
    return QueryFileWatcher.getQuery("GET_CONTRACTOR_RENEWLIST_FOR_SEQUENTIAL_APPROVERS");
}
public String getContRenewListForParallelApprovers() {
    return QueryFileWatcher.getQuery("GET_CONTRACTOR_RENEWLIST_FOR_PARALLEL_APPROVERS");
}
public String approveRejectContRenew() {
    return QueryFileWatcher.getQuery("APPROVE_REJECT_CONTRACTOR_RENEW");
}
public String updateContStatusByTransactionId() {
    return QueryFileWatcher.getQuery("UPDATE_CONTRACTOR_STATUS_BY_TRANSACTIONID");
}
public String getWorkFlowTypeByTransactionIdforContReg() {
    return QueryFileWatcher.getQuery("GET_WORKFLOWTYPE_BY_TRANSACTIONID_FOR_CONTREG");
}
public String isLastApprover() {
    return QueryFileWatcher.getQuery("IS_LASTAPPROVER_FOR_CONTRACTOR_RENEWAL");
}
public String isLastApproverforParallel() {
    return QueryFileWatcher.getQuery("IS_LASTAPPROVER_FOR_PARALLEL_CONTRACTOR_RENEWAL");
}
public String getWorkorderNumber() {
    return QueryFileWatcher.getQuery("GET_WORKORDER_NUMBER_BY_CONTREGID");
}
public String insertWorkOrderLLWC() {
    return QueryFileWatcher.getQuery("INSERT_WORKORDER_LLWC");
}
public String getContractorPreviousDocuments() {
    return QueryFileWatcher.getQuery("GET_CONTRACTOR_PREVIOUS_DOCUMENTS");
}
@Override
public List<ContractorRegistration> getContRenewListForApprovers(String roleId, int workFlowType, String deptId,
		String principalEmployerId) {
	List<ContractorRegistration> listDto= new ArrayList<ContractorRegistration>();
	SqlRowSet rs =null;
	String query=null;
	if(workFlowType == WorkFlowType.SEQUENTIAL.getWorkFlowTypeId()) {
		query="SELECT DISTINCT\r\n"
				+ "    CCR.CONTRACTORREGID,\r\n"
				+ "    cpe.NAME,\r\n"
				+ "    ccr.CODE,\r\n"
				+ "    CCR.CONTRACTORNAME,\r\n"
				+ "    CCR.STATUS,\r\n"
				+ "    CCR.TYPE\r\n"
				+ "FROM CMSContractorRegistration CCR\r\n"
				+ "JOIN CMSPRINCIPALEMPLOYER cpe \r\n"
				+ "       ON cpe.UNITID = ccr.UNITID\r\n"
				+ "JOIN CMSWORKFLOWTYPE cwt \r\n"
				+ "       ON cwt.ModuleId = ccr.ModuleId \r\n"
				+ "      AND cwt.UnitId = CCR.UnitId\r\n"
				+ "JOIN CMSAPPROVERHIERARCHY cah   \r\n"
				+ "       ON cah.WORKFLOWTYPEID = cwt.WorkflowTypeId\r\n"
				+ "WHERE \r\n"
				+ "    CCR.status = '3'\r\n"
				+ "    AND CCR.ContractorId = ?\r\n"
				+ "    AND CCR.UnitId = ?\r\n"
				+ "    AND CCR.TYPE = 'renew'\r\n"
				+ "    AND cah.ROLE_ID = ?\r\n"
				+ "    AND cah.[Index] = (\r\n"
				+ "            SELECT COUNT(DISTINCT gas.ContRenewApprovalStatusId) + 1\r\n"
				+ "            FROM CONTRENEWAPPROVALSTATUS gas\r\n"
				+ "            JOIN CMSAPPROVERHIERARCHY cah1 \r\n"
				+ "                  ON gas.RoleId = cah1.ROLE_ID\r\n"
				+ "            JOIN CMSWORKFLOWTYPE cwt1 \r\n"
				+ "                  ON cwt1.WorkflowTypeId = cah1.WORKFLOWTYPEID\r\n"
				+ "                 AND cwt1.UnitId = CCR.UnitId\r\n"
				+ "            WHERE gas.status = 4\r\n"
				+ "              AND gas.CONTRACTORREGID = CCR.CONTRACTORREGID\r\n"
				+ "        )\r\n"
				+ "    AND NOT EXISTS (\r\n"
				+ "        SELECT 1 \r\n"
				+ "        FROM CONTRENEWAPPROVALSTATUS gas2\r\n"
				+ "        WHERE gas2.CONTRACTORREGID = CCR.CONTRACTORREGID\r\n"
				+ "          AND gas2.RoleId = cah.ROLE_ID\r\n"
				+ "    );\r\n"
				+ "";
		log.info("Query to getBVRListingForApprovers "+query);
		//String query = getContRenewListForSequentialApprovers();
		
		
		 rs = jdbcTemplate.queryForRowSet(query,deptId,principalEmployerId,roleId);
	}else {
		query="SELECT DISTINCT\r\n"
				+ "    CCR.CONTRACTORREGID,\r\n"
				+ "    cpe.NAME,\r\n"
				+ "    ccr.CODE,\r\n"
				+ "    CCR.CONTRACTORNAME,\r\n"
				+ "    CCR.STATUS,\r\n"
				+ "    CCR.TYPE\r\n"
				+ "FROM CMSContractorRegistration CCR\r\n"
				+ "JOIN CMSPRINCIPALEMPLOYER cpe \r\n"
				+ "       ON cpe.UNITID = ccr.UNITID\r\n"
				+ "JOIN CMSAPPROVERHIERARCHY cah\r\n"
				+ "       ON cah.ACTION_ID = CCR.ActionId\r\n"
				+ "WHERE\r\n"
				+ "    cah.ROLE_ID = ?\r\n"
				+ "    AND CCR.Status = '3'\r\n"
				+ "    AND CCR.ContractorId = ?\r\n"
				+ "    AND CCR.UnitId = ?\r\n"
				+ "    AND CCR.TYPE = 'renew'\r\n"
				+ "    AND NOT EXISTS (\r\n"
				+ "        SELECT 1\r\n"
				+ "        FROM CONTRENEWAPPROVALSTATUS gas\r\n"
				+ "        WHERE gas.CONTRACTORREGID = CCR.CONTRACTORREGID\r\n"
				+ "          AND gas.RoleId = ?\r\n"
				+ "    );";
		log.info("Query to getBVRListingForApprovers "+query);
		 rs = jdbcTemplate.queryForRowSet(query,roleId,deptId,principalEmployerId,roleId);
		//String query1 = getContRenewListForParallelApprovers();
		
	}
	
	while(rs.next()) {
		ContractorRegistration pe  = new ContractorRegistration();
		pe.setContractorregId(rs.getString("CONTRACTORREGID"));
		pe.setPrincipalEmployer(rs.getString("NAME"));
		pe.setVendorCode(rs.getString("CODE"));
		pe.setContractorName(rs.getString("CONTRACTORNAME"));
		pe.setStatus(rs.getString("STATUS"));
		pe.setRequestType(rs.getString("TYPE"));
		String status =String.valueOf(rs.getInt("Status"));
		if(status.equals(GatePassStatus.APPROVALPENDING.getStatus())) {
			pe.setStatusValue("Approval Pending");
		}else if(status.equals(GatePassStatus.APPROVED.getStatus())) {
			pe.setStatusValue("Approved");
		}else if(status.equals(GatePassStatus.REJECTED.getStatus())) {
			pe.setStatusValue("Rejected");
		}else if(status.equals(GatePassStatus.DRAFT.getStatus())) {
			pe.setStatusValue("Draft");
		}
		listDto.add(pe);
	}
	
	return listDto;
}

@Override
public String approveRejectContRenew(ApproveRejectContRenewDto dto) {
		 String result = null; 


		        Object[] parameters = new Object[] {dto.getTransactionId(),dto.getApproverId(),dto.getApproverRole(),Integer.parseInt(dto.getStatus()),dto.getComments(),1,dto.getRoleId()}; 

		        try {
		        	String query = approveRejectContRenew();
		        	//String query = "INSERT INTO CONTRENEWAPPROVALSTATUS(ContractorRegId,UserId,UserRole,Status,Comments,ContTypeId,RoleId,LastUpdatedDate)\r\n"
		        	//		+ " VALUES (?,?,?,?,?,?,?,GETDATE())";
		            int status = jdbcTemplate.update(query, parameters);
		            if (status > 0) {
		                result="Contractor Renewal approved/rejected successfully";
		            } else {
		                log.warn("Failed to approve/reject Contractor Renewal for transactionId: " +  dto.getTransactionId());
		            }
		        } catch (Exception e) {
		            log.error("Error approving/rejecting Contractor Renewal for transactionId: " +  dto.getTransactionId(), e);
		            return null;
		        }
		    

		    return result;
	}

@Override
public synchronized boolean updateContStatusByTransactionId(String transactionId, String status) {
	boolean res=false;
	Object[] object=new Object[]{status,transactionId};
	String query = updateContStatusByTransactionId();
	//String query= "update CMSContractorRegistration set STATUS=? where CONTRACTORREGID=?";
	int i = jdbcTemplate.update(query,object);
	if(i>0){
		res=true;
	}
	return res;
}
@Override
public int getWorkFlowTYpeByTransactionId(String transactionId) {
	log.info("Entering into getWorkFlowTYpe dao method ");
	int workflowTypeId = 0;
	String query ="select distinct cwt.WorkflowType from CMSContractorRegistration ccr \r\n"
			+ " join CMSPRINCIPALEMPLOYER CPE on cpe.UNITID=ccr.UnitId\r\n"
			+ " join CMSWORKFLOWTYPE cwt on cwt.UnitId = ccr.UnitId  \r\n"
			+ " join CMSAPPROVERHIERARCHY cah on cah.WorkFlowTypeId=cwt.WorkFlowTypeId\r\n"
			+ " WHERE ccr.CONTRACTORREGID=?  and cah.ACTION_NAME='Contractor Renewal'";
	//String query = getWorkFlowTypeByTransactionIdforContReg();
	
	log.info("Query to getWorkFlowTYpe "+query);
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query,transactionId);
	if(rs.next()) {
		workflowTypeId = rs.getInt("WorkflowType");
	}
	log.info("Exiting from getWorkFlowTYpe dao method "+transactionId);
	return workflowTypeId;
}
@Override
public boolean isLastApprover(String roleName,String unitId) {
	boolean status=false;
	String query="select Role_Name FROM CMSAPPROVERHIERARCHY cah \r\n"
			+ "join CMSWORKFLOWTYPE cwt on cwt.WorkflowTypeId=cah.WORKFLOWTYPEID\r\n"
			+ "WHERE  cah.[Index] = (SELECT MAX(cah1.[Index])\r\n"
			+ "FROM CMSAPPROVERHIERARCHY cah1 join CMSWORKFLOWTYPE cwt1 on cwt1.WorkflowTypeId=cah1.WORKFLOWTYPEID\r\n"
			+ "WHERE cah1.ACTION_NAME = 'Contractor Renewal'  AND cwt1.UnitId=?) \r\n"
			+ "and cah.ACTION_NAME='Contractor Renewal' and cwt.UnitId=?";
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId,unitId);
	//String query = isLastApprover();
	
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

    String query = "WITH RequiredApprovers AS (SELECT ROLE_ID FROM CMSAPPROVERHIERARCHY cah\r\n"
    		+ "join CMSWORKFLOWTYPE cwt on cwt.WorkflowTypeId=cah.WORKFLOWTYPEID\r\n"
    		+ "WHERE cah.ACTION_NAME = 'Contractor Renewal' and cwt.UnitId=?\r\n"
    		+ "AND cah.[INDEX] != 0  ), ApprovedRoles AS (SELECT DISTINCT RoleId FROM CONTRENEWAPPROVALSTATUS\r\n"
    		+ "WHERE CONTRACTORREGID = ? and ContTypeId=1 )  SELECT CASE WHEN (SELECT COUNT(*) \r\n"
    		+ "FROM ApprovedRoles) = \r\n"
    		+ " (SELECT COUNT(*) FROM RequiredApprovers) AND\r\n"
    		+ " EXISTS (SELECT 1 FROM ApprovedRoles WHERE RoleId = ?) THEN 'YES' ELSE 'NO' END AS IsLastApprover";
   // String query = isLastApproverforParallel();
   

    try {
        // Ensure proper data type conversion
       
        int approverRoleId = Integer.parseInt(roleId);

        SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId, transactionId, approverRoleId);
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
@Override
public void insertWorkOrderLLWC(String contractorRegId, String contractorId, String unitId, String createdBy) {
    try {
        // Step 1: Fetch all LLWC records for this contractorRegId
    	String fetchSql = getWorkorderNumber();
        //String fetchSql = "SELECT WONUMBER, WCCODE, LICENCETYPE FROM CMSCONTRACTORREGISTRATIONLLWC WHERE CONTRACTORREGID = ?";
        List<Map<String, Object>> records = jdbcTemplate.queryForList(fetchSql, contractorRegId);

        if (records == null || records.isEmpty()) {
            System.out.println("⚠️ No LLWC records found for ContractorRegId: " + contractorRegId);
            return;
        }

        // Step 2: Prepare insert SQL for CMSWORKORDER_LLWC
        //String insertSql = "INSERT INTO CMSWORKORDER_LLWC (WONUMBER, LICENSE_NUMBER, LICENSE_TYPE) VALUES (?, ?, ?)";
        String insertSql = insertWorkOrderLLWC();
        // Step 3: Loop through records and insert one by one
        for (Map<String, Object> record : records) {
            try {
                String woNumber = (String) record.get("WONUMBER");
                String licenseNum = (String) record.get("WCCODE");
                String licenseType = (String) record.get("LICENCETYPE");

                jdbcTemplate.update(insertSql, woNumber, licenseNum, licenseType);
                System.out.println("✅ Inserted record: WO=" + woNumber + ", License=" + licenseNum + ", Type=" + licenseType);
            } catch (Exception innerEx) {
                System.err.println("⚠️ Skipped one record due to error: " + innerEx.getMessage());
            }
        }

        System.out.println("✅ All eligible records inserted into CMSWORKORDER_LLWC for ContractorRegId: " + contractorRegId);

    } catch (Exception e) {
        System.err.println("❌ Error in insertWorkOrderLLWC: " + e.getMessage());
        e.printStackTrace();
    }
}
@Override
public Map<String, String> getContractorPreviousDocuments(String contractorRegId, String requestType) {
	String sql = getContractorPreviousDocuments();
	//String sql = "select ccr.AADHARDOCNAME,ccr.PANDOCNAME,ccr.PFDOCNAME,ccrp.ATTACHMENTNAME from CMSContractorRegistration ccr\r\n"
    //		+ "join CMSContractorRegPolicy ccrp on ccrp.CONTRACTORREGID=ccr.CONTRACTORREGID\r\n"
    //		+ "where ccr.CONTRACTORREGID=? and ccr.TYPE=?";

    return jdbcTemplate.query(sql, rs -> {
        Map<String, String> map = new LinkedHashMap<>();
        if (rs.next()) {
        //Map<String, String> map = new LinkedHashMap<>();
        map.put("AADHAR", rs.getString("AADHARDOCNAME"));
        map.put("PAN", rs.getString("PANDOCNAME"));
        map.put("PF", rs.getString("PFDOCNAME"));
        map.put("Attachment", rs.getString("ATTACHMENTNAME"));
        }
        return map;
    }, contractorRegId,requestType);
    }
public String getAllContractorProfileDetailForReg() {
    return QueryFileWatcher.getQuery("GET_ALL_CONTRACTOR_PROFILE_DETAILS_FOR_REG");
}
@Override
public Contractor getAllContractorProfileDetailForReg(String unitId, String contractorId) {
	String query=getAllContractorProfileDetailForReg();
	log.info("Query to getAllContractorBasedOnPE "+query);
	SqlRowSet rs = jdbcTemplate.queryForRowSet(query,unitId,contractorId);
	Contractor cont = new Contractor();
	while(rs.next()) {
		cont.setManagerName(rs.getString("MANAGERNM"));
		cont.setEsiwc(rs.getString("ESIWC"));
		cont.setValidTo(rs.getString("VALIDTODT"));
	}
	return cont;
}

public String getLLActiveWorkmenCount() {
    return QueryFileWatcher.getQuery("GET_LL_ACTIVE_WORKMEN_COUNT");
}

public int getLLActiveWorkmenCount(String contractorId, String unitId, String llNo) {
    String sql =getLLActiveWorkmenCount();
    Integer count = jdbcTemplate.queryForObject(sql,Integer.class,contractorId,unitId,llNo);
    return count != null ? count : 0;
}

public String getWCESICActiveWorkmenCount() {
    return QueryFileWatcher.getQuery("GET_WCESIC_ACTIVE_WORKMEN_COUNT");
}

public int getWCESICActiveWorkmenCount(String contractorId, String unitId, String wcesic) {
    String sql =getWCESICActiveWorkmenCount();
    Integer count = jdbcTemplate.queryForObject(sql,Integer.class,contractorId,unitId,wcesic);
    return count != null ? count : 0;
}

public String getWorkorderActiveWorkmenCount() {
    return QueryFileWatcher.getQuery("GET_WORKORDER_ACTIVE_WORKMEN_COUNT");
}

public int getWorkorderActiveWorkmenCount(String contractorId, String unitId, String workorderId) {
    String sql =getWorkorderActiveWorkmenCount();
    Integer count = jdbcTemplate.queryForObject(sql,Integer.class,contractorId,unitId,workorderId);
    return count != null ? count : 0;
}

public void saveWorkorderLLWC(List<ContractorRegistrationPolicy> policies) {
	 String sql= "INSERT INTO CMSWORKORDER_LLWC (" +
           "[WONUMBER],[LICENSE_NUMBER],[LICENSE_TYPE]) " +
          "VALUES (?,?,?)";

   for (ContractorRegistrationPolicy policy : policies) {
       jdbcTemplate.update(sql,
    		   policy.getSapWoNumber(),
    		   policy.getDocumentNumber(),
           policy.getDocumentType()              // LICENCE_TYPE
    		   );
   }
}
@Override
public boolean contractorExistsForPeContractor(String contractorId, Long unitid) {
    String sql = "select count(*)  from CMSCONTRPEMM where CONTRACTORID=? and UNITID=?";
Integer count = jdbcTemplate.queryForObject(sql, Integer.class, contractorId, unitid);
return count != null && count > 0;
}
@Override
public void updateContractorPemm(ContractorRegistration contreg) {

    String sql ="UPDATE CMSCONTRPEMM SET MANAGERNM = ?,TOTALSTRENGTH = ?,MAXNOEMP = ?,NATUREOFWORK = ?,LOCOFWORK = ?, PFNUM = ?,RCVALIDATED = ?,PERIODSTARTDT = ?,PERIODENDDT = ?, PFAPPLYDT = ? WHERE CONTRACTORID = ? AND UNITID = ?";

    try {
        jdbcTemplate.update(
            sql,
            contreg.getManagerName(),
            contreg.getTotalStrength() != null ? Integer.parseInt(contreg.getTotalStrength()) : null,
            contreg.getRcMaxEmp() != null ? Integer.parseInt(contreg.getRcMaxEmp()) : null,
            contreg.getNatureOfWork(),
            contreg.getLocofWork(),
            contreg.getPfNum(),
            (contreg.getRcVerified() != null &&
             (contreg.getRcVerified().equalsIgnoreCase("Y")
              || contreg.getRcVerified().equalsIgnoreCase("YES")))
                ? "Y" : "N",
            contreg.getContractFrom() != null ? java.sql.Date.valueOf(contreg.getContractFrom()) : null,
            contreg.getContractTo() != null ? java.sql.Date.valueOf(contreg.getContractTo()) : null,
            contreg.getPfApplyDate() != null ? java.sql.Date.valueOf(contreg.getPfApplyDate()) : null,

            // WHERE condition values (LAST)
            contreg.getContractorId(),
            contreg.getUnitId()
        );

    } catch (Exception e) {
        e.printStackTrace();
    }
}

@Override
public ApproveRejectContRenewDto getContractorRenewComments(String contractorRegId) {
	String sql ="select top 1 comments,ContractorRegId from CONTRENEWAPPROVALSTATUS where ContractorRegId=? order by LastUpdatedDate desc";
	SqlRowSet rs = jdbcTemplate.queryForRowSet(sql,contractorRegId);
	ApproveRejectContRenewDto cont = new ApproveRejectContRenewDto();
	while(rs.next()) {
		cont.setComments(rs.getString("comments"));		
		cont.setTransactionId(rs.getString("ContractorRegId"));
	}
	return cont;
}

}

