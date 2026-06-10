package com.daniel.microservices.product_microservice.category;

import java.util.List;

import org.springframework.stereotype.Service;

import com.daniel.microservices.product_microservice.exceptions.CategoryNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Integer saveCategory(CategoryRequest request) {
        var id = request.id();

        if (id != null) {
            var category = categoryRepository.findById(id)
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
            categoryMapper.updateCategory(request, category);
            var updatedId = categoryRepository.save(category).getId();
            log.info("Category updated: {}", updatedId);
            return updatedId;
        }

        var category = categoryMapper.toCategory(request);
        var savedId = categoryRepository.save(category).getId();
        log.info("Category created: {}", savedId);
        return savedId;
    }

    public CategoryResponse getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toCategoryResponse)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
    }

    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    public void deleteCategory(Integer id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
        categoryRepository.delete(category);
        log.info("Category deleted: {}", id);
    }
}
