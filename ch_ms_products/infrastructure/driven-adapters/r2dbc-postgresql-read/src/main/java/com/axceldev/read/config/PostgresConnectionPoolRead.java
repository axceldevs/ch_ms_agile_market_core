package com.axceldev.read.config;

import co.com.bancolombia.secretsmanager.api.GenericManager;
import com.axceldev.management.config.WrappedConnectionPool;
import com.axceldev.secretsmanager.SecretsHelper;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@Slf4j
@EnableR2dbcRepositories(entityOperationsRef = "entityOperationsRead",
        basePackages = "com.axceldev.read")
public class PostgresConnectionPoolRead extends
        SecretsHelper<PostgresqlConnectionPropertiesRead, PostgresqlConnectionConfiguration> {

    public PostgresConnectionPoolRead(@Value("${secrets.postgresql}") final String secretName) {
        super(PostgresqlConnectionPropertiesRead.class, secretName);
    }

    @Bean
    public R2dbcEntityOperations entityOperationsRead(@Qualifier("connectionFactoryRead")
                                                      ConnectionFactory connectionFactory) {
        return new R2dbcEntityTemplate(connectionFactory);
    }

    @Bean
    public ConnectionFactory connectionFactoryRead(@Qualifier("getConnectionConfigRead")
                                                   PostgresqlConnectionConfiguration connectionConfiguration) {
        var pool = ConnectionPoolConfiguration
                .builder(new PostgresqlConnectionFactory(connectionConfiguration))
                .build();
        return new WrappedConnectionPool(pool);
    }

    @Bean
    @SneakyThrows
    public PostgresqlConnectionConfiguration getConnectionConfigRead(final GenericManager manager) {
        return createConfigFromSecret(manager, this::buildConnectionConfiguration);
    }

    public PostgresqlConnectionConfiguration buildConnectionConfiguration(
            PostgresqlConnectionPropertiesRead properties) {
        return PostgresqlConnectionConfiguration.builder()
                .host(properties.getHostro() != null ? properties.getHostro() : properties.getHost())
                .port(properties.getPort())
                .database(properties.getDbname())
                .username(properties.getUsername())
                .password(properties.getPassword())
                .build();
    }
}