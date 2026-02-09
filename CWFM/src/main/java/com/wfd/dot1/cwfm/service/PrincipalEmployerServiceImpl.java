package com.wfd.dot1.cwfm.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wfd.dot1.cwfm.dao.PrincipalEmployerDao;
import com.wfd.dot1.cwfm.pojo.KronosReport;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployerDocument;
@Service
public class PrincipalEmployerServiceImpl implements PrincipalEmployerService{

	@Autowired
	PrincipalEmployerDao peDao;
	
	 private static final String BASE_DIR = "D:/wfd_cwfm/ep_docs/";
	 
	@Override
	public List<PrincipalEmployer> getAllPrincipalEmployer(String userAccount) {
		// TODO Auto-generated method stub
		return peDao.getAllPrincipalEmployer( userAccount);
	}
	@Override
	public PrincipalEmployer getIndividualPEDetailByUnitId(String id) {
		// TODO Auto-generated method stub
		return peDao.getIndividualPEDetailByUnitId(id);
	}
	@Override
	public List<PrincipalEmployer> getAllPrincipalEmployerForAdmin() {
		// TODO Auto-generated method stub
		return peDao.getAllPrincipalEmployerForAdmin();
	}
	
	@Override
	public List<PrincipalEmployer> getAllDepartmentForAdmin() {
		// TODO Auto-generated method stub
		return peDao.getAllDepartmentForAdmin();
	}
	
	@Override
	public void saveDocument(int employerId, String docType, MultipartFile file, String uploadedBy) throws IOException {
	    int version = peDao.getNextVersion(employerId, docType);

	    // Get original filename and extension
	    String originalFileName = file.getOriginalFilename(); // e.g., License.pdf
	    String extension = originalFileName.substring(originalFileName.lastIndexOf('.')); // .pdf
	    String baseFileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'))  // License
	                                      .toLowerCase()
	                                      .replaceAll("\\s+", "_");

	    // Format: docType_originalfilename_vX.extension -> e.g., lali_license_v2.pdf
	    String versionedName = docType.toLowerCase().replaceAll("\\s+", "_") 
	                         + "_" + baseFileName 
	                         + "_v" + version 
	                         + extension;

	    // Create folder if not exists
	    String dirPath = BASE_DIR + employerId + "/";
	    Files.createDirectories(Paths.get(dirPath));

	    // Save file
	    String fullPath = dirPath + versionedName;
	    file.transferTo(new File(fullPath));

	    // Save metadata to DB
	    PrincipalEmployerDocument doc = new PrincipalEmployerDocument();
	    doc.setPrincipalEmployerId(employerId);
	    doc.setDocType(docType);
	    doc.setFileName(versionedName);     // e.g., lali_license_v2.pdf
	    doc.setVersion(version);
	    doc.setUploadedBy(uploadedBy);
	    doc.setUploadDate(LocalDateTime.now());
	    doc.setFilePath(fullPath);

	    peDao.saveDocument(doc);
	}

	@Override
    public Map<String, List<PrincipalEmployerDocument>> getDocumentsGroupedByType(String employerId) {
        List<PrincipalEmployerDocument> docs = peDao.getDocumentsByEmployerId(employerId);
        return docs.stream().collect(Collectors.groupingBy(PrincipalEmployerDocument::getDocType));
    }

    @Override
    public List<KronosReport> getAllDocTypes() {
        return peDao.getAllDocTypes();
    }
}
