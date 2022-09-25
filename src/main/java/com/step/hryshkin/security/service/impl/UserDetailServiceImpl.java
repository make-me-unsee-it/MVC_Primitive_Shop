package com.step.hryshkin.security.service.impl;

import com.step.hryshkin.dao.UserDAO;
import com.step.hryshkin.security.model.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {


    private UserDAO userDAO;

    private UserDetailServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.getUserByName(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new RuntimeException("User with name " + username + "not found"));
    }
}