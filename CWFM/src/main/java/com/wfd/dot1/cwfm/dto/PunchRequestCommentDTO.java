package com.wfd.dot1.cwfm.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PunchRequestCommentDTO {

    @JsonProperty("do")
    private DoDTO doObj;

    private WhereDTO where;

    /* ===================== INNER DTO CLASSES ===================== */

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DoDTO {
        private PunchesDTO punches;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PunchesDTO {
        private List<AddedPunchDTO> added;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AddedPunchDTO {

        private EmployeeDTO employee;

        private List<CommentsNoteDTO> commentsNotes;

        private String punchDtm;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CommentsNoteDTO {

        private CommentDTO comment;

        private List<NoteDTO> notes;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CommentDTO {

        private List<CategoryDTO> categories;

        private String name;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CategoryDTO {

        private String qualifier;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NoteDTO {

        private String text;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WhereDTO {

        private EmployeeDTO employee;

        private DateRangeDTO dateRange;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EmployeeDTO {

        private String qualifier;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DateRangeDTO {

        private String startDate;

        private String endDate;
    }
}