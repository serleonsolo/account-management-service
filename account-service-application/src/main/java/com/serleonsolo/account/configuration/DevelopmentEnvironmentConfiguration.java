package com.serleonsolo.account.configuration;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Profile("development")
@Configuration
public class DevelopmentEnvironmentConfiguration {

    @Value("${account.install-database.datasource.server-name}")
    private String serverName;

    @Value("${account.install-database.datasource.user}")
    private String user;

    @Value("${account.install-database.datasource.password}")
    private String password;

    @Value("${account.install-database.datasource.database-name}")
    private String databaseName;

    @Value("${account.install-database.datasource.schema}")
    private String schema;

    @Value("${account.install-database.datasource.repair-install:false}")
    private boolean repairInstall;

    @Bean
    DataSource dbaasPostgresDataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerName(serverName);
        ds.setDatabaseName(databaseName);
        ds.setUser(user);
        ds.setPassword(password);
        return ds;
    }

    @Bean(value = "flyway")
    @DependsOn(value = "dbaasPostgresDataSource")
    Flyway createFlyway(@Qualifier(value = "isCleanInstall") boolean isCleanInstall) {
        Flyway flyway = Flyway.configure()
                .dataSource(dbaasPostgresDataSource())
                .baselineOnMigrate(true)
                .encoding("UTF-8")
                .schemas(schema)
                .cleanOnValidationError(true)
                .locations("classpath:db.migration.postgresql")
                .load();
        if (isCleanInstall) {
            flyway.clean();
        } else if (repairInstall) {
            flyway.repair();
        }
        flyway.migrate();
        return flyway;
    }
}
