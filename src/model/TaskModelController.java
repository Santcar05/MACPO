package model;

import java.util.ArrayList;

public class TaskModelController {

	private ArrayList<Task> Tasks = new ArrayList<Task>();
	
	public Task createTask(int IDTask, String Title, String Description, int Status, int Priority) {
		return new Task(IDTask, Title, Description, Status, Priority);
	}
	
	public void addTask(Task task) {
		Tasks.add(task);
	}
	
	public void deleteTask(Task task) {
		Tasks.remove(task);
	}
}
