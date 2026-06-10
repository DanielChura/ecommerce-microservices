package com.daniel.microservices.product_microservice.category;

import java.util.List;

import com.daniel.microservices.product_microservice.product.ProductResponse;

import lombok.Builder;

@Builder
public record CategoryResponse(
                Integer id,
                String name,
                String description,
                List<ProductResponse> products) {
}
