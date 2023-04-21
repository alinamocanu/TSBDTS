package com.store.service;

import com.store.domain.Decoration;
import com.store.domain.DecorationCategory;
import com.store.dto.DecorationDto;
import com.store.mapper.DecorationMapper;
import com.store.repository.DecorationRepository;
import com.store.service.impl.DecorationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

import static com.store.util.DecorationDtoUtil.aDecorationDto;
import static com.store.util.DecorationUtil.aDecoration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class DecorationServiceTest {
    @Mock
    private DecorationRepository decorationRepository;
    @Mock
    private DecorationMapper decorationMapper;
    @InjectMocks
    private DecorationServiceImpl decorationService;

    /*
    @BeforeEach
    public void setUp() throws Exception {
        productService = new ProductServiceImpl(productRepository);
    }
    */

    @Test
    public void findProducts() {
        List<Decoration> productsRet = new ArrayList<Decoration>();
        Decoration product = new Decoration();
        product.setDecorationId(1L);
        productsRet.add(product);

        when(decorationRepository.findAll()).thenReturn(productsRet);
        List<Decoration> products = decorationRepository.findAll();
        assertEquals(products.size(), 1);
        verify(decorationRepository, times(1)).findAll();
    }

    @Test
    void findDecorationByDecorationId() {
        //arrange
        Long id = Long.valueOf(1);
        Decoration decoration = aDecoration(1);

        when(decorationRepository.findDecorationByDecorationId(id)).thenReturn(decoration);

        //Act
        Decoration result = decorationService.findDecorationByDecorationId(id);

        //Assert
        assertEquals(decoration, result);
    }

    @Test
    void add() {
        //Arrange
        DecorationDto decorationDto = aDecorationDto(1L);
        Decoration decoration = aDecoration(1L);

        when(decorationMapper.mapToEntity(decorationDto)).thenReturn(decoration);
        when(decorationRepository.save(any())).thenReturn(decoration);

        Decoration result = decorationService.save(decorationDto);

        //Assert
        assertThat(result).isNotNull();
        verify(decorationRepository, times(1)).save(decoration);
    }

}