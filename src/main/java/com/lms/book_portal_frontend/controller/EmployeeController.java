package com.lms.book_portal_frontend.controller;

import com.lms.book_portal_frontend.dto.EmployeeDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class EmployeeController {

    private final WebClient webClient = WebClient.create("http://13.233.193.166:9091/api");

    // ✅ Show all employees
    @GetMapping("/employees/chaitanya")
    public String getEmployees(Model model) {
        List<EmployeeDTO> employees = webClient.get()
                .uri("/employees")
                .retrieve()
                .bodyToFlux(EmployeeDTO.class)
                .collectList()
                .block();

        model.addAttribute("employees", employees);
        return "employee";
    }

    @GetMapping("/employees/add-form")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new EmployeeDTO());
        return "add-employee";
    }


    @PostMapping("/employees/add")
    public String addEmployee(@ModelAttribute("employee") EmployeeDTO employee) {
        try {
            webClient.post()
                    .uri("/employees")
                    .bodyValue(employee)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

            return "redirect:/employees/chaitanya"; // ✅ This should redirect after successful addition
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // fallback view
        }
    }



    // ✅ Show the edit form
    @GetMapping("/employees/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        EmployeeDTO employee = webClient.get()
                .uri("/employees/{id}", id)
                .retrieve()
                .bodyToMono(EmployeeDTO.class)
                .block();

        model.addAttribute("employee", employee);
        return "edit-employee";
    }

    @PostMapping("/employees/update")
    public String updateEmployee(@ModelAttribute("employee") EmployeeDTO employeeDTO) {
        try {
            System.out.println("Received DTO: " + employeeDTO);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(employeeDTO.getHireDate(), formatter);
            Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();

            EmployeeDTO backendEmp = new EmployeeDTO();
            backendEmp.setEmpId(employeeDTO.getEmpId());
            backendEmp.setFname(employeeDTO.getFname());
            backendEmp.setMinit(employeeDTO.getMinit());
            backendEmp.setLname(employeeDTO.getLname());
            backendEmp.setJobLvl(employeeDTO.getJobLvl());
            backendEmp.setHireDate(instant.toString());
            backendEmp.setJobId(employeeDTO.getJobId());
            backendEmp.setPubId(employeeDTO.getPubId());

            webClient.put()
                    .uri("/employees/{id}", backendEmp.getEmpId())
                    .bodyValue(backendEmp)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

            return "redirect:/employees/chaitanya";

        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // return your custom error.html template
        }
    }
}
