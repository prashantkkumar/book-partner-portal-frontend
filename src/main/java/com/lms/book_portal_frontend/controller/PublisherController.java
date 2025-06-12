package com.lms.book_portal_frontend.controller;

import com.lms.book_portal_frontend.dto.PublisherDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PublisherController {

    private final WebClient webClient = WebClient.create("http://13.233.193.166:9091/api");

    @GetMapping("/publishers")
    public String getPublishers(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        String uri = "/publishers";
        if (keyword != null && !keyword.isEmpty()) {
            String encodedKeyword = java.net.URLEncoder.encode(keyword, java.nio.charset.StandardCharsets.UTF_8);
            uri += "?keyword=" + encodedKeyword;
            System.out.println("Searching with URI: http://13.233.193.166:9091/api" + uri);
        }

        List<PublisherDTO> publishers;
        try {
            publishers = webClient.get()
                    .uri(uri)
                    .header("Accept", "application/json")
                    .retrieve()
                    .bodyToFlux(PublisherDTO.class)
                    .collectList()
                    .block();
            System.out.println("Publishers fetched: " + publishers);

            // Client-side filtering since the backend isn't filtering
            if (keyword != null && !keyword.isEmpty()) {
                String keywordLower = keyword.toLowerCase();
                // Normalize "US" to "USA" for consistency
                if (keywordLower.equals("us")) {
                    keywordLower = "usa";
                }
                final String finalKeyword = keywordLower;
                publishers = publishers.stream()
                        .filter(p -> {
                            String country = p.getCountry() != null ? p.getCountry().toLowerCase() : "";
                            if (country.equals("us")) {
                                country = "usa"; // Normalize "US" to "USA"
                            }
                            return (p.getPubName() != null && p.getPubName().toLowerCase().contains(finalKeyword)) ||
                                    (p.getCity() != null && p.getCity().toLowerCase().contains(finalKeyword)) ||
                                    (country.contains(finalKeyword));
                        })
                        .collect(Collectors.toList());
                System.out.println("Publishers after client-side filtering: " + publishers);
            }
        } catch (Exception e) {
            System.err.println("Error fetching publishers: " + e.getMessage());
            model.addAttribute("error", "Failed to fetch publishers. Please try again later.");
            publishers = List.of();
        }

        model.addAttribute("publishers", publishers);
        model.addAttribute("keyword", keyword);
        return "publishers";
    }

    // Rest of the controller remains unchanged
    @GetMapping("/publishers/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        PublisherDTO publisher = webClient.get()
                .uri("/publishers/{id}", id)
                .retrieve()
                .bodyToMono(PublisherDTO.class)
                .block();

        model.addAttribute("publisher", publisher);
        return "edit-publisher";
    }

    @PostMapping("/publishers/edit")
    public String editPublisher(@ModelAttribute PublisherDTO publisher) {
        webClient.put()
                .uri("/publishers/{id}", publisher.getPubId())
                .body(Mono.just(publisher), PublisherDTO.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        return "redirect:/publishers";
    }

    @GetMapping("/publishers/add")
    public String addForm(Model model) {
        model.addAttribute("publisher", new PublisherDTO());
        return "add-publisher";
    }

    @PostMapping("/publishers/add")
    public String addPublisher(@ModelAttribute PublisherDTO publisher) {
        System.out.println("Adding publisher: " + publisher);
        try {
            webClient.post()
                    .uri("/publishers")
                    .body(Mono.just(publisher), PublisherDTO.class)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            System.out.println("Publisher added successfully");
        } catch (Exception e) {
            System.err.println("Failed to add publisher: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/publishers";
    }
}