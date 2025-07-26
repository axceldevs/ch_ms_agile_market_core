package com.axceldev.model.exceptions.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessErrorMessage {

    INVALID_REQUEST("MPB001", "Invalid request"),
    PRODUCT_NOT_FOUND("MPB002", "Product not found"),
    PRODUCT_ALREADY_EXISTS("MPB003", "Product already exists"),
    PRODUCT_VALIDATION_ERROR("MPB004", "Product validation error"),
    PRODUCT_FIND_ERROR("MPB005", "Product find error");

    private final String code;
    private final String message;
}
