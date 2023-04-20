package com.store.service.impl;

import com.store.domain.*;
import com.store.dto.OrderItemDto;
import com.store.exception.*;
import com.store.repository.OrderItemRepository;
import com.store.repository.OrderRepository;
import com.store.service.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final DecorationService decorationService;
    private final BankAccountService bankAccountService;
    private final CartService cartService;
    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CustomerService customerService, DecorationService decorationService, BankAccountService bankAccountService, CartService cartService, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.decorationService = decorationService;
        this.bankAccountService = bankAccountService;
        this.cartService = cartService;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Order findOrderById(Long id) {
        return orderRepository.findOrderByOrderId(id);
    }

    @Override
    public List<Order> getOrdersByCustomer(Customer customer) {
        return orderRepository.findAllByCustomer(customer);
    }

    @Override
    public Optional<Order> createOrder(Customer customer, List<OrderItemDto> orderItemDtos, String cardNumber) {

        BankAccount bankAccount = bankAccountService.findBankAccountByCardNumber(cardNumber);
        if(bankAccount == null){
            throw new BankAccountNotFoundException(cardNumber);
        }

        Cart cart = cartService.findCartByCustomer(customer);
        if (cart == null)
            throw new CartNotFoundException(customer.getCustomerId());
        if (cart.getTotalAmount() == 0)
            throw new EmptyCartException(customer.getCustomerId());

        Order order = new Order();
        order.setCustomer(customer);
        order.setBankAccount(bankAccount);

        double total = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto item : orderItemDtos) {
            Decoration decoration = decorationService.findDecorationByDecorationId(item.getDecorationId());
            total += decoration.getPrice() * item.getQuantity();
            orderItems.add(new OrderItem(item.getQuantity(), item.getPrice(), decoration));
        }

        if (total == 0)
            return Optional.empty();

        boolean result = checkBalanceForOrder(bankAccount, total);
        order.setTotalAmount(total);
        order.setOrderPlaced(LocalDateTime.now());

        orderRepository.save(order);
        orderItems.forEach(item -> {
            item.setOrders(order);
            orderItemRepository.save(item);
        });

        bankAccountService.withdrawMoneyFromAccount(bankAccount.getCardNumber(), bankAccount.getBalance() - total);

        cartService.resetCart(cart);

        return Optional.of(order);
    }

    @Override
    public boolean checkBalanceForOrder(BankAccount bankAccount, double total) {
        if (bankAccount.getBalance() < total)
            throw new InsufficientFundsException(bankAccount.getCardNumber());
        else return true;
    }

}
