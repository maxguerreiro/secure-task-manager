package com.maxwell.taskmanager.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxwell.taskmanager.domain.User;
import com.maxwell.taskmanager.services.UserService;

/**
 * REST controller responsible for handling user-related requests.
 * 
 * This is a temporary test endpoint that returns a static list of users.
 * It will be replaced by a real implementation using database integration.
 */
@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	/**
     * Returns all users stored in the database.
     *
     * @return list of users
     */
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}
}
