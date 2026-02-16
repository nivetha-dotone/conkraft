package com.wfd.dot1.cwfm.dto;

import lombok.Data;
import java.util.List;

@Data
public class ActiveEmpStatusDto {

    private PersonInformation personInformation;

    @Data
    public static class PersonInformation {

        private List<CustomData> customDataList;
        private List<EmploymentStatus> employmentStatusList;
        private List<PersonLicenseType> personLicenseTypes;


    }


    @Data
    public static class CustomData {
        private String customDataTypeName;
        private String text;
    }
    @Data
    public static class EmploymentStatus {
        private String employmentStatusName;
        private String effectiveDate;
    }

    @Data
    public static class PersonLicenseType {
        private Boolean activeFlag;
        private String licenseTypeName;
    }

    @Data
    public static class Person {
        private String personNumber;
    }

    @Data
    public static class UserAccountStatus {
        private String userAccountStatusName;
    }
}
