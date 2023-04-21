package com.store.controller;

import com.store.domain.Cart;
import com.store.domain.Customer;
import com.store.domain.Decoration;
import com.store.domain.Order;
import com.store.domain.security.User;
import com.store.dto.OrderItemDto;
import com.store.exception.NegativeQuantityException;
import com.store.repository.CustomerRepository;
import com.store.service.BankAccountService;
import com.store.service.CartService;
import com.store.service.DecorationService;
import com.store.service.OrderService;
import com.store.service.security.JpaUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
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

    @PostMapping("/add")
    public String addDecorationToCart(@RequestParam Long decorationId, @RequestParam int quantity, Principal principal) {
        log.info("Creating new cart...");
        UserDetails user = (UserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Customer customer = customerRepository.findCustomerByUsername(user.getUsername());

        if (quantity <= 0)
            throw new NegativeQuantityException();

        Decoration decoration = decorationService.findDecorationByDecorationId(decorationId);
        OrderItemDto item = new OrderItemDto(quantity, decoration.getPrice(), decorationId);
        cartService.addDecorationToCart(customer, item);

        return "redirect:/cart/";
    }

    @GetMapping()
    public String getCartContent(Principal principal, Model model) {
        UserDetails user = (UserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Customer customer = customerRepository.findCustomerByUsername(user.getUsername());

        Cart cart = cartService.findCartByCustomer(customer);
        List<OrderItemDto> cartItems = cartService.getCartContent(customer.getCustomerId());

        model.addAttribute("items", cartItems);
        model.addAttribute("cart", cart != null ? cart : Cart.builder().totalAmount(0).build());
        model.addAttribute("bankAccounts", bankAccountService.getBankAccountsForCustomer(customer));

        return "cart";
    }

    @Transactional
    @PostMapping("/delete")
    public String deleteItem(@RequestParam Long decorationId, Principal principal, Model model){
        log.info("Deleting cart...");
        UserDetails user = (UserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Customer customer = customerRepository.findCustomerByUsername(user.getUsername());

        Cart cart = cartService.findCartByCustomer(customer);
        List<OrderItemDto> cartItems = cartService.getCartContent(customer.getCustomerId());
        model.addAttribute("items", cartItems);
        model.addAttribute("cart", cart);

        cartService.deleteOrderItemFromCart(cart, customer.getCustomerId(), decorationId);

        return "cart";
    }

    @PostMapping("/update")
    public ModelAndView updateDecorationQty(@RequestParam Long decorationId, @RequestParam int qty, Principal principal){
        log.info("Updating cart...");
        UserDetails user = (UserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Customer customer = customerRepository.findCustomerByUsername(user.getUsername());

        OrderItemDto item = cartService.getOrderItemByDecorationId(decorationId, customer.getCustomerId());
        Cart cart = cartService.findCartByCustomer(customer);

        int oldQty = cartService.updateItemQuantity(customer.getCustomerId(), item, qty);
        double newCost= cart.getTotalAmount() - oldQty * item.getPrice() + qty * item.getPrice();
        cartService.updateCartAmount(cart.getCartId(), newCost);

        ModelAndView model = new ModelAndView("cart");
        model.addObject("cart", cart != null ? cart : Cart.builder().totalAmount(0).build());
        model.addObject("bankAccounts", bankAccountService.getBankAccountsForCustomer(customer));

        return model;
    }


    @RequestMapping(method=RequestMethod.POST, value = "/checkout")
    public ModelAndView orderCheckout(@RequestParam String cardNumber, Principal principal) {
        log.info("Checkout cart...");
        UserDetails user = (UserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Customer customer = customerRepository.findCustomerByUsername(user.getUsername());

        Optional<Order> order = orderService.createOrder(customer, cartService.getCartItems().get(customer.getCustomerId()), cardNumber);

        ModelAndView modelAndView;
        if (order.isPresent()) {
            modelAndView = new ModelAndView("orders");
            modelAndView.addObject("orders", orderService.getOrdersByCustomer(customer));
        } else {
            modelAndView = new ModelAndView("cart");
            Cart cart = cartService.findCartByCustomer(customer);
            List<OrderItemDto> items = cartService.getCartContent(customer.getCustomerId());
            modelAndView.addObject("cart", cart != null ? cart : Cart.builder().totalAmount(0).build());
            modelAndView.addObject("items", items);
            modelAndView.addObject("bankAccounts", bankAccountService.getBankAccountsForCustomer(customer));
        }

        return modelAndView;
    }
}
