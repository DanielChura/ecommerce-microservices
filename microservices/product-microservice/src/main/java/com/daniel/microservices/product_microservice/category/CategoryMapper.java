package com.daniel.microservices.product_microservice.category;

import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toCategory(CategoryRequest request) {
        return Category.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .build();
    }

    public CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .products(category.getProducts().stream().map(ProductMapper::toProductResponse).toList())
                .build();
    }

    public void updateCategory(CategoryRequest request, Category category) {
        if (request.name() != null) {
            category.setName(request.name());
        }
        if (request.description() != null) {
            category.setDescription(request.description());
        }
    }
}
