package com.ibatech.app.controller;

import com.ibatech.app.entity.Exchange;
import com.ibatech.app.entity.User;
import com.ibatech.app.repo.ExchangeJpaRepo;
import com.ibatech.app.repo.UserJpaRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rel")
public class TestController {
    private final ExchangeJpaRepo exchangeJpaRepo;
    private final UserJpaRepo userJpaRepo;


    public TestController(ExchangeJpaRepo exchangeJpaRepo, UserJpaRepo userJpaRepo) {
        this.exchangeJpaRepo = exchangeJpaRepo;
        this.userJpaRepo = userJpaRepo;
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable int id) {
        return userJpaRepo.getOne(id);
    }

    @GetMapping("ex/{id}")
    public Exchange getExchange(@PathVariable int id) {
        return exchangeJpaRepo.getOne(id);
    }
}
