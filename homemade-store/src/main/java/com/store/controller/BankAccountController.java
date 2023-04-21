package com.store.controller;

import com.store.domain.BankAccount;
import com.store.domain.Customer;
import com.store.domain.Decoration;
import com.store.domain.DecorationCategory;
import com.store.dto.BankAccountDto;
import com.store.repository.CustomerRepository;
import com.store.service.BankAccountService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/bankAccounts")
public class BankAccountController {
    private final BankAccountService bankAccountService;
    private final CustomerRepository customerRepository;

    public BankAccountController(BankAccountService bankAccountService, CustomerRepository customerRepository) {
        this.bankAccountService = bankAccountService;
        this.customerRepository = customerRepository;
    }

    @RequestMapping("/new")
    public String newAccount(Model model) {
        log.info("Adding new bank account...");
        model.addAttribute("bankAccountDto", new BankAccountDto());

        return "bankAccountForm";
    }

    @PostMapping()
    public ModelAndView createBankAccount(@Valid @RequestBody @ModelAttribute BankAccountDto bankAccountDto,
            BindingResult bindingResult,
            Principal principal) {

        ModelAndView modelAndView = new ModelAndView("bankAccounts");

        if (bindingResult.hasErrors()) {
            return modelAndView;
        }

        UserDetails user = (UserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Customer customer = customerRepository.findCustomerByUsername(user.getUsername());

        bankAccountDto.setCustomer(customer);
        bankAccountService.addBankAccount(bankAccountDto);

        List<BankAccount> bankAccountsFound = bankAccountService.getBankAccountsForCustomer(customer);
        modelAndView.addObject("bankAccounts", bankAccountsFound);

        return modelAndView;
    }

    @GetMapping()
    public ModelAndView getBankAccountsForCustomer(Principal principal) {
        log.info("Get bank accounts for customers...");
        UserDetails user = (UserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Customer customer = customerRepository.findCustomerByUsername(user.getUsername());

        ModelAndView modelAndView = new ModelAndView("bankAccounts");
        List<BankAccount> accountsFound = bankAccountService.getBankAccountsForCustomer(customer);
        modelAndView.addObject("bankAccounts", accountsFound);

        return modelAndView;
    }

    @Transactional
    @RequestMapping("/delete/{cardNumber}")
    public String deleteByCardNumber(@PathVariable String cardNumber){
        log.info("Deleting bank account...");
        bankAccountService.deleteByCardNumber(cardNumber);

        return "redirect:/bankAccounts";
    }

    @RequestMapping("/update/{cardNumber}")
    public String update(@PathVariable String cardNumber, Model model){
        log.info("Updating bank account...");
        BankAccount bankAccount = bankAccountService.findBankAccountByCardNumber(cardNumber);
        model.addAttribute("bankAccount", bankAccount);

        return "bankAccountUpdate";
    }

    @PostMapping("/update")
    @Transactional
    public String updateBankAccount(@Valid @ModelAttribute BankAccount bankAccount,
                                   BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
             return "bankAccountUpdate";
        }
        UserDetails user = (UserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Customer customer = customerRepository.findCustomerByUsername(user.getUsername());

        bankAccount.setCustomer(customer);
        bankAccountService.update(bankAccount);

        return "redirect:/bankAccounts";
    }

}
