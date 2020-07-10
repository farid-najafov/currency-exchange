package com.xe.config;

import com.xe.entity.User;
import com.xe.entity.api.Exchange;
import com.xe.enums.XCurrency;
import com.xe.repo.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class TestInitialUser {

    private final UserRepository repo;
    private final PasswordEncoder enc;

    public TestInitialUser(UserRepository repo, PasswordEncoder enc) {
        this.repo = repo;
        this.enc = enc;
    }

    @Bean
    public void create() {
        List<Exchange> f = new ArrayList<>();
        f.add(new Exchange(XCurrency.EUR, XCurrency.BGN, 3434.2, null));
        f.add(new Exchange(XCurrency.CHF, XCurrency.BGN, 123.1, null));

        List<Exchange> aq = new ArrayList<>();
        aq.add(new Exchange(XCurrency.EUR, XCurrency.BGN, 3434.2, null));
        aq.add(new Exchange(XCurrency.EUR, XCurrency.BGN, 3434.2, null));


        List<Exchange> as = new ArrayList<>();
        as.add(new Exchange(XCurrency.EUR, XCurrency.BGN, 3434.2, null));
        as.add(new Exchange(XCurrency.EUR, XCurrency.BGN, 3434.2, null));

        String encode = enc.encode("111");
        String encode2 = enc.encode("222");
        String encode3 = enc.encode("333");

        User user1 = new User("Ferid", encode, encode, "farid.r.najafov@gmail.com", f, "USER");
        User user2 = new User("Aqil", encode2, encode2, "aqil99@gmail.com", aq, "USER");
        User user3 = new User("Samir", encode3, encode3, "s@mail.ru", as, "USER");
        repo.saveAll(Arrays.asList(user1, user2, user3));
    }
}
