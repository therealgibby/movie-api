package com.example.movieapi.backend.exceptions;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException() {
        super("Movie Not Found");
    }

    public MovieNotFoundException(String exMessage) {
        super(exMessage);
    }
}
