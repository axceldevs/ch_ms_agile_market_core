package com.axceldev.read.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "sch_product.products")
public class ProductData {
    @Id
    @Column(value = "product_id")
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
    @Column(value = "created_at")
    private Instant createdAt;
    @Column(value = "updated_at")
    private Instant updatedAt;
    private List<String> tags;
    @Column(value = "image_url")
    private String imageUrl;
    @Column(value = "supplier_id")
    private String supplierId;
    private Double tax;
    private String location;
    private Boolean featured;
    private String sku;
    private List<String> keywords;
    @Column(value = "origin_country")
    private String originCountry;
    @Column(value = "on_sale")
    private Boolean onSale;
    @Column(value = "previous_price")
    private Double previousPrice;
    @Column(value = "sale_start_date")
    private Instant saleStartDate;
    @Column(value = "sale_end_date")
    private Instant saleEndDate;
}
