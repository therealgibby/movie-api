package com.example.movieapi.backend.exceptions;

public class DuplicateMovieException extends RuntimeException {

    public DuplicateMovieException() {
        super("Movie Already Exists");
    }

    public DuplicateMovieException(String exMessage) {
        super(exMessage);
    }
}
