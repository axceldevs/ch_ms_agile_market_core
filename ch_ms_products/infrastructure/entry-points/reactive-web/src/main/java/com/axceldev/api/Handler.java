package com.axceldev.api;

import com.axceldev.api.dto.CreateProductRequest;
import com.axceldev.api.validator.ProductRequestValidator;
import com.axceldev.model.exceptions.BusinessException;
import com.axceldev.model.product.Product;
import com.axceldev.usecase.product.ProductUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.axceldev.model.exceptions.message.BusinessErrorMessage.INVALID_REQUEST;

@Component
@RequiredArgsConstructor
public class Handler {

    private final ObjectMapper mapper;
    private final ProductUseCase productUseCase;
    private final ProductRequestValidator validator;

    public Mono<ServerResponse> createProduct(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateProductRequest.class)
                .flatMap(validator::validate)
                .flatMap(request -> productUseCase.createProduct(toData(request)))
                .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    private Product toData(Object request) {
        return mapper.convertValue(request, Product.class);
    }
}
