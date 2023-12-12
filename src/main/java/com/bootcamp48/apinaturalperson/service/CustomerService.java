package com.bootcamp48.apinaturalperson.service;

import com.bootcamp48.apinaturalperson.model.Customer;
import com.bootcamp48.apinaturalperson.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Mono<Customer> getCustomerByNumDoc(int documentNumber) {
        return customerRepository.findByNumDoc(documentNumber);
    }

    @Override
    public Flux<Customer> getCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    @Override
    public Mono<Customer> registrarCustomer(Customer customer) {
        if (!customerExists(customer)){
            customer.setCreationDate(LocalDateTime.now());
            return customerRepository.save(customer);
        }else {
            return Mono.error(new CustomerValidationException("El cliente ya existe"));
        }
    }

    public boolean customerExists(Customer customer) {
        Mono<Customer> customerMono = customerRepository.findByNumDoc(customer.getNumDoc());
        return customerMono == null;
    }

    public class CustomerValidationException extends RuntimeException {

        public CustomerValidationException(String message) {
            super(message);
        }
    }

}
