package com.daniel.microservices.cart_microservice.cart;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.daniel.microservices.cart_microservice.customer.CustomerClient;
import com.daniel.microservices.cart_microservice.exceptions.CartNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CustomerClient customerClient;

    public Cart getCartByCustomerId(String customerId) {
        customerClient.getCustomer(customerId)
                .orElseThrow(() -> new CartNotFoundException("Customer not found with id: " + customerId));

        return cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for customer: " + customerId));
    }

    public CartResponse getCartResponse(String customerId) {
        Cart cart = getCartByCustomerId(customerId);
        return new CartResponse(cart);
    }

    public Cart createCart(String customerId) {
        customerClient.getCustomer(customerId)
                .orElseThrow(() -> new CartNotFoundException("Customer not found with id: " + customerId));

        Cart cart = Cart.builder()
                .customerId(customerId)
                .items(new ArrayList<>())
                .build();
        return cartRepository.save(cart);
    }

    public void clearCart(String customerId) {
        Cart cart = getCartByCustomerId(customerId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
