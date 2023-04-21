package com.store.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.domain.BankAccount;
import com.store.domain.Customer;
import com.store.dto.BankAccountDto;
import com.store.dto.CustomerDto;
import com.store.mapper.BankAccountMapper;
import com.store.repository.BankAccountRepository;
import com.store.repository.CustomerRepository;
import com.store.service.BankAccountService;
import com.store.service.CustomerService;
import com.store.service.impl.BankAccountServiceImpl;
import com.store.util.CustomerDtoUtil;
import com.store.validator.OnlyDigitsAndSpacesValidator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static com.store.util.BankAccountDtoUtil.aBankAccountDto;
import static com.store.util.BankAccountUtil.aBankAccount;
import static com.store.util.CustomerUtil.aCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("h2")
class BankAccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BankAccountServiceImpl bankAccountService;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private BankAccountMapper bankAccountMapper;
    @MockBean
    private  BankAccountRepository bankAccountRepository;
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    Model model;
    @MockBean
    private OnlyDigitsAndSpacesValidator onlyDigitsAndSpacesValidator;

    @Test
    @DisplayName("Create a new Bank Account")
    @WithMockUser(username = "customer", password = "1234", roles = "CUSTOMER")
    @Disabled
    public void test_createBankAccountHappyFlow() throws Exception {
        Long customerId = 1L;
        Customer customer = aCustomer(customerId.intValue());
        customer.setUsername("customer");
        BankAccount bankAccount = aBankAccount("1234567891234567", customer);

        when(bankAccountService.addBankAccount(any())).thenReturn(bankAccount);
        when(customerRepository.findCustomerByUsername(any())).thenReturn(customer);

        mockMvc.perform(post("/bankAccounts/"))
                        .andExpect(status().isCreated());


    }
}