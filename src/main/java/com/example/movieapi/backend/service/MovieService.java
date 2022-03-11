package com.example.movieapi.backend.service;

import com.example.movieapi.backend.entity.Movie;
import com.example.movieapi.backend.exceptions.DuplicateMovieException;
import com.example.movieapi.backend.exceptions.MovieNotFoundException;
import com.example.movieapi.backend.repository.MovieRepository;
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
    public Movie getMovie(int id) throws RuntimeException {
        return movieRepository.findById(id)
                .orElseThrow(MovieNotFoundException::new);
    }

    @Transactional
    public void addMovie(Movie movieFromUser) throws RuntimeException {
        if(movieFromUser.getTitle() == null || movieFromUser.getTitle().equals("")) {
            throw new RuntimeException("Movie Title Was Not Given");
        }
        if(movieRepository.existsByTitle(movieFromUser.getTitle())) {
            throw new DuplicateMovieException("Movie Exists With Given Title");
        }

        Movie movie = new Movie(movieFromUser.getTitle(), movieFromUser.getDirector(), movieFromUser.getActors());

        movieRepository.save(movie);
    }

    @Transactional
    public void updateMovie(Movie movieFromUser, int id) throws RuntimeException {
        if(!movieRepository.existsById(id)) {
            throw new MovieNotFoundException("A Movie Was Not Found With Given ID");
        }

        Movie movie = new Movie(id, movieFromUser.getTitle(), movieFromUser.getDirector(), movieFromUser.getActors());

        movieRepository.save(movie);
    }

    @Transactional
    public void deleteMovie(int id) throws RuntimeException {
        if(movieRepository.existsById(id))
            movieRepository.deleteById(id);
        else
            throw new MovieNotFoundException("Movie ID Doesn't Exist");
    }
}
