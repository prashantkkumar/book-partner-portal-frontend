package com.lms.book_portal_frontend.controller;

import com.lms.book_portal_frontend.dto.PublisherDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PublisherController {

    private final WebClient webClient = WebClient.create("http://13.233.193.166:9091/api");

    @GetMapping("/publishers")
    public String getPublishers(Model model) {
        List<PublisherDTO> publishers = webClient.get()
                .uri("/publishers")
                .retrieve()
                .bodyToFlux(PublisherDTO.class)
                .collectList()
                .block();

        model.addAttribute("publishers", publishers);
        return "publishers";
    }

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
            // Optionally, add an error attribute to the model and return to form page
            // model.addAttribute("error", "Failed to add publisher");
            // return "add-publisher";
        }
        return "redirect:/publishers";
    }



}
