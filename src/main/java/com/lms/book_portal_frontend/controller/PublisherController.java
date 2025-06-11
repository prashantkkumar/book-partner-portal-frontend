package com.lms.book_portal_frontend.controller;

import com.lms.book_portal_frontend.dto.PublisherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://13.233.193.166:9091/api";

    @GetMapping("/ayush")
    public String getAyush(Model model) {
        try {
            PublisherDTO[] response = restTemplate.getForObject(BASE_URL + "/publishers", PublisherDTO[].class);
            model.addAttribute("publishers", List.of(response)); // Convert array to List
        } catch (Exception e) {
            model.addAttribute("error", "Error fetching data: " + e.getMessage());
            e.printStackTrace();
            return "error";  // if you have an error.html
        }
        model.addAttribute("name", "Ayush");
        return "publisher-list";  // Thymeleaf will look for publisher-list.html
    }

}
