package com.axceldev.api.exceptions;

import com.axceldev.model.exceptions.BusinessException;
import com.axceldev.model.exceptions.TechnicalException;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;

@Order(-2)
@Component
public class ExceptionHandler extends AbstractErrorWebExceptionHandler {

    public ExceptionHandler(ErrorAttributes errorAttributes, WebProperties resources,
                            ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resources.getResources(), applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
       return Mono.error(getError(request))
               .onErrorResume(BusinessException.class,
                       errorBusiness ->
                               ServerResponse.badRequest()
                               .bodyValue(Map.of(
                                       "code", errorBusiness.getErrorMessage().getCode(),
                                       "message", errorBusiness.getErrorMessage().getMessage(),
                                       "timestamp", Instant.now().toString()
                               ))
               ).onErrorResume(TechnicalException.class, errorTechnical ->
                       ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                       .bodyValue(Map.of(
                                       "code", errorTechnical.getErrorMessage().getCode(),
                                       "message", errorTechnical.getErrorMessage().getMessage(),
                                       "timestamp", Instant.now().toString()
                               ))
               ).onErrorResume( error -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                       .build()
               )
               .cast(ServerResponse.class);
    }
}
