package com.axceldev.read;

import com.axceldev.read.entity.ProductData;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductDataReadRepository extends ReactiveCrudRepository<ProductData, Long>, ReactiveQueryByExampleExecutor<ProductData> {
    Mono<ProductData> findByName(String name);
    @Query("SELECT p.product_id, p.name, p.description, p.category, p.brand, p.price, p.discount, p.stock, p.unit, p.active, p.barcode, p.created_at, p.updated_at, p.tags, p.image_url, p.supplier_id, p.tax, p.location, p.featured, p.sku, p.keywords, p.origin_country, p.on_sale, p.previous_price, p.sale_start_date, p.sale_end_date FROM sch_product.products p LIMIT :size OFFSET GREATEST((:page - 1) * :size, 0);")
    Flux<ProductData> findAllPaged(int size, int offset);
    @Query("SELECT COUNT(*) FROM sch_product.products")
    Mono<Long> countAll();
}
