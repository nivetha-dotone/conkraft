package com.wfd.dot1.cwfm.dto;

import lombok.Data;

@Data
public class FaceLoginResponse {
    private boolean success;
    private String message;
    private String userId;
    private String encPassword;
    private String decryptedPassword;
    private String username;
    private String retainer;
    private String pubCode;

    public FaceLoginResponse() {}

    public FaceLoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public FaceLoginResponse(boolean success, String message, String userId, String encPassword,
                             String decryptedPassword, String username, String retainer, String pubCode) {
        this.success = success;
        this.message = message;
        this.userId = userId;
        this.encPassword = encPassword;
        this.decryptedPassword = decryptedPassword;
        this.username = username;
        this.retainer = retainer;
        this.pubCode = pubCode;
    }

}
