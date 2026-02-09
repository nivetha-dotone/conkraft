package com.wfd.dot1.cwfm.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wfd.dot1.cwfm.pojo.HrChecklistItem;
import com.wfd.dot1.cwfm.pojo.KronosReport;
import com.wfd.dot1.cwfm.pojo.StatutoryAttachment;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

@Repository
public class BillConfigDaoImpl implements BillConfigDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String clearBillConfigKronos() {
	    return QueryFileWatcher.getQuery("CLEAR_BILLCONFIGKRONOS");
	}
    public String clearBillConfigStatutory() {
	    return QueryFileWatcher.getQuery("CLEAR_BILLCONFIGSTATUTORY");
	}
    public String clearBillConfigHrChecklist() {
	    return QueryFileWatcher.getQuery("CLEAR_BILLCONFIGHRCHECKLIST");
	}
    public String saveKronosReports() {
	    return QueryFileWatcher.getQuery("SAVE_KRONOS_REPORT");
	}
    public String saveStatutoryReports() {
	    return QueryFileWatcher.getQuery("SAVE_STATUTORY_REPORT");
	}
    public String saveChecklistItems() {
	    return QueryFileWatcher.getQuery("SAVE_CHECKLIST_ITEMS");
	}
    public String fetchingKronosReports() {
	    return QueryFileWatcher.getQuery("FETCH_KRONOS_REPORTS");
	}
    public String fetchingStatutoryReports() {
	    return QueryFileWatcher.getQuery("FETCH_STATUTORY_REPORTS");
	}
    public String fetchingChecklistItems() {
	    return QueryFileWatcher.getQuery("FETCH_CHECKLIST_ITEMS");
	}
    public String fetchingKronosReportsWithId() {
	    return QueryFileWatcher.getQuery("FETCH_KRONOS_REPORTS_BY_ID");
	}
    public String fetchingStatutoryReportsWithId() {
	    return QueryFileWatcher.getQuery("FETCH_STATUTORY_REPORTS_BY_ID");
	}
    
    @Override
    public void clearExisting() {
    	String sql=clearBillConfigKronos();
    	String sql1=clearBillConfigStatutory();
    	String sql2=clearBillConfigHrChecklist();
    	
        jdbcTemplate.update(sql);
        jdbcTemplate.update(sql1);
        jdbcTemplate.update(sql2);
    }

    @Override
    public void saveKronosReports(List<String> reports) {
    	String sql=saveKronosReports();
        //String sql = "INSERT INTO BillConfigKronos (REPORTNAME) VALUES (?)";
        for (String report : reports) {
            jdbcTemplate.update(sql, report);
        }
    }

    @Override
    public void saveStatutoryReports(List<String> reports) {
    	String sql=saveStatutoryReports();
       // String sql = "INSERT INTO BillConfigStatutory (ATTACHMENTNAME) VALUES (?)";
        for (String report : reports) {
            jdbcTemplate.update(sql, report);
        }
    }

    @Override
    public void saveChecklistItems(List<HrChecklistItem> items) {
    	String sql=saveChecklistItems();
        //String sql = "INSERT INTO BillConfigHrChecklist (CHECKPOINTNAME, LICENSEREQUIRED, VALIDUPTOREQUIRED) VALUES (?, ?, ?)";
        for (HrChecklistItem item : items) {
            jdbcTemplate.update(sql, item.getCheckpointName(), item.isLicenseRequired(), item.isValidUptoRequired());
        }
    }

    @Override
    public List<String> fetchKronosReports() {
    	String sql=fetchingKronosReports();
        //String sql = "SELECT REPORTNAME FROM BillConfigKronos";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("REPORTNAME"));
    }

    @Override
    public List<String> fetchStatutoryReports() {
    	String sql=fetchingStatutoryReports();
       // String sql = "SELECT ATTACHMENTNAME FROM BillConfigStatutory";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("ATTACHMENTNAME"));
    }

    @Override
    public List<HrChecklistItem> fetchChecklistItems() {
    	String sql=fetchingChecklistItems();
       // String sql = "SELECT ID,CHECKPOINTNAME, LICENSEREQUIRED, VALIDUPTOREQUIRED FROM BillConfigHrChecklist";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            HrChecklistItem item = new HrChecklistItem();
            item.setId(rs.getInt("ID"));
            item.setCheckpointName(rs.getString("CHECKPOINTNAME"));
            item.setLicenseRequired(rs.getBoolean("LICENSEREQUIRED"));
            item.setValidUptoRequired(rs.getBoolean("VALIDUPTOREQUIRED"));
            return item;
        });
    }
    @Override
    public List<KronosReport> fetchKronosReportsWithId() {
    	String sql=fetchingKronosReportsWithId();
       // String sql = "SELECT ID,REPORTNAME FROM BillConfigKronos";
        return jdbcTemplate.query(sql, (rs, rowNum) ->{
        KronosReport kr = new KronosReport();
        kr.setReportName( rs.getString("REPORTNAME"));
        kr.setId(rs.getInt("ID"));
        return kr;
        });
    }

    @Override
    public List<StatutoryAttachment> fetchStatutoryReportsWithId() {
    	String sql=fetchingStatutoryReportsWithId();
       // String sql = "SELECT ID,ATTACHMENTNAME FROM BillConfigStatutory";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
        {
        	StatutoryAttachment kr = new StatutoryAttachment();
            kr.setAttachmentName( rs.getString("ATTACHMENTNAME"));
            kr.setId(rs.getInt("ID"));
            return kr;
            });
    }
}

