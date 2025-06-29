package com.lms.book_portal_frontend.controller;

import com.lms.book_portal_frontend.dto.TitleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/titles")
public class TitlesController {
    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8081/api";

    // Display title list
    @GetMapping
    public String getTitles(Model model) {
        TitleDTO[] response = restTemplate.getForObject(BASE_URL + "/titles", TitleDTO[].class);
        System.out.println("Fetched titles: " + Arrays.toString(response));
        model.addAttribute("titles", List.of(response));
        model.addAttribute("name", "Prashant");
        return "title-list";
    }

    // Display add title form
    @GetMapping("/add")
    public String showAddTitleForm(Model model) {
        model.addAttribute("title", new TitleDTO());
        return "add-titles";
    }

    // Handle add title form submission
    @PostMapping("/add")
    public String addTitle(@ModelAttribute("title") TitleDTO titleDTO) {
        restTemplate.postForObject(BASE_URL + "/titles", titleDTO, TitleDTO.class);
        return "redirect:/titles";
    }

    // Display edit title form
    @GetMapping("/edit/{titleId}")
    public String showEditTitleForm(@PathVariable String titleId, Model model) {
        TitleDTO titleDTO = restTemplate.getForObject(BASE_URL + "/titles/{titleId}", TitleDTO.class, titleId);
        if (titleDTO == null) {
            return "redirect:/titles"; // Handle not found
        }
        model.addAttribute("title", titleDTO);
        return "edit-title";
    }

    // Handle edit title form submission
    @PostMapping("/edit/{titleId}")
    public String updateTitle(@PathVariable String titleId, @ModelAttribute("title") TitleDTO titleDTO) {
        restTemplate.put(BASE_URL + "/titles/{titleId}", titleDTO, titleId);
        return "redirect:/titles";
    }
}