package com.axceldev.model.exceptions;

import com.axceldev.model.exceptions.message.BusinessErrorMessage;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final BusinessErrorMessage errorMessage;

    public BusinessException(BusinessErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}
