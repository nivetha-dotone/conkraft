package com.wfd.dot1.cwfm.dao;

import com.wfd.dot1.cwfm.dto.WorkmanFullHistoryDTO;

public interface AadharSearchHistoryDao {

	public WorkmanFullHistoryDTO getFullHistory(String aadhaar);
}
