package com.wfd.dot1.cwfm.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CMSStateMinimumWageDTO {

    private Long refId;              // RefId
    private Long unitId;             // FK -> CMSPrincipalEmployer.UNITID
    private Integer stateId;         // FK -> CMSSTATE.STATEID
    private Long zoneId;             // FK -> CMSGENERALMASTER.GMID
    private Long tradeId;            // FK -> CMSGENERALMASTER.GMID
    private Long skillId;            // FK -> CMSGENERALMASTER.GMID

    private BigDecimal basic;        // DECIMAL(8,2)
    private BigDecimal da;           // DECIMAL(8,2)
    private BigDecimal hra;          // DECIMAL(8,2)
    private BigDecimal otherAllowance; // DECIMAL(8,2)

    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private LocalDateTime updateDTM; // Default GETDATE()
}
