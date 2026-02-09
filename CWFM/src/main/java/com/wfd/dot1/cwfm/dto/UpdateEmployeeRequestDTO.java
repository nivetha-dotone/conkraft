package com.wfd.dot1.cwfm.dto;

import lombok.Data;

import java.util.List;
@Data
public class UpdateEmployeeRequestDTO {

    
    private UpdateEmployeeRequestDTO.PersonIdentity personIdentity;
    private UpdateEmployeeRequestDTO.PersonInformation personInformation;
    private UpdateEmployeeRequestDTO.JobAssignment jobAssignment;
    private UpdateEmployeeRequestDTO.User user;
    @Data
    public static class PersonInformation {
        private UpdateEmployeeRequestDTO.AccessAssignment accessAssignment;
        private List<UpdateEmployeeRequestDTO.EmailAddress> emailAddresses;
        private List<UpdateEmployeeRequestDTO.EmploymentStatus> employmentStatusList;
        private UpdateEmployeeRequestDTO.Person person;
        private List<UpdateEmployeeRequestDTO.CustomDataDTO> customDataList;
        private List<UpdateEmployeeRequestDTO.PersonAuthenticationType> personAuthenticationTypes;
        private List<UpdateEmployeeRequestDTO.PersonLicenseType> personLicenseTypes;
        private List<UpdateEmployeeRequestDTO.UserAccountStatus> userAccountStatusList;
    }
    @Data
    public static class PersonIdentity {
        private Integer personKey;

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
    public  static class  CustomDataDTO {
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
        private List<UpdateEmployeeRequestDTO.BaseWageRate> baseWageRates;
        private UpdateEmployeeRequestDTO.JobAssignmentDetails jobAssignmentDetails;
        private List<UpdateEmployeeRequestDTO.PrimaryLaborAccount> primaryLaborAccounts;
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
        private UpdateEmployeeRequestDTO.UserAccount userAccount;
    }

    @Data
    public static class UserAccount {
        private String logonProfileName;
        private String userName;
        private String userPassword;
    }
    
}
