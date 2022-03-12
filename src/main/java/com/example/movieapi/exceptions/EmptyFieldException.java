package com.example.movieapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmptyFieldException extends RuntimeException {

    public EmptyFieldException() {
        super("Field Was Empty");
    }

    public EmptyFieldException(String exMessage) {
        super(exMessage);
    }
}
