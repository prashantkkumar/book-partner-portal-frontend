package com.lms.book_portal_frontend.controller;

import com.lms.book_portal_frontend.dto.JobDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

@Controller
public class JobController {

    @Autowired
    private RestTemplate restTemplate;

    private final String apiUrl = "http://localhost:8081/api/jobs";

    @GetMapping("/job/mohit")
    public String showJobs(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int pageSize,
            @RequestParam(value = "search", required = false) String search,
            Model model) {

        // 1. Get jobs from external API
        JobDto[] allJobsArray = restTemplate.getForObject(apiUrl, JobDto[].class);
        List<JobDto> filteredJobs = new ArrayList<>(Arrays.asList(allJobsArray));

        // 2. Pagination calculation
        int totalJobs = filteredJobs.size();
        int totalPages = (int) Math.ceil((double) totalJobs / pageSize);
        page = Math.max(1, Math.min(page, totalPages)); // Safe page bounds

        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalJobs);
        List<JobDto> jobsOnPage = filteredJobs.subList(fromIndex, toIndex);

        // 3. Add attributes to model
        model.addAttribute("jobs", jobsOnPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalJobs);
        model.addAttribute("search", search);
        model.addAttribute("size", pageSize);
        model.addAttribute("startEntry", totalJobs == 0 ? 0 : fromIndex + 1);
        model.addAttribute("endEntry", toIndex);

        return "job-list";
    }

    // Search Response
    @GetMapping("/job/search")
    @ResponseBody
    public List<JobDto> liveSearch(@RequestParam("query") String query) {
        JobDto[] allJobsArray = restTemplate.getForObject(apiUrl, JobDto[].class);

        return Arrays.stream(allJobsArray)
                .filter(job -> job.getJobDesc().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    // GET: Load edit form for a specific job
    @GetMapping("/editJob/{id}")
    public String editJobForm(@PathVariable("id") Short id, Model model) {
        JobDto[] jobs = restTemplate.getForObject(apiUrl, JobDto[].class);
        JobDto selectedJob = Arrays.stream(jobs)
                .filter(job -> job.getId().equals(id))
                .findFirst()
                .orElse(null);

        model.addAttribute("job", selectedJob);
        return "edit-job";
    }

    // POST: Update job using PUT API
    @PostMapping("/updateJob")
    public String updateJob(@ModelAttribute JobDto jobDto, RedirectAttributes redirectAttributes) {
        String putUrl = apiUrl + "/" + jobDto.getId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<JobDto> requestEntity = new HttpEntity<>(jobDto, headers);

        try {
            ResponseEntity<JobDto> response = restTemplate.exchange(
                    putUrl,
                    HttpMethod.PUT,
                    requestEntity,
                    JobDto.class
            );
            redirectAttributes.addFlashAttribute("message", "Job updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Update failed!");
        }

        return "redirect:/job/mohit";
    }

    //  for post add new data

    @GetMapping("/jobs/new")
    public String showAddJobForm(Model model) {
        model.addAttribute("job", new JobDto()); // Empty form-binding object
        return "add-job"; // HTML page name
    }
    @PostMapping("/jobs")
    public String saveJob(@ModelAttribute("job") JobDto jobDto, RedirectAttributes redirectAttributes) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<JobDto> request = new HttpEntity<>(jobDto, headers);
            restTemplate.postForEntity(apiUrl, request, String.class);

            redirectAttributes.addFlashAttribute("message", "Job added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add job.");
        }
        return "redirect:/job/mohit";
    }
}