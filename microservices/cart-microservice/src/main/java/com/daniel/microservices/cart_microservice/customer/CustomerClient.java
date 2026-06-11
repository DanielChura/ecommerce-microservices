package com.daniel.microservices.cart_microservice.customer;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerClient {
    @GetMapping("/api/v1/customers/{customerId}")
    Optional<CustomerResponse> getCustomer(@PathVariable("customerId") String customerId);
}
