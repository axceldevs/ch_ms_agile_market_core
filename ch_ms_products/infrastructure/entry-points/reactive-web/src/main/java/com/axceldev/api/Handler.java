package com.axceldev.api;

import com.axceldev.api.dto.CreateProductRequest;
import com.axceldev.model.product.Product;
import com.axceldev.usecase.product.ProductUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final ObjectMapper mapper;
    private final ProductUseCase productUseCase;

    public Mono<ServerResponse> createProduct(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateProductRequest.class)
                .switchIfEmpty(Mono.error(() -> new IllegalStateException("Invalid request")))
                .flatMap(request -> productUseCase.createProduct(toData(request)))
                .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    private Product toData(Object request) {
        return mapper.convertValue(request, Product.class);
    }
}
