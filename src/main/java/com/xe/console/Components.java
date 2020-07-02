package com.xe.console;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Components {

    @Bean
    public RestTemplate build(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

}
