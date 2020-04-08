package com.kiranshinde.restservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiranshinde.restservices.entities.User;
import com.kiranshinde.restservices.exceptions.UserNotFoundException;
import com.kiranshinde.restservices.repositories.UserRepository;

//Service
@Service
public class UserService {

	// Autowire the UserRepository
	@Autowired
	private UserRepository userRepository;

	// getAllUsers Method
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	//CreateUser Method
	public User createUser(User user) {
		return userRepository.save(user);
	}
	
	//getUserByIds
	public Optional<User> getUserById(Long id) throws UserNotFoundException{
		Optional<User> user = userRepository.findById(id);		
		if(!user.isPresent()) {
			throw new UserNotFoundException("User not found!!");
		}
		
		return user;
	}

	//updateUserById
	public User updateUserById(Long id, User user) {
		user.setId(id);
		return userRepository.save(user);		
	}	
	
	//deleteUserById
	public void deleteUserById(Long id) {
		if(userRepository.findById(id).isPresent()) {
			userRepository.deleteById(id);
			
		}
	}	
	
	//getUserByUsername	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
