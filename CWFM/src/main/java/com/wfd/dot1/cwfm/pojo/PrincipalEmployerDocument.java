package com.wfd.dot1.cwfm.pojo;

import java.sql.Date;
import java.time.LocalDateTime;

public class PrincipalEmployerDocument {
	private int id;
    private int principalEmployerId;
    private String fileName;
    private int version;
    private LocalDateTime  uploadDate;
    private String uploadedBy;
    private String docType;
    private String filePath;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPrincipalEmployerId() {
		return principalEmployerId;
	}
	public void setPrincipalEmployerId(int principalEmployerId) {
		this.principalEmployerId = principalEmployerId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public LocalDateTime  getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate( LocalDateTime  date) {
		this.uploadDate = date;
	}
	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	private String encodedId;
	public String getEncodedId() { return encodedId; }
	public void setEncodedId(String encodedId) { this.encodedId = encodedId; }

    // Getters and Setters
}
