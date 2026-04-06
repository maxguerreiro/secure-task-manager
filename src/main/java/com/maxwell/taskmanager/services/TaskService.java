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
	 * Retrieves all tasks belonging to the authenticated user.
	 *
	 * The user is identified through the SecurityContext,
	 * ensuring that only their own tasks are returned.
	 *
	 * @return list of TaskDTO
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
	
	/**
	 * Retrieves a specific task belonging to the authenticated user.
	 *
	 * Ensures that the user can only access their own task.
	 *
	 * @param id task ID
	 * @return TaskDTO
	 *
	 * @throws ResourceNotFoundException if task or user not found
	 * @throws ForbiddenException if task does not belong to the user
	 */
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
	
	/**
	 * Retrieves a task by ID without ownership validation.
	 *
	 * This method is intended for administrative use and should
	 * be protected at the controller/security level.
	 *
	 * @param id task ID
	 * @return TaskDTO
	 *
	 * @throws ResourceNotFoundException if task not found
	 */
	public TaskDTO findTasksById(String id) {
		Task task = taskRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
		
		return new TaskDTO(task);
		
	}
	
	/**
	 * Creates a new task for the authenticated user.
	 *
	 * The task is automatically associated with the user
	 * based on the authentication token.
	 *
	 * @param dto task creation data
	 * @return created TaskDTO
	 *
	 * @throws ResourceNotFoundException if user not found
	 */
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
	
	/**
	 * Updates a task with ownership validation.
	 *
	 * Only the task owner is allowed to update it.
	 *
	 * @param id task ID
	 * @param dto updated data
	 * @return updated TaskDTO
	 *
	 * @throws ResourceNotFoundException if task or user not found
	 * @throws ForbiddenException if user is not the task owner
	 */
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
	
	/**
	 * Marks a task as completed.
	 *
	 * Only the task owner is allowed to perform this operation.
	 *
	 * @param id task ID
	 * @return updated TaskDTO
	 *
	 * @throws ResourceNotFoundException if task or user not found
	 * @throws ForbiddenException if user is not the task owner
	 */
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
