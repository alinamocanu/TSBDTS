package com.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.domain.Customer;
import com.store.dto.CustomerDto;
import com.store.mapper.CustomerMapper;
import com.store.service.CustomerService;
import com.store.util.CustomerDtoUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("h2")
public class CustomerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    public void getCustomers() throws Exception{

        mockMvc.perform(get("/customers/"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers"));
    }

    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    public void deleteCustomer() throws Exception{

        mockMvc.perform(post("/customers/delete/{id}",20))
                .andExpect(status().isFound());
    }
}
