package com.store.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String cardNumber) {
        super("There aren't enough funds in account with card number " + cardNumber);
    }
}
