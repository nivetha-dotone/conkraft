package com.wfd.dot1.cwfm.dto;

import lombok.Data;
import org.springframework.core.io.Resource;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RequestorListDTO {

    private String transactionId;              // Primary Key
    private Integer unitID;                      // Principal Employer reference
    private String unitName;                      // Principal Employer Name
    private Integer contractorId;                 // Contractor reference
    private String contractName;               // Contractor full name
    private String name;                       // Requester full name
    private String aadharNumber;               // Aadhaar number
    private String forPostId;                    // Refers to Workman table
    private Integer academicId;                   // Academic reference
    private String academicString;             // Academic Name
    private String additionalQualification;    // Extra qualification
    private String attachmentCv;               // Path or file name of CV
    private String fileName;               // Path or file name of CV
    private String fileType;               // Path or file name of CV
    private String shortNote;
    private Boolean status;                    // Status (e.g. 1,2,3,4,5)
    private String updatedDate;         // Last update timestamp
    private String updatedBy;
    private String ApproveBy;                  // Who updated it
    private String RemarkApprover;
    private List<RequesterAttachmentDTO> requesterAttachmentDTOList;
}