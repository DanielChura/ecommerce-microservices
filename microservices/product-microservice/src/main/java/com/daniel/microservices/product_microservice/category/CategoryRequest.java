package com.daniel.microservices.product_microservice.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
                Integer id,
                @NotBlank(message = "Name is required") String name,
                String description) {
}
