package com.store.service.impl;

import com.store.domain.Customer;
import com.store.exception.CustomerAlreadyExistException;
import com.store.exception.CustomerNotFoundException;
import com.store.repository.CustomerRepository;
import com.store.service.CustomerService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void register(Customer customer) {
        if(customerRepository.findCustomerByUsername(customer.getUsername())!= null){
            throw new CustomerAlreadyExistException();
        }

        customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        Customer customer = customerRepository.findCustomerByCustomerId(id);
        customer.setRoles(null);

        customerRepository.deleteByCustomerId(id);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findCustomerByCustomerId(id);
    }


//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Customer customer = customerRepository.findCustomerByUsername(username);
//        if(customer == null)
//            throw new CustomerNotFoundException();
//
//        return customer;
//    }
}
