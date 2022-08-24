package com.step.hryshkin.service.impl;

import com.step.hryshkin.dao.UserDAO;
import com.step.hryshkin.model.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format; //TODO проверить, правильный ли импорт выбран

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDAO userDAO;

    public UserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDAO.getUserByName(s)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(format("User with name {} is no found", s)));
    }
}
