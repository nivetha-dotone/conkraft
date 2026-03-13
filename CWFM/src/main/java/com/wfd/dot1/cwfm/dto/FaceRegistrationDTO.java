package com.wfd.dot1.cwfm.dto;

import lombok.Data;

@Data
public class FaceRegistrationDTO {

    private String userId;
    private String username;
    private String unitID;
    private Double latitude;
    private Double longitude;
    private String base64Image; // UI captured image
    private String updatedBy;

}
