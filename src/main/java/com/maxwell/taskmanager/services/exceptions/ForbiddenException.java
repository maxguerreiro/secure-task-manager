package com.maxwell.taskmanager.services.exceptions;

/**
 * Exception thrown when a user attempts to access a resource
 * without having the required permissions.
 *
 * This is typically used for authorization failures,
 * where the user is authenticated but not allowed to perform
 * the requested operation.
 */
public class ForbiddenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ForbiddenException(String message) {
		super(message);
	}

}
