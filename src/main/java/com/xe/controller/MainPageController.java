package com.xe.controller;

import com.xe.dto.UserRegistrationDto;
import com.xe.entity.Exchange2;
import com.xe.service.ExchangeService;
import com.xe.util.XCurrency;
import com.xe.entity.api.Quote;
import com.xe.service.QuoteService;
import lombok.extern.log4j.Log4j2;
import org.h2.engine.Mode;
import org.hibernate.boot.model.relational.QualifiedTableName;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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

    private final QuoteService qService;
    private final ExchangeService eService;
    private static DecimalFormat df = new DecimalFormat("0.0000");

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

    @ModelAttribute("object")
    public Quote userRegistrationDto() {
        return new Quote("EUR", "USD");
    }


    @GetMapping
    public String showMainPage(@ModelAttribute("object") Quote quote) {
//        model.addAttribute("object",new Quote() );

        return "main-page";
    }

    @PostMapping
    public String get_rates(@RequestParam("base") String baseCcy,
                            @RequestParam("quote") String quoteCcy,
                            @RequestParam(value = "amount", defaultValue = "1") String value,
                            @ModelAttribute("object") Quote q,
                            Model model, RedirectAttributes ra
    ) {
//        List<Quote> rates = qService.get_rates(baseCcy);
        log.info("Base " + baseCcy + " Quote" + quoteCcy);
        log.info("value " + value);
        q = qService.get_rate_for_specific_exchange(baseCcy, quoteCcy);
        log.info("RATE " + q.rate);
        Double v = 0.00;

        v = eService.find_quoteCcy_value(Double.parseDouble(value), q.rate);

        BigDecimal bigDecimal = new BigDecimal(value);
        String s = bigDecimal.setScale(2, RoundingMode.CEILING).toPlainString();

        model.addAttribute("object", q);
        model.addAttribute("amount", s);
        model.addAttribute("result", df.format(v));

        model.addAttribute("left", df.format(q.rate));
        model.addAttribute("right", df.format(1 / q.rate));
        return "main-page";
    }

    //    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "requested book not found")
    @ExceptionHandler({NumberFormatException.class})
    public RedirectView handleErr2(RedirectAttributes ra) {
//        log.info("NumberFormatException");
//        ModelAndView modelAndView = new ModelAndView();
////        ra.addFlashAttribute("msj", "Enter digits or String is empty");
////        mv.addObject("msj", "Enter digits or String is empty");
//        modelAndView.addObject("object", new Quote());
//        modelAndView.addObject("msj", "Enter digits or String is empty");
//        modelAndView.setViewName("main-page");
        ra.addFlashAttribute("msj",  "Enter digits or String is empty");
        return new RedirectView("/main-page");
    }

}
