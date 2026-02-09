package com.wfd.dot1.cwfm.service;

import java.util.List;

public interface PeDocConfigService {
	
	 List<String> getAllPeDocuments();

	void saveDocuments(List<String> documentNames);

}
