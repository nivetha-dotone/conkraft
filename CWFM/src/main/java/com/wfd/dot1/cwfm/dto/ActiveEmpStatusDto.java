//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.wfd.dot1.cwfm.dto;

import java.util.List;

public class ActiveEmpStatusDto {
    private PersonInformation personInformation;

    public ActiveEmpStatusDto() {
    }

    public PersonInformation getPersonInformation() {
        return this.personInformation;
    }

    public void setPersonInformation(final PersonInformation personInformation) {
        this.personInformation = personInformation;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ActiveEmpStatusDto)) {
            return false;
        } else {
            ActiveEmpStatusDto other = (ActiveEmpStatusDto)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$personInformation = this.getPersonInformation();
                Object other$personInformation = other.getPersonInformation();
                if (this$personInformation == null) {
                    if (other$personInformation != null) {
                        return false;
                    }
                } else if (!this$personInformation.equals(other$personInformation)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ActiveEmpStatusDto;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $personInformation = this.getPersonInformation();
        result = result * 59 + ($personInformation == null ? 43 : $personInformation.hashCode());
        return result;
    }

    public String toString() {
        return "ActiveEmpStatusDto(personInformation=" + String.valueOf(this.getPersonInformation()) + ")";
    }

    public static class PersonInformation {
        private List<CustomData> customDataList;
        private List<EmploymentStatus> employmentStatusList;
        private List<PersonLicenseType> personLicenseTypes;
        private Person person;

        public PersonInformation() {
        }

        public List<CustomData> getCustomDataList() {
            return this.customDataList;
        }

        public List<EmploymentStatus> getEmploymentStatusList() {
            return this.employmentStatusList;
        }

        public List<PersonLicenseType> getPersonLicenseTypes() {
            return this.personLicenseTypes;
        }

        public Person getPerson() {
            return this.person;
        }

        public void setCustomDataList(final List<CustomData> customDataList) {
            this.customDataList = customDataList;
        }

        public void setEmploymentStatusList(final List<EmploymentStatus> employmentStatusList) {
            this.employmentStatusList = employmentStatusList;
        }

        public void setPersonLicenseTypes(final List<PersonLicenseType> personLicenseTypes) {
            this.personLicenseTypes = personLicenseTypes;
        }

        public void setPerson(final Person person) {
            this.person = person;
        }

        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof PersonInformation)) {
                return false;
            } else {
                PersonInformation other = (PersonInformation)o;
                if (!other.canEqual(this)) {
                    return false;
                } else {
                    Object this$customDataList = this.getCustomDataList();
                    Object other$customDataList = other.getCustomDataList();
                    if (this$customDataList == null) {
                        if (other$customDataList != null) {
                            return false;
                        }
                    } else if (!this$customDataList.equals(other$customDataList)) {
                        return false;
                    }

                    Object this$employmentStatusList = this.getEmploymentStatusList();
                    Object other$employmentStatusList = other.getEmploymentStatusList();
                    if (this$employmentStatusList == null) {
                        if (other$employmentStatusList != null) {
                            return false;
                        }
                    } else if (!this$employmentStatusList.equals(other$employmentStatusList)) {
                        return false;
                    }

                    Object this$personLicenseTypes = this.getPersonLicenseTypes();
                    Object other$personLicenseTypes = other.getPersonLicenseTypes();
                    if (this$personLicenseTypes == null) {
                        if (other$personLicenseTypes != null) {
                            return false;
                        }
                    } else if (!this$personLicenseTypes.equals(other$personLicenseTypes)) {
                        return false;
                    }

                    Object this$person = this.getPerson();
                    Object other$person = other.getPerson();
                    if (this$person == null) {
                        if (other$person != null) {
                            return false;
                        }
                    } else if (!this$person.equals(other$person)) {
                        return false;
                    }

                    return true;
                }
            }
        }

        protected boolean canEqual(final Object other) {
            return other instanceof PersonInformation;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            Object $customDataList = this.getCustomDataList();
            result = result * 59 + ($customDataList == null ? 43 : $customDataList.hashCode());
            Object $employmentStatusList = this.getEmploymentStatusList();
            result = result * 59 + ($employmentStatusList == null ? 43 : $employmentStatusList.hashCode());
            Object $personLicenseTypes = this.getPersonLicenseTypes();
            result = result * 59 + ($personLicenseTypes == null ? 43 : $personLicenseTypes.hashCode());
            Object $person = this.getPerson();
            result = result * 59 + ($person == null ? 43 : $person.hashCode());
            return result;
        }

        public String toString() {
            String var10000 = String.valueOf(this.getCustomDataList());
            return "ActiveEmpStatusDto.PersonInformation(customDataList=" + var10000 + ", employmentStatusList=" + String.valueOf(this.getEmploymentStatusList()) + ", personLicenseTypes=" + String.valueOf(this.getPersonLicenseTypes()) + ", person=" + String.valueOf(this.getPerson()) + ")";
        }
    }

    public static class CustomData {
        private String customDataTypeName;
        private String text;

        public CustomData() {
        }

        public String getCustomDataTypeName() {
            return this.customDataTypeName;
        }

        public String getText() {
            return this.text;
        }

        public void setCustomDataTypeName(final String customDataTypeName) {
            this.customDataTypeName = customDataTypeName;
        }

        public void setText(final String text) {
            this.text = text;
        }

        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof CustomData)) {
                return false;
            } else {
                CustomData other = (CustomData)o;
                if (!other.canEqual(this)) {
                    return false;
                } else {
                    Object this$customDataTypeName = this.getCustomDataTypeName();
                    Object other$customDataTypeName = other.getCustomDataTypeName();
                    if (this$customDataTypeName == null) {
                        if (other$customDataTypeName != null) {
                            return false;
                        }
                    } else if (!this$customDataTypeName.equals(other$customDataTypeName)) {
                        return false;
                    }

                    Object this$text = this.getText();
                    Object other$text = other.getText();
                    if (this$text == null) {
                        if (other$text != null) {
                            return false;
                        }
                    } else if (!this$text.equals(other$text)) {
                        return false;
                    }

                    return true;
                }
            }
        }

        protected boolean canEqual(final Object other) {
            return other instanceof CustomData;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            Object $customDataTypeName = this.getCustomDataTypeName();
            result = result * 59 + ($customDataTypeName == null ? 43 : $customDataTypeName.hashCode());
            Object $text = this.getText();
            result = result * 59 + ($text == null ? 43 : $text.hashCode());
            return result;
        }

        public String toString() {
            String var10000 = this.getCustomDataTypeName();
            return "ActiveEmpStatusDto.CustomData(customDataTypeName=" + var10000 + ", text=" + this.getText() + ")";
        }
    }

    public static class Person {
        private String personNumber;

        public Person() {
        }

        public String getPersonNumber() {
            return this.personNumber;
        }

        public void setPersonNumber(final String personNumber) {
            this.personNumber = personNumber;
        }

        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof Person)) {
                return false;
            } else {
                Person other = (Person)o;
                if (!other.canEqual(this)) {
                    return false;
                } else {
                    Object this$personNumber = this.getPersonNumber();
                    Object other$personNumber = other.getPersonNumber();
                    if (this$personNumber == null) {
                        if (other$personNumber != null) {
                            return false;
                        }
                    } else if (!this$personNumber.equals(other$personNumber)) {
                        return false;
                    }

                    return true;
                }
            }
        }

        protected boolean canEqual(final Object other) {
            return other instanceof Person;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            Object $personNumber = this.getPersonNumber();
            result = result * 59 + ($personNumber == null ? 43 : $personNumber.hashCode());
            return result;
        }

        public String toString() {
            return "ActiveEmpStatusDto.Person(personNumber=" + this.getPersonNumber() + ")";
        }
    }

    public static class EmploymentStatus {
        private String employmentStatusName;
        private String effectiveDate;

        public EmploymentStatus() {
        }

        public String getEmploymentStatusName() {
            return this.employmentStatusName;
        }

        public String getEffectiveDate() {
            return this.effectiveDate;
        }

        public void setEmploymentStatusName(final String employmentStatusName) {
            this.employmentStatusName = employmentStatusName;
        }

        public void setEffectiveDate(final String effectiveDate) {
            this.effectiveDate = effectiveDate;
        }

        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof EmploymentStatus)) {
                return false;
            } else {
                EmploymentStatus other = (EmploymentStatus)o;
                if (!other.canEqual(this)) {
                    return false;
                } else {
                    Object this$employmentStatusName = this.getEmploymentStatusName();
                    Object other$employmentStatusName = other.getEmploymentStatusName();
                    if (this$employmentStatusName == null) {
                        if (other$employmentStatusName != null) {
                            return false;
                        }
                    } else if (!this$employmentStatusName.equals(other$employmentStatusName)) {
                        return false;
                    }

                    Object this$effectiveDate = this.getEffectiveDate();
                    Object other$effectiveDate = other.getEffectiveDate();
                    if (this$effectiveDate == null) {
                        if (other$effectiveDate != null) {
                            return false;
                        }
                    } else if (!this$effectiveDate.equals(other$effectiveDate)) {
                        return false;
                    }

                    return true;
                }
            }
        }

        protected boolean canEqual(final Object other) {
            return other instanceof EmploymentStatus;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            Object $employmentStatusName = this.getEmploymentStatusName();
            result = result * 59 + ($employmentStatusName == null ? 43 : $employmentStatusName.hashCode());
            Object $effectiveDate = this.getEffectiveDate();
            result = result * 59 + ($effectiveDate == null ? 43 : $effectiveDate.hashCode());
            return result;
        }

        public String toString() {
            String var10000 = this.getEmploymentStatusName();
            return "ActiveEmpStatusDto.EmploymentStatus(employmentStatusName=" + var10000 + ", effectiveDate=" + this.getEffectiveDate() + ")";
        }
    }

    public static class PersonLicenseType {
        private Boolean activeFlag;
        private String licenseTypeName;

        public PersonLicenseType() {
        }

        public Boolean getActiveFlag() {
            return this.activeFlag;
        }

        public String getLicenseTypeName() {
            return this.licenseTypeName;
        }

        public void setActiveFlag(final Boolean activeFlag) {
            this.activeFlag = activeFlag;
        }

        public void setLicenseTypeName(final String licenseTypeName) {
            this.licenseTypeName = licenseTypeName;
        }

        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof PersonLicenseType)) {
                return false;
            } else {
                PersonLicenseType other = (PersonLicenseType)o;
                if (!other.canEqual(this)) {
                    return false;
                } else {
                    Object this$activeFlag = this.getActiveFlag();
                    Object other$activeFlag = other.getActiveFlag();
                    if (this$activeFlag == null) {
                        if (other$activeFlag != null) {
                            return false;
                        }
                    } else if (!this$activeFlag.equals(other$activeFlag)) {
                        return false;
                    }

                    Object this$licenseTypeName = this.getLicenseTypeName();
                    Object other$licenseTypeName = other.getLicenseTypeName();
                    if (this$licenseTypeName == null) {
                        if (other$licenseTypeName != null) {
                            return false;
                        }
                    } else if (!this$licenseTypeName.equals(other$licenseTypeName)) {
                        return false;
                    }

                    return true;
                }
            }
        }

        protected boolean canEqual(final Object other) {
            return other instanceof PersonLicenseType;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            Object $activeFlag = this.getActiveFlag();
            result = result * 59 + ($activeFlag == null ? 43 : $activeFlag.hashCode());
            Object $licenseTypeName = this.getLicenseTypeName();
            result = result * 59 + ($licenseTypeName == null ? 43 : $licenseTypeName.hashCode());
            return result;
        }

        public String toString() {
            Boolean var10000 = this.getActiveFlag();
            return "ActiveEmpStatusDto.PersonLicenseType(activeFlag=" + var10000 + ", licenseTypeName=" + this.getLicenseTypeName() + ")";
        }
    }

    public static class UserAccountStatus {
        private String userAccountStatusName;

        public UserAccountStatus() {
        }

        public String getUserAccountStatusName() {
            return this.userAccountStatusName;
        }

        public void setUserAccountStatusName(final String userAccountStatusName) {
            this.userAccountStatusName = userAccountStatusName;
        }

        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof UserAccountStatus)) {
                return false;
            } else {
                UserAccountStatus other = (UserAccountStatus)o;
                if (!other.canEqual(this)) {
                    return false;
                } else {
                    Object this$userAccountStatusName = this.getUserAccountStatusName();
                    Object other$userAccountStatusName = other.getUserAccountStatusName();
                    if (this$userAccountStatusName == null) {
                        if (other$userAccountStatusName != null) {
                            return false;
                        }
                    } else if (!this$userAccountStatusName.equals(other$userAccountStatusName)) {
                        return false;
                    }

                    return true;
                }
            }
        }

        protected boolean canEqual(final Object other) {
            return other instanceof UserAccountStatus;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            Object $userAccountStatusName = this.getUserAccountStatusName();
            result = result * 59 + ($userAccountStatusName == null ? 43 : $userAccountStatusName.hashCode());
            return result;
        }

        public String toString() {
            return "ActiveEmpStatusDto.UserAccountStatus(userAccountStatusName=" + this.getUserAccountStatusName() + ")";
        }
    }
}
