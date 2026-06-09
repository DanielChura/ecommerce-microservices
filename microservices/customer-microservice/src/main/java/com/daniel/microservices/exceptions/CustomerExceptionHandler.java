package com.daniel.microservices.exceptions;

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

@RestControllerAdvice(basePackages = "com.daniel.microservices.customer_microservice")
@Primary
@Slf4j
public class CustomerExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException exception,
            HttpServletRequest request) {
        var errors = new HashMap<String, String>();
        var errorMessage = exception.getMessage();
        var fieldName = "customer";
        errors.put(fieldName, errorMessage);
        log.warn("Customer not found: {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(errorMessage, LocalDateTime.now(), request.getRequestURI(), errors));
    }
}
