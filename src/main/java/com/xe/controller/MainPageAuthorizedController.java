package com.xe.controller;

import com.xe.entity.api.Exchange;
import com.xe.entity.sec_ent.XUserDetails;
import com.xe.enums.XCurrency;
import com.xe.exception.InvalidPeriodException;
import com.xe.service.ExchangeService;
import com.xe.service.SocialUserService;
import com.xe.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
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
import java.util.stream.Collectors;

@Log4j2
@Controller
@RequestMapping("/main-page-authorized")
public class MainPageAuthorizedController {
    private final RestTemplate rest;
    private final UserService userService;
    private final ExchangeService exchangeService;
    private final SocialUserService socialUserService;
    private static final DecimalFormat df = new DecimalFormat("0.0000");

    public MainPageAuthorizedController(RestTemplate rest, UserService userService, ExchangeService exchangeService, SocialUserService socialUserService) {
        this.rest = rest;
        this.userService = userService;
        this.exchangeService = exchangeService;
        this.socialUserService = socialUserService;
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
    public String get(Principal p, Model model) {

        if (p instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken user = (OAuth2AuthenticationToken) p;
            socialUserService.addUserSocial(user);
            model.addAttribute("name", user.getPrincipal().getAttribute("name"));
        } else {
            UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) p;
            XUserDetails xUserDetails = (XUserDetails) user.getPrincipal();
            model.addAttribute("name", xUserDetails.getFullName());
        }
        return "main-page-authorized";
    }

    @PostMapping
    public String post_with_history(@RequestParam(value = "amount", defaultValue = "1") String value,
                                    @RequestParam("single-date") String date,
                                    @RequestParam("base") String baseCcy,
                                    @RequestParam("quote") String quoteCcy,
                                    Model md, Principal p) throws ParseException {


        LocalDate d = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(date)
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (d.isAfter(LocalDate.now())) throw new InvalidPeriodException("Invalid Period Exception");

        Exchange ex = exchangeService.get_rate_for_specific_date(date, baseCcy, quoteCcy);

        double calc = Double.parseDouble(value) * ex.rate;

        BigDecimal bd = new BigDecimal(value);
        String amount = bd.setScale(2, RoundingMode.CEILING).toPlainString();

        ex.setAmount(Double.parseDouble(amount));
        ex.setResult(Double.parseDouble(df.format(calc)));

        if (p instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken user = (OAuth2AuthenticationToken) p;
            socialUserService.addExchange(user.getPrincipal().getAttribute("email"), user.getAuthorizedClientRegistrationId(), ex);
        } else {
            userService.addExchange(p.getName(), ex);
        }

        md.addAttribute("object", ex);
        md.addAttribute("amount", amount);
        md.addAttribute("date", date);
        md.addAttribute("result", df.format(calc));
        md.addAttribute("left", df.format(ex.rate));
        md.addAttribute("right", df.format(1 / ex.rate));
        md.addAttribute("name", UserService.getUserNameFromPrincipal(p));

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
