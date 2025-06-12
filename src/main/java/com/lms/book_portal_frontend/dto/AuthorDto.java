package com.lms.book_portal_frontend.dto;

import lombok.Data;

@Data
public class AuthorDto {
    private String auId;
    private String auLname;
    private String auFname;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zip;
    private Integer contract;
}
