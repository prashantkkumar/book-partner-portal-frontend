package com.lms.book_portal_frontend.controller;

import com.lms.book_portal_frontend.dto.SaleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.List;

@Controller
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String API_BASE_URL = "http://localhost:8081/api/sales";


    @GetMapping("/bhuvan")
    public String getSales(Model model) {
        SaleDTO[] salesArray = restTemplate.getForObject(API_BASE_URL, SaleDTO[].class);
        List<SaleDTO> sortedSales = List.of(salesArray).stream()
                .sorted(Comparator.comparing(SaleDTO::getOrdDate))
                .collect(Collectors.toList());

        model.addAttribute("sales", sortedSales);
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