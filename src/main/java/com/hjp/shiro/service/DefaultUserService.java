package com.hjp.shiro.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjp.shiro.dao.UserDAO;
import com.hjp.shiro.model.User;

import java.util.List;


@Transactional
@Service("userService")
public class DefaultUserService implements UserService {

    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getCurrentUser() {
        final Long currentUserId = (Long) SecurityUtils.getSubject().getPrincipal();
        if( currentUserId != null ) {
            return getUser(currentUserId);
        } else {
            return null;
        }
    }

    public void createUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword( new Sha256Hash(password).toHex() );
        userDAO.createUser( user );
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUser(Long userId){
    	 return userDAO.getUser(userId);
    }

    public void deleteUser(Long userId) {
        userDAO.deleteUser( userId );
    }

    public void updateUser(User user) {
        userDAO.updateUser( user );
    }

}
