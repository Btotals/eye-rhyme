package com.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.models.MovieArticle;

@Repository
public interface MovieArticleRepository extends CrudRepository<MovieArticle, Integer> {
	MovieArticle findMovieArticleById(Integer id);
}
