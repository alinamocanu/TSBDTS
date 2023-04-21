package com.store.service;

import com.store.domain.Customer;
import com.store.domain.Order;
import com.store.dto.CustomerDto;
import com.store.dto.OrderDto;
import com.store.mapper.OrderMapper;
import com.store.repository.OrderRepository;
import com.store.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.store.util.CustomerDtoUtil.aCustomerDto;
import static com.store.util.CustomerUtil.aCustomer;
import static com.store.util.OrderDtoUtil.aOrderDto;
import static com.store.util.OrderUtil.aOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void findOrderByOrderId() {
        //arrange
        Long id = Long.valueOf(1);
        Order order = aOrder(1L);

        when(orderRepository.findOrderByOrderId(id)).thenReturn(order);

        //Act
        Order result = orderService.findOrderById(id);

        //Assert
        assertEquals(order, result);
    }

    @Test
    void getOne() {
        //arrange
        Long id = Long.valueOf(1);
        Order order = aOrder(1L);

        when(orderRepository.findOrderByOrderId(id)).thenReturn(order);

        //Act
        Order result = orderService.findOrderById(id);

        //Assert
        assertEquals(order, result);
    }

    @Test
    void getOrdersByCustomer() {
        Long customerId = 1L;
        Customer customer = aCustomer(customerId);
        Order order1 = aOrder(1L, customer);
        Order order2 = aOrder(2L, customer);
        List<Order> orders = new ArrayList<>(){{
            add(order1);
            add(order2);
        }};

        //when(customerService.findCustomerByCustomerId(customerId)).thenReturn(customer);
        when(orderRepository.findAllByCustomer(customer)).thenReturn(orders);


        //Act
        List<Order> result = orderService.getOrdersByCustomer(customer);

        //Assert
        assertEquals(orders, result);
    }
}