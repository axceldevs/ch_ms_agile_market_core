package com.axceldev.r2dbc;

import com.axceldev.model.product.Product;
import com.axceldev.model.product.gateways.ProductRepository;
import com.axceldev.r2dbc.helper.ReactiveAdapterOperations;
import com.axceldev.r2dbc.entity.ProductData;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Repository
public class ProductDataRepositoryAdapter extends ReactiveAdapterOperations<
        Product,
        ProductData,
        Long,
        ProductDataRepository
> implements ProductRepository {

    public ProductDataRepositoryAdapter(ProductDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Product.class));
    }

    @Override
    public Mono<Product> createProduct(Product product) {
        return repository.save(bulidProductData(product))
                .map(this::toEntity);
    }

    @Override
    public Mono<Product> findByName(String name) {
        return repository.findByName(name)
                .map(this::toEntity);
    }

    private ProductData bulidProductData(Product product) {
        return ProductData.builder()
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .brand(product.getBrand())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .stock(product.getStock())
                .unit(product.getUnit())
                .active(product.getActive())
                .barcode(product.getBarcode())
                .createdAt(Instant.now())
                .tags(product.getTags())
                .imageUrl(product.getImageUrl())
                .supplierId(product.getSupplierId())
                .tax(product.getTax())
                .location(product.getLocation())
                .featured(product.getFeatured())
                .sku(product.getSku())
                .keywords(product.getKeywords())
                .originCountry(product.getOriginCountry())
                .build();
    }
}
