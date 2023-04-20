package com.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.domain.Customer;
import com.store.dto.CustomerDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {
    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Testing creating a customer")
    void test_createCustomer_happyFlow() throws Exception {
        //Arrange
        CustomerDto dto = CustomerDtoUtil.aCustomerDto("Ana", "Popescu");
        //when(customerService.create(any())).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(post("/customers")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }

    @Test
    void test_getOneCustomer() throws Exception {
        Long id = Long.valueOf(2);
        CustomerDto dto = CustomerDtoUtil.aCustomerDto(id);
        //when(customerService.getOne(id)).thenReturn(dto);

        mockMvc.perform(get("/customers/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("customerId", is(dto.getCustomerId().intValue())))
                .andExpect(jsonPath("$.lastName", is(dto.getLastName())))
                .andExpect(jsonPath("$.firstName", is(dto.getFirstName())));
    }
}
