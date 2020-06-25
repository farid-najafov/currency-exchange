package com.ibatech.project.service;

import com.ibatech.project.entity.User;

public interface UserService {
    void addUser(User user);
    User findByEmail(String email);
}
