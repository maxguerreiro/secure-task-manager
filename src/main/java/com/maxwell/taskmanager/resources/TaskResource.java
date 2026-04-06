package com.maxwell.taskmanager.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.maxwell.taskmanager.dtos.TaskCreateDTO;
import com.maxwell.taskmanager.dtos.TaskDTO;
import com.maxwell.taskmanager.services.TaskService;

/**
 * REST controller responsible for task-related operations.
 *
 * Provides endpoints for creating, retrieving, updating,
 * and completing tasks associated with authenticated users.
 */
@RestController
@RequestMapping(value = "tasks")
public class TaskResource {
	
	@Autowired
	private TaskService service;

	/**
	 * Endpoint to retrieve all tasks of the authenticated user.
	 *
	 * This endpoint returns only the tasks that belong to the currently logged-in user,
	 * based on the authentication token provided in the request.
	 *
	 * @return HTTP 200 response containing a list of TaskDTO
	 */
	@GetMapping("/mytasks")
	public ResponseEntity<List<TaskDTO>> findMyTasks() {
		return ResponseEntity.ok().body(service.findMyTasks());
	}
	
	@GetMapping
	public ResponseEntity<List<TaskDTO>> findAll(){
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@GetMapping("/mytasks/{id}")
	public ResponseEntity<TaskDTO> findMyTasksById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findMyTasksById(id));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TaskDTO> findTasksById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findTasksById(id));
	}
	
	@PostMapping
	public ResponseEntity<TaskDTO> newTask(@RequestBody TaskCreateDTO task) {
		return ResponseEntity.ok().body(service.newTask(task));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TaskDTO> update(@PathVariable String id, @RequestBody TaskCreateDTO updatedTask) {
		return ResponseEntity.ok().body(service.update(id, updatedTask));
	}
	
	@PatchMapping("/{id}/complete")
	public ResponseEntity<TaskDTO> complete(@PathVariable String id) {
	    return ResponseEntity.ok(service.completeTask(id));
	}

}
