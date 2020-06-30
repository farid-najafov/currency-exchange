package com.xe.controller;

import com.xe.util.XCurrency;
import com.xe.entity.api.Quote;
import com.xe.service.QuoteService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Controller
@RequestMapping("/main-page")
public class MainPageController {

    private final QuoteService qService;

    public MainPageController(QuoteService qService) {
        this.qService = qService;
    }

    private static String fmt(String format, Object... args) {
        return String.format(format, args);
    }

    @ModelAttribute("currencies")
    public List<XCurrency> addCurrenciesToModel(Model model) {
        List<XCurrency> collect = Arrays.stream(XCurrency.values())
                .collect(Collectors.toList());
        model.addAllAttributes(collect);
        return collect;
    }

    @GetMapping
    public String showMainPage() {
        return "main-page";
    }

    @PostMapping
    @ResponseBody
    public List<Quote> get_rates(@RequestParam("base") String baseCcy) {
        List<Quote> rates = qService.get_rates(baseCcy);

        return rates;
    }

}
