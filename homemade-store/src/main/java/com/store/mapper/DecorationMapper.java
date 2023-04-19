package com.store.mapper;

import com.store.domain.Decoration;
import com.store.dto.DecorationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DecorationMapper {
    DecorationDto mapToDto(Decoration decoration);
    Decoration mapToEntity(DecorationDto decorationDto);
}
