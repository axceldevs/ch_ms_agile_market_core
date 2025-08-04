package com.axceldev.model.product.gateways;

import com.axceldev.model.product.Product;
import reactor.core.publisher.Mono;

public interface ProductWriteRepository {
    Mono<Product> createProduct(Product product);
}
