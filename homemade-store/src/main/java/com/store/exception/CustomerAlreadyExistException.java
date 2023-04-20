package com.store.exception;

import com.store.domain.Customer;

public class CustomerAlreadyExistException extends RuntimeException {
    public CustomerAlreadyExistException(){
        super("The customer with this email already exist in the database");
    }
}
