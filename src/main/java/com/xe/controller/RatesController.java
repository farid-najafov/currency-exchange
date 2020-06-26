package com.xe.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/rates")
public class RatesController {

    /**
     * http://localhost:8081/rates
     */
    @GetMapping
    public String showRates() {
        log.info("GET -> /rates");
        return "rates";
    }
}
