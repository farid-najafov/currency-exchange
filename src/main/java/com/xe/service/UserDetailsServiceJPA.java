package com.xe.service;

import com.xe.entity.User;
import com.xe.entity.sec_ent.XUserDetails;
import com.xe.repo.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserDetailsServiceJPA implements UserDetailsService {
    private final UserRepository repo;

    public UserDetailsServiceJPA(UserRepository repo) {
        this.repo = repo;
    }

    public static XUserDetails mapper_to_XUser(User user) {
        return new XUserDetails(
                user.getId(),
                user.getFullName(),
                user.getPassword(),
                user.getEmail(),
                user.getExchanges(),
                user.getRoles()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return repo.findByEmail(s).
                map(UserDetailsServiceJPA::mapper_to_XUser)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User: %s isn't found in our DB with that mail", s)
                ));
    }

}
