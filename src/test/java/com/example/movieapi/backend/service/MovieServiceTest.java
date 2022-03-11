package com.example.movieapi.backend.service;

import com.example.movieapi.backend.entity.Movie;
import com.example.movieapi.backend.exceptions.DuplicateMovieException;
import com.example.movieapi.backend.exceptions.MovieNotFoundException;
import com.example.movieapi.backend.repository.MovieRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;
    private MovieService movieService;

    @BeforeEach
    public void setup() {
        movieService = new MovieService(movieRepository);
    }


    // GET

    @Test
    @DisplayName("Should Return All Movies")
    public void shouldReturnAllMovies() {
        List<Movie> expectedMovieList = new ArrayList<>(Arrays.asList(
                new Movie(1, "Star Wars", "Director", "Actors"),
                new Movie(2, "Harry Potter", "Director", "Actors"),
                new Movie(3, "Movie Title", "Director", "Actors")
        ));

        Mockito.when(movieRepository.findAll()).thenReturn(expectedMovieList);

        List<Movie> actualMovieList = movieService.getAllMovies();

        assertEquals(expectedMovieList.size(), actualMovieList.size());
        assertEquals(3, actualMovieList.size());
    }

    @Test
    @DisplayName("Should Find Movie By Id")
    public void shouldFindPostById() {
        Movie expectedMovie = new Movie(123, "Movie Title", "Director", "Actors");

        Mockito.when(movieRepository.findById(123)).thenReturn(Optional.of(expectedMovie));

        Movie actualMovie = movieService.getMovie(123);

        assertEquals(expectedMovie, actualMovie);
    }

    // POST

    @Test
    @DisplayName("Should Fail When Movie Title Is Blank")
    public void shouldFailWhenMovieTitleIsBlank() {
        Movie movie = new Movie(1, "", "Director", "Actors");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> movieService.addMovie(movie));

        assertTrue(exception.getMessage().contains("Movie Title Was Not Given"));
    }

    @Test
    @DisplayName("Should Fail When Movie Exists By Title")
    public void shouldFailWhenMovieExistsByTitle() {
        Movie movie = new Movie(1, "Movie", "", "");

        Mockito.when(movieRepository.existsByTitle(movie.getTitle())).thenReturn(true);

        DuplicateMovieException exception = assertThrows(DuplicateMovieException.class, () -> movieService.addMovie(movie));

        assertTrue(exception.getMessage().contains("Movie Exists With Given Title"));
    }

    @Test
    @DisplayName("Should Save Movie")
    public void shouldSaveMovie() {
        Movie movie = new Movie(1, "Title", "Director", "Actors");

        movieService.addMovie(movie);

        Mockito.verify(movieRepository, Mockito.times(1)).save(ArgumentMatchers.any(Movie.class));
    }

    // PUT

    @Test
    @DisplayName("Should Fail When Id Does Not Exist")
    public void shouldFailWhenIdDoesNotExist() {
        Movie movie = new Movie(1, "Title", "Director", "Actors");

        Mockito.when(movieRepository.existsById(1)).thenReturn(false);

        MovieNotFoundException exception = assertThrows(MovieNotFoundException.class, () -> movieService.updateMovie(movie, 1));

        assertTrue(exception.getMessage().contains("A Movie Was Not Found With Given ID"));
    }

    @Test
    @DisplayName("Should Update Movie")
    public void shouldUpdateMovie() {
        Movie movie = new Movie(10, "Title", "Director", "Actors");
        int idFromUrl = 10;

        Mockito.when(movieRepository.existsById(idFromUrl)).thenReturn(true);

        movieService.updateMovie(movie, idFromUrl);

        Mockito.verify(movieRepository, Mockito.times(1)).save(ArgumentMatchers.any(Movie.class));
    }

    // DELETE

    @Test
    @DisplayName("Should Fail When Movie Doesn't Exist")
    public void shouldFailWhenMovieDoesNotExist() {
        int id = 1;

        Mockito.when(movieRepository.existsById(id)).thenReturn(false);

        MovieNotFoundException exception = assertThrows(MovieNotFoundException.class, () -> movieService.deleteMovie(id));

        assertTrue(exception.getMessage().contains("Movie ID Doesn't Exist"));
    }

    @Test
    @DisplayName("Should Delete Movie")
    public void shouldDeleteMovie() {
        int id = 1;

        Mockito.when(movieRepository.existsById(id)).thenReturn(true);

        movieService.deleteMovie(id);

        Mockito.verify(movieRepository, Mockito.times(1)).deleteById(ArgumentMatchers.anyInt());
    }
}