package com.step.hryshkin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
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

                //TODO убрать комменты. Этот блок устанавливает страницы с открытым доступом для всех
                .authorizeRequests()
                .antMatchers("/login", "/new", "h2/*")
                .permitAll()

                .and()
                .authorizeRequests()
                //.antMatchers("/shop")
                //.hasRole("USER")
                .anyRequest()
                .authenticated()

                .and()
                .formLogin()
                //.loginPage("/login")
                //.loginProcessingUrl("/login")
                //.failureUrl("/404")
                .defaultSuccessUrl("/shop")
                //.passwordParameter("password")
                //.usernameParameter("email")
                .permitAll()
                .and()

                .logout()
                .logoutSuccessUrl("/login")
                .logoutUrl("/logout")
                .invalidateHttpSession(Boolean.TRUE)
                .permitAll();
                //.and()
                //.sessionManagement()
                //.invalidSessionUrl("/welcome")
                //.maximumSessions(1)
                //.expiredUrl("/welcome");
    }
}

