package com.maxwell.taskmanager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.maxwell.taskmanager.domain.Task;
import com.maxwell.taskmanager.domain.User;
import com.maxwell.taskmanager.dtos.TaskCreateDTO;
import com.maxwell.taskmanager.dtos.TaskDTO;
import com.maxwell.taskmanager.repositories.TaskRepository;
import com.maxwell.taskmanager.repositories.UserRepository;
import com.maxwell.taskmanager.services.exceptions.ResourceNotFoundException;

@Service
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
	
	public List<TaskDTO> findAll(){
		return taskRepo.findAll()
				.stream()
				.map(TaskDTO::new)
				.toList();
	}
	
	public TaskDTO findMyTasksById(String id) {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		
		User user = userRepo.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException(id));
		
		Task task = taskRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
		
		if (!user.getId().equals(task.getUserId())) {
			throw new RuntimeException("Access denied");
		}
		
		return new TaskDTO(task);
	}
	
	public TaskDTO findTasksById(String id) {
		Task task = taskRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
		
		return new TaskDTO(task);
		
	}
	
	public TaskDTO newTask(TaskCreateDTO dto) {
		
		var auth = SecurityContextHolder.getContext().getAuthentication();
		
		String email = auth.getName();
		
		User user = userRepo.findByEmail(email).
				orElseThrow(() -> new RuntimeException("User not found"));
		
		Task task = new Task();
		task.setTitle(dto.getTitle());
		task.setDescription(dto.getDescription());
		task.setCompleted(false);
		task.setUserId(user.getId());
		
		return new TaskDTO(taskRepo.save(task));
	}
	
	public TaskDTO update(String id, TaskCreateDTO updatedTask) {
		
		var auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		
		User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(id));
		
		Task task = taskRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		
		if (!user.getId().equals(task.getUserId())) {
			throw new RuntimeException("Access denied");
		}
		
		task.setTitle(updatedTask.getTitle());
		task.setDescription(updatedTask.getDescription());
		
		Task saved = taskRepo.save(task);

	    return new TaskDTO(saved);
	}
	
	public TaskDTO completeTask(String id) {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		
		String email = auth.getName();
		
		User user = userRepo.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException(id));
		
		Task task = taskRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
		
		if (!user.getId().equals(task.getUserId())) {
			throw new RuntimeException("Access denied");
		}
		
		task.setCompleted(!task.isCompleted());
		
		Task saved = taskRepo.save(task);
		
		return new TaskDTO(saved);
		
		
	}
}
