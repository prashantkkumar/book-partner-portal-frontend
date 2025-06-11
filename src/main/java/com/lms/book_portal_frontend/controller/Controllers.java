package com.lms.book_portal_frontend.controller;

import com.lms.book_portal_frontend.dto.PublisherDTO;
import com.lms.book_portal_frontend.dto.PublisherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/publishers")
public class Controllers {

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




    @GetMapping("/darpan")
    public String getDarpan(Model model) {
        PublisherResponse response = restTemplate.getForObject(BASE_URL + "/authors", PublisherResponse.class);
        model.addAttribute("publishers", response);
        model.addAttribute("name", "Darpan");
        return "publisher-list";
    }

    @GetMapping("/prashant")
    public String getPrashant(Model model) {
        PublisherResponse response = restTemplate.getForObject(BASE_URL + "/prashant", PublisherResponse.class);
        model.addAttribute("publishers", response);
        model.addAttribute("name", "Prashant");
        return "publisher-list";
    }

    @GetMapping("/bhuvan")
    public String getBhuvan(Model model) {
        PublisherResponse response = restTemplate.getForObject(BASE_URL + "/bhuvan", PublisherResponse.class);
        model.addAttribute("publishers", response);
        model.addAttribute("name", "Bhuvan");
        return "publisher-list";
    }

    @GetMapping("/chaitanya")
    public String getChaitanya(Model model) {
        PublisherResponse response = restTemplate.getForObject(BASE_URL + "/chaitanya", PublisherResponse.class);
        model.addAttribute("publishers", response);
        model.addAttribute("name", "Chaitanya");
        return "publisher-list";
    }

    @GetMapping("/mohit")
    public String getMohit(Model model) {
        PublisherResponse response = restTemplate.getForObject(BASE_URL + "/mohit", PublisherResponse.class);
        model.addAttribute("publishers", response);
        model.addAttribute("name", "Mohit");
        return "publisher-list";
    }

    @GetMapping("/naman")
    public String getNaman(Model model) {
        PublisherResponse response = restTemplate.getForObject(BASE_URL + "/naman", PublisherResponse.class);
        model.addAttribute("publishers", response);
        model.addAttribute("name", "Naman");
        return "publisher-list";
    }
}
