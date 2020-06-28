package com.xe.controller;

import com.xe.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Log4j2
@Controller
@RequestMapping("/main-page")
public class MainPageController {

    private static String fmt(String format, Object... args) {
        return String.format(format, args);
    }

    @GetMapping
    public String showMainPage(
            @ModelAttribute("user") User user,
            HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        log.info(fmt("Found user %s", user));
        return session == null ? "main-page" : "main-page-authorized";
    }


}
