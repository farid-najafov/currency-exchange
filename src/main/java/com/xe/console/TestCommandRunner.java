package com.xe.console;

import com.xe.entity.Exchange;
import com.xe.entity.User;
import com.xe.repo.ExchangeJpaRepo;
import com.xe.repo.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Configuration
public class TestCommandRunner {
    private final ExchangeJpaRepo exchangeJpaRepo;
    private final UserRepository userJpaRepo;

    public TestCommandRunner(ExchangeJpaRepo exchangeJpaRepo, UserRepository userJpaRepo) {
        this.exchangeJpaRepo = exchangeJpaRepo;
        this.userJpaRepo = userJpaRepo;
    }

    @Bean
    public CommandLineRunner autoRun2() {

        return args -> {
            List<Exchange> f =new ArrayList<>();
            f.add(new Exchange("67"));
            f.add(new Exchange("32"));
            List<Exchange> aq =new ArrayList<>();
            aq.add(new Exchange("123"));
            aq.add(new Exchange("3342"));
            List<Exchange> as =new ArrayList<>();
            as.add(new Exchange("12"));
            as.add(new Exchange("435"));

            User user1 = new User("Ferid","1","1","f@mail.ru",f);
            User user2 = new User("Aqil","1","1","aqilzeka99@gmail.com",aq);
            User user3 = new User("Samir","1","1","as@mail.ru",as);
            userJpaRepo.saveAll(Arrays.asList(user1,user2,user3));
        };
    }


}
