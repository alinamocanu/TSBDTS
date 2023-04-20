package com.store.service.impl;

import com.store.domain.Customer;
import com.store.domain.security.User;
import com.store.exception.CustomerAlreadyExistException;
import com.store.repository.CustomerRepository;
import com.store.repository.security.UserRepository;
import com.store.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void register(Customer customer) {
        if(customerRepository.findCustomerByUsername(customer.getUsername())!= null){
            throw new CustomerAlreadyExistException();
        }

        customerRepository.save(customer);
    }

    @Override
    public void save(Customer customer){
        customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
        customerRepository.deleteByCustomerId(id);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findCustomerByCustomerId(id);
    }

}
