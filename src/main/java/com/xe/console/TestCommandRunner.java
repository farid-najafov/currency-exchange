package com.xe.console;


import com.xe.entity.User;
import com.xe.entity.api.Exchange;
import com.xe.repo.UserRepository;
import com.xe.enums.XCurrency;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class TestCommandRunner {
    private final UserRepository userRepository;

    public TestCommandRunner( UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public CommandLineRunner autoRun2() {

        return args -> {
            List<Exchange> f =new ArrayList<>();
            f.add(new Exchange(XCurrency.EUR,XCurrency.BGN,3434.2, null));
            f.add(new Exchange(XCurrency.CHF,XCurrency.BGN,123.1, null));

            List<Exchange> aq =new ArrayList<>();
            aq.add(new Exchange(XCurrency.EUR,XCurrency.BGN,3434.2, null));
            aq.add(new Exchange(XCurrency.EUR,XCurrency.BGN,3434.2, null));

            List<Exchange> as =new ArrayList<>();
            as.add(new Exchange(XCurrency.EUR,XCurrency.BGN,3434.2, null));
            as.add(new Exchange(XCurrency.EUR,XCurrency.BGN,3434.2, null));

            User user1 = new User("Ferid","111", "111", "f@mail.ru", f);
            User user2 = new User("Aqil","222", "222", "aqil99@gmail.com", aq);
            User user3 = new User("Samir","333", "333", "s@mail.ru", as);
            userRepository.saveAll(Arrays.asList(user1,user2,user3));
        };
    }
}
