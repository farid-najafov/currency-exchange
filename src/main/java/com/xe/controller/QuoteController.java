package com.xe.controller;

import com.xe.entity.ext_api.QResponse;
import com.xe.service.QuoteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ccy")
public class QuoteController {

    private final QuoteService qService;

    public QuoteController(QuoteService qService) {
        this.qService = qService;
    }

    @GetMapping
    public QResponse get_rates(@RequestParam("base") String baseCcy) {
        return qService.get_rates(baseCcy);
    }
}
