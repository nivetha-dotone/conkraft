package com.wfd.dot1.cwfm.service;

import java.util.List;

import com.wfd.dot1.cwfm.dto.RenewalDTO;
import com.wfd.dot1.cwfm.dto.RenewalDocumentDTO;
import com.wfd.dot1.cwfm.pojo.CMSContractorRegistrationLLWC;

public interface RenewalViewService {
    RenewalDTO getRegistrationDetails(Long contractorRegId);
    List<RenewalDocumentDTO> getPolicyDocuments(Long contractorRegId);
    List<CMSContractorRegistrationLLWC> getLLWCDetails(Long contractorRegId);
}

