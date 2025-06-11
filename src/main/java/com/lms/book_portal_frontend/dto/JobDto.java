package com.lms.book_portal_frontend.dto;

import lombok.Data;

@Data
public class JobDto {
    private Short id;
    private String jobDesc;
    private Integer minLvl;
    private Integer maxLvl;
}

