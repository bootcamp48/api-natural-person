package com.bootcamp48.apinaturalperson.controller;

import com.bootcamp48.apinaturalperson.model.Customer;
import com.bootcamp48.apinaturalperson.service.CustomerService;
import com.bootcamp48.apinaturalperson.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping("/naturalPerson")
public class CustomerRestController {

    private final ICustomerService customerService;

    @Autowired
    public CustomerRestController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/document/{numDoc}")
    public Mono<ResponseEntity<Customer>> getCustomerByNumDoc(@PathVariable int numDoc) {
        return customerService.getCustomerByNumDoc(numDoc)
                .map(customer -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(customer))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public Mono<ResponseEntity<Flux<Customer>>> getCustomersByName(String name) {
        return Mono.just(ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(customerService.getCustomerByName(name)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/registrar")
    public Mono<ResponseEntity<Customer>> registrarCustomer(@RequestBody Customer customer) {

        return Mono.just(customer)
                .flatMap(c -> {
                    if (c.getBirthDate() == null) {
                        c.setBirthDate(LocalDate.now());
                    }
                    return customerService.registrarCustomer(c)
                            .map(cus -> ResponseEntity
                                    .created(URI.create("/naturalPerson".concat(String.valueOf(cus.getId()))))
                                    .contentType(MediaType.APPLICATION_JSON).body(cus));

                });

    }
}
