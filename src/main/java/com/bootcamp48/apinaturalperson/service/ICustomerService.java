package com.bootcamp48.apinaturalperson.service;

import com.bootcamp48.apinaturalperson.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerService {

    Mono<Customer> getCustomerByNumDoc(int documentNumber);
    Flux<Customer> getCustomerByName(String name);
    Mono<Customer> registrarCustomer(Customer customer);

}
