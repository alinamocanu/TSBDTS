package com.store.util;

import com.store.domain.Cart;
import com.store.domain.Customer;

public class CartUtil {
    public static Cart aCart(Long id){
        return Cart.builder()
                .cartId(id)
                .totalAmount(200)
                .build();
    }

    public static Cart aCart(Long id, Customer customer){
        return Cart.builder()
                .cartId(id)
                .totalAmount(200)
                .customer(customer)
                .build();
    }

    public static Cart aCart(Customer customer){
        return Cart.builder()
                .totalAmount(200)
                .customer(customer)
                .build();
    }
}
