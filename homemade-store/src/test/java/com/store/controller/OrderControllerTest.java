package com.store.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.domain.BankAccount;
import com.store.domain.Customer;
import com.store.domain.Order;
import com.store.dto.OrderDto;
import com.store.dto.OrderItemDto;
import com.store.service.CustomerService;
import com.store.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static com.store.util.BankAccountUtil.aBankAccount;
import static com.store.util.CustomerUtil.aCustomer;
import static com.store.util.OrderDtoUtil.aOrderDto;
import static com.store.util.OrderUtil.aOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("h2")
class OrderControllerTest {
    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getOrderById() throws Exception {
        Long id = 2L;
        Order order = aOrder(id);
        order.setBankAccount(aBankAccount("1122334455667788"));

        when(orderService.findOrderById(any())).thenReturn(order);

        mockMvc.perform(get("/orders/" + id.intValue()))
                .andExpect(status().isOk())
                .andExpect(view().name("orderDetails"));
    }

}