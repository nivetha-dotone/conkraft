package com.wfd.dot1.cwfm.dto;

import lombok.Data;


@Data
public class FaceLogFetchDtoReg {
    private Integer punchId;
    private String personNum;
    private String punchType;
    private String location;
    private String punchDtm;
    private String activePunch;
}
