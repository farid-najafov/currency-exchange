package com.xe.controller;

import com.xe.entity.ext_api.TestResponse;
import com.xe.service.ExchangeService;
import lombok.extern.log4j.Log4j2;
import org.aspectj.weaver.ast.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.model.IModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;

@Log4j2
@Controller
@RequestMapping("/rates")
public class RatesController {
    private final ExchangeService exchangeService;

    public RatesController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    private static String fmt(String format, Object... args) {
        return String.format(format, args);
    }
//
//    @GetMapping
//    public String showRates(HttpServletRequest req) throws ParseException {
//
//        HttpSession session = req.getSession(false);
////        String first = "03 July 2020 ";
////        String second = "06 July 2020 ";
////        String base = "USD";
////        String quote = "EUR";
//        log.info(fmt("GET -> /rates"));
//        TestResponse obj = exchangeService.get_rate_for_specific_interval(first, second, base, quote);
//        log.info(obj);
//        return session.getAttribute("user") == null ? "error-404" : "rates";
//
//    }

    @GetMapping
    public String exchange(@RequestParam("start_at") String a,
                           @RequestParam("end_at") String b,
                           @RequestParam("base") String fd,
                           @RequestParam("symbols") String q,
                            Model model ) throws ParseException {

        TestResponse testResponse = exchangeService.get_rate_for_specific_interval(a,b,fd,q);
        log.info(testResponse);

        model.addAttribute("object",testResponse);
        return "rates";
    }

    @ExceptionHandler(NullPointerException.class)
    public String handleErr() {
        log.info("User Not Found Exception");
        return "error-404";
    }

}
