package com.lms.book_portal_frontend.controller;



//import org.example.backend.dto.StoreDto;
import com.lms.book_portal_frontend.dto.StoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class StoreController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/store/naman")
    public String showStores(Model model) {
        String apiUrl = "http://13.233.193.166:9091/api/stores";

        // Fetch list of StoreDto using ParameterizedTypeReference
        List<StoreDto> stores = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StoreDto>>() {}
        ).getBody();

        // Print to console
        if (stores != null) {
            for (StoreDto store : stores) {
                System.out.println("Store ID: " + store.getStorId());
                System.out.println("Name: " + store.getStorName());
                System.out.println("Address: " + store.getStorAddress());
                System.out.println("City: " + store.getCity());
                System.out.println("State: " + store.getState());
                System.out.println("Zip: " + store.getZip());
                System.out.println("--------");
            }
        }

        // Pass to Thymeleaf view
        model.addAttribute("stores", stores);
        return "store-view";
    }
}

