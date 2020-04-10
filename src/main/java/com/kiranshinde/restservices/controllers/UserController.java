package com.kiranshinde.restservices.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.kiranshinde.restservices.entities.User;
import com.kiranshinde.restservices.exceptions.UserExistsException;
import com.kiranshinde.restservices.exceptions.UserNameNotFoundException;
import com.kiranshinde.restservices.exceptions.UserNotFoundException;
import com.kiranshinde.restservices.services.UserService;

//Controller -
@RestController
@Validated
public class UserController {
	
	// Autowire the UserService
		@Autowired
		private UserService userService;

		// getAllUsers Method
		@GetMapping("/users")
		public List<User> getAllUsers() {

			return userService.getAllUsers();

		}

		// Create User Method
		// @RequestBody Annotation
		// @PostMapping Annotation
		@PostMapping("/users")
		public ResponseEntity<Void> createUser(@Valid @RequestBody User user, UriComponentsBuilder builder) {
			try {
				userService.createUser(user);
				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
				return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
				
			} catch(UserExistsException ex) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
			}
		}

		// getUserById
		@GetMapping("/users/{id}")
		public Optional<User> getUserById(@PathVariable("id") @Min(1) Long id) {

			try {
				return userService.getUserById(id);
			} catch (UserNotFoundException ex) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
			}

		}

		// updateUserById
		@PutMapping("/users/{id}")
		public User updateUserById(@PathVariable("id") Long id, @RequestBody User user) {

			try {
				return userService.updateUserById(id, user);
			} catch (UserNotFoundException ex) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
			}

		}

		// deleteUserById
		@DeleteMapping("/users/{id}")
		public void deleteUserById(@PathVariable("id") Long id) {
			userService.deleteUserById(id);
		}

		// getUserByUsername
		@GetMapping("/users/byusername/{username}")
		public User getUserByUsername(@PathVariable("username") String username) throws UserNameNotFoundException {
			User user =  userService.getUserByUsername(username);
			if(user == null)
				throw new UserNameNotFoundException("Username: '" + username + "' not found in User repository");
			return user;
		
		}
}
