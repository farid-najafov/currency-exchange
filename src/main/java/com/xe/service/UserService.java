package com.xe.service;

import com.xe.entity.User;
import com.xe.entity.api.Exchange;
import com.xe.entity.sec_ent.XUserDetails;
import com.xe.repo.UserRepository;
import lombok.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Value
@Service
public class UserService {

    UserRepository userRepository;
    PasswordEncoder enc;

    public void addUser(User user) {
        String encode = enc.encode(user.getPassword());
        user.setPassword(encode);
        user.setMatchingPassword(encode);
        user.setRoles("USER");
        userRepository.save(user);
    }

    public void addExchange(String email, Exchange ex) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        User user = byEmail.orElseThrow(RuntimeException::new);
        user.getExchanges().add(ex);
        userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void updatePassword(String password, Long userId) {
        userRepository.updatePassword(enc.encode(password), userId);
    }

    public static String getUserNameFromPrincipal(Principal p) {

        if (p instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken user = (OAuth2AuthenticationToken) p;
            return user.getPrincipal().getAttribute("name");
        } else {
            UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) p;
            XUserDetails xUserDetails = (XUserDetails) user.getPrincipal();
            return xUserDetails.getFullName();
        }
    }
}
