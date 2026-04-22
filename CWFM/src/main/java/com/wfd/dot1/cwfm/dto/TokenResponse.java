package com.wfd.dot1.cwfm.dto;



public class TokenResponse {
    private String token;
    private long expiresIn;

    public TokenResponse(String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public String getToken() { return token; }
    public long getExpiresIn() { return expiresIn; }


}
