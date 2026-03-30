package com.maxwell.taskmanager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.maxwell.taskmanager.domain.User;
import com.maxwell.taskmanager.dtos.TaskDTO;
import com.maxwell.taskmanager.repositories.TaskRepository;
import com.maxwell.taskmanager.repositories.UserRepository;

public class TaskService {
	
	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	
	/**
	 * Retrieves all tasks belonging to the currently authenticated user.
	 *
	 * This method extracts the authenticated user's identity from the SecurityContext,
	 * fetches the corresponding user from the database, and returns all tasks
	 * associated with that user.
	 *
	 * Ensures that each user only has access to their own tasks.
	 *
	 * @return a list of TaskDTO representing the user's tasks
	 *
	 * @throws RuntimeException if the authenticated user is not found in the database
	 */
	public List<TaskDTO> findMyTasks() {
		
		var auth = SecurityContextHolder.getContext().getAuthentication();
		
		String email = auth.getName();
		
		User user = userRepo.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		return taskRepo.findByUserId(user.getId())
				.stream()
				.map(TaskDTO::new)
				.toList();
				
		
	}

}
