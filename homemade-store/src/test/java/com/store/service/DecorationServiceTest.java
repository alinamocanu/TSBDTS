package com.store.service;

import com.store.domain.Decoration;
import com.store.domain.DecorationCategory;
import com.store.dto.DecorationDto;
import com.store.mapper.DecorationMapper;
import com.store.repository.DecorationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private DecorationService decorationService;

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
        Decoration savedCustomer = aDecoration(1L);

        when(decorationMapper.mapToEntity(decorationDto)).thenReturn(decoration);
        when(decorationRepository.save(decoration)).thenReturn(savedCustomer);
        when(decorationMapper.mapToDto(savedCustomer)).thenReturn(decorationDto);

        //Act
        //DecorationDto result = decorationService.add(decorationDto, "christmas");

        //Assert
        //assertThat(result).isNotNull();
        verify(decorationMapper, times(1)).mapToEntity(decorationDto);
        verify(decorationMapper, times(1)).mapToDto(savedCustomer);
        verify(decorationRepository, times(1)).save(decoration);

        verifyNoMoreInteractions(decorationMapper, decorationRepository);
    }

    @Test
    void getOne() {
        //arrange
        Long id = Long.valueOf(1);
        DecorationDto decorationDto = aDecorationDto(id);
        Decoration decoration = aDecoration(1);

        when(decorationRepository.findDecorationByDecorationId(id)).thenReturn(decoration);
        when(decorationMapper.mapToDto(decoration)).thenReturn(decorationDto);

        //Act
        //DecorationDto result = decorationService.getOne(id);

        //Assert
        //assertEquals(decorationDto, result);
    }

    @Test
    void getAllDecorations() {
        //arrange
        Long id1 = Long.valueOf(1);
        DecorationDto decorationDto1 = aDecorationDto(id1);
        Long id2 = Long.valueOf(2);
        DecorationDto decorationDto2 = aDecorationDto(id2);
        List<DecorationDto> decorationDtos = new ArrayList<>(){{
            add(decorationDto1);
            add(decorationDto2);
        }};

        Decoration decoration1 = aDecoration(1);
        Decoration decoration2 = aDecoration(2);
        List<Decoration> decorations = new ArrayList<>(){{
            add(decoration1);
            add(decoration2);
        }};

        when(decorationRepository.findAll()).thenReturn(decorations);
        when(decorationMapper.mapToDto(decoration1)).thenReturn(decorationDto1);
        when(decorationMapper.mapToDto(decoration2)).thenReturn(decorationDto2);

        //Act
        //List<DecorationDto> result = decorationService.getAllDecorations();

        //Assert
        //assertEquals(decorationDtos, result);
    }

    @Test
    void getByCategory() {
        //arrange
        Long id1 = Long.valueOf(1);
        DecorationDto decorationDto1 = aDecorationDto(id1);
        Long id2 = Long.valueOf(2);
        DecorationDto decorationDto2 = aDecorationDto(id2);
        List<DecorationDto> decorationDtos = new ArrayList<>(){{
            add(decorationDto1);
            add(decorationDto2);
        }};

        Decoration decoration1 = aDecoration(1);
        Decoration decoration2 = aDecoration(2);
        List<Decoration> decorations = new ArrayList<>(){{
            add(decoration1);
            add(decoration2);
        }};

        when(decorationRepository.findAllByCategory(DecorationCategory.CHRISTMAS)).thenReturn(decorations);
        when(decorationMapper.mapToDto(decoration1)).thenReturn(decorationDto1);
        when(decorationMapper.mapToDto(decoration2)).thenReturn(decorationDto2);

        //Act
        //List<DecorationDto> result = decorationService.getByCategory(DecorationCategory.CHRISTMAS);

        //Assert
        //assertEquals(decorationDtos, result);
    }
}