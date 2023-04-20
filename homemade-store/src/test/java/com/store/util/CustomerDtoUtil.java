package com.store.util;

import com.store.dto.CustomerDto;

public class CustomerDtoUtil {
    public static CustomerDto aCustomerDto(String firstName, String lastName) {
        return CustomerDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .address("Bucuresti, Calea Crangasi nr.9")
                .username("betty")
                .build();
    }

    public static CustomerDto aCustomerDto(Long id) {
        return CustomerDto.builder()
                .customerId(id)
                .firstName("Ana")
                .lastName("Popescu")
                .address("Bucuresti, Calea Crangasi nr.9")
                .username("betty")
                .build();
    }
}
