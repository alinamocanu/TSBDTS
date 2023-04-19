package com.store.util;

import com.store.domain.Customer;
import com.store.domain.Order;
import com.store.service.CustomerService;

import java.time.LocalDateTime;

public class OrderUtil {
    public static Order aOrder(Long id) {
        return Order.builder()
                .orderId(id)
                .totalAmount(200)
                .orderPlaced(LocalDateTime.now())
                .build();
    }

    public static Order aOrder(Long id, Customer customer) {
        return Order.builder()
                .orderId(id)
                .totalAmount(200)
                .orderPlaced(LocalDateTime.now())
                .customer(customer)
                .build();
    }
}
