package com.axceldev.write;

import com.axceldev.write.entity.ProductData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductDataWriteRepository extends ReactiveCrudRepository<ProductData, Long>, ReactiveQueryByExampleExecutor<ProductData> {
}
