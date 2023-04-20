package com.store.service;

import com.store.domain.BankAccount;
import com.store.domain.Customer;
import com.store.dto.BankAccountDto;
import com.store.mapper.BankAccountMapper;
import com.store.repository.BankAccountRepository;
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
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {
    @Mock
    private BankAccountRepository bankAccountRepository;
    @Mock
    private BankAccountMapper bankAccountMapper;
    @Mock
    private CustomerService customerService;
    @InjectMocks
    private BankAccountService bankAccountService;

    @Test
    void addBankAccount() {
        //Arrange
        Long customerId = 1L;
        String cardNumber = "1234 5678 9012 3456";
        Customer customer = aCustomer(customerId);
        BankAccountDto bankAccountDto = aBankAccountDto(cardNumber);
        BankAccount bankAccount = aBankAccount(cardNumber);
        BankAccount savedBankAccount = aBankAccount(cardNumber);

        when(bankAccountMapper.mapToEntity(bankAccountDto)).thenReturn(bankAccount);
        //when(customerService.findCustomerByCustomerId(customerId)).thenReturn(customer);
        when(bankAccountRepository.findBankAccountByCardNumber(cardNumber)).thenReturn(null);
        when(bankAccountRepository.save(bankAccount)).thenReturn(savedBankAccount);
        when(bankAccountMapper.mapToDto(savedBankAccount)).thenReturn(bankAccountDto);

        //Act
        //BankAccountDto result = bankAccountService.addBankAccount(bankAccountDto, customerId);

        //Assert
        //assertThat(result).isNotNull();
        verify(bankAccountMapper, times(1)).mapToEntity(bankAccountDto);
        verify(bankAccountMapper, times(1)).mapToDto(savedBankAccount);
        verify(bankAccountRepository, times(1)).save(bankAccount);

        verifyNoMoreInteractions(bankAccountMapper, bankAccountRepository);
    }

    @Test
    void getBankAccountsForCustomer() {
        //Arrange
        Long customerId = 1L;
        Customer customer = aCustomer(customerId);
        BankAccountDto bankAccountDto1 = aBankAccountDto("1234 5678 9012 3456", customer);
        BankAccountDto bankAccountDto2 = aBankAccountDto("2612 3671 9022 1569", customer);
        BankAccount bankAccount1 = aBankAccount("1234 5678 9012 3456", customer);
        BankAccount bankAccount2 = aBankAccount("2612 3671 9022 1569", customer);
        List<BankAccount> bankAccounts = new ArrayList<>(){{
            add(bankAccount1);
            add(bankAccount2);
        }};
        List<BankAccountDto> bankAccountDtos = new ArrayList<>(){{
            add(bankAccountDto1);
            add(bankAccountDto2);
        }};


        //when(customerService.findCustomerByCustomerId(customerId)).thenReturn(customer);
        when(bankAccountRepository.findBankAccountsByCustomer(customer)).thenReturn(bankAccounts);
        when(bankAccountMapper.mapToDto(bankAccount1)).thenReturn(bankAccountDto1);
        when(bankAccountMapper.mapToDto(bankAccount2)).thenReturn(bankAccountDto2);

        //Act
        //List<BankAccountDto> result = bankAccountService.getBankAccountsForCustomer(customerId);

        //Assert
        //assertEquals(bankAccountDtos, result);
    }

    @Test
    void delete() {
        //Arrange
        Long customerId = 1L;
        String cardNumber = "1234 5678 9012 3456";
        Customer customer = aCustomer(customerId);
        BankAccount bankAccount = aBankAccount(cardNumber, customer);

//        when(customerService.findCustomerByCustomerId(customerId)).thenReturn(customer);
//        when(bankAccountService.findBankAccountByCustomerAndCardNumber(customer, cardNumber)).thenReturn(bankAccount);
//        doNothing().when(bankAccountRepository).deleteBankAccountByCustomerAndCardNumber(customer, cardNumber);
//
//        //Act
//        Boolean ok = bankAccountService.delete(customerId, cardNumber);

        //Assert
        //assertEquals(true, ok);
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