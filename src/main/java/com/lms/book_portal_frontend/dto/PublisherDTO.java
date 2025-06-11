package com.lms.book_portal_frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDTO {
    private String pubId;
    private String pubName;
    private String city;
    private String state;
    private String country;
}
