package com.hjp.shiro.dao;

import java.util.List;

import com.hjp.shiro.model.User;

public interface UserDAO {

    User getUser(Long userId);

    User findUser(String username);

    void createUser(User user);

    List<User> getAllUsers();

    void deleteUser(Long userId);

    void updateUser(User user);
}