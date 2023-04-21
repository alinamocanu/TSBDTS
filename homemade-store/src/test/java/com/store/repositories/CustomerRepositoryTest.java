package com.store.repositories;

import com.store.domain.Customer;
import com.store.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Order(1)
    public void addCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Adam");
        customerRepository.save(customer);
    }

    @Test
    @Order(2)
    public void findAll() {
        List<Customer> customers = customerRepository.findAll();
        assertFalse(customers.isEmpty());
        log.info("find all customers ...");
        customers.forEach(customer -> log.info(customer.getLastName()));
    }

    @Test
    @Order(3)
    public void findById() {
        Customer customer = customerRepository.findCustomerByCustomerId(1L);
        assertFalse(customer == null);
        log.info("findById ..."+customer.getFirstName()+" "+customer.getLastName());
    }


}
