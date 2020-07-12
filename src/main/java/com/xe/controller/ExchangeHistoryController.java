package com.xe.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/history")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ExchangeHistoryController {

    private final UserService userService;

    public ExchangeHistoryController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String get(Principal principal, Model model) {
        model.addAttribute("user", userService.findByEmail(principal.getName()).orElse(null));
        return "history";
    }

}
