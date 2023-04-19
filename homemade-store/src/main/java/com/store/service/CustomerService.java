package com.store.service;

import com.store.domain.Customer;
import com.store.dto.CustomerDto;
import com.store.mapper.CustomerMapper;
import com.store.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository repository, CustomerMapper customerMapper) {
        this.customerRepository = repository;
        this.customerMapper = customerMapper;
    }

    public CustomerDto create(CustomerDto customerDto) {
        Customer customer = customerMapper.mapToEntity(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.mapToDto(savedCustomer);
    }

    public CustomerDto getOne(Long id) {
        return customerMapper.mapToDto(customerRepository.findCustomerByCustomerId(id));
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream().map(c -> customerMapper.mapToDto(c)).collect(Collectors.toList());
    }

    public Customer findCustomerByCustomerId(Long customerId) {
        return customerRepository.findCustomerByCustomerId(customerId);
    }
}
