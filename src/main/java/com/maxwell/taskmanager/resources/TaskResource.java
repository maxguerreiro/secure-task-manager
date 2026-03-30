package com.maxwell.taskmanager.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxwell.taskmanager.dtos.TaskDTO;
import com.maxwell.taskmanager.services.TaskService;

@RestController
@RequestMapping("tasks")
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
	public ResponseEntity<List<TaskDTO>> findMyTasks() {
		return ResponseEntity.ok().body(service.findMyTasks());
	}

}
