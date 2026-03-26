package com.maxwell.taskmanager.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO used for user creation.
 */
public class UserCreateDTO {
	
	@NotBlank(message = "Username is required")
	private String userName;
	
	@Email(message = "Invalid email")
	private String email;
	
	@NotBlank(message = "Password is required")
	private String password;
	
	private String roles;
	
	public UserCreateDTO() {
	}

	public UserCreateDTO(@NotBlank(message = "Username is required") String userName,
			@Email(message = "Invalid email") String email, @NotBlank(message = "Password is required") String password,
			String roles) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
