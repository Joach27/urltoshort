package com.joach27.urltoshort.exception;

public class SlugNotFoundException extends RuntimeException{

    public SlugNotFoundException(String message){
        super(message);
    }
}