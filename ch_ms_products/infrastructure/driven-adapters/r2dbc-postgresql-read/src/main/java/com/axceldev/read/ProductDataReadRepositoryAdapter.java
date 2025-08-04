package com.axceldev.read;

import com.axceldev.model.product.Product;
import com.axceldev.model.product.gateways.ProductReadRepository;
import com.axceldev.read.entity.ProductData;
import com.axceldev.read.helper.ReactiveAdapterReadOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class ProductDataReadRepositoryAdapter extends ReactiveAdapterReadOperations<
        Product,
        ProductData,
        Long,
        ProductDataReadRepository
> implements ProductReadRepository {

    public ProductDataReadRepositoryAdapter(ProductDataReadRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Product.class));
    }

    @Override
    public Mono<Product> findByName(String name) {
        return repository.findByName(name)
                .map(this::toEntity);
    }

    @Override
    public Mono<Product> findById(Long id) {
        return repository.findById(id)
                .map(this::toEntity);
    }

    @Override
    public Mono<List<Product>> findAllPaged(int size, int page) {
        return repository.findAllPaged(size, page)
                .map(this::toEntity)
                .collectList();
    }

    @Override
    public Mono<Long> countAll() {
        return repository.countAll();
    }
}
