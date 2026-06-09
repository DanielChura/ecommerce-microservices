package com.daniel.microservices.customer_microservice.customer;

import java.util.List;
import org.springframework.stereotype.Service;
import com.daniel.microservices.exceptions.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public String saveCustomer(CustomerRequest request) {
        var id = request.id();

        if (id != null) {
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
            customerMapper.updateCustomer(request, customer);
            return customerRepository.save(customer).getId();
        }

        var customer = customerMapper.toCustomer(request);
        return customerRepository.save(customer).getId();
    }

    public CustomerResponse getCustomerById(String id) {
        return customerRepository.findById(id)
                .map(customerMapper::toCustomerResponse)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
    }

    public List<CustomerResponse> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toCustomerResponse)
                .toList();
    }

    public void deleteCustomer(String id) {
        var customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        customerRepository.delete(customer);
    }
}
