package com.lms.book_portal_frontend.controller;

import com.lms.book_portal_frontend.dto.SaleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String API_BASE_URL = "http://13.233.193.166:9091/api/sales";

    @GetMapping("/bhuvan")
    public String getSales(Model model) {
        SaleDTO[] sales = restTemplate.getForObject(API_BASE_URL, SaleDTO[].class);
        model.addAttribute("sales", List.of(sales));
        model.addAttribute("name", "bhuvan");
        return "sales";
    }

    // Show add sale form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("sale", new SaleDTO());
        return "add-sale";
    }

    // Handle add sale POST
    @PostMapping()
    public String addSale(@ModelAttribute("sale") SaleDTO saleDTO) {
        restTemplate.postForObject(API_BASE_URL, saleDTO, SaleDTO.class);
        return "redirect:/sales/bhuvan";
    }

    // Show edit sale form
    @GetMapping("/edit")
    public String showEditForm(@RequestParam String storId,
                               @RequestParam String titleId,
                               @RequestParam String ordDate,
                               Model model) {

        String url = API_BASE_URL + "/edit?storId={storId}&titleId={titleId}&ordDate={ordDate}";
        SaleDTO sale = restTemplate.getForObject(url, SaleDTO.class, storId, titleId, ordDate);
        model.addAttribute("sale", sale);
        return "edit-sale";
    }

    // Handle PUT update
    @PostMapping("/edit")
    public String updateSale(@ModelAttribute("sale") SaleDTO saleDTO) {
        restTemplate.put(API_BASE_URL, saleDTO);
        return "redirect:/sales/bhuvan";
    }
}