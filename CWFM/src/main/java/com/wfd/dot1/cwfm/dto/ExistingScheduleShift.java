package com.wfd.dot1.cwfm.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public  class ExistingScheduleShift {

    private Integer scheduleShiftDbId;

    private String personNumber;

    private Timestamp startDateTimeShift;

    private Timestamp endDateTimeShift;
}