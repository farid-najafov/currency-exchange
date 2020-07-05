package com.xe.controller;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xe.entity.User;
import com.xe.repo.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/history")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ExchangeHistoryController {


    private final UserRepository userRepository;

    public ExchangeHistoryController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @ResponseBody
    public List<User> get(){
        return userRepository.findAll();
    }

}
