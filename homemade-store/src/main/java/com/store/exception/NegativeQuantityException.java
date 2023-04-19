package com.store.exception;

public class NegativeQuantityException extends RuntimeException {
    public NegativeQuantityException(){
        super("Quantity cannot be less than 1!");
    }
}
