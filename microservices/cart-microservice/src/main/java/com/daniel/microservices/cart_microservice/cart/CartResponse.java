package com.daniel.microservices.cart_microservice.cart;

import java.util.List;

import com.daniel.microservices.cart_microservice.cartItem.CartItem;

public record CartResponse(
        String id,
        String customerId,
        List<CartItem> items) {

    public CartResponse(Cart cart) {
        this(cart.getId(), cart.getCustomerId(), cart.getItems());
    }
}
