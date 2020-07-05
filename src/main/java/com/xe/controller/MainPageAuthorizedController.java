package com.xe.controller;

import com.xe.entity.User;
import com.xe.entity.api.Exchange;
import com.xe.service.ExchangeService;
import com.xe.service.UserService;
import com.xe.enums.XCurrency;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Controller
@RequestMapping("/main-page-authorized")

public class MainPageAuthorizedController {

    private final UserService userService;
    private final ExchangeService exchangeService;

    private static final DecimalFormat df = new DecimalFormat("0.0000");

    public MainPageAuthorizedController(UserService userService, ExchangeService exchangeService) {
        this.userService = userService;
        this.exchangeService = exchangeService;
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
    public String get(HttpServletRequest req) {

        HttpSession session = req.getSession(false);
        log.info("GET -> /main-page-authorized ");

        return session.getAttribute("user") == null ?
                "error-404" : "main-page-authorized";
    }


    @PostMapping
    public String post(@RequestParam(value = "amount", defaultValue = "1") String value,
                       @RequestParam("single-date") String date,
                       @RequestParam("base") String baseCcy,
                       @RequestParam("quote") String quoteCcy,
                       @ModelAttribute("object") Exchange ex,
                       HttpServletRequest req, Model model) throws ParseException {

        log.info("Post -> /main-page-authorized");

        ex = exchangeService.get_rate_for_specific_date(date, baseCcy, quoteCcy);

        double calc = Double.parseDouble(value) * ex.rate;

        BigDecimal bigDecimal = new BigDecimal(value);
        String amount = bigDecimal.setScale(2, RoundingMode.CEILING).toPlainString();

        ex.setAmount(Double.parseDouble(amount));
        ex.setResult(Double.parseDouble(df.format(calc)));

        User user = (User) req.getSession().getAttribute("user");
        userService.addExchangeTest(user.getId(), ex);

        model.addAttribute("object", ex);
        model.addAttribute("amount", amount);
        model.addAttribute("date", date);
        model.addAttribute("result", df.format(calc));
        model.addAttribute("left", df.format(ex.rate));
        model.addAttribute("right", df.format(1 / ex.rate));

        return "main-page-authorized";
    }


    @ExceptionHandler({Exception.class, NullPointerException.class})
    public String handleErr2(RedirectAttributes ra, Exception ex) {
        if (ex.getClass().getSimpleName().equals("NullPointerException")) {
            log.info("User Not Found Exception");
            return "error-404";
        } else {
            ra.addFlashAttribute("msg", "Please choose correct details to convert");
            return "redirect:/main-page-authorized";
        }
    }
}
