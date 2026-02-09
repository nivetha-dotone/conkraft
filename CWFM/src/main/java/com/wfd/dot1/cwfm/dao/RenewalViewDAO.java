package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.dto.RenewalDTO;
import com.wfd.dot1.cwfm.dto.RenewalDocumentDTO;
import com.wfd.dot1.cwfm.pojo.CMSContractorRegistrationLLWC;

public interface RenewalViewDAO {
    RenewalDTO fetchRegistration(Long contractorRegId);
    List<RenewalDocumentDTO> fetchPolicies(Long contractorRegId);
    List<CMSContractorRegistrationLLWC> fetchLLWC(Long contractorRegId);
}

