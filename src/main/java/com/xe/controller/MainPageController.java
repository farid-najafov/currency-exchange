package com.xe.controller;

import com.xe.enums.XCurrency;
import com.xe.entity.api.Exchange;
import com.xe.service.ExchangeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Controller
@RequestMapping("/main-page")
public class MainPageController {

    private final ExchangeService qService;
    private static final DecimalFormat df = new DecimalFormat("0.0000");

    public MainPageController(ExchangeService qService) {
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

    @ModelAttribute("object")
    public Exchange userRegistrationDto() {
        return new Exchange();
    }


    @GetMapping
    public String showMainPage() {
        log.info("GET -> /main-page");
        return "main-page";
    }

    @PostMapping
    public String get_rates(@RequestParam("base") String baseCcy,
                            @RequestParam("quote") String quoteCcy,
                            @RequestParam(value = "amount", defaultValue = "1") String value,
                            @ModelAttribute("object") Exchange q,
                            Model model) {

        log.info(fmt("Base: %s, Quote: %s", baseCcy, quoteCcy));
        log.info(fmt("value: %s", value));

        q = qService.get_rate_for_specific_exchange(baseCcy, quoteCcy);
        log.info(fmt("RATE: %s", q.rate));

        Double calc = Double.parseDouble(value) * q.rate;

        BigDecimal bigDecimal = new BigDecimal(value);
        String s = bigDecimal.setScale(2, RoundingMode.CEILING).toPlainString();

        model.addAttribute("object", q);
        model.addAttribute("amount", s);
        model.addAttribute("result", df.format(calc));
        model.addAttribute("left", df.format(q.rate));
        model.addAttribute("right", df.format(1 / q.rate));
        return "main-page";
    }

    @ExceptionHandler({Exception.class})
    public RedirectView handleErr2(RedirectAttributes ra) {
        ra.addFlashAttribute("msg",  "Please choose correct details to convert");
        return new RedirectView("/main-page");
    }
}
