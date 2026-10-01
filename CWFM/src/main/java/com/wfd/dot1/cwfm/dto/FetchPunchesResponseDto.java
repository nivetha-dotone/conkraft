package com.wfd.dot1.cwfm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FetchPunchesResponseDto {

    private Employee employee;

    private List<Punch> punches;
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Employee {

        private Long id;

        private String qualifier;

        private String name;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Punch {

        private Long id;

        private Employee employee;

        private String punchDtm;

        private String roundedPunchDtm;

        private String enteredOnDtm;

        private TypeOverride typeOverride;

        private List<ExceptionDto> exceptions;

        private List<CommentsNote> commentsNotes;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TypeOverride {

        private Long associatedRuleId;

        private String associatedRuleName;

        private String description;

        private Long id;

        private String qualifier;

        private Long typeOverrideId;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ExceptionDto {

        /*
         * Example:
         * 2026-09-21
         */
        private String applyDate;

        private ExceptionType exceptionType;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ExceptionType {

        private String category;

        private String description;

        private String displayName;

        private Long id;

        private String name;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CommentsNote {

        private Comment comment;

        private Long id;

        private List<Note> notes;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Comment {

        private Long id;

        private String name;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Note {

        private String dataSourceDisplayName;

        private Long dataSourceId;

        private String text;

        private String timestamp;
    }
}