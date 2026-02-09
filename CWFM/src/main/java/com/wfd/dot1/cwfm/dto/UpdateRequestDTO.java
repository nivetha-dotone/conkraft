package com.wfd.dot1.cwfm.dto;

import lombok.Data;

@Data
public class UpdateRequestDTO {

    private Boolean status;
    private String reMark;
    private String aORstatusBy;
    private String transactionId;

}