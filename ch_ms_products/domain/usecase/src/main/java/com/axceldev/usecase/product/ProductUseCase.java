package com.axceldev.usecase.product;

import com.axceldev.model.product.Product;
import com.axceldev.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase {

    private final ProductRepository productRepository;

    public Mono<Product> createProduct(Product product) {
        return existsProduct(product.getName())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalStateException("Product already exists"));
                    } else {
                        return productRepository.createProduct(product);
                    }
                })
                .doOnError(e -> System.err.println("Error creating product: " + e.getMessage()));
    }

    private Mono<Boolean> existsProduct(String name) {
        return productRepository.findByName(name)
                .hasElement();
    }
}
