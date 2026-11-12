package com.wfd.dot1.cwfm.service;

import java.time.YearMonth;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.wfd.dot1.cwfm.dto.BankWageDetailDTO;
import com.wfd.dot1.cwfm.dto.BankWageExpectedDTO;
import com.wfd.dot1.cwfm.dto.BankWagePaymentDTO;
import com.wfd.dot1.cwfm.dto.BankWageUploadDTO;
import com.wfd.dot1.cwfm.dto.ReconciliationResultDTO;
import com.wfd.dot1.cwfm.dto.WorkmenReconciliationDTO;

public interface ReconciliationService {

    List<WorkmenReconciliationDTO> getPfReconciliationList(Long contractorId, String fromDate, String toDate);

    List<WorkmenReconciliationDTO> getEsicReconciliationList(Long contractorId, String fromDate, String toDate);

    ReconciliationResultDTO processReconciliation(Long contractorId, String reconType,
                                                  String fromDate, String toDate, MultipartFile file, String uploadedBy) throws Exception;
    
    List<BankWagePaymentDTO> parseBankWageCsv(
            MultipartFile file)throws Exception;

	List<BankWageDetailDTO> reconcileBankWages(List<BankWageExpectedDTO> expectedList,
			List<BankWagePaymentDTO> paymentList, YearMonth wageMonth);

	void calculateBankUploadSummary(BankWageUploadDTO upload, List<BankWageDetailDTO> details);
	

Long processBankWageReconciliation(
        Long principalEmployerId,
        Long contractorId,
        String wageMonth,
        String fromDate,
        String toDate,
        MultipartFile file,
        String uploadedBy) throws Exception;

List<BankWageExpectedDTO> getExpectedBankWages(Long principalEmployerId, Long contractorId, String fromDate,
		String toDate);

List<BankWageDetailDTO> getBankWageDetails(Long uploadId);

}