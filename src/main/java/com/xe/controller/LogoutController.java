package com.xe.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Log4j2
@Controller
@RequestMapping("/logout")
public class LogoutController {
    private static String fmt(String format, Object... args) {
        return String.format(format, args);
    }

    @GetMapping
    @ResponseBody
    public RedirectView logout_get(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        log.info("SESSION " + session);

        if (session != null) {
            session.invalidate();
        }
        return new RedirectView("/login");
    }

}
