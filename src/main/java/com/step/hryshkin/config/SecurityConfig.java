package com.step.hryshkin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        http.cors().disable();
        http.csrf().disable();

        //TODO КАМЕНТЫ. БЕЗ ЭТОГО НЕ РАБОТАЕТ H2 - НЕ МАЦАТЬ!!!
        http.headers().frameOptions().disable();
        http.authorizeRequests().antMatchers("/h2/**").permitAll();

        //TODO КАМЕНТЫ. Страницы с открытым доступом для всех
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/new").permitAll();
        http.authorizeRequests().antMatchers("/new_success").permitAll();
        //http.authorizeRequests().antMatchers().permitAll();
        //http.authorizeRequests().antMatchers("/").permitAll();
        //http.authorizeRequests().antMatchers("/403").permitAll();
        //http.authorizeRequests().antMatchers("/404").permitAll();

        //TODO КАМЕНТЫ. Страницы, доступные для пользователей! Этот метод нужен!
        //http.authorizeRequests().antMatchers("/super").hasRole("USER")
        //        .anyRequest().authenticated();

        //TODO КАМЕНТЫ. НЕ РАБОТАЕТ!
        http
                .formLogin()
                //.loginPage("/login")
                //.loginProcessingUrl("/super")
                //.failureUrl("/403")
                .defaultSuccessUrl("/super", true)
                //.successForwardUrl("/super")
                //.passwordParameter("password")
                //.usernameParameter("email")
                .permitAll();

        //TODO КАМЕНТЫ! Этот кусок конфига отвечает за работу URL "logout" на html страницах
        http.logout()
                .logoutSuccessUrl("/login")
                .logoutUrl("/logout")
                .invalidateHttpSession(Boolean.TRUE)
                .permitAll();

        //TODO КАМЕНТЫ! Отправляем на 404, устанавливаем количество устройств и еще что-то
        //TODO КАМЕНТЫ! Последняя строчка - адрес, куда нас перенаправит после истечения сессии
        // http.sessionManagement()
        //       .invalidSessionUrl("/404")
        //      .maximumSessions(1)
        //      .expiredUrl("/login");
    }
}

