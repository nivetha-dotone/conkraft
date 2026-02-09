package com.wfd.dot1.cwfm.dao;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.KronosReport;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployerDocument;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Repository
public class PrincipalEmployerDaoImpl implements PrincipalEmployerDao {

	private static final Logger log = LoggerFactory.getLogger(PrincipalEmployerDaoImpl.class.getName());
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	    public String getAllPes() {
		    return QueryFileWatcher.getQuery("GET_ALL_PES");
		}
	    
	    public String getPeByUnitId() {
		    return QueryFileWatcher.getQuery("GET_PE_BY_UNITID");
		}
	    
	    public String getAllPesForAdmin() {
		    return QueryFileWatcher.getQuery("GET_ALL_PES_FOR_ADMIN");
		}
	    public String getNextVersion() {
		    return QueryFileWatcher.getQuery("GET_NEXT_VERSION_OF_DOCUMENT");
		}
	    public String savePeDocument() {
		    return QueryFileWatcher.getQuery("SAVE_PE_DOCUMENTS");
		}
	    public String getDocumentsByEmployerId() {
		    return QueryFileWatcher.getQuery("GET_DOCS_BY_EMPLOYERID");
		}
	    public String getAllPeDocTypes() {
		    return QueryFileWatcher.getQuery("GET_ALL_DOCTYPES");
		}
	    public String getDocumentById() {
		    return QueryFileWatcher.getQuery("GET_DOCUMENT_BY_ID");
		}
	    public String getAllDepartmentsForAdmin() {
		    return QueryFileWatcher.getQuery("GET_AAL_DEPARTMENTS_FOR_ADMIN");
		}
	 
	@Override
	public List<PrincipalEmployer> getAllPrincipalEmployer(String userAccount) {
		log.info("Entering into getAllPrincipalEmployer dao method ");
		List<PrincipalEmployer> peList= new ArrayList<PrincipalEmployer>();
		String query = getAllPes();
		log.info("Query to getAllPrincipalEmployer "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,userAccount);
		while(rs.next()) {
			PrincipalEmployer pe = new PrincipalEmployer();
			pe.setUnitId(rs.getInt("UNITID"));
			pe.setName(rs.getString("NAME"));
			pe.setAddress(rs.getString("ADDRESS"));
			pe.setManagerName(rs.getString("MANAGERNAME"));
			pe.setManagerAddrs(rs.getString("MANAGERADDRS"));
			pe.setBusinessType(rs.getString("BUSINESSTYPE"));
			pe.setMaxWorkmen(rs.getInt("MAXWORKMEN"));
			pe.setMaxCntrWorkmen(rs.getInt("MAXCNTRWORKMEN"));
			pe.setBocwApplicability(rs.getInt("BOCWAPPLICABILITY"));
			pe.setIsMwApplicability(rs.getInt("ISMWAPPLICABILITY"));
			pe.setCode(rs.getString("CODE"));
			pe.setOrganization(rs.getString("ORGANIZATION"));
			pe.setPfCode(rs.getString("PFCODE"));
			pe.setLicenseNumber(rs.getString("LICENSENUMBER"));
			pe.setWcNumber(rs.getString("WCNUMBER"));
			pe.setEsicNumber(rs.getString("ESICNUMBER"));
			pe.setPtRegNo(rs.getString("PTREGNO"));
			pe.setLwfRegNo(rs.getString("LWFREGNO"));
			pe.setFactoryLicenseNumber(rs.getString("FACTORYLICENCENUMBER"));
			pe.setIsRcApplicable(rs.getString("ISRCAPPLICABLE"));
			pe.setRcNumber(rs.getString("RCNUMBER"));
			pe.setAttachmentNm(rs.getString("ATTACHMENTNM"));
			pe.setStateId(rs.getInt("STATEID"));
			pe.setIsActive(rs.getInt("ISACTIVE"));
			
			peList.add(pe);
		}
		log.info("Exiting from getAllPrincipalEmployer dao method "+peList.size());
		return peList;
	}

	@Override
	public PrincipalEmployer getIndividualPEDetailByUnitId(String id) {
		log.info("Entering into getIndividualPEDetailByUnitId dao method ");
		PrincipalEmployer pe = new PrincipalEmployer();
		String query =getPeByUnitId();
		log.info("Query to getIndividualPEDetailByUnitId "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,id);
		while(rs.next()) {
		
			pe.setUnitId(rs.getInt("UNITID"));
			pe.setName(rs.getString("NAME"));
			pe.setAddress(rs.getString("ADDRESS"));
			pe.setManagerName(rs.getString("MANAGERNAME"));
			pe.setManagerAddrs(rs.getString("MANAGERADDRS"));
			pe.setBusinessType(rs.getString("BUSINESSTYPE"));
			pe.setMaxWorkmen(rs.getInt("MAXWORKMEN"));
			pe.setMaxCntrWorkmen(rs.getInt("MAXCNTRWORKMEN"));
			pe.setBocwApplicability(rs.getInt("BOCWAPPLICABILITY"));
			pe.setIsMwApplicability(rs.getInt("ISMWAPPLICABILITY"));
			pe.setCode(rs.getString("CODE"));
			pe.setOrganization(rs.getString("ORGANIZATION"));
			pe.setPfCode(rs.getString("PFCODE"));
			pe.setLicenseNumber(rs.getString("LICENSENUMBER"));
			pe.setWcNumber(rs.getString("WCNUMBER"));
			pe.setEsicNumber(rs.getString("ESICNUMBER"));
			pe.setPtRegNo(rs.getString("PTREGNO"));
			pe.setLwfRegNo(rs.getString("LWFREGNO"));
			pe.setFactoryLicenseNumber(rs.getString("FACTORYLICENCENUMBER"));
			pe.setIsRcApplicable(rs.getString("ISRCAPPLICABLE"));
			pe.setRcNumber(rs.getString("RCNUMBER"));
			pe.setAttachmentNm(rs.getString("ATTACHMENTNM"));
			pe.setStateId(rs.getInt("STATEID"));
			pe.setIsActive(rs.getInt("ISACTIVE"));
			
		}
		log.info("Exiting from getIndividualPEDetailByUnitId dao method ");
		return pe;
	}

