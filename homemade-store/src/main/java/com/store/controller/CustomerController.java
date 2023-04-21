package com.store.controller;

import com.store.domain.*;
import com.store.domain.security.Authority;
import com.store.domain.security.User;
import com.store.dto.CustomerDto;
import com.store.mapper.CustomerMapper;
import com.store.repository.security.AuthorityRepository;
import com.store.service.CustomerService;
import com.store.service.security.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
@Slf4j
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthorityRepository authorityRepository;

    public CustomerController(CustomerService customerService, CustomerMapper customerMapper, PasswordEncoder passwordEncoder, UserService userService, AuthorityRepository authorityRepository) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.authorityRepository = authorityRepository;
    }

    @GetMapping("/access_denied")
    public String accessDenied() {
        return "access_denied";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        return "login-error";
    }

    @GetMapping(path = "/login")
    public String loginForm() {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm(Model model){
        log.info("Register customer...");
        model.addAttribute("customerDto", new CustomerDto());

        return "registerForm";
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @RequestBody @ModelAttribute CustomerDto customerDto, BindingResult bindingResult){
        ModelAndView viewRegister = new ModelAndView("registerForm");
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            return viewRegister;
        }

        Customer customer = customerMapper.mapToEntity(customerDto);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setEnabled(1);
        customerService.register(customer);

        Set<Authority> authorities = new HashSet<>();
        authorities.add(authorityRepository.findAuthorityByRole("ROLE_CUSTOMER"));
        User user = new User(customer.getCustomerId(), customer.getUsername(), customer.getPassword(), authorities, true, true, true, true);
        userService.save(user);

        ModelAndView model= new ModelAndView("login");

        return model;
    }

    @GetMapping("/customers")
    public ModelAndView getCustomers() {
        log.info("Getting customers...");
        ModelAndView modelAndView = new ModelAndView("customers");
        List<Customer> customers = customerService.getAllCustomers();
        modelAndView.addObject("customers", customers);

        return modelAndView;
    }

    @RequestMapping("/customers/delete/{id}")
    @Transactional
    public String deleteByCardNumber(@PathVariable Long id){
        log.info("Deleting customer...");
        customerService.deleteById(id);

        return "redirect:/customers";
    }

    @RequestMapping("/customers/update/{id}")
    public String update(@PathVariable Long id, Model model){
        log.info("Updating customer info...");
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);

        return "updateCustomerForm";
    }

    @PostMapping("/customers/update")
    @Transactional
    public String updateCustomer(@Valid @ModelAttribute Customer customer,
                                   BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            return "updateCustomerForm";
        }

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerService.save(customer);

        User user = userService.findByUsername(customer.getUsername());
        user.setPassword(customer.getPassword());
        userService.save(user);

        return "redirect:/customers";
    }
}
