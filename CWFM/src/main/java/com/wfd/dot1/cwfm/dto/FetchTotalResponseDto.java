package com.wfd.dot1.cwfm.dto;


import lombok.Data;

@Data
public class FetchTotalResponseDto {

    private String requestId;
    private String message;

    public FetchTotalResponseDto(

            String requestId,
            String message) {

        this.requestId = requestId;
        this.message = message;
    }
}