package com.store.mapper;

import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.dto.OrderItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "decorationId", source = "decoration.decorationId")
    OrderItemDto mapToDto(OrderItem orderItem);

    @Mapping(target = "decoration.decorationId", source = "decorationId")
    OrderItem mapToEntity(OrderItemDto orderItemDto);
}
