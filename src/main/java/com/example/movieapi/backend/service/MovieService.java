package com.example.movieapi.backend.service;

import com.example.movieapi.backend.entity.Movie;
import com.example.movieapi.backend.repository.MovieRepository;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Movie getMovie(int id) {
        return Optional.of(movieRepository.findById(id))
                .get()
                .orElse(new Movie(0, "N/A", "", ""));
    }

    public void addMovie(Movie movie) throws IOException {
        if(movieRepository.existsById(movie.getId())) {
            throw new IOException("Movie Exists With Given ID");
        }
        if(movieRepository.existsByTitle(movie.getTitle())) {
            throw new IOException("Movie Title Already In Database");
        }
        if(movie.getTitle() == null || movie.getTitle().equals("")) {
            throw new IOException("Movie Title Was Not Given");
        }
        movieRepository.save(movie);
    }

    public void updateMovie(Movie movie, int id) throws IOException{
        if(!movieRepository.existsById(id)) {
            throw new IOException("A Movie Was Not Found With Given ID");
        }
        if(movie.getId() != id) {
            throw new IOException("Movie ID and ID in URL Do Not Match");
        }
        movieRepository.save(movie);
    }

    public void deleteMovie(int id) throws IOException{
        if(movieRepository.existsById(id))
            movieRepository.deleteById(id);
        else
            throw new IOException("Movie ID Doesn't Exist");
    }
}
