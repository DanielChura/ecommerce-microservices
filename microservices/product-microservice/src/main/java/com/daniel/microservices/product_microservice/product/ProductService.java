package com.daniel.microservices.product_microservice.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daniel.microservices.product_microservice.category.Category;
import com.daniel.microservices.product_microservice.category.CategoryRepository;
import com.daniel.microservices.product_microservice.exceptions.CategoryNotFoundException;
import com.daniel.microservices.product_microservice.exceptions.InsufficientStockException;
import com.daniel.microservices.product_microservice.exceptions.ProductNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public Integer saveProduct(ProductRequest request) {
        var id = request.id();

        if (id != null) {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
            Category category = findCategory(request.categoryId());
            productMapper.updateProduct(request, product, category);
            Integer updatedId = productRepository.save(product).getId();
            log.info("Product updated: {}", updatedId);
            return updatedId;
        }

        Category category = findCategory(request.categoryId());
        Product product = productMapper.toProduct(request, category);
        Integer savedId = productRepository.save(product).getId();
        log.info("Product created: {}", savedId);
        return savedId;
    }

    public ProductResponse getProductById(Integer id) {
        return productRepository.findById(id)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    public void deleteProduct(Integer id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
        log.info("Product deleted: {}", id);
    }

    @Transactional
    public void purchaseProduct(List<ProductQuantityRequest> request) {
        for (ProductQuantityRequest req : request) {
            Product product = productRepository.findById(req.productId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + req.productId()));

            if (product.getStock() < req.quantity()) {
                throw new InsufficientStockException(
                        "Insufficient stock for product " + req.productId() + ": available " + product.getStock()
                                + ", requested " + req.quantity());
            }
            product.setStock(product.getStock() - req.quantity());
            productRepository.save(product);
            log.info("Product purchased: {} (qty: {})", req.productId(), req.quantity());
        }
    }

    @Transactional
    public void restockProduct(List<ProductQuantityRequest> request) {
        for (ProductQuantityRequest req : request) {
            Product product = productRepository.findById(req.productId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + req.productId()));
            product.setStock(product.getStock() + req.quantity());
            productRepository.save(product);
            log.info("Product restocked: {} (qty: {})", req.productId(), req.quantity());
        }
    }

    private Category findCategory(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + categoryId));
    }
}
