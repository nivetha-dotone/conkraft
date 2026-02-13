package com.wfd.dot1.cwfm.dto;



import java.util.List;

public class CertificationAssignmentRequestDTO {
    private List<Assignment> assignments;

    public CertificationAssignmentRequestDTO() {
    }

    public List<Assignment> getAssignments() {
        return this.assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public static class Assignment {
        private ProficiencyLevel proficiencyLevel;
        private Certification certification;
        private String effectiveDate;
        private String expirationDate;

        public Assignment() {
        }

        public ProficiencyLevel getProficiencyLevel() {
            return this.proficiencyLevel;
        }

        public void setProficiencyLevel(ProficiencyLevel proficiencyLevel) {
            this.proficiencyLevel = proficiencyLevel;
        }

        public Certification getCertification() {
            return this.certification;
        }

        public void setCertification(Certification certification) {
            this.certification = certification;
        }

        public String getEffectiveDate() {
            return this.effectiveDate;
        }

        public void setEffectiveDate(String effectiveDate) {
            this.effectiveDate = effectiveDate;
        }

        public String getExpirationDate() {
            return this.expirationDate;
        }

        public void setExpirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
        }
    }

    public static class Certification {
        private String qualifier;

        public Certification() {
        }

        public String getQualifier() {
            return this.qualifier;
        }

        public void setQualifier(String qualifier) {
            this.qualifier = qualifier;
        }
    }

    public static class ProficiencyLevel {
        private String qualifier;

        public ProficiencyLevel() {
        }

        public String getQualifier() {
            return this.qualifier;
        }

        public void setQualifier(String qualifier) {
            this.qualifier = qualifier;
        }
    }
}
