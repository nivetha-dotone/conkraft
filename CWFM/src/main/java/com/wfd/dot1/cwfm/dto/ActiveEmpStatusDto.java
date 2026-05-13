package com.wfd.dot1.cwfm.dto;

import lombok.Data;
import java.util.List;

@Data
public class ActiveEmpStatusDto {

    private PersonInformation personInformation;

    // ================= PersonInformation =================

    @Data
    public static class PersonInformation {

        private List<CustomData> customDataList;
        private List<EmploymentStatus> employmentStatusList;
        private List<PersonLicenseType> personLicenseTypes;
        private Person person;
        private List<UserAccountStatus> userAccountStatusList;
    }

    // ================= CustomData =================

    @Data
    public static class CustomData {

        private String customDataTypeName;
        private String text;
    }

    // ================= Person =================

    @Data
    public static class Person {

        private String personNumber;
    }

    // ================= EmploymentStatus =================

    @Data
    public static class EmploymentStatus {

        private String employmentStatusName;
        private String effectiveDate;
    }

    // ================= PersonLicenseType =================

    @Data
    public static class PersonLicenseType {

        private Boolean activeFlag;
        private String licenseTypeName;
    }

    // ================= UserAccountStatus =================

    @Data
    public static class UserAccountStatus {

        private String userAccountStatusName;
    }
}