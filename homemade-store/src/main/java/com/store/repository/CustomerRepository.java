package com.store.repository;

import com.store.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findCustomerByCustomerId(Long id);
    List<Customer> findAll();
    Customer findCustomerByUsername(String username);
    void deleteByCustomerId(Long id);
}
