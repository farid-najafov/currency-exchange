package com.xe.console;

import com.xe.entity.Exchange;
import com.xe.entity.User;
import com.xe.repo.ExchangeRepository;
import com.xe.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class TestCommandRunner {
    private final ExchangeRepository exchangeRepository;
    private final UserRepository userRepository;

    public TestCommandRunner(ExchangeRepository exchangeRepository, UserRepository userRepository) {
        this.exchangeRepository = exchangeRepository;
        this.userRepository = userRepository;
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

            User user1 = new User("Ferid","111", "111", "f@mail.ru", f);
            User user2 = new User("Aqil","222", "222", "aqil99@gmail.com", aq);
            User user3 = new User("Samir","333", "333", "s@mail.ru", as);
            userRepository.saveAll(Arrays.asList(user1,user2,user3));
        };
    }
}
