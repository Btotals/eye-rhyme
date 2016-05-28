package com.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	User findUserByEmail(String email);
	User findUserById(Integer id);
}
