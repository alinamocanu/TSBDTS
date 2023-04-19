package com.store.service;

import com.store.domain.BankAccount;
import com.store.domain.Customer;
import com.store.dto.BankAccountDto;
import com.store.exception.BankAccountNotFoundException;
import com.store.exception.CustomerNotFoundException;
import com.store.exception.DuplicateCardNumberException;
import com.store.mapper.BankAccountMapper;
import com.store.repository.BankAccountRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final CustomerService customerService;
    private final BankAccountMapper bankAccountMapper;

    public BankAccountService(BankAccountRepository bankAccountRepository, CustomerService customerService, BankAccountMapper bankAccountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerService = customerService;
        this.bankAccountMapper = bankAccountMapper;
    }

    public BankAccount findBankAccountByCustomerAndCardNumber(Customer customer, String cardNumber){
        return bankAccountRepository.findBankAccountByCustomerAndCardNumber(customer, cardNumber);
    }

    public BankAccountDto addBankAccount(BankAccountDto bankAccountDto, Long customerId) {
        Customer customer = customerService.findCustomerByCustomerId(customerId);
        if (customer == null){
            throw new CustomerNotFoundException(customerId);
        }
        bankAccountDto.setCustomer(customer);

        BankAccount bankAccount = bankAccountMapper.mapToEntity(bankAccountDto);
        Optional<BankAccount> existingCardNumber = Optional.ofNullable(bankAccountRepository.findBankAccountByCardNumber(bankAccount.getCardNumber()));
        existingCardNumber.ifPresent(e -> {
            throw new DuplicateCardNumberException(bankAccount.getCardNumber());
        });
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);

        return bankAccountMapper.mapToDto(savedBankAccount);
    }

    public List<BankAccountDto> getBankAccountsForCustomer(Long customerId) {
        Customer customer = customerService.findCustomerByCustomerId(customerId);
        List<BankAccount> bankAccounts =  bankAccountRepository.findBankAccountsByCustomer(customer);
        return bankAccounts.stream().map(b -> bankAccountMapper.mapToDto(b)).collect(Collectors.toList());
    }

    public boolean delete(Long customerId, String cardNumber) {
        Customer customer = customerService.findCustomerByCustomerId(customerId);
        BankAccount bankAccount = bankAccountRepository.findBankAccountByCustomerAndCardNumber(customer, cardNumber);
        if(bankAccount != null){
            bankAccountRepository.deleteBankAccountByCustomerAndCardNumber(customer, cardNumber);
            return true;
        }
        else {
            throw new BankAccountNotFoundException(cardNumber);
        }
    }

    public void withdrawMoneyFromAccount(String cardNumber, double balance) {
        BankAccount bankAccount = bankAccountRepository.findBankAccountByCardNumber(cardNumber);
        bankAccount.setBalance(balance);
        bankAccountRepository.save(bankAccount);
    }

}
