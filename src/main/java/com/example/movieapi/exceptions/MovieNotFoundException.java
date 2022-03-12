package com.example.movieapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException() {
        super("Movie Not Found");
    }

    public MovieNotFoundException(String exMessage) {
        super(exMessage);
    }
}
