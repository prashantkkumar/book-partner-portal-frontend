package com.lms.book_portal_frontend.controller;

import com.lms.book_portal_frontend.dto.SaleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Controller
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL = "http://13.233.193.166:9091/api/sales";

    @GetMapping("/{name}")
    public String getSales(@PathVariable String name, Model model) {
        SaleDTO[] sales = restTemplate.getForObject(API_URL, SaleDTO[].class);
        model.addAttribute("sales", Arrays.asList(sales));
        model.addAttribute("name", name);
        return "sales";
    }
}
