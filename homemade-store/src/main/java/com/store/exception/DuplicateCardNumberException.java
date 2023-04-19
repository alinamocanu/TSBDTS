package com.store.exception;

public class DuplicateCardNumberException extends RuntimeException {
    public DuplicateCardNumberException(String cardNumber) {
        super("This card already exists!");
    }
}
