package com.wfd.dot1.cwfm.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StageMinimumWageDTO {

    private Long refIdS;             // RefIdS

    private Integer stateIdS;        // FK -> CMSSTATE.STATEID
    private String stateName;

    private Long zoneIdS;            // FK -> CMSGENERALMASTER.GMID
    private String zoneName;

    private Long tradeIdS;           // FK -> CMSGENERALMASTER.GMID
    private String tradeName;

    private Long skillIdS;           // FK -> CMSGENERALMASTER.GMID
    private String skillName;

    private BigDecimal basicS;       // DECIMAL(8,2)
    private BigDecimal daS;          // DECIMAL(8,2)
    private BigDecimal hraS;         // DECIMAL(8,2)
    private BigDecimal otherAllowanceS; // DECIMAL(8,2)

    private String fromDates;
    private String updateDTM; // Default GETDATE()
}
