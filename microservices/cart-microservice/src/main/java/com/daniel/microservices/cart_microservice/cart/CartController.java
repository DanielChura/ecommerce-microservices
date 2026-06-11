package com.daniel.microservices.cart_microservice.cart;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/{customerId}/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponse> getCartByCustomerId(@PathVariable("customerId") String customerId) {
        CartResponse cartResponse = cartService.getCartResponse(customerId);
        return ResponseEntity.ok(cartResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@PathVariable("customerId") String customerId) {
        cartService.clearCart(customerId);
        return ResponseEntity.noContent().build();
    }
}
