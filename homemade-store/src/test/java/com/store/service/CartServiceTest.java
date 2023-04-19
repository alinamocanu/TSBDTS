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
    @Mock
    private CartMapper cartMapper;
    @Mock
    private CustomerMapper customerMapper;
    @InjectMocks
    private CartService cartService;

    @Test
    void findCartByCustomer() {
        Customer customer = aCustomer(1L);
        Cart cart = aCart(1L, customer);
//
//        when(cartService.findCartByCustomer(customer)).thenReturn(cart);
//
//        Cart result = cartService.findCartByCustomer(customer);
//
//        assertEquals(cart, result);
    }

    @Test
    void createCart() {
        Customer customer = aCustomer(1L);
        CustomerDto customerDto = aCustomerDto(1L);
        OrderItemDto orderItemDto = OrderItemDto.builder()
                .quantity(2)
                .price(40)
                .decorationId(2L)
                .build();
        Cart cart = aCart(customer);

        when(customerMapper.mapToEntity(customerDto)).thenReturn(customer);
        when(cartRepository.save(cart)).thenReturn(cart);


        //Act
        //Cart result = cartService.createCart(customerDto, 200, orderItemDto);

        //Assert
        //assertThat(result).isNotNull();

        verifyNoMoreInteractions(cartMapper, cartRepository);

    }

    @Test
    void getAll() {
        Cart cart1 = aCart(1L);
        Cart cart2 = aCart(2L);
        List<Cart> carts = new ArrayList<>(){{
            add(cart1);
            add(cart2);
        }};

        CartDto cartDto1 = aCartDto(1L);
        CartDto cartDto2 = aCartDto(2L);
        List<CartDto> cartDtos = new ArrayList<>(){{
            add(cartDto1);
            add(cartDto2);
        }};

        when(cartRepository.findAll()).thenReturn(carts);
        when(cartMapper.mapToDto(cart1)).thenReturn(cartDto1);
        when(cartMapper.mapToDto(cart2)).thenReturn(cartDto2);

        //Act
        //List<CartDto> result = cartService.getAll();

        //Assert
        //assertEquals(cartDtos, result);
    }

}