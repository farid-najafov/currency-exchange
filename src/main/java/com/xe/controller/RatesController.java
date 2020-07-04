package com.xe.controller;

import com.xe.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Log4j2
@Controller
@RequestMapping("/rates")
public class RatesController {

    private static String fmt(String format, Object... args) {
        return String.format(format, args);
    }

    @GetMapping
    public String showRates(HttpServletRequest httpServletRequest) {
        log.info("GET -> /rates");
        //       TODO get rid og try/catch. Optimize
        try {
            HttpSession session = httpServletRequest.getSession(false);
            User user = (User) session.getAttribute("user");
            log.info(fmt("Found user %s in Main Page Authorized", user));
            return "rates";
        }catch (Exception e){
            return "redirect:/login";
        }
//        return session == null ? "index" : "rates";
    }

    @PostMapping
    public String exchange() {
        return "rates";
    }
}
