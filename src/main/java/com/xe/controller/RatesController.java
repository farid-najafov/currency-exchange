package com.xe.controller;

import com.xe.entity.api.RateByPeriod;
import com.xe.enums.XCurrency;
import com.xe.service.ExchangeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Controller
@RequestMapping("/rates")
@SessionAttributes(
        names = {"dateObj"},
        types ={RateByPeriod.class}
)
public class RatesController {
    private final ExchangeService exchangeService;

    public RatesController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    private static String fmt(String format, Object... args) {
        return String.format(format, args);
    }

    @ModelAttribute("obj")
    public List<RateByPeriod> create2() {
        return new ArrayList<>();
    }

    @ModelAttribute("dateObj")
    public RateByPeriod create3() {
        return new RateByPeriod();
    }

    @ModelAttribute("currencies")
    public List<XCurrency> addCurrenciesToModel(Model model) {
        List<XCurrency> collect = Arrays.stream(XCurrency.values())
                .collect(Collectors.toList());
        model.addAllAttributes(collect);
        return collect;
    }

    @GetMapping
    public String get(HttpServletRequest req) {

        HttpSession session = req.getSession(false);
        log.info("GET -> /main-page-authorized ");

        return session.getAttribute("user") == null ?
                "error-404" : "main-page-rates";
    }

    @PostMapping()
    public String post_show_rates(@RequestParam("start_at") String a,
                                  @RequestParam("end_at") String b,
                                  @RequestParam("base") String fd,
                                  @RequestParam("quote") String q,
                                  Model model) throws ParseException {

        List<RateByPeriod> list = exchangeService.get_rate_for_specific_interval(a, b, fd, q);
        log.info("LIST OF RATE BY PERIOD" + list);

        model.addAttribute("obj", list);
        return "rates";
    }

    @ExceptionHandler({Exception.class, NullPointerException.class})
    public String handleErr2(RedirectAttributes ra, Exception ex) {
        if (ex.getClass().getSimpleName().equals("NullPointerException")) {
            log.info("User Not Found Exception");
            return "error-404";
        } else if(ex.getClass().getSimpleName().equals("MissingServletRequestParameterException") ) {
            ra.addFlashAttribute("msg", "Please choose correct details to convert");
            return "redirect:/main-page-rates";
        }else
        {
            log.info(ex.getClass().getSimpleName());
            return "error-404";

        }
    }

}
