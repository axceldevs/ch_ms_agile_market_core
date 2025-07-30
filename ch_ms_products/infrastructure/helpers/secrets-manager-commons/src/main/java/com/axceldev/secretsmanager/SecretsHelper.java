package com.axceldev.secretsmanager;

import co.com.bancolombia.secretsmanager.api.GenericManager;
import co.com.bancolombia.secretsmanager.api.exceptions.SecretException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class SecretsHelper<T, R> {

    private static final String FAIL_MSG = "Secret {} cannot be loaded";
    private static final String OK_MSG = "Secret {} loaded successfully";

    private static final Logger LOGGER = LoggerFactory.getLogger(SecretsHelper.class.getName());

    protected String secretName;
    protected Class<T> clazz;

    protected SecretsHelper(Class<T> clazz, String secretName) {
        this.clazz = clazz;
        this.secretName = secretName;
    }

    protected R createConfigFromSecret(GenericManager manager, Function<T, R> configMaker) throws SecretException {
        try {
            LOGGER.info(OK_MSG, secretName);
            return configMaker.apply(manager.getSecret(secretName, clazz));
        } catch (SecretException exception) {
            throw new SecretException(String.format(FAIL_MSG,secretName));
        }
    }

}