	@Override
	public List<PrincipalEmployer> getAllPrincipalEmployerForAdmin() {
		log.info("Entering into getAllPrincipalEmployerForAdmin dao method ");
		List<PrincipalEmployer> peList= new ArrayList<PrincipalEmployer>();
		String query = getAllPesForAdmin();
		log.info("Query to getAllPrincipalEmployerForAdmin "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		while(rs.next()) {
			PrincipalEmployer pe = new PrincipalEmployer();
			pe.setUnitId(rs.getInt("UNITID"));
			pe.setName(rs.getString("NAME"));
			pe.setAddress(rs.getString("ADDRESS"));
			pe.setManagerName(rs.getString("MANAGERNAME"));
			pe.setManagerAddrs(rs.getString("MANAGERADDRS"));
			pe.setBusinessType(rs.getString("BUSINESSTYPE"));
			pe.setMaxWorkmen(rs.getInt("MAXWORKMEN"));
			pe.setMaxCntrWorkmen(rs.getInt("MAXCNTRWORKMEN"));
			pe.setBocwApplicability(rs.getInt("BOCWAPPLICABILITY"));
			pe.setIsMwApplicability(rs.getInt("ISMWAPPLICABILITY"));
			pe.setCode(rs.getString("CODE"));
			pe.setOrganization(rs.getString("ORGANIZATION"));
			pe.setPfCode(rs.getString("PFCODE"));
			pe.setLicenseNumber(rs.getString("LICENSENUMBER"));
			pe.setWcNumber(rs.getString("WCNUMBER"));
			pe.setEsicNumber(rs.getString("ESICNUMBER"));
			pe.setPtRegNo(rs.getString("PTREGNO"));
			pe.setLwfRegNo(rs.getString("LWFREGNO"));
			pe.setFactoryLicenseNumber(rs.getString("FACTORYLICENCENUMBER"));
			pe.setIsRcApplicable(rs.getString("ISRCAPPLICABLE"));
			pe.setRcNumber(rs.getString("RCNUMBER"));
			pe.setAttachmentNm(rs.getString("ATTACHMENTNM"));
			pe.setStateId(rs.getInt("STATEID"));
			pe.setIsActive(rs.getInt("ISACTIVE"));
			pe.setId(String.valueOf(rs.getInt("UNITID")));
			pe.setDescription(rs.getString("NAME"));
			peList.add(pe);
		}
		log.info("Exiting from getAllPrincipalEmployerForAdmin dao method "+peList.size());
		return peList;
	}
	
	@Override
	public List<PrincipalEmployer> getAllDepartmentForAdmin() {
		log.info("Entering into getAllPrincipalEmployerForAdmin dao method ");
		List<PrincipalEmployer> peList= new ArrayList<PrincipalEmployer>();
		String query = getAllDepartmentsForAdmin();
		//String query = "select GMID,GMNAME from CMSGENERALMASTER cgm join CMSGMTYPE cgt on cgt.GMTYPEID=cgm.GMTYPEID where cgt.GMTYPE='Department'";
		log.info("Query to getAllPrincipalEmployerForAdmin "+query);
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		while(rs.next()) {
			PrincipalEmployer pe = new PrincipalEmployer();
			pe.setId(rs.getString("GMID"));
			pe.setDescription(rs.getString("GMNAME"));
			
			peList.add(pe);
		}
		log.info("Exiting from getAllPrincipalEmployerForAdmin dao method "+peList.size());
		return peList;
	}

	@Override
    public void saveDocument(PrincipalEmployerDocument doc) {
		String query = savePeDocument();
       // String sql = "INSERT INTO principal_employer_documents " +
                   //  "(principal_employer_id, file_name, version, upload_date, uploaded_by, doc_type, file_path) " +
                   //  "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, doc.getPrincipalEmployerId(), doc.getFileName(), doc.getVersion(),
               doc.getUploadDate(), doc.getUploadedBy(), doc.getDocType(), doc.getFilePath());
    }

    @Override
    public int getNextVersion(int employerId, String docType) {
    	String query = getNextVersion();
       // String sql = "SELECT MAX(version) FROM principal_employer_documents WHERE principal_employer_id = ? AND doc_type = ?";
        Integer version = jdbcTemplate.queryForObject(query, Integer.class, employerId, docType);
        return (version == null ? 1 : version + 1);
    }

    @Override
    public List<PrincipalEmployerDocument> getDocumentsByEmployerId(String employerId) {
    	String query = getDocumentsByEmployerId();
        //String sql = "SELECT * FROM principal_employer_documents WHERE principal_employer_id = ? ORDER BY doc_type, version DESC";
        return jdbcTemplate.query(query, new Object[]{employerId}, new BeanPropertyRowMapper<>(PrincipalEmployerDocument.class));
    }

    @Override
    public List<KronosReport> getAllDocTypes() {
    	String query = getAllPeDocTypes();
        //String sql = "SELECT REPORTNAME FROM PeDocConfigKronos ORDER BY REPORTNAME";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(KronosReport.class));
    }
    @Override
    public PrincipalEmployerDocument getDocumentById(int docId) {
    	String query = getDocumentById();
       // String sql = "SELECT * FROM principal_employer_documents WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{docId}, new BeanPropertyRowMapper<>(PrincipalEmployerDocument.class));
    }

}
