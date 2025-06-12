package com.lms.book_portal_frontend.controller;


import com.lms.book_portal_frontend.dto.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class EmployeeController {

    private final WebClient webClient = WebClient.create("http://13.233.193.166:9091/api");

    @GetMapping("/employees/chaitanya")
    public String getEmployees(Model model) {
        List<EmployeeDTO> employees = webClient.get()
                .uri("/employees")
                .retrieve()
                .bodyToFlux(EmployeeDTO.class)
                .collectList()
                .block();

        model.addAttribute("employees", employees);
        return "employee"; // Displays all employees
    }

    // Show the add form
    @GetMapping("/employees/add-form")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new EmployeeDTO());
        return "add-employee";
    }

    // Handle the form submission
    @PostMapping("/employees/add")
    public String addEmployee(@ModelAttribute("employee") EmployeeDTO employee) {
        webClient.post()
                .uri("/employees")
                .body(Mono.just(employee), EmployeeDTO.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        return "redirect:/employees/chaitanya";
    }
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

    // Handle update
    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute("employee") EmployeeDTO employeeDTO) {
        webClient.put()
                .uri("/employees/{id}", employeeDTO.getEmpId())
                .bodyValue(employeeDTO)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        return "redirect:/employees/chaitanya"; // go back to list
    }


}

//
//
//@Controller
//public class EmployeeController {
//
//    private final WebClient webClient = WebClient.create("http://13.233.193.166:9091/api");
//
//    // ✅ Show all employees
//    @GetMapping("/employees/chaitanya")
//    public String getEmployees(Model model) {
//        List<EmployeeDTO> employees = webClient.get()
//                .uri("/employees")
//                .retrieve()
//                .bodyToFlux(EmployeeDTO.class)
//                .collectList()
//                .block();
//
//        model.addAttribute("employees", employees);
//        return "employee"; // thymeleaf template: employee.html
//    }
//
//
////    // ✅ Show the add employee form
////    @GetMapping("/employees/add-form")
////    public String showAddForm(Model model) {
////        model.addAttribute("employee", new EmployeeDTO());
////        return "add-employee"; // thymeleaf template: add-employee.html
////    }
//
//    @GetMapping("/employees/add-form")
//    public String showAddForm(Model model) {
//        model.addAttribute("employee", new EmployeeDTO());
//        return "add-employee";
//    }
//
//    // ✅ Handle form submission for adding employee
//    @PostMapping("/employees/add")
//    public String addEmployee(@ModelAttribute("employee") EmployeeDTO employee) {
//        System.out.println("Received employee: " + employee);
//        webClient.post()
//                .uri("/employees")
//                .body(Mono.just(employee), EmployeeDTO.class)
//                .retrieve()
//                .bodyToMono(Void.class)
//                .block();
//
//        return "redirect:/employees/chaitanya"; // ✅ Redirect instead of returning a template
//    }
//
//
//    // ✅ Show the edit form
//    @GetMapping("/employees/edit/{id}")
//    public String showEditForm(@PathVariable("id") String id, Model model) {
//        EmployeeDTO employee = webClient.get()
//                .uri("/employees/{id}", id)
//                .retrieve()
//                .bodyToMono(EmployeeDTO.class)
//                .block();
//
//        model.addAttribute("employee", employee); // not "employees"
//        return "edit-employee"; // thymeleaf template: edit-employee.html
//    }
//
//    // ✅ Handle form submission for update
//    @PostMapping("/employees/update")
//    public String updateEmployee(@ModelAttribute("employee") EmployeeDTO employee) {
//        webClient.put()
//                .uri("/employees/{id}", employee.getEmpId())
//                .body(Mono.just(employee), EmployeeDTO.class)
//                .retrieve()
//                .bodyToMono(Void.class)
//                .block();
//
//        return "redirect:/employees/chaitanya";
//    }
//}
