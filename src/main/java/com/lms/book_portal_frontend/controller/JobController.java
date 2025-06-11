package com.lms.book_portal_frontend.controller;


import com.lms.book_portal_frontend.dto.JobDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
public class JobController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/job/mohit")
    public String showJobs(Model model) {
        String apiUrl = "http://13.233.193.166:9091/api/jobs"; // Replace with actual backend URL
        JobDto[] jobs = restTemplate.getForObject(apiUrl, JobDto[].class);

        List<JobDto> jobList = Arrays.asList(jobs);
        model.addAttribute("jobs", jobList);
        return "job-list";



    }
}

