package com.lms.book_portal_frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private String storId;
    private String titleId;
    private LocalDate ordDate;
    private Short qty;
    private String payterms;
}
