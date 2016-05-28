package com.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.models.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
	Account findAccountByPhone(String phone);
	Account findAccountById(Integer id);
}
