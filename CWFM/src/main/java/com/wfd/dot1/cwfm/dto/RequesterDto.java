package com.wfd.dot1.cwfm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequesterDto {

    private String transactionId;              // Primary Key
    private Long prEmpId;                      // Principal Employer reference
    private Long contractorId;                 // Contractor reference
    private String name;                       // Requester full name
    private String aadharNumber;               // Aadhaar number
    private Long forPostId;                    // Refers to Workman table
    private Long academicId;                   // Academic reference
    private String additionalQualification;    // Extra qualification
    private String attachmentCv;               // Path or file name of CV
    private String fileName;               // Path or file name of CV
    private String fileType;               // Path or file name of CV
    private String shortNote;

    private Boolean status;                    // Status (e.g. 1,2,3,4,5)

    private LocalDateTime updatedDate;         // Last update timestamp
    private String updatedBy;                  // Who updated it

}
