package com.daniel.microservices.product_microservice.product;

import org.springframework.stereotype.Component;

import com.daniel.microservices.product_microservice.category.Category;

@Component
public class ProductMapper {

    public Product toProduct(ProductRequest request, Category category) {
        return Product.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stock(request.stock())
                .imageUrl(request.imageUrl())
                .category(category)
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
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

    public void updateProduct(ProductRequest request, Product product, Category category) {
        if (request.name() != null) {
            product.setName(request.name());
        }
        if (request.description() != null) {
            product.setDescription(request.description());
        }
        product.setPrice(request.price());
        product.setStock(request.stock());
        if (request.imageUrl() != null) {
            product.setImageUrl(request.imageUrl());
        }
        if (category != null) {
            product.setCategory(category);
        }
    }
}
