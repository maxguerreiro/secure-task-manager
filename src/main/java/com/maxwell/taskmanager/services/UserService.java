package com.maxwell.taskmanager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxwell.taskmanager.domain.User;
import com.maxwell.taskmanager.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	/**
     * Returns all users stored in the database.
     *
     * @return list of users
     */
	public List<User> findAll() {
		return repo.findAll();
	}
}
