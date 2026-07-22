package com.wfd.dot1.cwfm.dto;


import java.io.Serializable;

public class GatepassDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer todayGatepasses;

    public Integer getTodayGatepasses() {
        return todayGatepasses;
    }

    public void setTodayGatepasses(Integer todayGatepasses) {
        this.todayGatepasses = todayGatepasses;
    }

}