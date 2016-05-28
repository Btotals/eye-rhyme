package com.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.models.MovieTicket;

@Repository
public interface MovieTicketRepository extends CrudRepository<MovieTicket, Integer> {
	List<MovieTicket> findByUserIdAndStatusEquals(Integer userId, Integer status);
	List<MovieTicket> findByUserIdAndStatusEqualsAndIdLessThan(Integer userId, Integer status, Integer ticket_id);
	List<MovieTicket> findTop10ByUserIdAndStatusEqualsOrderByIdDesc(Integer userId, Integer status);
	List<MovieTicket> findTop10ByUserIdAndStatusEqualsAndIdLessThanOrderByIdDesc(Integer userId, Integer status, Integer ticket_id);
}
