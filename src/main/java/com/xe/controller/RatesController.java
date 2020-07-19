package com.xe.controller;

import com.xe.entity.api.RateByPeriod;
import com.xe.enums.XCurrency;
import com.xe.exception.InvalidPeriodException;
import com.xe.service.ExchangeService;
import com.xe.service.UserService;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Value
@Log4j2
@Controller
@RequestMapping("/main-page-rates")
public class RatesController {

    ExchangeService exchangeService;

    public Date parseDate(String s) throws ParseException {
        return new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(s);
    }

    public LocalDate dateToLD(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @ModelAttribute("obj")
    public List<RateByPeriod> create() {
        return new ArrayList<>();
    }

    @ModelAttribute("currencies")
    public List<XCurrency> addCurrenciesToModel(Model model) {
        List<XCurrency> collect = Arrays.stream(XCurrency.values())
                .collect(Collectors.toList());
        model.addAllAttributes(collect);
        return collect;
    }

    @GetMapping
    public String get(Principal p, Model model) {

        model.addAttribute("name", UserService.getUserNameFromPrincipal(p));
        log.info("GET -> /main-page-authorized ");
        return "main-page-rates";
    }

    @SneakyThrows
    @PostMapping("show-rates")
    public String post_show_rates(@RequestParam("start_at") String start,
                                  @RequestParam("end_at") String end,
                                  @RequestParam("base") String base,
                                  @RequestParam("quote") String quote,
                                  Model model, Principal p) {

        Date fmtS = parseDate(start);
        Date fmtE = parseDate(end);
        LocalDate d1 = dateToLD(fmtS);
        LocalDate d2 = dateToLD(fmtE);
        long period = ChronoUnit.DAYS.between(d1, d2);

        if (period > 10 || d1.isAfter(LocalDate.now()) || d1.isAfter(d2))
            throw new InvalidPeriodException("Invalid Period exception");

        List<RateByPeriod> list = exchangeService.get_rate_for_specific_interval(fmtS, fmtE, base, quote);
        log.info(String.format("LIST OF RATES BY PERIOD %s", list));

        model.addAttribute("obj", list);
        model.addAttribute("name", UserService.getUserNameFromPrincipal(p));
        return "rates";
    }

    @ExceptionHandler({Exception.class, NullPointerException.class, InvalidPeriodException.class})
    public String handleError(RedirectAttributes ra, Exception ex) {

        if (ex.getClass().getSimpleName().equals("InvalidPeriodException")) {
            ra.addFlashAttribute("msg", "Please choose valid period \n" +
                    "(Period should be less than 10 days)");
            return "redirect:/main-page-rates";
        } else if (ex.getClass().getSimpleName().equals("NullPointerException")) {
            log.info("User Not Found Exception");
            return "error-404";
        } else {
            ra.addFlashAttribute("msg", "Please choose correct details to convert");
            return "redirect:/main-page-rates";
        }
    }
}
