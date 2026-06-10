package com.daniel.microservices.product_microservice.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.daniel.common_exceptions.ErrorResponse;
import com.daniel.common_exceptions.GlobalExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice(basePackages = "com.daniel.microservices.product_microservice")
@Primary
@Slf4j
public class ProductExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException exception,
            HttpServletRequest request) {
        var errors = new HashMap<String, String>();
        var errorMessage = exception.getMessage();
        var fieldName = "product";
        errors.put(fieldName, errorMessage);
        log.warn("Product not found: {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(errorMessage, LocalDateTime.now(), request.getRequestURI(), errors));
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStockException(InsufficientStockException exception,
            HttpServletRequest request) {
        var errors = new HashMap<String, String>();
        var errorMessage = exception.getMessage();
        var fieldName = "stock";
        errors.put(fieldName, errorMessage);
        log.warn("Insufficient stock: {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Insufficient stock", LocalDateTime.now(), request.getRequestURI(), errors));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException exception,
            HttpServletRequest request) {
        var errors = new HashMap<String, String>();
        var errorMessage = exception.getMessage();
        var fieldName = "category";
        errors.put(fieldName, errorMessage);
        log.warn("Category not found: {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(errorMessage, LocalDateTime.now(), request.getRequestURI(), errors));
    }
}
