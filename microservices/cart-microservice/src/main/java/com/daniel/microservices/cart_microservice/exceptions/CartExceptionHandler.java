package com.daniel.microservices.cart_microservice.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.daniel.common_exceptions.ErrorResponse;
import com.daniel.common_exceptions.GlobalExceptionHandler;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice(basePackages = "com.daniel.microservices.cart_microservice")
@Primary
@Slf4j
public class CartExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCartNotFoundException(CartNotFoundException exception,
            HttpServletRequest request) {
        var errors = new HashMap<String, String>();
        var errorMessage = exception.getMessage();
        var fieldName = "cart";
        errors.put(fieldName, errorMessage);
        log.warn("Cart not found: {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(errorMessage, LocalDateTime.now(), request.getRequestURI(), errors));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(FeignException exception, HttpServletRequest request) {
        var errors = new HashMap<String, String>();
        var errorMessage = "Feign error: " + exception.getMessage();
        var fieldName = "message";
        errors.put(fieldName, errorMessage);
        log.error("Feign error on {}: {}", request.getRequestURI(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errorMessage, LocalDateTime.now(), request.getRequestURI(), errors));
    }
}
