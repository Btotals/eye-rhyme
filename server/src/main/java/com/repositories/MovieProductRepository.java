package com.repositories;

import org.springframework.data.repository.CrudRepository;

import com.models.MovieProduct;

public interface MovieProductRepository extends CrudRepository<MovieProduct, Integer> {
	MovieProduct findMovieProductById(Integer id);
}
