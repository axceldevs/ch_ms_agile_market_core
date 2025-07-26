package com.axceldev.model.exceptions;

import com.axceldev.model.exceptions.message.TechnicalErrorMessage;
import lombok.Getter;

@Getter
public class TechnicalException extends RuntimeException {

    private TechnicalErrorMessage errorMessage;

    public TechnicalException(Throwable cause, TechnicalErrorMessage message) {
        super(message.getMessage(), cause);
        this.errorMessage = message;
    }
}
