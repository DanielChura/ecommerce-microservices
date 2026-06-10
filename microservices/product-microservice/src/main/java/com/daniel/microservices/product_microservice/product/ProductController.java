package com.daniel.microservices.product_microservice.product;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Integer> saveProduct(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.saveProduct(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @PutMapping
    public ResponseEntity<Void> updateProduct(@Valid @RequestBody ProductRequest request) {
        productService.saveProduct(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/purchase")
    public ResponseEntity<Void> purchaseProduct(@Valid @RequestBody List<ProductQuantityRequest> request) {
        productService.purchaseProduct(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restock")
    public ResponseEntity<Void> restockProduct(@Valid @RequestBody List<ProductQuantityRequest> request) {
        productService.restockProduct(request);
        return ResponseEntity.ok().build();
    }

}
