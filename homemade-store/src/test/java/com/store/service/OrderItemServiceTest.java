package com.store.service;

import com.store.domain.Decoration;
import com.store.domain.DecorationCategory;
import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.dto.CustomerDto;
import com.store.dto.OrderItemDto;
import com.store.mapper.OrderItemMapper;
import com.store.repository.OrderItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private OrderItemMapper orderItemMapper;
    @Mock
    private OrderService orderService;
    @InjectMocks
    private OrderItemService orderItemService;

    @Test
    void getOrderItemsForOrder() {
        OrderItem orderItem1 = OrderItem.builder()
                .quantity(1)
                .price(150)
                .decoration(Decoration.builder()
                        .decorationId(1L)
                        .price(150)
                        .category(DecorationCategory.CHRISTMAS)
                        .decorationName("lamp")
                        .description("-")
                        .build())
                .build();
        OrderItemDto orderItemDto1 = OrderItemDto.builder()
                .quantity(1)
                .price(150)
                .decorationId(1L)
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .quantity(2)
                .price(40)
                .decoration(Decoration.builder()
                        .decorationId(2L)
                        .price(20)
                        .category(DecorationCategory.CHRISTMAS)
                        .decorationName("globe")
                        .description("-")
                        .build())
                .build();
        OrderItemDto orderItemDto2 = OrderItemDto.builder()
                .quantity(2)
                .price(40)
                .decorationId(2L)
                .build();

        Long id = 1L;
        List<OrderItem> orderItems = new ArrayList<>(){{
            add(orderItem1);
            add(orderItem2);
        }};
        List<OrderItemDto> orderItemDtos = new ArrayList<>(){{
            add(orderItemDto1);
            add(orderItemDto2);
        }};
        Order order = Order.builder()
                .orderId(id)
                .orderPlaced(LocalDateTime.now())
                .totalAmount(150)
                .orderItems(orderItems)
                .build();

        when(orderService.findOrderByOrderId(id)).thenReturn(order);
        when(orderItemRepository.findAllByOrders(order)).thenReturn(orderItems);
        when(orderItemMapper.mapToDto(orderItem1)).thenReturn(orderItemDto1);
        when(orderItemMapper.mapToDto(orderItem2)).thenReturn(orderItemDto2);

        //Act
        List<OrderItemDto> result = orderItemService.getOrderItemsForOrder(id);

        //Assert
        assertEquals(orderItemDtos, result);
    }

}