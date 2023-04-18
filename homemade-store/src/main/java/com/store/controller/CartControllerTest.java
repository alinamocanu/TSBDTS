package com.store.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.dto.CartDto;
import com.store.mapper.CustomerMapper;
import com.store.service.CartService;
import com.store.service.CustomerService;
import com.store.service.DecorationService;
import com.store.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.store.util.CartDtoUtil.aCartDto;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CartController.class)
class CartControllerTest {
    @MockBean
    private DecorationService decorationService;
    @MockBean
    private OrderService orderService;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private CustomerMapper  customerMapper;
    @MockBean
    private CartService cartService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllCarts() throws Exception {
        CartDto cart1 = aCartDto(1L);
        CartDto cart2 = aCartDto(2L);
        List<CartDto> carts = new ArrayList<>(){{
            add(cart1);
            add(cart2);
        }};
//
//        when(cartService.getAll()).thenReturn(carts);
//
//        mockMvc.perform(get("/carts"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(content().json(convertObjectToJsonString(carts)));
    }

    private String convertObjectToJsonString(List<CartDto> carts) {
        try {
            return objectMapper.writeValueAsString(carts);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}