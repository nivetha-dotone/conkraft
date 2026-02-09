package com.wfd.dot1.cwfm.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.dto.RenewalDTO;
import com.wfd.dot1.cwfm.dto.RenewalDocumentDTO;
import com.wfd.dot1.cwfm.pojo.CMSContractorRegistrationLLWC;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Repository
public class RenewalViewDAOImpl implements RenewalViewDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
	public String fetchRegistration() {
	    return QueryFileWatcher.getQuery("FETCH_REGISTRATION");
	}
	public String fetchPolicies() {
	    return QueryFileWatcher.getQuery("FETCH_REGISTRATION_POLICY");
	}
	public String fetchLLWC() {
	    return QueryFileWatcher.getQuery("FETCH_LLWC");
	}
    @Override
    public RenewalDTO fetchRegistration(Long contractorRegId) {
    	String sql = fetchRegistration() ;
       // String sql = "SELECT * FROM CMSContractorRegistration WHERE CONTRACTORREGID = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(RenewalDTO.class), contractorRegId);
    }

    @Override
    public List<RenewalDocumentDTO> fetchPolicies(Long contractorRegId) {
    	String sql = fetchPolicies() ;
        //String sql = "SELECT LICENCETYPE AS documentType, WCCODE AS documentNumber, WCTOTAL AS coverage, \r\n"
        //		+ "                WCFROMDTM AS validFrom, WCTODTM AS validTo, ISGLOBAL AS panIndia, SUBCONTAPPL\r\n"
       // 		+ "				AS subApplicable \r\n"
      //  		+ "                FROM CMSContractorRegPolicy WHERE CONTRACTORREGID = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RenewalDocumentDTO.class), contractorRegId);
    }

    @Override
    public List<CMSContractorRegistrationLLWC> fetchLLWC(Long contractorRegId) {
    	String sql = fetchLLWC() ;
       // String sql = "SELECT * FROM CMSContractorRegistrationLLWC WHERE CONTRACTORREGID = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CMSContractorRegistrationLLWC.class), contractorRegId);
    }
}

