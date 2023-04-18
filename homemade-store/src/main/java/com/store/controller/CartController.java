package com.store.controller;

import com.store.domain.Cart;
import com.store.domain.Customer;
import com.store.domain.Decoration;
import com.store.domain.Order;
import com.store.dto.OrderItemDto;
import com.store.exception.NegativeQuantityException;
import com.store.repository.CustomerRepository;
import com.store.service.BankAccountService;
import com.store.service.CartService;
import com.store.service.DecorationService;
import com.store.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final DecorationService decorationService;
    private final BankAccountService bankAccountService;
    private final OrderService orderService;
    private final CustomerRepository customerRepository;

    public CartController(CartService cartService, DecorationService decorationService, BankAccountService bankAccountService, OrderService orderService, CustomerRepository customerRepository) {
        this.cartService = cartService;
        this.decorationService = decorationService;
        this.bankAccountService = bankAccountService;
        this.orderService = orderService;
        this.customerRepository = customerRepository;
    }

    @RequestMapping
    public String get(){
        return "cart";
    }

    @PostMapping("/add")
    public String addDecorationToCart(@RequestParam Long decorationId, @RequestParam int quantity) {
        //Customer customer = (Customer) ((customernamePasswordAuthenticationToken) principal).getPrincipal();
        Customer customer = Customer.builder().customerId(1L).build();
        Customer existingCustomer = customerRepository.save(customer);
        if (quantity <= 0)
            throw new NegativeQuantityException();
        Decoration decoration = decorationService.findDecorationByDecorationId(decorationId);
        OrderItemDto item = new OrderItemDto(quantity, decoration.getPrice(), decorationId);
        cartService.addDecorationToCart(existingCustomer, item);

        return "redirect:/cart/";
    }

    @GetMapping()
    public String getCartContent(Principal principal, Model model) {
        //Customer customer = (Customer) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Customer customer = Customer.builder().customerId(1L).build();
        Cart cart = cartService.findCartByCustomer(customer);
        List<OrderItemDto> cartItems = cartService.getCartContent(customer.getCustomerId());

        model.addAttribute("items", cartItems);
        model.addAttribute("cart", cart != null ? cart : Cart.builder().totalAmount(0).build());
        model.addAttribute("accounts", bankAccountService.getBankAccountsForCustomer(customer.getCustomerId()));

        return "cart";
    }

    @Transactional
    @PostMapping("/delete")
    public String deleteItem(@RequestParam Long decorationId, Principal principal, Model model){
        //Customer customer = (Customer) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Customer customer = Customer.builder().customerId(1L).build();

        Cart cart = cartService.findCartByCustomer(customer);
        List<OrderItemDto> cartItems = cartService.getCartContent(customer.getCustomerId());
        model.addAttribute("items", cartItems);
        model.addAttribute("cart", cart);

        cartService.deleteOrderItemFromCart(cart, customer.getCustomerId(), decorationId);

        return "cart";
    }

    @PostMapping("/update")
    public ModelAndView updateDecorationQty(@RequestParam Long decorationId, @RequestParam int qty, Principal principal){
        //Customer customer = (Customer) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Customer customer = Customer.builder().customerId(1L).build();
        OrderItemDto item = cartService.getOrderItemByDecorationId(decorationId, customer.getCustomerId());
        Cart cart = cartService.findCartByCustomer(customer);

        int oldQty = cartService.updateItemQuantity(customer.getCustomerId(), item, qty);
        double newCost= cart.getTotalAmount() - oldQty * item.getPrice() + qty * item.getPrice();
        cartService.updateCartAmount(cart.getCartId(), newCost);

        ModelAndView model = new ModelAndView("cart");
        model.addObject("cart", cart != null ? cart : Cart.builder().totalAmount(0).build());
        model.addObject("accounts", bankAccountService.getBankAccountsForCustomer(customer.getCustomerId()));

        return model;
    }


    @PostMapping("/checkout")
    public ModelAndView orderCheckout(@RequestParam String cardNumber, Principal principal) {
        //Customer customer = (Customer) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Customer customer = Customer.builder().customerId(1L).build();
        Optional<Order> order = Optional.ofNullable(orderService.createOrder(customer.getCustomerId(), cartService.getCartItems().get(customer.getCustomerId()), cardNumber));

        ModelAndView modelAndView;
        if (order.isPresent()) {
            modelAndView = new ModelAndView("orders");
            modelAndView.addObject("orders", orderService.getOrdersByCustomer(customer.getCustomerId()));
        } else {
            modelAndView = new ModelAndView("cart");
            Cart cart = cartService.findCartByCustomer(customer);
            List<OrderItemDto> items = cartService.getCartContent(customer.getCustomerId());
            modelAndView.addObject("cart", cart != null ? cart : Cart.builder().totalAmount(0).build());
            modelAndView.addObject("items", items);
            modelAndView.addObject("accounts", bankAccountService.getBankAccountsForCustomer(customer.getCustomerId()));
        }

        return modelAndView;
    }


}
