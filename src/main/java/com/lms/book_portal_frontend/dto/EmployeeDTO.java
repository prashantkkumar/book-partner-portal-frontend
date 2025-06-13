package com.lms.book_portal_frontend.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class EmployeeDTO {

    private String empId;        // e.g., "A-C71970F"
    private String fname;        // First name
    private Character minit;     // Middle initial
    private String lname;        // Last name
    private Integer jobLvl;      // Job level (e.g., 3)
    private String hireDate;    // Hire date and time
    private Short jobId;         // Job ID (e.g., 10)
    private String pubId;        // Publisher ID (e.g., "P001")
}
