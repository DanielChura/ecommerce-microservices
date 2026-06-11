package com.daniel.microservices.cart_microservice.cartItem;

import org.springframework.stereotype.Service;

import com.daniel.microservices.cart_microservice.cart.Cart;
import com.daniel.microservices.cart_microservice.cart.CartRepository;
import com.daniel.microservices.cart_microservice.cart.CartService;
import com.daniel.microservices.cart_microservice.exceptions.CartException;
import com.daniel.microservices.cart_microservice.product.ProductClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartRepository cartRepository;
    private final CartService cartService;
    private final ProductClient productClient;

    public String addItemToCart(String customerId, CartItemRequest request) {
        if (request.quantity() == null || request.quantity() < 1) {
            throw new CartException("Quantity must be at least 1");
        }

        var product = productClient.getProduct(String.valueOf(request.productId()))
                .orElseThrow(() -> new CartException("Product not found with id: " + request.productId()));

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> cartService.createCart(customerId));

        var existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(request.productId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + request.quantity();
            if (newQuantity > product.stock()) {
                throw new CartException(
                        "Insufficient stock. Available: " + product.stock() + ", requested: " + newQuantity);
            }
            item.setQuantity(newQuantity);
            cartRepository.save(cart);
            return "Item quantity updated successfully";
        }

        if (request.quantity() > product.stock()) {
            throw new CartException(
                    "Insufficient stock. Available: " + product.stock() + ", requested: " + request.quantity());
        }

        CartItem newItem = CartItem.builder()
                .productId(request.productId())
                .quantity(request.quantity())
                .build();

        cart.getItems().add(newItem);
        cartRepository.save(cart);
        return "Item added to cart successfully";
    }

    public void updateItemFromCart(String customerId, CartItemRequest request) {
        if (request.quantity() == null || request.quantity() < 1) {
            throw new CartException("Quantity must be at least 1");
        }

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CartException("Cart not found for customer: " + customerId));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(request.productId()))
                .findFirst()
                .orElseThrow(() -> new CartException("Product not found in cart: " + request.productId()));

        var product = productClient.getProduct(String.valueOf(request.productId()))
                .orElseThrow(() -> new CartException("Product not found with id: " + request.productId()));

        if (request.quantity() > product.stock()) {
            throw new CartException(
                    "Insufficient stock. Available: " + product.stock() + ", requested: " + request.quantity());
        }

        item.setQuantity(request.quantity());
        cartRepository.save(cart);
    }

    public void removeItemFromCart(String customerId, Integer productId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CartException("Cart not found for customer: " + customerId));

        boolean removed = cart.getItems().removeIf(item -> item.getProductId().equals(productId));

        if (!removed) {
            throw new CartException("Product not found in cart: " + productId);
        }

        cartRepository.save(cart);
    }
}
