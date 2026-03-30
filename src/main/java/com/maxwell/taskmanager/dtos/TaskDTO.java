package com.maxwell.taskmanager.dtos;

import com.maxwell.taskmanager.domain.Task;

public class TaskDTO {

	private String id;
	private String title;
	private String description;
	private boolean completed;

	public TaskDTO(Task task) {
		this.id = task.getId();
		this.title = task.getTitle();
		this.description = task.getDescription();
		this.completed = task.isCompleted();

	}

	public TaskDTO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	
}
