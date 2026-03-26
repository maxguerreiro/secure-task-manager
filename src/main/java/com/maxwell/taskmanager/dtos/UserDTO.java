package com.maxwell.taskmanager.dtos;

import com.maxwell.taskmanager.domain.User;
import com.maxwell.taskmanager.domain.enums.UserRole;

/**
 * Data Transfer Object for User.
 * Used to expose safe data to the client.
 */
public class UserDTO {
	
	private String id;
    private String username;
    private String email;
    private UserRole roles;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getRoles() {
		return roles;
	}

	public void setRoles(UserRole roles) {
		this.roles = roles;
	}
}
