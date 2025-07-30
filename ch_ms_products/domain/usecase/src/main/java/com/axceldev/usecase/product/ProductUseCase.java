package com.axceldev.usecase.product;

import com.axceldev.model.exceptions.BusinessException;
import com.axceldev.model.exceptions.TechnicalException;
import com.axceldev.model.product.Product;
import com.axceldev.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import static com.axceldev.model.exceptions.message.BusinessErrorMessage.PRODUCT_ALREADY_EXISTS;
import static com.axceldev.model.exceptions.message.TechnicalErrorMessage.ERROR_CREATE_PRODUCT;

@RequiredArgsConstructor
public class ProductUseCase {

    private final ProductRepository productRepository;

    public Mono<Product> createProduct(Product product) {
        return existsProduct(product.getName())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new BusinessException(PRODUCT_ALREADY_EXISTS));
                    } else {
                        return productRepository.createProduct(product);
                    }
                })
                .onErrorMap(this::mapToTechnicalIfNeeded);
    }


    private Mono<Boolean> existsProduct(String name) {
        return productRepository.findByName(name)
                .hasElement();
    }

    private Throwable mapToTechnicalIfNeeded(Throwable error) {
        return (error instanceof BusinessException) ? error :
                new TechnicalException(error, ERROR_CREATE_PRODUCT);
    }
}
