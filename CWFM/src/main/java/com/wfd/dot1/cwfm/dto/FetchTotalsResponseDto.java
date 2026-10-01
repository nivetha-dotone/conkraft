package com.wfd.dot1.cwfm.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class FetchTotalsResponseDto {

    private Employee employee;
    private List<Total> totals;

    @Data
    public static class Employee {
        private Long id;
        private String qualifier;
        private String name;
    }

    @Data
    public static class Total {

        private EmployeeContext employeeContext;
        private TotalContext totalContext;
        private List<AggregatedTotal> aggregatedTotals;
    }

    @Data
    public static class EmployeeContext {

        private Employee employee;
        private Timezone timezone;
    }

    @Data
    public static class Timezone {

        private Long id;
        private String qualifier;
        private String name;
    }

    @Data
    public static class TotalContext {

        private String totalType;
        private String totalAggregationType;
        private String totalGroupByType;
    }

    @Data
    public static class AggregatedTotal {

        private Boolean approvableByManager;

        private Employee employee;

        private Location location;

        private Job job;

        private PayCode payCode;

        private String amountType;

        private BigDecimal amount;

        private BigDecimal wages;

        private WagesCurrency wagesCurrency;

        private Boolean jobTransfer;

        private Boolean laborCategoryTransfer;

        private Map<String, Object> laborCategory;

        private Map<String, Object> timeItemType;
    }

    @Data
    public static class Location {

        private Long id;
        private String qualifier;
        private String name;
    }

    @Data
    public static class Job {

        private Long id;
        private String qualifier;
        private String name;
    }

    @Data
    public static class PayCode {

        private Long id;
        private String qualifier;
        private String name;
    }

    @Data
    public static class WagesCurrency {

        private BigDecimal amount;
        private String currencyCode;
    }
}