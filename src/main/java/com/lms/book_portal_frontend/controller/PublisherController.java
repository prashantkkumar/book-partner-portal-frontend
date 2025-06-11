package com.lms.book_portal_frontend.controller;

import com.lms.book_portal_frontend.dto.PublisherDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PublisherController {

    private final WebClient webClient = WebClient.create("http://13.233.193.166:9091/api/");

    @GetMapping("/publishers")
    public String getPublishers(Model model) {
        List<PublisherDTO> publishers = webClient.get()
                .uri("/publishers")
                .retrieve()
                .bodyToFlux(PublisherDTO.class)
                .collectList()
                .block();

        model.addAttribute("publishers", publishers);
        return "publishers"; // maps to publishers.html
    }

    @GetMapping("/publishers/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        PublisherDTO publisher = webClient.get()
                .uri("/publishers/{id}", id)
                .retrieve()
                .bodyToMono(PublisherDTO.class)
                .block();

        model.addAttribute("publisher", publisher);
        return "edit-publisher"; // form page for edit
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
        return "add-publisher"; // form page for add
    }

    @PostMapping("/publishers/add")
    public String addPublisher(@ModelAttribute PublisherDTO publisher) {
        webClient.post()
                .uri("/publishers")
                .body(Mono.just(publisher), PublisherDTO.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        return "redirect:/publishers";
    }
}
