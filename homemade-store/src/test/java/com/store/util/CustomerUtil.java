package com.store.util;

import com.store.domain.Customer;

public class CustomerUtil {
    public static Customer aCustomer(String firstName, String lastName) {
        return Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .address("Bucuresti, Calea Crangasi nr.9")
                .username("betty")
                .build();
    }

    public static Customer aCustomer(long id) {
        return Customer.builder()
                .customerId(id)
                .firstName("Maria")
                .lastName("Nedelcu")
                .address("Bucuresti, Calea Crangasi nr.9")
                .username("betty")
                .build();
    }
}
