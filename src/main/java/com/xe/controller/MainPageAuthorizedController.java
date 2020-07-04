package com.xe.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Log4j2
@Controller
@RequestMapping("/main-page-authorized")
public class MainPageAuthorizedController {

    private static String fmt(String format, Object... args) {
        return String.format(format, args);
    }

    @GetMapping
    public String showMainPageAuthorized(HttpServletRequest req) {

        HttpSession session = req.getSession(false);
        log.info(fmt("GET -> /main-page-authorized "));

        return session.getAttribute("user") == null ?
                "error-404" : "main-page-authorized";
    }

    @ExceptionHandler(NullPointerException.class)
    public String handleErr3() {
        log.info("User Not Found Exception");
        return "error-404";
    }
}
