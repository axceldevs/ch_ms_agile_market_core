package com.axceldev.model.exceptions.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechnicalErrorMessage {

    TECHNICAL_REST_CLIENT_ERROR("MPT001", "Request timed out while trying to connect to the server"),
    EXTERNAL_MESSAGE_ERROR("MPT002","Failed to receive a valid response from the external service."),
    ERROR_CREATE_PRODUCT("MPT003", "Error creating product");

    private final String code;
    private final String message;
}
