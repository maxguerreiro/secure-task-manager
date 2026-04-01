package com.maxwell.taskmanager.dtos;

public class UserUpdateDTO {
	
	private String userName;
	private String email;
	private String password;
	
	public UserUpdateDTO() {
		super();
	}

	public UserUpdateDTO(String userName, String email, String password) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}


	public String getEmail() {
		return email;
	}


	public String getPassword() {
		return password;
	}

}
