package com.store.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.domain.Order;
import com.store.dto.DecorationDto;
import com.store.dto.OrderItemDto;
import com.store.service.CustomerService;
import com.store.service.OrderItemService;
import com.store.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.store.util.OrderUtil.aOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderItemController.class)
class OrderItemControllerTest {
    @MockBean
    private OrderItemService orderItemService;

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getOrderItemsForOrder() throws Exception {
        OrderItemDto orderItemDto1 = OrderItemDto.builder()
                .quantity(1)
                .price(150)
                .decorationId(1L)
                .build();
        OrderItemDto orderItemDto2 = OrderItemDto.builder()
                .quantity(2)
                .price(40)
                .decorationId(2L)
                .build();
        List<OrderItemDto> orderItemDtos = new ArrayList<>(){{
            add(orderItemDto1);
            add(orderItemDto2);
        }};

        Long orderId = 1L;
        Order order = aOrder(orderId);

        when(orderService.findOrderByOrderId(any())).thenReturn(order);
        when(orderItemService.getOrderItemsForOrder(any())).thenReturn(orderItemDtos);

        mockMvc.perform(get("/orderItems/" + orderId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(convertObjectToJsonString(orderItemDtos)));
    }

    private String convertObjectToJsonString(List<OrderItemDto> orderItemDtos) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(orderItemDtos);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}