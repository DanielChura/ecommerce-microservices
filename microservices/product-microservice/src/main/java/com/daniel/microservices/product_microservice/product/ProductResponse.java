package com.daniel.microservices.product_microservice.product;

import lombok.Builder;

@Builder
public record ProductResponse(
        Integer id,
        String name,
        String description,
        double price,
        int stock,
        String imageUrl,
        Integer categoryId,
        String categoryName) {
}
