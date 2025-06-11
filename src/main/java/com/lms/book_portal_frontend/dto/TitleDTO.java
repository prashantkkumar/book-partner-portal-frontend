package com.lms.book_portal_frontend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TitleDTO {
    private String titleId;
    private String title;
    private String type;
    private double price;
    private double advance;
    private int royalty;
    private int ytdSales;
    private String notes;
    private LocalDate pubdate;
}

