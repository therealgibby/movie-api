package com.example.movieapi.backend.repository;

import com.example.movieapi.backend.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    boolean existsByTitle(String title);
}
