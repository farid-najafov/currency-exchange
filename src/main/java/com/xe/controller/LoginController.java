package com.xe.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Log4j2
@Controller
public class LoginController {

    @GetMapping("login")
    public String getLogin() {
        log.info("GET -> /login");
        return "index";
    }

    @GetMapping("login-error")
    public RedirectView postLogin(RedirectAttributes ra) {
        ra.addFlashAttribute("loginError", "Login credentials are incorrect, please try again");
        log.warn("Incorrect login credentials");
        return new RedirectView("/login");
    }
}
