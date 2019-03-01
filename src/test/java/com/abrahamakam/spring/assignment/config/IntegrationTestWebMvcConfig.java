package com.abrahamakam.spring.assignment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.abrahamakam.spring.assignment")
public class IntegrationTestWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private Environment env;

    /*
     * We only need to specify the database url. All other configurations
     * in the #AppConfig class remains the same
     */
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("jdbc.test.url"));
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("jdbc.driverClassName")));

        return dataSource;
    }
}
