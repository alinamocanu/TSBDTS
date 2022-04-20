package com.store.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.domain.DecorationCategory;
import com.store.dto.CustomerDto;
import com.store.dto.DecorationDto;
import com.store.service.DecorationService;
import com.store.util.CustomerDtoUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static com.store.util.DecorationDtoUtil.aDecorationDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = DecorationController.class)
class DecorationControllerTest {

    @MockBean
    private DecorationService decorationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_addDecoration() throws Exception {
        //Arrange
        DecorationDto decorationDto = aDecorationDto(1L);
        String category = "christmas";
        //when(decorationService.add(any(), any())).thenReturn(decorationDto);

        //Act
        MvcResult result = mockMvc.perform(post("/decorations/" +category)
                        .content(objectMapper.writeValueAsString(decorationDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(decorationDto));
    }

    @Test
    void test_getOneDecoration() throws Exception {
        Long id = Long.valueOf(2);
        DecorationDto dto = aDecorationDto(id);
        //when(decorationService.getOne(id)).thenReturn(dto);

        mockMvc.perform(get("/decorations/"+id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("decorationId", is(dto.getDecorationId().intValue())))
                .andExpect(jsonPath("$.decorationName", is(dto.getDecorationName())))
                .andExpect(jsonPath("$.price", is(dto.getPrice())));
    }

    @Test
    void getDecorationByCategory() throws Exception {
        DecorationCategory category = DecorationCategory.CHRISTMAS;
        DecorationDto dto1 = aDecorationDto(1L);
        DecorationDto dto2 = aDecorationDto(2L);
        List<DecorationDto> decorationDtos = new ArrayList<>(){{
            add(dto1);
            add(dto2);
        }};

        //when(decorationService.getByCategory(category)).thenReturn(decorationDtos);

        mockMvc.perform(get("/decorations/filter/" + category))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(convertObjectToJsonString(decorationDtos)));
    }

    private String convertObjectToJsonString(List<DecorationDto> decorationDtos) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(decorationDtos);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}