package com.store.service;

import com.store.domain.*;
import com.store.dto.OrderDto;
import com.store.dto.OrderItemDto;
import com.store.exception.BankAccountNotFoundException;
import com.store.exception.CartNotFoundException;
import com.store.exception.EmptyCartException;
import com.store.exception.InsufficientFundsException;
import com.store.mapper.OrderMapper;
import com.store.repository.OrderItemRepository;
import com.store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface OrderService {
    Order findOrderById(Long id);

    List<Order> getOrdersByCustomer(Customer customer);

    Optional<Order> createOrder(Customer customer, List<OrderItemDto> orderItemDto, String cardNumber);

    boolean checkBalanceForOrder(BankAccount bankAccount, double total);

}
