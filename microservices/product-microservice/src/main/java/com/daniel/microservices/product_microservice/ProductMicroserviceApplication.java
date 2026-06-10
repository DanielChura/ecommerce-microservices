package com.daniel.microservices.product_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.daniel.microservices.product_microservice",
        "com.daniel.common_exceptions"
})
public class ProductMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductMicroserviceApplication.class, args);
    }

}
