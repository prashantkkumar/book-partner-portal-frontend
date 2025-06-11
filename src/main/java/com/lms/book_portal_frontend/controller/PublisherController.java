package com.lms.book_portal_frontend.controller;


import com.lms.book_portal_frontend.dto.PublisherDTO;
import com.lms.book_portal_frontend.dto.PublisherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL = "http://13.233.193.166:9091/api/publishers";


    @GetMapping("/{name}")
    public String listPublishers(@PathVariable String name,
                                 @RequestParam(defaultValue = "0") int page,
                                 Model model) {
        String url = API_URL + "?page=" + page + "&size=5";
        PublisherResponse response = restTemplate.getForObject(url, PublisherResponse.class);

        model.addAttribute("publishers", response);
        model.addAttribute("name", name);
        return "publisher-list";
    }

    @GetMapping("/edit/{id}")
    public String editPublisher(@PathVariable Long id, Model model) {
        PublisherDTO publisher = restTemplate.getForObject(API_URL + "/" + id, PublisherDTO.class);
        model.addAttribute("publisher", publisher);
        return "publisher-form";
    }

    @PostMapping("/update")
    public String updatePublisher(@ModelAttribute PublisherDTO publisher, RedirectAttributes redirectAttributes) {
        restTemplate.put(API_URL + "/" + publisher.getId(), publisher);
        redirectAttributes.addFlashAttribute("message", "Updated successfully!");
        return "redirect:/publishers/AYUSH";
    }

    @GetMapping("/add")
    public String addPublisher(Model model) {
        model.addAttribute("publisher", new PublisherDTO());
        return "publisher-form";
    }

    @PostMapping("/insert")
    public String insertPublisher(@ModelAttribute PublisherDTO publisher, RedirectAttributes redirectAttributes) {
        restTemplate.postForObject(API_URL, publisher, PublisherDTO.class);
        redirectAttributes.addFlashAttribute("message", "Added successfully!");
        return "redirect:/publishers/AYUSH";
    }
}
