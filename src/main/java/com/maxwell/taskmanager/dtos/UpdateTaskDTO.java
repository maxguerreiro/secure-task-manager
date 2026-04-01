package com.maxwell.taskmanager.dtos;

public class UpdateTaskDTO {
	
	public class TaskUpdateDTO {

	    private String title;
	    private String description;

	    public TaskUpdateDTO() {}

	    public String getTitle() {
	        return title;
	    }

	    public String getDescription() {
	        return description;
	    }
	}

}
