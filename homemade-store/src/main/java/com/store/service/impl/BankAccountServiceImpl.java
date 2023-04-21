package com.store.service.impl;

import com.store.domain.BankAccount;
import com.store.domain.Customer;
import com.store.domain.Decoration;
import com.store.dto.BankAccountDto;
import com.store.exception.DuplicateCardNumberException;
import com.store.exception.ResourceNotFoundException;
import com.store.mapper.BankAccountMapper;
import com.store.repository.BankAccountRepository;
import com.store.service.BankAccountService;
import com.store.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final CustomerService customerService;
    private final BankAccountMapper bankAccountMapper;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, CustomerService customerService, BankAccountMapper bankAccountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerService = customerService;
        this.bankAccountMapper = bankAccountMapper;
    }

    @Override
    public BankAccount addBankAccount(BankAccountDto bankAccountDto) {
        Optional<BankAccount> existingAccountCardNumber = Optional.ofNullable(bankAccountRepository.findBankAccountByCardNumber(bankAccountDto.getCardNumber()));
        existingAccountCardNumber.ifPresent(e -> {
            throw new DuplicateCardNumberException(bankAccountDto.getCardNumber());
        });

        return bankAccountRepository.save(bankAccountMapper.mapToEntity(bankAccountDto));
    }

    @Override
    public List<BankAccount> getBankAccountsForCustomer(Customer customer) {
        return bankAccountRepository.findBankAccountsByCustomer(customer);
    }

    @Override
    public BankAccount findBankAccountByCardNumber(String cardNumber) {
        Optional<BankAccount> accountOptional = Optional.ofNullable(bankAccountRepository.findBankAccountByCardNumber(cardNumber));
        if (accountOptional.isPresent()) {
            return accountOptional.get();
        } else {
            throw new ResourceNotFoundException("Bank account with card number "+cardNumber+ " not found.");
        }
    }

    @Override
    public void withdrawMoneyFromAccount(String cardNumber, double balance) {
        BankAccount bankAccount = findBankAccountByCardNumber(cardNumber);
        bankAccount.setBalance(balance);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void deleteByCardNumber(String cardNumber) {
        bankAccountRepository.deleteBankAccountByCardNumber(cardNumber);
    }

    @Override
    public BankAccount save(BankAccount bankAccount) {
        Optional<BankAccount> existingAccountCardNumber = Optional.ofNullable(bankAccountRepository.findBankAccountByCardNumber(bankAccount.getCardNumber()));
        existingAccountCardNumber.ifPresent(e -> {
            throw new DuplicateCardNumberException(bankAccount.getCardNumber());
        });

        return bankAccountRepository.save(bankAccount);
    }
    
    public BankAccount update(BankAccount bankAccount){
        return  bankAccountRepository.save(bankAccount);
    }
}
