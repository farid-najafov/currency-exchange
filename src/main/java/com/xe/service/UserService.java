package com.xe.service;

import com.xe.entity.User;

public interface UserService {
    void addUser(User user);
    User findByEmail(String email);
}
