package com.axceldev.secretsmanager;

import co.com.bancolombia.secretsmanager.api.GenericManager;
import co.com.bancolombia.secretsmanager.connector.AWSSecretManagerConnector;
import com.axceldev.secretsmanager.model.SecretsManagerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@RequiredArgsConstructor
@Configuration
public class SecretsManagerConfig {


    @Bean
    @Profile({"!local"})
    public GenericManager connectionAws(SecretsManagerProperties properties) {
        return new AWSSecretManagerConnector(properties.getRegion());
    }

    @Bean
    @Profile("local")
    public GenericManager connectionLocal(SecretsManagerProperties properties) {
        return new AWSSecretManagerConnector(properties.getRegion(), properties.getEndpoint());
    }

}
