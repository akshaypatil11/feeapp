package com.feeapp.feeapp.controller;

import com.feeapp.feeapp.repository.FeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final FeeRepository feeRepository;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("fees", feeRepository.findAll());
        return "dashboard";
    }
}
