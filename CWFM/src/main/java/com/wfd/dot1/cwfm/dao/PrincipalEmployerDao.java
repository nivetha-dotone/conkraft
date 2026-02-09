package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.pojo.KronosReport;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployerDocument;

public interface PrincipalEmployerDao {

	public List<PrincipalEmployer> getAllPrincipalEmployer(String userAccount);

	public PrincipalEmployer getIndividualPEDetailByUnitId(String id);

	public List<PrincipalEmployer> getAllPrincipalEmployerForAdmin();

    void saveDocument(PrincipalEmployerDocument doc);
    
    int getNextVersion(int employerId, String docType);
    
    List<PrincipalEmployerDocument> getDocumentsByEmployerId(String employerId);
    
    List<KronosReport> getAllDocTypes();
    
    PrincipalEmployerDocument getDocumentById(int docId);

	public List<PrincipalEmployer> getAllDepartmentForAdmin();

}
