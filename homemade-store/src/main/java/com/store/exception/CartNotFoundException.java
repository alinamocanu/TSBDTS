package com.store.exception;

public class CartNotFoundException extends RuntimeException{
    public CartNotFoundException(Long customerId) {
        super("Cart fot this customer id " + customerId + " doesn't exist!");
    }
}
