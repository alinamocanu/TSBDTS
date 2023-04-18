package com.store.service;

import com.store.domain.Cart;
import com.store.domain.Customer;
import com.store.domain.Decoration;
import com.store.dto.*;
import com.store.exception.EmptyCartException;
import com.store.exception.ProductNotInCartException;
import com.store.mapper.CartMapper;
import com.store.mapper.CustomerMapper;
import com.store.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface CartService {

    Cart addDecorationToCart(Customer customer, OrderItemDto item);

    Cart createNewCart(Customer customer, double cost, OrderItemDto orderItemDto);

    Cart findCartByCustomer(Customer customer);

    void resetCart(Cart cart);

    List<OrderItemDto> getCartContent(Long customerId);

    Map<Long, List<OrderItemDto>> getCartItems();

    List<OrderItemDto> deleteOrderItemFromCart(Cart cart, Long customerId, Long decorationId);

    void updateCartAmount(Long cartId, double cost);

    OrderItemDto getOrderItemByDecorationId(Long decorationId, Long customerId);

    int updateItemQuantity(Long customerId, OrderItemDto item, int quantity);


}
