package com.store.service.impl;

import com.store.domain.Cart;
import com.store.domain.Customer;
import com.store.domain.Decoration;
import com.store.domain.OrderItem;
import com.store.dto.OrderItemDto;
import com.store.exception.ProductNotInCartException;
import com.store.mapper.CartMapper;
import com.store.mapper.CustomerMapper;
import com.store.repository.CartRepository;
import com.store.service.CartService;
import com.store.service.DecorationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final Map<Long, List<OrderItemDto>> cartItems = new HashMap<>();
    private final CustomerMapper customerMapper;
    private final CartMapper cartMapper;
    private final DecorationService decorationService;

    public CartServiceImpl(CartRepository cartRepository, CustomerMapper customerMapper, CartMapper cartMapper, DecorationService decorationService) {
        this.cartRepository = cartRepository;
        this.customerMapper = customerMapper;
        this.cartMapper = cartMapper;
        this.decorationService = decorationService;
    }

    @Override
    public Cart addDecorationToCart(Customer customer, OrderItemDto item) {
        Cart cart = cartRepository.findCartByCustomer(customer);

        if (cart == null) {
            cart = createNewCart(customer, item.getQuantity() * item.getPrice(), item);
        } else {
            addItemToCart(customer, item);
            addToCartAmount(cart.getCartId(), item.getQuantity() * item.getPrice());
        }

        return cart;
    }

    @Override
    public Cart createNewCart(Customer customer, double cost, OrderItemDto item){
        Cart cart = Cart.builder().totalAmount(cost).customer(customer).build();
        List<OrderItemDto> cartItemsAux = new ArrayList<>();
        cartItemsAux.add(item);
        cartItems.put(customer.getCustomerId(), cartItemsAux);

        return cartRepository.save(cart);
    }

    @Override
    public void resetCart(Cart cart) {
        cartItems.put(cart.getCustomer().getCustomerId(), new ArrayList<>());
        cart.setTotalAmount(0);

        cartRepository.save(cart);
    }

    @Override
    public Cart findCartByCustomer(Customer customer){
        return cartRepository.findCartByCustomer(customer);
    }


    @Override
    public List<OrderItemDto> getCartContent(Long customerId) {
        return cartItems.get(customerId);
    }

    @Override
    public Map<Long, List<OrderItemDto>> getCartItems() {
        return cartItems;
    }

    @Override
    public OrderItemDto getOrderItemByDecorationId(Long decorationId, Long customerId) {
        List<OrderItemDto> items = cartItems.get(customerId);
        int index = IntStream.range(0, items.size())
                .filter(i -> items.get(i).getDecorationId() == decorationId)
                .findFirst()
                .orElse(-1);

        return items.get(index);
    }

    @Override
    public List<OrderItemDto> deleteOrderItemFromCart(Cart cart, Long customerId, Long decorationId) {
        List<OrderItemDto> items = cartItems.get(customerId);

        int index = IntStream.range(0, items.size())
                .filter(i -> items.get(i).getDecorationId() == decorationId)
                .findFirst()
                .orElse(-1);
        if (index == -1) {
            throw new ProductNotInCartException(decorationId);
        }

        OrderItemDto item = items.get(index);
        updateCartAmount(cart.getCartId(), cart.getTotalAmount() - (item.getQuantity() * item.getPrice()));
        items.remove(index);
        cartItems.put(customerId, items);

        return items;
    }

    @Override
    public void updateCartAmount(Long cartId, double cost) {
        Cart cart = cartRepository.findCartByCartId(cartId);
        cart.setTotalAmount(cost);

        cartRepository.save(cart);
    }

    @Override
    public int updateItemQuantity(Long customerId, OrderItemDto item, int quantity) {
        List<OrderItemDto> items = cartItems.get(customerId);

        int index = IntStream.range(0, items.size())
                .filter(i -> items.get(i).getDecorationId() == item.getDecorationId())
                .findFirst()
                .orElse(-1);
        if (index != -1) {
            OrderItemDto foundItem = items.get(index);
            int oldQty= foundItem.getQuantity();
            foundItem.setQuantity(quantity);
            items.set(index, foundItem);
            cartItems.put(customerId, items);

            return oldQty;
        }

        return -1;
    }

    public void addItemToCart(Customer customer, OrderItemDto item){
        List<OrderItemDto> items = cartItems.get(customer.getCustomerId());

        int index = IntStream.range(0, items.size())
                .filter(i -> items.get(i).getDecorationId() == item.getDecorationId())
                .findFirst()
                .orElse(-1);

        if (index != -1) {
            OrderItemDto existingItem = items.get(index);
            items.set(index, new OrderItemDto(existingItem.getQuantity() + item.getQuantity(), existingItem.getPrice(), existingItem.getDecorationId()));
        } else {
            items.add(item);
        }

        cartItems.put(customer.getCustomerId(), items);
    }

    private void addToCartAmount(Long cartId, double value) {
        Cart cart = cartRepository.findCartByCartId(cartId);
        cart.setTotalAmount(cart.getTotalAmount() + value);

        cartRepository.save(cart);
    }

}