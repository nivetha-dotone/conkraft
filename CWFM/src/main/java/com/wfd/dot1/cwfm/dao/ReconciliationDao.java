package com.wfd.dot1.cwfm.dao;



import java.util.List;

import com.wfd.dot1.cwfm.dto.ReconciliationMismatchDTO;
import com.wfd.dot1.cwfm.dto.WorkmenReconciliationDTO;

public interface ReconciliationDao {

    List<WorkmenReconciliationDTO> getContractorWorkmenList(Long contractorId);

    Long saveUploadMaster(Long contractorId, String reconType, String fileName, String filePath,
                          String overallStatus, int totalCount, int verifiedCount, int unverifiedCount,
                          String uploadedBy);

    void saveMismatchList(Long uploadId, List<ReconciliationMismatchDTO> mismatchList);
}