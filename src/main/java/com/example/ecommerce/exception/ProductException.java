package com.example.ecommerce.exception;


public class ProductException extends Exception{
    private String message;

    public ProductException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
