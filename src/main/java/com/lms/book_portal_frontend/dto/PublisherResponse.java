package com.lms.book_portal_frontend.dto;


import lombok.Data;
import java.util.List;

@Data
public class PublisherResponse {
    private List<PublisherDTO> content;
    private int number;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
}
