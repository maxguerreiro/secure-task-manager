package com.maxwell.taskmanager.services.exceptions;

/**
 * Exception thrown when a requested resource cannot be found.
 *
 * This is used when an entity (such as User or Task)
 * does not exist in the database.
 */
public class ResourceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(Object id) {
		super("Resource not found. Id " + id);
	}
}
