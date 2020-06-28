package com.xe.controller;

import com.xe.entity.User;
import com.xe.service.UserService;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Log4j2
@Controller
@RequestMapping("/login")
public class LoginController {

    private static String fmt(String format, Object... args) {
        return String.format(format, args);
    }

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getLogin() {
        return "index";
    }


    @PostMapping
    public String postLogin(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            Model model, HttpServletRequest httpServletRequest) {

        Optional<User> user = userService.findByEmailAndPassword(email, password);
        log.info(fmt("Found user %s", user));

        if (!user.isPresent()) {
            model.addAttribute("loginError", "Login credentials are incorrect, please try again");
            log.warn("Incorrect login credentials");
            return "index";
        }

        httpServletRequest.getSession();
        return "redirect:/main-page-authorized";
    }
}
