package com.xe.controller;

import com.xe.entity.User;
import com.xe.entity.XCurrency;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@Controller
@RequestMapping("/main-page")
public class MainPageController {

    private static String fmt(String format, Object... args) {
        return String.format(format, args);
    }

    @ModelAttribute("currencies")
    public List<XCurrency.Type> addCurrenciesToModel(Model model) {
        List<XCurrency.Type> collect = Arrays.stream(XCurrency.Type.values())
                .collect(Collectors.toList());
        model.addAllAttributes(collect);
        return collect;
    }

    @GetMapping
    public String showMainPage() {
        return "main-page";
    }


}
