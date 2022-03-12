package com.example.movieapi.repository;

import com.example.movieapi.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    boolean existsByTitle(String title);
}
