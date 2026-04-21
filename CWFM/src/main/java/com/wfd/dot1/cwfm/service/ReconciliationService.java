package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.wfd.dot1.cwfm.dto.ReconciliationResultDTO;
import com.wfd.dot1.cwfm.dto.WorkmenReconciliationDTO;



public interface ReconciliationService {

    List<WorkmenReconciliationDTO> getContractorWorkmenList(Long contractorId);

    ReconciliationResultDTO processReconciliation(Long contractorId, String reconType,
                                                  MultipartFile file, String uploadedBy) throws Exception;
}
