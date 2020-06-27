package com.xe.controller;

import com.xe.entity.User;
import com.xe.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/showLogin")
    public String shoeLogin() {
        return "index";
    }

    @PostMapping("/processLogin")
    public String login(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            BindingResult bindingResult,
            Model model
    ) {

        System.out.println(email + " " + password);

        final User tempUser = userService.findByEmail(email);

        if (bindingResult.hasErrors()) return "index";

        if (tempUser == null) {
            model.addAttribute("loginError", "Email doesn't exist");
            log.warn("Email doesn't exist");
            return "index";
        }

        if (!tempUser.getPassword().equals(password)) {
            model.addAttribute("loginError", "Invalid Password");
            log.warn("Invalid Password");
            return "index";
        }

        return "redirect:/landing";

    }
}
