package com.store.service;

import com.store.domain.Cart;
import com.store.domain.Customer;
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

@Service
public class CartService {

    private final DecorationService decorationService;
    private final CartRepository cartRepository;
    private final Map<Long, List<OrderItemDto>> cartItems = new HashMap<>();
    private final CustomerMapper customerMapper;
    private final CartMapper cartMapper;

    public Cart findCartByCustomer(Customer customer) { return cartRepository.findCartByCustomer(customer);}

    public Map<Long, List<OrderItemDto>> getCartItems() {
        return cartItems;
    }

    public CartService(DecorationService decorationService, CartRepository cartRepository, CustomerMapper customerMapper, CartMapper cartMapper) {
        this.decorationService = decorationService;
        this.cartRepository = cartRepository;
        this.customerMapper = customerMapper;
        this.cartMapper = cartMapper;
    }

    public Cart createCart(CustomerDto customerDto, double value, OrderItemDto orderItemDto) {
        Cart cart = new Cart();
        cart.setTotalAmount(value);

        Customer customer = customerMapper.mapToEntity(customerDto);
        cart.setCustomer(customer);

        List<OrderItemDto> items = new ArrayList<>();
        items.add(orderItemDto);
        cartItems.put(customerDto.getCustomerId(), items);

        return cartRepository.save(cart);
    }

    public void addItemToCart(CustomerDto customerDto, OrderItemDto item) {
        List<OrderItemDto> orderItems = cartItems.get(customerDto.getCustomerId());
        int found = IntStream.range(0, orderItems.size())
                .filter(i -> orderItems.get(i).getDecorationId() == item.getDecorationId())
                .findFirst().orElse(-1);

        if (found != -1) {
            OrderItemDto old = orderItems.get(found);
            // update quantity of item in customer's list
            orderItems.set(found, new OrderItemDto(old.getQuantity() + item.getQuantity(), old.getPrice(), old.getDecorationId()));
        } else {
            orderItems.add(item);
        }
        cartItems.put(customerDto.getCustomerId(), orderItems);
    }

    public void addToCartAmount(Long cartId, double newPrice) {
        Cart cart = cartRepository.findCartByCartId(cartId);
        cart.setTotalAmount(cart.getTotalAmount() + newPrice);
        cartRepository.save(cart);
    }

    public CartDto add(CustomerDto customerDto, OrderItemDto orderItemDto) {
        Customer customer = customerMapper.mapToEntity(customerDto);
        Cart cart = cartRepository.findCartByCustomer(customer);

        if (cart == null) {
            // create customer's cart in case he doesn't have one yet
            cart = createCart(customerDto, orderItemDto.getQuantity() * orderItemDto.getPrice(), orderItemDto);
        } else {
            addItemToCart(customerDto, orderItemDto);
            addToCartAmount(cart.getCartId(), orderItemDto.getQuantity() * orderItemDto.getPrice());
        }
        return cartMapper.mapToDto(cart);
    }

    public List<OrderItemDto> deleteItemFromCart(Cart cart, Long customerId, Long decorationId) {
        //decorationService.getOne(decorationId);

        List<OrderItemDto> items = cartItems.get(customerId);

        int found = IntStream.range(0, items.size())
                .filter(i -> items.get(i).getDecorationId() == decorationId)
                .findFirst().orElse(-1);

        if (found == -1) {
            throw new ProductNotInCartException(decorationId);
        }
        OrderItemDto item = items.get(found);

        updateCartAmount(cart.getCartId(), cart.getTotalAmount() - (item.getQuantity() * item.getPrice()));
        items.remove(found);
        cartItems.put(customerId, items);
        
        return items;
    }

    private void updateCartAmount(Long cartId, double newPrice) {
        Cart cart = cartRepository.findCartByCartId(cartId);
        cart.setTotalAmount(newPrice);

        cartRepository.save(cart);
    }

    public List<CartDto> getAll() {
        List<Cart> carts = cartRepository.findAll();

        return carts.stream().map(c -> cartMapper.mapToDto(c)).collect(Collectors.toList());
    }

    public List<OrderItemDto> getCartContent(Long customerId) {
        if (getCartItems().get(customerId) == null)
            throw new EmptyCartException(customerId);
        else return cartItems.get(customerId);
    }

    public void resetCart(Cart cart) {
        cartRepository.delete(cart);
    }
}
