package com.xe.controller;

import com.xe.entity.XCurrency;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Log4j2
@Controller
@RequestMapping("/rates")
public class RatesController {



    /**
     * http://localhost:8081/rates
     */
    @GetMapping
    public String showRates(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        log.info("GET -> /rates");
        return session == null ? "redirect:/login" : "rates";
    }

    @PostMapping
    public String exchange() {
        return "rates";
    }
}
