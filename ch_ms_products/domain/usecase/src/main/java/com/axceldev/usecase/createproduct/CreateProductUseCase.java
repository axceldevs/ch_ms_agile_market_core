package com.axceldev.usecase.createproduct;

import com.axceldev.model.product.Product;
import com.axceldev.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateProductUseCase {

    private final ProductRepository productRepository;

    public Mono<Product> createProduct(Product product) {
        return existsProduct(product.getProductId())
                .flatMap(existsProduct -> productRepository.createProduct(product));
    }

    private Mono<Boolean> existsProduct(Long productId) {
     return productRepository.getProductById(productId)
             .flatMap(product -> Mono.just(true))
             .switchIfEmpty(Mono.just(false));
    }
}
