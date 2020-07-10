package com.xe.controller;

import com.xe.entity.User;
import com.xe.entity.api.Exchange;
import com.xe.enums.XCurrency;
import com.xe.exception.InvalidPeriodException;
import com.xe.service.ExchangeService;
import com.xe.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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
    public Exchange create() {
        return new Exchange();
    }


    @GetMapping
    public String get() {

        log.info("GET -> /main-page-authorized ");

        return "main-page-authorized";
    }


    @PostMapping
    public String post_with_history(@RequestParam(value = "amount", defaultValue = "1") String value,
                                    @RequestParam("single-date") String date,
                                    @RequestParam("base") String baseCcy,
                                    @RequestParam("quote") String quoteCcy,
                                     Model md, Principal principal) throws ParseException {

        log.info("Post -> /main-page-authorized");

        LocalDate d = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(date)
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (d.isAfter(LocalDate.now())) throw new InvalidPeriodException("Invalid Period Exception");

        Exchange ex = exchangeService.get_rate_for_specific_date(date, baseCcy, quoteCcy);

        double calc = Double.parseDouble(value) * ex.rate;

        BigDecimal bd = new BigDecimal(value);
        String amount = bd.setScale(2, RoundingMode.CEILING).toPlainString();

        ex.setAmount(Double.parseDouble(amount));
        ex.setResult(Double.parseDouble(df.format(calc)));

        userService.addExchange(principal.getName(),ex);

        md.addAttribute("object", ex);
        md.addAttribute("amount", amount);
        md.addAttribute("date", date);
        md.addAttribute("result", df.format(calc));
        md.addAttribute("left", df.format(ex.rate));
        md.addAttribute("right", df.format(1 / ex.rate));

        return "main-page-authorized";
    }

    @ExceptionHandler({Exception.class, NullPointerException.class, InvalidPeriodException.class})
    public String handleErr(RedirectAttributes ra, Exception ex) {

        if (ex.getClass().getSimpleName().equals("InvalidPeriodException")) {
            ra.addFlashAttribute("msg", "Please choose correct date");
            return "redirect:/main-page-authorized";
        } else if (ex.getClass().getSimpleName().equals("NullPointerException")) {
            log.info("User Not Found Exception");
            return "error-404";
        } else {
            ra.addFlashAttribute("msg", "Please choose correct details to convert");
            return "redirect:/main-page-authorized";
        }
    }
}
