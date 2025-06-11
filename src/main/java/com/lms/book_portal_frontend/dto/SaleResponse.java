package com.lms.book_portal_frontend.dto;

import lombok.Data;

import java.util.List;

@Data
public class SaleResponse {
    private List<SaleDTO> content;
}
