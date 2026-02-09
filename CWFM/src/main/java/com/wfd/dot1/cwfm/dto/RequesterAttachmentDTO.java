package com.wfd.dot1.cwfm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequesterAttachmentDTO {

    private String attachmentId;     // e.g. ATT00001
    private String transactionId;    // maps to REQUESTER.TransactionId
    private String fileName;         // stored file name
    private String filePath;         // full/relative path
    private String fileType;         // MIME type (pdf, jpg, etc.)
    private String uploadedDate;     // store as String for JSON (yyyy-MM-dd HH:mm:ss)
    private String uploadedBy;       // who uploaded


}
