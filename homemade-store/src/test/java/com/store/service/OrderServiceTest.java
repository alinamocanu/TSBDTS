package com.store.service;

import com.store.domain.Customer;
import com.store.domain.Order;
import com.store.dto.CustomerDto;
import com.store.dto.OrderDto;
import com.store.mapper.OrderMapper;
import com.store.repository.OrderRepository;
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
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private  CustomerService customerService;
    @InjectMocks
    private OrderService orderService;

    @Test
    void findOrderByOrderId() {
        //arrange
        Long id = Long.valueOf(1);
        Order order = aOrder(1L);

        when(orderRepository.findOrderByOrderId(id)).thenReturn(order);

        //Act
        Order result = orderService.findOrderByOrderId(id);

        //Assert
        assertEquals(order, result);
    }

    @Test
    void createOrder() {
    }

    @Test
    void getOne() {
        //arrange
        Long id = Long.valueOf(1);
        OrderDto orderDto = aOrderDto(id);
        Order order = aOrder(1L);

        when(orderRepository.findOrderByOrderId(id)).thenReturn(order);
        when(orderMapper.mapToDto(order)).thenReturn(orderDto);

        //Act
        OrderDto result = orderService.getOne(id);

        //Assert
        assertEquals(orderDto, result);
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

        OrderDto orderDto1 = aOrderDto(1L, customer);
        OrderDto orderDto2 = aOrderDto(2L, customer);
        List<OrderDto> orderDtos = new ArrayList<>(){{
            add(orderDto1);
            add(orderDto2);
        }};

        when(customerService.findCustomerByCustomerId(customerId)).thenReturn(customer);
        when(orderRepository.findAllByCustomer(customer)).thenReturn(orders);
        when(orderMapper.mapToDto(order1)).thenReturn(orderDto1);
        when(orderMapper.mapToDto(order2)).thenReturn(orderDto2);

        //Act
        List<OrderDto> result = orderService.getOrdersByCustomer(customerId);

        //Assert
        assertEquals(orderDtos, result);
    }
}