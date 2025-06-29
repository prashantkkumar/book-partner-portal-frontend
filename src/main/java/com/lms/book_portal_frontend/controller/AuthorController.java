package com.lms.book_portal_frontend.controller;

import com.lms.book_portal_frontend.dto.AuthorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final WebClient webClient = WebClient.create("http://localhost:8081/api/");

    @GetMapping("/authors")
    public String getAuthors(Model model) {
        List<AuthorDto> authors = webClient.get()
                .uri("/authors")
                .retrieve()
                .bodyToFlux(AuthorDto.class)
                .collectList()
                .block();

        model.addAttribute("authors", authors);
        return "authors"; // maps to authors.html
    }

    @GetMapping("/authors/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        AuthorDto author = webClient.get()
                .uri("/authors/{id}", id)
                .retrieve()
                .bodyToMono(AuthorDto.class)
                .block();

        model.addAttribute("author", author);
        return "edit-author";
    }

    @PostMapping("/authors/edit")
    public String editAuthor(@ModelAttribute AuthorDto author) {
        webClient.put()
                .uri("/authors/{id}", author.getAuId())
                .body(Mono.just(author), AuthorDto.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        return "redirect:/authors";
    }

    @GetMapping("/authors/add")
    public String addForm(Model model) {
        model.addAttribute("author", new AuthorDto());
        return "add-author";
    }

    @PostMapping("/authors/add")
    public String addAuthor(@ModelAttribute AuthorDto author) {
        webClient.post()
                .uri("/authors")
                .body(Mono.just(author), AuthorDto.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        return "redirect:/authors";
    }
}