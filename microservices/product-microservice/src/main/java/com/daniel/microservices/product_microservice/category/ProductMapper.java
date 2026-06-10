package com.daniel.microservices.product_microservice.category;

import com.daniel.microservices.product_microservice.product.Product;
import com.daniel.microservices.product_microservice.product.ProductResponse;

public class ProductMapper {

    public static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .imageUrl(product.getImageUrl())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .build();
    }
}
