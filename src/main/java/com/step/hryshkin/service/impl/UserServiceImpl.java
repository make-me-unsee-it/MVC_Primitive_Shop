package com.step.hryshkin.service.impl;

import com.step.hryshkin.dao.UserDAO;
import com.step.hryshkin.model.User;
import com.step.hryshkin.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public void createNewUser(User user) {
        String encodedPassword = new BCryptPasswordEncoder(4).encode(user.getPassword());
        User newEncodedUser = new User(user.getUserName(), encodedPassword);
        userDAO.createNewUser(newEncodedUser);
    }

    @Override
    @Transactional
    public Optional<User> getUserByName(String userName) {
        return userDAO.getUserByName(userName);
    }
}
