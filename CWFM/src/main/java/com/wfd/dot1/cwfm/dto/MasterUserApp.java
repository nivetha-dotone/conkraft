package com.wfd.dot1.cwfm.dto;

import lombok.Data;

@Data
public class MasterUserApp {
private  String status;
private String message;
private Dat data;

@Data
    public static class Dat {

        private int totalSubcontractor;
        private int totalDevice;
        private int totalUser;
        private int totalJobsite;

    }
}
