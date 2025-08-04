package com.axceldev.read;

import com.axceldev.read.entity.ProductData;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface ProductDataReadRepository extends ReactiveCrudRepository<ProductData, Long>, ReactiveQueryByExampleExecutor<ProductData> {
    Mono<ProductData> findByName(String name);
    @Query("SELECT * FROM sch_product.products LIMIT :size OFFSET :offset")
    Flux<ProductData> findAllPaged(int size, int offset);
    @Query("SELECT COUNT(*) FROM sch_product.products")
    Mono<Long> countAll();
}
