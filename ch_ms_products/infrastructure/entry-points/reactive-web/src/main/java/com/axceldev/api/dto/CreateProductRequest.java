package com.axceldev.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CreateProductRequest {

    @NotBlank(message = "Please provide a valid product name. This field cannot be blank.")
    private String name;
    private String description;
    private String category;
    private String brand;
    @PositiveOrZero
    private Double price;
    private Double discount;
    private Integer stock;
    private String unit;
    private Boolean active;
    private String barcode;
    private Instant createdAt;
    private List<String> tags;
    private String imageUrl;
    private String supplierId;
    private Double tax;
    private String location;
    private Boolean featured;
    private String sku;
    private List<String> keywords;
    private String originCountry;
}
