package com.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.models.MovieDescription;

@Repository
public interface MovieDescriptionRepository extends CrudRepository<MovieDescription, Integer> {
	MovieDescription findMovieDescriptionById(Integer id);
}
