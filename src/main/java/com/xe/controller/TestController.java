package com.xe.controller;

import com.xe.entity.Exchange;
import com.xe.entity.User;
import com.xe.repo.ExchangeJpaRepo;
import com.xe.repo.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rel")
public class TestController {
    private final ExchangeJpaRepo exchangeJpaRepo;
    private final UserRepository userJpaRepo;

    public TestController(ExchangeJpaRepo exchangeJpaRepo, UserRepository userJpaRepo) {
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
