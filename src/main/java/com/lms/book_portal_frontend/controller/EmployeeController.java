package com.lms.book_portal_frontend.controller;


import com.lms.book_portal_frontend.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private RestTemplate restTemplate;

    private  String API_URL = "http://13.233.193.166:9091/api/employees"; // Change if using a real backend

    @GetMapping("/employee/chaitanya")
    public String showEmployees(Model model) {
        EmployeeDTO[] employee = restTemplate.getForObject(API_URL, EmployeeDTO[].class);
        List<EmployeeDTO> employees = Arrays.asList(employee);
        model.addAttribute("employees", employees);
        return "Employee"; // this must match your Thymeleaf file name: Employee.html
    }
}
