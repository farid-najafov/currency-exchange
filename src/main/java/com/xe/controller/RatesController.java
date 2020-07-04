package com.xe.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Log4j2
@Controller
@RequestMapping("/rates")
public class RatesController {

    private static String fmt(String format, Object... args) {
        return String.format(format, args);
    }

    @GetMapping
    public String showRates(HttpServletRequest req) {

        HttpSession session = req.getSession(false);

        log.info(fmt("GET -> /rates"));

        return session.getAttribute("user") == null ? "error-404" : "rates";

    }

    @PostMapping
    public String exchange() {
        return "rates";
    }

    @ExceptionHandler(NullPointerException.class)
    public String handleErr() {
        log.info("User Not Found Exception");
        return "error-404";
    }

}
