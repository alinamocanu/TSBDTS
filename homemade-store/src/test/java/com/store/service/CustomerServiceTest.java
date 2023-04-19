package com.store.service;

import com.store.domain.Customer;
import com.store.dto.CustomerDto;
import com.store.mapper.CustomerMapper;
import com.store.repository.CustomerRepository;
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

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper customerMapper;
    @InjectMocks
    private CustomerService customerService;

    @Test
    void create() {
        //Arrange
        CustomerDto customerDto = aCustomerDto("Maria", "Nedelcu");
        Customer customer = aCustomer("Maria", "Nedelcu");
        Customer savedCustomer = aCustomer(1L);

        when(customerMapper.mapToEntity(customerDto)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(savedCustomer);
        when(customerMapper.mapToDto(savedCustomer)).thenReturn(customerDto);

        //Act
        CustomerDto result = customerService.create(customerDto);

        //Assert
        assertThat(result).isNotNull();
        verify(customerMapper, times(1)).mapToEntity(customerDto);
        verify(customerMapper, times(1)).mapToDto(savedCustomer);
        verify(customerRepository, times(1)).save(customer);

        verifyNoMoreInteractions(customerMapper, customerRepository);
    }

    @Test
    void getOne() {
        //arrange
        Long id = Long.valueOf(1);
        CustomerDto customerDto = aCustomerDto(id);
        Customer customer = aCustomer(1);

        when(customerRepository.findCustomerByCustomerId(id)).thenReturn(customer);
        when(customerMapper.mapToDto(customer)).thenReturn(customerDto);

        //Act
        CustomerDto result = customerService.getOne(id);

        //Assert
        assertEquals(customerDto, result);
    }

    @Test
    void getAllCustomers() {
        //arrange
        Long id1 = Long.valueOf(1);
        CustomerDto customerDto1 = aCustomerDto(id1);
        Long id2 = Long.valueOf(2);
        CustomerDto customerDto2 = aCustomerDto(id2);
        List<CustomerDto> customerDtos = new ArrayList<>(){{
            add(customerDto1);
            add(customerDto2);
        }};

        Customer customer1 = aCustomer(1);
        Customer customer2 = aCustomer(2);
        List<Customer> customers = new ArrayList<>(){{
            add(customer1);
            add(customer2);
        }};

        when(customerRepository.findAll()).thenReturn(customers);
        when(customerMapper.mapToDto(customer1)).thenReturn(customerDto1);
        when(customerMapper.mapToDto(customer2)).thenReturn(customerDto2);

        //Act
        List<CustomerDto> result = customerService.getAllCustomers();

        //Assert
        assertEquals(customerDtos, result);
    }

    @Test
    void findCustomerByCustomerId() {
        //arrange
        Long id = Long.valueOf(1);
        Customer customer = aCustomer(1);

        when(customerRepository.findCustomerByCustomerId(id)).thenReturn(customer);

        //Act
        Customer result = customerService.findCustomerByCustomerId(id);

        //Assert
        assertEquals(customer, result);
    }
}