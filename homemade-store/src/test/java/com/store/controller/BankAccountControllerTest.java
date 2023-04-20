package com.store.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.domain.BankAccount;
import com.store.domain.Customer;
import com.store.dto.BankAccountDto;
import com.store.dto.CustomerDto;
import com.store.mapper.BankAccountMapper;
import com.store.repository.BankAccountRepository;
import com.store.service.BankAccountService;
import com.store.service.CustomerService;
import com.store.util.CustomerDtoUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static com.store.util.BankAccountDtoUtil.aBankAccountDto;
import static com.store.util.BankAccountUtil.aBankAccount;
import static com.store.util.CustomerUtil.aCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BankAccountController.class)
class BankAccountControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @MockBean
//    private BankAccountService bankAccountService;
//    @MockBean
//    private CustomerService customerService;
//    @MockBean
//    private BankAccountRepository bankAccountRepository;
//
//    @Test
//    @DisplayName("Create a new Bank Account")
//    public void test_createBankAccountHappyFlow() throws Exception {
//        Long customerId = 1L;
//        Customer customer = aCustomer(customerId.intValue());
//        BankAccountDto request = aBankAccountDto("1234 5678 9123 4567", customer);
//
//        when(bankAccountService.addBankAccount(any(), any())).thenReturn(request);
//        when(customerService.findCustomerByCustomerId(customer.getCustomerId())).thenReturn(customer);
//
//        MvcResult result = mockMvc.perform(post("/bankAccounts/" + customerId.intValue())
//                        .content(objectMapper.writeValueAsString(request))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        //Assert
//        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(request));
//    }
//
//    @Test
//    @DisplayName("Get bank accounts for a customer")
//    void test_getBankAccountsByCustomer() throws Exception {
//        Long customerId = Long.valueOf(2);
//        Customer customer = aCustomer(customerId.intValue());
//        BankAccount bankAccount1= aBankAccount("1234 5678 9123 4567", customer);
//        BankAccount bankAccount2 = aBankAccount("1234 5678 9123 4568", customer);
//        List<BankAccount> bankAccountList = new ArrayList<>(){{
//            add(bankAccount1);
//            add(bankAccount2);
//        }};
//
//        BankAccountDto bankAccountDto1 = aBankAccountDto("1234 5678 9123 4567", customer);
//        BankAccountDto bankAccountDto2 = aBankAccountDto("1234 5678 9123 4568", customer);
//        List<BankAccountDto> bankAccountDtoList = new ArrayList<>(){{
//            add(bankAccountDto1);
//            add(bankAccountDto2);
//        }};
//
//        when(customerService.findCustomerByCustomerId(any())).thenReturn(customer);
//        when(bankAccountRepository.findBankAccountsByCustomer(customer)).thenReturn(bankAccountList);
//        when(bankAccountService.getBankAccountsForCustomer(customerId)).thenReturn(bankAccountDtoList);
//
//        MvcResult result = mockMvc.perform(get("/bankAccounts/" + customerId.intValue()))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andReturn();
//        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(bankAccountDtoList));
//
//    }

}