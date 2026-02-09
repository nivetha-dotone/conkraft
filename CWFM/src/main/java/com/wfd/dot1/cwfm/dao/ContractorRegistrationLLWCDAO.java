package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.pojo.CMSContractorRegistrationLLWC;

public interface ContractorRegistrationLLWCDAO {
    int getNextId();
    void insertLLWCRecords(List<CMSContractorRegistrationLLWC> records);
    boolean exists(long regId, int woId, String wcCode);
}
