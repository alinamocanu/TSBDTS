package com.store.controller;

import com.store.dto.CustomerDto;
import com.store.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    @ApiOperation(value = "Create a new customer",
            notes = "Creates a new customer based on the information received in the request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The customer was successfully created based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity
                .ok()
                .body(service.create(customerDto));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a customer", notes = "Get a customer based on the Id received in the request")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Customer with the entered Id does not exist")})
    public ResponseEntity<CustomerDto> get(@PathVariable Long id) {
        if (service.getOne(id) == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity
                .ok()
                .body(service.getOne(id));
    }

    @GetMapping
    @ApiOperation(value = "Get all customers", notes = "Get all customers from the database")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Validation error on the received request")})
    public ResponseEntity<List<CustomerDto>> getAll(){
        return ResponseEntity
                .ok()
                .body(service.getAllCustomers());
    }
}
