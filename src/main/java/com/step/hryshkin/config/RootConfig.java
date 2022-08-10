package com.step.hryshkin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"com.step.hryshkin.service"
        ,"com.step.hryshkin.dao"})
public class RootConfig {
}
