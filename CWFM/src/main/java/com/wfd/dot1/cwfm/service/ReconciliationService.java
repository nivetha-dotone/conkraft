package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.wfd.dot1.cwfm.dto.ReconciliationResultDTO;
import com.wfd.dot1.cwfm.dto.WorkmenReconciliationDTO;

public interface ReconciliationService {

    List<WorkmenReconciliationDTO> getPfReconciliationList(Long contractorId, String fromDate, String toDate);

    List<WorkmenReconciliationDTO> getEsicReconciliationList(Long contractorId, String fromDate, String toDate);

    ReconciliationResultDTO processReconciliation(Long contractorId, String reconType,
                                                  String fromDate, String toDate, MultipartFile file, String uploadedBy) throws Exception;
}