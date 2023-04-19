package com.store.mapper;

import com.store.domain.Cart;
import com.store.dto.CartDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "customerId", source = "customer.customerId")
    CartDto mapToDto(Cart cart);

    @Mapping(target = "customer.customerId", source = "customerId")
    Cart mapToEntity(CartDto cartDto);
}
