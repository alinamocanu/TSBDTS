package com.store.mapper;

import com.store.domain.Order;
import com.store.dto.OrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto mapToDto(Order order);
    Order mapToEntity(OrderDto orderDto);
}
