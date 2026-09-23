package com.joach27.urltoshort.exception;

public class UrlNotFoundException extends RuntimeException{

    public UrlNotFoundException(String message){
        super(message);
    }
}