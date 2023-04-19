package com.store.util;

import com.store.domain.Customer;

public class CustomerUtil {
    public static Customer aCustomer(String firstName, String lastName) {
        return Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .address("Bucuresti, Calea Crangasi nr.9")
                .phoneNumber("0712345678")
                .build();
    }

    public static Customer aCustomer(long id) {
        return Customer.builder()
                .customerId(id)
                .firstName("Maria")
                .lastName("Nedelcu")
                .address("Bucuresti, Calea Crangasi nr.9")
                .phoneNumber("0712345678")
                .build();
    }
}
