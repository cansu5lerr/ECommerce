package com.example.ecommerce.exception;

public class UnauthorizedUserException extends Exception{
    public UnauthorizedUserException(String message) {
        super(message);
    }
}
