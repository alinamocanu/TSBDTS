package com.store.repository;

import com.store.domain.Customer;
import com.store.domain.Order;
import com.store.dto.OrderDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findOrderByOrderId(Long id);
    List<Order> findAllByCustomer(Customer customer);
}
