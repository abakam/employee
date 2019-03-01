package com.abrahamakam.spring.assignment.config;

import com.abrahamakam.spring.assignment.factory.EmployeeFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnitTestConfig {

    @Bean
    public EmployeeFactory employeeFactory() {
        return new EmployeeFactory();
    }
}
