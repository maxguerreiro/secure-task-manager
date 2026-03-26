package com.maxwell.taskmanager.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxwell.taskmanager.dtos.UserCreateDTO;
import com.maxwell.taskmanager.dtos.UserDTO;
import com.maxwell.taskmanager.services.UserService;

import jakarta.validation.Valid;

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
	public ResponseEntity<List<UserDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody @Valid UserCreateDTO userDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.newUser(userDTO));
	}
}
