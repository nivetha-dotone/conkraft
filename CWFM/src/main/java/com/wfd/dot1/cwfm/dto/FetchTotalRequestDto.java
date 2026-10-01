package com.wfd.dot1.cwfm.dto;

import lombok.Data;

import java.util.List;

@Data
public class FetchTotalRequestDto {

    private List<String> personNumbers;

    private String startDate;

    private String endDate;

    private String reqId;
}
