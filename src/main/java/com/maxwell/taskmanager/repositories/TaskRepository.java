package com.maxwell.taskmanager.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.maxwell.taskmanager.domain.Task;

@Repository
public interface TaskRepository extends MongoRepository<Task, String>{
	
	/**
	 * Finds all tasks associated with a specific user ID.
	 *
	 * @param userId the ID of the user
	 * @return list of tasks belonging to the user
	 */
	List<Task> findByUserId(String userId);

}
