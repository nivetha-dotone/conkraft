package com.wfd.dot1.cwfm.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.wfd.dot1.cwfm.pojo.WorkmenBulkUpload;

public interface WorkmenBulkUploadDao {

	List<WorkmenBulkUpload> getAllWorkmenBulkUploadData();

	WorkmenBulkUpload getByTransactionId(int transactionId);

	void saveToGatePassMain(WorkmenBulkUpload data,String createdBy);
	
	void updateRecordStatusByTransactionId(int txnId, String combinedErrors);

	void updateRecordProcessedByTransactionId(Integer txnId);

	public boolean isAadharExists(String aadharNumber);
	
	public LocalDate workorderValidityCheck(String workorderNumber);

	String getNextTransactionId();

}
