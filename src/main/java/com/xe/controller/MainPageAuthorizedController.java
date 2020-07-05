package com.xe.controller;

import com.xe.entity.Exchange;
import com.xe.entity.User;
import com.xe.entity.api.Quote;
import com.xe.service.QuoteService;
import com.xe.service.UserService;
import com.xe.util.XCurrency;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Controller
@RequestMapping("/main-page-authorized")
public class MainPageAuthorizedController {

    private final UserService userService;
    private final QuoteService quoteService;

    private static final DecimalFormat df = new DecimalFormat("0.0000");

    public MainPageAuthorizedController(UserService userService, QuoteService quoteService) {
        this.userService = userService;
        this.quoteService = quoteService;
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
        return new Quote();
    }

    @GetMapping
    public String get(HttpServletRequest req) {

        HttpSession session = req.getSession(false);
        log.info(fmt("GET -> /main-page-authorized "));

        return session.getAttribute("user") == null ?
                "error-404" : "main-page-authorized";
    }


    @PostMapping
    public String post(@RequestParam(value = "amount", defaultValue = "1") String value,
                       @RequestParam("single-date") String date,
                       HttpServletRequest req, Model model) {

        log.info("Post -> /main-page-authorized");

        log.info(value);
        log.info(date);
        Quote q = quoteService.get_rate_for_specific_exchange("USD", "EUR");
        double calc = Double.parseDouble(value) * q.rate;

        BigDecimal bigDecimal = new BigDecimal(value);
        String s = bigDecimal.setScale(2, RoundingMode.CEILING).toPlainString();

        Exchange exchange = new Exchange(
                q.base_ccy.name(),
                q.quote_ccy.name(),
                Double.parseDouble(s),
                Double.parseDouble(df.format(calc)),
                q.rate,
                q.date
        );




        List<Exchange> exchanges = new ArrayList();
        exchanges.add(exchange);
        User user = (User) req.getSession().getAttribute("user");
//        user.setExchanges(exchanges);

        userService.addExchangeTest(user.getId(),exchange);


        model.addAttribute("amount", s);
        model.addAttribute("result", df.format(calc));

        return "main-page-authorized";
    }


    @ExceptionHandler(NullPointerException.class)
    public String handleErr3() {
        log.info("User Not Found Exception");
        return "error-404";
    }

    @ExceptionHandler({Exception.class})
    public RedirectView handleErr2(RedirectAttributes ra) {
        ra.addFlashAttribute("msg",  "Please choose correct details to convert");
        return new RedirectView("/main-page-authorized");
    }
}
