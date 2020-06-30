package com.xe.controller;

import com.xe.entity.Exchange2;
import com.xe.service.ExchangeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("test")
public class TestController {

    private final ExchangeService exchangeService;

    public TestController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }



    @GetMapping("/{value}")
    @ResponseBody
    public Exchange2 get(@PathVariable int value){
     return    exchangeService.exchange_value("USD","EUR",value);
    }
}
