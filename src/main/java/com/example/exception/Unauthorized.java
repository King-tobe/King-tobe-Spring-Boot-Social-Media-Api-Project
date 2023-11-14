package com.example.exception;

public class Unauthorized extends RuntimeException{
    public Unauthorized(String message){
        super(message);
    }
}
