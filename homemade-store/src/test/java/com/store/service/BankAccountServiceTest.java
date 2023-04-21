package com.store.service;

import com.store.domain.BankAccount;
import com.store.domain.Customer;
import com.store.dto.BankAccountDto;

import com.store.repository.BankAccountRepository;
import com.store.service.impl.BankAccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.store.util.BankAccountDtoUtil.aBankAccountDto;
import static com.store.util.BankAccountUtil.aBankAccount;
import static com.store.util.CustomerUtil.aCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {
    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @Test
    void addBankAccount() {
        //Arrange
        Long customerId = 1L;
        String cardNumber = "1234 5678 9012 3456";
        Customer customer = aCustomer(customerId);
        BankAccountDto bankAccountDto = aBankAccountDto(cardNumber);
        BankAccount bankAccount = aBankAccount(cardNumber);

        when(bankAccountRepository.findBankAccountByCardNumber(cardNumber)).thenReturn(null);
        when(bankAccountRepository.save(any())).thenReturn(bankAccount);

        //Act
        BankAccount result = bankAccountService.addBankAccount(bankAccountDto);

        //Assert
        assertThat(result).isNotNull();
    }

    @Test
    void getBankAccountsForCustomer() {
        //Arrange
        Long customerId = 1L;
        Customer customer = aCustomer(customerId);

        BankAccount bankAccount1 = aBankAccount("1234 5678 9012 3456", customer);
        BankAccount bankAccount2 = aBankAccount("2612 3671 9022 1569", customer);
        List<BankAccount> bankAccounts = new ArrayList<>(){{
            add(bankAccount1);
            add(bankAccount2);
        }};


        when(bankAccountRepository.findBankAccountsByCustomer(customer)).thenReturn(bankAccounts);

        //Act
        List<BankAccount> result = bankAccountService.getBankAccountsForCustomer(customer);

        //Assert
        assertEquals(bankAccounts, result);
    }

    @Test
    void withdrawMoneyFromAccount() {
        //Arrange
        String cardNumber = "1234 5678 9012 3456";
        BankAccount bankAccount = aBankAccount(cardNumber);

        when(bankAccountRepository.findBankAccountByCardNumber(cardNumber)).thenReturn(bankAccount);
        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);

        //Act
        bankAccountService.withdrawMoneyFromAccount(cardNumber, 100);

        // Assert
        assertEquals( 100, bankAccount.getBalance());
    }
}