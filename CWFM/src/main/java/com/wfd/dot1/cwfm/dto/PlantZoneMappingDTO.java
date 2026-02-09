package com.wfd.dot1.cwfm.dto;

import lombok.Data;

@Data
public class PlantZoneMappingDTO {

    private String refMppId;     // Primary key (RefMppId)

    private String unitIdS;      // Foreign key ID (UnitIdS)
    private String unitName;     // Human-readable name from CMSPRINCIPALEMPLOYER

    private String stateS;       // Foreign key ID (StateS)
    private String stateName;    // Human-readable name from CMSSTATE

    private String zoneS;        // Foreign key ID (ZoneS)
    private String zoneName;     // Human-readable name from CMSGENERALMASTER

    private String isActive;     // Status (1 = Active, 0 = Inactive)
    private String updateDTM;    // Last updated timestamp
}
