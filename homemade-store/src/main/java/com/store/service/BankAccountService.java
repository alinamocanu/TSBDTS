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

public interface BankAccountService {

    BankAccount addBankAccount(BankAccountDto bankAccountDto);

    List<BankAccount> getBankAccountsForCustomer(Customer customer);

    BankAccount findBankAccountByCardNumber(String cardNumber);

    void withdrawMoneyFromAccount(String cardNumber, double balance);

    void deleteByCardNumber(String cardNumber);

    BankAccount save(BankAccount bankAccount);

    BankAccount update(BankAccount bankAccount);
}
