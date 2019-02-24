package com.abrahamakam.spring.assignment.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:persistence.properties"})
@ComponentScan({"com.abrahamakam.spring.assignment.persistence", "com.abrahamakam.spring.assignment.api.controller"})
public class TestConfig {

    private Environment env;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setHibernateProperties(hibernateProperties());
        sessionFactory.setPackagesToScan("com.abrahamakam.spring.assignment.persistence.model");
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("jdbc.driverClassName")));

        return dataSource;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }

    @Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }

    private Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.globally_quoted_identifiers", "true");
                setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
                setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
                setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
            }
        };
    }
}
