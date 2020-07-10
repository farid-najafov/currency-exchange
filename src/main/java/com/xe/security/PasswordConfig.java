package com.xe.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;

@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new DelegatingPasswordEncoder("pbkdf2",
                new HashMap<String, PasswordEncoder>(11) {{
                    put("pbkdf2", new org.springframework.security.crypto.password.Pbkdf2PasswordEncoder());
                }}
    );
}
}