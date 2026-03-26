package com.maxwell.taskmanager.domain.enums;

public enum UserRole {
	
	ADMIN("admin"),
	USER("user");

	private String roles;
	
	UserRole(String roles) {
		this.roles = roles;
	}
	
	public String getRole() {
		return roles;
	}
}
