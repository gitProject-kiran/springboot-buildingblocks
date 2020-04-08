package com.kiranshinde.restservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kiranshinde.restservices.entities.User;

//Repository
@Repository
public interface UserRepository  extends JpaRepository<User, Long>{

	User findByUsername(String username);
}
