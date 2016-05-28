package com.repositories;

import org.springframework.data.repository.CrudRepository;

import com.models.MovieActivity;

public interface MovieActivityRepository extends CrudRepository<MovieActivity, Integer> {
	MovieActivity findMovieActivityById(Integer id);
}
