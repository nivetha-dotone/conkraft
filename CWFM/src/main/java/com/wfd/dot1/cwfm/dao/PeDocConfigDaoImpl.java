package com.wfd.dot1.cwfm.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.BillReportFile;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
@Repository
public class PeDocConfigDaoImpl implements PeDocConfigDao {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public String fetchPeDocument() {
	    return QueryFileWatcher.getQuery("FETCH_PE_DOCUMENTS");
	}
	public String saveDocument() {
	    return QueryFileWatcher.getQuery("SAVE_DOCUMENT");
	}
	@Override
    public void clearExisting() {
        jdbcTemplate.update("DELETE FROM PeDocConfigKronos");
       
    }
	
	@Override
    public List<String> fetchPeDocuments() {
		String query = fetchPeDocument();
        //String sql = "SELECT REPORTNAME FROM PeDocConfigKronos";
        return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("REPORTNAME"));
    }

	 @Override
	    public void saveDocument(String reportName) {
		 String query = saveDocument();
	        //String sql = "INSERT INTO PeDocConfigKronos (REPORTNAME) VALUES (?)";
	        jdbcTemplate.update(query, reportName.trim());
	    }

	

	
}
