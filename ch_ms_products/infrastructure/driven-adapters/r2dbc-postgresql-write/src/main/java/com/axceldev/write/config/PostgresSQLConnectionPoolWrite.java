package com.axceldev.write.config;

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
@EnableR2dbcRepositories(entityOperationsRef = "entityOperationsWrite",
        basePackages = "com.axceldev.write")
public class PostgresSQLConnectionPoolWrite extends
        SecretsHelper<PostgresSQLConnectionPropertiesWrite, PostgresqlConnectionConfiguration> {

    public PostgresSQLConnectionPoolWrite(@Value("${secrets.postgresql}") final String secretName) {
        super(PostgresSQLConnectionPropertiesWrite.class, secretName);
    }

    @Bean
    public R2dbcEntityOperations entityOperationsWrite(@Qualifier("connectionFactoryWrite")
                                                      ConnectionFactory connectionFactory) {
        return new R2dbcEntityTemplate(connectionFactory);
    }

    @Bean
    public ConnectionFactory connectionFactoryWrite(@Qualifier("getConnectionConfigWrite")
                                                   PostgresqlConnectionConfiguration connectionConfiguration) {
        var pool = ConnectionPoolConfiguration
                .builder(new PostgresqlConnectionFactory(connectionConfiguration))
                .build();
        return new WrappedConnectionPool(pool);
    }

    @Bean
    @SneakyThrows
    public PostgresqlConnectionConfiguration getConnectionConfigWrite(final GenericManager manager) {
        return createConfigFromSecret(manager, this::buildConnectionConfiguration);
    }

    public PostgresqlConnectionConfiguration buildConnectionConfiguration(
            PostgresSQLConnectionPropertiesWrite properties) {
        return PostgresqlConnectionConfiguration.builder()
                .host(properties.getHostro() != null ? properties.getHostro() : properties.getHost())
                .port(properties.getPort())
                .database(properties.getDbname())
                .username(properties.getUsername())
                .password(properties.getPassword())
                .build();
    }
}