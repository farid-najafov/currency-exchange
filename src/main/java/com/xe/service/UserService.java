package com.xe.service;

import com.xe.entity.User;
import com.xe.entity.api.Exchange;
import com.xe.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder enc;


    public UserService(UserRepository userRepository, PasswordEncoder enc) {
        this.userRepository = userRepository;
        this.enc = enc;
    }

    public void addUser(User user) {
        String encode = enc.encode(user.getPassword());
        user.setPassword(encode);
        user.setMatchingPassword(encode);
        userRepository.save(user);
    }

    public void addExchange(String email, Exchange ex) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        User user = byEmail.orElseThrow(RuntimeException::new);
        user.getExchanges().add(ex);
        addUser(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }



    public void updatePassword(String password, Long userId) {
        userRepository.updatePassword(enc.encode(password), userId);
    }

}
