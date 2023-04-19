package com.store.util;

import com.store.domain.BankAccount;
import com.store.domain.Customer;
import com.store.dto.BankAccountDto;

public class BankAccountDtoUtil {
    public static BankAccountDto aBankAccountDto(String cardNumber) {
        return BankAccountDto.builder()
                .cardNumber(cardNumber)
                .bankAccountCVV(123)
                .balance(500)
                .customer(Customer.builder().customerId(1L).build())
                .build();
    }

    public static BankAccountDto aBankAccountDto(String cardNumber, Customer customer) {
        return BankAccountDto.builder()
                .cardNumber(cardNumber)
                .bankAccountCVV(123)
                .balance(500)
                .customer(customer)
                .build();
    }
}
