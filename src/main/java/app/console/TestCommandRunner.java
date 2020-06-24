package app.console;

import app.entity.Exchange;
import app.entity.User;
import app.repo.ExchangeJpaRepo;
import app.repo.UserJpaRepo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Configuration
public class TestCommandRunner {
    private final ExchangeJpaRepo exchangeJpaRepo;
    private final UserJpaRepo userJpaRepo;

    public TestCommandRunner(ExchangeJpaRepo exchangeJpaRepo, UserJpaRepo userJpaRepo) {
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

            User user1 = new User("Ferid","123","f@mail.ru",f);
            User user2 = new User("Aqil","231","aq@mail.ru",aq);
            User user3 = new User("Samir","321","as@mail.ru",as);
            userJpaRepo.saveAll(Arrays.asList(user1,user2,user3));
        };
    }


}
