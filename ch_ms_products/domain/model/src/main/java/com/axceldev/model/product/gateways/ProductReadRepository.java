package com.axceldev.model.product.gateways;

import com.axceldev.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProductReadRepository {
    Mono<Product> findByName(String name);
    Mono<Product> findById(Long id);
    Mono<List<Product>> findAllPaged(int size, int page);
    Mono<Long> countAll();
}
