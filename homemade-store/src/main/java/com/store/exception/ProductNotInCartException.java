package com.store.exception;

public class ProductNotInCartException extends RuntimeException{
    public ProductNotInCartException(Long decorationId) {
        super("Product with Id " + decorationId + " not in cart.");
    }
}
