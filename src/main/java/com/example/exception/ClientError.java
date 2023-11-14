package com.example.exception;

public class ClientError extends RuntimeException{
   
    public ClientError(String message){
        super(message);
    }
}