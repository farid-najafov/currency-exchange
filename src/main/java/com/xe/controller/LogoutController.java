//package com.xe.controller;
//
//import lombok.extern.log4j.Log4j2;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.view.RedirectView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//@RequestMapping("/logout")
//@Log4j2
//@Controller
//public class LogoutController {
//
//    @GetMapping
//    public RedirectView logout_get(HttpServletRequest req) {
//        HttpSession session = req.getSession(false);
//
//        log.info(String.format("SESSION %s", session.getId()));
//
//        if (session.getAttribute("RM") == null) session.invalidate();
//
//        return new RedirectView("/login");
//    }
//
//}
