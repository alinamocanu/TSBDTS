package com.store.service;

import com.store.domain.Cart;
import com.store.domain.Customer;
import com.store.domain.Decoration;
import com.store.dto.CartDto;
import com.store.dto.CustomerDto;
import com.store.dto.DecorationDto;
import com.store.dto.OrderItemDto;
import com.store.mapper.CartMapper;
import com.store.mapper.CustomerMapper;
import com.store.repository.CartRepository;
import com.store.service.impl.CartServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.store.util.CartDtoUtil.aCartDto;
import static com.store.util.CartUtil.aCart;
import static com.store.util.CustomerDtoUtil.aCustomerDto;
import static com.store.util.CustomerUtil.aCustomer;
import static com.store.util.DecorationDtoUtil.aDecorationDto;
import static com.store.util.DecorationUtil.aDecoration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    void findCartByCustomer() {
        Customer customer = aCustomer(1L);
        Cart cart = aCart(1L, customer);

        when(cartService.findCartByCustomer(customer)).thenReturn(cart);

        Cart result = cartService.findCartByCustomer(customer);

        assertEquals(cart, result);
    }

    @Test
    void createCart() {
        Customer customer = aCustomer(1L);
        OrderItemDto orderItemDto = OrderItemDto.builder()
                .quantity(2)
                .price(40)
                .decorationId(2L)
                .build();
        Cart cart = aCart(customer);

        when(cartRepository.save(any())).thenReturn(cart);

        //Act
        Cart result = cartService.createNewCart(customer, 200, orderItemDto);

        //Assert
        assertThat(result).isNotNull();

    }
}