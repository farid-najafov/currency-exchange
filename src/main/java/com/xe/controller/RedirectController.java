package com.xe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/*")
public class RedirectController {


    @GetMapping
    public RedirectView redirect_get() {
        return new RedirectView("/");
    }


}
