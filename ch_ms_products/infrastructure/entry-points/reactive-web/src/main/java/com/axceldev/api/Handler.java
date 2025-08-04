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

import java.util.Optional;

import static com.axceldev.model.exceptions.message.BusinessErrorMessage.INVALID_REQUEST;

@Component
@RequiredArgsConstructor
public class Handler {

    public static final int PAGE_DEFAULT = 1;
    public static final int SIZE_DEFAULT = 10;

    public static final String PAGE_VALUE = "page";
    public static final String SIZE_VALUE = "size";

    private final ObjectMapper mapper;
    private final ProductUseCase productUseCase;
    private final ProductRequestValidator validator;

    public Mono<ServerResponse> createProduct(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateProductRequest.class)
                .flatMap(validator::validate)
                .flatMap(request -> productUseCase.createProduct(toData(request)))
                .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    public Mono<ServerResponse> getProductById(ServerRequest serverRequest) {
        Long productId = Long.valueOf(serverRequest.pathVariable("productId"));
        return productUseCase.getProductById(productId)
                .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    public Mono<ServerResponse> getAllProducts(ServerRequest serverRequest) {
        int page = serverRequest.queryParam(PAGE_VALUE)
                .map(Integer::parseInt)
                .orElse(PAGE_DEFAULT);

        int size = serverRequest.queryParam(SIZE_VALUE)
                .map(Integer::parseInt)
                .orElse(SIZE_DEFAULT);

        return productUseCase.getAllProducts(size, page)
                .flatMap(products -> ServerResponse.ok().bodyValue(products));
    }

    private Product toData(Object request) {
        return mapper.convertValue(request, Product.class);
    }
}
