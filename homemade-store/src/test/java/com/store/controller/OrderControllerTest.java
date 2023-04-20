package com.store.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.domain.Customer;
import com.store.dto.OrderDto;
import com.store.dto.OrderItemDto;
import com.store.service.CustomerService;
import com.store.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static com.store.util.CustomerUtil.aCustomer;
import static com.store.util.OrderDtoUtil.aOrderDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {
    @MockBean
    private OrderService orderService;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getOrderById() throws Exception {
        Long id = 2L;
        OrderDto dto = aOrderDto(id);

        //when(orderService.getOne(any())).thenReturn(dto);

        mockMvc.perform(get("/orders/" + id.intValue()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("orderId", is(dto.getOrderId().intValue())))
                .andExpect(jsonPath("$.totalAmount", is(dto.getTotalAmount())));
    }

    @Test
    void getOrdersFromCustomer() throws Exception {
        Long customerId = 1L;
        Customer customer = aCustomer(customerId);
        OrderDto orderDto1 = aOrderDto(1L, customer);
        OrderDto orderDto2 = aOrderDto(2L, customer);
        List<OrderDto> orderDtoList = new ArrayList<>(){{
            add(orderDto1);
            add(orderDto2);
        }};

        when(customerService.findCustomerByCustomerId(any())).thenReturn(customer);
        //when(orderService.getOrdersByCustomer(any())).thenReturn(orderDtoList);

        MvcResult result = mockMvc.perform(get("/orders/all/" + customerId.intValue()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(orderDtoList));
    }
}