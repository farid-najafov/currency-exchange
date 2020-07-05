package com.xe.service;

import com.xe.entity.Exchange;
import com.xe.entity.User;
import com.xe.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void addExchangeTest(long id, Exchange exchange) {
        User user = userRepository.getOne(id);
        user.getExchanges().add(exchange);
        addUser(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public void updatePassword(String password, Long userId) {
        userRepository.updatePassword(password, userId);
    }


}
