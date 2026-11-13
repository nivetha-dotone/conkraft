package com.wfd.dot1.cwfm.dao;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import com.wfd.dot1.cwfm.dto.BankWageDetailDTO;
import com.wfd.dot1.cwfm.dto.BankWageExpectedDTO;
import com.wfd.dot1.cwfm.dto.BankWageUploadDTO;
import com.wfd.dot1.cwfm.dto.ReconciliationComplianceDetailDTO;
import com.wfd.dot1.cwfm.dto.ReconciliationHistoryDTO;
import com.wfd.dot1.cwfm.dto.ReconciliationMismatchDTO;
import com.wfd.dot1.cwfm.dto.WorkmenReconciliationDTO;

public interface ReconciliationDao {

    List<WorkmenReconciliationDTO> getPfReconciliationList(Long contractorId, String fromDate, String toDate);

    List<WorkmenReconciliationDTO> getEsicReconciliationList(Long contractorId,String fromDate, String toDate);

    Long saveUploadMaster(Long contractorId, String reconType, String fileName, String filePath,
                          String overallStatus, int totalCount, int verifiedCount, int unverifiedCount,
                          String fromDate, String toDate, String uploadedBy);

    void saveMismatchList(Long uploadId, List<ReconciliationMismatchDTO> mismatchList);
    
    List<BankWageExpectedDTO> getExpectedBankWages(
            Long principalEmployerId,
            Long contractorId,
            String fromDate,
            String toDate);

List<String> findExistingBankUtrs(List<String> utrNumbers);

Long saveBankUpload(BankWageUploadDTO upload);

void saveBankDetails(
        Long uploadId,
        List<BankWageDetailDTO> details);

boolean existsBankWageReconciliation(
        Long contractorId,
        YearMonth wageMonth);

List<Map<String, Object>> findInvalidBankWageRecords(
        Long principalEmployerId,
        Long contractorId,
        String fromDate,
        String toDate);

boolean isValidBankWagePeContractor(
        Long principalEmployerId,
        Long contractorId);


List<BankWageDetailDTO> getBankWageDetails(Long uploadId);

void saveComplianceDetails(
        Long uploadId,
        List<ReconciliationComplianceDetailDTO> details);

List<ReconciliationHistoryDTO> getReconciliationHistory(
        List<Long> contractorIds);
List<ReconciliationComplianceDetailDTO> getSavedComplianceDetails(
        Long uploadId);

List<ReconciliationMismatchDTO> getSavedMismatchDetails(
        Long uploadId);

boolean hasReconciliationUploadAccess(
        Long uploadId,
        String reconType,
        List<Long> contractorIds);
}