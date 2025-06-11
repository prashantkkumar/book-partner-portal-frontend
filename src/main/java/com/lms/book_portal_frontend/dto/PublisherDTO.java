package com.lms.book_portal_frontend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDTO {
    private Long id;
    private String name;
    private String email;
}
