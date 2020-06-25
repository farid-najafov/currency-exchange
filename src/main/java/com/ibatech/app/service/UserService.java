package com.ibatech.app.service;

import com.ibatech.app.entity.User;

public interface UserService {
    void addUser(User user);
    User findByEmail(String email);
}
