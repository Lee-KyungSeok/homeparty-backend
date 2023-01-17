package com.homeparty.invitation;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@SpringBootApplication
@Slf4j
public class TestConfiguration {

    @Value("${homeparty.datasource.main.jdbc-url}")
    private String databaseUrl;

    @Primary
    @Bean
    @ConfigurationProperties("homeparty.datasource.main")
    public DataSource homepartyDataSource() {
        log.info("Configured Database, jdbc-url: {}", databaseUrl);

        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }
}
