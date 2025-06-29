package com.lms.book_portal_frontend.controller;



//import org.example.backend.dto.StoreDto;
import com.lms.book_portal_frontend.dto.StoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class StoreController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/store/naman")
    public String showStores(Model model) {
        String apiUrl = "http://localhost:8081/api/stores";

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
    @GetMapping("/store/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        String apiUrl = "http://13.233.193.166:9091/api/stores/" + id;
        StoreDto store = restTemplate.getForObject(apiUrl, StoreDto.class);
        model.addAttribute("store", store);
        return "edit-store";
    }

    @PostMapping("/store/update")
    public String updateStore(@ModelAttribute StoreDto storeDto) {
        String apiUrl = "http://13.233.193.166:9091/api/stores/" + storeDto.getStorId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StoreDto> requestEntity = new HttpEntity<>(storeDto, headers);

        restTemplate.exchange(apiUrl, HttpMethod.PUT, requestEntity, Void.class);

        return "redirect:/store/naman"; // redirect to store list
    }

    @GetMapping("/store/add")
    public String showAddForm(Model model) {
        model.addAttribute("store", new StoreDto()); // empty form
        return "add-store";
    }

    @PostMapping("/store/save")
    public String saveStore(@ModelAttribute StoreDto storeDto) {
        try {
            String apiUrl = "http://13.233.193.166:9091/api/stores";
            restTemplate.postForObject(apiUrl, storeDto, StoreDto.class);
            return "redirect:/store/naman"; // Redirect to the store list
        } catch (Exception e) {
            e.printStackTrace(); // See error in console
            return "error"; // You can create error.html for friendly error page
        }
    }




}

