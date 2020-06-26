package com.xe.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/main-page-authorized")
public class MainPageAuthorizedController {

    @GetMapping
    public String showMainPageAuthorized() {
        log.info("GET -> /main-page-authorized");
        return "main-page-authorized";
    }
}
