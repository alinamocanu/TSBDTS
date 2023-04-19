package com.store.util;

import com.store.domain.BankAccount;
import com.store.domain.Customer;


public class BankAccountUtil {
    public static BankAccount aBankAccount(String cardNumber) {
        return BankAccount.builder()
                .cardNumber(cardNumber)
                .bankAccountCVV(123)
                .balance(500)
                .customer(Customer.builder().customerId(1L).build())
                .build();
    }

    public static BankAccount aBankAccount(String cardNumber, Customer customer) {
        return BankAccount.builder()
                .cardNumber(cardNumber)
                .bankAccountCVV(123)
                .balance(500)
                .customer(customer)
                .build();
    }
}
