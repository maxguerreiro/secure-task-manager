package com.maxwell.taskmanager.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxwell.taskmanager.domain.User;
import com.maxwell.taskmanager.domain.enums.UserRole;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		User maria = new User("1", "Maria", "maria@gmail.com", "1a2b3c", UserRole.USER);
		User pedro = new User("2", "Pedro", "Pedro@gmail.com", "4d5e6f", UserRole.ADMIN);
		return ResponseEntity.ok().body(Arrays.asList(maria, pedro));
	}
}
