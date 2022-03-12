package com.example.movieapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateMovieException extends RuntimeException {

    public DuplicateMovieException() {
        super("Movie Already Exists");
    }

    public DuplicateMovieException(String exMessage) {
        super(exMessage);
    }
}
