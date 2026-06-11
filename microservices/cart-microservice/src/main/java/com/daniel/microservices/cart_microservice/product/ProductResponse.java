package com.daniel.microservices.cart_microservice.product;

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
