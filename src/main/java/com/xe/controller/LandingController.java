package com.xe.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Log4j2
@Controller
@RequestMapping("/landing")
public class LandingController {

    private static String fmt(String format, Object... args) {
        return String.format(format, args);
    }

//    @GetMapping
//    public String handleLanding(HttpServletRequest httpServletRequest) {
//
//        return  "landing";
//    }
    @GetMapping
    public String handleLanding(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        log.info("GET -> /landing");
        return session == null ? "redirect:" : "landing";
    }
}
