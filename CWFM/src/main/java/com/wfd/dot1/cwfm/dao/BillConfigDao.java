package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.pojo.HrChecklistItem;
import com.wfd.dot1.cwfm.pojo.KronosReport;
import com.wfd.dot1.cwfm.pojo.StatutoryAttachment;

public interface BillConfigDao {
	void clearExisting();

    void saveKronosReports(List<String> reports);
    void saveStatutoryReports(List<String> reports);
    void saveChecklistItems(List<HrChecklistItem> items);

    List<String> fetchKronosReports();
    List<String> fetchStatutoryReports();
    List<HrChecklistItem> fetchChecklistItems();

	List<KronosReport> fetchKronosReportsWithId();

	List<StatutoryAttachment> fetchStatutoryReportsWithId();
}
