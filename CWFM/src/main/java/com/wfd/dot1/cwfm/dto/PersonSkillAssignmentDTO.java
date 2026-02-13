

package com.wfd.dot1.cwfm.dto;

import java.util.List;

public class PersonSkillAssignmentDTO {
    private List<Assignment> assignments;

    public PersonSkillAssignmentDTO() {
    }

    public List<Assignment> getAssignments() {
        return this.assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public static class Assignment {
        private Skill skill;
        private ProficiencyLevel proficiencyLevel;
        private String effectiveDate;
        private boolean active;

        public Assignment() {
        }

        public Skill getSkill() {
            return this.skill;
        }

        public void setSkill(Skill skill) {
            this.skill = skill;
        }

        public ProficiencyLevel getProficiencyLevel() {
            return this.proficiencyLevel;
        }

        public void setProficiencyLevel(ProficiencyLevel proficiencyLevel) {
            this.proficiencyLevel = proficiencyLevel;
        }

        public String getEffectiveDate() {
            return this.effectiveDate;
        }

        public void setEffectiveDate(String effectiveDate) {
            this.effectiveDate = effectiveDate;
        }

        public boolean isActive() {
            return this.active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }

    public static class Skill {
        private String qualifier;

        public Skill() {
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
