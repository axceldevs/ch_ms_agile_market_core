package com.axceldev.usecase.product;

import com.axceldev.model.exceptions.BusinessException;
import com.axceldev.model.exceptions.TechnicalException;
import com.axceldev.model.product.Product;
import com.axceldev.model.product.gateways.ProductReadRepository;
import com.axceldev.model.product.gateways.ProductWriteRepository;
import com.axceldev.model.shared.PageResult;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import static com.axceldev.model.exceptions.message.BusinessErrorMessage.PRODUCT_ALREADY_EXISTS;
import static com.axceldev.model.exceptions.message.BusinessErrorMessage.PRODUCT_NOT_FOUND;
import static com.axceldev.model.exceptions.message.TechnicalErrorMessage.ERROR_CREATE_PRODUCT;

@RequiredArgsConstructor
public class ProductUseCase {

    private final ProductReadRepository productReadRepository;
    private final ProductWriteRepository productWriteRepository;

    public Mono<Product> createProduct(Product product) {
        return existsProduct(product.getName())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new BusinessException(PRODUCT_ALREADY_EXISTS));
                    } else {
                        return productWriteRepository.createProduct(product);
                    }
                })
                .onErrorMap(this::mapToTechnicalIfNeeded);
    }

    public Mono<Product> getProductById(Long productId) {
        return Mono.just(productId)
                .flatMap(productReadRepository::findById)
                .switchIfEmpty(Mono.error(new BusinessException(PRODUCT_NOT_FOUND)));
    }

    public Mono<PageResult<Product>> getAllProducts(int size, int page) {
        return Mono.zip(
                productReadRepository.findAllPaged(size, page), // Mono<List<Product>>
                productReadRepository.countAll() // Mono<Long>
        ).map(tuple -> PageResult.<Product>builder()
                .items(tuple.getT1()) // List<Product>
                .size(size)
                .page(page)
                .totalItems(tuple.getT2())
                .totalPages((int) Math.ceil((double) tuple.getT2() / size))
                .build()
        );
    }

    private Mono<Boolean> existsProduct(String name) {
        return productReadRepository.findByName(name)
                .hasElement();
    }

    private Throwable mapToTechnicalIfNeeded(Throwable error) {
        return (error instanceof BusinessException) ? error :
                new TechnicalException(error, ERROR_CREATE_PRODUCT);
    }
}
