

package com.wfd.dot1.cwfm.dto;

public class WfdResponse {
    private boolean success;
    private String message;

    public WfdResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }
}
