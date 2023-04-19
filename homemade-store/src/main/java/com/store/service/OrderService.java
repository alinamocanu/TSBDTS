package com.store.service;

import com.store.domain.*;
import com.store.dto.OrderDto;
import com.store.dto.OrderItemDto;
import com.store.exception.BankAccountNotFoundException;
import com.store.exception.CartNotFoundException;
import com.store.exception.EmptyCartException;
import com.store.exception.InsufficientFundsException;
import com.store.mapper.OrderMapper;
import com.store.repository.OrderItemRepository;
import com.store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final CustomerService customerService;
    private final BankAccountService bankAccountService;
    private final CartService cartService;
    private final DecorationService decorationService;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    public OrderService(CustomerService customerService, BankAccountService bankAccountService, CartService cartService, DecorationService decorationService, OrderRepository orderRepository, OrderItemRepository orderItemRepository, OrderMapper orderMapper) {
        this.customerService = customerService;
        this.bankAccountService = bankAccountService;
        this.cartService = cartService;
        this.decorationService = decorationService;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderMapper = orderMapper;
    }

    public Order findOrderByOrderId(Long orderId){ return orderRepository.findOrderByOrderId(orderId);}

    public Order createOrder(Long customerId, List<OrderItemDto> orderItemDtos, String cardNumber) {
//        Customer customer = customerService.findCustomerByCustomerId(customerId);
//
//        BankAccount bankAccount = bankAccountService.findBankAccountByCustomerAndCardNumber(customer, cardNumber);
//        if(bankAccount == null){
//            throw new BankAccountNotFoundException(cardNumber);
//        }
//
//       // Cart cart = cartService.findCartByCustomer(customer);
//        if (cart == null)
//            throw new CartNotFoundException(customerId);
//        if (cart.getTotalAmount() == 0)
//            throw new EmptyCartException(customerId);
//
//        Order order = new Order();
//        order.setCustomer(customer);
//        order.setBankAccount(bankAccount);
//
//        double total = 0;
//        List<OrderItem> orderItems = new ArrayList<>();
//        for (OrderItemDto item : orderItemDtos) {
//            Decoration decoration = decorationService.findDecorationByDecorationId(item.getDecorationId());
//            total += decoration.getPrice() * item.getQuantity();
//            orderItems.add(new OrderItem(item.getQuantity(), item.getPrice(), decoration));
//        }
//        // check if there is enough money in the account to pay the order
//        boolean result = checkBalanceForOrder(bankAccount, total);
//        order.setTotalAmount(total);
//        order.setOrderPlaced(LocalDateTime.now());
//
//        // Saving entities in database
//        orderRepository.save(order);
//        orderItems.forEach(item -> {
//            item.setOrders(order);
//            orderItemRepository.save(item);
//        });
//
//        // withdraw money from bank account
//        bankAccountService.withdrawMoneyFromAccount(bankAccount.getCardNumber(), bankAccount.getBalance() - total);
//
//        cartService.resetCart(cart);
//        return orderMapper.mapToDto(order);
        return null;
    }

    public boolean checkBalanceForOrder(BankAccount bankAccount, double total) {
        if (bankAccount.getBalance() < total)
            throw new InsufficientFundsException(bankAccount.getCardNumber());
        else return true;
    }

    public OrderDto getOne(Long id) {
        return orderMapper.mapToDto(orderRepository.findOrderByOrderId(id));
    }

    public List<OrderDto> getOrdersByCustomer(Long customerId) {
        Customer customer = customerService.findCustomerByCustomerId(customerId);
        List<Order> orders = orderRepository.findAllByCustomer(customer);

        return orders.stream().map(o -> orderMapper.mapToDto(o)).collect(Collectors.toList());
    }
}
