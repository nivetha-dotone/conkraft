package com.wfd.dot1.cwfm.dto;

import java.util.List;
import lombok.Data;

@Data
public class EmployeeRequestDTO {

    private PersonInformation personInformation;
    private JobAssignment jobAssignment;
    private User user;

    @Data
    public static class PersonInformation {
        private AccessAssignment accessAssignment;
        private List<EmailAddress> emailAddresses;
        private List<EmploymentStatus> employmentStatusList;
        private Person person;
        private List<CustomDataDTO> customDataList;
        private List<PersonAuthenticationType> personAuthenticationTypes;
        private List<PersonLicenseType> personLicenseTypes;
        private List<UserAccountStatus> userAccountStatusList;
    }

    @Data
    public static class AccessAssignment {
        private String accessProfileName;
        private String preferenceProfileName;
        private String professionalPayCodeName;
        private String professionalWorkRuleName;
        private String shiftCodeName;
    }

    @Data
    public static class EmailAddress {
        private String address;
        private String contactTypeName;
        private boolean hasEmailNotificationDelivery;
    }

    @Data
    public static class EmploymentStatus {
        private String effectiveDate;
        private String employmentStatusName;
        private String expirationDate;
    }

    @Data
    public static class Person {
        private String birthDate;
        private String firstName;
        private String lastName;
        private String fullName;
        private String hireDate;
        private String personNumber;
        private String shortName;
    }

    @Data
    public static class CustomDataDTO {
        private String customDataTypeName;
        private String text;
    }

    @Data
    public static class PersonAuthenticationType {
        private boolean activeFlag;
        private String authenticationTypeName;
    }

    @Data
    public static class PersonLicenseType {
        private boolean activeFlag;
        private String licenseTypeName;
    }

    @Data
    public static class UserAccountStatus {
        private String effectiveDate;
        private String expirationDate;
        private String userAccountStatusName;
    }

    @Data
    public static class JobAssignment {
        private List<BaseWageRate> baseWageRates;
        private JobAssignmentDetails jobAssignmentDetails;
        private List<PrimaryLaborAccount> primaryLaborAccounts;
    }

    @Data
    public static class BaseWageRate {
        private String effectiveDate;
        private String expirationDate;
        private double hourlyRate;
    }

    @Data
    public static class JobAssignmentDetails {
        private String payRuleName;
        private String supervisorName;
        private String supervisorPersonNumber;
        private String timeZoneName;
    }

    @Data
    public static class PrimaryLaborAccount {
        private String effectiveDate;
        private String expirationDate;
        private String organizationPath;
    }

    @Data
    public static class User {
        private UserAccount userAccount;
    }

    @Data
    public static class UserAccount {
        private String logonProfileName;
        private String userName;
        private String userPassword;
    }
}
