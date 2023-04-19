package com.store.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long customerId) {
        super("Customer with id " + customerId + " not found in the database!");
    }
}
