package com.store.controller;


import com.store.domain.Decoration;

import com.store.service.DecorationService;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.ui.Model;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("h2")
class DecorationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    DecorationService decorationService;

    @MockBean
    Model model;

    @Disabled
    @Test
    public void showByIdMvc() throws Exception {

        mockMvc.perform(get("/decorations/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("decorationDetails"));
    }



    @Test
    @WithMockUser(username = "guest", password = "12345", roles = "GUEST")
    public void showByIdMockMvc() throws Exception {
        Long id = 1L;
        Decoration decorationTest = new Decoration();
        decorationTest.setDecorationId(id);
        decorationTest.setDecorationName("test");

        when(decorationService.findDecorationByDecorationId(id)).thenReturn(decorationTest);

        mockMvc.perform(get("/decorations/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("decorationDetails"))
                .andExpect(model().attribute("decoration", decorationTest))
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }



    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    public void deleteByIdMockMvc() throws Exception {

        mockMvc.perform(get("/decorations/delete/{id}", "1"))
                .andExpect(redirectedUrl("/decorations"));
    }

}