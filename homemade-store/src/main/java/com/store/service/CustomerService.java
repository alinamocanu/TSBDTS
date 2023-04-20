package com.store.service;

import com.store.domain.Customer;
import com.store.dto.CustomerDto;
import com.store.mapper.CustomerMapper;
import com.store.repository.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

public interface CustomerService{

    void register(Customer customer);

    List<Customer> getAllCustomers();

    void deleteById(Long id);

    Customer findById(Long id);
}
