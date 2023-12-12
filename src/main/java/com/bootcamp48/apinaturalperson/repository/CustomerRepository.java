package com.bootcamp48.apinaturalperson.repository;

import com.bootcamp48.apinaturalperson.model.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, Integer> {

    Mono<Customer> findByNumDoc(int documentNumber);
    Flux<Customer> findByName(String name);

}
