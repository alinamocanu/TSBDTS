package com.store.exception;

public class EmptyCartException extends RuntimeException {
    public EmptyCartException(Long customerId) {
        super("Cart with customer id " + customerId + " is empty");
    }
}
