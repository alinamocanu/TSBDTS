package com.store.repository;

import com.store.domain.BankAccount;
import com.store.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
    BankAccount findBankAccountByCardNumber(String cardNumber);

    List<BankAccount> findBankAccountsByCustomer(Customer customer);

    BankAccount findBankAccountByCustomerAndCardNumber(Customer customer, String cardNumber);

    void deleteBankAccountByCustomerAndCardNumber(Customer customer, String cardNumber);
}
