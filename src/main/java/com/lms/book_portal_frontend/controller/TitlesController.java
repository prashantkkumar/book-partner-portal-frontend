package com.lms.book_portal_frontend.controller;

import com.lms.book_portal_frontend.dto.TitleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/titles")
public class TitlesController {
    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://13.233.193.166:9091/api";

    @GetMapping("/prashant")
    public String getTitles(Model model) {
        TitleDTO[] response = restTemplate.getForObject("http://13.233.193.166:9091/api/titles", TitleDTO[].class);

        // Debug log to verify data fetched
        System.out.println("Fetched titles: " + Arrays.toString(response));

        model.addAttribute("titles", List.of(response));
        model.addAttribute("name", "Prashant");
        return "title-list"; // must match filename title-list.html
    }


}
