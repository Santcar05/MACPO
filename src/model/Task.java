package model;

import java.io.Serializable;

public class Task implements Serializable {
	private static int IDTaskCount = 0;
	private int IDTask;
	private String Title;
	private String Description;
	private int Status;
	private int Priority;
	
	public Task(){
		
	}
	
	public Task(String Title, String Description, int Status, int Priority) {
		this.IDTask = IDTaskCount;
		IDTaskCount++;
		this.Description = Description;
		this.Priority = Priority;
		this.Title = Title;
		this.Status = Status;
	}

	public static int getIDTaskCount() {
		return IDTaskCount;
	}

	public static void setIDTaskCount(int iDTaskCount) {
		IDTaskCount = iDTaskCount;
	}

	public int getIDTask() {
		return IDTask;
	}

	public void setIDTask(int iDTask) {
		IDTask = iDTask;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public int getPriority() {
		return Priority;
	}

	public void setPriority(int priority) {
		Priority = priority;
	}
	
	
}
