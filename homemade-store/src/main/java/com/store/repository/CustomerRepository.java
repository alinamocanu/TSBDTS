package com.store.repository;

import com.store.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findCustomerByCustomerId(Long id);
    List<Customer> findAll();
    Customer findCustomerByUsername(String username);
    void deleteByCustomerId(Long id);

}
