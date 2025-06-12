package com.lms.book_portal_frontend.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private String empId;
    private String fname;
    private String minit;        //  middle initial
    private String lname;
    private Integer jobLvl;
    private LocalDateTime hireDate;    //  ISO 8601 compatible
}
