package com.wfd.dot1.cwfm.dto;


import lombok.Data;

@Data
public class KTCWorkorderDTO {

    private String workOrderNumber;
    private String item;
    private String line;
    private String lineNumber;
    private String serviceCode;
    private String shortText;
    private String deliveryCompletion;
    private String itemChangedON;
    private String vendorCode;
    private String vendorName;
    private String vendorAddress;
    private String blockedVendor;
    private String workOrderValiditiyFrom;
    private String workOrderValiditiyTo;
    private String workOrderType;
    private String plantcode;
    private String sectionCode;
    private String departmentCode;
    private String GLCode;
    private String costCenter;
    private String natureofJob;
    private String rateUnit;
    private String quantity;
    private String baseUnitofMeasure;
    private String workOrderReleased;
    private String PMOrderNo;
    private String WBSElement;
    private String qtyCompleted;
    private String workOrderReleaseDate;
    private String serviceEntryCreatedDate;
    private String serviceEntryUpdatedDate;
    private String purchaseOrgLevel;
    private String companycode;
}