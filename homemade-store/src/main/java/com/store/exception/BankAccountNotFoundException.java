package com.store.exception;

public class BankAccountNotFoundException extends RuntimeException {
    public BankAccountNotFoundException(String cardNumber) {
        super("Account with card number " + cardNumber + " was not found!");
    }
}
