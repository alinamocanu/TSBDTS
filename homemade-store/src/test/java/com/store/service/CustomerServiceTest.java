package com.store.service;

import com.store.domain.Customer;
import com.store.dto.CustomerDto;
import com.store.mapper.CustomerMapper;
import com.store.repository.CustomerRepository;
import com.store.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.store.util.CustomerDtoUtil.aCustomerDto;
import static com.store.util.CustomerUtil.aCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void create() {
        //Arrange
        Customer customer = aCustomer("Maria", "Nedelcu");
        Customer savedCustomer = aCustomer(1L);

        when(customerRepository.save(customer)).thenReturn(savedCustomer);

//        Act
        customerService.save(customer);

        //Assert
        verify(customerRepository, times(1)).save(customer);

    }

    @Test
    void getOne() {
        //arrange
        Long id = Long.valueOf(1);
        Customer customer = aCustomer(1);

        when(customerRepository.findCustomerByCustomerId(id)).thenReturn(customer);

        //Act
        Customer result = customerService.findById(id);

        //Assert
        assertEquals(customer, result);
    }

    @Test
    void getAllCustomers() {
        //arrange
        Customer customer1 = aCustomer(1);
        Customer customer2 = aCustomer(2);
        List<Customer> customers = new ArrayList<>(){{
            add(customer1);
            add(customer2);
        }};

        when(customerRepository.findAll()).thenReturn(customers);

        //Act
        List<Customer> result = customerService.getAllCustomers();

        //Assert
        assertEquals(customers, result);
    }

}