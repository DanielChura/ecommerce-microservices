package com.daniel.microservices.product_microservice.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductRequest(
        Integer id,
        @NotBlank(message = "Name is required") String name,
        String description,
        @Positive(message = "Price must be positive") double price,
        @PositiveOrZero(message = "Stock must be zero or positive") int stock,
        String imageUrl,
        @Positive(message = "Category is required") Integer categoryId) {
}
