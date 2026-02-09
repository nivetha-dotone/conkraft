package com.wfd.dot1.cwfm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.RenewalViewDAO;
import com.wfd.dot1.cwfm.dto.RenewalDTO;
import com.wfd.dot1.cwfm.dto.RenewalDocumentDTO;
import com.wfd.dot1.cwfm.pojo.CMSContractorRegistrationLLWC;

@Service
public class RenewalViewServiceImpl implements RenewalViewService {

    @Autowired
    private RenewalViewDAO renewalViewDAO;

    @Override
    public RenewalDTO getRegistrationDetails(Long contractorRegId) {
        return renewalViewDAO.fetchRegistration(contractorRegId);
    }

    @Override
    public List<RenewalDocumentDTO> getPolicyDocuments(Long contractorRegId) {
        return renewalViewDAO.fetchPolicies(contractorRegId);
    }

    @Override
    public List<CMSContractorRegistrationLLWC> getLLWCDetails(Long contractorRegId) {
        return renewalViewDAO.fetchLLWC(contractorRegId);
    }
}

