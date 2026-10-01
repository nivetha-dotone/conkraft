package com.wfd.dot1.cwfm.dto;


import lombok.Data;

import java.util.List;

@Data
public class FetchPunchesRequestDto {

    private Where where;

    private List<String> select;

    @Data
    public static class Where {

        private DateRange dateRange;

        private Employees employees;

        private HyperFind hyperFind;
    }

    @Data
    public static class DateRange {

        private String startDate;

        private String endDate;
    }

    @Data
    public static class Employees {

        private List<String> qualifiers;
    }

    @Data
    public static class HyperFind {

        private String qualifier;
    }
}