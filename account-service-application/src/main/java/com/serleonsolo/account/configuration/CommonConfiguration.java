package com.serleonsolo.account.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = "serleonsolo.account-management-service")
public class CommonConfiguration {

    private boolean cleanInstall = false;

    @Bean(name = "isCleanInstall")
    public boolean isCleanInstall() {
        return cleanInstall;
    }

    public void setCleanInstall(boolean cleanInstall) {
        this.cleanInstall = cleanInstall;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Autowired DataSource dbaasPostgresDataSource) {
        return new JdbcTemplate(dbaasPostgresDataSource);
    }
}
