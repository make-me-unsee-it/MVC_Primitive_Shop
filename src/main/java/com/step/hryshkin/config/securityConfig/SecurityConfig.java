package com.step.hryshkin.config.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author dshargaev
 * - don't touch it!
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .headers().frameOptions().disable();

        http
                .authorizeRequests()
                .antMatchers(
                        "/h2/**",
                        "/login",
                        "/new",
                        "/new_success",
                        "/homepage",
                        "/")
                .permitAll()

                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()

                .and()
                .formLogin()
                .defaultSuccessUrl("/shop", true)
                .permitAll();

        http.logout()
                .logoutSuccessUrl("/homepage")
                .logoutUrl("/logout")
                .invalidateHttpSession(Boolean.TRUE)
                .permitAll()

                .and()
                .sessionManagement()
                .invalidSessionUrl("/homepage")
                .maximumSessions(1)
                .expiredUrl("/homepage");
    }
}

