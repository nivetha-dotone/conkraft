package com.wfd.dot1.cwfm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FetchScheduleShiftResponseDto {

    private Employee employee;

    private List<ScheduleShift> scheduleShifts;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Employee {

        private Long id;

        private String qualifier;

        private String name;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ScheduleShift {

        private Long id;

        private String startDateTime;

        private String endDateTime;

        private Employee employee;

        private List<Segment> segments;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Segment {

        private Long id;

        private SegmentTypeRef segmentTypeRef;

        private String startDateTime;

        private String endDateTime;

        private OrgJobRef orgJobRef;

        private WorkruleRef workruleRef;

        private WorkruleRef primaryWorkruleRef;

        private String type;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SegmentTypeRef {

        private Long id;

        private String qualifier;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrgJobRef {

        private Long id;

        private String qualifier;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WorkruleRef {

        private Long id;

        private String qualifier;

        private String guid;
    }
}
