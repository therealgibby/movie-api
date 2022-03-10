package com.example.movieapi.backend.service;

import com.example.movieapi.backend.entity.Movie;
import com.example.movieapi.backend.exceptions.DuplicateMovieException;
import com.example.movieapi.backend.exceptions.MovieNotFoundException;
import com.example.movieapi.backend.repository.MovieRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        List<Movie> allMovies = new ArrayList<>();
        allMovies.addAll(movieRepository.findAll());
        return allMovies;
    }

    public Movie getMovie(int id) throws RuntimeException {
        return movieRepository.findById(id)
                .orElseThrow(MovieNotFoundException::new);
    }

    public void addMovie(Movie movie) throws RuntimeException {
        if(movieRepository.existsById(movie.getId()) || movieRepository.existsByTitle(movie.getTitle())) {
            throw new DuplicateMovieException("Movie Exists With Given ID Or Title");
        }
        if(movie.getTitle() == null || movie.getTitle().equals("")) {
            throw new RuntimeException("Movie Title Was Not Given");
        }
        movieRepository.save(movie);
    }

    public void updateMovie(Movie movie, int id) throws RuntimeException {
        if(movie.getId() != id) {
            throw new RuntimeException("Movie ID and ID in URL Do Not Match");
        }
        if(!movieRepository.existsById(id)) {
            throw new MovieNotFoundException("A Movie Was Not Found With Given ID");
        }
        movieRepository.save(movie);
    }

    public void deleteMovie(int id) throws RuntimeException {
        if(movieRepository.existsById(id))
            movieRepository.deleteById(id);
        else
            throw new MovieNotFoundException("Movie ID Doesn't Exist");
    }
}
