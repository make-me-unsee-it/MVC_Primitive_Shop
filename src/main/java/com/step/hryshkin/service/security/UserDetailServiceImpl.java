package com.step.hryshkin.service.security;

import com.step.hryshkin.dao.UserDAO;
import com.step.hryshkin.model.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailsService {

    private final UserDAO userDAO;

    public UserDetailServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userDAO.getUserByName(name)
                .map(CustomUserDetails::new)
                .orElseThrow(()->new RuntimeException("User not found with name " + name));
    }
}
