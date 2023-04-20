package com.store.service;

import com.store.domain.Customer;

import java.util.List;

public interface CustomerService{

    void register(Customer customer);

    void save(Customer customer);

    List<Customer> getAllCustomers();

    void deleteById(Long id);

    Customer findById(Long id);
}
