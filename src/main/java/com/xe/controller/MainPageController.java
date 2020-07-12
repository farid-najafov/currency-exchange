package com.xe.controller;

import com.xe.entity.api.Exchange;
import com.xe.enums.XCurrency;
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

    private static final DecimalFormat df = new DecimalFormat("0.0000");
    private final ExchangeService qService;

    public MainPageController(ExchangeService qService) {
        this.qService = qService;
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
                            Model md) {

        Exchange ex = qService.get_rate_for_specific_exchange(baseCcy, quoteCcy);
        log.info(String.format("Base: %s, Quote: %s, Value: %s, RATE: %s", baseCcy, quoteCcy, value, ex.rate));

        double calc = Double.parseDouble(value) * ex.rate;

        BigDecimal bd = new BigDecimal(value);
        String s = bd.setScale(2, RoundingMode.CEILING).toPlainString();

        md.addAttribute("object", ex);
        md.addAttribute("amount", s);
        md.addAttribute("result", df.format(calc));
        md.addAttribute("left", df.format(ex.rate));
        md.addAttribute("right", df.format(1 / ex.rate));
        return "main-page";
    }

    @ExceptionHandler({Exception.class})
    public RedirectView handleErr(RedirectAttributes ra) {
        ra.addFlashAttribute("msg", "Please choose correct details to convert");
        return new RedirectView("/main-page");
    }
}
