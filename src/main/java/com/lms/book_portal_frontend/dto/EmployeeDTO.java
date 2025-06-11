package com.lms.book_portal_frontend.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private String empId;
    private String fname;
    private String minit;
    private String lname;
    private Integer jobLvl;
    //    private String jobId;
//    private String pubId;
    private Instant hireDate;
}
