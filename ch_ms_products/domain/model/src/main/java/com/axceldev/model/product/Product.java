package com.axceldev.model.product;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Product {
    private Long productId;
    private String name;
    private String description;
    private String category;
    private String brand;
    private Double price;
    private Double discount;
    private Integer stock;
    private String unit;
    private Boolean active;
    private String barcode;
    private Instant createdAt;
    private Instant updatedAt;
    private List<String> tags;
    private String imageUrl;
    private String supplierId;
    private Double tax;
    private String location;
    private Boolean featured;
    private String sku;
    private List<String> keywords;
    private String originCountry;
    private Boolean onSale;
    private Double previousPrice;
    private Instant saleStartDate;
    private Instant saleEndDate;
}
