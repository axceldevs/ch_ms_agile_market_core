package com.axceldev.r2dbc;

import com.axceldev.r2dbc.entity.ProductData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductDataRepository extends ReactiveCrudRepository<ProductData, Long>, ReactiveQueryByExampleExecutor<ProductData> {
    Mono<ProductData> findByName(String name);
}
