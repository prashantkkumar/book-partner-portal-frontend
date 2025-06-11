package com.lms.book_portal_frontend.controller;

import com.lms.book_portal_frontend.dto.SaleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL = "http://13.233.193.166:9091/api/sales";

    @GetMapping("/bhuvan")
    public String getSales(Model model) {
        SaleDTO[] sales = restTemplate.getForObject(API_URL, SaleDTO[].class);
        model.addAttribute("sales", List.of(sales));
        model.addAttribute("name", "bhuvan");
        return "sales";
    }

}
