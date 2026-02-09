package com.wfd.dot1.cwfm.service;

import java.util.List;
import java.util.Map;

import com.wfd.dot1.cwfm.dto.EntryPassStatusDto;
import com.wfd.dot1.cwfm.dto.GatePassListingDto;

public interface EntrypassStatusService {

	List<GatePassListingDto> getGatePassStatus(String transactionId, String gatepassId);

	List<GatePassListingDto> getHistoryofEP(String aadharNumber);

	List<EntryPassStatusDto> getEntryPassStatusReport(String unitId);
}
