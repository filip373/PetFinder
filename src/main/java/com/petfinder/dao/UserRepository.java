package com.petfinder.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.petfinder.domain.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	//@Modifying
	//@Query("SELECT u FROM User u where u.login=:login")
	//@Param("login") 
	User findOneByLogin(String login);
	
	User findOneByEmail(String email);
}
