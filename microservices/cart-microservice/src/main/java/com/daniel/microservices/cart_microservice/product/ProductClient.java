package com.daniel.microservices.cart_microservice.product;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {
    @GetMapping("/api/v1/products/{productId}")
    Optional<ProductResponse> getProduct(@PathVariable("productId") String productId);
}
