package com.hashbus.back.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
public class ApplicationConfiguration {
    private static final String driverClassName = "com.mysql.jdbc.Driver";
    @Value("${url}")
    private static final String URL = "jdbc:mysql://google/hashbus?cloudSqlInstance=model-arcadia-405711:us-central1:hashbus-db&socketFactory=com.google.cloud.sql.mysql.SocketFactory";
    private static final String dbUsername = "root";
    private static final String dbPassword = "deya@12@";
    @Bean
    public JdbcTemplate getJdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(getDataSource());
        return jdbcTemplate;
    }
    @Bean
    public static DriverManagerDataSource getDataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(driverClassName);

        dataSource.setUrl(URL);

        dataSource.setUsername(dbUsername);

        dataSource.setPassword(dbPassword);

        return dataSource;
    }

}
