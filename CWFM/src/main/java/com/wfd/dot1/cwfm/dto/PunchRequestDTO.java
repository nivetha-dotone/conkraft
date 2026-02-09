package com.wfd.dot1.cwfm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PunchRequestDTO {

    @JsonProperty("do")
    private DoDTO doObj;

    private WhereDTO where;

    /* ===================== GETTERS & SETTERS ===================== */

    public DoDTO getDoObj() {
        return doObj;
    }

    public void setDoObj(DoDTO doObj) {
        this.doObj = doObj;
    }

    public WhereDTO getWhere() {
        return where;
    }

    public void setWhere(WhereDTO where) {
        this.where = where;
    }

    /* ===================== INNER DTO CLASSES ===================== */

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DoDTO {

        private PunchesDTO punches;

        public PunchesDTO getPunches() {
            return punches;
        }

        public void setPunches(PunchesDTO punches) {
            this.punches = punches;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PunchesDTO {

        private List<AddedPunchDTO> added;

        public List<AddedPunchDTO> getAdded() {
            return added;
        }

        public void setAdded(List<AddedPunchDTO> added) {
            this.added = added;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AddedPunchDTO {

        private EmployeeDTO employee;
        private String punchDtm; // ISO_LOCAL_DATE_TIME

        public EmployeeDTO getEmployee() {
            return employee;
        }

        public void setEmployee(EmployeeDTO employee) {
            this.employee = employee;
        }

        public String getPunchDtm() {
            return punchDtm;
        }

        public void setPunchDtm(String punchDtm) {
            this.punchDtm = punchDtm;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WhereDTO {

        private EmployeeDTO employee;
        private DateRangeDTO dateRange;

        public EmployeeDTO getEmployee() {
            return employee;
        }

        public void setEmployee(EmployeeDTO employee) {
            this.employee = employee;
        }

        public DateRangeDTO getDateRange() {
            return dateRange;
        }

        public void setDateRange(DateRangeDTO dateRange) {
            this.dateRange = dateRange;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EmployeeDTO {

        private String qualifier;

        public String getQualifier() {
            return qualifier;
        }

        public void setQualifier(String qualifier) {
            this.qualifier = qualifier;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DateRangeDTO {

        private String startDate;
        private String endDate;

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }
    }
}
