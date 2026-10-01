package com.wfd.dot1.cwfm.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public  class ExistingPunch {

    private Integer punchDbId;

    private Timestamp enteredOnDtm;
}