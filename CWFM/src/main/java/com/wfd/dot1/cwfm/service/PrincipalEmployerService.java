package com.wfd.dot1.cwfm.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.wfd.dot1.cwfm.pojo.KronosReport;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployerDocument;

public interface PrincipalEmployerService {
	
	public List<PrincipalEmployer> getAllPrincipalEmployer(String userAccount);

	public PrincipalEmployer getIndividualPEDetailByUnitId(String id);

	public List<PrincipalEmployer> getAllPrincipalEmployerForAdmin();

	void saveDocument(int employerId, String docType, MultipartFile file, String uploadedBy) throws IOException;
	
	 Map<String, List<PrincipalEmployerDocument>> getDocumentsGroupedByType(String id);
	 
	    List<KronosReport> getAllDocTypes();

		List<PrincipalEmployer> getAllDepartmentForAdmin();
}
