package com.daniel.microservices.customer_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"com.daniel.microservices.customer_microservice",
		"com.daniel.microservices.exceptions",
		"com.daniel.common_exceptions"
})
public class CustomerMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerMicroserviceApplication.class, args);
	}

}
