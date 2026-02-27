package com.wfd.dot1.cwfm.dto;

import lombok.Data;

import java.util.Date;

@Data
public class WorkOrderDTOMail {

    private Long contractorId;
    private String code;
    private String contractor;
    private String unitCode;
    private String unitName;
    private String conEmail;
    private String hrEmail;
    private Long workOrderId;
    private String sapWorkOrderNum;
    private String validDt;



}
