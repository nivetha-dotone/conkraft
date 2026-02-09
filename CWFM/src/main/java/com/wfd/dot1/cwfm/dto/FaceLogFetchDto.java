package com.wfd.dot1.cwfm.dto;

import lombok.Data;


@Data
public class FaceLogFetchDto {

    private String personNum;
    private String username;   // renamed from UserAcco
    private String imagePath;
    private String fileName;
    private double faceScore;
    private String deviceId;
    private String location;
    private String ipAddress;
    private String punchDtm;
}
