package com.hjp.shiro.service;

import java.util.List;

import com.hjp.shiro.model.User;

public interface UserService {

    User getCurrentUser();

    void createUser(String username, String email, String password);

    List<User> getAllUsers();

    User getUser(Long userId);

    void deleteUser(Long userId);

    void updateUser(User user);
}
