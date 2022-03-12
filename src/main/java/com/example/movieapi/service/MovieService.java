package com.example.movieapi.service;

import com.example.movieapi.entity.Movie;
import com.example.movieapi.exceptions.DuplicateMovieException;
import com.example.movieapi.exceptions.EmptyFieldException;
import com.example.movieapi.exceptions.MovieNotFoundException;
import com.example.movieapi.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Transactional(readOnly = true)
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Movie getMovie(int id) {
        return movieRepository.findById(id)
                .orElseThrow(MovieNotFoundException::new);
    }

    @Transactional
    public Movie addMovie(Movie movieFromUser) {
        if(movieFromUser.getTitle() == null || movieFromUser.getTitle().equals("")) {
            throw new EmptyFieldException("Movie Title Was Not Given");
        }
        if(movieRepository.existsByTitle(movieFromUser.getTitle())) {
            throw new DuplicateMovieException("Movie Exists With Given Title");
        }

        Movie movie = new Movie(movieFromUser.getTitle(), movieFromUser.getDirector(), movieFromUser.getActors());

        return movieRepository.save(movie);
    }

    @Transactional
    public Movie updateMovie(Movie movieFromUser, int id) {
        if(!movieRepository.existsById(id)) {
            throw new MovieNotFoundException("A Movie Was Not Found With Given ID");
        }

        Movie movie = new Movie(id, movieFromUser.getTitle(), movieFromUser.getDirector(), movieFromUser.getActors());

        return movieRepository.save(movie);
    }

    @Transactional
    public void deleteMovie(int id) {
        if(!movieRepository.existsById(id)) {
            throw new MovieNotFoundException("Movie ID Doesn't Exist");
        }

        movieRepository.deleteById(id);
    }
}
