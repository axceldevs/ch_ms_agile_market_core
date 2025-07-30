package com.axceldev.api.validator;

import com.axceldev.model.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Set;

import static com.axceldev.model.exceptions.message.BusinessErrorMessage.PRODUCT_VALIDATION_ERROR;

@Component
@Validated
@RequiredArgsConstructor
@Slf4j
public class ProductRequestValidator {

    private final Validator validator;

    public <T> Mono<T> validate(T obj) {
        Set<ConstraintViolation<T>> violations = validator.validate(obj);
        if (!violations.isEmpty()) {
            String message = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .reduce("", (s1, s2) -> s1 + s2 + "; ");
            log.error("Error: " + message);
            return Mono.error(() -> new BusinessException(PRODUCT_VALIDATION_ERROR));
        }
        return Mono.just(obj);
    }

}
