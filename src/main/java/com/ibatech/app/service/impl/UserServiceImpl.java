package com.ibatech.app.service.impl;

import com.ibatech.app.entity.User;
import com.ibatech.app.repo.UserRepository;
import com.ibatech.app.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
