package com.wfd.dot1.cwfm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FetchTotalsResponseDto {

    private Employee employee;
    private List<Total> totals;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Employee {

        private Long id;
        private String qualifier;
        private String name;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Total {

        private List<AggregatedTotal> aggregatedTotals;

        private EmployeeContext employeeContext;

        private TotalContext totalContext;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EmployeeContext {

        private Employee employee;
        private Timezone timezone;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Timezone {

        private Long id;
        private String qualifier;
        private String name;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TotalContext {

        private String totalAggregationType;
        private String totalGroupByType;
        private String totalType;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
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
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Location {

        private Long id;
        private String qualifier;
        private String name;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Job {

        private Long id;
        private String qualifier;
        private String name;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PayCode {

        private Long id;
        private String qualifier;
        private String name;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WagesCurrency {

        private BigDecimal amount;
        private String currencyCode;
    }
}