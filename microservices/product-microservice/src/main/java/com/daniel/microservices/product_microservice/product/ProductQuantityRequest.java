package com.daniel.microservices.product_microservice.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductQuantityRequest(
        @NotNull(message = "Product ID is required") Integer productId,
        @Positive(message = "Quantity must be positive") Integer quantity) {

}
