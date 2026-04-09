package com.wfd.dot1.cwfm.dto;

import lombok.Data;

@Data
public class PostLaborCatDTO {


    private LaborCategory laborCategory;
    private String name;
    private Boolean inactive;

    @Data
    public static class LaborCategory {
        private String qualifier;
    }


}
