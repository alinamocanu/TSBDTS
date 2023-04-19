package com.store.service;

import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.dto.OrderItemDto;
import com.store.mapper.OrderItemMapper;
import com.store.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;
    private final OrderItemMapper orderItemMapper;

    public OrderItemService(OrderItemRepository orderItemRepository, OrderService orderService, OrderItemMapper orderItemMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderService = orderService;
        this.orderItemMapper = orderItemMapper;
    }

    public List<OrderItemDto> getOrderItemsForOrder(Long orderId) {
        Order order = orderService.findOrderByOrderId(orderId);
        List <OrderItem> orderItems = orderItemRepository.findAllByOrders(order);

        return orderItems.stream().map(o -> orderItemMapper.mapToDto(o)).collect(Collectors.toList());
    }

}
