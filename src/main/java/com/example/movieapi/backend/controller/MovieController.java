package com.example.movieapi.backend.controller;

import com.example.movieapi.backend.entity.Movie;
import com.example.movieapi.backend.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("")
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable int id) {
        try {
            return movieService.getMovie(id);
        } catch (RuntimeException e) {
            return new Movie(0, "", "", "");
        }
    }

    @PostMapping("")
    public ResponseEntity<String> addMovie(@RequestBody Movie movie) {
        try {
            movieService.addMovie(movie);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMovie(@RequestBody Movie movie, @PathVariable int id) {
        try {
            movieService.updateMovie(movie, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable int id) {
        try {
            movieService.deleteMovie(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

