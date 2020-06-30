package com.xe.controller;

import com.xe.dto.UserRegistrationDto;
import com.xe.entity.Exchange2;
import com.xe.service.ExchangeService;
import com.xe.util.XCurrency;
import com.xe.entity.api.Quote;
import com.xe.service.QuoteService;
import lombok.extern.log4j.Log4j2;
import org.h2.engine.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Controller
@RequestMapping("/main-page")
        @SessionAttributes(
        names = { "exchange2" },
        types = { Exchange2.class })
public class MainPageController {

    private final QuoteService qService;
    private final ExchangeService eService;

    public MainPageController(QuoteService qService, ExchangeService eService) {
        this.qService = qService;
        this.eService = eService;
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

    @ModelAttribute("exchange2")
    public Exchange2 userRegistrationDto() {
        return new Exchange2();
    }


    @GetMapping
    public String showMainPage(@ModelAttribute("exchange2") Exchange2 exchange2, Model model) {
        model.addAttribute("exchange2",exchange2);
        return "main-page";
    }

    @PostMapping

    public String get_rates(@RequestParam("base") String baseCcy,
                            @RequestParam("quote") String quoteCcy,
                            @RequestParam("value") int value,
                            @ModelAttribute("exchange2") Exchange2 exchange2,
                            Model model
    ) {
//        List<Quote> rates = qService.get_rates(baseCcy);
        System.out.println(exchange2);
         exchange2 = eService.exchange_value(baseCcy, quoteCcy, value);
         log.info("EXCHANGE 2 " + exchange2);
        model.addAttribute("exchange2",exchange2);
        return "main-page";
    }

}
