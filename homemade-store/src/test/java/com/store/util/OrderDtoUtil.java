package com.store.util;

import com.store.domain.Customer;
import com.store.domain.Order;
import com.store.dto.OrderDto;

import java.time.LocalDateTime;

public class OrderDtoUtil {
    public static OrderDto aOrderDto(Long id) {
        return OrderDto.builder()
                .orderId(id)
                .totalAmount(200)
                .orderPlaced(LocalDateTime.now())
                .build();
    }

    public static OrderDto aOrderDto(Long id, Customer customer) {
        return OrderDto.builder()
                .orderId(id)
                .totalAmount(200)
                .orderPlaced(LocalDateTime.now())
                .customer(customer)
                .build();
    }
}
