package com.store.mapper;

import com.store.domain.Customer;
import com.store.dto.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto mapToDto(Customer customer);
    Customer mapToEntity(CustomerDto customerDto);
}
