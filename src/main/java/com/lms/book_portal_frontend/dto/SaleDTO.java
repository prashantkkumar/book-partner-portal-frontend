package com.lms.book_portal_frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private Instant ordDate;
    private Short qty;
    private String payterms;
}
