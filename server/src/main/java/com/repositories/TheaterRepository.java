package com.repositories;

import org.springframework.data.repository.CrudRepository;

import com.models.Theater;

public interface TheaterRepository extends CrudRepository<Theater, Integer> {
	Theater findTheaterById(Integer id);
}
