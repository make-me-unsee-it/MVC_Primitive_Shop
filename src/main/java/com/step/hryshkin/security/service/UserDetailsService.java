package com.step.hryshkin.security.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {

    UserDetails loadUserByUsername(String name);
}
